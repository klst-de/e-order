package com.klst.eorder.api;

import java.util.List;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;

/**
 * BG-25 ORDER LINE
 * <p>
 * A group of business terms providing information on individual order lines.
 * <p>
 * Similar to EN16931 business group BG-25
 */
public interface BG25_OrderLine extends OrderLineFactory {
	
	// getter
	public List<OrderLine> getLines();

	// factory methods
	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, 
			IAmount priceAmount, String itemName);

	// setter
	public void addLine(OrderLine line);
	default void addLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount, String itemName) {
		addLine(createOrderLine(id, quantity, lineTotalAmount, priceAmount, itemName));
	}
	
}
