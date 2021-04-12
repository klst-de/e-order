package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.BusinessPartyAddress;
import com.klst.edoc.api.BusinessPartyContact;
import com.klst.edoc.api.BusinessPartyFactory;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.LegalOrganizationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TaxRegistrationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeAddressType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeContactType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradePartyType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.UniversalCommunicationType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.TextType;

public class TradeParty extends TradePartyType implements BusinessParty, BusinessPartyAddress, BusinessPartyContact {

	@Override  // implements BusinessPartyFactory
	public BusinessParty createParty(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		return create(name, tradingName, address, contact);
	}
	static TradeParty create(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		return new TradeParty(name, tradingName, address, contact);
	}

	public static TradeParty create() { // aka factory
		return new TradeParty((TradePartyType)null);
	}
	// copy factory
	static TradeParty create(TradePartyType object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof TradePartyType && object.getClass()!=TradePartyType.class) {
			// object is instance of a subclass of TradePartyType, but not TradePartyType itself
			return (TradeParty)object;
		} else {
			return new TradeParty(object); 
		}
	}

	private static final Logger LOG = Logger.getLogger(TradeParty.class.getName());

	// copy ctor
	private TradeParty(TradePartyType doc) {
		super();
		if(doc!=null) {
			SCopyCtor.getInstance().invokeCopy(this, doc);
			LOG.config("copy ctor:"+this);
		}
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
	private TradeParty(String registrationName, String businessName, PostalAddress address, ContactInfo contact) {
		super();
		setRegistrationName(registrationName);
		setBusinessName(businessName);
		setAddress(address);
		if(contact!=null) setContactInfo(contact);
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
		return super.getPostalTradeAddress()==null ? null : TradeAddress.create(getPostalTradeAddress());
	}

	@Override
	public void setAddress(PostalAddress address) {
		if(address!=null) setAddress((TradeAddressType)address);
	}
	void setAddress(TradeAddressType address) {
		super.setPostalTradeAddress(address);
	}
	@Override
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		return TradeAddress.create(countryCode, postalCode, city);
	}


	// Contact
	@Override
	public ContactInfo getContactInfo() {
//		TradeContactType tradeContact = super.getDefinedTradeContact();
//		return tradeContact==null ? null : TradeContact.create(tradeContact);
		List<TradeContactType> tradeContactList = super.getDefinedTradeContact();
		return tradeContactList.isEmpty() ? null : TradeContact.create(tradeContactList.get(0));
	}

	@Override
	public void setContactInfo(ContactInfo contact) {
//		super.setDefinedTradeContact((TradeContact)contact);
		super.getDefinedTradeContact().add((TradeContact)contact);
	}

	@Override
	public ContactInfo createContactInfo(String contactName, String contactTel, String contactMail) {
		return TradeContact.create(contactName, contactTel, contactMail);
	}

