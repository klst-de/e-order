package com.klst.marshaller;

import java.io.InputStream;

import javax.inject.Named;
import javax.inject.Singleton;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;

@Named
@Singleton
public class CioTransformer extends AbstactTransformer {

	public static AbstactTransformer SINGLETON = new CioTransformer();

	public static AbstactTransformer getInstance() {
		return SINGLETON;
	}
	
//	private static final String CI_INVOICE_100 = "/cii/maindoc/CrossIndustryInvoice_100pD16B.xsd";
	// aus bin:
	private static final String CIO_XSD_1 = "/xsd/SCRDMCCBDACIOMessageStructure_1p0.xsd";
	// CONTENT_PATH aka package name
	private static final String CONTENT_PATH = "un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1"; 
			                                 //"un.unece.uncefact.data.standard.crossindustryinvoice._100";
	
	private CioTransformer() {
		super(CONTENT_PATH, SINGLETON);
	}
	
	@Override
	String getResource() {
		return CIO_XSD_1;
	}

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
