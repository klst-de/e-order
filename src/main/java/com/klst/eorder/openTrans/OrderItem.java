package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.bmecat.bmecat._2005.COSTCATEGORYID;
import org.opentrans.xmlschema._2.CUSTOMERORDERREFERENCE;
import org.opentrans.xmlschema._2.FEATURE;
import org.opentrans.xmlschema._2.ORDERITEM;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.ISupplyChainEvent;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;
import com.klst.eorder.impl.ID;
import com.klst.eorder.impl.TradeProductInstance;
import com.klst.eorder.impl.UnitPriceAmount;
import com.klst.eorder.openTrans.reflection.Mapper;

/**
 * 33: BG-25 1..n ORDER LINE
 * <p>
 * A group of business terms providing information on individual order line.
 * <p>
 * Similar to EN16931 business group BG-25
 */
public class OrderItem extends ORDERITEM implements OrderLine {

	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		return create(id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
	}

	static OrderItem create(String id, IQuantity quantity, IAmount lineTotalAmount, UnitPriceAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		OrderItem orderLine =  new OrderItem(id, quantity, lineTotalAmount, priceAmount, itemName, taxCat, percent);
		return orderLine;
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

	private static final Logger LOG = Logger.getLogger(OrderItem.class.getName());

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

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatus(String status) {
		LOG.warning(WARN_ORDERLINEID + "ignore status:'"+status+"'.");
	}
	
	// 37: BT-127 0..1/n Freitext zur Rechnungsposition : ram:IncludedNote
	@Override // getter
	public List<OrderNote> getNotes() {
		return Remarks.getNotes(super.getREMARKS());
	}
	@Override
	public OrderNote createNote(String subjectCode, String content) {
		return Remarks.create(subjectCode, content);
	}
	@Override
	public void addNote(OrderNote note) {
		super.getREMARKS().add((Remarks)note);
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

	// 43: BG-31.BT-157 0..n Item (Trade Product) Global ID
	// 44:                   Item (Trade Product) Global ID Scheme ID
	@Override
	public Identifier createStandardIdentifier(String globalID, String schemeID) {
		return productid.createStandardIdentifier(globalID, schemeID);
	}
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
	@Override
	public void addItemAttribute(String name, String value) {
		// TODO 
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
	/* PRODUCTFEATURES enthält List<FEATURE> feature:
	 * Produktmerkmal FEATURE Informationen über ein Produktmerkmal 
	 */
	@Override
	public Properties getItemAttributes() {
		List<FEATURE> feature = super.getPRODUCTFEATURES()==null ? null : super.getPRODUCTFEATURES().getFEATURE();
//		List<ProductCharacteristicType> productCharacteristics = specifiedTradeProduct.getApplicableProductCharacteristic();
		Properties result = new Properties();
		feature.forEach(f -> {
			if(f.getFNAME().isEmpty()) { // eindeutiger Name des Merkmals
				// nix tun
			} else {
				// FVALUE : Ausprägung(en) des referenzierten Merkmals
				if(f.getFVALUE().isEmpty()) {
					// in OT steht was von VARIANTS, dann aber doch nicht
				} else {
					result.put(f.getFNAME().get(0).getValue(), f.getFVALUE().get(0).getValue());
				}
			}
//			result.put(pc.getDescription().get(0).getValue(), pc.getValue().get(0).getValue());			
		});
		return result;
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

	// 77,78: BG-31.BT-159 0..1 Item country of origin / nicht in opentrans, ===> "countryoforigin" als ARTIKELATTRIBUTE
	private static final String COUNTRY_OF_ORIGIN = "countryoforigin";
	@Override
	public void setCountryOfOrigin(String code) {
		addItemAttribute(COUNTRY_OF_ORIGIN, code);
	}
	@Override
	public String getCountryOfOrigin() {
		Properties attributes = getItemAttributes();
		return (String) attributes.get(COUNTRY_OF_ORIGIN);
	}

	// 107: LINE TRADE AGREEMENT
	// 178: (Net Price)
	// 179: BG-29.BT-146 1..1 Item net price aka UnitPriceAmount / PRODUCTPRICEFIX.priceamount
	@Override
	public IAmount getUnitPriceAmount() {
		// delegieren:
		return productpricefix.getUnitPriceAmount();
	}
	private void setUnitPriceAmount(IAmount unitPriceAmount) {
		productpricefix.setUnitPriceAmount(unitPriceAmount);
	}

	// 180+181: BG-29.BT-150 + BG-29.BT-149 0..1 / PRODUCTPRICEFIX.pricequantity
	@Override
	public IQuantity getUnitPriceQuantity() {
		if(super.getPRODUCTPRICEFIX()==null) return null;
		// delegieren:
		return productpricefix.getUnitPriceQuantity();
	}
	@Override
	public void setUnitPriceQuantity(IQuantity basisQuantity) {
//		Mapper.set(getPRODUCTPRICEFIX(), "pricequantity", basisQuantity); // BUG
		productpricefix.setUnitPriceQuantity(basisQuantity);
	}
	
//	158 SCT_LINE_TA COMFORT	  (Gross Price)
//	159 SCT_LINE_TA COMFORT	  Gross Price
//	160 SCT_LINE_TA COMFORT	  Gross Price Base quantity
//	161 SCT_LINE_TA COMFORT	  Gross Price Unit Code for base quantity
	// 158: BG-29.BT-148 0..1 Item gross price
	@Override
	public IAmount getGrossPrice() {
		// delegieren:
		return productpricefix.getGrossPrice();
	}
	@Override
	public void setGrossPrice(IAmount grossPrice) {
		productpricefix.setGrossPrice(grossPrice);
	}

	// 162: BG-29.BT-147 0..1 PriceDiscount
	@Override
	public AllowancesAndCharges getPriceDiscount() {
		// delegieren:
		return productpricefix.getPriceDiscount();
	}
	@Override
	public void setPriceDiscount(AllowancesAndCharges discount) {
		productpricefix.setPriceDiscount(discount);
	}

	
	// BG.25.BT-128 0..1 Objektkennung // (OBJECT IDENTIFIER FOR INVOICE LINE) Zeile 154
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

	// BT-133 0..1 line Buyer accounting reference : ram:ReceivableSpecifiedTradeAccountingAccount
	public void setBuyerAccountingReference(String text) {
		Mapper.newFieldInstance(this, "accountinginfo", text);
		Mapper.set(getACCOUNTINGINFO(), "costcategoryid", text);
// TODO testen
	}
	/* ACCOUNTING_INFO (Kontierungsinformation) namespace: BMECAT
	 * In diesem Element werden Informationen über die Kontierung erfasst, 
	 * die beim einkaufenden Unternehmen durch den Auftrag anfallen. Zu diesen Informationen gehören 
	 *  die Nummer der zu belastenden Kostenstelle oder des zu belastenden Projekts oder des zu belastenden Werkauftrags
	 *  die Kostenart 
	 *  sowie das eigentliche Konto. 
	 * Die Kontierungsinformationen werden vom einkaufenden Unternehmen angegeben, damit der Lieferant sie auf der 
	 * Rechnung angeben kann und so wiederum die Rechnungsprüfung beim einkaufenden Unternehmen erleichtert wird.
	 */
	public String getBuyerAccountingReference() {
		COSTCATEGORYID cc = super.getACCOUNTINGINFO()==null ? null : getACCOUNTINGINFO().getCOSTCATEGORYID();
		return cc==null ? null : cc.getValue();
	}

//---------------- BG-26 0..1 BG-26.BT-134 + BG-26.BT-135
	// 291..393 + 294..296
	// 304..306 + 305..307
	@Override // factory
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return DeliveryDate.create(start, end);
	}	
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

/*
                    <ram:SpecifiedTradeAllowanceCharge>
                         <ram:ChargeIndicator>
                              <udt:Indicator>false</udt:Indicator>
                         </ram:ChargeIndicator>
                         <ram:CalculationPercent>10.00</ram:CalculationPercent>
                         <ram:BasisAmount>60.00</ram:BasisAmount>
                         <ram:ActualAmount>6.00</ram:ActualAmount>
                         <ram:ReasonCode>64</ram:ReasonCode>
                         <ram:Reason>SPECIAL AGREEMENT</ram:Reason>
                    </ram:SpecifiedTradeAllowanceCharge>
                    <ram:SpecifiedTradeAllowanceCharge>
                         <ram:ChargeIndicator>
                              <udt:Indicator>true</udt:Indicator>
                         </ram:ChargeIndicator>
                         <ram:CalculationPercent>10.00</ram:CalculationPercent>
                         <ram:BasisAmount>60.00</ram:BasisAmount>
                         <ram:ActualAmount>6.00</ram:ActualAmount>
                         <ram:ReasonCode>64</ram:ReasonCode>
                         <ram:Reason>FREIGHT SERVICES</ram:Reason>
                    </ram:SpecifiedTradeAllowanceCharge>

 */
	/*
	 * BG-27 0..n LINE ALLOWANCES
	 * BG-28 0..n LINE CHARGES
	 * 
	 * 
	 * TODO in PRODUCTPRICEFIX :   List<ALLOWORCHARGE> alloworcharge
	 */
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return AllowOrCharge.create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return AllowOrCharge.create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}

	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
//		if(allowanceOrCharge==null) return; // optional
//		super.getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceCharge().add((TradeAllowanceCharge)allowanceOrCharge);
	}

	// BG-27 0..n LINE ALLOWANCES / ABSCHLÄGE
	// BG-28 0..n LINE CHARGES / ZUSCHLÄGE
	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		return productpricefix.getAllowancesAndCharges();
/* TODO
			<PRODUCT_PRICE_FIX>
				<bmecat:PRICE_AMOUNT>0.0</bmecat:PRICE_AMOUNT>
				<ALLOW_OR_CHARGES_FIX>

 */
//		List<TradeAllowanceChargeType> allowanceChargeList = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceCharge();
//		List<AllowancesAndCharges> res = new ArrayList<AllowancesAndCharges>(allowanceChargeList.size());
//		allowanceChargeList.forEach(allowanceOrCharge -> {
//			res.add(TradeAllowanceCharge.create(allowanceOrCharge));
//		});
//		return res;
	}

	
	// BG-30.BT-151 1..1 item VAT category code
	@Override
	public void setTaxCategory(TaxCategoryCode codeEnum) {
		productpricefix.setTaxCategory(codeEnum);
	}
	@Override
	public TaxCategoryCode getTaxCategory() {
		return productpricefix.getTaxCategory();
	}

