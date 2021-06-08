package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.ORDERRESPONSEITEM;
import org.opentrans.xmlschema._2.PRODUCTFEATURES;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.impl.UnitPriceAmount;

/**
 * 33-342: BG-25 1..n ORDER LINE
 * <p>
 * A group of business terms providing information on individual order line.
 * <p>
 * Similar to EN16931 business group BG-25
 */
public class OrderResponseItem extends ORDERRESPONSEITEM implements DefaultOrderLine {

	private static final Logger LOG = Logger.getLogger(OrderResponseItem.class.getName());

	static OrderResponseItem create(String id, IQuantity quantity, IAmount lineTotalAmount, UnitPriceAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		return new OrderResponseItem(id, quantity, lineTotalAmount, priceAmount, itemName, taxCat, percent);
	}

	// copy factory
	static OrderResponseItem create(ORDERRESPONSEITEM object) {
		if(object instanceof ORDERRESPONSEITEM && object.getClass()!=ORDERRESPONSEITEM.class) {
			// object is instance of a subclass of ORDERRESPONSEITEM, but not ORDERRESPONSEITEM itself
			return (OrderResponseItem)object;
		} else {
			return new OrderResponseItem(object); 
		}
	}

	Productid productid;
	Productpricefix productpricefix;

	// copy ctor
	private OrderResponseItem(ORDERRESPONSEITEM line) {
		SCopyCtor.getInstance().invokeCopy(this, line);
		productid = Productid.create(super.getPRODUCTID());
		productpricefix = Productpricefix.create(super.getPRODUCTPRICEFIX());
		LOG.config("copy ctor:"+this);
	}

	private OrderResponseItem(String id
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
	void setId(String id) {
		super.setLINEITEMID(id);
	}
	@Override
	public String getId() {
		return super.getLINEITEMID();
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

	// 107: LINE TRADE AGREEMENT
	// 178: (Net Price)
	// 179: BG-29.BT-146 1..1 Item net price aka UnitPriceAmount / PRODUCTPRICEFIX.priceamount
	@Override
	public IAmount getUnitPriceAmount() {
		return productpricefix.getUnitPriceAmount();
	}
	private void setUnitPriceAmount(IAmount unitPriceAmount) {
		productpricefix.setUnitPriceAmount(unitPriceAmount);
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

}
