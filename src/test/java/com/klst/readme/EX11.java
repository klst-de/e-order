package com.klst.readme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.MessageFunctionEnum;
import com.klst.eorder.api.AbstactTransformer;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.impl.Amount;               // impl.jar
import com.klst.eorder.impl.CrossIndustryOrder;   // impl.jar
import com.klst.eorder.impl.ID;
import com.klst.eorder.impl.Quantity;
import com.klst.eorder.impl.TradeAddress;
import com.klst.eorder.impl.TradeContact;
import com.klst.eorder.impl.UnitPriceAmount;      // impl.jar
import com.klst.marshaller.CioTransformer;

public class EX11 {
	
	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = EX11.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(EX11.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(EX11.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(EX11.class.getName());		
	}
//	private static final Logger LOG = Logger.getLogger(OrderTest.class.getName());

	static private final String TESTDIR = "src/test/resources/";

	static final String EUR = "EUR";
	static final String C62 = "C62";
	static final String MTK = "MTK"; // m²
	static final String PRD = "PRD"; // UNTDID 4451: Product information

	// Coding Systems aka ICD Schemas : 
	// System Information et Repertoire des Entreprise et des Etablissements: SIRENE
	static final String SIRENE 		= "0002"; // ICD Schema for SIRENE
	static final String EAN_LOCO 	= "0088"; // ICD Schema for EAN Location Code
	static final String GTIN 		= "0160"; // Global Trade Item Number (GTIN)
	
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

	private File getTestFile(String uri) {
		File file = new File(uri);
		LOG.info("test file "+file.getAbsolutePath() + " canRead:"+file.canRead());
		return file;
	}

	private CoreOrder unmarshal(File testFile) {
		try {
			InputStream is = new FileInputStream(testFile);
			object = transformer.unmarshal(is);
			LOG.info("unmarshaled object:"+object);
			Class<?> type = Class.forName(com.klst.marshaller.CioTransformer.CONTENT_TYPE_NAME); // CrossIndustryOrder aus jar laden
			// dynamisch:
			return CoreOrder.class.cast(type.getConstructor(object.getClass()).newInstance(object));
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}
		return null;
	}

	private void marshal() {
		LOG.info("object.Class:"+object.getClass());
		
		byte[] xml = transformer.marshal(object);
		LOG.info(new String(xml));
		try {
			Path temp = Files.createTempFile("EX11-TestResult", ".xml");
			Files.write(temp, xml);
			LOG.info("written to "+temp);
			File testFile = getTestFile(temp.toString());
			CoreOrder cio = null;
			// unmarshal toModel:
			if(transformer.isValid(testFile)) {
				cio = unmarshal(testFile);
				doAssert(cio);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static final Timestamp issueDate = DateTimeFormats.ymdToTs("2020-01-09");
	private void doAssert(CoreOrder cio) {
		assertFalse(cio.isTest());                                        // 2
		assertEquals(BG2_ProcessControl.PROFILE_BASIC, cio.getProfile()); // 7
		assertEquals(DocumentNameCode.Order, cio.getDocumentCode());      // 11
		assertEquals(issueDate, cio.getIssueDateAsTimestamp());
		assertFalse(cio.isCopy());
		assertEquals(MessageFunctionEnum.Original, cio.getPurposeCode());
		assertEquals("AC", cio.getRequestedResponse()); // 20: "AC" to request an Order Response
		
		List<OrderLine> ol = cio.getLines();
		assertEquals(3, ol.size());
		
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
			//LOG.info("compare:"+((Quantity)(e.qty)).compareTo((Quantity)(l.getQuantity())));
			LOG.info("expected:"+e.upa + " =?= "+new UnitPriceAmount(l.getUnitPriceAmount().getValue()));
			assertEquals(e.upa.toString(), new UnitPriceAmount(l.getUnitPriceAmount().getValue()).toString());
			assertEquals(e.lna.toString(), l.getLineTotalAmount().toString());
			assertEquals(e.upq.toString(), l.getUnitPriceQuantity().toString());
			assertEquals(e.name, l.getItemName());
			assertEquals(e.pdi, l.isPartialDeliveryAllowed());
			assertEquals(e.sid.toString(), l.getStandardIdentifier().get(0).toString());
			assertEquals(e.sai, l.getSellerAssignedID());
			assertEquals(e.bai, l.getBuyerAssignedID());
		}
	}
	
	@Test
    public void readFile() {
		File testFile = getTestFile(TESTDIR+"ORDER-X_EX11_ORDER_PICK-UP-BASIC.xml");
		transformer = cioTransformer;
		CoreOrder cio = null;
		// unmarshal toModel:
		if(transformer.isValid(testFile)) {
			cio = unmarshal(testFile);
			doAssert(cio);
		}	
	}

	public class ExpectedLine {
		String id;
		OrderNote note = null;
		IQuantity qty;
		IAmount lna;         // line net amount
		UnitPriceAmount upa;
		IQuantity upq;       // UnitPriceQuantity
		String name;
		boolean pdi;         // PartialDeliveryIndicator
		Identifier sid;      // StandardIdentifier
		String sai;          // SellerAssignedID
		String bai;          // BuyerAssignedID
	}
	static final String NODE_CONTENT = "certifiés  PEFC mini 90% PEFC/10-34-97 ";
	ArrayList<ExpectedLine> expected() {
		ArrayList<ExpectedLine> lines = new ArrayList<ExpectedLine>(3);
		ExpectedLine line = new ExpectedLine();
		line.id = "1";
		line.note = CrossIndustryOrder.getFactory().createNote(PRD, NODE_CONTENT);
		line.qty = new Quantity(MTK, new BigDecimal(4.052));
		line.upa = new UnitPriceAmount(new BigDecimal(18.74));
		line.upq = new Quantity(MTK, new BigDecimal(1));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue())); // line net amount
		line.name = "HPL 0.8 mm  3070x1320";
		line.pdi = OrderLine.NO;
		line.sid = new ID("3607765426686", GTIN);
		line.sai = "542668";
		line.bai = "198765";
		lines.add(line);
		
		line = new ExpectedLine();
		line.id = "2";
		line.qty = new Quantity(C62, new BigDecimal(5));
		line.upa = new UnitPriceAmount(new BigDecimal(89.03));
		line.upq = new Quantity(C62, new BigDecimal(1));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue())); // line net amount
		line.name = "wedi Kit d'étanchéité Fundo";
		line.pdi = OrderLine.YES;
		line.sid = new ID("4024125000178", GTIN);
		line.sai = "73796000";
		line.bai = "186954";
		lines.add(line);
		
