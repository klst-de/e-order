package com.klst.test;

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

import com.klst.ebXml.reflection.SCopyCtor;
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

	private Object unmarshal(File testFile) {
		try {
			InputStream is = new FileInputStream(testFile);
			object = transformer.unmarshal(is);
			LOG.info("unmarshaled object:"+object);
			return object;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}
		return null;
	}

	private Object marshal(String xmlFn) {
		LOG.info("object.Class:"+object.getClass());
		
		byte[] xml = transformer.marshal(object);
		LOG.info(new String(xml));
		
		if(xmlFn!=null) { // write xml to file and unmarshal it back to object
			File testFile = CommonUtils.xmlToTempFile(xmlFn, xml);
			// unmarshal the result file toModel and perform assertions:
			if(transformer.isValid(testFile)) {
				return unmarshal(testFile);
			}
		}
		return null;
	}

	@Test
	public void xcreate() {
		Address adr;
		Address factory = Address.create();
		LOG.info("Address factory:"+factory);
		PostalAddress pa = factory.createAddress("CH", "8500", "Frauenfeld");
		pa.setAddressLine1("Plica AG");
		pa.setAddressLine2("Systeme für Elektrotechnik");
		adr = (Address)pa;
		adr.setStreet("Zürcherstrasse 350");
		
		transformer = otTransformer;
		object = adr;
		Object o = marshal("Address-TestResult");
		// o ist ADDRESS nach marshal to file und unmarshal zurück
		try {
			Class<?> type = Class.forName("com.klst.eorder.openTrans.Address");
//			LOG.info("Object (vor cast):"+o);
			Object oa = SCopyCtor.getInstance().castTo(type, o);
//			LOG.info("Object (nach cast):"+oa);
			Address a = (Address)oa;
			LOG.info("Address nach unmarshal:"+a);
			assertEquals("CH", a.getCountryCode());
			assertEquals("Zürcherstrasse 350", a.getStreet());
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}
		
	}

}
