package com.klst.marshaller;

import javax.inject.Named;

import com.klst.eorder.api.AbstactTransformer;

@Named
/* Notice 
 * that there are two @Singleton annotations, 
 * one in javax.inject and the other in the javax.ebj package. 
 * I'm referring to them by their fully-qualified names to avoid confusion.
 * @see https://stackoverflow.com/questions/26832051/singleton-vs-applicationscope
 * @see https://github.com/javax-inject/javax-inject
 */
@javax.inject.Singleton
public class OpenTransOrderResponseTransformer extends OpenTransTransformer {

	public static AbstactTransformer SINGLETON = new OpenTransOrderResponseTransformer();

	public static AbstactTransformer getInstance() {
		return SINGLETON;
	}
	
	// CONTENT_SUPERTYPE_NAME aka class name
	// CONTENT_TYPE_NAME aka class name, dh CONTENT_TYPE_NAME extends CONTENT_SUPERTYPE_NAME
	private static final String CONTENT_SUPERTYPE_NAME = CONTENT_PATH+".ORDERRESPONSE"; 
	public static final String CONTENT_TYPE_NAME = "com.klst.eorder.openTrans.OrderResponse"; 
	
	private OpenTransOrderResponseTransformer() {
		super(CONTENT_PATH, SINGLETON);
	}
	
	@Override
	protected String getSupertypeName() {
		return CONTENT_SUPERTYPE_NAME;
	}

}
