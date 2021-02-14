package com.klst.marshaller;

import java.io.InputStream;

import javax.inject.Named;
import javax.inject.Singleton;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;

@Named
/* Notice 
 * that there are two @Singleton annotations, 
 * one in javax.inject and the other in the javax.ebj package. 
 * I'm referring to them by their fully-qualified names to avoid confusion.
 * @see https://stackoverflow.com/questions/26832051/singleton-vs-applicationscope
 * @see https://github.com/javax-inject/javax-inject
 */
@Singleton
public class CioTransformer extends AbstactTransformer {

	public static AbstactTransformer SINGLETON = new CioTransformer();

	public static AbstactTransformer getInstance() {
		return SINGLETON;
	}
	
	/* xsd file name in the output folder started with "/" == project_loc
	 * actually the cross industry order version is 103, not 100 as the corresponding CII!
	 * 
	 * SCRDM : Supply Chain REFERENCE DATA MODEL
	 * CCBDA : Core Component Business Document Assembly 
	 * CIO   : Cross Industry Order
	 */
	private static final String CIO_1_XSD = "/xsd/SCRDMCCBDACIOMessageStructure_1p0.xsd";
	// CONTENT_PATH aka package name
	private static final String CONTENT_PATH = "un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1"; 
	
	private CioTransformer() {
		super(CONTENT_PATH, SINGLETON);
	}
	
	@Override
	String getResource() {
		return CIO_1_XSD;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T toModel(InputStream xmlInputStream) {
		Object result = this.toModel(xmlInputStream, SCRDMCCBDACIOMessageStructureType.class);
		return (T) result;
	}

	@Override
	NamespacePrefixMapper getNamespacePrefixMapper() {
		return new CioNamespacePrefixMapper();
	}

}