		line = new ExpectedLine();
		line.id = "3";
		line.qty = new Quantity(C62, new BigDecimal(5));
		line.upa = new UnitPriceAmount(new BigDecimal(208.02));
		line.upq = new Quantity(C62, new BigDecimal(1));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue())); // line net amount
		line.name = "RSS SCD CHROME 0500W";
		line.pdi = OrderLine.YES;
		line.sid = new ID("3546335717048", GTIN);
		line.sai = "571704";
		line.bai = "125965";
		lines.add(line);
		
		return lines;
	}
	
	@Test
	public void create() {
		CoreOrder order;
		order = CrossIndustryOrder.getFactory().createOrder(BG2_ProcessControl.PROFILE_BASIC, "A1", DocumentNameCode.Order);
		order.setTestIndicator(CoreOrder.PROD);                  // 2:
		order.setId("1861728");                                  // 9: BT-1 Identifier (mandatory)
		order.setIssueDate(DateTimeFormats.ymdToTs("20200109")); // 14: BT-2
		order.setCopyIndicator(!CoreOrder.COPY);                 // 16:
		order.setPurpose(MessageFunctionEnum.Original);          // 19: defined in UNTDID 1225
		order.setRequestedResponse("AC");                        // 20: defined in UNTDID 4343

		// calculate line 1 net amount:
		UnitPriceAmount upa = new UnitPriceAmount(new BigDecimal(18.74));
		Quantity qty = new Quantity(MTK, new BigDecimal(4.052));
		Amount line1net = new Amount(upa.getValue().multiply(qty.getValue()));
		
		OrderLine line1 = order.createOrderLine("1"    // order line number
		  , new Quantity(MTK, new BigDecimal(4.052))   // m²
//		  , new Amount(new BigDecimal(75.93))          // line net amount
		  , line1net         // line net amount
		  , new UnitPriceAmount(new BigDecimal(18.74)) // price
		  , "HPL 0.8 mm  3070x1320"                    // itemName
		  );
		line1.addNote(PRD, NODE_CONTENT);                                 // 37
		line1.addStandardIdentifier("3607765426686", GTIN);               // 43+44
		line1.setSellerAssignedID("542668");                              // 45
		line1.setBuyerAssignedID("198765");                               // 46
		line1.setUnitPriceQuantity(new Quantity(MTK, new BigDecimal(1))); // 180+181 (optional) price base quantity
		line1.setPartialDeliveryIndicator(OrderLine.NO);                  // 208
		order.addLine(line1);
	
		OrderLine line2 = order.createOrderLine("2"    // order line number
		  , new Quantity(C62, new BigDecimal(5))       // 5 units/C62
		  , new Amount(new BigDecimal(445.15))         // line net amount
		  , new UnitPriceAmount(new BigDecimal(89.03)) // price
		  , "wedi Kit d'étanchéité Fundo"              // itemName
		  );
		line2.addStandardIdentifier("4024125000178", GTIN);
		line2.setSellerAssignedID("73796000");
		line2.setBuyerAssignedID("186954");
		line2.setUnitPriceQuantity(new Quantity(C62, new BigDecimal(1)));
		line2.setPartialDeliveryIndicator(OrderLine.YES);
		order.addLine(line2);
	
		OrderLine line3 = order.createOrderLine("3"     // order line number
		  , new Quantity(C62, new BigDecimal(5))        // 5 units/C62
		  , new Amount(new BigDecimal(1040.1))          // line net amount
		  , new UnitPriceAmount(new BigDecimal(208.02)) // price
		  , "RSS SCD CHROME 0500W"                      // itemName
		  );
		line3.addStandardIdentifier("3546335717048", GTIN);
		line3.setSellerAssignedID("571704");
		line3.setBuyerAssignedID("125965");
		line3.setUnitPriceQuantity(new Quantity(C62, new BigDecimal(1))); // (optional) price base quantity
		line3.setPartialDeliveryIndicator(OrderLine.YES);
		order.addLine(line3);

		PostalAddress sellerAddress = TradeAddress.create().createAddress(
			"FR", "44100", "NANTES");
		sellerAddress.setAddressLine1("12 RUE DE LA FONTAINE SALEE");
		String sellerName = "DMBP NANTES DISPANO ROUX - 1535"; // 349: BG-4.BT-27
		BusinessParty seller = order.createParty(sellerName
		  , null
		  , sellerAddress
		  , null);
		seller.setId("3020816001302", EAN_LOCO);
		seller.setCompanyId("50810215900334", SIRENE);
		seller.setVATRegistrationId("FR86508102159");
		order.setSeller(seller);

		PostalAddress buyerAddress = TradeAddress.create().createAddress(
			"FR", "01500", "Amberieu en bugey");
		buyerAddress.setAddressLine1("Avenue Leon Blum");
		ContactInfo contact = TradeContact.create().createContactInfo("ALAIN DUPOND"
			, "06 78 56 23 00"
			, "alain.dupond@saint-gobain.com"
			);
		BusinessParty buyer = order.createParty(
			"AMBERIEU EN BUGEY CEDEO" // 394: BG-7.BT-44  1..1 Buyer name
		  , null
		  , buyerAddress
		  , contact
		  );
		buyer.setId("3306949923804", EAN_LOCO);
		buyer.setCompanyId("57214188502180", SIRENE);
		buyer.setVATRegistrationId("FR94572141885");
		buyer.setUriUniversalCommunication("alain.dupond@saint-gobain.com", "EM");
		order.setBuyer(buyer);

		BusinessParty shipFrom = order.createParty(sellerName
		  , null
		  , sellerAddress
		  , null);
		shipFrom.setId("3020816001302", EAN_LOCO);
		order.setShipFromParty(shipFrom);
		
		order.setPickupDate("2020-01-15 09:00:00"); // 778: format="203"
		order.setDocumentCurrency(EUR);             // 790:
		
		order.createTotals(new Amount(new BigDecimal(1561.18)) // Sum of line net amount
		, new Amount(new BigDecimal(1561.18))
		, null);
		
		transformer = cioTransformer;
		object = order;
		marshal();
	}

}
