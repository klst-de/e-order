package com.klst.eorder.impl;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.eorder.api.BG22_DocumentTotals;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeSettlementHeaderMonetarySummationType;

public class TradeSettlementHeaderMonetarySummation extends TradeSettlementHeaderMonetarySummationType implements BG22_DocumentTotals {

//	@Override  // implements Factory
//	public DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
//		return create(lineNet, taxExclusive, taxInclusive);
//	}
	static TradeSettlementHeaderMonetarySummation create(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		return new TradeSettlementHeaderMonetarySummation(lineNet, taxExclusive, taxInclusive);
	}

	static TradeSettlementHeaderMonetarySummation create() {
		return new TradeSettlementHeaderMonetarySummation(null); 
	}
	// copy factory
	static TradeSettlementHeaderMonetarySummation create(TradeSettlementHeaderMonetarySummationType object) {
		if(object instanceof TradeSettlementHeaderMonetarySummationType && object.getClass()!=TradeSettlementHeaderMonetarySummationType.class) {
			// object is instance of a subclass of TradeSettlementHeaderMonetarySummationType, but not TradeSettlementHeaderMonetarySummationType itself
			return (TradeSettlementHeaderMonetarySummation)object;
		} else {
			return new TradeSettlementHeaderMonetarySummation(object); 
		}
	}

	// copy ctor
	private TradeSettlementHeaderMonetarySummation(TradeSettlementHeaderMonetarySummationType object) {
		super();
		if(object!=null) {
			CopyCtor.invokeCopy(this, object);
		}
	}
	
	private TradeSettlementHeaderMonetarySummation(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		setLineNetTotal(lineNet);
	}
	
	void setLineNetTotal(IAmount amount) {
		if(amount!=null) super.setLineTotalAmount((Amount)amount);
	}
	void setTotalTaxExclusive(IAmount amount) {
		if(amount!=null) super.setTaxBasisTotalAmount((Amount)amount);
	}
	void setTotalTaxInclusive(IAmount amount) {
		if(amount!=null) super.setGrandTotalAmount((Amount)amount);
	}
	
	@Override
	public IAmount getLineNetTotal() {
		return super.getLineTotalAmount()==null ? null : Amount.create(getLineTotalAmount());
	}
	@Override
	public IAmount getTotalTaxExclusive() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IAmount getTotalTaxInclusive() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAllowancesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public IAmount getAllowancesTotal() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setChargesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public IAmount getChargesTotal() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IAmount getTaxTotal() {
		// TODO Auto-generated method stub
		return null;
	}

}
