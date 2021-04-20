package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IQuantity;

import un.unece.uncefact.data.standard.unqualifieddatatype._128.MeasureType;

/**
 * Measure contains unit of measure and quantity for spatial dimensions.
 * <p>
 * This is a "decimal" type with 2 digits maximum after the decimal point, without a thousand separator, and with the ". " as a decimal separator. 
 * Example 1.50 "MTR"
 * 
 * <br>The unit of measure that applies to the packaging dimension, f.i.width, length and height.
 * 
 */
public class Measure extends MeasureType implements IQuantity {

	@Override
	public IQuantity createQuantity(String unitCode, BigDecimal value) {
		return create(unitCode, value);
	}

	static Measure create(String unitCode, BigDecimal value) {
		return new Measure(unitCode, value);
	}

	public static final int SCALE = 2;
	
	static Measure create() {
		return new Measure(null); 
	}
	// copy factory
	static Measure create(MeasureType object) {
		if(object instanceof MeasureType && object.getClass()!=MeasureType.class) {
			// object is instance of a subclass of MeasureType, but not MeasureType itself
			return (Measure)object;
		} else {
			return new Measure(object); 
		}
	}

	// copy ctor
	private Measure(MeasureType object) {
		super();
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	public Measure(String unitCode, BigDecimal value) {
		super();
		super.setUnitCode(unitCode);
		super.setValue(value.setScale(SCALE, RoundingMode.HALF_UP));
	}

	@Override
	public BigDecimal getValue(RoundingMode roundingMode) {
		return getValue().setScale(SCALE, roundingMode);
	}
	
	@Override
	public String toString() {
		return getValue(RoundingMode.HALF_UP) + (getUnitCode()==null ? "" : getUnitCode());
	}

}
