package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IPeriod;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SpecifiedPeriodType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.DateTimeType;

public class Period extends SpecifiedPeriodType implements IPeriod {

	// factory
	@Override
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return create(start, end);
	}
	static Period create(Timestamp start, Timestamp end) {
		return new Period(start, end);
	}

	// copy factory
	static Period create(SpecifiedPeriodType object) {
		if(object instanceof SpecifiedPeriodType && object.getClass()!=SpecifiedPeriodType.class) {
			// object is instance of a subclass of SpecifiedPeriodType, but not SpecifiedPeriodType itself
			return (Period)object;
		} else {
			return new Period(object); 
		}
	}

	// copy ctor
	private Period(SpecifiedPeriodType object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	private Period(Timestamp start, Timestamp end) {
		setStartDate(start);
		setEndDate(end);
	}
	
	void setStartDate(Timestamp ts) {
		if(ts==null) return;
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		super.setStartDateTime(dateTime);
	}

	@Override
	public Timestamp getStartDateAsTimestamp() {
		return DateTimeFormatStrings.getDateAsTimestamp(super.getStartDateTime());
	}

	void setEndDate(Timestamp ts) {
		if(ts==null) return;
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		super.setEndDateTime(dateTime);
	}

	@Override
	public Timestamp getEndDateAsTimestamp() {
		return DateTimeFormatStrings.getDateAsTimestamp(super.getEndDateTime());
	}

}
