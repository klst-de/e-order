package com.klst.edoc.api;

import java.math.BigDecimal;

/**
 * abstract-factory, aka Kit for IAmount
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface IAmountFactory {
	
	/**
	 * monatary Amount (used in BT-106/Sum of Invoice line net amount or BT-146/Unit price amount f.i.)
	 * 
	 * @param currencyID - the (optional) currency of the amount is defined as a separate business term. 
	 * @param amount - a "decimal" type (with 2 or 4 digits after the decimal point f.i.)
	 * 
	 * @return Amount object
	 * 
	 * @see IAmount
	 */
	public IAmount createAmount(String currencyID, BigDecimal amount);
	
	default IAmount createAmount(BigDecimal amount) {
		return createAmount((String)null, amount);
	}

}
