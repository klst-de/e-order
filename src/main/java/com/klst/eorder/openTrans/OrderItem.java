package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Logger;

import org.bmecat.bmecat._2005.ACCOUNTINGINFO;
import org.bmecat.bmecat._2005.COSTCATEGORYID;
import org.opentrans.xmlschema._2.ORDERITEM;
import org.opentrans.xmlschema._2.PRODUCTFEATURES;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.ISupplyChainEvent;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;
import com.klst.eorder.impl.TradeProductInstance;
import com.klst.eorder.impl.UnitPriceAmount;

/**
 * 33-342: BG-25 1..n ORDER LINE
 * <p>
 * A group of business terms providing information on individual order line.
 * <p>
 * Similar to EN16931 business group BG-25
 */
public class OrderItem extends ORDERITEM implements DefaultOrderLine {

	private static final Logger LOG = Logger.getLogger(OrderItem.class.getName());

	static OrderItem create(String id, IQuantity quantity, IAmount lineTotalAmount, UnitPriceAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		return new OrderItem(id, quantity, lineTotalAmount, priceAmount, itemName, taxCat, percent);
	}

	// copy factory
	static OrderItem create(ORDERITEM object, CoreOrder order) {
		if(object instanceof ORDERITEM && object.getClass()!=ORDERITEM.class) {
			// object is instance of a subclass of ORDERITEM, but not ORDERITEM itself
			return (OrderItem)object;
		} else {
			return new OrderItem(object); 
		}
	}

	Productid productid;
	Productpricefix productpricefix;
	
	// copy ctor
	private OrderItem(ORDERITEM line) {
		SCopyCtor.getInstance().invokeCopy(this, line);
		productid = Productid.create(super.getPRODUCTID());
		productpricefix = Productpricefix.create(super.getPRODUCTPRICEFIX());
		LOG.config("copy ctor:"+this);
	}

