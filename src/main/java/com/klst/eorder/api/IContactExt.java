package com.klst.eorder.api;

import com.klst.edoc.api.IContact;

// reusable ABIE (aggregate business information entity) 103 TradeContactType hat ein element
// das zwar in ABIE 100 TradeContactType , aber nicht in UBL existiert
public interface IContactExt extends IContact {

	public void setContactDepartment(String department);
	public String getContactDepartment();

}
