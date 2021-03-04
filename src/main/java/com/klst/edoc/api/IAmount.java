package com.klst.edoc.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

/* Mit diesem Datentyp wird ein Betrag in numerischer Form abgebildet. 
 * 
 * Er basiert auf dem Typ „Amount. Type“, wie in ISO 15000-5:2014 Anhang B definiert.
 * 
 * Hinweis: Dem Geldwert wird über ein eigenständiges Informationselement eine Währung zugeordnet.
 * 
 */
/**
 * Amount
 * 
 * This is a "decimal" type with 2 digits maximum after the decimal point, without a thousand separator, and with the ". " as a decimal separator. 
 * It can be supplemented by a "Currency" attribute, if different from the currency in the header. Example 10000.34
 * 
 * @see European standard EN 16931-1:2017 : 6.5.2 Amount. Type
 * 
 * An amount states a numerical monetary value. 
 * The currency of the amount is defined as a separate business term. 
 * 
 * This EN 16931_ Amount. Type4 is based on the Amount. Type as defined in ISO 15000-5:2014, Annex B. EN 16931_ Amount. 
 * Type is floating up to two fraction digits.
 *
 */
public interface IAmount extends IAmountFactory, Rounding {

	static final RoundingMode roundingMode = RoundingMode.HALF_UP;
	
	// implements Rounding
	public BigDecimal getValue(RoundingMode roundingMode);

	default BigDecimal getValue() {
		return getValue(roundingMode);
	}

}