	private OrderItem(String id
			, IQuantity quantity
			, IAmount lineTotalAmount, UnitPriceAmount priceAmount, String itemName
			, TaxCategoryCode taxCat, BigDecimal percent) {
		productid = Productid.create();
		super.setPRODUCTID(productid);
		
		setId(id);
		setQuantity(quantity);
		setLineTotalAmount(lineTotalAmount);

		productpricefix = Productpricefix.create();
		super.setPRODUCTPRICEFIX(productpricefix);
		setUnitPriceAmount(priceAmount);
		setItemName(itemName);
		if(taxCat!=null) setTaxCategoryAndRate(taxCat, percent);
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("[Id:"+getId()); 
		stringBuilder.append(", ItemName:");
		stringBuilder.append(getItemName()==null ? "null" : getItemName());
		stringBuilder.append(", Quantity:");
		stringBuilder.append(getQuantity()==null ? "null" : getQuantity());
		stringBuilder.append(", LineTotalAmount:");
		stringBuilder.append(getLineTotalAmount()==null ? "null" : getLineTotalAmount());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	// 35: BG-25.BT-126 1..1
	// interface java.util.function.Function<T,R>
	// interface java.util.function.BiFunction<T,U,R>
	private BiFunction<ORDERITEM, String, ORDERITEM> setLineItemID = (ORDERITEM i, String s) -> {
		i.setLINEITEMID(s);
		return i;
	};
	private Function<ORDERITEM, String> getLineItemID = (ORDERITEM s) -> {
		return s.getLINEITEMID();
	};
	void setId(String id) {
		setLineItemID.apply(this, id);
//		super.setLINEITEMID(id);
	}
	@Override
	public String getId() {
		return getLineItemID.apply(this);
//		return super.getLINEITEMID();
	}

	// 37: BT-127 0..1/n Freitext zur Rechnungsposition
	@Override // getter
	public List<OrderNote> getNotes() {
		return Remarks.getNotes(super.getREMARKS());
	}
	@Override
	public void addNote(OrderNote note) {
		super.getREMARKS().add((Remarks)note);
	}
	
	// 43: BG-31.BT-157 0..n Item (Trade Product) Global ID
	// 44:                   Item (Trade Product) Global ID Scheme ID
	@Override
	public void addStandardIdentifier(Identifier id) {
		productid.addStandardIdentifier(id);
	}
	@Override
	public List<Identifier> getStandardIdentifier() {
		return productid.getStandardIdentifier();
	}

	// 45: BG-31.BT-155 0..1 Item Seller's identifier, productid.supplierpid
	@Override
	public void setSellerAssignedID(String id) {
		productid.setSellerAssignedID(id);
	}
	@Override
	public String getSellerAssignedID() {
		return productid.getSellerAssignedID();
	}

	// 46: BG-31.BT-156 0..1 Item Buyer's identifier, productid.buyerpid (1..n)
	@Override
	public void setBuyerAssignedID(String id) {
		productid.setBuyerAssignedID(id);
	}
	@Override
	public String getBuyerAssignedID() {
		return productid.getBuyerAssignedID();
	}

	// 47: 0..1 SpecifiedTradeProduct.industryAssignedID
	// 48: 0..1 SpecifiedTradeProduct.modelID
	
	// 49: BG-31.BT-153 1..1 SpecifiedTradeProduct.Name
	void setItemName(String text) {
		productid.setItemName(text);
	}
	@Override
	public String getItemName() {
		return productid.getItemName();
	}

	// 50: BG-31.BT-154 0..1 Item description
	@Override
	public void setDescription(String text) {
		productid.setDescription(text);
	}
	@Override
	public String getDescription() {
		return productid.getDescription();
	}
	
	// 51: 0..1 Item (Trade Product) Batch ID (lot ID)
	// Eindeutige Identifikation der Charge aus der das Produkt stammt.
	// TODO Unterschied zu 66: Item (Trade Product) Instances Batch ID
	@Override
	public void setBatchID(String id) {
		productid.getLOTNUMBER().add(id);
	}
	@Override
	public String getBatchID() {
		return productid.getLOTNUMBER().isEmpty() ? null 
			: productid.getLOTNUMBER().get(0);
	}

	// 52: 0..1 SpecifiedTradeProduct.brandName
	// 53: 0..1 SpecifiedTradeProduct.modelName

	// 54: BG-32 0..n ITEM ATTRIBUTES
	/* PRODUCTFEATURES enthält List<FEATURE> feature:
	 *  Produktmerkmal FEATURE Informationen über ein Produktmerkmal 
	 */
	@Override
	public void addItemAttribute(String name, String value) {
		if(name==null) return;
		
		PRODUCTFEATURES pf = getPRODUCTFEATURES();
		if(pf==null) {
			pf = new PRODUCTFEATURES();
			setPRODUCTFEATURES(pf);
		}
		
		pf.getFEATURE().add(createFeature(name, value));
		
//		PRODUCTFEATURES pf = super.getPRODUCTFEATURES()
//			    "referencefeaturesystemname",
//			    "referencefeaturegroupid",
//			    "referencefeaturegroupname",
//			    "referencefeaturegroupid2",
//			    "groupproductorder",
//			    List<FEATURE> feature:
//	    protected List<FNAME> fname;
//	    protected String ftidref;
//	    protected TypeFTEMPLATE ftemplate;
//	    protected List<FVALUE> fvalue;
//	    protected List<String> valueidref;
//	    protected String funit;
//	    protected BigInteger forder;
//	    protected List<FDESCR> fdescr;
//	    protected List<FVALUEDETAILS> fvaluedetails;
//	    protected String fvaluetype;
/*
				<FEATURE>
					<bmecat:FNAME>a</bmecat:FNAME>
					<bmecat:FVALUE>a</bmecat:FVALUE>
					<bmecat:FUNIT>a</bmecat:FUNIT>
					<bmecat:FORDER>0</bmecat:FORDER>
					<bmecat:FDESCR lang="aar">a</bmecat:FDESCR>
					<bmecat:FVALUE_DETAILS lang="aar">a</bmecat:FVALUE_DETAILS>
					<bmecat:FVALUE_TYPE>choice</bmecat:FVALUE_TYPE>
				</FEATURE>

 */
	}
	@Override
	public Properties getItemAttributes() {
		return getItemAttributes(super.getPRODUCTFEATURES());
	}

	// 77,78: BG-31.BT-159 0..1 Item country of origin / nicht in opentrans, ===> "countryoforigin" als ARTIKELATTRIBUTE
	private static final String COUNTRY_OF_ORIGIN = "countryoforigin";
	@Override
	public void setCountryOfOrigin(String code) {
		addItemAttribute(COUNTRY_OF_ORIGIN, code);
	}
	@Override
	public String getCountryOfOrigin() {
		Properties attributes = getItemAttributes();
		if(attributes==null) return null;
		return (String) attributes.get(COUNTRY_OF_ORIGIN);
	}

	// LINE TRADE AGREEMENT

	// 158: BG-29.BT-148 0..1 Item gross price / Bruttopreis (nicht in OT)
	// 159: BG-29.BT-148 0..1 Item gross price (wird berechnet, wenn Rabatt/PriceDiscount vorhanden)
	@Override
	public IAmount getGrossPrice() {
		return productpricefix.getGrossPrice();
	}

	// 162: BG-29.BT-147 0..1 PriceDiscount
	@Override
	public void setPriceDiscount(AllowancesAndCharges discount) {
		productpricefix.setPriceDiscount(discount);
	}
	@Override
	public AllowancesAndCharges getPriceDiscount() {
		return productpricefix.getPriceDiscount();
	}
	
	// 170: 0..1 Item price charge
	@Override
	public void setPriceCharge(AllowancesAndCharges charge) {
		productpricefix.setPriceCharge(charge);
	}
	@Override
	public AllowancesAndCharges getPriceCharge() {
		return productpricefix.getPriceCharge();
	}

	// 178: (Net Price)
	// 179: BG-29.BT-146 1..1 Item net price aka UnitPriceAmount / PRODUCTPRICEFIX.priceamount
	@Override
	public IAmount getUnitPriceAmount() {
		return productpricefix.getUnitPriceAmount();
	}
	private void setUnitPriceAmount(IAmount unitPriceAmount) {
		productpricefix.setUnitPriceAmount(unitPriceAmount);
	}

	// 318: BG-27 0..n LINE ALLOWANCES / ABSCHLÄGE
	// 326: BG-28 0..n LINE CHARGES / ZUSCHLÄGE
	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		productpricefix.addAllowanceCharge(allowanceOrCharge);
	}
	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		return productpricefix.getAllowancesAndCharges();
	}

	// 180+181: BG-29.BT-150 + BG-29.BT-149 0..1 / PRODUCTPRICEFIX.pricequantity
	@Override
	public IQuantity getUnitPriceQuantity() {
		if(super.getPRODUCTPRICEFIX()==null) return null;
		return productpricefix.getUnitPriceQuantity();
	}
	@Override
	public void setUnitPriceQuantity(IQuantity basisQuantity) {
		productpricefix.setUnitPriceQuantity(basisQuantity);
	}

	// 210: BT-129 1..1 bestellte Menge / The quantity, at line level, requested for this trade delivery.
	// 211: Unit of measure Code for Requested quantity BT-129+BT-130
	void setQuantity(IQuantity quantity) { 
		super.setORDERUNIT(quantity.getUnitCode()); // required
		super.setQUANTITY(quantity.getValue()); // required		
	}
	@Override
	public IQuantity getQuantity() {
		return Quantity.create(super.getORDERUNIT(), super.getQUANTITY());
	}

	//---------------- BG-26 0..1 BG-26.BT-134 + BG-26.BT-135
	// 291..393 + 294..296
	// 304..306 + 305..307
	@Override
	public void setDeliveryPeriod(IPeriod period) {
		super.setDELIVERYDATE((DeliveryDate)period);
	}
	@Override
	public void setDeliveryDate(Timestamp timestamp) {
		setDeliveryPeriod(DeliveryDate.create(timestamp, timestamp));
	}
	@Override
	public Timestamp getDeliveryDateAsTimestamp() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return DateTimeFormats.dtDATETIMEToTs(getDELIVERYDATE().getDELIVERYSTARTDATE()); 
		}
		return null;
	}
	@Override
	public IPeriod getDeliveryPeriod() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return null;
		}
		// DELIVERYDATE ist Zeitraum
		return DeliveryDate.create(getDELIVERYDATE());
	}

	// 315: BG-30.BT-151 1..1 item VAT category code
	@Override
	public void setTaxCategory(TaxCategoryCode code) {
		productpricefix.setTaxCategory(code);
	}
	@Override
	public TaxCategoryCode getTaxCategory() {
		return productpricefix.getTaxCategory();
	}

	// 317: BG-30.BT-152 0..1 item VAT rate
	@Override
	public void setTaxRate(BigDecimal taxRate) {
		productpricefix.setTaxRate(taxRate);
	}
	@Override
	public BigDecimal getTaxRate() {
		return productpricefix.getTaxRate();
	}

	// 315+317:
	@Override
	public void setTaxCategoryAndRate(TaxCategoryCode code, BigDecimal taxRate) {
		productpricefix.setTaxCategoryAndRate(code, taxRate);
	}

	/* 335: BT-131 1..1 Nettobetrag der Rechnungsposition / PRICE_LINE_AMOUNT
	 * Höhe des Preises für die Positionszeile. 
	 * Der Wert ergibt sich im Regelfall aus der Multiplikation von PRICE_AMOUNT und QUANTITY, 
	 * muss aber explizit aufgeführt werden. 
	 * Der PRICE_LINE_AMOUNT kann sich auch aus PRICE_AMOUNT und PRICE_UNIT_VALUE ergeben, 
	 * wenn der Preis nicht an die Bestelleinheit gekoppelt ist. Siehe auch PRICE_BASE_FIX.
	 */
	void setLineTotalAmount(IAmount amount) {
		super.setPRICELINEAMOUNT(amount.getValue());
	}
	@Override
	public IAmount getLineTotalAmount() {
		return super.getPRICELINEAMOUNT()==null ? null : Amount.create(getPRICELINEAMOUNT());
	}

