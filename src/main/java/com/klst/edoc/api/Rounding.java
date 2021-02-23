package com.klst.eorder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface Rounding {

	public BigDecimal getValue(RoundingMode roundingMode);
	
}
