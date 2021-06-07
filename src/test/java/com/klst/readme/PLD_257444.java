package com.klst.readme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AbstactTransformer;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.SupportingDocument;
import com.klst.eorder.impl.Amount;               // impl.jar
import com.klst.eorder.impl.ID;
import com.klst.eorder.impl.Quantity;
import com.klst.eorder.impl.UnitPriceAmount;      // impl.jar
import com.klst.eorder.openTrans.Address;
import com.klst.eorder.openTrans.Order;
import com.klst.eorder.openTrans.PartyID;
import com.klst.eorder.openTrans.PostalAddressExt;
import com.klst.marshaller.OpenTransOrderTransformer;
import com.klst.test.CommonUtils;

public class PLD_257444 extends Constants {
	
	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = PLD_257444.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(PLD_257444.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(PLD_257444.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(PLD_257444.class.getName());		
	}
//	private static final Logger LOG = Logger.getLogger(PLD_257444.class.getName());
	
	static private AbstactTransformer otTransformer;
	static private AbstactTransformer transformer;
	Object object;

    @BeforeClass
    public static void staticSetup() {
    	initLogger();
    }

	@Before 
    public void setup() {
	   	otTransformer = OpenTransOrderTransformer.getInstance();
	   	object = null;
    }

	private CoreOrder unmarshal(File testFile) {
		try {
			InputStream is = new FileInputStream(testFile);
			object = transformer.unmarshal(is);
			LOG.info("unmarshaled object:"+object);
			Class<?> type = Class.forName(com.klst.marshaller.OpenTransOrderTransformer.CONTENT_TYPE_NAME); // OT aus jar laden
			// dynamisch, dazu muss der ctor public sein: public OrderResponse(ORDERRESPONSE doc):
			return CoreOrder.class.cast(type.getConstructor(object.getClass()).newInstance(object));
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}
		return null;
	}

	private void marshal(String filename) {
		LOG.info("object.Class:"+object.getClass());
		
		byte[] xml = transformer.marshal(object);
		LOG.info(new String(xml));
		
		if(filename==null) return;
		
		File testFile = CommonUtils.xmlToTempFile(filename, xml);
		CoreOrder co = null;
		// unmarshal the result file testFile and perform assertions:
		if(transformer.isValid(testFile)) {
			co = unmarshal(testFile);
			doAssert(co);
		}
	}

	static final String ORDER_ID = "PLEX-141269";
	static final Timestamp issueDate = Timestamp.valueOf("2020-01-22"+_HMS);
	private void doAssert(CoreOrder cio) {
//		assertFalse(cio.isTest());                                           // 2
//		assertEquals(BG2_ProcessControl.PROFILE_BASIC, cio.getProfile());    // 7
// keine Methode für <GENERATION_DATE>2020-01-22T07:35:18.6258</GENERATION_DATE>
		assertEquals(ORDER_ID, cio.getId());                                 // 9: BT-1
		assertEquals(DocumentNameCode.Order, cio.getDocumentCode());         // 11
		assertEquals(issueDate, cio.getIssueDateAsTimestamp());              // 14
//		assertFalse(cio.isCopy());
//		assertEquals(MessageFunctionEnum.Original, cio.getPurposeCode());    // 19
//		assertEquals(AC, cio.getRequestedResponse());                        // 20
//		
		List<OrderLine> ol = cio.getLines();
		//assertEquals(11, ol.size());
		
		ArrayList<ExpectedLine> exp = expected();
		for(int i=0; i<ol.size(); i++) {
			OrderLine l = ol.get(i);
			LOG.info("line "+i + ":"+l);
			
			if(i>=exp.size()) continue;
			
			ExpectedLine e = exp.get(i);
			assertEquals(e.id, l.getId());
			if(e.note==null) {
				assertTrue(l.getNotes().isEmpty());
			} else {
				assertEquals(e.note.toString(), l.getNotes().get(0).toString());
			}
			assertEquals(e.qty.toString(), l.getQuantity().toString());
			LOG.info("upa expected:"+e.upa + " =?= "+new UnitPriceAmount(l.getUnitPriceAmount().getValue())
					+" delivery:"+l.getDeliveryDateAsTimestamp()
					+" tcc:"+l.getTaxCategory()
					);
			assertEquals(e.upa.toString(), new UnitPriceAmount(l.getUnitPriceAmount().getValue()).toString());
			assertEquals(e.lna.toString(), l.getLineTotalAmount().toString());
			assertEquals(e.upq.toString(), l.getUnitPriceQuantity().toString());
			assertEquals(e.name, l.getItemName());
			assertEquals(e.desc, l.getDescription());
			assertEquals(e.sid.toString(), l.getStandardIdentifier().get(0).toString());
			assertEquals(e.sai, l.getSellerAssignedID());
			assertEquals(e.bai, l.getBuyerAssignedID());
			assertEquals(e.pdi, l.isPartialDeliveryAllowed());
			assertEquals(e.delivery, l.getDeliveryDateAsTimestamp());
			assertEquals(e.tcc, l.getTaxCategory());
			List<SupportingDocument> sdList = l.getReferencedProductDocuments();
			assertFalse(sdList.isEmpty());
			SupportingDocument sd = sdList.get(0);
			LOG.info("CUSTOMER_ORDER_REFERENCE:"+l.getReferencedProductDocuments()
				+ sdList.get(0).getLineReference()
				+ sdList.get(0).getDateAsTimestamp()
			);
			assertEquals(ORDER_ID, sdList.get(0).getDocumentReference().getName());
			assertEquals(e.id, sdList.get(0).getLineReference().getName());
			assertEquals(issueDate, sdList.get(0).getDateAsTimestamp());
			assertEquals(e.sddr.getName(), sd.getDocumentReference().getName());
			assertEquals(e.sdlr.getName(), sd.getLineReference().getName());
			assertEquals(e.sdts, sd.getDateAsTimestamp());		
		}
		
		BusinessParty seller = cio.getSeller(); // 345
		LOG.info("seller:"+seller + ", BuyerReferenceValue="+cio.getBuyerReferenceValue());
		ExpectedBP expSeller = expectedSeller();
		assertEquals(expSeller.id.toString(), seller.getIdentifier().toString());
		if(expSeller.companyId==null) {
			assertNull(seller.getCompanyIdentifier());
		} else {
			assertEquals(expSeller.companyId.toString(), seller.getCompanyIdentifier().toString());
		}
		assertEquals(expSeller.name, seller.getRegistrationName());
		assertEquals(expSeller.bpn, seller.getBusinessName());
		assertEquals(expSeller.vatId, seller.getVATRegistrationId());
		if(expSeller.uriId==null) {
			assertNull(seller.getUriUniversalCommunication());
		} else {
			assertEquals(expSeller.uriId.toString(), seller.getUriUniversalCommunication().toString());
		}
		assertEquals(expSeller.pa.toString(), seller.getAddress().toString());
		if(expSeller.ci==null) {
			assertNull(seller.getBPContact());
		} else {
			assertEquals(expSeller.ci.toString(), seller.getBPContact().toString());
		}
		
		BusinessParty buyer = cio.getBuyer(); // 390
		LOG.info("buyer:"+buyer);
		ExpectedBP expBuyer = expectedBuyer();
		assertEquals(expBuyer.id.toString(), buyer.getIdentifier().toString());
		if(expBuyer.companyId==null) {
			assertNull(buyer.getCompanyIdentifier());
		} else {
			assertEquals(expBuyer.companyId.toString(), buyer.getCompanyIdentifier().toString());
		}
		assertEquals(expBuyer.name, buyer.getRegistrationName());
		assertEquals(expBuyer.bpn, buyer.getBusinessName());
		assertEquals(expBuyer.vatId, buyer.getVATRegistrationId());
		if(expBuyer.uriId==null) {
			assertNull(buyer.getUriUniversalCommunication());
		} else {
			assertEquals(expBuyer.uriId.toString(), buyer.getUriUniversalCommunication().toString());
		}
		assertEquals(expBuyer.pa.toString(), buyer.getAddress().toString());
		if(expBuyer.ci==null) {
			assertNull(buyer.getBPContact());
		} else {
			assertEquals(expBuyer.ci.toString(), buyer.getBPContact().toString());
		}
		
		BusinessParty billTo = cio.getBillTo(); // invoice_recipient
		LOG.info("billTo:"+billTo);
		ExpectedBP expBillTo = expectedBillTo();
		assertEquals(expBillTo.id.toString(), billTo.getIdentifier().toString());
		if(expBillTo.companyId==null) {
			assertNull(billTo.getCompanyIdentifier());
		} else {
			assertEquals(expBillTo.companyId.toString(), billTo.getCompanyIdentifier().toString());
		}
		assertEquals(expBillTo.name, billTo.getRegistrationName());
		assertEquals(expBillTo.bpn, billTo.getBusinessName());
		assertEquals(expBillTo.vatId, billTo.getVATRegistrationId());
		if(expBillTo.uriId==null) {
			assertNull(billTo.getUriUniversalCommunication());
		} else {
			assertEquals(expBillTo.uriId.toString(), billTo.getUriUniversalCommunication().toString());
		}
		assertEquals(expBillTo.pa.toString(), billTo.getAddress().toString());
		assertEquals(expBillTo.al1, billTo.getAddress().getAddressLine1());
//		assertEquals(expBillTo.al2), billTo.getAddress().getAddressLine2());
		if(expBillTo.ci==null) {
			assertNull(billTo.getBPContact());
		} else {
			assertEquals(expBillTo.ci.toString(), billTo.getBPContact().toString());
		}
		
		// Anlieferort, Ort (Geschäftspartner) der Leistungserbringung bzw. Anlieferung
		BusinessParty shipTo = cio.getShipTo();
		LOG.info("shipTo:"+shipTo);
		ExpectedBP expShipTo = expectedShipTo();
		assertEquals(expShipTo.id.toString(), shipTo.getIdentifier().toString());
		if(expShipTo.companyId==null) {
			assertNull(shipTo.getCompanyIdentifier());
		} else {
			assertEquals(expShipTo.companyId.toString(), shipTo.getCompanyIdentifier().toString());
		}
		assertEquals(expShipTo.name, shipTo.getRegistrationName());
		assertEquals(expShipTo.bpn, shipTo.getBusinessName());
		assertEquals(expShipTo.vatId, shipTo.getVATRegistrationId());
		if(expShipTo.uriId==null) {
			assertNull(shipTo.getUriUniversalCommunication());
		} else {
			assertEquals(expShipTo.uriId.toString(), shipTo.getUriUniversalCommunication().toString());
		}
		assertEquals(expShipTo.pa.toString(), shipTo.getAddress().toString());
		assertEquals(expShipTo.al1, shipTo.getAddress().getAddressLine1());
//		assertEquals(expShipTo.al2, shipTo.getAddress().getAddressLine2());
		if(expShipTo.ci==null) {
			assertNull(shipTo.getBPContact());
		} else {
			assertEquals(expShipTo.ci.toString(), shipTo.getBPContact().toString());
		}

	}
	
	@Test
    public void readFile() {
		File testFile = CommonUtils.getTestFile(TESTDIR+"PLD_257444_Order_example.XML");
		transformer = otTransformer;
		CoreOrder co = null;
		// unmarshal toModel:
		if(transformer.isValid(testFile)) {
			co = unmarshal(testFile);
			LOG.info("CoreOrder co:"+co);
			doAssert(co);
		} else {
			LOG.severe("not valid: "+testFile);
		}
		LOG.info("fertig.\n");
	}

	ExpectedBP expectedBuyer() {
		ExpectedBP bp = new ExpectedBP();
		bp.id = new PartyID("7611577000008", ILN);
		//<bmecat:PARTY_ID type="supplier_specific">1786</bmecat:PARTY_ID>
		bp.al1 = "Plica AG";
		bp.bpn = bp.al1;
		bp.ci = Address.create().createContactInfo("Marbach", "+41 52 723 67 34", "laura.marbach@plica.ch");
		bp.pa = Address.create().createAddress("CH", "8500", "Frauenfeld");
		bp.pa.setAddressLine1(bp.al1);
		bp.pa.setAddressLine2("Systeme für Elektrotechnik");
		bp.street = "Zürcherstrasse 350";
		bp.vatId = "CHE-103.663.775 MWST";
		bp.uriId = new ID("www.plica.ch");
				
		return bp;
	}
	ExpectedBP expectedShipTo() {
		ExpectedBP bp = expectedBuyer();
		bp.ci = Address.create().createContactInfo("Logistik", "+41 52 723 61 11", "jlogistik@janico.ch");		
		return bp;
	}
	ExpectedBP expectedBillTo() {
		ExpectedBP bp = new ExpectedBP();
		bp.id = new PartyID("7611577000008", ILN);
		//<bmecat:PARTY_ID type="supplier_specific">1786</bmecat:PARTY_ID>
		bp.al1 = "Plica AG";
		bp.bpn = bp.al1;
		bp.ci = Address.create().createContactInfo("Administration", "+41 52 723 61 11", "administration@janico.ch");
		bp.pa = Address.create().createAddress("CH", "8500", "Frauenfeld");
		bp.pa.setAddressLine1(bp.al1);
		bp.pa.setAddressLine2("Systeme für Elektrotechnik");
		bp.street = "Zürcherstrasse 350";
		bp.vatId = "CHE-103.663.775 MWST";
		bp.uriId = new ID("www.plica.ch");
		
		return bp;
	}
	ExpectedBP expectedSeller() {
		ExpectedBP bp = new ExpectedBP();
		bp.id = new PartyID("3661458000003", ILN);
		bp.al1 = "A Company";
		bp.bpn = bp.al1;
		bp.ci = Address.create().createContactInfo("Weber", "24257572524", "hans.weber@ultra.fyi");
		bp.pa = Address.create().createAddress("FR", "54152", "Citoyene");
		bp.pa.setAddressLine1(bp.al1);
		bp.pa.setAddressLine2("Industry for dummy Manufacturing");
		bp.street = "108, rue un non-sens";
		bp.vatId = "CHE-000.000.000 MWST";
		bp.uriId = new ID("https://www.ultra.fyi/");
		
		return bp;
	}
	
	ArrayList<ExpectedLine> expected() {
		ArrayList<ExpectedLine> lines = new ArrayList<ExpectedLine>(3);
		ExpectedLine line = new ExpectedLine();
		line.id = "1";
		line.qty = new Quantity(C62, new BigDecimal(2000));
		line.upa = new UnitPriceAmount(new BigDecimal(5.16));
		line.upq = new Quantity(null, new BigDecimal(100));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue()).divide(line.upq.getValue()));
		line.name = "BLISTOM25K";
		line.desc = "BLISTO K M25 noir, Bouchon de ferm. PA GFK 20pcs";
		line.pdi = OrderLine.YES;
		line.sid = new ID("7611577104836", EAN);
		line.sai = "G4525220";
		line.bai = "907216725";
		line.tcc = TaxCategoryCode.ExemptFromTax;
		line.delivery = Timestamp.valueOf("2020-01-30"+_HMS);
		line.sddr = new ID(ORDER_ID);
		line.sdlr = new ID(line.id);
		line.sdts = issueDate;
		lines.add(line);
		
