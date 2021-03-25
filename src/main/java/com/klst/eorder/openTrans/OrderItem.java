package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.ORDERITEM;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.ebXml.reflection.Mapper;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.impl.Code;
import com.klst.eorder.impl.ID;
import com.klst.eorder.impl.Note;
import com.klst.eorder.impl.ReferencedDocument;
import com.klst.eorder.impl.Text;
import com.klst.eorder.impl.TradeAllowanceCharge;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.DocumentLineDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeDeliveryType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeSettlementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ProductClassificationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ReferencedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeAccountingAccountType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeAllowanceChargeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradePriceType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeSettlementLineMonetarySummationType;

import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IndicatorType;
import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

/*
    "lineitemid",                BT-126 ++ 1..1 Invoice line identifier
 PRODUCTID "productid" required, BG-31               ?BT-128
	    "supplierpid",
	    "supplieridref",
	    "configcodefix",
	    "lotnumber",
	    "serialnumber",
	    "internationalpid",
	    "buyerpid",
	    "descriptionshort",
	    "descriptionlong",
	    "manufacturerinfo",
	    "producttype"
    "productfeatures",
    "productcomponents",
    "quantity",                  BT-129 ++ 1..1 Invoiced quantity
    "orderunit",                 BT-130 ++ 1..1 Invoiced quantity unit of measure code
 PRODUCTPRICEFIX "productpricefix",
    "pricelineamount",           BT-131 ++ 1..1 Invoice line net amount
    "partialshipmentallowed",
    "deliverydate",
    "partialdeliverylist",
    "sourcinginfo",
    "customerorderreference",
    "accountinginfo",
    "shipmentpartiesreference",
    "transport",
    "internationalrestrictions",
    "specialtreatmentclass",
    "mimeinfo",
    "remarks",                   BT-127 ++ 0..1 Invoice line note
    "itemudx"

BG-25 + 1..n INVOICE LINE


BT-128 ++ 0..1 Invoice line object identifier
          0..1 Scheme identifier



BT-132 ++ 0..1 Referenced purchase order line reference
BT-133 ++ 0..1 Invoice line Buyer accounting reference

BG-26 ++ 0..1 INVOICE LINE PERIOD
...
BG-27 ++ 0..n INVOICE LINE ALLOWANCES
...
BG-28 ++ 0..n INVOICE LINE CHARGES
...
BG-29 ++ 1..1 PRICE DETAILS               ------> ram:SpecifiedLineTradeAgreement 
BT-146 +++ 1..1 Item net price                                ram:NetPriceProductTradePrice ram:ChargeAmount
BT-147 +++ 0..1 Item price discount
BT-148 +++ 0..1 Item gross price
BT-149 +++ 0..1 Item price base quantity                      ram:NetPriceProductTradePrice ram:BasisQuantity
BT-150 +++ 0..1 Item price base quantity unit of measure code ram:NetPriceProductTradePrice ram:BasisQuantity

BG-30 ++ 1..1 LINE VAT INFORMATION
...
BG-31 ++ 1..1 ITEM INFORMATION            ------> ram:SpecifiedTradeProduct
...
BG-32 +++ 0..n ITEM ATTRIBUTES

 */
/**
 * BG-25 ORDER LINE
 * <p>
 * A group of business terms providing information on individual order line.
 * <p>
 * Similar to EN16931 business group BG-25
 */
