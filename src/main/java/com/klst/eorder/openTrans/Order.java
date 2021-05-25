package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.ORDER;
import org.opentrans.xmlschema._2.ORDERITEM;

import com.klst.ebXml.reflection.SCopyCtor;
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
import com.klst.eorder.impl.UnitPriceAmount;

/* alle drei elemente sind required:
    "orderheader",
    "orderitemlist",
    "ordersummary"
 */
public class Order extends ORDER implements CoreOrder {

	private static final Logger LOG = Logger.getLogger(Order.class.getName());

	OrderHeader orderHeader;
	// elemente:
//    protected CONTROLINFO controlinfo; hat nur 3 String elemente: stopautomaticprocessing, generatorinfo, generationdate
//    protected SOURCINGINFO sourcinginfo; Informationen über Beschaffungsaktivitäten zusammengefasst, die diesem Auftrag vorausgegangen sind.
//    protected ORDERINFO orderinfo; mit parties usw
	OrderInfo orderInfo;
	
	public Order(ORDER doc) {
		super();
		if(doc!=null) {
			SCopyCtor.getInstance().invokeCopy(this, doc);
		}

		LOG.info("Type:"+super.getType());
		LOG.info("Version:"+super.getVersion());
		orderHeader = OrderHeader.create(super.getORDERHEADER(), this);
// orderheader:
//	    "controlinfo"
		//orderHeader.createtControlInfo(); // hat nur 3 elemente
//	    "sourcinginfo" SOURCING_INFO (Beschaffungsinformationen) : optional
//		Im Element SOURCING_INFO werden Informationen über Beschaffungsaktivitäten zusammengefasst, 
//		die diesem Auftrag vorausgegangen sind:
//	protected String quotationid;   ===> // 534: 0..1 QUOTATION REFERENCE
//	protected List<AGREEMENT> agreement;  ?? ==> wie applicableHeaderTradeAgreement ??? (Referenz auf Rahmenvertrag)
//	    protected String agreementid;
//	    protected String agreementlineid;
//	    protected String agreementstartdate;
//	    protected String agreementenddate;
//	    protected TypePARTYID supplieridref;
//	    protected List<AGREEMENTDESCR> agreementdescr;
//	    protected MIMEINFO mimeinfo;
//	    protected String type;
//	    protected String _default;

//	protected CATALOGREFERENCE catalogreference;

		
//	    "orderinfo" : required
		
		orderInfo = orderHeader.createOrderInfo();
//		applicableHeaderTradeDelivery = supplyChainTradeTransaction.createtHeaderTradeDelivery();
//		applicableHeaderTradeSettlement = supplyChainTradeTransaction.createtHeaderTradeSettlement();
//		if(super.getSupplyChainTradeTransaction()==null) {
//			super.setSupplyChainTradeTransaction(supplyChainTradeTransaction);
//			supplyChainTradeTransaction.setApplicableHeaderTradeAgreement(applicableHeaderTradeAgreement);
//			supplyChainTradeTransaction.setApplicableHeaderTradeDelivery(applicableHeaderTradeDelivery);
//			supplyChainTradeTransaction.setApplicableHeaderTradeSettlement(applicableHeaderTradeSettlement);
//		}

	}

	@Override
	public CoreOrder createOrder(String profile, String processType, DocumentNameCode code) {
		// TODO Auto-generated method stub
		return null;
	}

	// BG-1
	@Override
	public List<OrderNote> getOrderNotes() {
		// in ORDER_INFO : <REMARKS type="customType">a</REMARKS>
		// REMARKS extends TypeMLSTRING64000 extends DtMLSTRING mit value + lang
		// REMARKS. String type <== subjectCode
		// dh. OrderNote durch Remarks implementieren
		// delegieren:
		return Remarks.getNotes(orderInfo.getREMARKS());
	}
	@Override
	public OrderNote createNote(String subjectCode, String content) {
		return Remarks.create(subjectCode, content);
	}
	@Override
	public void addNote(OrderNote note) {
		// delegieren:
		orderInfo.getREMARKS().add((Remarks)note);
	}

