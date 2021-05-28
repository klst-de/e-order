package com.klst.eorder.openTrans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bmecat.bmecat._2005.TypePARTYID;
import org.opentrans.xmlschema._2.PARTY;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.BusinessPartyAddress;
import com.klst.edoc.api.BusinessPartyContact;
import com.klst.edoc.api.BusinessPartyFactory;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.eorder.impl.ID;

/*
    protected List<TypePARTYID> partyid , required = true
    protected List<String> partyrole;
    protected List<ADDRESS> address;
    protected List<ACCOUNT> account;
    protected MIMEINFO mimeinfo;
 */
public class Party extends PARTY implements BusinessParty, BusinessPartyAddress, BusinessPartyContact {

	/*
	 * Zulässige Werte für das Element PARTY_ROLE
	 * Vollständige Liste siehe doku
	 * 
	 */
	public enum PartyRole {
		supplier, // BG4_Seller: Lieferant
		buyer,    // BG7_Buyer: Einkaufende Organisation, einkaufendes Unternehmen
		invoice_recipient, // Rechnungsempfänger
		delivery           // Anlieferort, Ort (Geschäftspartner) der Leistungserbringung bzw. Anlieferung
	}
	
	@Override  // implements BusinessPartyFactory
	public BusinessParty createParty(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		return create(name, tradingName, address, contact);
	}
	static Party create(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		return new Party(name, tradingName, address, contact);
	}

