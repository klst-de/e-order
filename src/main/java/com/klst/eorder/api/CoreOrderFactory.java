package com.klst.eorder.api;

import com.klst.untdid.codelist.DocumentNameCode;

/**
 * abstract-factory, aka Kit to create order objects
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface CoreOrderFactory {

	/**
	 * factory method to create an order object, uses PROCESS CONTROL (BG-2) params
	 * 
	 * @param CustomizationID aka profile Identifier String, f.i. PROFILE_EN_16931, BG-2.BT-24
	 * @param processType (optional) Textstring identifying the business process context, BG-2.BT-23
	 * @param Invoice type code - A code specifying the functional type of the Order, BT-3
	 * 
	 * @return order object
	 * 
	 * @see BG2_ProcessControl#getCustomization()
	 * @see BG2_ProcessControl#getProcessType()
	 */
	public CoreOrder createOrder(String profile, String processType, DocumentNameCode code);
	
	default CoreOrder createOrder(String profile, DocumentNameCode code) {
		return createOrder(profile, null, code);
	}

}
