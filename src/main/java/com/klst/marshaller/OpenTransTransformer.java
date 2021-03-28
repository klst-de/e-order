package com.klst.marshaller;

import java.io.InputStream;

import javax.inject.Named;
import javax.inject.Singleton;

//import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

@Named
/* Notice 
 * that there are two @Singleton annotations, 
 * one in javax.inject and the other in the javax.ebj package. 
 * I'm referring to them by their fully-qualified names to avoid confusion.
 * @see https://stackoverflow.com/questions/26832051/singleton-vs-applicationscope
 * @see https://github.com/javax-inject/javax-inject
 */
@Singleton
public class OpenTransTransformer extends AbstactTransformer {

	public static AbstactTransformer SINGLETON = new OpenTransTransformer();

	public static AbstactTransformer getInstance() {
		return SINGLETON;
	}
	
	/* xsd file name in the output folder started with "/" == project_loc
	 * 
	 * OT   : OpenTrans
	 */
	private static final String OT_2_1_XSD = "/xsd/opentrans_2_1.xsd";
	// CONTENT_PATH aka package name
	private static final String CONTENT_PATH = "org.opentrans.xmlschema._2"; 
	// CONTENT_TYPE_NAME aka class name
	public static final String CONTENT_TYPE_NAME = "com.klst.eorder.openTrans.Order"; 
	
	private OpenTransTransformer() {
		super(CONTENT_PATH, SINGLETON);
	}
	
	@Override
	String getResource() {
		return OT_2_1_XSD;
	}

	Class<?> loadClass() {
		Class<?> type = null;
		try {
			// dynamisch die CIO  Klasse laden 
			type = Class.forName(CONTENT_PATH+".ORDER");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T toModel(InputStream xmlInputStream) {
		Class<?> type = loadClass();
		Object result = this.toModel(xmlInputStream, type);
		return (T) result;
	}

	@Override
	NamespacePrefixMapper getNamespacePrefixMapper() {
		return new CioNamespacePrefixMapper();
	}

}
