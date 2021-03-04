package com.klst.eorder.api;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;

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
	 * @param priceAmount     : BT-146, item net price (mandatory part in BG-29 PRICE DETAILS)
	 * @param itemName        : BT-153, a name for an item (mandatory part in BG-31 ITEM INFORMATION)
	 * 
	 * @return line object
	 */
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, 
			IAmount priceAmount, String itemName);

}
