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
	 * Item price discount aka Allowance
	 * <p>
	 * {@link AllowancesAndCharges#getAmountWithoutTax()} is the total discount subtracted from the Item gross price to calculate the Item net price. 
	 * Only applies if the discount is provided per unit and if it is not included in the Item gross price.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-147
	 * <br>Order-X-No: 	162
	 * 
	 * @return {@link AllowancesAndCharges}
	 */
	public AllowancesAndCharges getPriceDiscount();
	public void setPriceDiscount(AllowancesAndCharges allowance);

	/**
	 * Item price charge
	 * <p>
	 * Only applies if the charge is provided per unit and if it is not included in the Item gross price, for instance for WEEE tax.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	170
	 * 
	 * @return {@link AllowancesAndCharges}
	 */
	public AllowancesAndCharges getPriceCharge();
	public void setPriceCharge(AllowancesAndCharges charge);
	
	/**
	 * Item gross price
	 * <p>
	 * The unit price, exclusive of VAT, before subtracting Item price discount.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-148
	 * <br>Order-X-No: 	159
	 * 
	 * @return UnitPriceAmount
	 */
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
	 * <br>Order-X-No:  180+181	
	 * 
	 * @return UnitPriceQuantity
	 * @see #getQuantity
	 */
	public IQuantity getUnitPriceQuantity();
	public void setUnitPriceQuantity(IQuantity quantity);

}
