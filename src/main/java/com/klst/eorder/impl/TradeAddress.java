package com.klst.eorder.impl;

import java.util.logging.Logger;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.PostalAddress;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradeAddressType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.TextType;
import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

public class TradeAddress extends TradeAddressType implements PostalAddress {

	@Override // implements PostalAddressFactory
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		return create(countryCode, postalCode, city);
	}
	
	static TradeAddress create(String countryCode, String postalCode, String city) {
		return new TradeAddress(countryCode, postalCode, city, null);
	}

	public static TradeAddress create() {
		return create((TradeAddressType)null);
	}
	// copy factory
	static TradeAddress create(TradeAddressType object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof TradeAddressType && object.getClass()!=TradeAddressType.class) {
			// object is instance of a subclass of TradeAddressType, but not TradeAddressType itself
			return (TradeAddress)object;
		} else {
			return new TradeAddress(object); 
		}
	}

	private static final Logger LOG = Logger.getLogger(TradeAddress.class.getName());

	// copy ctor
	private TradeAddress(TradeAddressType address) {
		SCopyCtor.getInstance().invokeCopy(this, address);
	}
	
	private TradeAddress(String countryCode, String postalCode, String city, String street) {
		this(countryCode, null, postalCode, city, street, null);
	}
	
	// building nicht in XRechnung-v1-2-0.pdf dokumentiert
	private TradeAddress(String countryCode, String postalCode, String city, String street, String building) {
		this(countryCode, null, postalCode, city, street, building);
	}
	
	private TradeAddress(String countryCode, String regionCode, String postalCode, String city, String street, String building) {
		super();
		setCountryCode(countryCode);
		
		if(regionCode!=null) {
			setCountrySubdivision(regionCode);
		}
		
		setPostCode(postalCode);
		setCity(city);
		
		// wg.  	[CII-DT-094] - BuildingNumber shall not be used.
		String mStreet = street;
		if(mStreet==null) {
			mStreet = building;
		} else {
			if(building!=null) {
				mStreet = mStreet + " " + building;
			}
		}
		if(mStreet!=null) {
			// wg.  	[CII-DT-088] - StreetName shall not be used.
			setAddressLine3(mStreet);
		}
		
	}

	@Override
	public void setAddressLine1(String addressLine) {
//		if(addressLine==null) return;
//		super.setLineOne(Text.create(addressLine));
		// alternativ:
		SCopyCtor.getInstance().set(this, "lineOne", addressLine);
	}

	@Override
	public void setAddressLine2(String addressLine) {
		if(addressLine==null) return;
		super.setLineTwo(Text.create(addressLine));
	}

	@Override
	public void setAddressLine3(String addressLine) {
		if(addressLine==null) return;
		super.setLineThree(Text.create(addressLine));
	}

	private void setCity(String city) {
		if(city==null) return;
		super.setCityName(Text.create(city));
	}

	private void setPostCode(String postCode) {
//		if(postCode==null) return;
//		CodeType postcode = new CodeType();
//		postcode.setValue(postCode);
//		this.setPostcodeCode(postcode);
		// alternativ:
		SCopyCtor.getInstance().set(this, "postcodeCode", postCode);
	}

	@Override
	public void setCountrySubdivision(String countrySubdivision) {
//		if(countrySubdivision==null) return;
//		TextType region = new TextType();
//		region.setValue(countrySubdivision);
//		this.setCountrySubDivisionName(region);
		// alternativ:
		SCopyCtor.getInstance().set(this, "countrySubDivisionName", countrySubdivision);
	}

	private void setCountryCode(String countryCode) {
		try {
//			CountryIDType countryID = new CountryIDType();
//			countryID.setValue(ISOTwoletterCountryCodeContentType.fromValue(countryCode));
//			super.setCountryID(countryID);
			// alternativ:
			SCopyCtor.getInstance().set(this, "countryID", ISOTwoletterCountryCodeContentType.fromValue(countryCode));
		} catch (Exception e) {
			LOG.warning("Invalid countryCode "+countryCode);
			throw new IllegalArgumentException("Invalid countryCode "+countryCode);
		}
	}

	@Override
	public String getAddressLine1() {
		TextType line = super.getLineOne();
		return line==null ? null : line.getValue();
	}
	
	@Override
	public String getAddressLine2() {
		TextType line = super.getLineTwo();
		return line==null ? null : line.getValue();
	}
	
	@Override
	public String getAddressLine3() {
		TextType line = super.getLineThree();
		return line==null ? null : line.getValue();
	}
	
	@Override
	public String getCity() {
		return super.getCityName()==null ? null : super.getCityName().getValue();
	}
	
	@Override
	public String getPostCode() {
		return super.getPostcodeCode()==null ? null : super.getPostcodeCode().getValue();
	}
	
	@Override
	public String getCountrySubdivision() {
		TextType region = super.getCountrySubDivisionName();
		return region==null ? null : region.getValue();
	}

	@Override
	public String getCountryCode() {
		// ISOTwoletterCountryCodeContentType :: super.getCountryID().getValue()
		return super.getCountryID()==null ? null : super.getCountryID().getValue().value();
	}

//	@Override
//	public String getStreet() {
//		return super.g.getStreetName()==null ? null : super.getStreetName().getValue();
//	}

//	@Override
//	public String getBuilding() {
//		return super.getBuildingNumber()==null ? null : super.getBuildingNumber().getValue();
//	}
	
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
