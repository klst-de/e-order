package com.klst.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.edoc.api.PostalAddress;
import com.klst.eorder.api.AbstactTransformer;
import com.klst.eorder.openTrans.Address;
import com.klst.marshaller.OpenTransAddressTransformer;

public class AddressTest {

	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = AddressTest.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(AddressTest.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(AddressTest.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(AddressTest.class.getName());		
	}
//	private static final Logger LOG = Logger.getLogger(AddressTest.class.getName());
	
	static private AbstactTransformer otTransformer;
	static private AbstactTransformer transformer;
	Object object;

    @BeforeClass
    public static void staticSetup() {
    	initLogger();
    }

	@Before 
    public void setup() {
	   	otTransformer = OpenTransAddressTransformer.getInstance();
	   	object = null;
    }

	private void marshal() {
		LOG.info("object.Class:"+object.getClass());
		
		byte[] xml = transformer.marshal(object);
		LOG.info(new String(xml));
		
//		File testFile = xmlToTempFile("Address-TestResult", xml);
//		CoreOrder cio = null;
//		// unmarshal the result file toModel and perform assertions:
//		if(transformer.isValid(testFile)) {
//			cio = unmarshal(testFile);
//			doAssert(cio);
//		}
	}

	@Test
	public void xcreate() {
		Address or;
		Address factory = Address.create();
		LOG.info("Address factory:"+factory);
		PostalAddress pa = factory.createAddress("CH", "8500", "Frauenfeld");
		or = (Address)pa;
		
		transformer = otTransformer;
		object = or;
		marshal();
	}

}