	@Override
	public String getProcessType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomization() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId,
			String companyLegalForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSeller(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getSeller() {
		return orderInfo.getSeller();
	}

	@Override
	public void setBuyer(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBuyer(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getBuyer() {
		return orderInfo.getBuyer();
	}

	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		// TODO Auto-generated method stub
		// in OT werden ALLOW_OR_CHARGES_FIX (Festgelegte Zu- oder Abschläge)
		// benutzt in PRODUCT_PRICE_FIX auf Positionsebene
		// und TIME_FOR_PAYMENT auf Belegebene
	}

	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAmount getLineNetTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAmount getTotalTaxExclusive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAmount getTotalTaxInclusive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAllowancesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAmount getAllowancesTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChargesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAmount getChargesTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAmount getTaxTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	// BG-25 1..n ORDER LINE
	@Override
	public List<OrderLine> getLines() {
		List<ORDERITEM> lines = super.getORDERITEMLIST().getORDERITEM();
		List<OrderLine> resultLines = new ArrayList<OrderLine>(lines.size());
		lines.forEach(line -> {
			resultLines.add(OrderItem.create(line, this));
		});
		return resultLines;
	}
	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount,
			String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		return OrderItem.create(this, id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
	}

	@Override
	public void addLine(OrderLine line) {
//		this.supplyChainTradeTransaction.getIncludedSupplyChainTradeLineItem().add((SupplyChainTradeLineItem)line);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShipToParty(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShipToParty(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getShipToParty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShipFromParty(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShipFromParty(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getShipFromParty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactInfo createContactInfo(String contactName, String contactTel, String contactMail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessParty createParty(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		return null;
	}

	// Order number BT-1 Identifier (mandatory) - A unique identification of the Order.
	@Override
	public void setId(String id) {
		orderInfo.setId(id);
	}
	@Override
	public String getId() {
		return orderInfo.getId();
	}

	/* Document issue date, BT-2  Date (mandatory) 
	 * Das Datum, an dem der Beleg ausgestellt wurde.
	 */
	@Override
	public void setIssueDate(Timestamp timestamp) {
		// in <ORDER_INFO> : <ORDER_DATE>2009-05-13T06:20:00+01:00</ORDER_DATE>
		orderInfo.setIssueDate(timestamp);
	}
	@Override
	public Timestamp getIssueDateAsTimestamp() {
		return orderInfo.getIssueDateAsTimestamp();
	}

	// BG-14 0..1 DELIVERY DATE
	@Override
	public void setDeliveryDate(Timestamp timestamp) {
		orderInfo.setDeliveryDate(timestamp);
	}
	@Override
	public Timestamp getDeliveryDateAsTimestamp() {
		return orderInfo.getDeliveryDateAsTimestamp();
	}
	@Override // factory
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return DeliveryDate.create(start, end);
	}	
	// BG-14 0..1 DELIVERY PERIOD
	@Override
	public void setDeliveryPeriod(IPeriod period) {
		orderInfo.setDeliveryPeriod((DeliveryDate)period);
	}
	@Override
	public IPeriod getDeliveryPeriod() {
		return orderInfo.getDeliveryPeriod();
	}

	@Override
	public DocumentNameCode getDocumentCode() {
		// TODO Auto-generated method stub
		return null;
	}

	// BT-5 + 1..1 Invoice currency code
	@Override
	public void setDocumentCurrency(String isoCurrencyCode) {
		orderInfo.setDocumentCurrency(isoCurrencyCode);
	}
	@Override
	public String getDocumentCurrency() {
		return orderInfo.getDocumentCurrency();
	}

	@Override
	public void setBuyerReference(String reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBuyerReferenceValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProjectReference(String id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reference getProjectReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTenderOrLotReference(String docRefId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTenderOrLotReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBuyerAccountingReference(Reference textReference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reference getBuyerAccountingReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeliveryTerms(String deliveryType, String functionCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDeliveryType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeliveryFunctionCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBlanketOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBlanketOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreviousOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPreviousOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreviousOrderChangeReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPreviousOrderChangeReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreviousOrderResponseReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPreviousOrderResponseReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPickupDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getPickupDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPickupPeriod(IPeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPeriod getPickupPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, byte[] content, String mimeCode, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTestIndicator(boolean indicator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCopyIndicator(boolean indicator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCopy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addLanguage(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPurpose(MessageFunctionEnum code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageFunctionEnum getPurposeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRequestedResponse(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRequestedResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxCurrency(String isoCurrencyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTaxCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxPointDateCode(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTaxPointDateCode() {
		// TODO Auto-generated method stub
		return null;
	}

	// 524: BT-14 0..1 SALES ORDER REFERENCED DOCUMENT
	// eine vom Verkäufer ausgegebene Kennung für einen referenzierten Verkaufsauftrag
	@Override
	public void setOrderReference(String docRefId) {
		// TODO Auto-generated method stub	
	}
	@Override
	public String getOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	// 529: BT-13 0..1 Purchase order reference
	// eine vom Käufer ausgegebene Kennung für eine referenzierte Bestellung
	@Override
	public void setPurchaseOrderReference(String id) {
		// TODO Auto-generated method stub	
	}
	@Override
	public String getPurchaseOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	// 534: 0..1 QUOTATION REFERENCE, not in CII
	@Override
	public void setQuotationReference(String id) {
		// TODO Auto-generated method stub	
	}
	@Override
	public String getQuotationReference() {
		// TODO Auto-generated method stub
		return null;
	}

	// 539: BT-12 0..1 Contract reference / (Referenz auf Rahmenvertrag)
	// Die Vertragsreferenz sollte im Kontext der spezifischen Handelsbeziehung 
	// und für einen definierten Zeitraum einmalig vergeben sein
	@Override
	public void setContractReference(String id) {
		orderHeader.setContractReference(id);
	}
	@Override
	public String getContractReference() {
		return orderHeader.getContractReference();
	}

	// 549: BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, String description, String uri) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, String description, byte[] content,
			String mimeCode, String filename) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addSupportigDocument(SupportingDocument supportigDocument) {
		// TODO Auto-generated method stub	
	}
	@Override
	public List<SupportingDocument> getAdditionalSupportingDocuments() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setInvoicedObject(String name, String schemeID) {
		// TODO Auto-generated method stub	
	}
	@Override
	public String getInvoicedObject() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Identifier getInvoicedObjectIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentMeansEnum getPaymentMeansEnum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaymentMeansEnum(PaymentMeansEnum code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPaymentMeansText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaymentMeansText(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPaymentTerm(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPaymentTerms(List<String> paymentTerms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getPaymentTerms() {
		// TODO Auto-generated method stub
		return null;
	}

}
