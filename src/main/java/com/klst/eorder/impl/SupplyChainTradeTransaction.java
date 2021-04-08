package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeLineItemType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeTransactionType;

public class SupplyChainTradeTransaction extends SupplyChainTradeTransactionType {

	static SupplyChainTradeTransaction create() {
		return new SupplyChainTradeTransaction(null); 
	}
	// copy factory
	static SupplyChainTradeTransaction create(SupplyChainTradeTransactionType object, CoreOrder order) {
		SupplyChainTradeTransaction res;
		if(object instanceof SupplyChainTradeTransactionType && object.getClass()!=SupplyChainTradeTransactionType.class) {
			// object is instance of a subclass of SupplyChainTradeTransactionType, but not SupplyChainTradeTransactionType itself
			res = (SupplyChainTradeTransaction)object;
		} else {
			res = new SupplyChainTradeTransaction(object); 
		}
		res.order = order;
		return res;
	}

	private CoreOrder order;

	// copy ctor
	private SupplyChainTradeTransaction(SupplyChainTradeTransactionType object) {
		super();
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	public List<OrderLine> getLines() {
		List<SupplyChainTradeLineItemType> lines = super.getIncludedSupplyChainTradeLineItem();
		List<OrderLine> resultLines = new ArrayList<OrderLine>(lines.size());
		lines.forEach(line -> {
			resultLines.add(SupplyChainTradeLineItem.create(line, this.order));
		});
		return resultLines;
	}
	
	public HeaderTradeAgreement createtHeaderTradeAgreement() {
		return HeaderTradeAgreement.create(getApplicableHeaderTradeAgreement());
	}

	public HeaderTradeDelivery createtHeaderTradeDelivery() {
		return HeaderTradeDelivery.create(getApplicableHeaderTradeDelivery());
	}
	
	public HeaderTradeSettlement createtHeaderTradeSettlement() {
		return HeaderTradeSettlement.create(getApplicableHeaderTradeSettlement());
	}
	
}
