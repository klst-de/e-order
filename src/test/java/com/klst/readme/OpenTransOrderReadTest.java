package com.klst.readme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.marshaller.AbstactTransformer;
import com.klst.marshaller.CioTransformer;
import com.klst.marshaller.OpenTransTransformer;

public class OpenTransOrderReadTest {

	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = OpenTransOrderReadTest.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(OpenTransOrderReadTest.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(OpenTransOrderReadTest.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(OpenTransOrderReadTest.class.getName());		
	}
	static private final String TESTDIR = "src/test/resources/";
	static private final String EUR = "EUR";

	static private AbstactTransformer cioTransformer;
	static private AbstactTransformer otTransformer;
	static private AbstactTransformer transformer;

    @BeforeClass
    public static void staticSetup() {
    	initLogger();
    }

	Object object;
	private File testFile;
	
	@Before 
    public void setup() {
	   	cioTransformer = CioTransformer.getInstance();
	   	otTransformer = OpenTransTransformer.getInstance();
	   	object = null;
    }

	private File getTestFile(String uri) {
		File file = new File(uri);
		LOG.info("test file "+file.getAbsolutePath() + " canRead:"+file.canRead());
		return file;
	}

	@Test
    public void cioEX01() {
		testFile = getTestFile(TESTDIR+"ORDER-X_EX01_ORDER_FULL_DATA-BASIC.xml");
		transformer = otTransformer;
		CoreOrder cio = null;
		// toModel:
		if(transformer.isValid(testFile)) {
			// expected not valid
		}
		// not valid
		transformer = cioTransformer;
		if(transformer.isValid(testFile)) {
			try {
				InputStream is = new FileInputStream(testFile);
				object = transformer.toModel(is);
				LOG.info(">>>>"+object);
				Class<?> type = Class.forName(com.klst.marshaller.CioTransformer.CONTENT_TYPE_NAME); // CrossIndustryOrder aus jar laden
				// dynamisch:
				cio = CoreOrder.class.cast(type.getConstructor(object.getClass()).newInstance(object));
			} catch (Exception ex) {
				ex.printStackTrace();
				LOG.severe(ex.getMessage());
			}
		}
		assertEquals(BG2_ProcessControl.PROFILE_BASIC, cio.getProfile());
		assertEquals(DocumentNameCode.Order, cio.getDocumentCode());
		
		assertEquals("BUYER_REF_BU123", cio.getBuyerReferenceValue());
		
//		BusinessParty seller = cio.getSeller();
//		BusinessParty buyer = cio.getBuyer();
//		BusinessParty shipToParty = cio.getShipToParty();
//		BusinessParty shipFromParty = cio.getShipFromParty();
//		LOG.info("Seller:"+seller);
//		LOG.info("Buyer:"+buyer);
//		
//		assertEquals("FCA", cio.getDeliveryType()); // UNTDID 4053 FCA : Free Carrier
//		assertEquals("7", cio.getDeliveryFunctionCode()); // UNTDID 4055 7 Delivered by the supplier
//
//		assertEquals("PO123456789", cio.getPurchaseOrderReference());
//		assertEquals("QUOT_125487", cio.getQuotationReference());
//		assertEquals("CONTRACT_2020-25987", cio.getContractReference());
//		assertEquals("BLANKET_ORDER_ID", cio.getBlanketOrderReference());
///*
//               <ram:PreviousOrderChangeReferencedDocument>
//                    <ram:IssuerAssignedID>PREV_ORDER_C_ID</ram:IssuerAssignedID>
//               </ram:PreviousOrderChangeReferencedDocument>
//               <ram:PreviousOrderResponseReferencedDocument>
//                    <ram:IssuerAssignedID>PREV_ORDER_R_ID</ram:IssuerAssignedID>
//               </ram:PreviousOrderResponseReferencedDocument>
//               <ram:SpecifiedProcuringProject>                     <!-- BT-11 nicht in BASIC / TODO test COMFORT
//                    <ram:ID>PROJECT_ID</ram:ID>
//                    <ram:Name>Project Reference</ram:Name>
//               </ram:SpecifiedProcuringProject>
// */
//		assertEquals("PREV_ORDER_C_ID", cio.getPreviousOrderChangeReference());
//		assertEquals("PREV_ORDER_R_ID", cio.getPreviousOrderResponseReference());
//		assertNull(cio.getProjectReference());
//		
//		assertEquals("20200415", DateTimeFormats.tsToCCYYMMDD(cio.getDeliveryDateAsTimestamp()));
//		IPeriod deliveryPeriod = cio.getDeliveryPeriod();
//		assertEquals("20200415", DateTimeFormats.tsToCCYYMMDD(deliveryPeriod.getStartDateAsTimestamp()));
//		assertEquals("20200430", DateTimeFormats.tsToCCYYMMDD(deliveryPeriod.getEndDateAsTimestamp()));
//		
///*
//		<ram:ApplicableHeaderTradeSettlement>
//			<ram:OrderCurrencyCode>EUR</ram:OrderCurrencyCode>
//			<ram:SpecifiedTradeSettlementHeaderMonetarySummation>
//				<ram:LineTotalAmount>310.00</ram:LineTotalAmount>
//				<ram:ChargeTotalAmount>21.00</ram:ChargeTotalAmount>
//				<ram:AllowanceTotalAmount>31.00</ram:AllowanceTotalAmount>
//				<ram:TaxBasisTotalAmount>300.00</ram:TaxBasisTotalAmount>
//				<ram:TaxTotalAmount currencyID="EUR">60.00</ram:TaxTotalAmount>
//				<ram:GrandTotalAmount>360.00</ram:GrandTotalAmount>
//			</ram:SpecifiedTradeSettlementHeaderMonetarySummation>
//			<ram:ReceivableSpecifiedTradeAccountingAccount>
//				<ram:ID>BUYER_ACCOUNT_REF</ram:ID>
//			</ram:ReceivableSpecifiedTradeAccountingAccount>
//		</ram:ApplicableHeaderTradeSettlement>
// */
//		assertEquals(EUR, cio.getDocumentCurrency());
//		assertEquals(0, new BigDecimal(310).compareTo(cio.getLineNetTotal().getValue(RoundingMode.UNNECESSARY)));
//		assertEquals(0, new BigDecimal( 21).compareTo(cio.getChargesTotal().getValue(RoundingMode.UNNECESSARY)));
//		assertEquals(0, new BigDecimal( 31).compareTo(cio.getAllowancesTotal().getValue(RoundingMode.UNNECESSARY)));
//		assertEquals(0, new BigDecimal(300).compareTo(cio.getTotalTaxExclusive().getValue(RoundingMode.UNNECESSARY)));
//		IAmount taxTotal = cio.getTaxTotal();		
//		assertEquals(0, new BigDecimal( 60).compareTo(taxTotal.getValue(RoundingMode.UNNECESSARY)));
//		assertEquals(EUR, taxTotal.getCurrencyID());
//		assertEquals(0, new BigDecimal(360).compareTo(cio.getTotalTaxInclusive().getValue(RoundingMode.UNNECESSARY)));
//		assertEquals("BUYER_ACCOUNT_REF", cio.getBuyerAccountingReference().getName());

	}

	private CoreOrder getCoreOrder(File testFile) {
		try {
			InputStream is = new FileInputStream(testFile);
			object = transformer.toModel(is);
			LOG.info(">>>>"+object);
			Class<?> type = Class.forName(com.klst.marshaller.OpenTransTransformer.CONTENT_TYPE_NAME); // openTrans.Order aus jar laden
//			Class<?> type = Class.forName(transformer.getCONTENT_TYPE_NAME); // ????? openTrans.Order aus jar laden
			return CoreOrder.class.cast(type.getConstructor(object.getClass()).newInstance(object));
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}
		return null;
	}

	// ein paar Beispiele: https://github.com/zdavatz/yopenedi/tree/master/yopenedi_dokumentation/Musternachrichten%20OpenTrans%202.1
	@Test
    public void pldOrder() {
		File testFile = getTestFile(TESTDIR+"PLD_257444_Order_example.XML");
		transformer = otTransformer;
		CoreOrder cio = null;	
		if(transformer.isValid(testFile)) {
			cio = getCoreOrder(testFile);
		}
		
		BusinessParty seller = cio.getSeller();
		LOG.info("seller:"+seller);
		BusinessParty buyer = cio.getBuyer();
		LOG.info("buyer:"+buyer);
	
		LOG.info("\n");
	}
	
	@Test
    public void otOrder() {
		File testFile = getTestFile(TESTDIR+"sample_order_opentrans_2_1.xml");
		transformer = otTransformer;
		CoreOrder cio = null;	
		if(transformer.isValid(testFile)) {
			cio = getCoreOrder(testFile);
		}
		
		BusinessParty seller = cio.getSeller();
		LOG.info("seller:"+seller);
		BusinessParty buyer = cio.getBuyer();
		LOG.info("buyer:"+buyer);
//	<ADDRESS>
//		<bmecat:NAME>ADDRESS.NAME</bmecat:NAME>
//		<bmecat:NAME2>ADDRESS.NAME2</bmecat:NAME2>
//		<bmecat:NAME3>ADDRESS.NAME3</bmecat:NAME3>
//		<bmecat:DEPARTMENT>ADDRESS.DEPARTMENT</bmecat:DEPARTMENT> <----------- ContactInfoExt.ContactDepartment TODO
		assertEquals("ADDRESS.NAME", buyer.getAddress().getAddressLine1());
		assertEquals("ADDRESS.NAME2", buyer.getAddress().getAddressLine2());
		assertEquals("ADDRESS.NAME3", buyer.getAddress().getAddressLine3());
		LOG.info("VATRegistrationId/Umsatzsteuer-ID:"+buyer.getVATRegistrationId()); // mit vorangestelltem Ländercode
		// ??? Steuernummer : Steuernummer
// Das Format der Steuernummer beim Finanzamt variiert ab und an  von Bundesland zu Bundesland. Sie beinhaltet mal 10 und mal 11 Zahlen, die durch Schrägstriche in 3 Teile zerlegt wird. 
		
		
		LOG.info("getIssueDateAsTimestamp:"+cio.getIssueDateAsTimestamp());
		// 2009-05-13 07:20:00.0 test ohne Time:
		assertEquals("20090513", DateTimeFormats.tsToCCYYMMDD(cio.getIssueDateAsTimestamp()));
		assertEquals("2009-05-13T07:20:00+02:00", DateTimeFormats.tsTodtDATETIME(cio.getIssueDateAsTimestamp()));
		assertNotNull(cio.getDeliveryDateAsTimestamp());
		assertEquals("20090520", DateTimeFormats.tsToCCYYMMDD(cio.getDeliveryDateAsTimestamp()));
		
		PostalAddress pa = buyer.getAddress();
				pa.setAddressLine1("AddressLine1");
		LOG.info("pa.AddressLine1:"+pa.getAddressLine1());
		
		List<OrderLine> lines = cio.getLines();
		assertEquals(1, lines.size());
		OrderLine line = lines.get(0);
		assertEquals("1", line.getId());               // BT-126
		assertEquals("a", line.getSellerAssignedID()); // BG-31.BT-155 0..1 ohne type/Schema : upc
		List<Identifier> stdIDs = line.getStandardIdentifier(); // BG-31.BT-157 0..n
		LOG.info("StandardIdentifier:"+stdIDs.get(0));          // ... mit type/Schema
		assertEquals("gtin", stdIDs.get(0).getSchemeIdentifier());
		assertEquals("a", stdIDs.get(0).getContent());
		assertEquals(1, stdIDs.size());
		IPeriod lineDeliveryPeriod = line.getLineDeliveryPeriod();
		assertEquals("20200130", DateTimeFormats.tsToCCYYMMDD(lineDeliveryPeriod.getStartDateAsTimestamp()));
		assertEquals(DateTimeFormats.ymdToTs("2020-02-15"), lineDeliveryPeriod.getEndDateAsTimestamp());
 
		LOG.info("\n");
	}
	
}
