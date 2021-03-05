package com.klst.eorder.impl;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.eorder.api.BG22_DocumentTotals;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeSettlementHeaderMonetarySummationType;

public class TradeSettlementHeaderMonetarySummation extends TradeSettlementHeaderMonetarySummationType implements BG22_DocumentTotals {

	@Override  // implements Factory
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		return create(lineNet, taxExclusive, taxInclusive);
	}
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
		setTotalTaxExclusive(taxExclusive);
		setTotalTaxInclusive(taxInclusive);
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
		return super.getTaxBasisTotalAmount()==null ? null : Amount.create(getTaxBasisTotalAmount());
	}
	@Override
	public IAmount getTotalTaxInclusive() {
		return super.getGrandTotalAmount()==null ? null : Amount.create(getGrandTotalAmount());
	}
	
	@Override
	public void setAllowancesTotal(IAmount amount) {
		if(amount!=null) super.setAllowanceTotalAmount((Amount)amount);
	}
	@Override
	public IAmount getAllowancesTotal() {
		return super.getAllowanceTotalAmount()==null ? null : Amount.create(getAllowanceTotalAmount());
	}
	
	@Override
	public void setChargesTotal(IAmount amount) {
		if(amount!=null) super.setChargeTotalAmount((Amount)amount);
	}
	@Override
	public IAmount getChargesTotal() {
		return super.getChargeTotalAmount()==null ? null : Amount.create(getChargeTotalAmount());
	}
	
	@Override
	public void setTaxTotal(IAmount amount) {
		if(amount!=null) super.setTaxTotalAmount((Amount)amount);
	}
	@Override
	public IAmount getTaxTotal() {
		return super.getTaxTotalAmount()==null ? null : Amount.create(getTaxTotalAmount());
	}

}
