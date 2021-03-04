package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.eorder.api.OrderLine;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeLineItemType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeTransactionType;

public class SupplyChainTradeTransaction extends SupplyChainTradeTransactionType {

	static SupplyChainTradeTransaction create() {
		return new SupplyChainTradeTransaction(null); 
	}
	// copy factory
	static SupplyChainTradeTransaction create(SupplyChainTradeTransactionType object) {
		if(object instanceof SupplyChainTradeTransactionType && object.getClass()!=SupplyChainTradeTransactionType.class) {
			// object is instance of a subclass of SupplyChainTradeTransactionType, but not SupplyChainTradeTransactionType itself
			return (SupplyChainTradeTransaction)object;
		} else {
			return new SupplyChainTradeTransaction(object); 
		}
	}

	// copy ctor
	private SupplyChainTradeTransaction(SupplyChainTradeTransactionType object) {
		super();
		if(object!=null) {
			CopyCtor.invokeCopy(this, object);
		}
	}

	public List<OrderLine> getLines() {
		List<SupplyChainTradeLineItemType> lines = super.getIncludedSupplyChainTradeLineItem();
		List<OrderLine> resultLines = new ArrayList<OrderLine>(lines.size());
		lines.forEach(line -> {
			resultLines.add(SupplyChainTradeLineItem.create(line));
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
