package com.klst.readme;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
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
		
		BusinessParty seller = cio.getSeller();
		BusinessParty buyer = cio.getBuyer();
		BusinessParty shipToParty = cio.getShipToParty();
		BusinessParty shipFromParty = cio.getShipFromParty();
		LOG.info("Seller:"+seller);
		LOG.info("Buyer:"+buyer);
	}
	
}
