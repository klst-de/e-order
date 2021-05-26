package com.klst.eorder.api;

import java.sql.Timestamp;
import java.util.List;

import com.klst.edoc.api.BusinessPartyFactory;
import com.klst.edoc.api.ContactInfoFactory;
import com.klst.edoc.api.IPeriodFactory;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddressFactory;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.MessageFunctionEnum;
import com.klst.edoc.untdid.PaymentMeansEnum;

public interface CoreOrder extends CoreOrderFactory, BG1_OrderNote, BG2_ProcessControl, BG4_Seller, BG7_Buyer,
	BG14_DeliveryOrPickup, BG20_DocumentLevelAllowences, BG21_DocumentLevelCharges,
	BG22_DocumentTotals, BG24_AdditionalSupportingDocs, BG25_OrderLine, 	
	ShipTo, ShipFrom,
	PostalAddressFactory, ContactInfoFactory, BusinessPartyFactory, IPeriodFactory {

	public static final boolean PROD = false;
	public static final boolean TEST = true;
	/**
	 * set Test Indicator
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	2
	 * 
	 * @param indicator - TEST or PROD
	 */
	public void setTestIndicator(boolean indicator);
	public boolean isTest();

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
	 * <br>Order-X-No: 	9
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
	 * <br>Order-X-No: 	14
	 * 
	 * @param timestamp
	 */
	public void setIssueDate(Timestamp timestamp);
	public Timestamp getIssueDateAsTimestamp();

	public static final boolean COPY = true;
	/**
	 * set Copy Indicator
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	16
	 * 
	 * @param indicator - COPY or !COPY
	 */
	public void setCopyIndicator(boolean indicator);
	public boolean isCopy();

	/**
	 * Document issue date
	 * 
	 * @param ymd - String in UNTDID 2379 Format "102" : CCYYMMDD = "yyyyMMdd" or "yyyy-MM-dd" 
	 * @see #setIssueDate(Timestamp)
	 */
	default void setIssueDate(String ymd) {
		if(ymd!=null) setIssueDate(DateTimeFormats.ymdToTs(ymd));
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
	 * <br>Order-X-No: 	11
	 * 
	 * @param code
	 */
//	void setTypeCode(DocumentNameCode code); // in factory
	public DocumentNameCode getDocumentCode();
	
	/**
	 * Language
	 * <p>
	 * A unique identifier for a language used in this exchanged document.	
	 * <p>
	 * Cardinality: 	0..n (optional)
	 * <br>Order-X-No: 	18
	 * 
	 * @param id
	 * to be chosen from the entries in UNTDID 3453 / ISO 639-1: de, en, es, ...
	 */
	public void addLanguage(String id);
	public List<String> getLanguage();
	
	/**
	 * Purpose Code
	 * <p>
	 * The purpose, expressed as UNTDID 1225 Message function code, of this exchanged document.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	19
	 * 
	 * @param code - potential values 
	 * 	7 : Duplicate 
	 * 	9 : Original 
	 * 35 : Retransmission
	 */
	public void setPurpose(MessageFunctionEnum code);
	public MessageFunctionEnum getPurposeCode();

	/**
	 * Requested Response Code
	 * <p>
	 * A code specifying a type of response requested for this exchanged document.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	20
	 * 
	 * @param code - "AC" to request an Order Response, defined in UNTDID 4343.
	 */
	public void setRequestedResponse(String code);
	public String getRequestedResponse();

	/**
	 * ORDER CURRENCY
	 * <p>
	 * The currency in which all order amounts are given.
	 * Only one currency shall be used in the order.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-5
	 * <br>Rule ID: 	BR-5
	 * <br>Order-X-No: 	790
	 * 
	 * @param isoCode
	 * <p>
	 * The lists of valid currencies are registered with the ISO 4217 Maintenance Agency “Codes for the representation of currencies and funds”.
	 */
	public void setDocumentCurrency(String isoCurrencyCode);
	public String getDocumentCurrency();
	
	/**
	 * REQUESTED TAX CURRENCY IN INVOICE
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-6
	 * <br>Rule ID:
	 * <br>Order-X-No: 	789
	 * 
	 * @param isoCode
	 * <p>
	 * The lists of valid currencies are registered with the ISO 4217 Maintenance Agency “Codes for the representation of currencies and funds”.
	 */
	public void setTaxCurrency(String isoCurrencyCode);
	public String getTaxCurrency();
	
	// BT-7 BT-7-0 : nicht in CIO

	/**
	 * Value added tax point date code
	 * <p>
	 * The code of the date when the VAT becomes accountable for the Seller and for the Buyer.
	 * The code shall distinguish between the following entries of UNTDID 2005:
	 * <br> - Invoice document issue date
	 * <br> - Delivery date, actual
	 * <br> - Paid to date
	 * <p>
	 * The Value added tax point date code is used if the Value added tax point date is not known when the invoice is issued. 
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-8
	 * <br>Rule ID: 	BR-CO-3
	 * <br>Order-X-No: 	886
	 * 
	 * @param code
	 */
	/* Folgende Codes aus der Codeliste UNTDID 2005 werden verwendet:
	 *   3 (Invoice document issue date time)
	 *  35 (Delivery date/time, actual)
	 * 432 (Paid to date)
	 * 
	 * In Deutschland ist das Liefer- und Leistungsdatum maßgebend (BT-72)
	 */
	public void setTaxPointDateCode(String code);
	public String getTaxPointDateCode();

	// BT-9 (Payment due date) : nicht in CIO
	
	/**
	 * Buyer reference - An identifier assigned by the Buyer used for internal routing purposes.
	 * <p>
	 * The identifier is defined by the Buyer (e.g. contact ID, department, office id, project code), 
	 * but provided by the Seller in the order.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-10
	 * <br>Rule ID: 	BR-DE-15
	 * <br>Order-X-No: 	344
	 * 
	 * @param reference
	 */
	public void setBuyerReference(String reference);
	public String getBuyerReferenceValue();

	/**
	 * The procuring project specified for this header trade agreement.
	 * <p>
	 * The identification of the project the order refers to
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-11, (Projektname BT-11-0)
	 * <br>Rule ID: 	
	 * <br>Order-X-No: 	634
	 * 
	 * @param id - Project reference
	 * @param name - Project name
	 */
	public void setProjectReference(String id, String name);
//	public void setProjectReference(String id);
	default void setProjectReference(Reference ref) {
		setProjectReference(ref.getName(), ref.getID()); // TODO in e-invoice andersrum?
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
	 * <br>Order-X-No: 	539
	 * 
	 * @param docRefId  - 540 Contract reference
	 * @param timestamp - 541 Contract Reference Date (EXTENDED)
	 */
	// Eine eindeutige Bezeichnung des Vertrages (z. B. Vertragsnummer).
	public void setContractReference(String docRefId, Timestamp timestamp);
	default void setContractReference(String docRefId) {
		setContractReference(docRefId, null);
	}
	public String getContractReference();
	public Timestamp getContractDate();

	/**
	 * Purchase order reference
	 * <p>
	 * An identifier of a referenced purchase order, issued by the Buyer. 
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-13
	 * <br>Rule ID: 	In an Order type message (BT-3 = 220), 
	 *     if the Buyer Order Referenced Document ID (BT-13) is present, 
	 *     it MUST be equal to Document ID (BT-1)
	 * <br>Order-X-No: 	529
	 * 
	 * @param docRefId  - 530 Document reference
	 * @param timestamp - 531 Buyer Order Reference Date (EXTENDED)
	 */
	// Eine vom Erwerber ausgegebene Kennung für eine referenzierte Bestellung.
	public void setPurchaseOrderReference(String docRefId, Timestamp timestamp);
	default void setPurchaseOrderReference(String docRefId) {
		setPurchaseOrderReference(docRefId, null);
	}
	public String getPurchaseOrderReference();
	public Timestamp getPurchaseOrderDate();

	/**
	 * Sales order reference
	 * <p>
	 * An identifier of a referenced sales order, issued by the Seller.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-14
	 * <br>Order-X-No: 	524
	 * 
	 * @param docRefId  - 525 Document reference
	 * @param timestamp - 526 Sales Order Reference Date (EXTENDED)
	 */
	public void setOrderReference(String docRefId, Timestamp timestamp);
	default void setOrderReference(String docRefId) {
		setOrderReference(docRefId, null);
	}
	public String getOrderReference();
	public Timestamp getOrderDate();

	/**
	 * QUOTATION REFERENCE
	 * <p>
	 * An Identifier of a Quotation, issued by the Seller.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>Order-X-No: 	534
	 * 
	 * @param docRefId  - 535 Quotation Reference
	 * @param timestamp - 536 Quotation Reference Date (EXTENDED)
	 */
	// 534: 0..1 QUOTATION REFERENCE, nicht in CII: Angebot ref
	public void setQuotationReference(String docRefId, Timestamp timestamp);
	default void setQuotationReference(String docRefId) {
		setQuotationReference(docRefId, null);
	}
	public String getQuotationReference();
	public Timestamp getQuotationDate();

	/**
	 * REQUISITION REFERENCE
	 * <p>
	 * The identification of a Requisition Document, issued by the Buyer or the Buyer Requisitioner.
	 * Originator Document. To be able to give a reference to the internal requisition on the buyer site on which the order is based.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>Order-X-No: 	544 (not in CII)
	 * 
	 * @param docRefId  - 545 Requisition Reference
	 * @param timestamp - 546 Requisition Reference Date (EXTENDED)
	 */
	public void setRequisitionReference(String docRefId, Timestamp timestamp);
	default void setRequisitionReference(String docRefId) {
		setRequisitionReference(docRefId, null);
	}
	public String getRequisitionReference();
	public Timestamp getRequisitionDate();

	// BT-15 (Receiving advice / Receipt document reference) : nicht in CIO
	// BT-16 (Despatch advice reference) : nicht in CIO
	
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
	 * <br>Order-X-No: 	561
	 * 
	 * @param docRefId Document reference (Order-X-No: 562)
	 */
	public void setTenderOrLotReference(String docRefId);
	public String getTenderOrLotReference();
	
	/**
	 * BT-18 (OBJECT IDENTIFIER FOR INVOICE)
	 * <p>
	 * A group of business terms providing information about additional 
	 * supporting documents substantiating the claims made in the order.
	 * The additional supporting documents can be used for both 
	 * referencing a document number which is expected to be known by the receiver, 
	 * an external document (referenced by a URL) 
	 * or as an embedded document (such as a time report in pdf). 
	 * The option to link to an external document will be needed, 
	 * for example in the case of large attachments and/or when sensitive information, 
	 * e.g. person-related services, has to be separated from the order itself.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-18
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	564
	 * 
	 * @param name   - 565 the identifier name BT-18
	 * @param scheme - 567 identifier BT-18-1
	 * The identification scheme identifier of the Invoiced object identifier.
	 * If it may be not clear for the receiver what scheme is used for the identifier, 
	 * a conditional scheme identifier should be used that shall be chosen from the UNTDID 1153 code list entries.
	 */
	public void setInvoicedObject(String name, String schemeID);
	default void setInvoicedObject(String name) {
		setInvoicedObject(name, null);
	}
	default void setInvoicedObjectIdentifier(Identifier id) {
		if(id!=null) setInvoicedObject(id.getContent(), id.getSchemeIdentifier());
	}
	public String getInvoicedObject();
	public Identifier getInvoicedObjectIdentifier();

	/**
	 * Buyer accounting reference
	 * <p>
	 * A textual value that specifies where to book the relevant data into the Buyer's financial accounts.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-19
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	942
	 * 
	 * @param textReference
	 */
	public void setBuyerAccountingReference(Reference textReference);
	public Reference getBuyerAccountingReference();

	// 345: BG-4 1..1 SELLER @see BG4_Seller
	// 390: BG-7 1..1 BUYER @see BG7_Buyer
	
	// 435: HEADER BUYER REQUISITIONER (ORIGINATOR) TODO
	// 476: PRODUCT END USER TODO
	// 568: BUYER AGENT TODO

	// 643: BG-13 0..1 ShipToParty @see ShipTo
	// 684: ULTIMATE SHIP TO PARTY TODO
	// 725: ShipFromParty @see ShipFrom

	/* 766: BG-14 0..1 DELIVERY PERIOD with BT-73 start date and BT-74 end date
	 *
	 * The Requested Date or Period on which Delivery is requested for Delivery, 
	 * mutually exclusive with 777 Pick up = Despatch (not in CII)
	 * 
	 * contains
	 * Requested Delivery Date
	 * Requested Delivery Period, at least 1 StartDate BT-73 or 1 EndDate BT-74
	 *
	 */
	
	/* 874: BG-16 PAYMENT MEANS - A group of business terms providing information about the payment.
example:
               <ram:SpecifiedTradeSettlementPaymentMeans>
                    <ram:TypeCode>30</ram:TypeCode>
                    <ram:Information>Credit Transfer</ram:Information>
               </ram:SpecifiedTradeSettlementPaymentMeans>

	 */
	/**
	 * Payment means type code
	 * <p>
	 * The means, expressed as code, for how a payment is expected to be or has been settled.
	 * <p>
	 * Entries from the UNTDID 4461 code list shall be used. Distinction should be made between SEPA and non-SEPA payments, 
	 * and between credit payments, direct debits, card payments and other instruments.
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BT-81
	 * <br>Rule ID: 	BR-49
	 * <br>Order-X-No: 	875
	 * 
	 * @return Code
	 */
	public PaymentMeansEnum getPaymentMeansEnum();
	public void setPaymentMeansEnum(PaymentMeansEnum code);
	
	/**
	 * Payment means text
	 * <p>
	 * The means, expressed as text, for how a payment is expected to be or has been settled.
	 * Such as cash, credit transfer, direct debit, credit card, etc.
	 * <p>
	 * Cardinality: 	0..1
	 * <br>EN16931-ID: 	BT-82
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	876
	 * 
	 * @return Text
 	 */
	public String getPaymentMeansText();
	public void setPaymentMeansText(String text);

	/**
	 * PAYMENT TERMS
	 * <p>
	 * A textual description of the payment terms that apply to the amount due for payment 
	 * (Including description of possible penalties).
	 * This element may contain multiple lines and multiple terms.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-20
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	925
	 * 
	 * @param description
	 */
	public void addPaymentTerm(String description);
	public void setPaymentTerms(List<String> paymentTerms);
	public List<String> getPaymentTerms();
	
	// 517: 0..1 DELIVERY TERMS, nicht in CII : ram:ApplicableTradeDeliveryTerms
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

	// 614: 0..1 BLANKET ORDER REFERENCE, nicht in CII: Rahmenauftrag ref: ram:BlanketOrderReferencedDocument
/*
Blanket Order number ID
The identification of a Blanket Order, issued by the Buyer or the Buyer Requisitioner.
 */
	public void setBlanketOrderReference(String id);
	public String getBlanketOrderReference();

	// 619: 0..1 PREVIOUS ORDER REFERENCE, nicht in CII: ram:PreviousOrderReferencedDocument (ohne Beispiel)
/*
Previous Order Reference in case the Buyer decides to raise a new ORDER to replace a prvious one

Previous Order Reference ID
The identification of a the Previous Order Document, issued by the Buyer or the Buyer Requisitioner.
In case the Buyer wants to raise a new PURCHASE ORDER with a new Puchase Order Reference ID, 
it can refer to the previous one with this ID
 */
	public void setPreviousOrderReference(String id);
	public String getPreviousOrderReference();

	// 624: 0..1 PREVIOUS ORDER CHANGE REFERENCED DOCUMENT, not in CII
/*
An Order (Document Type Code BT-3 = 220) MUST NOT contain a Previous Order Change Referenced Document

Previous Order CHANGE Referenced Document ID
The identification of a the Previous Order Change Document, issued by the Buyer or the Buyer Requisitioner.
 */
	public void setPreviousOrderChangeReference(String id);
	public String getPreviousOrderChangeReference();

	// 629: 0..1 PREVIOUS ORDER RESPONSE REFERENCED DOCUMENT, not in CII
/*
An Order (Document Type Code BT-3 = 220) MUST NOT contain a Previous Order Response Referenced Document

Previous Order RESPONSE Referenced Document ID
The identification of a the Previous Order Response Document, issued by the Seller.
 */
	public void setPreviousOrderResponseReference(String id);
	public String getPreviousOrderResponseReference();
	
}