// ---------------------------------------------------------------
	/* SupportingDocument
		- at document line level:
		- 141ff: 0..n ADDITIONAL REFERENCED DOCUMENT in SpecifiedLineTradeAgreement

CUSTOMER_ORDER_REFERENCE / (Kundenauftragsbezug)
Referenzinformationen zum Auftrag des Kunden (des Einkäufers) auf den sich die Position bezieht.

	<ORDER_ITEM> ...
	<CUSTOMER_ORDER_REFERENCE>
		<ORDER_ID>PLEX-141269</ORDER_ID>
		<LINE_ITEM_ID>1</LINE_ITEM_ID>
		<ORDER_DATE>2020-01-22</ORDER_DATE>
	</CUSTOMER_ORDER_REFERENCE>

*/
	@Override
	public void addReferencedDocument(SupportingDocument supportigDocument) {
		super.getCUSTOMERORDERREFERENCE().add((CustomerOrderReference)supportigDocument);		
	}
	@Override
	public List<SupportingDocument> getReferencedDocuments() {
		List<SupportingDocument> res = new ArrayList<SupportingDocument>();
		super.getCUSTOMERORDERREFERENCE().forEach(cor -> {
			res.add( CustomerOrderReference.create(cor) );
		});
		return res;
	}
	
	// 208: 0..1
	// funktonal:
	@Override
	public boolean isPartialDeliveryAllowed() {
		return DefaultOrder.isTRUE.test(super.getPARTIALSHIPMENTALLOWED());
	}
	@Override
	public void setPartialDeliveryIndicator(boolean indicator) {
		if(indicator) {
			setPARTIALSHIPMENTALLOWED(DefaultOrder.TRUE);
		}
	}

	// 340: BT-133 0..1 line Buyer accounting reference
	/* ACCOUNTING_INFO (Kontierungsinformation) namespace: BMECAT
	 * In diesem Element werden Informationen über die Kontierung erfasst, 
	 * die beim einkaufenden Unternehmen durch den Auftrag anfallen. Zu diesen Informationen gehören 
	 *  die Nummer der zu belastenden Kostenstelle oder des zu belastenden Projekts oder des zu belastenden Werkauftrags
	 *  die Kostenart 
	 *  sowie das eigentliche Konto. 
	 * Die Kontierungsinformationen werden vom einkaufenden Unternehmen angegeben, damit der Lieferant sie auf der 
	 * Rechnung angeben kann und so wiederum die Rechnungsprüfung beim einkaufenden Unternehmen erleichtert wird.
	 */
	public void setBuyerAccountingReference(String text) {
		ACCOUNTINGINFO accInfo = new ACCOUNTINGINFO();
		COSTCATEGORYID ccId = new COSTCATEGORYID(); // required
//		ccId.setType("cost_center"); // cost_center, project, work_order
		ccId.setValue(text);
		accInfo.setCOSTCATEGORYID(ccId);
		super.setACCOUNTINGINFO(accInfo);
	}
	public String getBuyerAccountingReference() {
		COSTCATEGORYID cc = super.getACCOUNTINGINFO()==null ? null : getACCOUNTINGINFO().getCOSTCATEGORYID();
		return cc==null ? null : cc.getValue();
	}

