package com.klst.eorder.openTrans;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bmecat.bmecat._2005.DtCURRENCIES;
import org.bmecat.bmecat._2005.TypePARTYID;
import org.opentrans.xmlschema._2.ALLOWORCHARGE;
import org.opentrans.xmlschema._2.ALLOWORCHARGESFIX;
import org.opentrans.xmlschema._2.ORDERINFO;
import org.opentrans.xmlschema._2.ORDERPARTIESREFERENCE;
import org.opentrans.xmlschema._2.PARTIES;
import org.opentrans.xmlschema._2.PARTY;
import org.opentrans.xmlschema._2.PAYMENTTERMS;
import org.opentrans.xmlschema._2.TIMEFORPAYMENT;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.PaymentMeansEnum;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG4_Seller;
import com.klst.eorder.api.BG7_Buyer;
import com.klst.eorder.openTrans.Party.PartyRole;

public class OrderInfo extends ORDERINFO implements BG4_Seller, BG7_Buyer {

	private static final Logger LOG = Logger.getLogger(OrderInfo.class.getName());

//	// factory
//	static OrderInfo create() {
//		return new OrderInfo(null); 
//	}
//	
	// copy factory
	static OrderInfo create(ORDERINFO object) {
		if(object instanceof ORDERINFO && object.getClass()!=ORDERINFO.class) {
			// object is instance of a subclass of ORDERINFO, but not ORDERINFO itself
			return (OrderInfo)object;
		} else {
			return new OrderInfo(object); 
		}
	}
	
	// copy ctor
	private OrderInfo(ORDERINFO object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
		if(object==null) {
			setPARTIES(new PARTIES());
			setORDERPARTIESREFERENCE(new ORDERPARTIESREFERENCE());
		}
	}

	// Order number BT-1 Identifier (mandatory) - A unique identification of the Order.
	void setId(String id) {
		super.setORDERID(id);
	}
	String getId() {
		return super.getORDERID();
	}
	
	// Document issue date, BT-2 : <ORDER_DATE>2009-05-13T06:20:00+01:00</ORDER_DATE>
/* in doku dtDATETIME und nicht String:
dieser neue Datentyp löst die Datentypen dtDATETYPE, dtTIMETYPE und dtTIMEZONETYPE ab.
Beispiele:
2005-03-27T08:10:30+01:00 (entspricht: 27. März 2005, 08:10:30 Mitteleuropäische Zeit);
2005-03;
2005-03-27;
2005-03-27T08:10
 */
	void setIssueDate(Timestamp timestamp) {
		super.setORDERDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}
	Timestamp getIssueDateAsTimestamp() {
		return DateTimeFormats.dtDATETIMEToTs(super.getORDERDATE());
	}

