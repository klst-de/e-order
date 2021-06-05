package com.klst.eorder.openTrans;

import java.sql.Timestamp;
import java.util.logging.Logger;

import org.bmecat.bmecat._2005.DtCURRENCIES;
import org.opentrans.xmlschema._2.ORDERPARTIESREFERENCE;
import org.opentrans.xmlschema._2.ORDERRESPONSEINFO;
import org.opentrans.xmlschema._2.PARTIES;
import org.opentrans.xmlschema._2.PARTY;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.openTrans.Party.PartyRole;

/*
   required:
    protected String orderid;            ==> setId
    protected String orderresponsedate;  ==> setIssueDate
    protected PARTIES parties;           ==> setParty
    protected ORDERPARTIESREFERENCE orderpartiesreference; TODO:
     TypePARTYID orderpartiesreference.buyeridref;
     TypePARTYID orderpartiesreference.supplieridref;
			<ORDER_PARTIES_REFERENCE>
				<bmecat:BUYER_IDREF type="iln">7611007000004</bmecat:BUYER_IDREF>
				<bmecat:SUPPLIER_IDREF type="iln">7611577000008</bmecat:SUPPLIER_IDREF>
			</ORDER_PARTIES_REFERENCE>
 */
public class OrderResponseInfo extends ORDERRESPONSEINFO {

	private static final Logger LOG = Logger.getLogger(OrderResponseInfo.class.getName());

	// copy factory
	static OrderResponseInfo create(ORDERRESPONSEINFO object) {
		if(object instanceof ORDERRESPONSEINFO && object.getClass()!=ORDERRESPONSEINFO.class) {
			// object is instance of a subclass of ORDERRESPONSEINFO, but not ORDERRESPONSEINFO itself
			return (OrderResponseInfo)object;
		} else {
			return new OrderResponseInfo(object); 
		}
	}
	
	// copy ctor
	private OrderResponseInfo(ORDERRESPONSEINFO object) {
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
	void setIssueDate(Timestamp timestamp) {
		super.setORDERRESPONSEDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}
	Timestamp getIssueDateAsTimestamp() {
		return DateTimeFormats.dtDATETIMEToTs(super.getORDERRESPONSEDATE());
	}

	// DELIVERYDATE / Zeitraum oder Zeitpunkt
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
	
	// 345: BG-4 1..1 SELLER @see BG4_Seller
	void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId,
			String companyLegalForm) {
		// TODO Auto-generated method stub	
	}
	void setSeller(BusinessParty party) {
		setParty(party, Party.PartyRole.supplier);
		// required:
		super.getORDERPARTIESREFERENCE().setSUPPLIERIDREF((PartyID)party.getIdentifier());
	}
	BusinessParty getSeller() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.supplier);
	}

	// 390: BG-7 1..1 BUYER @see BG7_Buyer
	void setBuyer(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub	
//		BusinessParty party = TradeParty.create(name, null, address, contact);
//		setBuyer(party);
	}
	void setBuyer(BusinessParty party) {
		setParty(party, Party.PartyRole.buyer);
		// required:
		super.getORDERPARTIESREFERENCE().setBUYERIDREF((PartyID)party.getIdentifier());
	}
	BusinessParty getBuyer() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.buyer);
	}

	void setShipToParty(BusinessParty party) {
		setParty(party, Party.PartyRole.delivery);
	}
	BusinessParty getShipToParty() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.delivery);
	}
	
	void setBillTo(BusinessParty party) {
		setParty(party, Party.PartyRole.invoice_recipient);
	}
	BusinessParty getBillTo() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.invoice_recipient);
	}

	private void setParty(BusinessParty party, PartyRole partyrole) {
		if(super.getPARTIES()==null) {
			super.setPARTIES(new PARTIES());
		}
		((PARTY)party).getPARTYROLE().add(partyrole.toString());
		super.getPARTIES().getPARTY().add((Party)party);
	}

	// 790: BT-5 1..1 Document currency code
	void setDocumentCurrency(String isoCurrencyCode) {
		super.setCURRENCY(DtCURRENCIES.fromValue(isoCurrencyCode)); // enum DtCURRENCIES value
	}
	String getDocumentCurrency() {
		return getCURRENCY().value();
	}

}
