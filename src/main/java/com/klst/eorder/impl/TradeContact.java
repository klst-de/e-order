package com.klst.eorder.impl;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.api.IContact;
import com.klst.eorder.api.IContactExt;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeContactType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.UniversalCommunicationType;

public class TradeContact extends TradeContactType implements IContactExt {

	@Override // implements IContactFactory
	public IContact createContact(String contactName, String contactTel, String contactMail) {
		return create(contactName, contactTel, contactMail);
	}
	
	static TradeContact create(String contactName, String contactTel, String contactMail) {
		return new TradeContact(contactName, contactTel, contactMail);
	}
	
	public static TradeContact create() {
		return create((TradeContactType)null);
	}
	// copy factory
	static TradeContact create(TradeContactType object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof TradeContactType && object.getClass()!=TradeContactType.class) {
			// object is instance of a subclass of TradeContactType, but not TradeContactType itself
			return (TradeContact)object;
		} else {
			return new TradeContact(object); 
		}
	}

	// copy ctor
	private TradeContact(TradeContactType contact) {
		super();
		if(contact!=null) {
			CopyCtor.invokeCopy(this, contact);
		}
	}

	/**
	 * ctor for Seller or Buyer Contact
	 * 
	 * @param contactName
	 * @param contactTel
	 * @param contactMail
	 */
	private TradeContact(String contactName, String contactTel, String contactMail) {
		setContactPoint(contactName);
		setContactTelephone(contactTel);
		setContactEmail(contactMail);
	}
	
/*
	<ram:DefinedTradeContact>
		<ram:PersonName>SELLER_CONTACT_NAME</ram:PersonName>          <!-- ContactPoint
		<ram:DepartmentName>SELLER_CONTACT_DEP</ram:DepartmentName>   <!-- fehlt ==> ContactDepartment
		<ram:TelephoneUniversalCommunication>                         <!-- ContactTelephone
			<ram:CompleteNumber>+33 6 25 64 98 75</ram:CompleteNumber>
		</ram:TelephoneUniversalCommunication>
		<ram:EmailURIUniversalCommunication>                          <!-- ContactEmail
			<ram:URIID>contact@seller.com</ram:URIID>
		</ram:EmailURIUniversalCommunication>
	</ram:DefinedTradeContact>

 */
	private void setContactPoint(String name) {
		if(name==null) return;
		super.setPersonName(Text.create(name));
	}

	@Override
	public void setContactDepartment(String name) {
		if(name==null) return;
		super.setDepartmentName(Text.create(name));
	}
	@Override
	public String getContactDepartment() {
		return super.getDepartmentName()==null ? null : getDepartmentName().getValue();
	}

	private void setContactTelephone(String contactTel) {
		if(contactTel==null) return;
		UniversalCommunicationType telephone = new UniversalCommunicationType();
		telephone.setCompleteNumber(Text.create(contactTel));
		super.setTelephoneUniversalCommunication(telephone);
	}

	public void setContactEmail(String contactMail) {
		if(contactMail==null) return;
		UniversalCommunicationType electronicMail = new UniversalCommunicationType();
		electronicMail.setURIID(new ID(contactMail));
		super.setEmailURIUniversalCommunication(electronicMail);
	}
	
	@Override
	public String getContactPoint() {
		return super.getPersonName()==null ? null : getPersonName().getValue();
	}
	
	@Override
	public String getContactTelephone() {
		return super.getTelephoneUniversalCommunication()==null ? null : getTelephoneUniversalCommunication().getCompleteNumber().getValue();
	}
	
	@Override
	public String getContactEmail() {
		return super.getEmailURIUniversalCommunication()==null ? null : getEmailURIUniversalCommunication().getURIID().getValue();
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(getContactPoint()==null ? "null" : getContactPoint());
		
		if(getContactDepartment()!=null) {
			stringBuilder.append(", ").append(getContactDepartment());
		}

		stringBuilder.append(", ");
		stringBuilder.append(getContactTelephone()==null ? "null" : getContactTelephone());
		stringBuilder.append(", ");
		stringBuilder.append(getContactEmail()==null ? "null" : getContactEmail());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