public class OrderItem extends ORDERITEM implements OrderLine, IQuantity {

	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount,
			IAmount priceAmount, String itemName) {
//		return create(this.order, id, (Quantity)quantity, (Amount)lineTotalAmount, (UnitPriceAmount)priceAmount, itemName);
		return create(this.order, id
				, quantity.getUnitCode(), quantity.getValue()
				, (Amount)lineTotalAmount, (UnitPriceAmount)priceAmount, itemName);
	}

	static OrderItem create(CoreOrder order, String id
			, String unitCode, BigDecimal quantity
			, Amount lineTotalAmount, 
			UnitPriceAmount priceAmount, String itemName) {
		OrderItem orderLine =  new OrderItem(id, unitCode, quantity, lineTotalAmount, priceAmount, itemName);
		orderLine.order = order;
		return orderLine;
	}

	// copy factory
	static OrderItem create(ORDERITEM object, CoreOrder order) {
		OrderItem res;
		if(object instanceof ORDERITEM && object.getClass()!=ORDERITEM.class) {
			// object is instance of a subclass of ORDERITEM, but not ORDERITEM itself
			res = (OrderItem)object;
		} else {
			res = new OrderItem(object); 
		}
		res.order = order;
		return res;
	}

	private static final Logger LOG = Logger.getLogger(OrderItem.class.getName());

	private CoreOrder order; // order this orderLine belongs to
	
	// copy ctor
	private OrderItem(ORDERITEM line) {
		super();
		if(line!=null) {
			CopyCtor.invokeCopy(this, line);
			LOG.fine("copy ctor:"+this);
		}
		this.order = null;
	}

	private OrderItem(String id
			, String unitCode, BigDecimal quantity // Quantity quantity
			, Amount lineTotalAmount, UnitPriceAmount priceAmount, String itemName) {
//		super.setAssociatedDocumentLineDocument(new DocumentLineDocumentType()); // mit id
//		super.setSpecifiedLineTradeAgreement(new LineTradeAgreementType()); // mit setUnitPriceAmount
//		super.setSpecifiedLineTradeDelivery(new LineTradeDeliveryType()); // mit quantity
//		super.setSpecifiedLineTradeSettlement(new LineTradeSettlementType());
// optional		super.setSpecifiedTradeProduct(new TradeProductType()); // mit ItemName
		setId(id);
		setQuantity(unitCode, quantity); // setQuantity(quantity);
		setLineTotalAmount(lineTotalAmount);
		setUnitPriceAmount(priceAmount);
		setItemName(itemName);
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

/*
		<ram:IncludedSupplyChainTradeLineItem>
			<ram:AssociatedDocumentLineDocument>
				<ram:LineID>1</ram:LineID>                                    <!-- BG.25.BT-126 1..1
				<ram:IncludedNote>
					<ram:Content>Content of Note</ram:Content>
					<ram:SubjectCode>AAI</ram:SubjectCode>
				</ram:IncludedNote>
			</ram:AssociatedDocumentLineDocument>
			<ram:SpecifiedTradeProduct>
				<ram:GlobalID schemeID="0160">1234567890123</ram:GlobalID>
				<ram:SellerAssignedID>987654321</ram:SellerAssignedID>
				<ram:BuyerAssignedID>654987321</ram:BuyerAssignedID>
				<ram:Name>Product Name</ram:Name>                             <!-- BG-31.BT-153 0..1
			</ram:SpecifiedTradeProduct>
			<ram:SpecifiedLineTradeAgreement>
				<ram:BuyerOrderReferencedDocument>
					<ram:LineID>1</ram:LineID>
				</ram:BuyerOrderReferencedDocument>
				<ram:NetPriceProductTradePrice>
					<ram:ChargeAmount>10.00</ram:ChargeAmount>
					<ram:BasisQuantity unitCode="C62">1.00</ram:BasisQuantity>
				</ram:NetPriceProductTradePrice>
				<ram:BlanketOrderReferencedDocument>
					<ram:LineID>2</ram:LineID>
				</ram:BlanketOrderReferencedDocument>
			</ram:SpecifiedLineTradeAgreement>
			<ram:SpecifiedLineTradeDelivery>
				<ram:PartialDeliveryAllowedIndicator>
					<udt:Indicator>true</udt:Indicator>
				</ram:PartialDeliveryAllowedIndicator>
				<ram:RequestedQuantity unitCode="C62">6</ram:RequestedQuantity> <!-- BT-129+BT-130 1..1
			</ram:SpecifiedLineTradeDelivery>
			<ram:SpecifiedLineTradeSettlement>
				<ram:SpecifiedTradeSettlementLineMonetarySummation>
					<ram:LineTotalAmount>60.00</ram:LineTotalAmount>            <!-- BT-131 1..1
				</ram:SpecifiedTradeSettlementLineMonetarySummation>
			</ram:SpecifiedLineTradeSettlement>
		</ram:IncludedSupplyChainTradeLineItem>
 */
	// BG.25.BT-126 1..1
	@Override
	public String getId() {
		return super.getLINEITEMID();
	}
	void setId(String id) {
		super.setLINEITEMID(id);
	}

/* TODO

Line status code : not in CIO
An Order Response (Document Typecode BT-3 = 231) MUST contain a Line Status Code

 */
	
	// BT-127 0..1/n Freitext zur Rechnungsposition : ram:IncludedNote
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
//		List<ReferencedDocumentType> rds = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getAdditionalReferencedDocument();
//		if(rds==null || rds.isEmpty()) return null;
//		// A Line MUST NOT HAVE more than 1 Object Identifier BT-128
//		ReferencedDocument rd = ReferencedDocument.create(rds.get(0));
//		return new ID(rd.getIssuerAssignedID().getValue(), rd.getReferenceCode());
	}
	
	// BT-129 ++ 1..1 bestellte Menge
	// BT-129+BT-130
	// Quantity ist in CIO , in OT zwei elemente
