package com.klst.eorder.api;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.PostalAddress;

/**
 * INVOICEE PARTY / The "BILL TO"
 * <p>
 * The Party to which the invoice must be sent
 * <p>
 * Cardinality: 	0..1 (COMFORT)
 * <br>Rule ID: 	
 * <br>Order-X-No: 	833
 */
public interface BillTo {

	public void setBillTo(String name, PostalAddress address, ContactInfo contact);
	public void setBillTo(BusinessParty party);
	public BusinessParty getBillTo();

}