	/* DELIVERYDATE
	 * 
	 * Zeitraum oder Zeitpunkt für das Lieferdatum (bzw. die Leistungserbringung). 
	 * Das Lieferdatum spezifiziert den Eingang der beauftragten Ware beim Einkäufer. 
	 * Wenn sich dieses Datum auf Positions-Ebene vom Datum auf Kopf-Ebene unterscheidet, 
	 * so gilt für diese Position das entsprechende Positions-Datum. 
	 * 
	 * Möchte man exakt einen Liefer-Zeitpunkt definieren, 
	 * zum Beispiel in der Wareneingangsbestätigung(RECEIPTACKNOWLEDGEMENT), 
	 * sollten beide Unterelemente, DELIVERY_DATE und DELIVERY_END_DATE, gleich befüllt werden.
	 */
	void setDeliveryPeriod(DeliveryDate period) {
		super.setDELIVERYDATE(period);
	}
	void setDeliveryDate(Timestamp timestamp) {
		setDeliveryPeriod(DeliveryDate.create(timestamp, timestamp));
	}
	Timestamp getDeliveryDateAsTimestamp() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return DateTimeFormats.dtDATETIMEToTs(getDELIVERYDATE().getDELIVERYSTARTDATE()); 
		}
		return null;
	}
	IPeriod getDeliveryPeriod() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return null;
		}
		// DELIVERYDATE ist Zeitraum
		return DeliveryDate.create(getDELIVERYDATE());
	}

	// 344: BT-10 0..1 Buyer reference
	// Die Referenz wird vom Käufer festgelegt, vom Verkäufer aber im Beleg angegeben
	void setBuyerReference(String reference) {
		if(reference==null) return;
		super.getORDERPARTIESREFERENCE().setBUYERIDREF(new PartyID(reference));
	}
	String getBuyerReferenceValue() {
		TypePARTYID id = super.getORDERPARTIESREFERENCE().getBUYERIDREF();
		return id==null ? null : id.getValue();
	}
	
	// 345: BG-4 1..1 SELLER @see BG4_Seller
	@Override
	public void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId, String companyLegalForm) {
		BusinessParty party = Party.create(name, null, address, contact);
		party.setCompanyId(companyId);
		party.setCompanyLegalForm(companyLegalForm);
		setSeller(party);
	}
	@Override
	public void setSeller(BusinessParty party) {
		if(party==null) return;
		setParty(party, Party.PartyRole.supplier);
		// required:
		PartyID id = (PartyID)party.getIdentifier();
		if(id!=null) {
			super.getORDERPARTIESREFERENCE().setSUPPLIERIDREF(id);
			return;
		}
		id = (PartyID)party.getCompanyIdentifier();
		if(id!=null) {
			super.getORDERPARTIESREFERENCE().setSUPPLIERIDREF(id);
			return;
		}
		String name = party.getRegistrationName();
		if(name!=null) {
			super.getORDERPARTIESREFERENCE().setSUPPLIERIDREF(new PartyID(name));
			return;
		}
		name = party.getBusinessName();
		if(name!=null) {
			super.getORDERPARTIESREFERENCE().setSUPPLIERIDREF(new PartyID(name));
			return;
		}
		LOG.warning("No SUPPLIERIDREF for party:"+party);
	}
	
	@Override
	public BusinessParty getSeller() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.supplier);
	}
	
	// 390: BG-7 1..1 BUYER @see BG7_Buyer
	@Override
	public void setBuyer(String name, PostalAddress address, ContactInfo contact) {
		setBuyer(Party.create(name, null, address, contact));
	}
	@Override
	public void setBuyer(BusinessParty party) {
		setParty(party, Party.PartyRole.buyer);
		// required:
		PartyID id = (PartyID)party.getIdentifier();
		if(id!=null) {
			super.getORDERPARTIESREFERENCE().setBUYERIDREF(id);
			return;
		}
		id = (PartyID)party.getCompanyIdentifier();
		if(id!=null) {
			super.getORDERPARTIESREFERENCE().setBUYERIDREF(id);
			return;
		}
		String name = party.getRegistrationName();
		if(name!=null) {
			super.getORDERPARTIESREFERENCE().setBUYERIDREF(new PartyID(name));
			return;
		}
		name = party.getBusinessName();
		if(name!=null) {
			super.getORDERPARTIESREFERENCE().setBUYERIDREF(new PartyID(name));
			return;
		}
		LOG.warning("No BUYERIDREF for party:"+party);
	}
	@Override
	public BusinessParty getBuyer() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.buyer);
	}

	void setShipTo(BusinessParty party) {
		setParty(party, Party.PartyRole.delivery);
	}
	BusinessParty getShipTo() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.delivery);
	}
	
	void setInvoicer(BusinessParty party) {
		setParty(party, Party.PartyRole.invoice_issuer);
	}
	BusinessParty getInvoicer() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.invoice_issuer);
	}

	void setBillTo(BusinessParty party) {
		setParty(party, Party.PartyRole.invoice_recipient);
	}
	BusinessParty getBillTo() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.invoice_recipient);
	}

	private void setParty(BusinessParty party, PartyRole partyrole) {
		((PARTY)party).getPARTYROLE().add(partyrole.toString());
		super.getPARTIES().getPARTY().add((Party)party);
	}

	// 790: BT-5 1..1 Document currency code, in OT optional
	// Währung als Vorgabewert für alle Preisangaben im Dokument
	void setDocumentCurrency(String isoCurrencyCode) {
		super.setCURRENCY(DtCURRENCIES.fromValue(isoCurrencyCode)); // enum DtCURRENCIES value
	}
	String getDocumentCurrency() {
		return getCURRENCY().value();
	}

	// 888: BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
	// 903: BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
	void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		if(allowanceOrCharge==null) return; // defensiv
		
		// in ORDER_INFO.PAYMENT.PAYMENT_TERMS.TIME_FOR_PAYMENT auf Belegebene
		// PAYMENT nicht in ORDERRESPONSEINFO
		// aber in ORDERCHANGEINFO
		Payment payment = getPayment();
		if(payment==null) {
			payment = Payment.create(PaymentMeansEnum.DebitTransfer, null);
			super.setPAYMENT(payment);
		}

		if(payment.getPAYMENTTERMS()==null) {
			payment.setPAYMENTTERMS(new PAYMENTTERMS());
		}
		
	    // Liste festgelegter Zu- oder Abschläge, die noch auf den Preis angewendet werden.
		// siehe auch Productpricefix#addAllowanceCharge

		List<TIMEFORPAYMENT> tfpList = payment.getPAYMENTTERMS().getTIMEFORPAYMENT();
		TIMEFORPAYMENT tfp = new TIMEFORPAYMENT();
		
		ALLOWORCHARGESFIX acf = tfp.getALLOWORCHARGESFIX();
		if(acf==null) {
			acf = new ALLOWORCHARGESFIX();
			tfp.setDAYS(BigInteger.ZERO);
			tfp.setALLOWORCHARGESFIX(acf);
		}
		tfpList.add(tfp);
		acf.getALLOWORCHARGE().add((ALLOWORCHARGE)allowanceOrCharge);