// ---------------------------------------------------------------


	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatus(String status) {
		LOG.warning(WARN_ORDERLINEID + "ignore status:'"+status+"'.");
	}
	
	// 42: 0..1 PRODUCT_ID.SUPPLIER_PID ??????
	@Override
	public void setProductID(String id) {
		// TODO Auto-generated method stub	
	}
	@Override
	public String getProductID() {
		// TODO Auto-generated method stub
		return null;
	}


	// 60: BG-31.BT-158 0..n Item classification identifier designatedProductClassification
	/* ProductClassificationType CodeType classCode (value, listID, listVersionID)
	                             TextType className

 Bsp. 
        <ram:DesignatedProductClassification>
        <ram:ClassCode listID="EN">4047247110051</ram:ClassCode>
             <ram:ClassName>EN==EAN==European Article Number</ram:ClassName>
        </ram:DesignatedProductClassification>

			<PRODUCT_FEATURES>
				<bmecat:REFERENCE_FEATURE_GROUP_ID type="flat">a</bmecat:REFERENCE_FEATURE_GROUP_ID>
				<bmecat:REFERENCE_FEATURE_GROUP_NAME>a</bmecat:REFERENCE_FEATURE_GROUP_NAME>
				<bmecat:REFERENCE_FEATURE_GROUP_ID2 type="flat">a</bmecat:REFERENCE_FEATURE_GROUP_ID2>
				<bmecat:GROUP_PRODUCT_ORDER>0</bmecat:GROUP_PRODUCT_ORDER>
				<FEATURE>
					<bmecat:FNAME>a</bmecat:FNAME>
					<bmecat:FVALUE>a</bmecat:FVALUE>
					<bmecat:FUNIT>a</bmecat:FUNIT>
					<bmecat:FORDER>0</bmecat:FORDER>
					<bmecat:FDESCR lang="aar">a</bmecat:FDESCR>
					<bmecat:FVALUE_DETAILS lang="aar">a</bmecat:FVALUE_DETAILS>
					<bmecat:FVALUE_TYPE>choice</bmecat:FVALUE_TYPE>
				</FEATURE>
			</PRODUCT_FEATURES>

	 */
	@Override
	public IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID, String idText) {
		return null;
//		// TODO
	}
	@Override
	public void addClassificationIdentifier(IdentifierExt id) {
	}
	@Override
	public List<IdentifierExt> getClassifications() {
		return null;
	}

	// 65: SCT_LINE	COMFORT	  Item (Trade Product) Instances
