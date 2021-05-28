package com.klst.eorder.openTrans;

import java.util.List;

import org.bmecat.bmecat._2005.CONTACTNAME;
import org.bmecat.bmecat._2005.EMAILS;
import org.bmecat.bmecat._2005.PHONE;
import org.opentrans.xmlschema._2.CONTACTDETAILS;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.ContactInfo;
import com.klst.eorder.api.ContactInfoExt;

/* mapping:
    protected String contactid;
    protected List<CONTACTNAME> contactname, required  ===> ContactPoint aka contactName
    protected List<FIRSTNAME> firstname;
    protected List<TITLE> title;
    protected List<ACADEMICTITLE> academictitle;
    protected List<CONTACTROLE> contactrole;           ===> TODO ContactType
    // Mit diesem Attribut kann man die Funktion oder Position zusätzlich kodiert, maschineninterpretierbar angeben
    // in order-x: entries in UNTDID 3139 Contact function code  
    protected List<CONTACTDESCR> contactdescr;
    protected List<PHONE> phone;                       ===> ContactTelephone
    protected List<FAX> fax;
    protected String url;
    protected EMAILS emails;                           ===> ContactEmail
    protected AUTHENTIFICATION authentification;
   example:
		<CONTACT_DETAILS>
			<bmecat:CONTACT_NAME>Marbach</bmecat:CONTACT_NAME>
			<bmecat:FIRST_NAME>Laura</bmecat:FIRST_NAME>
			<bmecat:PHONE>+41 52 723 67 34</bmecat:PHONE>
			<bmecat:FAX>+41 52 723 67 18</bmecat:FAX>
<!-- 		<bmecat:CONTACT_ROLE type="document_issuer">Sachbearbeitung Ek</bmecat:CONTACT_ROLE> -->	
			<bmecat:EMAILS>
			  <bmecat:EMAIL>laura.marbach@plica.ch</bmecat:EMAIL>
			</bmecat:EMAILS>
		</CONTACT_DETAILS>
 */
public class Contactdetails extends CONTACTDETAILS implements ContactInfoExt {

	@Override
	public ContactInfo createContactInfo(String contactName, String contactTel, String contactMail) {
		return create(contactName, contactTel, contactMail);
	}

	static Contactdetails create(String contactName, String contactTel, String contactMail) {
		return new Contactdetails(contactName, contactTel, contactMail);
	}

//	public static Contactdetails create() {
//		return create((CONTACTDETAILS)null);
//	}
	// copy factory
	static Contactdetails create(CONTACTDETAILS object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof CONTACTDETAILS && object.getClass()!=CONTACTDETAILS.class) {
			// object is instance of a subclass of CONTACTDETAILS, but not CONTACTDETAILS itself
			return (Contactdetails)object;
		} else {
			return new Contactdetails(object); 
		}
	}

	// copy ctor
	private Contactdetails(CONTACTDETAILS object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}
	
	private Contactdetails(String contactName, String contactTel, String contactMail) {
		setContactPoint(contactName);
		setContactTelephone(contactTel);
		setContactEmail(contactMail);
	}

	private void setContactPoint(String name) {
		if(name==null) return;
//		super.setCONTACTID(name);
		CONTACTNAME contactname = new CONTACTNAME();
		contactname.setValue(name);
		super.getCONTACTNAME().add(contactname);
	}

	/* Example:
	<PHONE type="office">+49 201 183 4084</PHONE>
	<PHONE type="private">+49 201 12345678</PHONE>
	<PHONE type="mobile">+49 170 12345678</PHONE>
	 */
	private void setContactTelephone(String contactTel) {
		if(contactTel==null) return;
		PHONE phone = new PHONE();
		phone.setValue(contactTel);
		super.getPHONE().add(phone);
	}

	public void setContactEmail(String contactMail) {
		if(contactMail==null) return;
		EMAILS emails = new EMAILS();
		emails.getEMAILAndPUBLICKEY().add(contactMail);
		super.setEMAILS(emails);
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

	@Override
	public String getContactPoint() {
//		return super.getCONTACTID();
		List<CONTACTNAME> list = super.getCONTACTNAME();
		return list.isEmpty() ? null : list.get(0).getValue();
	}

	@Override
	public String getContactTelephone() {
		List<PHONE> list = super.getPHONE();
		return list.isEmpty() ? null : list.get(0).getValue();
	}

	@Override
	public String getContactEmail() {
		EMAILS emails = super.getEMAILS();
		if(emails==null) return null;
		
		List<Object> list = emails.getEMAILAndPUBLICKEY();
		if(list.isEmpty()) return null;
		return list.get(0).toString();
	}

	@Override
	public void setContactDepartment(String name) {
		if(name==null) return;
//		super.contactid
	}
	@Override
	public String getContactDepartment() {
		return null; // TODO
	}

	@Override
	public void setContactType(String code) {
		// TODO Auto-generated method stub
		// CONTACTROLE
/*
administrativ : Ansprechpartner bzgl. administrativer Fragestellungen
commercial : Ansprechpartner bzgl. betriebswirtschaftlicher Fragestellungen
special_treatment : Sonderbehandlungen, Ansprechpartner bzgl. der Handhabung von besonderen Produkten
technical : Technisch, Ansprechpartner bzgl. technischer Fragestellungen 2005fd
others : Andere Allgemeiner Ansprechpartner
Die Liste der zulässigen Werte wurde in Version 20v3 um 
'document_issuer' (Intermediär) Person, die das vorliegende Dokument ausgelöst hat, z.B. eine Bestellung auslösen
'marketplace' (Marktplatz),
'payer' (Zahlender), 
'remittee' (Zahlungsempfänger) und 
'central_regulator' (Zentralregulierer) 
erweitert.
 */
	}

	@Override
	public String getContactType() {
		// TODO Auto-generated method stub
		return null;
	}

}
