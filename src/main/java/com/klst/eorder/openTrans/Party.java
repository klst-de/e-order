package com.klst.eorder.openTrans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

/* PARTY:
    protected List<TypePARTYID> partyid , required = true
    protected List<String> partyrole; see enum PartyRole
    protected List<ADDRESS> address;
    protected List<ACCOUNT> account;
    protected MIMEINFO mimeinfo;
 */
public class Party extends PARTY implements BusinessParty, BusinessPartyAddress, BusinessPartyContact {

	/*
	OT type in Identifikator eines Geschäftspartners,  namespace: BMECAT, Vordefinierte Werte:
	buyer_specific    : Vom Einkäufer vergebene Identifikationsnummer
	customer_specific : Vom Kunden vergebene Identifikationsnummer
	duns              : Dun & Bradstreet DUNS-Kennung (siehe auch http://www.dnbgermany.de/datenbank/dunsnummer.html)
	iln               : Internationale Lokationsnummer, ILN-Kennung (siehe auch http://www.gs1-germany.de/internet/content/e39/e50/e221/e222/index_ger.html)
	gln               : Internationale Lokationsnummer, In Deutschland auch ILN genannt (siehe ILN oben)
	party_specific    : Von der jeweiligen Organisation selbst definierte Identifikationsnummer
	supplier_specific : Vom Lieferanten vergebene Identifikationsnummer
	w{1,250}          : Bezeichnung des Kodierungsstandards, "\w{1,250}" bedeutet, 
	                    die Bezeichnung des Kodierungsstandards muss mindestens 1 Zeichen lang und darf höchestens 250 Zeichen lang sein
	 */
	public enum PartyIDType {
		iln,             // ==> Identifier ID
		gln,             // ==> Identifier ID
		party_specific,  // ==> Identifier ohne Schema
		businessName,    // Name, unter dem der BP bekannt ist, sofern abweichend vom registrationName (auch als Firmenname bekannt)
		registrationName // (registration)Name : der volle formelle Name, unter dem der BP 
		                 // im nationalen Register für juristische Personen oder als steuerpflichtige Person eingetragen ist 
						 // oder anderweitig als Person(en) handelt
	}
	/*
	 * Zulässige Werte für das Element PARTY_ROLE
	 * Vollständige Liste siehe doku
	 * 
	 */
	public enum PartyRole {
		supplier,          // BG4_Seller: Lieferant
		buyer,             // BG7_Buyer: Einkaufende Organisation, einkaufendes Unternehmen
		invoice_recipient, // Rechnungsempfänger
		delivery           // Anlieferort, Ort (Geschäftspartner) der Leistungserbringung bzw. Anlieferung
//final_delivery Verweis auf die Adresse und den Kontakt für den Endempfänger.
		//Die Exportkontrollbehörde prüft Aufträge derzeit nur auf Auftragskopfebene. 
		//Daher sollte für nachweispflichtige Aufträge keine zum Kopfteil abweichende FINAL_DELIVERY_PARTY angegeben werden.
//customer Der Geschäftspartner ist ein Kunde des einkaufenden Unternehmens.
//central_regulator Der Geschäftspartner ist ein Zentralregulierer und unterstützt verschiedene Geschäftspartner bei der Geschäftsabwicklung.
//deliverer Der Geschäftspartner ist ein Transporteur
//document_creator Der Geschäftspartner ist der Ersteller des Dokumentes
//intermediary Der Geschäftspartner nimmt die Rolle eines Intermediäres zwischen Leistungserbringer und Leistungsempfänger ein
// ...
//other Wenn keine andere Rolle passt, kann die Rolle 'Sonstige' verwendet werden
	}
	