//	void setQuantity(Quantity quantity) { 
//	}
	void setQuantity(String unitCode, BigDecimal quantity) {
		super.setORDERUNIT(unitCode); // required
		super.setQUANTITY(quantity); // required		
	}
	@Override
	public IQuantity getQuantity() {
		return createQuantity(super.getORDERUNIT(), super.getQUANTITY());
	}

	/* BT-131 ++ 1..1 Nettobetrag der Rechnungsposition / PRICE_LINE_AMOUNT
	 * Höhe des Preises für die Positionszeile. 
	 * Der Wert ergibt sich im Regelfall aus der Multiplikation von PRICE_AMOUNT und QUANTITY, 
	 * muss aber explizit aufgeführt werden. 
	 * Der PRICE_LINE_AMOUNT kann sich auch aus PRICE_AMOUNT und PRICE_UNIT_VALUE ergeben, 
	 * wenn der Preis nicht an die Bestelleinheit gekoppelt ist. Siehe auch PRICE_BASE_FIX.
	 */
	void setLineTotalAmount(Amount amount) {
		super.setPRICELINEAMOUNT(amount.getValue());
	}

	@Override
	public IAmount getLineTotalAmount() {
		return super.getPRICELINEAMOUNT()==null ? null : Amount.create(getPRICELINEAMOUNT());
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
//		ReferencedDocumentType referencedDocument = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument();
//		return referencedDocument==null ? null : new ID(referencedDocument.getLineID()).getName();		
	}

	// BT-133 0..1 line Buyer accounting reference : ram:ReceivableSpecifiedTradeAccountingAccount
	public void setBuyerAccountingReference(String text) {
//		Mapper.newFieldInstance(getSpecifiedLineTradeSettlement(), "receivableSpecifiedTradeAccountingAccount", text);
//		Mapper.set(getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccount(), "id", text);
	}
	public String getBuyerAccountingReference() {
//		TradeAccountingAccountType taa = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccount();
//		return taa==null ? null : new ID(taa.getID()).getName();		
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
	 */
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// delegieren:
		return TradeAllowanceCharge.create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// delegieren:
		return TradeAllowanceCharge.create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}

	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		if(allowanceOrCharge==null) return; // optional
		super.getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceCharge().add((TradeAllowanceCharge)allowanceOrCharge);
	}

	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		List<TradeAllowanceChargeType> allowanceChargeList = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceCharge();
		List<AllowancesAndCharges> res = new ArrayList<AllowancesAndCharges>(allowanceChargeList.size());
		allowanceChargeList.forEach(allowanceOrCharge -> {
			res.add(TradeAllowanceCharge.create(allowanceOrCharge));
		});
		return res;
	}

	/*
	 * BG-29 1..1 PRICE DETAILS
	 * 
	 * BT-146 +++ 1..1      Item net price   ==> NetPriceProductTradePrice
	 * BT-149-0 + BT-150-0 UnitPriceQuantity ==> NetPriceProductTradePrice
	 */
	// BG-29.BT-146 1..1 Item net price aka UnitPriceAmount / PRODUCTPRICEFIX.priceamount
	@Override
	public IAmount getUnitPriceAmount() {
		return super.getPRODUCTPRICEFIX()==null ? null : Amount.create(getPRODUCTPRICEFIX().getPRICEAMOUNT());
	}

	// BT-146 1..1 Item net price + UnitPriceQuantity BT-149+BT-149-0 + BT-150-0 optional
