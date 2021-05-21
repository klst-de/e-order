package com.klst.eorder.api;

import com.klst.edoc.untdid.DocumentNameCode;

/**
 * abstract-factory, aka Kit to create order objects
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface CoreOrderFactory {

	/**
	 * factory method to create an order object, uses PROCESS CONTROL (BG-2) params
	 * 
	 * @param profile aka Customization Identifier, f.i. PROFILE_EN_16931, 7:BG-2.BT-24
	 * @param processType (optional) Textstring identifying the business process context, 5:BG-2.BT-23
	 * @param code - DocumentNameCode specifying the functional type of the Document, f.i. Order or OrderResponse, 11:BT-3
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