	static BusinessParty getParty(List<PARTY> bpList, PartyRole partyrole) {
		if(bpList.isEmpty()) return null;
		List<BusinessParty> resList = new ArrayList<BusinessParty>(bpList.size());
		bpList.forEach(bp -> {
			bp.getPARTYROLE().forEach(role ->{
				if(partyrole.toString().equals(role)) {
					Party party = Party.create(bp);
					LOG.finer("#getParty: partyrole="+partyrole+":"+party);
					resList.add(Party.create(bp));
				}
			});
		});
		return resList.isEmpty() ? null : resList.get(0);
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
weitere Namen und IDs (am Beispiel Seller):
BT-29 0..n Seller identifier ( mit Schema )         / Kennung des Verkäufers
BT-30 0..1 Seller legal registration identifier     / Kennung der rechtlichen Registrierung des Verkäufers
BT-31 0..1 Seller VAT identifier                    / Umsatzsteuer-Identifikationsnummer mit vorangestelltem Ländercode
BT-32 0..1 Seller tax registration identifier       / Steuernummer des Verkäufers
BT-33 0..1 Seller additional legal information      / weitere rechtliche Informationen, wie z. B. Aktienkapital
BT-34 0..1 Seller electronic address ( mit Schema ) / Elektronische Adresse des Verkäufers
 */
	private Party(String registrationName, String businessName, PostalAddress address, ContactInfo contact) {
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

	private List<PartyID> getPartyID(PartyIDType partyIDType) {
		if(super.getPARTYID().isEmpty()) return null;
		
		List<PartyID> resList = new ArrayList<PartyID>(getPARTYID().size());
		getPARTYID().forEach(id -> {
			if(partyIDType.toString().equals(id.getType())) {
				resList.add(new PartyID(id));
			}
		});
		return resList;
	}
	
	// (registration)Name BT-27 1..1 Name des Verkäufers / BT-44 1..1 Name des Käufers
	@Override
	public String getRegistrationName() {
//		if(super.getPARTYID().isEmpty()) return null;
//		
//		List<Identifier> resList = new ArrayList<Identifier>(getPARTYID().size());
//		getPARTYID().forEach(id -> {
//			if(PartyIDType.registrationName.toString().equals(id.getType())) {
//				resList.add(new PartyID(id));
//			}
//		});
//		return resList.isEmpty() ? null : resList.get(0).getContent();
		List<PartyID> resList = getPartyID(PartyIDType.registrationName);
		if(resList==null) return null; // super.getPARTYID().isEmpty()
		if(resList.isEmpty()) return null; // kein el mit passendem PartyIDType
		return resList.get(0).getContent();
	}
	// in OT steht der BP Name in Address, das ist aber der businessName
	void setRegistrationName(String name) {
		if(name==null) return;
		super.getPARTYID().add(new PartyID(name, PartyIDType.registrationName.toString()));
	}

	// businessName       BT-28 0..1 Handelsname des Verkäufers / Seller trading name
	@Override
	public String getBusinessName() {
		List<PartyID> resList = getPartyID(PartyIDType.businessName);
//		if(resList==null) return null; // super.getPARTYID().isEmpty()
//		if(resList.isEmpty()) return null; // kein el mit passendem PartyIDType
		if(!(resList==null || resList.isEmpty())) {
			return resList.get(0).getContent();
		}
		// name aus AddressLine1 holen:
		if(super.getADDRESS().isEmpty()) return null;
		return getAddress().getAddressLine1();
	}
	@Override
	public void setBusinessName(String name) {
//		if(name==null) return;
//		if(super.getADDRESS().isEmpty()) {
//			// ??? TODO
//		} else {
//			if(getAddress().getAddressLine1()==null) {
//				getAddress().setAddressLine1(name);
//			}
//		}
		if(name==null) return;
		super.getPARTYID().add(new PartyID(name, PartyIDType.businessName.toString()));
	}

	// BT-29 0..n Seller identifier ( mit Schema ) / Kennung des Verkäufers
	@Override
	public Identifier getIdentifier() { // holt nur den ersten
//		if(super.getPARTYID().isEmpty()) return null;
//		
//		List<Identifier> resList = new ArrayList<Identifier>(getPARTYID().size());
//		getPARTYID().forEach(id -> {
//			if(PartyIDType.gln.toString().equals(id.getType())) {
//				resList.add(new PartyID(id));
//			}
//			if(PartyIDType.iln.toString().equals(id.getType())) {
//				resList.add(new PartyID(id));
//			}
//		});
//		return resList.isEmpty() ? null : resList.get(0);
		
		List<PartyID> resList = getPartyID(PartyIDType.iln);
		if(!(resList==null || resList.isEmpty())) {
			return resList.get(0);
		}
		resList = getPartyID(PartyIDType.gln);
		if(!(resList==null || resList.isEmpty())) {
			return resList.get(0);
		}
		resList = getPartyID(PartyIDType.party_specific);
		if(!(resList==null || resList.isEmpty())) {
			return resList.get(0);
		}
		return null;
	}
	public String getId() {
		Identifier id = getIdentifier();
		return id==null ? null : id.getContent();
	}

	@Override
	public void addId(String name, String schemeID) {
		if(name==null) return;
		if(schemeID==null) {
			super.getPARTYID().add(new PartyID(name, PartyIDType.party_specific.toString()));
		} else {
			super.getPARTYID().add(new PartyID(name, schemeID));
		}
	}
	@Override
	public void setId(String name, String schemeID) {
		addId(name, schemeID);
	}
	
	// BT-30 0..1 Seller legal registration identifier / Kennung der rechtlichen Registrierung des Verkäufers
	@Override
	public String getCompanyId() {
		Identifier identifier = getCompanyIdentifier();
		return identifier==null ? null : identifier.getContent();
	}
	private static boolean isNumeric(String str) {
		  return str.matches("\\d+");  //match a number.
		}
	@Override
	public Identifier getCompanyIdentifier() {
		if(super.getPARTYID().isEmpty()) return null;
		List<Identifier> resList = new ArrayList<Identifier>(getPARTYID().size());
		getPARTYID().forEach(id -> {
			// Type numerisch 
			String type = id.getType();
			if(type!=null && isNumeric(type)) {
				resList.add(new PartyID(id));
			}
		});
		return resList.isEmpty() ? null : resList.get(0);
	}
	@Override
	public void setCompanyId(String name, String schemeID) {
		if(name==null) return;
		super.getPARTYID().add(new PartyID(name, schemeID));
	}

	// BT-31 0..1 Seller VAT identifier / Umsatzsteuer-Identifikationsnummer mit vorangestelltem Ländercode
	@Override
	public List<Identifier> getTaxRegistrationIdentifier() {
		return null;
//		Address address = (Address)getAddress();
//		return address==null ? null : new ID(address.getVATID(), "VAT"); // ID aus CIO
	}
	@Override
	public String getVATRegistrationId() {
		Address address = (Address)getAddress();
		return address==null ? null : address.getVATID();
	}
	@Override
	public void addTaxRegistrationId(String name, String schemeID) {
		setTaxRegistrationId(name, schemeID);
	}
	@Override
	public void setTaxRegistrationId(String name, String schemeID) {
		if(name==null) return;
		Address address = (Address)getAddress();
		address.setVATID(name);
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

	// BT-34 0..1 Seller electronic address ( mit Schema ) / Elektronische Adresse des Verkäufers
	@Override
	public Identifier getUriUniversalCommunication() {
		Address address = (Address)getAddress();
		if(address==null) return null;
		return address.getURL()==null ? null : new ID(address.getURL());
	}

	@Override
	public void setUriUniversalCommunication(String name, String schemeID) {
		if(name==null) return;
		Address address = (Address)getAddress();
		address.setURL(name);
	}

}