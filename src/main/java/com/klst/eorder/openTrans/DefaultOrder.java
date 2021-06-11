package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.bmecat.bmecat._2005.DtLANG;
import org.bmecat.bmecat._2005.LANGUAGE;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.MessageFunctionEnum;
import com.klst.edoc.untdid.PaymentMeansEnum;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG22_DocumentTotals;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;

/* Vergleich fields in
ORDERINFO						ORDERRESPONSEINFO				ORDERCHANGEINFO
"orderid",			     		"orderid",			          	"orderid",
       			     	 		"orderresponsedate",	      	"orderchangedate",
"orderdate",		     		"orderdate",		          	"orderdate",
            		     		"altcustomerorderid",		  	"altcustomerorderid",
            		     		"supplierorderid",			  	"supplierorderid",
            		     		"orderchangesequenceid",		"orderchangesequenceid",
"deliverydate",		     		"deliverydate",			  		"deliverydate",
"language",			     		"language",			  			"language",
"mimeroot",			     		"mimeroot",			  			"mimeroot",
"parties",			     		"parties",				  		"parties",
"customerorderreference",	     
"orderpartiesreference",	    "orderpartiesreference",		"orderpartiesreference",
"docexchangepartiesreference",  "docexchangepartiesreference",	"docexchangepartiesreference",
"currency",			     		"currency",			  			"currency",
"payment",			     					  					"payment",
"termsandconditions",	     					  				"termsandconditions",
"partialshipmentallowed",	     					  			"partialshipmentallowed",
"transport",		     
"internationalrestrictions",     
"mimeinfo",			     		"mimeinfo",   		          	"mimeinfo",
"remarks",			     		"remarks",				  		"remarks",
"headerudx"			     		"headerudx"			  			"headerudx"

 */
public interface DefaultOrder extends CoreOrder {

/*	// aus https://openbook.rheinwerk-verlag.de/java8/01_002.html#u1.2.7
	// Consumer, der Daten annimmt und dann verbraucht (konsumiert).
	// Implementiere einen Consumer-Wrapper, 
	// der die Ausführungszeit eines anderen Konsumenten loggt:
	public static <T> Consumer<T> measuringConsumer(Consumer<T> block) {
		return t -> {
			long start = System.nanoTime();
			block.accept(t);  // <== aus interface Consumer<T>
			long duration = System.nanoTime() - start;
//	      Logger.getAnonymousLogger().info( "Ausführungszeit (ns): " + duration );
		};
	}
	// Folgender Aufruf zeigt die Nutzung:
	// wrap ist auch Consumer
	static void nutzung() {
		Consumer<Void> wrap = measuringConsumer( Void -> {
			// der Block, der gemessen wird
			System.out.println( "Test" ); 
		});
		wrap.accept( null );
	}
	
	@Override
	default CoreOrder createOrder(String profile, String processType, DocumentNameCode code) {
		// TODO Auto-generated method stub
		return null;
	}
 */
	
	@Override
	default List<OrderNote> getOrderNotes() {
		return null;
	}
	@Override
	default void addNote(OrderNote note) {
	}
	@Override
	default OrderNote createNote(String subjectCode, String content) {
		return Remarks.create(subjectCode, content);
	}

	@Override
	default String getProcessType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default String getCustomization() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setSeller(String name, PostalAddress address, ContactInfo contact, 
		String companyId, String companyLegalForm) {
		BusinessParty party = Party.create(name, null, address, contact);
		party.setCompanyId(companyId);
		party.setCompanyLegalForm(companyLegalForm);
		setSeller(party);
	}
	@Override
	default void setSeller(BusinessParty party) {
	}
	@Override
	default BusinessParty getSeller() {
		return null;
	}

	@Override
	default void setBuyer(String name, PostalAddress address, ContactInfo contact) {
		setBuyer(createParty(name, null, address, contact));
	}
	@Override
	default void setBuyer(BusinessParty party) {
	}
	@Override
	default BusinessParty getBuyer() {
		return null;
	}

	@Override
	default void setDeliveryDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Timestamp getDeliveryDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPickupDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Timestamp getPickupDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setDeliveryPeriod(IPeriod period) {
	}
	@Override
	default IPeriod getDeliveryPeriod() {
		return null;
	}
	@Override
	default IPeriod createPeriod(Timestamp start, Timestamp end) {
		return DeliveryDate.create(start, end);
	}

	@Override
	default void setPickupPeriod(IPeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IPeriod getPickupPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<AllowancesAndCharges> getAllowancesAndCharges() {
		// TODO Auto-generated method stub
		return null;
	}

	// 888: BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
	@Override
	default AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return AllowOrCharge.create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	// 903: BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
	@Override
	default AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return AllowOrCharge.create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}

