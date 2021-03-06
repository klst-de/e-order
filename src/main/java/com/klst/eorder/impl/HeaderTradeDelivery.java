package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.api.ShipFrom;
import com.klst.eorder.api.ShipTo;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.HeaderTradeDeliveryType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SupplyChainEventType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.DateTimeType;

public class HeaderTradeDelivery extends HeaderTradeDeliveryType implements ShipTo, ShipFrom {

	static HeaderTradeDelivery create() {
		return new HeaderTradeDelivery(null); 
	}
	// copy factory
	static HeaderTradeDelivery create(HeaderTradeDeliveryType object) {
		if(object instanceof HeaderTradeDeliveryType && object.getClass()!=HeaderTradeDeliveryType.class) {
			// object is instance of a subclass of HeaderTradeDeliveryType, but not HeaderTradeDeliveryType itself
			return (HeaderTradeDelivery)object;
		} else {
			return new HeaderTradeDelivery(object); 
		}
	}

	// copy ctor
	private HeaderTradeDelivery(HeaderTradeDeliveryType object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	// 643: ShipToParty @see ShipTo
	@Override
	public void setShipTo(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = TradeParty.create(name, null, address, contact);
		setShipTo(party);
	}
	@Override
	public void setShipTo(BusinessParty party) {
		if(party==null) return;
		super.setShipToTradeParty((TradeParty)party);
	}
	@Override
	public BusinessParty getShipTo() {
		return super.getShipToTradeParty()==null ? null : TradeParty.create(super.getShipToTradeParty());
	}

	// 725: ShipFromParty @see ShipFrom
	@Override
	public void setShipFrom(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = TradeParty.create(name, null, address, contact);
		setShipFrom(party);
	}
	@Override
	public void setShipFrom(BusinessParty party) {
		if(party==null) return;
		super.setShipFromTradeParty((TradeParty)party);
	}
	@Override
	public BusinessParty getShipFrom() {
		return super.getShipFromTradeParty()==null ? null : TradeParty.create(super.getShipFromTradeParty());
	}

	// die nachfolgenden nicht public, methoden werden hierhin delegiert
	// 767: Requested Delivery Date
	// wie SupplyChainTradeLineItem#setLineDeliveryDate
	void setDeliveryDate(Timestamp ts) {
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		if(super.getRequestedDeliverySupplyChainEvent().isEmpty()) {
			getRequestedDeliverySupplyChainEvent().add(new SupplyChainEventType());
		}
		getRequestedDeliverySupplyChainEvent().get(0).setOccurrenceDateTime(dateTime);
	}
	Timestamp getDeliveryDateAsTimestamp() {
		if(super.getRequestedDeliverySupplyChainEvent().isEmpty()) return null;
		DateTimeType dateTime = getRequestedDeliverySupplyChainEvent().get(0).getOccurrenceDateTime();
		return dateTime==null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}
	
	// 770: Requested Delivery Period
	void setDeliveryPeriod(IPeriod period) {
		if(super.getRequestedDeliverySupplyChainEvent().isEmpty()) {
			getRequestedDeliverySupplyChainEvent().add(new SupplyChainEventType());
		}
		getRequestedDeliverySupplyChainEvent().get(0).setOccurrenceSpecifiedPeriod((Period)period);
	}
	IPeriod getDeliveryPeriod() {
		if(super.getRequestedDeliverySupplyChainEvent().isEmpty()) return null;
		return Period.create(getRequestedDeliverySupplyChainEvent().get(0).getOccurrenceSpecifiedPeriod());
	}

	// 778: Requested Pick up Date
	void setPickupDate(Timestamp ts) {
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		if(super.getRequestedDespatchSupplyChainEvent().isEmpty()) {
			getRequestedDespatchSupplyChainEvent().add(new SupplyChainEventType());
		}
		getRequestedDespatchSupplyChainEvent().get(0).setOccurrenceDateTime(dateTime);
	}
	Timestamp getPickupDateAsTimestamp() {
		if(super.getRequestedDespatchSupplyChainEvent().isEmpty()) return null;
		DateTimeType dateTime = getRequestedDespatchSupplyChainEvent().get(0).getOccurrenceDateTime();
		return dateTime==null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}
	
	// 781: Requested Pick up Period
	void setPickupPeriod(IPeriod period) {
		if(super.getRequestedDespatchSupplyChainEvent().isEmpty()) {
			getRequestedDeliverySupplyChainEvent().add(new SupplyChainEventType());
		}
		getRequestedDespatchSupplyChainEvent().get(0).setOccurrenceSpecifiedPeriod((Period)period);
	}
	IPeriod getPickupPeriod() {
		if(super.getRequestedDespatchSupplyChainEvent().isEmpty()) return null;
		return Period.create(getRequestedDespatchSupplyChainEvent().get(0).getOccurrenceSpecifiedPeriod());
	}

}
