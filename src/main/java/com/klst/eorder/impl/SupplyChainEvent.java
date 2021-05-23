package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.api.ISupplyChainEvent;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SupplyChainEventType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.DateTimeType;

public class SupplyChainEvent extends SupplyChainEventType implements ISupplyChainEvent {

	@Override
	public ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, Timestamp timestamp) {
		return create(quantity, timestamp, null);
	}

	@Override
	public ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, IPeriod period) {
		return create(quantity, null, period);
	}
	
	static SupplyChainEvent create(IQuantity quantity, Timestamp timestamp, IPeriod period) {
		return new SupplyChainEvent(quantity, timestamp, period);
	}

	// copy factory
	static SupplyChainEvent create(SupplyChainEventType object) {
		if(object instanceof SupplyChainEventType && object.getClass()!=SupplyChainEventType.class) {
			// object is instance of a subclass of SupplyChainEventType, but not SupplyChainEventType itself
			return (SupplyChainEvent)object;
		} else {
			return new SupplyChainEvent(object); 
		}
	}

	// copy ctor
	private SupplyChainEvent(SupplyChainEventType object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	private SupplyChainEvent(IQuantity quantity, Timestamp ts, IPeriod period) {
		setQuantity(quantity);
		setOccurrenceDate(ts);
		setOccurrencePeriod(period);
	}
	
	private void setOccurrenceDate(Timestamp ts) {
		if(ts==null) return;
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		super.setOccurrenceDateTime(dateTime);
	}
	private void setQuantity(IQuantity quantity) {
		if(quantity==null) return;
		super.setUnitQuantity((Quantity)quantity);
	}
	private void setOccurrencePeriod(IPeriod period) {
		if(period==null) return;
		super.setOccurrenceSpecifiedPeriod((Period)period);
	}

	public Timestamp getOccurrenceDateAsTimestamp() {
		DateTimeType dateTime = super.getOccurrenceDateTime();
		return dateTime == null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}
	
	public IQuantity getQuantity() {
		return Quantity.create(super.getUnitQuantity());
	}

	public IPeriod getOccurrencePeriod() {
		return Period.create(super.getOccurrenceSpecifiedPeriod());
	}

}
