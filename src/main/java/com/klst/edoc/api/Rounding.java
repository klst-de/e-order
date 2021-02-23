package com.klst.edoc.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface Rounding {

	public BigDecimal getValue(RoundingMode roundingMode);
	
}
