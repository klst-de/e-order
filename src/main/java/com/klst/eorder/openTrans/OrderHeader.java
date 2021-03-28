package com.klst.eorder.openTrans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.opentrans.xmlschema._2.AGREEMENT;
import org.opentrans.xmlschema._2.CATALOGREFERENCE;
import org.opentrans.xmlschema._2.ORDERHEADER;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.eorder.api.CoreOrder;

public class OrderHeader extends ORDERHEADER {

	static OrderHeader create() {
		return new OrderHeader(null); 
	}
	// copy factory
	static OrderHeader create(ORDERHEADER object, CoreOrder order) {
		OrderHeader res;
		if(object instanceof ORDERHEADER && object.getClass()!=ORDERHEADER.class) {
			// object is instance of a subclass of ORDERHEADER, but not ORDERHEADER itself
			res = (OrderHeader)object;
		} else {
			res = new OrderHeader(object); 
		}
		res.order = order;
		return res;
	}

	private CoreOrder order;

	// copy ctor
	private OrderHeader(ORDERHEADER object) {
		super();
		if(object!=null) {
			CopyCtor.invokeCopy(this, object);
		}
	}

//	public List<OrderLine> getLines() {
//		List<SupplyChainTradeLineItemType> lines = super.getIncludedSupplyChainTradeLineItem();
//		List<OrderLine> resultLines = new ArrayList<OrderLine>(lines.size());
//		lines.forEach(line -> {
//			resultLines.add(SupplyChainTradeLineItem.create(line, this.order));
//		});
//		return resultLines;
//	}
	
//	public ControlInfo createtControlInfo() {
//		super.getCONTROLINFO(); // hat nur 3 elemente
//	}
	
//	public SourcingInfo createSourcingInfo() {
//		return OrderInfo.create(super.getSOURCINGINFO());
////	SOURCINGINFO	hat 3 elemente:
////	    protected String quotationid;
////	    protected List<AGREEMENT> agreement;
////	    protected CATALOGREFERENCE catalogreference;
//	}
	
	public OrderInfo createOrderInfo() {
		return OrderInfo.create(super.getORDERINFO());
	}

//	public HeaderTradeDelivery createtHeaderTradeDelivery() {
//		return HeaderTradeDelivery.create(getApplicableHeaderTradeDelivery());
//	}
//	
//	public HeaderTradeSettlement createtHeaderTradeSettlement() {
//		return HeaderTradeSettlement.create(getApplicableHeaderTradeSettlement());
//	}
	
}
