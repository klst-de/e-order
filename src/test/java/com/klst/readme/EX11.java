package com.klst.readme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.MessageFunctionEnum;
import com.klst.eorder.api.AbstactTransformer;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.impl.Amount;               // impl.jar
import com.klst.eorder.impl.CrossIndustryOrder;   // impl.jar
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
//		writeBytesToFile(xml, "EX11-TestResult.xml");
	}

	static final Timestamp issueDate = DateTimeFormats.ymdToTs("2020-01-09");

	@Test
    public void readFile() {
		File testFile = getTestFile(TESTDIR+"ORDER-X_EX11_ORDER_PICK-UP-BASIC.xml");
		transformer = cioTransformer;
		CoreOrder cio = null;
		// unmarshal toModel:
		if(transformer.isValid(testFile)) {
			cio = unmarshal(testFile);
		}
		assertFalse(cio.isTest());                                        // 2
		assertEquals(BG2_ProcessControl.PROFILE_BASIC, cio.getProfile()); // 7
		assertEquals(DocumentNameCode.Order, cio.getDocumentCode());      // 11
		assertEquals(issueDate, cio.getIssueDateAsTimestamp());
		assertFalse(cio.isCopy());
		assertEquals(MessageFunctionEnum.Original, cio.getPurposeCode());
		assertEquals("AC", cio.getRequestedResponse()); // 20: "AC" to request an Order Response
		
		assertEquals(3, cio.getLines().size());
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
		line1.addNote(PRD, "certifiés  PEFC mini 90% PEFC/10-34-97");
		line1.setUnitPriceQuantity(new Quantity(MTK, new BigDecimal(1))); // (optional) price base quantity
		line1.setPartialDeliveryIndicator(OrderLine.NO);
		line1.addStandardIdentifier("3607765426686", GTIN);
		line1.setSellerAssignedID("542668");
		line1.setBuyerAssignedID("198765");
		order.addLine(line1);
	
		OrderLine line2 = order.createOrderLine("2"    // order line number
		  , new Quantity(C62, new BigDecimal(5))       // 5 units/C62
		  , new Amount(new BigDecimal(445.15))         // line net amount
		  , new UnitPriceAmount(new BigDecimal(89.03)) // price
		  , "wedi Kit d'étanchéité Fundo"              // itemName
		  );
		line2.setUnitPriceQuantity(new Quantity(C62, new BigDecimal(1))); // (optional) price base quantity
		line2.setPartialDeliveryIndicator(OrderLine.YES);
		line2.addStandardIdentifier("4024125000178", GTIN);
		line2.setSellerAssignedID("73796000");
		line2.setBuyerAssignedID("186954");
		order.addLine(line2);
	
		OrderLine line3 = order.createOrderLine("3"     // order line number
		  , new Quantity(C62, new BigDecimal(5))        // 5 units/C62
		  , new Amount(new BigDecimal(1040.1))          // line net amount
		  , new UnitPriceAmount(new BigDecimal(208.02)) // price
		  , "RSS SCD CHROME 0500W"                      // itemName
		  );
		line3.setUnitPriceQuantity(new Quantity(C62, new BigDecimal(1))); // (optional) price base quantity
		line3.setPartialDeliveryIndicator(OrderLine.YES);
		line3.addStandardIdentifier("3546335717048", GTIN);
		line3.setSellerAssignedID("571704");
		line3.setBuyerAssignedID("125965");
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
