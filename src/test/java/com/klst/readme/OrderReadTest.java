package com.klst.readme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.marshaller.AbstactTransformer;
import com.klst.marshaller.CioTransformer;

public class OrderReadTest {

	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = OrderReadTest.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(OrderReadTest.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(OrderReadTest.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(OrderReadTest.class.getName());		
	}
	static private final String TESTDIR = "src/test/resources/";
	static private final String EUR = "EUR";

	static private AbstactTransformer cioTransformer;
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
	   	object = null;
    }

	private File getTestFile(String uri) {
		File file = new File(uri);
		LOG.info("test file "+file.getAbsolutePath() + " canRead:"+file.canRead());
		return file;
	}

//	@Test
    public void cioEX01_BASIC() {
		testFile = getTestFile(TESTDIR+"ORDER-X_EX01_ORDER_FULL_DATA-BASIC.xml");
		transformer = cioTransformer;
		CoreOrder cio = null;
		// toModel:
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
		
		BusinessParty seller = cio.getSeller();
		BusinessParty buyer = cio.getBuyer();
		BusinessParty shipToParty = cio.getShipToParty();
		BusinessParty shipFromParty = cio.getShipFromParty();
		LOG.info("Seller:"+seller);
		LOG.info("Buyer:"+buyer);
		
		assertEquals("FCA", cio.getDeliveryType()); // UNTDID 4053 FCA : Free Carrier
		assertEquals("7", cio.getDeliveryFunctionCode()); // UNTDID 4055 7 Delivered by the supplier

		assertEquals("PO123456789", cio.getPurchaseOrderReference());
		assertEquals("QUOT_125487", cio.getQuotationReference());
		assertEquals("CONTRACT_2020-25987", cio.getContractReference());
		assertEquals("BLANKET_ORDER_ID", cio.getBlanketOrderReference());
/*
               <ram:PreviousOrderChangeReferencedDocument>
                    <ram:IssuerAssignedID>PREV_ORDER_C_ID</ram:IssuerAssignedID>
               </ram:PreviousOrderChangeReferencedDocument>
               <ram:PreviousOrderResponseReferencedDocument>
                    <ram:IssuerAssignedID>PREV_ORDER_R_ID</ram:IssuerAssignedID>
               </ram:PreviousOrderResponseReferencedDocument>
               <ram:SpecifiedProcuringProject>                     <!-- BT-11 nicht in BASIC / TODO test COMFORT
                    <ram:ID>PROJECT_ID</ram:ID>
                    <ram:Name>Project Reference</ram:Name>
               </ram:SpecifiedProcuringProject>
 */
		assertEquals("PREV_ORDER_C_ID", cio.getPreviousOrderChangeReference());
		assertEquals("PREV_ORDER_R_ID", cio.getPreviousOrderResponseReference());
		assertNull(cio.getProjectReference());
		
		assertEquals("20200415", DateTimeFormats.tsToCCYYMMDD(cio.getDeliveryDateAsTimestamp()));
		IPeriod deliveryPeriod = cio.getDeliveryPeriod();
		assertEquals("20200415", DateTimeFormats.tsToCCYYMMDD(deliveryPeriod.getStartDateAsTimestamp()));
		assertEquals("20200430", DateTimeFormats.tsToCCYYMMDD(deliveryPeriod.getEndDateAsTimestamp()));
		
/*
		<ram:ApplicableHeaderTradeSettlement>
			<ram:OrderCurrencyCode>EUR</ram:OrderCurrencyCode>
			<ram:SpecifiedTradeSettlementHeaderMonetarySummation>
				<ram:LineTotalAmount>310.00</ram:LineTotalAmount>
				<ram:ChargeTotalAmount>21.00</ram:ChargeTotalAmount>
				<ram:AllowanceTotalAmount>31.00</ram:AllowanceTotalAmount>
				<ram:TaxBasisTotalAmount>300.00</ram:TaxBasisTotalAmount>
				<ram:TaxTotalAmount currencyID="EUR">60.00</ram:TaxTotalAmount>
				<ram:GrandTotalAmount>360.00</ram:GrandTotalAmount>
			</ram:SpecifiedTradeSettlementHeaderMonetarySummation>
			<ram:ReceivableSpecifiedTradeAccountingAccount>
				<ram:ID>BUYER_ACCOUNT_REF</ram:ID>
			</ram:ReceivableSpecifiedTradeAccountingAccount>
		</ram:ApplicableHeaderTradeSettlement>
 */
		assertEquals(EUR, cio.getDocumentCurrency());
		assertEquals(0, new BigDecimal(310).compareTo(cio.getLineNetTotal().getValue(RoundingMode.UNNECESSARY)));
		assertEquals(0, new BigDecimal( 21).compareTo(cio.getChargesTotal().getValue(RoundingMode.UNNECESSARY)));
		assertEquals(0, new BigDecimal( 31).compareTo(cio.getAllowancesTotal().getValue(RoundingMode.UNNECESSARY)));
		assertEquals(0, new BigDecimal(300).compareTo(cio.getTotalTaxExclusive().getValue(RoundingMode.UNNECESSARY)));
		IAmount taxTotal = cio.getTaxTotal();		
		assertEquals(0, new BigDecimal( 60).compareTo(taxTotal.getValue(RoundingMode.UNNECESSARY)));
		assertEquals(EUR, taxTotal.getCurrencyID());
		assertEquals(0, new BigDecimal(360).compareTo(cio.getTotalTaxInclusive().getValue(RoundingMode.UNNECESSARY)));
		assertEquals("BUYER_ACCOUNT_REF", cio.getBuyerAccountingReference().getName());
		
		List<OrderLine> lines = cio.getLines();
		assertEquals(3, lines.size());
		lines.forEach(line -> {
			assertNull(line.getLineDeliveryPeriod());
		});
	}
	
