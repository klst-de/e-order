package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.bmecat.bmecat._2005.COSTCATEGORYID;
import org.opentrans.xmlschema._2.FEATURE;
import org.opentrans.xmlschema._2.ORDERITEM;

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
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.openTrans.reflection.Mapper;

/*                         BG-25 + 1..n INVOICE LINE
    "lineitemid",                BT-126 ++ 1..1 Invoice line identifier
 PRODUCTID "productid" required, BG-31               ?BT-128
	    "supplierpid",           BG-31.BT-155 0..1 Artikelkennung des Verkäufers
	    "supplieridref",
	    "configcodefix",
	    "lotnumber",
	    "serialnumber",
	    "internationalpid",      BG-31.BT-157 0..1 Kennung eines Artikels nach registriertem Schema
	    "buyerpid",              BG-31.BT-156 0..1 Artikelkennung des Käufers
	    "descriptionshort",      BG-31.BT-153      ItemName
	    "descriptionlong",       BG-31.BT-154 0..1 Item description
	    "manufacturerinfo",
	    "producttype"
    "productfeatures",
    "productcomponents",
    "quantity",                  BT-129 ++ 1..1 Invoiced quantity
    "orderunit",                 BT-130 ++ 1..1 Invoiced quantity unit of measure code
 PRODUCTPRICEFIX "productpricefix",
    "pricelineamount",           BT-131 ++ 1..1 Invoice line net amount
    "partialshipmentallowed",
    "deliverydate",              BG-26 ++ 0..1 LINE PERIOD mit BG-26.BT-134 + BG-26.BT-135
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

BT-128 ++ 0..1 Invoice line object identifier
          0..1 Scheme identifier

BT-132 ++ 0..1 Referenced purchase order line reference
BT-133 ++ 0..1 Invoice line Buyer accounting reference
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
erl  BT-153 +++ 1..1 Artikelname
erl  BT-154 +++ 0..1 Artikelbeschreibung
erl  BT-155 +++ 0..1 Artikelkennung des Verkäufers
erl  BT-156 +++ 0..1 Artikelkennung des Käufers
erl  BT-157 +++ 0..1 Kennung eines Artikels nach registriertem Schema
     BT-158 +++ 0..n Kennung der Artikelklassifizierung
     BT-159 +++ 0..1 Artikelherkunftsland
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
public class OrderItem extends ORDERITEM implements OrderLine {

	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount, String itemName) {
		return create(this.order, id, (Quantity)quantity, (Amount)lineTotalAmount, (UnitPriceAmount)priceAmount, itemName);
	}

	static OrderItem create(CoreOrder order, String id, Quantity quantity, Amount lineTotalAmount,UnitPriceAmount priceAmount, String itemName) {
		OrderItem orderLine =  new OrderItem(id, quantity, lineTotalAmount, priceAmount, itemName);
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
//	OrderHeader orderHeader;
	Productid productid;
	Productpricefix productpricefix;
	
	// copy ctor
	private OrderItem(ORDERITEM line) {
		super();
		if(line!=null) {
			SCopyCtor.getInstance().invokeCopy(this, line);
		}
		this.order = null;
		productid = Productid.create(super.getPRODUCTID());
		productpricefix = Productpricefix.create(super.getPRODUCTPRICEFIX());
		LOG.config("copy ctor:"+this);
	}

	private OrderItem(String id
			, Quantity quantity
			, Amount lineTotalAmount, UnitPriceAmount priceAmount, String itemName) {
//		super.setAssociatedDocumentLineDocument(new DocumentLineDocumentType()); // mit id
//		super.setSpecifiedLineTradeAgreement(new LineTradeAgreementType()); // mit setUnitPriceAmount
//		super.setSpecifiedLineTradeDelivery(new LineTradeDeliveryType()); // mit quantity
//		super.setSpecifiedLineTradeSettlement(new LineTradeSettlementType());
// optional		super.setSpecifiedTradeProduct(new TradeProductType()); // mit ItemName
		productid = Productid.create();
		super.setPRODUCTID(productid);
		
		setId(id);
		setQuantity(quantity);
		setLineTotalAmount(lineTotalAmount);

		productpricefix = Productpricefix.create();
		super.setPRODUCTPRICEFIX(productpricefix);
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
		return null;
//		List<ReferencedDocumentType> rds = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getAdditionalReferencedDocument();
//		if(rds==null || rds.isEmpty()) return null;
//		// A Line MUST NOT HAVE more than 1 Object Identifier BT-128
//		ReferencedDocument rd = ReferencedDocument.create(rds.get(0));
//		return new ID(rd.getIssuerAssignedID().getValue(), rd.getReferenceCode());
	}
	
	// BT-129 ++ 1..1 bestellte Menge
	// BT-129+BT-130
	void setQuantity(Quantity quantity) { 
		super.setORDERUNIT(quantity.getUnitCode()); // required
		super.setQUANTITY(quantity.getValue()); // required		
	}
	@Override
	public IQuantity getQuantity() {
		return Quantity.create(super.getORDERUNIT(), super.getQUANTITY());
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
	@Override // factory
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return DeliveryDate.create(start, end);
	}	
	@Override
	public void setLineDeliveryPeriod(IPeriod period) {
		super.setDELIVERYDATE((DeliveryDate)period);
	}
	@Override
	public void setLineDeliveryDate(Timestamp timestamp) {
		setLineDeliveryPeriod(DeliveryDate.create(timestamp, timestamp));
	}
	@Override
	public Timestamp getLineDeliveryDateAsTimestamp() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return DateTimeFormats.dtDATETIMEToTs(getDELIVERYDATE().getDELIVERYSTARTDATE()); 
		}
		return null;
	}
	@Override
	public IPeriod getLineDeliveryPeriod() {
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

	/*
	 * BG-29 1..1 PRICE DETAILS
	 * 
	 * BT-146 +++ 1..1      Item net price   ==> NetPriceProductTradePrice
	 * BT-149-0 + BT-150-0 UnitPriceQuantity ==> NetPriceProductTradePrice
	 */
	// BG-29.BT-146 1..1 Item net price aka UnitPriceAmount / PRODUCTPRICEFIX.priceamount
	@Override
	public IAmount getUnitPriceAmount() {
//		return super.getPRODUCTPRICEFIX()==null ? null : Amount.create(getPRODUCTPRICEFIX().getPRICEAMOUNT());
		// delegieren:
		return productpricefix.getUnitPriceAmount();
	}
	private void setUnitPriceAmount(UnitPriceAmount unitPriceAmount) {
//		Mapper.set(getPRODUCTPRICEFIX(), "priceamount", unitPriceAmount);
		productpricefix.setUnitPriceAmount(unitPriceAmount);
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
	
	// BG-29.BT-147 0..1 PriceDiscount
	@Override
	public IAmount getPriceDiscount() {
		// delegieren:
		return productpricefix.getPriceDiscount();
	}
	@Override
	public void setPriceDiscount(IAmount discount) {
		productpricefix.setPriceDiscount(discount);
	}

	// BG-29.BT-148 0..1 
	@Override
	public IAmount getGrossPrice() {
		// delegieren:
		return productpricefix.getGrossPrice();
	}
	@Override
	public void setGrossPrice(IAmount grossPrice) {
		productpricefix.setGrossPrice(grossPrice);
	}

	// BG-29.BT-150 + BG-29.BT-149 0..1 / PRODUCTPRICEFIX.pricequantity
	@Override
	public IQuantity getUnitPriceQuantity() {
		if(super.getPRODUCTPRICEFIX()==null) return null;
//		return getPRODUCTPRICEFIX().getPRICEQUANTITY()==null ? null : Quantity.create(getPRODUCTPRICEFIX().getPRICEQUANTITY());
		// delegieren:
		return productpricefix.getUnitPriceQuantity();
	}
	@Override
	public void setUnitPriceQuantity(IQuantity basisQuantity) {
		Mapper.set(getPRODUCTPRICEFIX(), "pricequantity", basisQuantity);
	}

	// BG-30.BT-151 1..1 item VAT category code
	@Override
	public void setTaxCategory(TaxCategoryCode codeEnum) {
		productpricefix.setTaxCategory(codeEnum);
	}
	@Override
	public TaxCategoryCode getTaxCategory() {
		// delegieren:
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

	// BG-31 ITEM INFORMATION : SpecifiedTradeProduct ==============> OT: productid
	// BG-31.BT-153 1..1 SpecifiedTradeProduct.Name
	void setItemName(String text) {
		productid.setItemName(text);
	}
	@Override
	public String getItemName() {
		return productid.getItemName();
	}

	// BG-31.BT-154 0..1 Item description
	@Override
	public void setDescription(String text) {
		productid.setDescription(text);
	}
	@Override
	public String getDescription() {
		return productid.getDescription();
	}
	
	// BG-31.BT-155 0..1 Item Seller's identifier, productid.supplierpid
	@Override
	public void setSellerAssignedID(String id) {
		productid.setSellerAssignedID(id);
	}
	@Override
	public String getSellerAssignedID() {
		return productid.getSellerAssignedID();
	}

	// BG-31.BT-156 0..1 Item Buyer's identifier, productid.buyerpid (1..n)
	@Override
	public void setBuyerAssignedID(String id) {
		productid.setBuyerAssignedID(id);
	}
	@Override
	public String getBuyerAssignedID() {
		return productid.getBuyerAssignedID();
	}

	// BG-31.BT-157 0..n SpecifiedTradeProduct.GlobalID
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
//		// ignore idText TODO
//		return new Code(classCode, listID, listVersionID);
	}
	@Override
	public void addClassificationIdentifier(IdentifierExt id) {
//		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, id);
//		ProductClassificationType productClassificationType = new ProductClassificationType();
//		productClassificationType.setClassCode((Code)id);
//		super.getSpecifiedTradeProduct().getDesignatedProductClassification().add(productClassificationType);		
	}
	@Override
	public List<IdentifierExt> getClassifications() {
		return null;
//		if(super.getSpecifiedTradeProduct()==null) return null;
//		List<ProductClassificationType> list = getSpecifiedTradeProduct().getDesignatedProductClassification();
//		List<IdentifierExt> result = new ArrayList<IdentifierExt>(list.size());
//		list.forEach(producClass -> {
//			IdentifierExt idExt= new Code(producClass.getClassCode());
//			//idExt.setIdText(producClass.getClassName().getValue());
//			result.add(idExt);
//		});
//		return result;
	}

	// BG-31.BT-159 0..1 Item country of origin / nicht in opentrans, ===> "countryoforigin" als ARTIKELATTRIBUTE
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

	// BG-32 0..n ITEM ATTRIBUTES with BT-160 name and BT-161 value
	@Override
	public void addItemAttribute(String name, String value) {
		// TODO Auto-generated method stub
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

	// --------------------------- CIO only:
	@Override
	public void setPartialDeliveryIndicator(boolean indicator) {
//		super.setPARTIALSHIPMENTALLOWED(String    TRUE ); TODO
//		Mapper.set(getSpecifiedLineTradeDelivery(), "partialDeliveryAllowedIndicator", indicator);		
	}
	@Override
	public boolean isPartialDeliveryAllowed() {
		String indicator = super.getPARTIALSHIPMENTALLOWED();
		return indicator!=null && indicator.equals("TRUE");
	}

}
