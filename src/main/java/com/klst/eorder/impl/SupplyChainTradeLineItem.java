package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.ebXml.reflection.Mapper;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.DocumentLineDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeDeliveryType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeSettlementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ProductClassificationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ReferencedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeLineItemType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeAccountingAccountType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradePriceType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeSettlementLineMonetarySummationType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.CodeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IndicatorType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.TextType;
import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

/*
BG-25 + 1..n INVOICE LINE
BT-126 ++ 1..1 Invoice line identifier
BT-127 ++ 0..1 Invoice line note
BT-128 ++ 0..1 Invoice line object identifier
          0..1 Scheme identifier
BT-129 ++ 1..1 Invoiced quantity
BT-130 ++ 1..1 Invoiced quantity unit of measure code
BT-131 ++ 1..1 Invoice line net amount
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
public class SupplyChainTradeLineItem extends SupplyChainTradeLineItemType implements OrderLine {

	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount,
			IAmount priceAmount, String itemName) {
		return create(id, (Quantity)quantity, (Amount)lineTotalAmount, (UnitPriceAmount)priceAmount, itemName);
	}

	static SupplyChainTradeLineItem create(String id, Quantity quantity, Amount lineTotalAmount, 
			UnitPriceAmount priceAmount, String itemName) {
		return new SupplyChainTradeLineItem(id, quantity, lineTotalAmount, priceAmount, itemName);
	}

	// copy factory
	static SupplyChainTradeLineItem create(SupplyChainTradeLineItemType object) {
		if(object instanceof SupplyChainTradeLineItemType && object.getClass()!=SupplyChainTradeLineItemType.class) {
			// object is instance of a subclass of SupplyChainTradeLineItemType, but not SupplyChainTradeLineItemType itself
			return (SupplyChainTradeLineItem)object;
		} else {
			return new SupplyChainTradeLineItem(object); 
		}
	}

	private static final Logger LOG = Logger.getLogger(SupplyChainTradeLineItem.class.getName());

	// copy ctor
	private SupplyChainTradeLineItem(SupplyChainTradeLineItemType line) {
		super();
		if(line!=null) {
			CopyCtor.invokeCopy(this, line);
			LOG.fine("copy ctor:"+this);
		}
	}

	private SupplyChainTradeLineItem(String id, Quantity quantity, Amount lineTotalAmount, UnitPriceAmount priceAmount, String itemName) {
		super.setAssociatedDocumentLineDocument(new DocumentLineDocumentType()); // mit id
		super.setSpecifiedLineTradeAgreement(new LineTradeAgreementType()); // mit setUnitPriceAmount
		super.setSpecifiedLineTradeDelivery(new LineTradeDeliveryType()); // mit quantity
		super.setSpecifiedLineTradeSettlement(new LineTradeSettlementType());
// optional		super.setSpecifiedTradeProduct(new TradeProductType()); // mit ItemName
		setId(id);
		setQuantity(quantity);
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
		return super.getAssociatedDocumentLineDocument().getLineID().getValue();
	}
	void setId(String id) {
//		super.getAssociatedDocumentLineDocument().setLineID(new ID(id));
		Mapper.newFieldInstance(this, "associatedDocumentLineDocument", id);
		Mapper.set(getAssociatedDocumentLineDocument(), "lineID", id);
	}

/* TODO

Line status code : not in CIO
An Order Response (Document Typecode BT-3 = 231) MUST contain a Line Status Code

 */
	
	// BT-127 0..1/n Freitext zur Rechnungsposition : ram:IncludedNote
	@Override // getter
	public List<OrderNote> getNotes() {
		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
		if(dld==null) return null;
		// delegieren:
		return Note.getNotes(dld.getIncludedNote());
	}

	@Override
	public OrderNote createNote(String subjectCode, String content) {
		return Note.create(subjectCode, content);
	}
	@Override
	public void addNote(OrderNote note) {
		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
		dld.getIncludedNote().add((Note)note);
	}
	
	// BT-128 ++ 0..1 Objektkennung // (OBJECT IDENTIFIER FOR INVOICE LINE) Zeile 154
	public void setLineObjectID(String id, String schemeID, String schemeCode) {
//		/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeAgreement
//		/ram:AdditionalReferencedDocument
		LineTradeAgreementType lta = super.getSpecifiedLineTradeAgreement(); // TODO wie in CrossIndustryOrder
		// in BASIC gibt es kein ram:AdditionalReferencedDocument
		lta.getAdditionalReferencedDocument();
	}
	
	// BT-129 ++ 1..1 bestellte Menge
	// BT-129+BT-130
	void setQuantity(Quantity quantity) { 
		Mapper.set(getSpecifiedLineTradeDelivery(), "requestedQuantity", quantity);
	}
	@Override
	public IQuantity getQuantity() {
		LineTradeDeliveryType ltd = super.getSpecifiedLineTradeDelivery();
		return ltd.getRequestedQuantity()==null ?  null : Quantity.create(ltd.getRequestedQuantity());
	}

	// BT-131 ++ 1..1 Nettobetrag der Rechnungsposition
	void setLineTotalAmount(Amount amount) {
		Mapper.newFieldInstance(getSpecifiedLineTradeSettlement(), "specifiedTradeSettlementLineMonetarySummation", amount);
		Mapper.set(getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation(), "lineTotalAmount", amount);
	}

	@Override
	public IAmount getLineTotalAmount() {
		TradeSettlementLineMonetarySummationType tsms = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation();
		return tsms==null ? null : Amount.create(tsms.getLineTotalAmount());
	}

	// BT-132 ++ 0..1 Referenced purchase order line reference
	public void setOrderLineID(String id) {
		if(id==null) return;
		// wie komme ich an DocumentCode??? TODO
//		if(getDocumentCode()==DocumentNameCode.Order) {
//			LOG.warning("An Order (Document Type Code BT-3 = 220) MUST NOT contain a Previous Order Change Referenced Document");
//		}
		Mapper.newFieldInstance(getSpecifiedLineTradeAgreement(), "buyerOrderReferencedDocument", id);
		Mapper.set(getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument(), "lineID", id);
	}
	public String getOrderLineID() {
		ReferencedDocumentType referencedDocument = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument();
		return referencedDocument==null ? null : new ID(referencedDocument.getLineID()).getName();		
	}

	// BT-133 0..1 line Buyer accounting reference : ram:ReceivableSpecifiedTradeAccountingAccount
	public void setBuyerAccountingReference(String text) {
		Mapper.newFieldInstance(getSpecifiedLineTradeSettlement(), "receivableSpecifiedTradeAccountingAccount", text);
		Mapper.set(getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccount(), "id", text);
	}
	public String getBuyerAccountingReference() {
		TradeAccountingAccountType taa = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccount();
		return taa==null ? null : new ID(taa.getID()).getName();		
	}

	/*
	 * BG-29 1..1 PRICE DETAILS
	 * 
	 * BT-146 +++ 1..1      Item net price   ==> NetPriceProductTradePrice
	 * BT-149-0 + BT-150-0 UnitPriceQuantity ==> NetPriceProductTradePrice
	 */
	// BG-29.BT-146 1..1 Item net price aka UnitPriceAmount
	@Override
	public IAmount getUnitPriceAmount() {
		TradePriceType tradePrice = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice();
		return tradePrice==null ? null : Amount.create(tradePrice.getChargeAmount());
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
		Mapper.newFieldInstance(getSpecifiedLineTradeAgreement(), "netPriceProductTradePrice", unitPriceAmount);
		Mapper.set(getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice(), "chargeAmount", unitPriceAmount);
	}

	// BG-29.BT-150 + BG-29.BT-149 0..1
	@Override
	public IQuantity getUnitPriceQuantity() {
		TradePriceType tradePrice = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice();
		return tradePrice==null ? null : Quantity.create(tradePrice.getBasisQuantity());
	}
	@Override
	public void setUnitPriceQuantity(IQuantity basisQuantity) {
		Mapper.newFieldInstance(getSpecifiedLineTradeAgreement(), "netPriceProductTradePrice", basisQuantity);
		Mapper.set(getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice(), "basisQuantity", basisQuantity);
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

}