//	@Override
//	public void setUnitPriceAmountAndQuantity(UnitPriceAmount unitPriceAmount, Quantity quantity) {
//		TradePriceType tradePrice = setUnitPriceAmount(unitPriceAmount);
//		if(quantity!=null) {
//			QuantityType qt = new QuantityType();
//			quantity.copyTo(qt);
//			tradePrice.setBasisQuantity(qt); 
//		}
//		super.getSpecifiedLineTradeAgreement().setNetPriceProductTradePrice(tradePrice);;
//	}	
	private void setUnitPriceAmount(UnitPriceAmount unitPriceAmount) {
		Mapper.set(getPRODUCTPRICEFIX(), "priceamount", unitPriceAmount);
	}

	// BG-29.BT-150 + BG-29.BT-149 0..1 / PRODUCTPRICEFIX.pricequantity
	@Override
	public IQuantity getUnitPriceQuantity() {
		pricequantity
		TradePriceType tradePrice = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice();
		return tradePrice==null ? null : Quantity.create(tradePrice.getBasisQuantity());
	}
	@Override
	public void setUnitPriceQuantity(IQuantity basisQuantity) {
		Mapper.set(getPRODUCTPRICEFIX(), "pricequantity", basisQuantity);
	}
	
	// BG-31 SpecifiedTradeProduct
	private static final String FIELD_specifiedTradeProduct = "specifiedTradeProduct";
	// BG-31.BT-153 1..1 SpecifiedTradeProduct.Name
	void setItemName(String text) {
		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, text);
		Mapper.set(getSpecifiedTradeProduct(), "name", text);
	}
	@Override
	public String getItemName() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return Text.create(super.getSpecifiedTradeProduct().getName()).getValue();
	}

	// BG-31.BT-154 0..1 Item description
	@Override
	public void setDescription(String text) {
		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, text);
		Mapper.set(getSpecifiedTradeProduct(), "description", text);
	}
	@Override
	public String getDescription() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return Text.create(super.getSpecifiedTradeProduct().getDescription()).getValue();
	}
	
	// BG-31.BT-155 0..1 SpecifiedTradeProduct.sellerAssignedID
	@Override
	public void setSellerAssignedID(String id) {
		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		Mapper.set(getSpecifiedTradeProduct(), "sellerAssignedID", id);
	}
	@Override
	public String getSellerAssignedID() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return new ID(super.getSpecifiedTradeProduct().getSellerAssignedID()).getContent();
	}

	// BG-31.BT-156 0..1 SpecifiedTradeProduct.buyerAssignedID
	@Override
	public void setBuyerAssignedID(String id) {
		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		Mapper.set(getSpecifiedTradeProduct(), "buyerAssignedID", id);		
	}
	@Override
	public String getBuyerAssignedID() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return new ID(super.getSpecifiedTradeProduct().getBuyerAssignedID()).getContent();
	}

	// BG-31.BT-157 0..n SpecifiedTradeProduct.GlobalID
	@Override
	public Identifier createStandardIdentifier(String globalID, String schemeID) {
		return new ID(globalID, schemeID);
	}
	@Override
	public void addStandardIdentifier(Identifier id) {
		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		super.getSpecifiedTradeProduct().getGlobalID().add((ID)id);
	}
	@Override
	public List<Identifier> getStandardIdentifier() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		List<IDType> list = getSpecifiedTradeProduct().getGlobalID();
		List<Identifier> result = new ArrayList<Identifier>(list.size());
		list.forEach(id -> {
			result.add(new ID(id));
		});
		return result;
	}

	// BG-31.BT-158 0..n Item classification identifier designatedProductClassification
	/* ProductClassificationType CodeType classCode (value, listID, listVersionID)
	                             TextType className

 Bsp. ORDER-X_EX01_ORDER_FULL_DATA-COMFORT.xml :
         ...
        <ram:DesignatedProductClassification>
        <ram:ClassCode listID="TST">Class_code</ram:ClassCode>
             <ram:ClassName>Name Class Codification</ram:ClassName> <!-- text zu "TST", TST nicht in UNTDID 7143
        </ram:DesignatedProductClassification>

realistisches Beispiel:
        <ram:DesignatedProductClassification>
        <ram:ClassCode listID="EN">4047247110051</ram:ClassCode>
             <ram:ClassName>EN==EAN==European Article Number</ram:ClassName>
        </ram:DesignatedProductClassification>

	 */
	@Override
	public IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID, String idText) {
		// ignore idText TODO
		return new Code(classCode, listID, listVersionID);
	}
	@Override
	public void addClassificationIdentifier(IdentifierExt id) {
		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		ProductClassificationType productClassificationType = new ProductClassificationType();
		productClassificationType.setClassCode((Code)id);
		super.getSpecifiedTradeProduct().getDesignatedProductClassification().add(productClassificationType);		
	}
	@Override
	public List<IdentifierExt> getClassifications() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		List<ProductClassificationType> list = getSpecifiedTradeProduct().getDesignatedProductClassification();
		List<IdentifierExt> result = new ArrayList<IdentifierExt>(list.size());
		list.forEach(producClass -> {
			IdentifierExt idExt= new Code(producClass.getClassCode());
			//idExt.setIdText(producClass.getClassName().getValue());
			result.add(idExt);
		});
		return result;
	}

	// BG-31.BT-159 0..1 Item country of origin
