package com.klst.eorder.openTrans;

import org.opentrans.xmlschema._2.ORDERRESPONSEHEADER;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.eorder.api.CoreOrder;

public class OrderResponseHeader extends ORDERRESPONSEHEADER {

	// copy factory
	static OrderResponseHeader create(ORDERRESPONSEHEADER object) {
		if(object instanceof ORDERRESPONSEHEADER && object.getClass()!=ORDERRESPONSEHEADER.class) {
			// object is instance of a subclass of ORDERRESPONSEHEADER, but not ORDERRESPONSEHEADER itself
			return (OrderResponseHeader)object;
		} else {
			return new OrderResponseHeader(object); 
		}
	}

	// copy ctor
	private OrderResponseHeader(ORDERRESPONSEHEADER object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	public OrderResponseInfo createOrderResponseInfo() {
		return OrderResponseInfo.create(super.getORDERRESPONSEINFO());
	}

}
