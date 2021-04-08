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

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.HeaderTradeDeliveryType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainEventType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.DateTimeType;

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
		super();
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	@Override
	public void setShipToParty(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = TradeParty.create(name, null, address, contact);
		setShipToParty(party);
	}
	@Override
	public void setShipToParty(BusinessParty party) {
		if(party==null) return;
		super.setShipToTradeParty((TradeParty)party);
	}
	@Override
	public BusinessParty getShipToParty() {
		return super.getShipToTradeParty()==null ? null : TradeParty.create(super.getShipToTradeParty());
	}

	@Override
	public void setShipFromParty(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = TradeParty.create(name, null, address, contact);
		setShipFromParty(party);
	}
	@Override
	public void setShipFromParty(BusinessParty party) {
		if(party==null) return;
		super.setShipFromTradeParty((TradeParty)party);
	}
	@Override
	public BusinessParty getShipFromParty() {
		return super.getShipFromTradeParty()==null ? null : TradeParty.create(super.getShipFromTradeParty());
	}

	// die nachfolgenden nicht public, methoden werden hierhin delegiert
	void setDeliveryDate(Timestamp ts) {
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		// nur ein (?) element:
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
	
}
