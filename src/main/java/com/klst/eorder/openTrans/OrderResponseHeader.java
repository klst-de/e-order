package com.klst.eorder.openTrans;

import org.opentrans.xmlschema._2.ORDERRESPONSEHEADER;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.eorder.api.CoreOrder;

public class OrderResponseHeader extends ORDERRESPONSEHEADER {

	// copy factory
	static OrderResponseHeader create(ORDERRESPONSEHEADER object, CoreOrder order) {
		OrderResponseHeader res;
		if(object instanceof ORDERRESPONSEHEADER && object.getClass()!=ORDERRESPONSEHEADER.class) {
			// object is instance of a subclass of ORDERRESPONSEHEADER, but not ORDERRESPONSEHEADER itself
			res = (OrderResponseHeader)object;
		} else {
			res = new OrderResponseHeader(object); 
		}
		res.order = order;
		return res;
	}

	private CoreOrder order;

	// copy ctor
	private OrderResponseHeader(ORDERRESPONSEHEADER object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	public OrderResponseInfo createOrderResponseInfo() {
		return OrderResponseInfo.create(super.getORDERRESPONSEINFO());
	}

}
