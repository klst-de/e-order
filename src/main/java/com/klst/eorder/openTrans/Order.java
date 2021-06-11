package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bmecat.bmecat._2005.LANGUAGE;
import org.opentrans.xmlschema._2.ORDER;
import org.opentrans.xmlschema._2.ORDERITEM;
import org.opentrans.xmlschema._2.ORDERITEMLIST;
import org.opentrans.xmlschema._2.ORDERSUMMARY;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
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
    "orderheader", elemente:
	    CONTROLINFO controlinfo; hat nur 3 String elemente: stopautomaticprocessing, generatorinfo, generationdate
	    SOURCINGINFO sourcinginfo; Informationen über Beschaffungsaktivitäten zusammengefasst, 
	                               die diesem Auftrag vorausgegangen sind.
	    ORDERINFO orderinfo: required mit parties usw
    "orderitemlist",
    "ordersummary"
   ---
"sourcinginfo" SOURCING_INFO (Beschaffungsinformationen) : optional
		Im Element SOURCING_INFO werden Informationen über Beschaffungsaktivitäten zusammengefasst, 
		die diesem Auftrag vorausgegangen sind:
	protected String quotationid;   ===> // 534: 0..1 QUOTATION REFERENCE
	protected List<AGREEMENT> agreement;  ?? ==> wie applicableHeaderTradeAgreement ??? (Referenz auf Rahmenvertrag)
	    protected String agreementid;
	    protected String agreementlineid;
	    protected String agreementstartdate;
	    protected String agreementenddate;
	    protected TypePARTYID supplieridref;
	    protected List<AGREEMENTDESCR> agreementdescr;
	    protected MIMEINFO mimeinfo;
	    protected String type;
	    protected String _default;

	protected CATALOGREFERENCE catalogreference;
 
 */
public class Order extends ORDER implements DefaultOrder {

	private static final Logger LOG = Logger.getLogger(Order.class.getName());

	@Override // implements CoreOrderFactory
	public CoreOrder createOrder(String profile, String processType, DocumentNameCode code) {
		return create(profile, processType, code);
	}
	Order create(String profile, String processType, DocumentNameCode code) {
		return new Order(profile, processType, code);
	}

	// factory
	public static Order create() {
		return create((ORDER)null);
	}
	// copy factory
	static Order create(ORDER object) {
		if(object instanceof ORDER && object.getClass()!=ORDER.class) {
			// object is instance of a subclass of ORDER, but not ORDER itself
			return (Order)object;
		} else {
			return new Order(object); 
		}
	}

	OrderHeader orderHeader;
	OrderInfo orderInfo;

	// ctor public, damit dynamisches cast im Test möglich ist
	public Order(ORDER doc) {
		SCopyCtor.getInstance().invokeCopy(this, doc);
		
		orderHeader = OrderHeader.create(super.getORDERHEADER(), this);
		orderInfo = orderHeader.createOrderInfo();
	}

	private Order(String profile, String processType, DocumentNameCode documentNameCode) {
		// alle 3 param nicht in OT
		// profile, aka Customization, BG-2.BT-24
		// processType, BG-2.BT-23
		// documentNameCode, BT-3 get liefert Order
		LOG.config("Version="+super.getVersion() + " set to "+OPENPRANS_VERSION);
		setVersion(OPENPRANS_VERSION); // required
		// Attribut 'type' muss in Element 'ORDER' vorkommen
		super.setType("standard"); // andere: express , release , consignment
		orderHeader = OrderHeader.create(super.getORDERHEADER(), this);
		super.setORDERHEADER(orderHeader);
		orderInfo = orderHeader.createOrderInfo();
		orderHeader.setORDERINFO(orderInfo);
	}

	// 9: Order number BT-1 Identifier (mandatory) - A unique identification of the Order.
	@Override
	public void setId(String id) {
		orderInfo.setId(id);
	}
	@Override
	public String getId() {
		return orderInfo.getId();
	}

	// 11: BT-3 The Document TypeCode
	@Override
	public DocumentNameCode getDocumentCode() {
		return DocumentNameCode.Order;
	}

	/* 14: Document issue date, BT-2  Date (mandatory) 
	 * Das Datum, an dem der Beleg ausgestellt wurde.
	 */
	@Override
	public void setIssueDate(Timestamp timestamp) {
		orderInfo.setIssueDate(timestamp);
	}
	@Override
	public Timestamp getIssueDateAsTimestamp() {
		return orderInfo.getIssueDateAsTimestamp();
	}

