package com.klst.eorder.openTrans;

import org.opentrans.xmlschema._2.PRODUCTID;

import com.klst.ebXml.reflection.CopyCtor;

public class Productid extends PRODUCTID {

	static Productid create() {
		return new Productid(null); 
	}
	// copy factory
	static Productid create(PRODUCTID object) {
		Productid res;
		if(object instanceof PRODUCTID && object.getClass()!=PRODUCTID.class) {
			// object is instance of a subclass of PRODUCTID, but not PRODUCTID itself
			res = (Productid)object;
		} else {
			res = new Productid(object); 
		}
		return res;
	}

	// copy ctor
	private Productid(PRODUCTID object) {
		super();
		if(object!=null) {
			CopyCtor.invokeCopy(this, object);
		}
	}
	
}
