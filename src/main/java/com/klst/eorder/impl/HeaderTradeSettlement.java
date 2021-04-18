package com.klst.eorder.impl;

import com.klst.ebXml.reflection.Mapper;
import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.Reference;

import un.unece.uncefact.codelist.standard.iso.iso3alphacurrencycode._2012_08_31.ISO3AlphaCurrencyCodeContentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.HeaderTradeSettlementType;

public class HeaderTradeSettlement extends HeaderTradeSettlementType {

// use protected List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge ist implementiert in CrossIndustryOrder

	static HeaderTradeSettlement create() {
		return new HeaderTradeSettlement(null); 
	}
	// copy factory
	static HeaderTradeSettlement create(HeaderTradeSettlementType object) {
		if(object instanceof HeaderTradeSettlementType && object.getClass()!=HeaderTradeSettlementType.class) {
			// object is instance of a subclass of HeaderTradeSettlementType, but not HeaderTradeSettlementType itself
			return (HeaderTradeSettlement)object;
		} else {
			return new HeaderTradeSettlement(object); 
		}
	}

	// copy ctor
	private HeaderTradeSettlement(HeaderTradeSettlementType object) {
		super();
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	// BT-5 + 1..1 Invoice currency code
	public void setDocumentCurrency(String isoCurrencyCode) {
		if(isoCurrencyCode==null) return;
		try {
//			if(super.getOrderCurrencyCode()==null) {
//				setOrderCurrencyCode(new CurrencyCodeType());
//			}
//			getOrderCurrencyCode().setValue(ISO3AlphaCurrencyCodeContentType.fromValue(isoCurrencyCode));
			Mapper.set(this, "orderCurrencyCode", ISO3AlphaCurrencyCodeContentType.fromValue(isoCurrencyCode));
		} catch (IllegalArgumentException e) {
//			LOG.warning("Invalid currencyCode "+isoCurrencyCode);
			throw new IllegalArgumentException("Invalid currencyCode "+isoCurrencyCode);
		}
	}
	public String getDocumentCurrency() {
		// ISO3AlphaCurrencyCodeContentType
		return super.getOrderCurrencyCode()==null ? null : super.getOrderCurrencyCode().getValue().value();
	}

	// BT-19 + 0..1 Buyer accounting reference
	public void setBuyerAccountingReference(Reference textReference) {
//		if(textReference==null) return;
//		if(super.getReceivableSpecifiedTradeAccountingAccount()==null) {
//			setReceivableSpecifiedTradeAccountingAccount(new TradeAccountingAccountType());
//		}
//		getReceivableSpecifiedTradeAccountingAccount().setID((ID)textReference);
		Mapper.set(this, "receivableSpecifiedTradeAccountingAccount", textReference);
	}
	public Reference getBuyerAccountingReference() {
		return getReceivableSpecifiedTradeAccountingAccount()==null ? null : new ID(getReceivableSpecifiedTradeAccountingAccount().getID());
	}

}
