package com.klst.eorder.api;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;

/**
 * PRICE DETAILS
 * <p>
 * A group of business terms providing information about the price applied for the goods and services
 * <p>
 * Cardinality: 	1..1
 * <br>EN16931-ID: 	BG-29
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG29_PriceDetails {

	/**
	 * Item net price, exclusive of VAT, after subtracting item price discount.
	 * <p>
	 * The unit net price has to be equal with the Item gross price less the Item price discount.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <br>EN16931-ID: 	BG-29.BT-146 
	 * 
	 * @return UnitPriceAmount
	 */
	public IAmount getUnitPriceAmount();
//	public void setUnitPriceAmount(IAmount priceAmount); // use OrderLineFactory

	/**
	 * Item price discount
	 * <p>
	 * The total discount subtracted from the Item gross price to calculate the Item net price. 
	 * Only applies if the discount is provided per unit and if it is not included in the Item gross price.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-147
	 * 
	 * @return UnitPriceAmount
	 */
	// ram:SpecifiedLineTradeAgreement/
/*	ram:GrossPriceProductTradePrice/ram:AppliedTradeAllowanceCharge/ram:ActualAmount existiert nicht in BASIC
 * aber in COMFORT:
                    <ram:GrossPriceProductTradePrice>
                         <ram:ChargeAmount>11.00</ram:ChargeAmount>
                         <ram:BasisQuantity unitCode="C62">1.00</ram:BasisQuantity>
                         <ram:AppliedTradeAllowanceCharge>
                              <ram:ChargeIndicator>
                                   <udt:Indicator>false</udt:Indicator> <!-- false bei discount
                              </ram:ChargeIndicator>
                              <ram:ActualAmount>1.00</ram:ActualAmount>
                         </ram:AppliedTradeAllowanceCharge>
                    </ram:GrossPriceProductTradePrice>

    protected AmountType chargeAmount;
    protected QuantityType basisQuantity;
    protected QuantityType minimumQuantity;
    protected QuantityType maximumQuantity;
    protected TradeAllowanceChargeType appliedTradeAllowanceCharge;
    protected List<TradeTaxType> includedTradeTax;

 */
	public IAmount getPriceDiscount();
	public void setPriceDiscount(IAmount grossPrice);

	/**
	 * Item gross price
	 * <p>
	 * The unit price, exclusive of VAT, before subtracting Item price discount.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-148
	 * 
	 * @return UnitPriceAmount
	 */
// ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:ChargeAmount existiert nicht nicht in BASIC
	public IAmount getGrossPrice();
	public void setGrossPrice(IAmount grossPrice);

	/**
	 * Item price unit (BT-150) and base quantity (BT-149)
	 * <p>
	 * The number of item units to which the price applies.
	 * <br>Item price base quantity unit of measure code. The unit of measure that applies to the Item price base quantity.
	 * The Item price base quantity unit of measure shall be the same as the ordered/invoiced quantity unit of measure (BT-130).
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-150 + BG-29.BT-149
	 * 
	 * @return UnitPriceQuantity
	 * @see #getQuantity
	 */
	public IQuantity getUnitPriceQuantity();
	public void setUnitPriceQuantity(IQuantity quantity);

}
