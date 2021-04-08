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
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG22_DocumentTotals;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;

/* alle drei elemente sind required:
    "orderheader",
    "orderitemlist",
    "ordersummary"
 */
public class Order extends ORDER implements CoreOrder {

	private static final Logger LOG = Logger.getLogger(Order.class.getName());

	OrderHeader orderHeader;
//    protected CONTROLINFO controlinfo;
//    protected SOURCINGINFO sourcinginfo;
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
//		die diesem Auftrag voraus gegangen sind. 
		
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
			String itemName) {
		// TODO Auto-generated method stub
		return null;
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
	public void setContractReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getContractReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPurchaseOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPurchaseOrderReference() {
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
	public void setQuotationReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getQuotationReference() {
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

}
