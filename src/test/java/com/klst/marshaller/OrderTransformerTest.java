package com.klst.marshaller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;

import com.klst.eorder.impl.CrossIndustryOrder;

import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderTransformerTest {

	static private AbstactTransformer transformer;
	static private AbstactTransformer cioTransformer;

	private static final String TESTDIR = "src/test/resources/";
	private static final String UNCEFACT_XML = "ORDER-X_EX01_ORDER_FULL_DATA-BASIC.xml";

	private static final Logger LOG = Logger.getLogger(OrderTransformerTest.class.getName());

	@Before 
    public void setup() {
		transformer = CioTransformer.getInstance();
    	cioTransformer = CioTransformer.getInstance();
    }

    @Test
    public void singletonTest() {
    	assertNotNull(transformer);
    	assertNotNull(cioTransformer);
    	
    	assertEquals(transformer, cioTransformer);
   }
    
    File getXmlFile(String uri) {
		File file = new File(uri);
		assertTrue(file.canRead());
		return file;
    }
    
    @Test
    public void validateCioTest() {
		File file = getXmlFile(TESTDIR+UNCEFACT_XML);	
		Validator validator = null;
		try {
			validator = transformer.getSchemaValidator(); // throws SAXException
		} catch (SAXException ex) {
			fail("Validator SAXException="+ex.getMessage());
		}
		
		try {
			Source xmlFile = new StreamSource(file);
			validator.validate(xmlFile); // throws SAXException, Exception
		} catch (SAXException ex) {
			LOG.warning("Validator SAXException="+ex.getMessage());
			fail("Validator SAXException="+ex.getMessage());
		} catch (Exception ex) {
			LOG.warning("Validator SAXException="+ex.getMessage());
			fail("Validator Exception="+ex.getMessage());
		}
		
		LOG.info("file " +file+ " is valid.");
		try {
			InputStream is = new FileInputStream(file);
			SCRDMCCBDACIOMessageStructureType ms = transformer.toModel(is);
			CrossIndustryOrder order = new CrossIndustryOrder(ms);
			// TODO ...
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}

    }

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

}