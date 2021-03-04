package com.klst.edoc.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

/* Mit diesem Datentyp wird die Mengenangabe zu einem Einzelposten abgebildet. 
 * 
 * Er basiert auf dem Typ „Quantity. Type“, wie in ISO 15000-5:2014 Anhang B definiert.
 * 
 * Hinweis: Der Mengenangabe wird über ein eigenständiges Informationselement eine Maßeinheit zugeordnet.
 * 
 */
/**
 * Quantity contains unit of measure and quantity of items (goods or services)
 * <p>
 * This is a "decimal" type with 4 digits maximum after the decimal point, without a thousand separator, and with the ". " as a decimal separator. 
 * Example 10000.3454
 * 
 * <br>The unit of measure that applies to the ordered or invoiced quantity.
 * The quantity of items (goods or services) that is charged in the Order/Invoice line.
 * 
 */
public interface IQuantity extends IQuantityFactory, Rounding {

	// implements Rounding
	public BigDecimal getValue(RoundingMode roundingMode);

	public String getUnitCode();
	
	default BigDecimal getValue() {
		return getValue(RoundingMode.HALF_UP);
	}

}
