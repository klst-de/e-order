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
public abstract class OpenTransTransformer extends AbstactTransformer {

	// ctor
	protected OpenTransTransformer(String contentPath, AbstactTransformer instance) {
		super(contentPath, instance);
	}
	
	/* xsd file name in the output folder started with "/" == project_loc
	 * 
	 * OT   : OpenTrans
	 */
	private static final String OT_2_1_XSD = "/xsd/opentrans_2_1.xsd";
	// CONTENT_PATH aka package name
	static final String CONTENT_PATH = "org.opentrans.xmlschema._2"; 
	
	@Override
	protected String getResource() {
		return OT_2_1_XSD;
	}
	
	abstract protected String getSupertypeName();

	@Override
	protected Class<?> loadClass() {
		Class<?> type = null;
		try {
			// dynamisch die OT Klasse laden 
			type = Class.forName(getSupertypeName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return type;
	}

//	public byte[] marshal(Object document) in super
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T unmarshal(InputStream xmlInputStream) {
		Class<?> type = loadClass();
		Object result = this.unmarshal(xmlInputStream, type);
		return (T) result;
	}

	public NamespacePrefixMapper createNamespacePrefixMapper() {
		return OpenTransNamespacePrefixMapper.getNamespacePrefixMapper();
	}

}
