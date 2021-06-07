package com.klst.readme;

import static org.junit.Assert.assertEquals;
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
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.AbstactTransformer;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.SupportingDocument;
import com.klst.eorder.impl.Amount;               // impl.jar
import com.klst.eorder.impl.ID;
import com.klst.eorder.impl.Quantity;
import com.klst.eorder.impl.UnitPriceAmount;      // impl.jar
import com.klst.eorder.openTrans.Address;
import com.klst.eorder.openTrans.OrderResponse;
import com.klst.eorder.openTrans.PartyID;
import com.klst.marshaller.OpenTransOrderResponseTransformer;
import com.klst.test.CommonUtils;

public class PLD_1051712 extends Constants {
	
	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = PLD_1051712.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(PLD_1051712.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(PLD_1051712.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(PLD_1051712.class.getName());		
	}
//	private static final Logger LOG = Logger.getLogger(PLD_1051712.class.getName());
	
	static private AbstactTransformer otTransformer;
	static private AbstactTransformer transformer;
	Object object;

    @BeforeClass
    public static void staticSetup() {
    	initLogger();
    }

	@Before 
    public void setup() {
	   	otTransformer = OpenTransOrderResponseTransformer.getInstance();
	   	object = null;
    }

	private CoreOrder unmarshal(File testFile) {
		try {
			InputStream is = new FileInputStream(testFile);
			object = transformer.unmarshal(is);
			LOG.info("unmarshaled object:"+object);
			Class<?> type = Class.forName(com.klst.marshaller.OpenTransOrderResponseTransformer.CONTENT_TYPE_NAME); // OT aus jar laden
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

	static final Timestamp issueDate = DateTimeFormats.ymdToTs("2018-06-21");
	private void doAssert(CoreOrder cio) {
//		assertFalse(cio.isTest());                                           // 2
//		assertEquals(BG2_ProcessControl.PROFILE_BASIC, cio.getProfile());    // 7
		assertEquals(DocumentNameCode.OrderResponse, cio.getDocumentCode()); // 11
		assertEquals(issueDate, cio.getIssueDateAsTimestamp());              // 14
//		assertFalse(cio.isCopy());
//		assertEquals(MessageFunctionEnum.Original, cio.getPurposeCode());    // 19
//		assertEquals(AC, cio.getRequestedResponse());                        // 20
//		
		List<OrderLine> ol = cio.getLines();
		assertEquals(1, ol.size());
		
		ArrayList<ExpectedLine> exp = expected();
		for(int i=0; i<ol.size(); i++) {
			OrderLine l = ol.get(i);
			LOG.info("line "+i + ":"+l);
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
//			assertTrue(sdList.isEmpty()); // not in ORDERRESPONSEITEM, daher:
			assertNull(sdList); // CUSTOMERORDERREFERENCE not in ORDERRESPONSEITEM
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
		File testFile = CommonUtils.getTestFile(TESTDIR+"PLD_1051712_OrderResponse_example.XML");
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
		bp.id = new PartyID("7611007000004", ILN);
		bp.al1 = "Adi A. Dassler AG";
		bp.bpn = bp.al1;
		
		bp.pa = Address.create().createAddress("CH", "8957", "Spreitenbach");		
		bp.street = "Zum breiten Streifen 8";
		return bp;
	}
	ExpectedBP expectedShipTo() {
		return expectedBuyer();
	}
	ExpectedBP expectedBillTo() {
		ExpectedBP bp = new ExpectedBP();
		bp.id = new PartyID("7611007000004", ILN);
		bp.al1 = "A. Steffen AG";
		bp.bpn = bp.al1;
		
		bp.pa = Address.create().createAddress("CH", "8957", "Spreitenbach");		
		bp.street = "Limmatstrasse 8";
		return bp;
	}
	ExpectedBP expectedSeller() {
		ExpectedBP bp = new ExpectedBP();
		bp.id = new PartyID("7611577000008", ILN);
		bp.al1 = "Plica AG";
		bp.bpn = bp.al1;
		
		bp.pa = Address.create().createAddress("CH", "8500", "Frauenfeld");
		bp.street = "Zürcherstrasse 350";
		return bp;
	}
	
	ArrayList<ExpectedLine> expected() {
		ArrayList<ExpectedLine> lines = new ArrayList<ExpectedLine>(3);
		ExpectedLine line = new ExpectedLine();
		line.id = "1";
		line.qty = new Quantity(MTR, new BigDecimal(680));
		line.upa = new UnitPriceAmount(new BigDecimal(172));
		line.upq = new Quantity(null, new BigDecimal(100));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue()).divide(line.upq.getValue()));
		line.name = "PLICALU";
		line.desc = "PLICALU SME M25, Steckrohr Alu 20m, Stangen à 2m";
		line.sid = new ID("7611577132396", EAN);
		line.sai = "134602025";
		line.bai = "0802525 2";
		line.delivery = Timestamp.valueOf("2018-06-26"+_HMS);
		lines.add(line);
		return lines;
	}
	
	@Test
	public void xcreate() {
		CoreOrder or;
		or = OrderResponse.create().createOrder("???", DocumentNameCode.OrderResponse);
		or.setTestIndicator(CoreOrder.PROD);                     // 2: ignored in OT
		or.setId("180008092");                                   // 9: BT-1 Identifier (mandatory)
		or.setIssueDate(DateTimeFormats.ymdToTs("2018-06-21"));  // 14: BT-2
		// liefert <ORDERRESPONSE_DATE>2018-06-21T00:00:00+02:00<
//		order.setCopyIndicator(!CoreOrder.COPY);                 // 16:
//		order.setPurpose(MessageFunctionEnum.Original);          // 19: defined in UNTDID 1225
//		order.setRequestedResponse("AC");                        // 20: defined in UNTDID 4343
//		or.addNote("type aka subjectCode", "String content");    // 21
		or.setDeliveryDate("2018-06-26");
		
		ExpectedLine line = expected().get(0);
		OrderLine line1 = or.createOrderLine("1"       // order line number
		  , new Quantity(MTR, new BigDecimal(680))
		  , new Amount(new BigDecimal(1169.6))         // line net amount
		  , new UnitPriceAmount(new BigDecimal(172))   // price
		  , "PLICALU"                                  // itemName
		  );
		line1.addStandardIdentifier(line.sid.getContent(), line.sid.getSchemeIdentifier()); // 43+44
		line1.setSellerAssignedID(line.sai);           // 45
		line1.setBuyerAssignedID(line.bai);            // 46
		line1.setDescription(line.desc);               // 50
		line1.setUnitPriceQuantity(line.upq);          // 180+181 (optional) price base quantity
		line1.setDeliveryDate(line.delivery);          // 298
		or.addLine(line1);

		PostalAddress sellerAddress = or.createAddress("CH", "8500", "Frauenfeld");
		sellerAddress.setAddressLine1(expectedSeller().al1);
		BusinessParty seller = or.createParty(null, sellerAddress.getAddressLine1(), sellerAddress, null);
		seller.setId("7611577000008", ILN);
//		seller.setVATRegistrationId("CHE-103.663.775 MWST");
		or.setSeller(seller);
//		or.setSeller(expectedSeller().al1, sellerAddress, null, "7611577000008", null);


		PostalAddress buyerAddress = or.createAddress("CH", "8957", "Spreitenbach");
		buyerAddress.setAddressLine1(expectedBuyer().al1);
		BusinessParty buyer = or.createParty(null, buyerAddress.getAddressLine1(), buyerAddress, null);
		buyer.setId("7611007000004", ILN);
		or.setBuyer(buyer);
		
		PostalAddress shipToAddress = or.createAddress("CH", "8957", "Spreitenbach");
		shipToAddress.setAddressLine1(expectedShipTo().al1);
		BusinessParty shipTo = or.createParty(null, shipToAddress.getAddressLine1(), shipToAddress, null);
		shipTo.setId("7611007000004", ILN);
		or.setShipTo(shipTo);
		
		PostalAddress billToAddress = or.createAddress("CH", "8957", "Spreitenbach");
		billToAddress.setAddressLine1(expectedBillTo().al1);
		BusinessParty billTo = or.createParty(null, billToAddress.getAddressLine1(), billToAddress, null);
		billTo.setId("7611007000004", ILN);
		or.setBillTo(billTo);

		or.setDocumentCurrency("CHF");             // 790:
		
		or.createTotals(new Amount(new BigDecimal(1259.65)) // Sum of line net amount
		, new Amount(new BigDecimal(1259.65)) // total amount without VAT, aka Tax Basis
		, null);
		
		transformer = otTransformer;
		object = (OrderResponse)or;
//		marshal(null);
		marshal("PLD-TestResult"); // unmarshal + asserts
	}

}