	public CoreOrder getCoreOrder(File testFile) {
		try {
			InputStream is = new FileInputStream(testFile);
			object = transformer.toModel(is);
			LOG.info(">>>>"+object);
			Class<?> type = Class.forName(com.klst.marshaller.CioTransformer.CONTENT_TYPE_NAME); // CrossIndustryOrder aus jar laden
			// dynamisch:
			return CoreOrder.class.cast(type.getConstructor(object.getClass()).newInstance(object));
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}
		return null;
	}
	
	@Test
    public void cioEX01_COMFORT() {
		File testFile = getTestFile(TESTDIR+"ORDER-X_EX01_ORDER_FULL_DATA-COMFORT.xml");
		transformer = cioTransformer;
		CoreOrder cio = null;
		// toModel:
		if(transformer.isValid(testFile)) {
			cio = getCoreOrder(testFile);
		}
		
		assertEquals(BG2_ProcessControl.PROFILE_COMFORT, cio.getProfile());
		assertEquals(DocumentNameCode.Order, cio.getDocumentCode());

		List<OrderLine> lines = cio.getLines();
		assertEquals(3, lines.size());
		OrderLine line = lines.get(0);
		assertEquals("20200415", DateTimeFormats.tsToCCYYMMDD(line.getLineDeliveryPeriod().getStartDateAsTimestamp()));
		assertEquals("20200430", DateTimeFormats.tsToCCYYMMDD(line.getLineDeliveryPeriod().getEndDateAsTimestamp()));
		line.setLineDeliveryDate("20210101");
		
		line = lines.get(1); // the second! the first has index 0!
		assertNull(line.getLineDeliveryPeriod());
		line.setLineDeliveryPeriod("20200415", "20200430");
		assertEquals("FR", line.getCountryOfOrigin());
		Properties attributes = line.getItemAttributes();
		assertEquals(1, attributes.size());
		assertEquals("3 meters", attributes.getProperty("Characteristic Description"));
		
//		lines.forEach(line -> {
//			assertNull(line.getStartDateAsTimestamp());
//		});
	}

}