/*
			<ram:SellerTradeParty>
				<ram:ID>SUPPLIER_ID_321654</ram:ID>                                         <!-- Identifier
				<ram:GlobalID schemeID="0088">123654879</ram:GlobalID>                      <!--BT-29 Identifier
				<ram:Name>SELLER_NAME</ram:Name>                                            <!--BT-27 RegistrationName
				<ram:SpecifiedLegalOrganization>
					<ram:ID schemeID="0002">123456789</ram:ID>                              <!-- CompanyId
					<ram:TradingBusinessName>SELLER_TRADING_NAME</ram:TradingBusinessName>  <!--BT-28 BusinessName
				</ram:SpecifiedLegalOrganization>
				<ram:DefinedTradeContact>
					<ram:PersonName>SELLER_CONTACT_NAME</ram:PersonName>
					<ram:DepartmentName>SELLER_CONTACT_DEP</ram:DepartmentName>
					<ram:TelephoneUniversalCommunication>
						<ram:CompleteNumber>+33 6 25 64 98 75</ram:CompleteNumber>
					</ram:TelephoneUniversalCommunication>
					<ram:EmailURIUniversalCommunication>
						<ram:URIID>contact@seller.com</ram:URIID>
					</ram:EmailURIUniversalCommunication>
				</ram:DefinedTradeContact>
				<ram:PostalTradeAddress>
					<ram:PostcodeCode>75001</ram:PostcodeCode>
					<ram:LineOne>SELLER_ADDR_1</ram:LineOne>
					<ram:LineTwo>SELLER_ADDR_2</ram:LineTwo>
					<ram:LineThree>SELLER_ADDR_3</ram:LineThree>
					<ram:CityName>SELLER_CITY</ram:CityName>
					<ram:CountryID>FR</ram:CountryID>
				</ram:PostalTradeAddress>
				<ram:URIUniversalCommunication>
					<ram:URIID schemeID="EM">sales@seller.com</ram:URIID>                   <!-- UriUniversalCommunication
				</ram:URIUniversalCommunication>
				<ram:SpecifiedTaxRegistration>
					<ram:ID schemeID="VA">FR 32 123 456 789</ram:ID>                        <!-- TaxRegistrationIdentifier
				</ram:SpecifiedTaxRegistration>
			</ram:SellerTradeParty>

 */
	// (registration)Name BT-27 1..1 Name des Verkäufers   / BT-44 1..1 Name des Käufers
	@Override
	public String getRegistrationName() {
		return super.getName()==null ? null : getName().getValue();
	}
	// nicht public, da im ctor
	void setRegistrationName(String name) {
		if(name==null) return;
		super.setName(Text.create(name));
	}

	// businessName       BT-28 0..1 Handelsname des Verkäufers / Seller trading name
	@Override
	public String getBusinessName() {
		TextType text = super.getSpecifiedLegalOrganization()==null ? null : getSpecifiedLegalOrganization().getTradingBusinessName();
		return text==null ? null : text.getValue();
	}
	@Override
	public void setBusinessName(String name) {
		if(name==null) return;
		if(super.getSpecifiedLegalOrganization()==null) {
			setSpecifiedLegalOrganization(new LegalOrganizationType());
		}
		getSpecifiedLegalOrganization().setTradingBusinessName(Text.create(name));
	}

	// BT-29 ++ 0..n Seller identifier ( mit Schema )         / Kennung des Verkäufers
	@Override
	public Identifier getIdentifier() { // holt nur den ersten
		List<IDType> idList = super.getGlobalID();
		return idList.isEmpty() ? null : new ID(idList.get(0));
	}
	public String getId() {
		Identifier id = getIdentifier();
		return id==null ? null : id.getContent();
	}

	@Override
	public void addId(String name, String schemeID) {
		if(name==null) return;
		if(schemeID==null) {
			super.setID(new ID(name));
		} else {
			super.getGlobalID().add(new ID(name, schemeID));
		}
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
		IDType id = super.getSpecifiedLegalOrganization()==null ? null : getSpecifiedLegalOrganization().getID();
		return id==null ? null : id.getValue();
	}
	@Override
	public Identifier getCompanyIdentifier() {
		IDType id = super.getSpecifiedLegalOrganization()==null ? null : getSpecifiedLegalOrganization().getID();
		return id==null ? null : new ID(id.getValue(), id.getSchemeID());
	}
	@Override
	public void setCompanyId(String name, String schemeID) {
		if(name==null) return;
		if(super.getSpecifiedLegalOrganization()==null) {
			setSpecifiedLegalOrganization(new LegalOrganizationType());
		}
		getSpecifiedLegalOrganization().setID(new ID(name, schemeID));
	}

	// BT-31 0..1 Seller VAT identifier / Umsatzsteuer-Identifikationsnummer mit vorangestelltem Ländercode
	@Override
	public List<Identifier> getTaxRegistrationIdentifier() {
		List<Identifier> result = new ArrayList<Identifier>();
		if(super.getSpecifiedTaxRegistration().isEmpty()) return result;
		getSpecifiedTaxRegistration().forEach( taxRegistration -> {
			result.add(new ID(taxRegistration.getID()));
		});
		return result;
	}
	
	@Override
	public void setTaxRegistrationId(String name, String schemeID) {
		addTaxRegistrationId(name, schemeID);
	}

	@Override
	public void addTaxRegistrationId(String name, String schemeID) {
		if(name==null) return;
		if(super.getSpecifiedTaxRegistration().isEmpty()) {
			TaxRegistrationType taxRegistrationType = new TaxRegistrationType();
			taxRegistrationType.setID(new ID(name, schemeID));
			getSpecifiedTaxRegistration().add(taxRegistrationType);
		}
	}
	
	// BT-33 ++ 0..1 Seller additional legal information      / weitere rechtliche Informationen, wie z. B. Aktienkapital
	// nicht in reusableaggregatebusinessinformationentity._103.TradePartyType
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
		return super.getURIUniversalCommunication()==null ? null : new ID(getURIUniversalCommunication().getURIID());
	}

	@Override
	public void setUriUniversalCommunication(String name, String schemeID) {
		if(name==null) return;
		if(super.getURIUniversalCommunication()==null) {
			setURIUniversalCommunication(new UniversalCommunicationType());
		}
		getURIUniversalCommunication().setURIID(new ID(name, schemeID));
	}

}
