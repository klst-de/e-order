package com.klst.eorder.api;

import java.math.BigDecimal;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.untdid.TaxCategoryCode;

/**
 * abstract-factory, aka Kit to create order lines
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface OrderLineFactory {

	/**
	 * factory method to create an order line, BG-25
	 * 
	 * @param id              : BT-126, a unique identifier for the individual line
	 * @param quantity        : BT-129+BT-130, UoM and quantity of items (goods or services) that is charged in the line
	 * @param lineTotalAmount : BT-131, the total amount of the line
	 * @param priceAmount     : BG-29.BT-146, item net price (mandatory part in BG-29 PRICE DETAILS)
	 * @param itemName        : BG-32.BT-153, a name for an item (mandatory part in BG-31 ITEM INFORMATION)
	 * @param codeEnum        : BG-30.BT-151, codeEnum 1..1 VAT category code
	 * @param percent         : BG-30.BT-152, percent  0..1 VAT rate
	 * 
	 * @return line object
	 */
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, 
			IAmount priceAmount, String itemName, TaxCategoryCode codeEnum, BigDecimal percent);

}