	// 18: In OT ISO 639-2 alpha-3 code, in CIO/order-x ISO 639-1: de, en, es, ...
	@Override
	public void addLanguage(String id) {
		orderInfo.getLANGUAGE().add(createDefaultLanguage(id));
	}
	@Override
	public List<String> getLanguage() {
		List<String> res = new ArrayList<String>();
		List<LANGUAGE> list = orderInfo.getLANGUAGE();
		list.forEach(lang -> {
			res.add(lang.getValue().value()); // DtLANG ist enum, Bsp ZUL("zul");
		});
		return res;
	}

	// 21: BG-1 ORDER NOTE / REMARKS
	@Override
	public List<OrderNote> getOrderNotes() {
		return Remarks.getNotes(orderInfo.getREMARKS());
	}
	@Override
	public void addNote(OrderNote note) {
		orderInfo.getREMARKS().add((Remarks)note);
	}

	// 33: BG-25 1..n ORDER LINE
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
		return OrderItem.create(id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
	}
	@Override
	public void addLine(OrderLine line) {
		if(super.getORDERITEMLIST()==null) {		
			super.setORDERITEMLIST(new ORDERITEMLIST());
		}
		super.getORDERITEMLIST().getORDERITEM().add((OrderItem)line);
	}

	// 344: BT-10 0..1 Buyer reference
	@Override
	public void setBuyerReference(String reference) {
		orderInfo.setBuyerReference(reference);
	}
	@Override
	public String getBuyerReferenceValue() {
		return orderInfo.getBuyerReferenceValue();
	}

	// 345: BG-4 1..1 SELLER @see BG4_Seller
	@Override
	public void setSeller(BusinessParty party) {
		orderInfo.setSeller(party);
	}
	@Override
	public BusinessParty getSeller() {
		return orderInfo.getSeller();
	}

	// 390: BG-7 1..1 BUYER @see BG7_Buyer
	@Override
	public void setBuyer(BusinessParty party) {
		orderInfo.setBuyer(party);
	}
	@Override
	public BusinessParty getBuyer() {
		return orderInfo.getBuyer();
	}

	// 524: BT-14 0..1 SALES ORDER REFERENCED DOCUMENT
	// eine vom Verkäufer ausgegebene Kennung für einen referenzierten Verkaufsauftrag
	@Override
	public void setOrderReference(String docRefId, Timestamp timestamp) {
		orderHeader.setContractReference(docRefId, timestamp);
	}
	@Override
	public String getOrderReference() {
		return orderHeader.getContractReference();
	}
	@Override
	public Timestamp getOrderDate() {
		return orderHeader.getOrderDate();
	}

	// 534: 0..1 QUOTATION REFERENCE
	@Override
	public void setQuotationReference(String docRefId, Timestamp timestamp) {
		orderHeader.setQuotationReference(docRefId, timestamp);
	}
	@Override
	public String getQuotationReference() {
		return orderHeader.getQuotationReference();
	}
	@Override
	public Timestamp getQuotationDate() {
		return orderHeader.getQuotationDate();
	}

	// 539: BT-12 0..1 Contract reference / (Referenz auf Rahmenvertrag)
	// Die Vertragsreferenz sollte im Kontext der spezifischen Handelsbeziehung 
	// und für einen definierten Zeitraum einmalig vergeben sein
	@Override
	public void setContractReference(String docRefId, Timestamp timestamp) {
		orderHeader.setContractReference(docRefId, timestamp);
	}
	@Override
	public String getContractReference() {
		return orderHeader.getContractReference();
	}
	@Override
	public Timestamp getContractDate() {
		return orderHeader.getContractDate();
	}

	// 643: SHIP TO PARTY
	@Override
	public void setShipTo(BusinessParty party) {
		orderInfo.setShipTo(party);
	}
	@Override
	public BusinessParty getShipTo() {
		return orderInfo.getShipTo();
	}

	// 725: SHIP FROM PARTY
	@Override
	public void setShipFrom(BusinessParty party) {
		// TODO Auto-generated method stub	
	}
	@Override
	public BusinessParty getShipFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	// 767: BG-14 0..1 DELIVERY DATE
	@Override
	public void setDeliveryDate(Timestamp timestamp) {
		orderInfo.setDeliveryDate(timestamp);
	}
	@Override
	public Timestamp getDeliveryDateAsTimestamp() {
		return orderInfo.getDeliveryDateAsTimestamp();
	}
	// 770: BG-14 0..1 DELIVERY PERIOD
	@Override
	public void setDeliveryPeriod(IPeriod period) {
		orderInfo.setDeliveryPeriod((DeliveryDate)period);
	}
	@Override
	public IPeriod getDeliveryPeriod() {
		return orderInfo.getDeliveryPeriod();
	}