	// BG-30.BT-152 0..1 item VAT rate
	@Override
	public void setTaxRate(BigDecimal taxRate) {
		productpricefix.setTaxRate(taxRate);
	}
	@Override
	public BigDecimal getTaxRate() {
		// delegieren:
		return productpricefix.getTaxRate();
	}


	// --------------------------- CIO only:
	// 208: 0..1
	static final String TRUE = Boolean.TRUE.toString().toUpperCase();
	// funktonal:
	private Predicate<String> isTRUE = indicator -> {
		return indicator!=null && indicator.equals(TRUE);
	};
	@Override
	public boolean isPartialDeliveryAllowed() {
		return isTRUE.test(super.getPARTIALSHIPMENTALLOWED());
	}
	@Override
	public void setPartialDeliveryIndicator(boolean indicator) {
		if(indicator) {
			setPARTIALSHIPMENTALLOWED(TRUE);
		}
	}

	@Override
	public void addReferencedProductDocument(String code, SupportingDocument supportigDocument) {
		// TODO Auto-generated method stub
		
	}

	/* SupportingDocument
	   - at document line level:
-  79ff: 0..n ADDITIONAL REFERENCED PRODUCT DOCUMENT in SpecifiedTradeProduct
- 141ff: 0..n ADDITIONAL REFERENCED DOCUMENT in SpecifiedLineTradeAgreement
- 154: BG.25.BT-128 0..1 line object identifier

CUSTOMER_ORDER_REFERENCE / (Kundenauftragsbezug)
Referenzinformationen zum Auftrag des Kunden (des Einkäufers) auf den sich die Position bezieht.
<ORDER_ITEM> ...
<CUSTOMER_ORDER_REFERENCE>
	<ORDER_ID>PLEX-141269</ORDER_ID>
	<LINE_ITEM_ID>1</LINE_ITEM_ID>
	<ORDER_DATE>2020-01-22</ORDER_DATE>
</CUSTOMER_ORDER_REFERENCE>

	class INTERNATIONAL_PID extends INTERNATIONALPID implements Identifier {
                                                                Reference
*/
	class SimpleId implements Reference {

