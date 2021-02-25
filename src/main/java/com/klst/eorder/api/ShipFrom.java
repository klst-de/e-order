package com.klst.eorder.api;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IContact;
import com.klst.edoc.api.PostalAddress;

public interface ShipFrom {

	public void setShipFromParty(String name, PostalAddress address, IContact contact);
	public void setShipFromParty(BusinessParty party);
	public BusinessParty getShipFromParty();

}