	// 790: BT-5 1..1 Document currency code
	@Override
	public void setDocumentCurrency(String isoCurrencyCode) {
		orderInfo.setDocumentCurrency(isoCurrencyCode);
	}
	@Override
	public String getDocumentCurrency() {
		return orderInfo.getDocumentCurrency();
	}

	// 792: 0..1 INVOICER PARTY
	@Override
	public void setInvoicer(BusinessParty party) {
		// TODO Auto-generated method stub	
	}
	@Override
	public BusinessParty getInvoicer() {
		// TODO Auto-generated method stub
		return null;
	}

	// 833: 0..1 INVOICEE PARTY / The "BILL TO"
	@Override
	public void setBillTo(BusinessParty party) {
		orderInfo.setBillTo(party);
	}
	@Override
	public BusinessParty getBillTo() {
		return orderInfo.getBillTo();
	}

	// 875: BG-16.BT-81 PAYMENT INSTRUCTIONS PaymentMeans
	// PaymentMeansEnum ist aus UNTDID 4461: Cheque, Bank card, ...
	@Override
	public void setPaymentMeansEnum(PaymentMeansEnum code) {
		orderInfo.setPaymentMeansEnum(code);
	}
	@Override
	public PaymentMeansEnum getPaymentMeansEnum() {
		return orderInfo.getPaymentMeansEnum();
	}
	// 876: BG-16.BT-82 PAYMENT INSTRUCTIONS PaymentMeans as text
//	@Override
//	public String getPaymentMeansText() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public void setPaymentMeansText(String text) {
//		// TODO Auto-generated method stub	
//	}
	
	// 925: BT-20 PAYMENT TERMS / Zahlungsmodalitäten
	// in CIO nur description
	// in OT.Order in ORDER_INFO.PAYMENT.PAYMENT_TERMS: List<PAYMENTTERM>
	// mit type="unece" + value aus UN/ECE 4279  Payment terms type code qualifier
	@Override
	public void addPaymentTerm(String description) {
		orderInfo.addPaymentTerm(description);
	}
	@Override
	public void setPaymentTerms(List<String> paymentTerms) {
		orderInfo.setPaymentTerms(paymentTerms);
	}
	@Override
	public List<String> getPaymentTerms() {
		return orderInfo.getPaymentTerms();
	}

	// 888: BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
	// 903: BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		orderInfo.addAllowanceCharge(allowanceOrCharge);
	}
	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		return orderInfo.getAllowancesAndCharges();
	}

	// 927: BG-22 DOCUMENT TOTALS 1..1 - mandatory BT-106, BT-109, BT-112
	// in ORDERSUMMARY gibt es nur protected BigDecimal totalamount
	// aber required = true : protected BigInteger totalitemnum
	@Override
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		if(super.getORDERSUMMARY()==null) {
			ORDERSUMMARY sum = new ORDERSUMMARY();
			sum.setTOTALAMOUNT(lineNet.getValue());
			sum.setTOTALITEMNUM(BigInteger.valueOf(getLines().size()));
			super.setORDERSUMMARY(sum);
		}
		return null;
	}
	// 928: BG-22.BT-106 - 1..1/1..1
	@Override
	public IAmount getLineNetTotal() {
		return super.getORDERSUMMARY()==null ? null : Amount.create(getORDERSUMMARY().getTOTALAMOUNT());
	}

//	@Override
//	public IAmount getTotalTaxExclusive() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public IAmount getTotalTaxInclusive() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setAllowancesTotal(IAmount amount) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public IAmount getAllowancesTotal() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setChargesTotal(IAmount amount) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public IAmount getChargesTotal() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setTaxTotal(IAmount amount) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public IAmount getTaxTotal() {
//		// TODO Auto-generated method stub
//		return null;
//	}



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

	// 942: BT-19 0..1 Buyer accounting reference
	@Override
	public void setBuyerAccountingReference(Reference textReference) {
		// auf item Ebene siehe item.ACCOUNTING_INFO
		// ACCOUNTING_INFO.Cost category id hat type e {cost_center,project,work_order}
	}
	@Override
	public Reference getBuyerAccountingReference() {
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


	// 529: BT-13 0..1 Purchase order reference
	// eine vom Käufer ausgegebene Kennung für eine referenzierte Bestellung
	@Override
	public void setPurchaseOrderReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub	
	}
	@Override
	public String getPurchaseOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getPurchaseOrderDate() {
		// TODO Auto-generated method stub
		return null;
	}

	// 544: 0..1 REQUISITION REFERENCE, not in CII
	@Override
	public void setRequisitionReference(String id, Timestamp timestamp) {
		// TODO
	}
	@Override
	public String getRequisitionReference() {
		return null;
	}
	@Override
	public Timestamp getRequisitionDate() {
		// TODO Auto-generated method stub
		return null;
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

}
