package com.klst.eorder.openTrans;

import java.sql.Timestamp;

import org.opentrans.xmlschema._2.AGREEMENT;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.untdid.DateTimeFormats;

public class SourcingInfoAgreement extends AGREEMENT {

	// Kennung für einen Rahmenvertrag des Einkäufers
	public static final String BUYER = "buyer";
	// Kennung für einen Rahmenvertrag des Lieferanten
	public static final String SUPPLIER = "supplier";

	public static final String QUOTATION = "quotation";
	public static final String CONTRACT = "contract";
	public static final String REQUISITION = "requisition";
	
	static SourcingInfoAgreement create(String type, String id, Timestamp timestamp) {
		return new SourcingInfoAgreement(type, id, timestamp);
	}

/*
HeaderTradeAgreement:
	// 524: BT-14 0..1 SALES ORDER REFERENCED DOCUMENT ==> SUPPLIER
	                   eine vom Verkäufer ausgegebene Kennung für einen referenzierten Verkaufsauftrag
	// 529: BT-13 0..1 Purchase order reference - this order ==> BUYER
	                   eine vom Käufer ausgegebene Kennung für eine referenzierte Bestellung
	// 534: 0..1 QUOTATION REFERENCE                        / Angebot
	// 539: BT-12 0..1 Contract reference
	// 544: 0..1 REQUISITION REFERENCE, not in CII          / Bedarfsermittlung
	// 549: BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS

    protected String agreementid; , required = true
    protected String agreementlineid;
    protected String agreementstartdate;
    protected String agreementenddate; , required = true
    protected TypePARTYID supplieridref;
    protected List<AGREEMENTDESCR> agreementdescr;
    protected MIMEINFO mimeinfo;
    protected String type;
    Vordefinierte Werte für das Attribut "type":
- Einkäufer "buyer" Kennung für einen Rahmenvertrag des Einkäufers.
- Lieferant "supplier" Kennung für einen Rahmenvertrag des Lieferanten.
- xxx Benutzerdefinierte Kennung. "\w{1,50}" bedeutet, die Kennung muss mindestens 1 Zeichen lang und darf höchstens 50 Zeichen lang sein.
    protected String _default;
 */
	// copy factory
	static SourcingInfoAgreement create(AGREEMENT object) {
		if(object instanceof AGREEMENT && object.getClass()!=AGREEMENT.class) {
			// object is instance of a subclass of AGREEMENT, but not AGREEMENT itself
			return (SourcingInfoAgreement)object;
		} else {
			return new SourcingInfoAgreement(object); 
		}
	}

	private SourcingInfoAgreement(AGREEMENT object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	private SourcingInfoAgreement(String type, String id, Timestamp timestamp) {
		super.setType(type);
		super.setAGREEMENTID(id);
		super.setAGREEMENTENDDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}

}
