package com.klst.eorder.api;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.PostalAddress;

public interface ShipFrom {

	public void setShipFrom(String name, PostalAddress address, ContactInfo contact);
	public void setShipFrom(BusinessParty party);
	public BusinessParty getShipFrom();

}
