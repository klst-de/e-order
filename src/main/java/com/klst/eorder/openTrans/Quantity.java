package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.klst.edoc.api.IQuantity;

public class Quantity implements IQuantity {

	static Quantity create(String unitCode, BigDecimal quantity) {
		return new Quantity(unitCode, quantity);
	}

	public static final int SCALE = 4;

	private String unitCode;
	private BigDecimal quantity;

	private Quantity(String unitCode, BigDecimal quantity) {
		this.unitCode = unitCode;
		this.quantity = quantity;
	}

	// factory
	@Override
	public IQuantity createQuantity(String unitCode, BigDecimal quantity) {
		return create(unitCode, quantity);
	}

	@Override
	public BigDecimal getValue(RoundingMode roundingMode) {
		return quantity.setScale(SCALE, roundingMode);
	}

	@Override
	public String getUnitCode() {
		return unitCode;
	}

}