/*
public class TIMEFORPAYMENT:
    protected String paymentdate;   Zeitpunkt an dem eine Zahlung durchgeführt werden soll
    protected BigInteger days;      Zeitangabe in Tagen
    protected Float discountfactor; Faktor zur Angabe des Skonto, Beispiel: Wert 0.3 entspricht 3% Skonto
    protected ALLOWORCHARGESFIX alloworchargesfix; Liste festgelegter Zu- oder Abschläge die noch auf den Preis angewendet werden.

 */
	}
	List<AllowancesAndCharges> getAllowancesAndCharges() {
		List<AllowancesAndCharges> res = new ArrayList<AllowancesAndCharges>();
		Payment payment = getPayment();
		if(payment==null) return res; // empty
		
		if(payment.getPAYMENTTERMS()==null) return res; // empty
		
		List<TIMEFORPAYMENT> tfpList = payment.getPAYMENTTERMS().getTIMEFORPAYMENT();
		tfpList.forEach(tfp -> {
			ALLOWORCHARGESFIX acf = tfp.getALLOWORCHARGESFIX();
			if(acf!=null) {
				acf.getALLOWORCHARGE().forEach(aoc -> {
					res.add(AllowOrCharge.create(aoc));
				});
			}
		});
		return res;
	}
	
	// 875: BG-16.BT-81 PAYMENT INSTRUCTIONS PaymentMeans
	PaymentMeansEnum getPaymentMeansEnum() {
		Payment payment = getPayment();
		if(payment==null) return null;
		
		return payment.getPaymentMeansEnum();
	}
	void setPaymentMeansEnum(PaymentMeansEnum code) {
		if(code==null) return;
		Payment payment = getPayment();
		if(payment==null) {
			payment = Payment.create(code, null);
			super.setPAYMENT(payment);
		} else {
			LOG.warning("Payment is just set to "+payment.getPaymentMeansEnum() + " - ignored:"+code);
		}
	}
	
	// 925: BT-20 PAYMENT TERMS / Zahlungsmodalitäten
	// in CIO nur description
	// in OT.Order in ORDER_INFO.PAYMENT.PAYMENT_TERMS: List<PAYMENTTERM>
	// mit type="unece" + value aus UN/ECE 4279  Payment terms type code qualifier
	void addPaymentTerm(String description) {
		if(description==null) return;
		List<String> paymentTerms = new ArrayList<String>();
		paymentTerms.add(description);
		setPaymentTerms(paymentTerms);
	}
	void setPaymentTerms(List<String> paymentTerms) {
		if(paymentTerms==null) return;
		Payment payment = getPayment();
		if(payment==null) {
			payment = Payment.create(paymentTerms);
			super.setPAYMENT(payment);
		} else {
			payment.addPaymentTerms(paymentTerms);
		}
	}
	List<String> getPaymentTerms() {
		Payment payment = getPayment();
		if(payment==null) return null;
		
		return payment.getPaymentTerms();
	}

	private Payment getPayment() {
		if(super.getPAYMENT()==null) return null;
		return Payment.create(getPAYMENT());
	}

}
