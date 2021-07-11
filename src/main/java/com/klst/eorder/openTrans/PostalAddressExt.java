package com.klst.eorder.openTrans;

import com.klst.edoc.api.PostalAddress;

public interface PostalAddressExt extends PostalAddress {

	// Straßenname und Hausnummer namespace: BMECAT
	public void setStreet(String street);
	public String getStreet();
	
	// TODO
	// BOXNO Nummer des Postfachs namespace: BMECAT
	// ZIPBOX Postleitzahl des Postfachs namespace: BMECAT
}
