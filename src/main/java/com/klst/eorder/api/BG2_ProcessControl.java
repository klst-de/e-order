package com.klst.eorder;


/**
 * BG-2 PROCESS CONTROL
 * <p>
 * A group of business terms providing information on the business process and rules applicable to the document.
 * <p>
 * Similar to EN16931 business group BG-2
 */

public interface BG2_ProcessControl {
	
	public static final String PROFILE_COMFORT   = "urn:order-x.eu:1p0:comfort";
	public static final String PROFILE_BASIC     = "urn:order-x.eu:1p0:basic";
	
	/**
	 * Business process type
	 * <p>
	 * Identifies the business process context in which the transaction appears, 
	 * to enable the Buyer to process the Purchase Order in an appropriate way.
	 * To be specified by the Buyer.
	 * 
	 * @return Textstring identifying the business process context
	 */
	public String getProcessType();
	
	/**
	 * Specification identifier aka PROFILE
	 * <p>
	 * An identification of the specification containing the total set of rules regarding semantic content,
	 * cardinalities and business rules to which the data contained in the instance document conforms.
	 * 
	 * @return PROFILE Identifier String, f.i. PROFILE_BASIC
	 * 
	 */
	public String getCustomization();

	default String getProfile() {
		return getCustomization();
	}

}
