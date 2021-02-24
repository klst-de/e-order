package com.klst.eorder.impl;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.api.BusinessParty;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.HeaderTradeAgreementType;

public class HeaderTradeAgreement extends HeaderTradeAgreementType {

	// factory
	static HeaderTradeAgreement create() {
		return new HeaderTradeAgreement(null); 
	}
	
	// copy factory
	static HeaderTradeAgreement create(HeaderTradeAgreementType object) {
		if(object instanceof HeaderTradeAgreementType && object.getClass()!=HeaderTradeAgreementType.class) {
			// object is instance of a subclass of HeaderTradeAgreementType, but not HeaderTradeAgreementType itself
			return (HeaderTradeAgreement)object;
		} else {
			return new HeaderTradeAgreement(object); 
		}
	}

	// copy ctor
	private HeaderTradeAgreement(HeaderTradeAgreementType object) {
		super();
		if(object!=null) {
			CopyCtor.invokeCopy(this, object);
		}
	}

	// BT-10 0..1 Buyer reference
	public void setBT10_BuyerReference(String reference) {
		if(reference==null) return;
		super.setBuyerReference(Text.create(reference));
	}
	public String getBuyerReferenceValue() {
		return super.getBuyerReference()==null ? null : getBuyerReference().getValue();	
	}

	public void setSeller(BusinessParty party) {
		if(party==null) return;
		super.setSellerTradeParty((TradeParty)party);
	}
	public BusinessParty getSeller() {
		return super.getSellerTradeParty()==null ? null : TradeParty.create(super.getSellerTradeParty());
	}
	
	public void setBuyer(BusinessParty party) {
		if(party==null) return;
		super.setBuyerTradeParty((TradeParty)party);
	}
	public BusinessParty getBuyer() {
		return super.getBuyerTradeParty()==null ? null : TradeParty.create(super.getBuyerTradeParty());
	}
	
}
