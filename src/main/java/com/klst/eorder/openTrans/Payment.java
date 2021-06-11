package com.klst.eorder.openTrans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.PAYMENT;
import org.opentrans.xmlschema._2.PAYMENTTERM;
import org.opentrans.xmlschema._2.PAYMENTTERMS;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.untdid.PaymentMeansEnum;

/* PAYMENT is part in ORDERINFO	ORDERCHANGEINFO, not in ORDERRESPONSEINFO !

Im Element PAYMENT werden Informationen zur Zahlungsweise zusammengefasst. 
Es muss genau eine Zahlungsweise verwendet werden. 
Soll keine Zahlungsinformation übertragen werden (z.B. weil diese in einem Rahmenvertrag hinterlegt ist), 
so wird das Element nicht verwendet.

PAYMENT contains a choice of
 CARD ,
 ACCOUNT ,
 DEBIT ,
 CHECK or
 CASH

    String centralregulation
    PAYMENTTERMS paymentterms // Angaben zu Zahlungsmodalitäten (925: BT-20)

 */
public class Payment extends PAYMENT {

	private static final Logger LOG = Logger.getLogger(Payment.class.getName());

	static Payment create(PaymentMeansEnum paymentChoice, List<String> paymentTerms) {
		return new Payment(paymentChoice, paymentTerms);
	}
	static Payment create(List<String> paymentTerms) {
		return new Payment(PaymentMeansEnum.DebitTransfer, paymentTerms);
	}
	
	// copy factory
	static Payment create(PAYMENT object) {
		if(object instanceof PAYMENT && object.getClass()!=PAYMENT.class) {
			// object is instance of a subclass of PAYMENT, but not PAYMENT itself
			return (Payment)object;
		} else {
			return new Payment(object); 
		}
	}

	// copy ctor
	private Payment(PAYMENT object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	// siehe BG-16.BT-81 Code für die Zahlungsart
	private Payment(PaymentMeansEnum paymentChoice, List<String> paymentTerms) {
		switch(paymentChoice) {
		case BankCard : // CARD TODO
			break;
		case CreditTransfer : // ACCOUNT		
		case SEPACreditTransfer :	
//			 ACCOUNT, Bankverbindung, SEPA-Überweisung, TODO
			break;
		case DebitTransfer : // DebitTransfer, Gutschriftsverfahren
			super.setDEBIT(Boolean.TRUE.toString());
			break;
		case Cheque : // CHECK, Scheckzahlung
			super.setCHECK(Boolean.TRUE.toString());
			break;
		case InCash : // CASH, Barzahlung
			super.setCHECK(Boolean.TRUE.toString());
			break;
		default:
			LOG.warning("default paymentChoice is DEBIT. "+paymentChoice+ " is ignored");
			super.setDEBIT(Boolean.TRUE.toString()); // default choice
		}
		addPaymentTerms(paymentTerms);
	}

/* in PAYMENTTERMS:
    protected List<PAYMENTTERM> paymentterm; // Die Angabe kann gemäß UN/ECE oder unternehmensspezifisch erfolgen
     PAYMENTTERM.type="unece"; .value="2" // 4279  Payment terms type code qualifier,  2 == End of month
     PAYMENTTERM.type="other"; .value="60 Tage netto"
     
    protected List<TIMEFORPAYMENT> timeforpayment;
    protected String valuedate; // Angabe der Valutafrist über ein Datum. 
    Werden die Zahlungsziele (TIME_FOR_PAYMENT) nicht oder über die Angabe von Tagen (DAYS) spezifiziert, 
    dann beginnt die Laufzeit der Zahlungsfristen mit dem Valutadatum. 
    Werden die Zahlungsziele über Datumsangaben mit dem Element PAYMENT_DATE definiert, 
    so ist diese Angabe maßgeblich und das Valutadatum sollte nicht genutzt werden.

   in TIMEFORPAYMENT: // Zahlungsziele
	 choice
	 - PAYMENT_DATE
	 - DAYS
	 
	 und
	 choice (optional)
	 - DISCOUNT_FACTOR
	 - ALLOW_OR_CHARGES_FIX
// Liste festgelegter Zu- oder Abschläge die noch auf den Preis angewendet werden.
// siehe auch Productpricefix#addAllowanceCharge
 */
	private static final String PT_UNECE = "unece";
	private static final String PT_OTHER = "other";
	List<String> getPaymentTerms() {
		List<String> res = new ArrayList<String>();
		if(super.getPAYMENTTERMS()==null) return null;
		List<PAYMENTTERM> terms = getPAYMENTTERMS().getPAYMENTTERM();
		terms.forEach(t -> {
			if(PT_OTHER.equals(t.getType())) {
				res.add(t.getValue());
			} else if(PT_UNECE.equals(t.getType())) {
				// 4279  Payment terms type code ist numerisch
				// TODO text dazu liefern
				res.add(t.getValue());
			} else {
				// nur "other" + "unece" sind in OT vorgesehen
				LOG.warning("invalid PAYMENTTERM.type "+t.getType() + "(other assumed)");
				res.add(t.getValue());
			}
		});
		return res;
	}

	void addPaymentTerms(List<String> paymentTerms) {
		if(paymentTerms==null) return;
		if(super.getPAYMENTTERMS()==null) {
			setPAYMENTTERMS(new PAYMENTTERMS());
		}
		paymentTerms.forEach(pt -> {
			PAYMENTTERM paymentTerm = new PAYMENTTERM();
			paymentTerm.setValue(pt);
			try {
				Integer.valueOf(pt);
				paymentTerm.setType(PT_UNECE);
			} catch(NumberFormatException e) {
				paymentTerm.setType(PT_OTHER);
			}
			getPAYMENTTERMS().getPAYMENTTERM().add(paymentTerm);
		});
	}

}
