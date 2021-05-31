package com.klst.eorder.api;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.PostalAddress;

/**
 * INVOICER PARTY
 * <p>
 * The Party who should issue or send the invoice on behalf of the supplier
 * <p>
 * Cardinality: 	0..1 (EXTENDED)
 * <br>Rule ID: 	
 * <br>Order-X-No: 	792
 */
public interface Invoicer {

	public void setInvoicer(String name, PostalAddress address, ContactInfo contact);
	public void setInvoicer(BusinessParty party);
	public BusinessParty getInvoicer();

}
