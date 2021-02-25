package com.klst.readme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IContact;
import com.klst.edoc.api.PostalAddress;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.impl.CrossIndustryOrder;
import com.klst.eorder.impl.ID;
import com.klst.eorder.impl.TradeAddress;
import com.klst.eorder.impl.TradeContact;
import com.klst.eorder.impl.TradeParty;
import com.klst.marshaller.AbstactTransformer;
import com.klst.marshaller.CioTransformer;
import com.klst.untdid.codelist.DateTimeFormats;
import com.klst.untdid.codelist.DocumentNameCode;

public class OrderTest {
	
	private static final Logger LOG = Logger.getLogger(OrderTest.class.getName());

	static private AbstactTransformer cioTransformer;
	static private AbstactTransformer transformer;
	Object object;

	@Before 
    public void setup() {
	   	cioTransformer = CioTransformer.getInstance();
	   	object = null;
    }

	@Test
	public void cioTest() {
		CoreOrder order;
		order = CrossIndustryOrder.getFactory().createOrder(BG2_ProcessControl.PROFILE_COMFORT, null, DocumentNameCode.Order);
		order.setId("Order#-1"); // BT-1 Identifier (mandatory)
		
		// ExchangedDocument.Name wird in einvoice nicht verwendet
		
		order.setIssueDate(DateTimeFormats.ymdToTs("20200331"));	 // BT-2 + 1..1
		order.setIssueDate(DateTimeFormats.ymdToTs("2020-03-31"));	 // BT-2 + 1..1
		
		order.addNote( order.createNote("AAI", "Content of Note") );
		
		IContact contact = TradeContact.create().createContact(null, null, null);
		LOG.info("contact:"+contact);
//		java.lang.IllegalArgumentException: No enum constant un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType.XX
//		PostalAddress postalAddress = TradeAddress.create().createAddress("XX", "123", "City");
//		java.lang.NullPointerException: Name is null // meint countryCode
//		PostalAddress postalAddress = TradeAddress.create().createAddress(null, null, null);
		PostalAddress postalAddress = TradeAddress.create().createAddress("DE", null, null);
		postalAddress.setCountrySubdivision("BY");
		postalAddress.setAddressLine1("one");
		LOG.info("postalAddress:"+postalAddress);
		assertEquals("one", postalAddress.getAddressLine1());
		assertNull(postalAddress.getAddressLine2());
		
		BusinessParty bp = TradeParty.create().createParty("Reg.Name", "Name", postalAddress, contact);
		bp.setId("123654879","0088"); // 0088 : EAN
		LOG.info("bp:"+bp);
		
		order.setDocumentCurrency("EUR");
		order.setBuyerReference("BUYER_REF_BU123");
		// <ram:BuyerReference>BUYER_REF_BU123</ram:BuyerReference>
		
		PostalAddress address = order.createAddress("DE", "123", "Ort");
		IContact icontact = order.createContact("name", "tel", "mail");
//		order.setSeller("SUPPLIER_ID_321654", address, icontact, null, null);
		BusinessParty seller = order.createParty("SELLER_NAME", "SELLER_TRADING_NAME", postalAddress, contact);
		seller.setId("SUPPLIER_ID_321654");
		seller.addId("123456789", "0088");
		seller.setCompanyId("123456789", "0002");
//		seller.setCompanyLegalForm("SUPPLIER_ID_321654"); // not defined
		seller.setUriUniversalCommunication("sales@seller.com<", "EM");
		seller.setVATRegistrationId("FR 32 123 456 789");
		order.setSeller(seller);
		
		order.setBuyer("Buyer", address, icontact); // OK
		
		order.setBuyerAccountingReference(new ID("BUYER_ACCOUNT_REF"));
		
		transformer = cioTransformer;
		object = order;
		commercialOrderTest();
	}

	void commercialOrderTest() {
		LOG.info("object.Class:"+object.getClass());
		
		byte[] xml = transformer.fromModel(object);
		LOG.info(new String(xml));
	}

}