		private String content;
		
		SimpleId(String content, String type) {
			setContent(content);
			setSchemeIdentifier(type);
		}

		@Override
		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String getContent() {
			return content;
		}

		@Override
		public void setSchemeIdentifier(String id) {
		}

		@Override
		public String getSchemeIdentifier() {
			return null;
		}
		
	}
	@Override
	public List<SupportingDocument> getReferencedProductDocuments() {
		List<SupportingDocument> res = new ArrayList<SupportingDocument>();
		List<CUSTOMERORDERREFERENCE> list = super.getCUSTOMERORDERREFERENCE();
		list.forEach(cor -> {
			String docRefId = cor.getORDERID();
//			Reference lineId = new SimpleId(cor.getLINEITEMID(), null);
			Reference lineId = new ID(cor.getLINEITEMID());
			Timestamp ts = DateTimeFormats.dtDATETIMEToTs(cor.getORDERDATE());
//			List<ORDERDESCR> description = TODO cor.getORDERDESCR();
			String description = null;
//			TypePARTYID cor.getCUSTOMERIDREF()
			res.add(createSupportigDocument(docRefId, lineId, description, ts, null));
		});
		return res;
	}

	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, String uri) {
		return CustomerOrderReference.create(docRefId, lineId, description, ts);
	}

	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, byte[] content, String mimeCode, String filename) {
		return CustomerOrderReference.create(docRefId, lineId, description, ts);
	}

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
	public AllowancesAndCharges getPriceCharge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPriceCharge(AllowancesAndCharges charge) {
		// TODO Auto-generated method stub
		
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
