package com.klst.eorder.openTrans;

import java.util.logging.Logger;

import org.bmecat.bmecat._2005.CITY;
import org.bmecat.bmecat._2005.NAME;
import org.bmecat.bmecat._2005.NAME2;
import org.bmecat.bmecat._2005.NAME3;
import org.bmecat.bmecat._2005.STATE;
import org.bmecat.bmecat._2005.ZIP;
import org.opentrans.xmlschema._2.ADDRESS;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.PostalAddress;
import com.klst.eorder.openTrans.reflection.Mapper;

/* aus Doku:
	<PARTY>
		<bmecat:PARTY_ID type="supplier_specific">108304</bmecat:PARTY_ID>
		<PARTY_ROLE>document_creator</PARTY_ROLE>
		<ADDRESS>
			<bmecat:NAME>Power Supply Corp.</bmecat:NAME>
			<CONTACT_DETAILS>
				<bmecat:CONTACT_ID>MA02</bmecat:CONTACT_ID>
				<bmecat:CONTACT_NAME>Mustermann</bmecat:CONTACT_NAME>
				<bmecat:FIRST_NAME>Max</bmecat:FIRST_NAME>
				<bmecat:ACADEMIC_TITLE>Dr</bmecat:ACADEMIC_TITLE>
			</CONTACT_DETAILS>
			<bmecat:STATE>Sachsen Anhalt</bmecat:STATE>
			<bmecat:COUNTRY>Germany</bmecat:COUNTRY>
			<bmecat:COUNTRY_CODED>DE</bmecat:COUNTRY_CODED>
			<bmecat:PHONE>+49 345 WE SELL</bmecat:PHONE>
			<bmecat:FAX>+49 555 77 88 99</bmecat:FAX>
			<bmecat:EMAIL>power@supply.com</bmecat:EMAIL>
			<bmecat:URL>www.powersupplycorp.com</bmecat:URL>
		</ADDRESS>
		<ACCOUNT>
			<HOLDER>Power Supply Corp.</HOLDER>
			<BANK_ACCOUNT type="iban">DE12 3456 7890 0000 9999 99
			</BANK_ACCOUNT>
			<BANK_CODE type="bic">1002003040</BANK_CODE>
			<BANK_NAME>Good Bank</BANK_NAME>
			<BANK_COUNTRY>DE</BANK_COUNTRY>
		</ACCOUNT>
	</PARTY>
 */
public class Address extends ADDRESS implements PostalAddress {

	@Override // implements PostalAddressFactory
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		return create(countryCode, postalCode, city);
	}
	
	static Address create(String countryCode, String postalCode, String city) {
		return new Address(countryCode, postalCode, city, null);
	}

	public static Address create() {
		return create((ADDRESS)null);
	}
	// copy factory
	static Address create(ADDRESS object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof ADDRESS && object.getClass()!=ADDRESS.class) {
			// object is instance of a subclass of ADDRESS, but not ADDRESS itself
			return (Address)object;
		} else {
			return new Address(object); 
		}
	}

	private static final Logger LOG = Logger.getLogger(Address.class.getName());

	// copy ctor
	private Address(ADDRESS address) {
		super();
		if(address!=null) {
			SCopyCtor.getInstance().invokeCopy(this, address);
		}
	}
	
	private Address(String countryCode, String postalCode, String city, String street) {
		this(countryCode, null, postalCode, city, street, null);
	}
	
	// building nicht in XRechnung-v1-2-0.pdf dokumentiert
	private Address(String countryCode, String postalCode, String city, String street, String building) {
		this(countryCode, null, postalCode, city, street, building);
	}
	
	private Address(String countryCode, String regionCode, String postalCode, String city, String street, String building) {
	super();
	setCountryCode(countryCode);
	
	if(regionCode!=null) {
		setCountrySubdivision(regionCode);
	}
	
	setPostCode(postalCode);
	setCity(city);
	
//	// wg.  	[CII-DT-094] - BuildingNumber shall not be used.
//	String mStreet = street;
//	if(mStreet==null) {
//		mStreet = building;
//	} else {
//		if(building!=null) {
//			mStreet = mStreet + " " + building;
//		}
//	}
//	if(mStreet!=null) {
//		// wg.  	[CII-DT-088] - StreetName shall not be used.
//		setAddressLine3(mStreet);
//	}
	
}

	private void setCountryCode(String countryCode) {
		super.setCOUNTRYCODED(countryCode);
	}
	
	private void setCity(String city) {
		Mapper.add(super.getCITY(), new CITY(), city);
	}
	
	private void setPostCode(String postCode) {
		Mapper.add(super.getZIP(), new ZIP(), postCode);
	}
	
	@Override
	public void setAddressLine1(String addressLine) {
//		// das nachfolgende generisch:
//		//     add(List list, Object objToAdd, Object value)
		Mapper.add(super.getNAME(), new NAME(), addressLine);
//		NAME n = new NAME();
//		n.setValue(addressLine);
//		if(super.getNAME().isEmpty()) {
//			getNAME().add(n);
//		} else {
//		    LOG msg
//			getNAME().set(0, n);
//		}
		LOG.info("nun?------>get addressLine:"+this.getAddressLine1());
	}

	@Override
	public void setAddressLine2(String addressLine) {
		Mapper.add(super.getNAME2(), new NAME2(), addressLine);
	}

	@Override
	public void setAddressLine3(String addressLine) {
		Mapper.add(super.getNAME3(), new NAME3(), addressLine);
	}

	@Override
	public void setCountrySubdivision(String countrySubdivision) {
		Mapper.add(super.getSTATE(), new STATE(), countrySubdivision);
	}

	@Override
	public String getAddressLine1() {
		return super.getNAME().isEmpty() ? null : getNAME().get(0).getValue();
	}

	@Override
	public String getAddressLine2() {
		return super.getNAME2().isEmpty() ? null : getNAME2().get(0).getValue();
	}

	@Override
	public String getAddressLine3() {
		return super.getNAME3().isEmpty() ? null : getNAME3().get(0).getValue();
	}

	@Override
	public String getCity() {
//		super.getCITY() liefert List<CITY> // class CITY extends DtMLSTRING mit String value und DtLANG lang
		return super.getCITY().isEmpty() ? null : getCITY().get(0).getValue();
		// Citi in mehreren Sprachen!
		// ==> In openTRANS® sind alle Dokumente per Vorgabe als einsprachig definiert.
		// Doku 3.2 Mehrsprachige Dokumente
	}

	@Override
	public String getPostCode() {
		return super.getZIP().isEmpty() ? null : getZIP().get(0).getValue();
	}

	@Override
	public String getCountrySubdivision() {
		return super.getSTATE().isEmpty() ? null : getSTATE().get(0).getValue();
	}

	@Override
	public String getCountryCode() {
		return super.getCOUNTRYCODED()==null ? null : getCOUNTRYCODED();
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(getCountryCode()==null ? "null" : getCountryCode());
		stringBuilder.append(getCountrySubdivision()==null ? "" : " ("+getCountrySubdivision()+")");
		stringBuilder.append(", ");
		stringBuilder.append(getPostCode()==null ? "null" : getPostCode());
		stringBuilder.append(", ");
		stringBuilder.append(getCity()==null ? "null" : getCity());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
