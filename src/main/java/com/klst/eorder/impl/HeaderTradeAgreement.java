package com.klst.eorder.impl;

import com.klst.ebXml.reflection.CopyCtor;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.HeaderTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradePartyType;

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

	public TradeParty getSeller() {
		TradePartyType sellerParty = super.getSellerTradeParty();
		return sellerParty==null ? null : TradeParty.create(sellerParty);
	}
}
