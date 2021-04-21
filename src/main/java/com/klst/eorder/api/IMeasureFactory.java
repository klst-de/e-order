package com.klst.eorder.api;

import java.math.BigDecimal;

import com.klst.edoc.api.IQuantity;

/**
 * abstract-factory, aka Kit for {@link com.klst.eorder.impl.Measure}
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface IMeasureFactory {
	
	/**
	 * Measure aka IQuantity (used in Packaging, Order-X-No: 	68), 
	 * {@link OrderLine#setPackaging(String, IQuantity, IQuantity, IQuantity)}
	 * 
	 * @param unitCode - the unit of measure that applies to the quantity
	 * @param quantity - the quantity of items (goods or services) that is ordered in the line
	 * 
	 * @return Measure object
	 * 
	 * @see com.klst.eorder.impl.Measure
	 * @see IQuantity
	 * @see OrderLine#getPackagingWidth()
	 */
	public IQuantity createMeasure(String unitCode, BigDecimal quantity);
	
}
