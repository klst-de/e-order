package com.klst.eorder.api;

import com.klst.edoc.api.ContactInfo;

// reusable ABIE (aggregate business information entity) 103 TradeContactType hat ein element
// das zwar in ABIE 100 TradeContactType , aber nicht in UBL existiert
public interface IContactExt extends ContactInfo {

	public void setContactDepartment(String department);
	public String getContactDepartment();

	/**
	 * optional contact code specifying the type of contact
	 * <p>
	 * To be chosen from the entries in UNTDID 3139
	 * 
	 * @param code - example buyer contact code 'LB' == Place of delivery contact : Department/employee to be contacted at the place of delivery.
	 */
	public void setContactType(String code);
	public String getContactType();

}
