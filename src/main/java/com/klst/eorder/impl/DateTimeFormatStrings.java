package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.edoc.untdid.DateTimeFormats;

import un.unece.uncefact.data.standard.qualifieddatatype._128.FormattedDateTimeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.DateTimeType;

// in CII/CIO werden mehrere ähnliche Klassen verwendet, die Date(Time)String als inner Klasse haben
// - DateType.DateString
// - DateTimeType.DateTimeString
// - FormattedDateTimeType.DateTimeString
public class DateTimeFormatStrings extends DateTimeFormats {

	static Timestamp getDateAsTimestamp(DateTimeType dateTime) {
		if(dateTime==null) return null;
		String format = dateTime.getDateTimeString()==null ? null : dateTime.getDateTimeString().getFormat();
		return format.equals(DateTimeFormats.CCYYMMDDHHMM_QUALIFIER) ? 
			  DateTimeFormats.yyyyMMddhhmmToTs(dateTime.getDateTimeString().getValue()) 
			: DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}

	static DateTimeType toDateTime(Timestamp ts) {
		if(ts==null) return null;
		
		String ymdhm = DateTimeFormats.tsToCCYYMMDDHHMM(ts);
		if(ymdhm.endsWith("0000")) {
			DateTimeType.DateTimeString dts = new DateTimeType.DateTimeString();
			dts.setFormat(DateTimeFormats.CCYYMMDD_QUALIFIER);
			dts.setValue(DateTimeFormats.tsToCCYYMMDD(ts));
			
			DateTimeType dateTime = new DateTimeType();
			dateTime.setDateTimeString(dts);
			return dateTime;
		}
		// else CCYYMMDDHHMM
		DateTimeType.DateTimeString dts = new DateTimeType.DateTimeString();
		dts.setFormat(DateTimeFormats.CCYYMMDDHHMM_QUALIFIER);
		dts.setValue(ymdhm);
		
		DateTimeType dateTime = new DateTimeType();
		dateTime.setDateTimeString(dts);
		return dateTime;
	}

	static FormattedDateTimeType toFormattedDateTime(Timestamp ts) {
		if (ts == null) return null;

		FormattedDateTimeType.DateTimeString dts = new FormattedDateTimeType.DateTimeString();
		dts.setFormat(DateTimeFormats.CCYYMMDD_QUALIFIER);
		dts.setValue(DateTimeFormats.tsToCCYYMMDD(ts));
		
		FormattedDateTimeType fdt = new FormattedDateTimeType();
		fdt.setDateTimeString(dts);

		return fdt;
	}

}
