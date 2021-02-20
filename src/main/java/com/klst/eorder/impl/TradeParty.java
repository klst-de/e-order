package com.klst.eorder.impl;

import java.util.List;
import java.util.logging.Logger;

import com.klst.einvoice.reflection.CopyCtor;
import com.klst.eorder.BusinessParty;
import com.klst.eorder.BusinessPartyAddress;
import com.klst.eorder.BusinessPartyContact;
import com.klst.eorder.BusinessPartyFactory;
import com.klst.eorder.IContact;
import com.klst.eorder.Identifier;
import com.klst.eorder.PostalAddress;

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
	public BusinessParty createParty(String name, String tradingName, PostalAddress address, IContact contact) {
		return create(name, tradingName, address, contact);
	}
	static TradeParty create(String name, String tradingName, PostalAddress address, IContact contact) {
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
			CopyCtor.invokeCopy(this, doc);
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
	private TradeParty(String registrationName, String businessName, PostalAddress address, IContact contact) {
		super();
		setRegistrationName(registrationName);
		setBusinessName(businessName);
		setAddress(address);
		if(contact!=null) setIContact(contact);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[RegistrationName:");
		stringBuilder.append(getRegistrationName()==null ? "null" : getRegistrationName());
		stringBuilder.append(", BusinessName:");
		stringBuilder.append(getBusinessName()==null ? "null" : getBusinessName());
//		stringBuilder.append(", Id:");
//		stringBuilder.append(getId()==null ? "null" : getId());
		
		stringBuilder.append(", Address:");
		stringBuilder.append(getAddress()==null ? "null" : getAddress());
		
		stringBuilder.append(", Contact:");
		stringBuilder.append(getIContact()==null ? "null" : getIContact());
		
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	// PostalAddress
	@Override
	public PostalAddress getAddress() {
		TradeAddressType address = super.getPostalTradeAddress();
		if(address==null) return null; // defensiv, sollte nie null sein
		return TradeAddress.create(address);
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
	public IContact getIContact() {
		TradeContactType tradeContact = super.getDefinedTradeContact();
		return tradeContact==null ? null : TradeContact.create(tradeContact);
	}

	@Override
	public void setIContact(IContact contact) {
		super.setDefinedTradeContact((TradeContact)contact);	
	}

	@Override
	public IContact createContact(String contactName, String contactTel, String contactMail) {
		return TradeContact.create(contactName, contactTel, contactMail);
	}

/*
			<ram:SellerTradeParty>
				<ram:ID>SUPPLIER_ID_321654</ram:ID>                                         <!-- Identifier
				<ram:GlobalID schemeID="0088">123654879</ram:GlobalID>                      <!-- CompanyLegalForm
				<ram:Name>SELLER_NAME</ram:Name>                                            <!-- RegistrationName
				<ram:SpecifiedLegalOrganization>
					<ram:ID schemeID="0002">123456789</ram:ID>                              <!-- CompanyId
					<ram:TradingBusinessName>SELLER_TRADING_NAME</ram:TradingBusinessName>  <!-- BusinessName
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
	@Override
	public String getRegistrationName() {
		return super.getName()==null ? null : getName().getValue();
	}

	void setRegistrationName(String name) {
		if(name==null) return;
		super.setName(Text.create(name));
	}

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

	@Override
	public Identifier getIdentifier() {
		return super.getID()==null ? null : new ID(getID());
	}
	public String getId() {
		return super.getID()==null ? null : getID().getValue();
	}

	@Override
	public void setId(String name, String schemeID) {
		if(name==null) return;
		super.setID(new ID(name, schemeID));
	}

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

	@Override
	public Identifier getTaxRegistrationIdentifier() {
		return super.getSpecifiedTaxRegistration()==null ? null : new ID(getSpecifiedTaxRegistration().getID());
		
//		List<TaxRegistrationType> taxRegistrationList = super.getSpecifiedTaxRegistration();
//		List<Identifier> result = new ArrayList<Identifier>(taxRegistrationList.size());
//		if(taxRegistrationList.isEmpty()) return result;
//		taxRegistrationList.forEach(taxRegistration -> {
//			result.add(new ID(taxRegistration.getID()));
//		});
//		return result;
	}
	
	@Override
	public void setTaxRegistrationId(String name, String schemeID) {
		if(name==null) return;
		if(super.getSpecifiedTaxRegistration()==null) {
			setSpecifiedTaxRegistration(new TaxRegistrationType());
		}
		getSpecifiedTaxRegistration().setID(new ID(name, schemeID));
		
//		List<TaxRegistrationType> taxRegistrationList = super.getSpecifiedTaxRegistration();
//		TaxRegistrationType taxRegistration = new TaxRegistrationType();
//		taxRegistration.setID(new ID(name, schemeID));
//		taxRegistrationList.add(taxRegistration);
	}

	@Override // 0..1 also genügt der erste
	public String getCompanyLegalForm() {
		List<IDType> idList = super.getGlobalID();
		return idList.isEmpty() ? null : idList.get(0).getValue();
//		List<TextType> textList = super.getDescription();
//		return textList.isEmpty() ? null : textList.get(0).getValue();
	}

	@Override
	public void setCompanyLegalForm(String name) {
		if(name==null) return;
//		super.getDescription().add(Text.create(name));
		super.getGlobalID().add(new ID(name));
	}

	@Override // 0..1 also genügt der erste
	public Identifier getUriUniversalCommunication() {
		return super.getURIUniversalCommunication()==null ? null : new ID(getURIUniversalCommunication().getURIID());
		
//		List<UniversalCommunicationType> uriList = super.getURIUniversalCommunication();
//		if(uriList.isEmpty()) return null;
//		return new ID(uriList.get(0).getURIID().getValue(), uriList.get(0).getURIID().getSchemeID()); 
	}

	@Override
	public void setUriUniversalCommunication(String name, String schemeID) {
		if(name==null) return;
		if(super.getURIUniversalCommunication()==null) {
			setURIUniversalCommunication(new UniversalCommunicationType());
		}
		getURIUniversalCommunication().setURIID(new ID(name, schemeID));
//		UniversalCommunicationType universalCommunicationType = new UniversalCommunicationType();
//		universalCommunicationType.setURIID(new ID(name, schemeID));
//		super.getURIUniversalCommunication().add(universalCommunicationType);
	}

}
