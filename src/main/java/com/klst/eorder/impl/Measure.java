package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IQuantity;
import com.klst.eorder.api.IMeasureFactory;

import un.unece.uncefact.data.standard.unqualifieddatatype._128.MeasureType;

/* Measure : „Core Data Types“ aus "ISO 15000-5" aka ebXML
 * <br>
 * A numeric value determined by measuring an object. 
 * Measures are specified with a unit of measure. 
 * <p>
 * The applicable unit of measure is taken from UN/ECE Rec. 20. 
 * <p>
 * [Note: This Representation Term shall also be used 
 * for measured coefficients (e.g. m/s).]
 */
/**
 * Measure that applies to the packaging dimension, f.i. width, length and height.
 * Example 1.50MTR
 * <p>
 * A "decimal" type with 2 digits maximum after the decimal point, 
 * without a thousand separator, and with the "." as a decimal separator. 
 * <br>The unit of measure that applies to the packaging dimension, f.i. "MTR"
 * 
 */
public class Measure extends MeasureType implements IQuantity, IMeasureFactory {

	@Override
	public IQuantity createQuantity(String unitCode, BigDecimal value) {
		return createMeasure(unitCode, value);
	}
	@Override
	public IQuantity createMeasure(String unitCode, BigDecimal value) {
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
		if(object==null) return null;
		if(object instanceof MeasureType && object.getClass()!=MeasureType.class) {
			// object is instance of a subclass of MeasureType, but not MeasureType itself
			return (Measure)object;
		} else {
			return new Measure(object); 
		}
	}

	// copy ctor
	private Measure(MeasureType object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	public Measure(String unitCode, BigDecimal value) {
		super();
		super.setUnitCode(unitCode);
		setvalue(value); // nicht super.setValue!
	}

	private void setvalue(BigDecimal value) {
		if(value==null) return;
		super.setValue(value.setScale(SCALE, RoundingMode.HALF_UP));
	}
	
	@Override
	public BigDecimal getValue(RoundingMode roundingMode) {
		return getValue()==null ? null : getValue().setScale(SCALE, roundingMode);
	}
	
	@Override
	public String toString() {
		return getValue()==null ? "" : getValue(RoundingMode.HALF_UP) 
				+ (getUnitCode()==null ? "" : getUnitCode());
	}

}
