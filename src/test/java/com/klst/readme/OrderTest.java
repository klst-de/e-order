package com.klst.readme;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.BusinessPartyAddress;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.MessageFunctionEnum;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.edoc.untdid.TaxTypeCode;
import com.klst.eorder.api.AbstactTransformer;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.ContactInfoExt;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.SupportingDocument;
import com.klst.eorder.impl.Amount;               // impl.jar
import com.klst.eorder.impl.CrossIndustryOrder;   // impl.jar
import com.klst.eorder.impl.ID;                   // ...
import com.klst.eorder.impl.Measure;
import com.klst.eorder.impl.Quantity;
import com.klst.eorder.impl.TradeAddress;
import com.klst.eorder.impl.TradeContact;
import com.klst.eorder.impl.TradeParty;
import com.klst.eorder.impl.UnitPriceAmount;      // impl.jar
import com.klst.marshaller.CioTransformer;

public class OrderTest {
	
	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = OrderTest.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(OrderTest.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(OrderTest.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(OrderTest.class.getName());		
	}
//	private static final Logger LOG = Logger.getLogger(OrderTest.class.getName());

	static final String EUR = "EUR";
	static final String C62 = "C62";
	static final String MTR = "MTR";
	static final String GTIN = "0160"; // Global Trade Item Number (GTIN)
	static final String TESTDIR = "src/test/resources/";
	// file content: "Das könnte eine Anlage sein."
	static final String PDF = "01_15_Anhang_01.pdf";
	static final String MIME = "application/pdf";

	static private AbstactTransformer cioTransformer;
	static private AbstactTransformer transformer;
	Object object;

    @BeforeClass
    public static void staticSetup() {
    	initLogger();
    }

	@Before 
    public void setup() {
	   	cioTransformer = CioTransformer.getInstance();
	   	object = null;
    }

	// https://stackoverflow.com/questions/4350084/byte-to-file-in-java
	private byte[] getBytesFromTestFile(String fileName) {
		Path path = Paths.get(TESTDIR+fileName);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	private void writeBytesToFile(byte[] bytes, String fileName) {
		File file = new File(fileName);
		Path path = Paths.get(file.getAbsolutePath());
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	public void cioTest() {
		CoreOrder order;
		order = CrossIndustryOrder.getFactory().createOrder(BG2_ProcessControl.PROFILE_COMFORT, null, DocumentNameCode.Order);
		order.setId("Order#-1"); // 9: BT-1 Identifier (mandatory)
		
		// ExchangedDocument.Name wird in einvoice nicht verwendet
		
		order.setIssueDate(DateTimeFormats.ymdToTs("20200331"));	 // 14: BT-2
		order.setIssueDate(DateTimeFormats.ymdToTs("2020-03-31"));
		order.addLanguage("fr");                        // 18: Language
		order.setPurpose(MessageFunctionEnum.Original); // 19: defined in UNTDID 1225
		order.setRequestedResponse("AC");               // 20: defined in UNTDID 4343
		
		order.addNote( order.createNote("AAI", "Content of Note") );
		
		ContactInfo contact = TradeContact.create().createContactInfo(null, null, null);
		ContactInfoExt contactExt = (ContactInfoExt)contact;
		contactExt.setContactDepartment("dept");
		LOG.info("contact:"+contactExt);
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
		bp.setId("123654879","0088"); // 0088 : EAN // TODO EAN is veraltert ==> ???
		LOG.info("bp:"+bp);
		
		order.setDocumentCurrency("EUR");
		order.setBuyerReference("BUYER_REF_BU123");
		// <ram:BuyerReference>BUYER_REF_BU123</ram:BuyerReference>
		
		PostalAddress address = order.createAddress("DE", "123", "Ort");
		ContactInfo icontact = order.createContactInfo("name", "tel", "mail");
//		order.setSeller("SUPPLIER_ID_321654", address, icontact, null, null);
		BusinessParty seller = order.createParty("SELLER_NAME", "SELLER_TRADING_NAME", postalAddress, contact);
		seller.setId("SUPPLIER_ID_321654");
		seller.addId("123456789", "0088");
		seller.setCompanyId("123456789", "0002");
//		seller.setCompanyLegalForm("SUPPLIER_ID_321654"); // not defined
		seller.setUriUniversalCommunication("sales@seller.com<", "EM");
		seller.setVATRegistrationId("FR 32 123 456 789");
		order.setSeller(seller);
		
		ContactInfo buyerContact = order.createContactInfo("buyerContact name", "buyerContact tel", null);
		if(buyerContact instanceof TradeContact) { // TradeContact implements IContactExt
			ContactInfoExt buyerContactExt = (ContactInfoExt)buyerContact;
			buyerContactExt.setContactDepartment("BUYER_CONTACT_DEP");
			buyerContactExt.setContactType("LB");
		}
		order.setBuyer("Buyer", address, buyerContact);
		
		order.setBuyerAccountingReference(new ID("BUYER_ACCOUNT_REF"));
		
		order.setShipFromParty("SHIP_FROM_NAME", order.createAddress("FR", "75003", "SHIP_FROM_CITY"), contact);
		
		order.setDeliveryTerms("FCA", "7");
		order.setPurchaseOrderReference("PO123456789");
		order.setContractReference("CONTRACT_2020-25987");
		order.setPreviousOrderResponseReference("PREV_ORDER_R_ID");
		
		BusinessParty buyer = order.getBuyer();
		// test shortcuts for Address + Contact
		if(buyer instanceof TradeParty) {
			BusinessPartyAddress buyerAddress = (BusinessPartyAddress)buyer;
			LOG.info("buyerAddress (no shortcut):"+buyerAddress.getAddress());
		}
		LOG.info("buyer Address:"+buyer.getAddress() + " Contact:"+buyer.getBPContact());
		
		// ---------------
		IAmount amount = new Amount(new BigDecimal(60.00));
		// 60 <> 60.00, aber mit compare to ==
		assertEquals(0, new BigDecimal(60).compareTo(amount.getValue(RoundingMode.UNNECESSARY)));
		
		order.createTotals(new Amount(new BigDecimal(310.00)) // Sum of line net amount
				, new Amount(new BigDecimal(300.00))
				, new Amount(new BigDecimal(360.00)));
		assertEquals(0, new BigDecimal(310).compareTo(order.getLineNetTotal().getValue(RoundingMode.UNNECESSARY)));
		order.setChargesTotal(new Amount(new BigDecimal(21.00)));
		order.setAllowancesTotal(new Amount(new BigDecimal(31.00)));
		assertEquals(order.getTotalTaxExclusive().getValue(), // 
				order.getLineNetTotal().getValue().add(order.getChargesTotal().getValue()).subtract(order.getAllowancesTotal().getValue())
				);
		
		// BG-20 0..n DOCUMENT LEVEL ALLOWANCE:
		BigDecimal tenPerCent = new BigDecimal(10);
		AllowancesAndCharges allowance = order.createAllowance(new Amount(new BigDecimal(31)), new Amount(new BigDecimal(310)), tenPerCent);
		allowance.setReasoncode("64");
		allowance.setReasonText("SPECIAL AGREEMENT");
		allowance.setTaxType(TaxTypeCode.VAT);
		allowance.setTaxCategoryCode(TaxCategoryCode.StandardRate);
		allowance.setTaxPercentage(new BigDecimal(20));
		order.addAllowanceCharge(allowance);
		
		// 549: BG-24 ADDITIONAL SUPPORTING DOCUMENTS ==> <ram:TypeCode>916</ram:TypeCode>
//		Use for "ADDITIONAL SUPPORTING DOCUMENTS" with TypeCode Value = 916, 
//		or for "OBJECT IDENTIFIER with Type Code Value = 130, "
//		or for "TENDER OR LOT REFERENCE" with Type Code Value = 50
		order.addSupportigDocument("ADD_REF_DOC_ID", "ADD_REF_DOC_Desc", "ADD_REF_DOC_URIID");
		// 561: BT-17 0..1 Tender or lot reference
		String TENDER_ID = "TENDER_ID";
		order.setTenderOrLotReference(TENDER_ID);
		// 564: BT-18 0..1 (OBJECT IDENTIFIER FOR INVOICE)
		String OBJECT_ID = "OBJECT_ID";
		order.setInvoicedObject(OBJECT_ID, "AWV"); // AWV == Phone number

		List<SupportingDocument> supportingDocs = order.getAdditionalSupportingDocuments();
		assertEquals(TENDER_ID, order.getTenderOrLotReference());
		assertEquals(1, supportingDocs.size());
		SupportingDocument supportingDoc = supportingDocs.get(0);
		assertEquals(DocumentNameCode.RelatedDocument.getValueAsString(), supportingDoc.getDocumentCode());
		assertEquals(OBJECT_ID, order.getInvoicedObject());
		
		OrderLine line = order.createOrderLine("1"    // order line number
				  , new Quantity("C62", new BigDecimal(6))              // one unit/C62
				  , new Amount(EUR, new BigDecimal(60.00))				// line net amount
				  , new UnitPriceAmount(EUR, new BigDecimal(10.00))	    // price
				  , "Zeitschrift [...]"									// itemName
				  , TaxCategoryCode.StandardRate, new BigDecimal(7)     // VAT category code, rate 7%
				  );
		line.addNote("AAI", "Content of Note");
		line.setUnitPriceQuantity(new Quantity("C62", new BigDecimal(1))); // (optional) price base quantity
		line.setPartialDeliveryIndicator(true);
		line.addStandardIdentifier("1234567890123", GTIN);
		line.setSellerAssignedID("987654321");
		line.setBuyerAssignedID("654987321");
		
		line.setStatus("Status");
		line.setLineObjectID("id", "schemeID", "AWV"); // 154: BG.25.BT-128
		line.setDescription("description"); // BG-31.BT-154
		line.addClassificationIdentifier("4047247110051", "EN", null, null); // BG-31.BT-158
		line.setCountryOfOrigin("FR"); // BG-31.BT-159
		
		// OrderLineID nicht mit ID verwechseln! 
		// OrderLineID: der Verweis auf die ursprüngliche ID ist in CIO überflüssig, in CIOR/CIOC sinnvoll
		line.setOrderLineID("id-1"); // warning expected
		
		// Order-X-No: 	68, Verpackung, ? für die 6 Zeitschriften
		String woodenCase = "7B"; // UNTDID 7065 Package type description code verweist auf UN/ECE Recommendation 21, Annex V
		line.setPackaging(woodenCase                      // 69: type of packaging
				                                          // 70: Dimension:
				, new Measure(MTR, new BigDecimal(0.30))  // 72+71: width/Breite 
				, new Measure(null, new BigDecimal(0.50)) // 73:length ohne 74:Einheit sollte nicht möglich sein
				, null);                                  // 76+75: height 
		assertEquals(woodenCase, line.getPackagingCode());
		assertEquals(MTR, line.getPackagingWidth().getUnitCode());
		LOG.info("Packaging Width:"+line.getPackagingWidth()
			+ " Length:"+line.getPackagingLength()
			+ " Height:"+line.getPackagingHeight()
				);
		assertThat(new BigDecimal(0.30),  Matchers.closeTo(line.getPackagingWidth().getValue(RoundingMode.HALF_UP), new BigDecimal(0.0001)));
		assertNull(line.getPackagingLength().getUnitCode());
		assertNull(line.getPackagingHeight());

		/* Test Order-X-No: 79 + 141
                    <ram:AdditionalReferenceReferencedDocument>
                         <ram:IssuerAssignedID>ADD_REF_PROD_ID</ram:IssuerAssignedID>
                         <ram:URIID>ADD_REF_PROD_URIID</ram:URIID>
                         <ram:TypeCode>6</ram:TypeCode>       TODO DocumentNameCode.ProductSpecification report
                         <ram:Name>ADD_REF_PROD_Desc</ram:Name>
                    </ram:AdditionalReferenceReferencedDocument>
               </ram:SpecifiedTradeProduct>
                    <ram:AdditionalReferencedDocument>
                         <ram:IssuerAssignedID>ADD_REF_DOC_ID</ram:IssuerAssignedID>
                         <ram:URIID>ADD_REF_DOC_URIID</ram:URIID>
                         <ram:LineID>5</ram:LineID>
                         <ram:TypeCode>916</ram:TypeCode>
                         <ram:Name>ADD_REF_DOC_Desc</ram:Name>
                    </ram:AdditionalReferencedDocument>
		 */
		// 79:
		line.addReferencedProductDocument("ADD_REF_PROD_ID", "6", "ADD_REF_PROD_Desc", "ADD_REF_PROD_URIID");
		
		// 98ff : SUBSTITUTED PRODUCT / OOR only
		line.setSubstitutedProductID("SubstitutedProductID");
		line.addSubstitutedIdentifier("global ID", "schemeID");
		
		// 141:
		Reference lineID_5 = new ID("5");
		byte[] content = getBytesFromTestFile(PDF);
		LOG.info("content.length:"+content.length); // das result xml ist zu gross für's Loggen
		line.addReferencedDocument("ADD_REF_DOC_ID", lineID_5, "ADD_REF_DOC_Desc"
				, null // no date for the issuance
//				, content, MIME, PDF);
				, "ADD_REF_DOC_URIID");
		
		List<SupportingDocument> refProdDocs = line.getReferencedProductDocuments();
		assertEquals(1, refProdDocs.size());
		SupportingDocument refProdDoc = refProdDocs.get(0);
		assertEquals("6", refProdDoc.getDocumentCode());
		List<SupportingDocument> refDocs = line.getReferencedDocuments();
		assertEquals(1, refDocs.size());
		SupportingDocument refDoc = refDocs.get(0);
		assertEquals(DocumentNameCode.RelatedDocument.getValueAsString(), refDoc.getDocumentCode());
		assertEquals("5", refDoc.getLineReference().getName());
		
		// 162: TODO ? createAllowance auch als createDiscount:
//		line.createDiscount(new Amount(new BigDecimal(6.00)), reasonCode, reason);
		AllowancesAndCharges discount = line.createAllowance(new Amount(new BigDecimal(1.00)), null, null);
		discount.setReasoncode("95");
		discount.setReasonText("DISCOUNT");
//		line.setPriceDiscount(line.createAllowance(new Amount(new BigDecimal(1.00)), null, null));
		line.setPriceDiscount(discount);
		
		// 318: BG-27 0..n LINE ALLOWANCES:
		//BigDecimal tenPerCent = new BigDecimal(10);
		line.addAllowance(new Amount(new BigDecimal(6.00)), new Amount(new BigDecimal(60.00)), tenPerCent);
		// 326: BG-28 0..n LINE CHARGES:
		AllowancesAndCharges charge = line.createCharge(new Amount(new BigDecimal(6.00)), new Amount(new BigDecimal(60.00)), tenPerCent);
		charge.setReasoncode("64");
		charge.setReasonText("Special agreement");
		line.addAllowanceCharge(charge);
		
		order.addLine(line);
		assertEquals(1, line.getNotes().size());
		assertEquals(line.getLineTotalAmount().getValue()
				, line.getQuantity().getValue().multiply(line.getUnitPriceAmount().getValue()).setScale(2, RoundingMode.HALF_UP));
		assertEquals(C62, line.getQuantity().getUnitCode());
		assertEquals(EUR, line.getLineTotalAmount().getCurrencyID());
		assertEquals(GTIN, line.getStandardIdentifier().get(0).getSchemeIdentifier());
		
		order.addLine("2"
				, new Quantity("C62", new BigDecimal(10))
				, new Amount(new BigDecimal(100.00))
				, new UnitPriceAmount(new BigDecimal(10.00))
				, "Product Name"
				, TaxCategoryCode.StandardRate, new BigDecimal(16)
				);

		transformer = cioTransformer;
		object = order;
		commercialOrderTest();
	}

	void commercialOrderTest() {
		LOG.info("object.Class:"+object.getClass());
		
		byte[] xml = transformer.marshal(object);
		LOG.info(new String(xml));
//		writeBytesToFile(xml, "orderTestResult.xml");
	}

}
