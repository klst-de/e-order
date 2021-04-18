package com.klst.marshaller;

import java.io.InputStream;

import javax.inject.Named;

import com.klst.eorder.api.AbstactTransformer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

@Named
/* Notice 
 * that there are two @Singleton annotations, 
 * one in javax.inject and the other in the javax.ebj package. 
 * I'm referring to them by their fully-qualified names to avoid confusion.
 * @see https://stackoverflow.com/questions/26832051/singleton-vs-applicationscope
 * @see https://github.com/javax-inject/javax-inject
 */
@javax.inject.Singleton
public class CioTransformer extends AbstactTransformer {

	public static AbstactTransformer SINGLETON = new CioTransformer();

	public static AbstactTransformer getInstance() {
		return SINGLETON;
	}
	
	/* xsd file name in the output folder started with "/" == project_loc
	 * actually the cross industry order version is 100.D20B, not 100.D16B as the corresponding CII!
	 * 
	 * SCRDM : Supply Chain REFERENCE DATA MODEL
	 * CCBDA : Core Component Business Document Assembly 
	 * CIO   : Cross Industry Order
	 */
	private static final String CIO_1_XSD = "/xsd/SCRDMCCBDACIOMessageStructure_100pD20B.xsd";
	// CONTENT_PATH aka package name
	private static final String CONTENT_PATH = "un.unece.uncefact.data.scrdmccbdaciomessagestructure._100"; 
	// CONTENT_SUPERTYPE_NAME aka class name
	private static final String CONTENT_SUPERTYPE_NAME = CONTENT_PATH+".SCRDMCCBDACIOMessageStructureType"; 
	// CONTENT_TYPE_NAME aka class name
	public static final String CONTENT_TYPE_NAME = "com.klst.eorder.impl.CrossIndustryOrder"; 
	
	private CioTransformer() {
		super(CONTENT_PATH, SINGLETON);
	}
	
	@Override
	protected String getResource() {
		return CIO_1_XSD;
	}

	@Override
	protected Class<?> loadClass() {
		Class<?> type = null;
		try {
			// dynamisch die CIO  Klasse laden 
			type = Class.forName(CONTENT_SUPERTYPE_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return type;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T unmashal(InputStream xmlInputStream) {
		Class<?> type = loadClass();
		Object result = this.unmarshal(xmlInputStream, type);
		return (T) result;
	}
	
	public NamespacePrefixMapper createNamespacePrefixMapper() {
		return CioNamespacePrefixMapper.getNamespacePrefixMapper();
	}

}
