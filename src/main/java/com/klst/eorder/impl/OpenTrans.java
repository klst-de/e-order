package com.klst.eorder.impl;

import org.opentrans.xmlschema._2.OPENTRANS;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.eorder.api.CoreOrder;

// Cannot cast com.klst.eorder.impl.OpenTrans to com.klst.eorder.api.CoreOrder
// implements CoreOrder fehlt --> die ganze implementierung
public class OpenTrans extends OPENTRANS {

	public OpenTrans(OPENTRANS doc) {
		super();
		if(doc!=null) {
			CopyCtor.invokeCopy(this, doc);
//			LOG.info("copy ctor:"+this); // toString liefert hier NPE wg.getLines
		}

	}
}