	@Override
	default BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IAmount getLineNetTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IAmount getTotalTaxExclusive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IAmount getTotalTaxInclusive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setAllowancesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IAmount getAllowancesTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setChargesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IAmount getChargesTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setTaxTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IAmount getTaxTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addSupportigDocument(SupportingDocument supportigDocument) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<SupportingDocument> getAdditionalSupportingDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, byte[] content, String mimeCode, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default List<OrderLine> getLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount,
			String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addLine(OrderLine line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void setShipTo(String name, PostalAddress address, ContactInfo contact) {
		setShipTo(createParty(name, null, address, contact));
	}
	@Override
	default void setShipTo(BusinessParty party) {
	}
	@Override
	default BusinessParty getShipTo() {
		return null;
	}

	@Override
	default void setShipFrom(String name, PostalAddress address, ContactInfo contact) {
		setShipFrom(createParty(name, null, address, contact));
	}
	@Override
	default void setShipFrom(BusinessParty party) {
	}
	@Override
	default BusinessParty getShipFrom() {
		return null;
	}

	@Override
	default void setBillTo(String name, PostalAddress address, ContactInfo contact) {
		setBillTo(createParty(name, null, address, contact));
	}
	@Override
	default void setBillTo(BusinessParty party) {
	}
	@Override
	default BusinessParty getBillTo() {
		return null;
	}

	@Override
	default void setInvoicer(String name, PostalAddress address, ContactInfo contact) {
		setInvoicer(createParty(name, null, address, contact));
	}
	@Override
	default void setInvoicer(BusinessParty party) {
	}
	@Override
	default BusinessParty getInvoicer() {
		return null;
	}

	@Override
	default PostalAddress createAddress(String countryCode, String postalCode, String city) {
		return Party.create().createAddress(countryCode, postalCode, city);
	}

	@Override
	default ContactInfo createContactInfo(String contactName, String contactTel, String contactMail) {
		return Party.create().createContactInfo(contactName, contactTel, contactMail);
	}

	@Override
	default BusinessParty createParty(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		return Party.create(name, tradingName, address, contact);
	}

	@Override
	default void setTestIndicator(boolean indicator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default boolean isTest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	default void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setIssueDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Timestamp getIssueDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setCopyIndicator(boolean indicator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default boolean isCopy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	default DocumentNameCode getDocumentCode() {
		// TODO Auto-generated method stub
		return null;
	}

	// 18: In OT ISO 639-2 alpha-3 code, in CIO/order-x ISO 639-1: de, en, es, ...
	static final String TRUE = Boolean.TRUE.toString().toUpperCase();
	default LANGUAGE createDefaultLanguage(String id) {
		LANGUAGE language = new LANGUAGE();
		// TODO bijektives mapping ISO 639-1 nach ISO 639-2 alpha-3, siehe ISO639.ods
		language.setValue(DtLANG.fromValue(id));
		language.setDefault(TRUE); // auch "true" wäre OK
		return language;
	}
	@Override
	default void addLanguage(String id) {
	}
	@Override
	default List<String> getLanguage() {
		return null;
	}

	@Override
	default void setPurpose(MessageFunctionEnum code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default MessageFunctionEnum getPurposeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setRequestedResponse(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getRequestedResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setDocumentCurrency(String isoCurrencyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getDocumentCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setTaxCurrency(String isoCurrencyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getTaxCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setTaxPointDateCode(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getTaxPointDateCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setBuyerReference(String reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getBuyerReferenceValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setProjectReference(String id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Reference getProjectReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setContractReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getContractReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Timestamp getContractDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPurchaseOrderReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getPurchaseOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Timestamp getPurchaseOrderDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setOrderReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Timestamp getOrderDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setQuotationReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getQuotationReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Timestamp getQuotationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setRequisitionReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getRequisitionReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Timestamp getRequisitionDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setTenderOrLotReference(String docRefId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getTenderOrLotReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setInvoicedObject(String name, String schemeID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getInvoicedObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Identifier getInvoicedObjectIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setBuyerAccountingReference(Reference textReference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Reference getBuyerAccountingReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default PaymentMeansEnum getPaymentMeansEnum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPaymentMeansEnum(PaymentMeansEnum code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getPaymentMeansText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPaymentMeansText(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void addPaymentTerm(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void setPaymentTerms(List<String> paymentTerms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<String> getPaymentTerms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setDeliveryTerms(String deliveryType, String functionCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getDeliveryType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default String getDeliveryFunctionCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setBlanketOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getBlanketOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPreviousOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getPreviousOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPreviousOrderChangeReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getPreviousOrderChangeReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPreviousOrderResponseReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getPreviousOrderResponseReference() {
		// TODO Auto-generated method stub
		return null;
	}

}