	public static Party create() { // aka factory
		return new Party((PARTY)null);
	}
	// copy factory
	static Party create(PARTY object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof PARTY && object.getClass()!=PARTY.class) {
			// object is instance of a subclass of PARTY, but not PARTY itself
			return (Party)object;
		} else {
			return new Party(object); 
		}
	}

	private static final Logger LOG = Logger.getLogger(Party.class.getName());

	// copy ctor
	private Party(PARTY object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	/**
	 * ctor for BusinessParty - use BusinessPartyFactory method
	 * 
	 * @param (registration)Name BT-27 1..1 Name des Verkäufers   / BT-44 1..1 Name des Käufers
	 * @param businessName       BT-28 0..1 Handelsname des Verkäufers / Seller trading name
	 * @param address            BG-5  1..1 SELLER POSTAL ADDRESS / BG-8  1..1 BUYER POSTAL ADDRESS
	 * @param contact            BG-6  0..1 SELLER CONTACT        / BG-9  0..1 BUYER CONTACT
	 * 
	 * @see BusinessPartyFactory
	 */
/*
weitere Namen:
BT-29 ++ 0..n Seller identifier ( mit Schema )         / Kennung des Verkäufers
BT-30 ++ 0..1 Seller legal registration identifier     / Kennung der rechtlichen Registrierung des Verkäufers
BT-31 ++ 0..1 Seller VAT identifier                    / Umsatzsteuer-Identifikationsnummer mit vorangestelltem Ländercode
BT-32 ++ 0..1 Seller tax registration identifier       / Steuernummer des Verkäufers
BT-33 ++ 0..1 Seller additional legal information      / weitere rechtliche Informationen, wie z. B. Aktienkapital
BT-34 ++ 0..1 Seller electronic address ( mit Schema ) / Elektronische Adresse des Verkäufers
 */
	private Party(String registrationName, String businessName, PostalAddress address, ContactInfo contact) {
		super();
//		setRegistrationName(registrationName);
//		setBusinessName(businessName);
		setAddress(address);
//		if(contact!=null) setContactInfo(contact);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[RegistrationName:");
		stringBuilder.append(getRegistrationName()==null ? "null" : getRegistrationName());
		stringBuilder.append(", BusinessName:");
		stringBuilder.append(getBusinessName()==null ? "null" : getBusinessName());
		stringBuilder.append(", Id:");
		stringBuilder.append(getIdentifier()==null ? "null" : getIdentifier());
		
		stringBuilder.append(", Address:");
		stringBuilder.append(getAddress()==null ? "null" : getAddress());
		
		stringBuilder.append(", Contact:");
		stringBuilder.append(getContactInfo()==null ? "null" : getContactInfo());
		
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	// PostalAddress
	@Override
	public PostalAddress getAddress() {
		return super.getADDRESS().isEmpty() ? null : Address.create(getADDRESS().get(0));
	}

	@Override
	public void setAddress(PostalAddress address) {
		if(address!=null) addAddress((Address)address);
	}
	void addAddress(Address address) {
		super.getADDRESS().add(address);
	}
	@Override
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		return Address.create(countryCode, postalCode, city);
	}


	// Contact
	@Override
	public ContactInfo getContactInfo() {
		Address address = (Address)getAddress();
		return address.getContactInfo();
	}

	@Override
	public void setContactInfo(ContactInfo contact) {
		Address address = (Address)getAddress();
		address.setContactInfo(contact);
	}

	@Override
	public ContactInfo createContactInfo(String contactName, String contactTel, String contactMail) {
//		return Address.create().createContactInfo(contactName, contactTel, contactMail)
		return Contactdetails.create(contactName, contactTel, contactMail);
	}

	// (registration)Name BT-27 1..1 Name des Verkäufers   / BT-44 1..1 Name des Käufers
	@Override
	public String getRegistrationName() {
		return null;
		// TODO
//		return super.getName()==null ? null : getName().getValue();
	}
//	// nicht public, da im ctor
//	void setRegistrationName(String name) {
//		if(name==null) return;
//		super.setName(Text.create(name));
//	}
//
	// businessName       BT-28 0..1 Handelsname des Verkäufers / Seller trading name
	@Override
	public String getBusinessName() {
		if(super.getADDRESS().isEmpty()) return null;
		return getAddress().getAddressLine1();
	}
	@Override
	public void setBusinessName(String name) {
		if(name==null) return;
		if(super.getADDRESS().isEmpty()) {
			// ??? TODO
		} else {
			if(getAddress().getAddressLine1()==null) {
				getAddress().setAddressLine1(name);
			}
		}
	}

	// BT-29 ++ 0..n Seller identifier ( mit Schema ) / Kennung des Verkäufers
	@Override
	public Identifier getIdentifier() { // holt nur den ersten
		List<TypePARTYID> idList = super.getPARTYID();
		if(idList.isEmpty()) return null;
		
		List<Identifier> res = new ArrayList<Identifier>();
		idList.forEach(id -> {
			// iln ==> GLN, https://de.wikipedia.org/wiki/Global_Location_Number
			if("iln".equals(id.getType())) res.add(new ID(id.getValue(), "iln"));
		});
//		<bmecat:PARTY_ID type="iln">7611577000008</bmecat:PARTY_ID>
//		<bmecat:PARTY_ID type="supplier_specific">1786</bmecat:PARTY_ID>
		return res.isEmpty() ? null : res.get(0);
	}
	public String getId() {
		Identifier id = getIdentifier();
		return id==null ? null : id.getContent();
	}

	@Override
	public void addId(String name, String schemeID) {
		if(name==null) return;
		// TODO
//		if(schemeID==null) {
//			super.setID(new ID(name));
//		} else {
//			super.getGlobalID().add(new ID(name, schemeID));
//		}
	}
	@Override
	public void setId(String name, String schemeID) {
		addId(name, schemeID);
//		if(name==null) return;
//		super.getGlobalID().add(new ID(name, schemeID));
	}
	
	// BT-30 ++ 0..1 Seller legal registration identifier     / Kennung der rechtlichen Registrierung des Verkäufers
	@Override
	public String getCompanyId() {
		return null;
		// TODO
//		IDType id = super.getSpecifiedLegalOrganization()==null ? null : getSpecifiedLegalOrganization().getID();
//		return id==null ? null : id.getValue();
	}
	@Override
	public Identifier getCompanyIdentifier() {
		return null;
		// TODO
//		IDType id = super.getSpecifiedLegalOrganization()==null ? null : getSpecifiedLegalOrganization().getID();
//		return id==null ? null : new ID(id.getValue(), id.getSchemeID());
	}
	@Override
	public void setCompanyId(String name, String schemeID) {
		if(name==null) return;
		// TODO
//		if(super.getSpecifiedLegalOrganization()==null) {
//			setSpecifiedLegalOrganization(new LegalOrganizationType());
//		}
//		getSpecifiedLegalOrganization().setID(new ID(name, schemeID));
	}

	// BT-31 ++ 0..1 Seller VAT identifier / Umsatzsteuer-Identifikationsnummer mit vorangestelltem Ländercode
	@Override
	public List<Identifier> getTaxRegistrationIdentifier() {
		return null;
//		Address address = (Address)getAddress();
//		return address==null ? null : new ID(address.getVATID(), "VAT"); // ID aus CIO
	}
	public String getVATRegistrationId() {
		Address address = (Address)getAddress();
		return address==null ? null : address.getVATID();
	}
	
	public void addTaxRegistrationId(String name, String schemeID) {
		// TODO
	}
	@Override
	public void setTaxRegistrationId(String name, String schemeID) {
		if(name==null) return;
		// TODO
//		if(super.getSpecifiedTaxRegistration().isEmpty()) {
//			TaxRegistrationType taxRegistrationType = new TaxRegistrationType();
//			taxRegistrationType.setID(new ID(name, schemeID));
//			getSpecifiedTaxRegistration().add(taxRegistrationType);
//		}
	}
	
	// BT-33 ++ 0..1 Seller additional legal information      / weitere rechtliche Informationen, wie z. B. Aktienkapital
	@Override
	public String getCompanyLegalForm() {
		LOG.warning("not defined");
		return null;
	}
	@Override
	public void setCompanyLegalForm(String name) {
		LOG.warning("not defined");
	}

	// BT-34 ++ 0..1 Seller electronic address ( mit Schema ) / Elektronische Adresse des Verkäufers
	@Override
	public Identifier getUriUniversalCommunication() {
		Address address = (Address)getAddress();
		if(address==null) return null;
		return address.getURL()==null ? null : new ID(address.getURL());
	}

	@Override
	public void setUriUniversalCommunication(String name, String schemeID) {
		if(name==null) return;
		// TODO
//		if(super.getURIUniversalCommunication()==null) {
//			setURIUniversalCommunication(new UniversalCommunicationType());
//		}
//		getURIUniversalCommunication().setURIID(new ID(name, schemeID));
	}

}
