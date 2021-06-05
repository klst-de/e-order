package com.klst.eorder.api;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.PostalAddress;

/*
			<ram:ShipToTradeParty>
				<ram:ID>SHIP_TO_ID</ram:ID>
				<ram:GlobalID schemeID="0088">5897546912</ram:GlobalID>
				<ram:Name>SHIP_TO_NAME</ram:Name>
				<ram:DefinedTradeContact>
					<ram:PersonName>SHIP_TO_CONTACT_NAME</ram:PersonName>
					<ram:DepartmentName>SHIP_TO_CONTACT_DEP</ram:DepartmentName>
					<ram:TelephoneUniversalCommunication>
						<ram:CompleteNumber>+33 6 85 96 32 41</ram:CompleteNumber>
					</ram:TelephoneUniversalCommunication>
					<ram:EmailURIUniversalCommunication>
						<ram:URIID>shipto@customer.com</ram:URIID>
					</ram:EmailURIUniversalCommunication>
				</ram:DefinedTradeContact>
				<ram:PostalTradeAddress>
					<ram:PostcodeCode>69003</ram:PostcodeCode>
					<ram:LineOne>SHIP_TO_ADDR_1</ram:LineOne>
					<ram:LineTwo>SHIP_TO_ADDR_2</ram:LineTwo>
					<ram:LineThree>SHIP_TO_ADDR_3</ram:LineThree>
					<ram:CityName>SHIP_TO_CITY</ram:CityName>
					<ram:CountryID>FR</ram:CountryID>
				</ram:PostalTradeAddress>
				<ram:URIUniversalCommunication>
					<ram:URIID schemeID="EM">delivery@buyer.com</ram:URIID>
				</ram:URIUniversalCommunication>
			</ram:ShipToTradeParty>
 */
public interface ShipTo {

	public void setShipTo(String name, PostalAddress address, ContactInfo contact);
	public void setShipTo(BusinessParty party);
	public BusinessParty getShipTo();

}