		line = new ExpectedLine();
		line.id = "2";
		line.sai = "D4816931";
		line.sid = new ID("7601577560732", EAN);
		line.bai = "916850027";
		line.name = "BLISTOM16MSATEX";
		line.desc = "Bst EX d/e MS M16 6-pans 25pcs, Bouch. de f. laiton";
		line.qty = new Quantity(C62, new BigDecimal(25));
		line.upa = new UnitPriceAmount(new BigDecimal(60.69));
		line.upq = new Quantity(null, new BigDecimal(100));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue()).divide(line.upq.getValue()));
		line.pdi = OrderLine.YES;
		line.delivery = Timestamp.valueOf("2020-01-30"+_HMS);
		line.sddr = new ID(ORDER_ID);
		line.sdlr = new ID(line.id);
		line.sdts = issueDate;
		lines.add(line);
		
		line = new ExpectedLine();
		line.id = "3";
		line.sai = "D7875920";
		line.sid = new ID("7611577099484", EAN);
		line.bai = "926001075";
		line.name = "GGMM75IN";
		line.desc = "GGM INOX V4A M75, Contre-écrou INOX V4A 1pce";
		line.qty = new Quantity(C62, new BigDecimal(1));
		line.upa = new UnitPriceAmount(new BigDecimal(2855));
		line.upq = new Quantity(null, new BigDecimal(100));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue()).divide(line.upq.getValue()));
		line.pdi = OrderLine.YES;
		line.delivery = Timestamp.valueOf("2020-01-30"+_HMS);
		line.sddr = new ID(ORDER_ID);
		line.sdlr = new ID(line.id);
		line.sdts = issueDate;
		lines.add(line);
		
		// TODO rest

		return lines;
	}
	
	@Test
	public void xcreate() {
		CoreOrder or;
		or = Order.create().createOrder("???", DocumentNameCode.Order);
		or.setTestIndicator(CoreOrder.PROD);                     // 2: ignored in OT
		or.setId(ORDER_ID);                                      // 9: BT-1 Identifier (mandatory)
		or.setIssueDate(issueDate);                              // 14: BT-2
//		order.setCopyIndicator(!CoreOrder.COPY);                 // 16:
		
//		<bmecat:LANGUAGE default="true">fra</bmecat:LANGUAGE> <!-- TODO type
		or.addLanguage("fra");                                   // 18:
		
//		order.setPurpose(MessageFunctionEnum.Original);          // 19: defined in UNTDID 1225
//		order.setRequestedResponse("AC");                        // 20: defined in UNTDID 4343
//		or.addNote("type aka subjectCode", "String content");    // 21
/*
			<DELIVERY_DATE type="optional">  <!-- TODO type fehlt
				<DELIVERY_START_DATE>2020-01-30</DELIVERY_START_DATE>
				<DELIVERY_END_DATE>2020-01-30</DELIVERY_END_DATE>
			</DELIVERY_DATE>

 */
		or.setDeliveryDate("2020-01-30");

		ExpectedLine line = expected().get(0);
		OrderLine line1 = or.createOrderLine("1"       // order line number
		  , new Quantity(C62, new BigDecimal(2000))
		  , line.lna                                   // line net amount
		  , line.upa                                   // unit price
		  , line.name                                  // itemName
		  );
		line1.addStandardIdentifier(line.sid.getContent(), line.sid.getSchemeIdentifier()); // 43+44
		line1.setSellerAssignedID(line.sai);           // 45
		line1.setBuyerAssignedID(line.bai);            // 46
		line1.setDescription(line.desc);               // 50
		line1.setUnitPriceQuantity(line.upq);          // 180+181 (optional) price base quantity
		line1.setPartialDeliveryIndicator(OrderLine.YES); // 208
		line1.setDeliveryDate(line.delivery);             // 298
		line1.setTaxCategory(line.tcc);                   // 315
/*
			<CUSTOMER_ORDER_REFERENCE>
				<ORDER_ID>PLEX-141269</ORDER_ID>
				<LINE_ITEM_ID>1</LINE_ITEM_ID>
				<ORDER_DATE>2020-01-22</ORDER_DATE>
			</CUSTOMER_ORDER_REFERENCE>
 */
//		line1.createSupportigDocument("PLEX-141269", line.sdlr, null, line.sdts, null);
		line1.addReferencedDocument(ORDER_ID, line.sdlr, null, line.sdts, null);
		or.addLine(line1);

		PostalAddressExt sellerAddress = (PostalAddressExt) or.createAddress("FR", "54152", "Citoyene");
		sellerAddress.setAddressLine1(expectedSeller().al1);
		sellerAddress.setAddressLine2("Industry for dummy Manufacturing");
		sellerAddress.setStreet(expectedSeller().street);
		ContactInfo sellerContact = or.createContactInfo("Weber", "24257572524", "hans.weber@ultra.fyi");
		BusinessParty seller = or.createParty(null, sellerAddress.getAddressLine1(), sellerAddress, sellerContact);
		seller.setId("3661458000003", ILN);
		seller.setVATRegistrationId(expectedSeller().vatId);
		seller.setUriUniversalCommunication("https://www.ultra.fyi/", null);
		or.setSeller(seller);

		PostalAddressExt buyerAddress = (PostalAddressExt) or.createAddress("CH", "8500", "Frauenfeld");
		buyerAddress.setAddressLine1(expectedBuyer().al1);
		buyerAddress.setAddressLine2("Systeme für Elektrotechnik");
		buyerAddress.setStreet(expectedBuyer().street);
		ContactInfo buyerContact = or.createContactInfo("Marbach", "+41 52 723 67 34", "laura.marbach@plica.ch");
		BusinessParty buyer = or.createParty(null, buyerAddress.getAddressLine1(), buyerAddress, buyerContact);
		buyer.setId("7611577000008", ILN);
		buyer.setVATRegistrationId(expectedBuyer().vatId);
		buyer.setUriUniversalCommunication("www.plica.ch", null);
		or.setBuyer(buyer);
	
		PostalAddressExt shipToAddress = (PostalAddressExt) expectedShipTo().pa;
		shipToAddress.setAddressLine1(expectedShipTo().al1);
		shipToAddress.setAddressLine2("Systeme für Elektrotechnik");
		shipToAddress.setStreet(expectedShipTo().street);
		ContactInfo shipToContact = expectedShipTo().ci;
		BusinessParty shipTo = or.createParty(null, shipToAddress.getAddressLine1(), shipToAddress, shipToContact);
		shipTo.setIdentifier(expectedShipTo().id);
		shipTo.setVATRegistrationId(expectedShipTo().vatId);
		shipTo.setUriUniversalCommunication("www.plica.ch", null);
		or.setShipTo(shipTo);
		
		PostalAddressExt billToAddress = (PostalAddressExt) or.createAddress("CH", "8500", "Frauenfeld");
		billToAddress.setAddressLine1(expectedBillTo().al1);
		billToAddress.setAddressLine2("Systeme für Elektrotechnik");
		billToAddress.setStreet(expectedBillTo().street);
		ContactInfo billToContact = or.createContactInfo("Administration", "+41 52 723 61 11", "administration@janico.ch");
		BusinessParty billTo = or.createParty(null, billToAddress.getAddressLine1(), billToAddress, billToContact);
		billTo.setId("7611577000008", ILN);
		billTo.setVATRegistrationId(expectedBillTo().vatId);
		billTo.setUriUniversalCommunication("www.plica.ch", null);
		or.setBillTo(billTo);

		or.setDocumentCurrency("CHF");             // 790:
		
		or.createTotals(new Amount(new BigDecimal(1259.65)) // Sum of line net amount
		, new Amount(new BigDecimal(1259.65)) // total amount without VAT, aka Tax Basis
		, null);
		
		transformer = otTransformer;
		object = (Order)or;
//		marshal(null);
		marshal("PLD-TestResult"); // unmarshal + asserts
	}

}
