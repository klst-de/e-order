package com.klst.eorder.impl;

import java.util.List;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.ebXml.reflection.Mapper;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.DocumentLineDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeDeliveryType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeSettlementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ReferencedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeLineItemType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradePriceType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeSettlementLineMonetarySummationType;

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

	//ram:IncludedNote
//	@Override // BT-127 0..1 Freitext zur Rechnungsposition 
	// - also nur maximal 1 Note, dann braucht man keine factory und kein add, sondern nur set/get
	// - und ohne subjectCode
	public void setNote(String text) {
		if(text==null) return; // optional
		Note note = Note.create(text);
		if(super.getAssociatedDocumentLineDocument().getIncludedNote().isEmpty()) {
			getAssociatedDocumentLineDocument().getIncludedNote().add(note);
		} else {
			getAssociatedDocumentLineDocument().getIncludedNote().set(0, note);
		}
	}

	@Override // getter
	public List<OrderNote> getNotes() {
		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
		if(dld==null) return null;
		// delegieren:
		return Note.getNotes(dld.getIncludedNote());
	}
//	public String getNote() {
//		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
//		if(dld==null) return null;
//		if(dld.getIncludedNote().isEmpty()) return null;
//		List<OrderNote> list = Note.getNotes(dld.getIncludedNote());
//		return list.get(0).getNote();
//	}	

	@Override
	public OrderNote createNote(String subjectCode, String content) {
		return Note.create(subjectCode, content);
	}

	public void addNote(OrderNote note) {
		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
		dld.getIncludedNote().add((Note)note);
	}
	
	// BT-128 ++ 0..1 Objektkennung // (OBJECT IDENTIFIER FOR INVOICE LINE) Zeile 154
	public void setLineObjectID(String id, String schemeID, String schemeCode) {
//		/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeAgreement
//		/ram:AdditionalReferencedDocument
		LineTradeAgreementType lta = super.getSpecifiedLineTradeAgreement(); // TODO wie in CrossIndustryOrder
		// in lta gibt es kein ram:AdditionalReferencedDocument
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

//	/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/
//	ram:SpecifiedLineTradeSettlement/ram:ReceivableSpecifiedTradeAccountingAccount/ram:ID
//	public void setBuyerAccountingReference(String text) {
//		Mapper.newFieldInstance(getSpecifiedLineTradeSettlement(), "buyerOrderReferencedDocument", id);
////		ram:ReceivableSpecifiedTradeAccountingAccount existiert nicht
//		Mapper.set(getSpecifiedLineTradeSettlement().getBuyerOrderReferencedDocument(), "lineID", id);
//	}
//	public String getBuyerAccountingReference();

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
	// BG-31.BT-153 1..1 SpecifiedTradeProduct.Name
	void setItemName(String text) {
		Mapper.newFieldInstance(this, "specifiedTradeProduct", text);
		Mapper.set(getSpecifiedTradeProduct(), "name", text);
	}
	@Override
	public String getItemName() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return Text.create(super.getSpecifiedTradeProduct().getName()).getValue();
	}

}