/*
                    <ram:OriginTradeCountry>
                         <ram:ID>FR</ram:ID>
                    </ram:OriginTradeCountry>
 */
	@Override
	public void setCountryOfOrigin(String code) {
		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, code);
		Mapper.newFieldInstance(getSpecifiedTradeProduct(), "originTradeCountry", code);		
		Mapper.set(getSpecifiedTradeProduct().getOriginTradeCountry(), "id", ISOTwoletterCountryCodeContentType.fromValue(code));
	}
	@Override
	public String getCountryOfOrigin() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return getSpecifiedTradeProduct().getOriginTradeCountry()==null ? null : getSpecifiedTradeProduct().getOriginTradeCountry().getID().getValue().value();
	}

	// --------------------------- CIO only:
	@Override
	public void setPartialDeliveryIndicator(boolean indicator) {
		Mapper.set(getSpecifiedLineTradeDelivery(), "partialDeliveryAllowedIndicator", indicator);		
	}
	@Override
	public boolean isPartialDeliveryAllowed() {
		IndicatorType indicator = super.getSpecifiedLineTradeDelivery().getPartialDeliveryAllowedIndicator();
		return indicator!=null && indicator.isIndicator().equals(YES);
	}

	// implements IQuantity:
	public static final int SCALE = 4;
	@Override
	public IQuantity createQuantity(String unitCode, BigDecimal quantity) {
		setQuantity(unitCode, quantity);
		return this;
	}

	@Override
	public BigDecimal getValue(RoundingMode roundingMode) {
		super.getQUANTITY().setScale(SCALE, roundingMode);
	}

	@Override
	public String getUnitCode() {
		super.getORDERUNIT();
	}

}
