package com.klst.eorder.api;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.PostalAddress;

public interface ShipFrom {

	public void setShipFromParty(String name, PostalAddress address, ContactInfo contact);
	public void setShipFromParty(BusinessParty party);
	public BusinessParty getShipFromParty();

}
