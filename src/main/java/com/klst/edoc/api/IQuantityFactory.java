package com.klst.edoc.api;

import java.math.BigDecimal;

/**
 * abstract-factory, aka Kit for IAmount
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface IQuantityFactory {
	
	/**
	 * Quantity (used in BT-129 1..1 Invoiced/Ordered quantity f.i.)
	 * 
	 * @param unitCode - the unit of measure that applies to the quantity
	 * @param quantity - the quantity of items (goods or services) that is ordered in the line
	 * 
	 * @return Quantity object
	 * 
	 * @see IQuantity
	 */
	public IQuantity createQuantity(String unitCode, BigDecimal quantity);
	
}
