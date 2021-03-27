package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.untdid.DateTimeFormats;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SpecifiedPeriodType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.DateTimeType;

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
		if(object!=null) {
			CopyCtor.invokeCopy(this, object);
		}
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
		DateTimeType dateTime = getStartDateTime();
		return dateTime==null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}

	void setEndDate(Timestamp ts) {
		if(ts==null) return;
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		super.setEndDateTime(dateTime);
	}

	@Override
	public Timestamp getEndDateAsTimestamp() {
		DateTimeType dateTime = super.getEndDateTime();
		return dateTime==null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}

}
