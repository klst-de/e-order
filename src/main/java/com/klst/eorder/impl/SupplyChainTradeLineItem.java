package com.klst.eorder.impl;

import java.util.List;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.eorder.OrderNote;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.DocumentLineDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeDeliveryType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LineTradeSettlementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeLineItemType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradePriceType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeProductType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeSettlementLineMonetarySummationType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.AmountType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.QuantityType;

/**
 * BG-25 ORDER LINE
 * <p>
 * A group of business terms providing information on individual Order line.
 * <p>
 * Similar to EN16931 business group BG-25
 */
public class SupplyChainTradeLineItem extends SupplyChainTradeLineItemType {

//	static CoreOrderLine createOrderLine(String id, Quantity quantity, Amount lineTotalAmount, 
//			UnitPriceAmount priceAmount, String itemName, TaxCategoryCode codeEnum, BigDecimal percent) {
//		return new SupplyChainTradeLineItem(id, quantity, lineTotalAmount, priceAmount, itemName, codeEnum, percent);
//	}

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
//		if(specifiedLineTradeSettlement.getApplicableTradeTax().isEmpty()) {
//			tradeTax = null; //TradeTax.create();
//		} else {
//			tradeTax = TradeTax.create(specifiedLineTradeSettlement.getApplicableTradeTax().get(0));
//		}
	}

	private SupplyChainTradeLineItem(String id, Quantity quantity, Amount lineTotalAmount, UnitPriceAmount priceAmount, String itemName) {
//			, TaxCategoryCode codeEnum, BigDecimal percent) {
		super.setAssociatedDocumentLineDocument(new DocumentLineDocumentType()); // mit id
		super.setSpecifiedLineTradeAgreement(new LineTradeAgreementType()); // mit setUnitPriceAmount
		super.setSpecifiedTradeProduct(new TradeProductType()); // mit ItemName
//		associatedDocumentLineDocument = new DocumentLineDocumentType();
//		specifiedTradeProduct = new TradeProductType();
//		specifiedLineTradeAgreement = new LineTradeAgreementType(); 
//		specifiedLineTradeDelivery = new LineTradeDeliveryType(); // 0..1, aber BT-129 ist mandatory 
//		specifiedLineTradeSettlement = new LineTradeSettlementType();
//		
//		init(id, quantity, lineTotalAmount, priceAmount, itemName, codeEnum, percent);
		setId(id);
		setQuantity(quantity);
		setLineTotalAmount(lineTotalAmount);
		setUnitPriceAmount(priceAmount);
		setItemName(itemName);
//		setTaxCategoryAndRate(taxCode, taxRate);
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
				<ram:LineID>1</ram:LineID>
				<ram:IncludedNote>
					<ram:Content>Content of Note</ram:Content>
					<ram:SubjectCode>AAI</ram:SubjectCode>
				</ram:IncludedNote>
			</ram:AssociatedDocumentLineDocument>
			<ram:SpecifiedTradeProduct>
				<ram:GlobalID schemeID="0160">1234567890123</ram:GlobalID>
				<ram:SellerAssignedID>987654321</ram:SellerAssignedID>
				<ram:BuyerAssignedID>654987321</ram:BuyerAssignedID>
				<ram:Name>Product Name</ram:Name>
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
				<ram:RequestedQuantity unitCode="C62">6</ram:RequestedQuantity>
			</ram:SpecifiedLineTradeDelivery>
			<ram:SpecifiedLineTradeSettlement>
				<ram:SpecifiedTradeSettlementLineMonetarySummation>
					<ram:LineTotalAmount>60.00</ram:LineTotalAmount>
				</ram:SpecifiedTradeSettlementLineMonetarySummation>
			</ram:SpecifiedLineTradeSettlement>
		</ram:IncludedSupplyChainTradeLineItem>
 */
	// BG.25.BT-126
	public String getId() {
		return super.getAssociatedDocumentLineDocument().getLineID().getValue();
	}
	public void setId(String id) {		
		super.getAssociatedDocumentLineDocument().setLineID(new ID(id));
	}

	//ram:IncludedNote
//	@Override // BT-127 0..1 Freitext zur Rechnungsposition 
	// - also nur maximal 1 Note, dann brauch man keine factory und kein add, sondern nur set/get
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

//	@Override // getter
//	public List<OrderNote> getOrderNotes() {
	public String getNote() {
		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
		if(dld==null) return null;
		if(dld.getIncludedNote().isEmpty()) return null;
		List<OrderNote> list = Note.getNotes(dld.getIncludedNote());
		return list.get(0).getNote();
	}	

	// BT-128 ++ 0..1 Objektkennung
	
	// BT-129 ++ 1..1 in Rechnung gestellte Menge
	// BT-129+BT-130
	// ram:SpecifiedLineTradeDelivery + ram:RequestedQuantity
	void setQuantity(Quantity quantity) { 
		QuantityType qt = new QuantityType();
		quantity.copyTo(qt);
		if(super.getSpecifiedLineTradeDelivery()==null) super.setSpecifiedLineTradeDelivery(new LineTradeDeliveryType());
		super.getSpecifiedLineTradeDelivery().setRequestedQuantity(qt);
	}

//	@Override
	public Quantity getQuantity() {
		QuantityType requestedQuantity = super.getSpecifiedLineTradeDelivery()==null ? null : getSpecifiedLineTradeDelivery().getRequestedQuantity();
		return requestedQuantity==null ? null : new Quantity(requestedQuantity.getUnitCode(), requestedQuantity.getValue());
	}

	// BT-131 ++ 1..1 Nettobetrag der Rechnungsposition
	// ram:SpecifiedLineTradeSettlement + ram:SpecifiedTradeSettlementLineMonetarySummation
	void setLineTotalAmount(Amount amount) {
		TradeSettlementLineMonetarySummationType tradeSettlementLineMonetarySummation = new TradeSettlementLineMonetarySummationType();
		AmountType lineTotalAmt = new AmountType();
		amount.copyTo(lineTotalAmt);
		tradeSettlementLineMonetarySummation.setLineTotalAmount(lineTotalAmt);
		if(super.getSpecifiedLineTradeSettlement()==null) super.setSpecifiedLineTradeSettlement(new LineTradeSettlementType());
		getSpecifiedLineTradeSettlement().setSpecifiedTradeSettlementLineMonetarySummation(tradeSettlementLineMonetarySummation);
	}

//	@Override
	public Amount getLineTotalAmount() {
		TradeSettlementLineMonetarySummationType tsms = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation();
		AmountType amount = tsms==null ? null : tsms.getLineTotalAmount();
		return amount==null ? null : new Amount(amount.getCurrencyID(), amount.getValue());
	}

	/*
	 * BG-29 1..1 PRICE DETAILS
	 * 
	 * BT-146 +++ 1..1      Item net price   ==> NetPriceProductTradePrice
	 * BT-149-0 + BT-150-0 UnitPriceQuantity ==> NetPriceProductTradePrice
	 */
	// BG-29.BT-146 1..1 Item net price aka UnitPriceAmount
//	@Override
	public UnitPriceAmount getUnitPriceAmount() {
		TradePriceType tradePrice = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice();
		AmountType upa = tradePrice==null ? null : tradePrice.getChargeAmount();
		return upa==null ? null : new UnitPriceAmount(upa.getCurrencyID(), upa.getValue());
	}

	// 1..1 Item net price + UnitPriceQuantity BT-149+BT-149-0 + BT-150-0 optional
//	@Override
	public void setUnitPriceAmountAndQuantity(UnitPriceAmount unitPriceAmount, Quantity quantity) {
		TradePriceType tradePrice = setUnitPriceAmount(unitPriceAmount);
		if(quantity!=null) {
			QuantityType qt = new QuantityType();
			quantity.copyTo(qt);
			tradePrice.setBasisQuantity(qt); 
		}
		super.getSpecifiedLineTradeAgreement().setNetPriceProductTradePrice(tradePrice);;
	}	
	private TradePriceType setUnitPriceAmount(UnitPriceAmount unitPriceAmount) {
		AmountType chargeAmount = new AmountType();
		unitPriceAmount.copyTo(chargeAmount);
		TradePriceType tradePrice = new TradePriceType();
		tradePrice.setChargeAmount(chargeAmount);

		super.getSpecifiedLineTradeAgreement().setNetPriceProductTradePrice(tradePrice);;
		return tradePrice;
	}

	// BG-31 SpecifiedTradeProduct
	// BG-31.BT-153 1..1 SpecifiedTradeProduct.Name
	void setItemName(String text) {
		super.getSpecifiedTradeProduct().setName(Text.create(text));;
	}

//	@Override
	public String getItemName() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return Text.create(super.getSpecifiedTradeProduct().getName()).getValue();
	}

}
