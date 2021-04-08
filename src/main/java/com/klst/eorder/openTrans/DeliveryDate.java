package com.klst.eorder.openTrans;

import java.sql.Timestamp;

import org.opentrans.xmlschema._2.DELIVERYDATE;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.untdid.DateTimeFormats;

public class DeliveryDate extends DELIVERYDATE implements IPeriod {

	// factory
	@Override
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return create(start, end);
	}
	static DeliveryDate create(Timestamp start, Timestamp end) {
		return new DeliveryDate(start, end);
	}

	// copy factory
	static DeliveryDate create(DELIVERYDATE object) {
		if(object instanceof DELIVERYDATE && object.getClass()!=DELIVERYDATE.class) {
			// object is instance of a subclass of DELIVERYDATE, but not DELIVERYDATE itself
			return (DeliveryDate)object;
		} else {
			return new DeliveryDate(object); 
		}
	}

	// copy ctor
	private DeliveryDate(DELIVERYDATE object) {
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	private DeliveryDate(Timestamp start, Timestamp end) {
		setStartDate(start);
		setEndDate(end);
	}
	
	void setStartDate(Timestamp timestamp) {
		if(timestamp==null) return;
		super.setDELIVERYSTARTDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}

	@Override
	public Timestamp getStartDateAsTimestamp() {
		return DateTimeFormats.dtDATETIMEToTs(super.getDELIVERYSTARTDATE());
	}

	void setEndDate(Timestamp timestamp) {
		if(timestamp==null) return;
		super.setDELIVERYENDDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}

	@Override
	public Timestamp getEndDateAsTimestamp() {
		return DateTimeFormats.dtDATETIMEToTs(super.getDELIVERYENDDATE());
	}

}