//	66  SCT_LINE	COMFORT	  Item (Trade Product) Instances Batch ID
//	67  SCT_LINE	COMFORT	  Item (Trade Product) Instances Supplier Serial ID
	@Override
	public void addTradeProductInstance(String batchId, String serialId) {
		// TODO Auto-generated method stub	
	}
	// TradeProductInstanceType ist uncefact, nicht OT
	@Override
	public List<TradeProductInstance> getTradeProductInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO 68: SCT_LINE	COMFORT	  Packaging
	@Override
	public void setPackaging(String code, IQuantity width, IQuantity length, IQuantity height) {
		// TODO Auto-generated method stub		
	}
	@Override
	public String getPackagingCode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IQuantity getPackagingWidth() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IQuantity getPackagingLength() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IQuantity getPackagingHeight() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	// BG.25.BT-128 0..1 Objektkennung // (OBJECT IDENTIFIER FOR INVOICE LINE)
/*
                    <ram:AdditionalReferencedDocument>
                         <ram:IssuerAssignedID>ADD_REF_DOC_ID</ram:IssuerAssignedID>
                         <ram:URIID>ADD_REF_DOC_URIID</ram:URIID>
                         <ram:LineID>5</ram:LineID>
                         <ram:TypeCode>916</ram:TypeCode>
                         <ram:Name>ADD_REF_DOC_Desc</ram:Name>
                    </ram:AdditionalReferencedDocument>
                    <ram:AdditionalReferencedDocument>
                         <ram:IssuerAssignedID>OBJECT_125487</ram:IssuerAssignedID> <!-- id
                         <ram:TypeCode>130</ram:TypeCode>                           <!-- schemeID, TypeCode = "130" Rechnungsdatenblatt, UNTDID 1001 Untermenge
                         <ram:ReferenceTypeCode>AWV</ram:ReferenceTypeCode>         <!-- schemeCode, aus UNTDID 1153
                    </ram:AdditionalReferencedDocument>
 */
	@Override
	public void setLineObjectID(String id, String schemeID, String schemeCode) {
		if(id==null) return;
//		ReferencedDocument rd = ReferencedDocument.create(id, schemeID, schemeCode);
//		LineTradeAgreementType lta = super.getSpecifiedLineTradeAgreement(); 
//		lta.getAdditionalReferencedDocument().add(rd);
	}
	@Override
	public Identifier getLineObjectIdentifier() {
		return null;
//		List<ReferencedDocumentType> rds = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getAdditionalReferencedDocument();
//		if(rds==null || rds.isEmpty()) return null;
//		// A Line MUST NOT HAVE more than 1 Object Identifier BT-128
//		ReferencedDocument rd = ReferencedDocument.create(rds.get(0));
//		return new ID(rd.getIssuerAssignedID().getValue(), rd.getReferenceCode());
	}
	

	private static final String WARN_ORDERLINEID = "An Order (Document Type Code BT-3 = 220) MUST NOT contain a purchase order line reference.";
	// BT-132 0..1 Referenced purchase order line reference
	public void setOrderLineID(String id) {
//		if(id==null) return;
//		if(this.order.getDocumentCode()==DocumentNameCode.Order) {
//			LOG.warning(WARN_ORDERLINEID + " Ignore:'"+id+"'.");
//			return;
//		}
//		Mapper.newFieldInstance(getSpecifiedLineTradeAgreement(), "buyerOrderReferencedDocument", id);
//		Mapper.set(getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument(), "lineID", id);
	}
	public String getOrderLineID() {
		return null;
//		ReferencedDocumentType referencedDocument = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument();
//		return referencedDocument==null ? null : new ID(referencedDocument.getLineID()).getName();		
	}



	// --------------------------- CIO only:

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
	public List<ISupplyChainEvent> getPickupEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPickupEvent(ISupplyChainEvent supplyChainEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ISupplyChainEvent> getDeliveryEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDeliveryEvent(ISupplyChainEvent supplyChainEvent) {
		// TODO Auto-generated method stub
		
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
	public ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, Timestamp timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, IPeriod period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubstitutedProductID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubstitutedProductID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSubstitutedIdentifier(Identifier id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Identifier> getSubstitutedIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubstitutedSellerAssignedID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubstitutedSellerAssignedID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubstitutedBuyerAssignedID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubstitutedBuyerAssignedID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubstitutedProductName(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubstitutedProductName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubstitutedProductDescription(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubstitutedProductDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAgreedQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IQuantity getAgreedQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPackageQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IQuantity getPackageQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPerPackageQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IQuantity getPerPackageQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQuotationID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getQuotationID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQuotationLineID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getQuotationLineID() {
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


}
