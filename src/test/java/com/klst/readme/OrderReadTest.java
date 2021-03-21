package com.klst.readme;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
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
import com.klst.eorder.impl.Amount;
import com.klst.eorder.impl.CrossIndustryOrder;
import com.klst.marshaller.AbstactTransformer;
import com.klst.marshaller.CioTransformer;

import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;

public class OrderReadTest {

//	private static final Logger LOG = Logger.getLogger(OrderReadTest.class.getName());
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = OrderReadTest.class.getClassLoader().getResource("testLogging.properties");
		try {
	        File file = new File(url.toURI());
			logManager.readConfiguration(new FileInputStream(file));
		} catch (IOException | URISyntaxException e) {
			LOG = Logger.getLogger(OrderReadTest.class.getName());
			LOG.warning(e.getMessage());
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

	@Test
    public void cioEX01() {
		testFile = getTestFile(TESTDIR+"ORDER-X_EX01_ORDER_FULL_DATA-BASIC.xml");
		transformer = cioTransformer;
		// toModel:
		if(transformer.isValid(testFile)) {
			try {
				InputStream is = new FileInputStream(testFile);
				object = transformer.toModel(is);
				LOG.info(">>>>"+object);
			} catch (Exception ex) {
				ex.printStackTrace();
				LOG.severe(ex.getMessage());
			}
		}
		// ---- cii = CrossIndustryInvoice.create(invoice);
		CoreOrder cio = new CrossIndustryOrder((SCRDMCCBDACIOMessageStructureType)object);
		// TODO aus Object cio bzw TypeCode:220 
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
		// TODO ram:QuotationReferencedDocument  ram:ContractReferencedDocument  ram:BlanketOrderReferencedDocument

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
	}
	
}