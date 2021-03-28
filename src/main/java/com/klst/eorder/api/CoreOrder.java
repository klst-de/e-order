package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.BusinessPartyFactory;
import com.klst.edoc.api.ContactInfoFactory;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IPeriodFactory;
import com.klst.edoc.api.PostalAddressFactory;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;

public interface CoreOrder extends CoreOrderFactory, BG1_OrderNote, BG2_ProcessControl, BG4_Seller, BG7_Buyer,
	BG14_OrderPeriod, BG20_DocumentLevelAllowences, BG21_DocumentLevelCharges,
	BG22_DocumentTotals, BG24_AdditionalSupportingDocs, BG25_OrderLine, 	
	ShipTo, ShipFrom,
	PostalAddressFactory, ContactInfoFactory, BusinessPartyFactory, IPeriodFactory {

	/**
	 * Document number   - A unique identification of the Document.
	 * <p>
	 * Similar to Invoice number.
	 * The sequential number required in Article 226(2) of the directive 2006/112/EC [2],
	 * to uniquely identify the Invoice within the business context, time-frame, operating systems and records of the Seller. 
	 * It may be based on one or more series of numbers, which may include alphanumeric characters. 
	 * No identification scheme is to be used.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-1
	 * <br>Rule ID: 	BR-2
	 * <br>Request ID: 	R56
	 * 
	 * @param id Identifier
	 */
	public void setId(String id);
	public String getId();

	/**
	 * Document issue date
	 * <p>
	 * The date when the Document was issued
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-2
	 * <br>Rule ID: 	BR-3
	 * <br>Request ID: 	R56
	 * 
	 * @param timestamp
	 */
	public void setIssueDate(Timestamp timestamp);
	public Timestamp getIssueDateAsTimestamp();
	
	/**
	 * Document issue date
	 * 
	 * @param ymd - String in UNTDID 2379 Format "102" : CCYYMMDD = "yyyyMMdd" or "yyyy-MM-dd" 
	 * @see #setIssueDate(Timestamp)
	 */
	default void setIssueDate(String ymd) {
		if(ymd!=null) setIssueDate(DateTimeFormats.ymdToTs(ymd));
	}

	/*
	 * The Requested Date or Period on which Delivery is requested
	 * for Delivery, mutually exclusive with Pick up = Despatch
	 * (nicht in CII)
	 * 
	 * Requested Delivery Date
	 * Requested Delivery Period, at least 1 StartDate or 1 EndDate
	 *
	 */
	public void setDeliveryDate(Timestamp timestamp);
	public Timestamp getDeliveryDateAsTimestamp();
	default void setDeliveryDate(String ymd) {
		if(ymd!=null) setDeliveryDate(DateTimeFormats.ymdToTs(ymd));
	}
	public void setDeliveryPeriod(IPeriod period);
	public IPeriod getDeliveryPeriod();
	default void setDeliveryPeriod(Timestamp start, Timestamp end) {
		setDeliveryPeriod(createPeriod(start, end));
	}
	default void setDeliveryPeriod(String ymdStart, String ymdEnd) {
		setDeliveryPeriod(createPeriod(ymdStart, ymdEnd));
	}
	
	/**
	 * The Document TypeCode (BT-3) MUST be:
- 220 for an ORDER
- 230 for an ORDER CHANGE
- 231 for an ORDER RESPONSE
	 * 
	 * 
	 * Invoice type code - A code specifying the functional type of the Invoice.
	 * <p>
	 * Commercial invoices and credit notes are defined according the entries in UNTDID 1001.
	 * Other entries of UNTDID 1001 with specific invoices or credit notes may be used if applicable.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-3
	 * <br>Rule ID: 	BR-4, BR-DE-17 :  
	 * Mit dem Element "Invoice type code" sollen ausschließlich folgende Codes aus der Codeliste UNTDID 1001 übermittelt werden: 
	 *     326 (Partial invoice), 
	 *     380 (Commercial invoice), 
	 *     384 (Corrected invoice), 
	 *     389 (Self-billed invoice) und 
	 *     381 (Credit note),
	 *     875 (Partial construction invoice), 
	 *     876 (Partial final construction invoice), 
	 *     877 (Final construction invoice)
	 * <br>Request ID: 	R44
	 * 
	 * @param code
	 */
//	void setTypeCode(DocumentNameCode code); // in factory
	public DocumentNameCode getDocumentCode();

	/**
	 * Invoice currency code
	 * <p>
	 * The currency in which all Invoice amounts are given, 
	 * except for the Total VAT amount in accounting currency.
	 * Only one currency shall be used in the Invoice, 
	 * except for the Invoice total VAT amount in accounting currency (BT-111) 
	 * in accordance with article 230 of Directive 2006/112/EC on VAT.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-5
	 * <br>Rule ID: 	BR-5
	 * <br>Request ID: 	R54, R47
	 * 
	 * @param isoCode
	 * <p>
	 * The lists of valid currencies are registered with the ISO 4217 Maintenance Agency “Codes for the representation of currencies and funds”.
	 */
	public void setDocumentCurrency(String isoCurrencyCode);
	public String getDocumentCurrency();
	
	// TODO BT-6 VAT accounting currency code
	//      BT-7 BT-7-0
	//      BT-8
	//      BT-9
	// BT-9 (Payment due date) & BT-20
	
	/**
	 * The identifier is defined by the Buyer (e.g. contact ID, department, office id, project code), 
	 * but provided by the Seller in the order.
	 * 
	 * @param reference
	 */
	// BT-10
	public void setBuyerReference(String reference);
	public String getBuyerReferenceValue();
	
	// BT-11, (Projektname BT-11-0)
	/**
	 * The procuring project specified for this header trade agreement.
	 * 
	 * @param id - Project reference
	 * @param name - Project name
	 */
	public void setProjectReference(String id, String name);
//	public void setProjectReference(String id);
	default void setProjectReference(Reference ref) {
		setProjectReference(ref.getName(), ref.getID()); // TODO symetrisch zu e-invoice falschrum?
	}	
	public Reference getProjectReference();

	/**
	 * Contract reference
	 * <p>
	 * The identification of a contract. 
	 * The contract identifier should be unique in the context of the specific trading relationship and for a defined time period.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-12
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R7
	 * 
	 * @param Document reference
	 */
	// Eine eindeutige Bezeichnung des Vertrages (z. B. Vertragsnummer).
	public void setContractReference(String id);
	public String getContractReference();

	/**
	 * Purchase order reference
	 * <p>
	 * An identifier of a referenced purchase order, issued by the Buyer. 
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-13
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R5, R56
	 * 
	 * @param Document reference
	 */
	// Eine vom Erwerber ausgegebene Kennung für eine referenzierte Bestellung.
	public void setPurchaseOrderReference(String id);
	public String getPurchaseOrderReference();

	/**
	 * Tender or lot reference
	 * <p>
	 * A group of business terms providing information about additional supporting documents substantiating 
	 * the claims made in the order.
	 * The identification of the call for tender or lot the order relates to.
	 * In some countries a reference to the call for tender that has led to the contract shall be provided.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-17
	 * <br>Rule ID: 	ON profiles BASIC OR COMFORT, 
	 *                  the Order MUST NOT HAVE more than 1 Tender or Lot Reference on Header Level
	 * <br>Request ID: 	R7, R4
	 * 
	 * @param Document reference
	 */
	public void setTenderOrLotReference(String docRefId);
	public String getTenderOrLotReference();

	/**
	 * Buyer accounting reference
	 * <p>
	 * A textual value that specifies where to book the relevant data into the Buyer's financial accounts.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-19
	 * <br>Rule ID: 	 
	 * <br>Request ID: 	R2, R4
	 * 
	 * @param textReference
	 */
	// BT-19 + 0..1 Buyer accounting reference
	public void setBuyerAccountingReference(Reference textReference);
	public Reference getBuyerAccountingReference();

	// BG-4 + 1..1 SELLER @see BG4_Seller
	// BG-7 + 1..1 BUYER @see BG7_Buyer
	// ShipToParty @see ShipTo
	// ShipFromParty @see ShipFrom

	// nicht in CII: 0..1 DELIVERY TERMS, Doku Zeile 505 : ram:ApplicableTradeDeliveryTerms
	// Added in CIO
/*
Added in CIO
Delivery type code (used for incoterms) and the relevant location. They might be needed. 
The Incoterms rules provide specific guidance to individuals participating in the import and export of global trade 
on a daily basis.

DELIVERY CODE:
To be chosen from the entries in UNTDID 4053 + INCOTERMS List 
1 : Delivery arranged by the supplier (Indicates that the supplier will arrange delivery of the goods).
2 : Delivery arranged by logistic service provider (Code indicating that the logistic service provider has arranged the delivery of goods).
CFR : Cost and Freight (insert named port of destination)
CIF : Cost, Insurance and Freight (insert named port of destination)
CIP : Carriage and Insurance Paid to (insert named place of destination)   
CPT : Carriage Paid To (insert named place of destination)
DAP : Delivered At Place (insert named place of destination)
DAT : Delivered At Terminal (insert named terminal at port or place of destination)
DDP : Delivered Duty Paid (insert named place of destination)
EXW : Ex Works (insert named place of delivery)
FAS : Free Alongside Ship (insert named port of shipment)
FCA : Free Carrier (insert named place of delivery)
FOB : Free On Board (insert named port of shipment)

DELIVERY MODE:
To be chosen from the entries in UNTDID 4055
4 Collected by Customer
7 Delivered by the supplier

example:
			<ram:ApplicableTradeDeliveryTerms>                      <!-- nicht in CII -->
				<ram:DeliveryTypeCode>FCA</ram:DeliveryTypeCode>
				<ram:FunctionCode>7</ram:FunctionCode>
			</ram:ApplicableTradeDeliveryTerms>

 */
	public void setDeliveryTerms(String deliveryType, String functionCode);
	public String getDeliveryType();
	public String getDeliveryFunctionCode();

	// nicht in CII: 0..1 QUOTATION REFERENCE, Doku Zeile 522 Angebot ref: ram:QuotationReferencedDocument
/*
Quotation Reference ID
An Identifier of a Quotation, issued by the Seller.
 */
	public void setQuotationReference(String id);
	public String getQuotationReference();

	// nicht in CII: 0..1 BLANKET ORDER REFERENCE, Doku Zeile 561 Rahmenauftrag ref: ram:BlanketOrderReferencedDocument
/*
Blanket Order number ID
The identification of a Blanket Order, issued by the Buyer or the Buyer Requisitioner.
 */
	public void setBlanketOrderReference(String id);
	public String getBlanketOrderReference();

	// nicht in CII: 0..1 PREVIOUS ORDER REFERENCE, Doku Zeile 566 : ram:PreviousOrderReferencedDocument (ohne Beispiel)
/*
Previous Order Reference in case the Buyer decides to raise a new ORDER to replace a prvious one

Previous Order Reference ID
The identification of a the Previous Order Document, issued by the Buyer or the Buyer Requisitioner.
In case the Buyer wants to raise a new PURCHASE ORDER with a new Puchase Order Reference ID, 
it can refer to the previous one with this ID
 */
	public void setPreviousOrderReference(String id);
	public String getPreviousOrderReference();

	// nicht in CII: 0..1 PREVIOUS ORDER CHANGE REFERENCED DOCUMENT, Doku Zeile 571
/*
An Order (Document Type Code BT-3 = 220) MUST NOT contain a Previous Order Change Referenced Document

Previous Order CHANGE Referenced Document ID
The identification of a the Previous Order Change Document, issued by the Buyer or the Buyer Requisitioner.
 */
	public void setPreviousOrderChangeReference(String id);
	public String getPreviousOrderChangeReference();

	// nicht in CII: 0..1 PREVIOUS ORDER RESPONSE REFERENCED DOCUMENT, Doku Zeile 576
/*
An Order (Document Type Code BT-3 = 220) MUST NOT contain a Previous Order Response Referenced Document

Previous Order RESPONSE Referenced Document ID
The identification of a the Previous Order Response Document, issued by the Seller.
 */
	public void setPreviousOrderResponseReference(String id);
	public String getPreviousOrderResponseReference();
	
}
