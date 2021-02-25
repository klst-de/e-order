package com.klst.eorder.impl;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IContact;
import com.klst.edoc.api.PostalAddress;
import com.klst.eorder.api.ShipFrom;
import com.klst.eorder.api.ShipTo;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.HeaderTradeDeliveryType;

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
			CopyCtor.invokeCopy(this, object);
		}
	}

	@Override
	public void setShipToParty(String name, PostalAddress address, IContact contact) {
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
	public void setShipFromParty(String name, PostalAddress address, IContact contact) {
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

}
