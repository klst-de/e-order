package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.PaymentMeansEnum;

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

	// das erste element der Liste applicableTradeTax aus super, die anderen werden nicht genutzt
	TradeTax tradeTax = null;

	// copy ctor
	private HeaderTradeSettlement(HeaderTradeSettlementType object) {
		super();
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	// 789: BT-6 0..1 REQUESTED TAX CURRENCY IN INVOICE
	void setTaxCurrency(String isoCurrencyCode) {
		if(isoCurrencyCode==null) return;
		try {
			SCopyCtor.getInstance().set(this, "taxCurrencyCode", ISO3AlphaCurrencyCodeContentType.fromValue(isoCurrencyCode));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid currencyCode "+isoCurrencyCode);
		}
	}
	String getTaxCurrency() {
		return super.getTaxCurrencyCode()==null ? null : super.getTaxCurrencyCode().getValue().value();
	}
	
	// 790: BT-5 1..1 Invoice currency code
	void setDocumentCurrency(String isoCurrencyCode) {
		if(isoCurrencyCode==null) return;
		try {
			SCopyCtor.getInstance().set(this, "orderCurrencyCode", ISO3AlphaCurrencyCodeContentType.fromValue(isoCurrencyCode));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid currencyCode "+isoCurrencyCode);
		}
	}
	String getDocumentCurrency() {
		return super.getOrderCurrencyCode()==null ? null : super.getOrderCurrencyCode().getValue().value();
	}

	private void checkTradeTax() {
		if(tradeTax==null) {
			tradeTax = TradeTax.create(); 
		super.getApplicableTradeTax().add(tradeTax);
		}
	}
	
	private static final String FIELD_specifiedTradeSettlementPaymentMeans = "specifiedTradeSettlementPaymentMeans";
	// in super FIELD_specifiedTradeSettlementPaymentMeans:
//  protected PaymentMeansCodeType typeCode;
//  protected List<TextType> information;
	
	// 875:	BG-16.BT-81 0..1 Payment Means Code
	void setPaymentMeansEnum(PaymentMeansEnum code) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeSettlementPaymentMeans, code);
		SCopyCtor.getInstance().set(FIELD_specifiedTradeSettlementPaymentMeans, "typeCode", code.getValueAsString());
	}
	String getPaymentMeansCode() {
		if(getSpecifiedTradeSettlementPaymentMeans()==null) return null;
		return getSpecifiedTradeSettlementPaymentMeans().getTypeCode()==null ? null : getSpecifiedTradeSettlementPaymentMeans().getTypeCode().getValue();
	}
	PaymentMeansEnum getPaymentMeansEnum() {
		String value = getPaymentMeansCode();
		if(value==null) return null;
		try {
			int code = Integer.parseInt(value);
			return PaymentMeansEnum.valueOf(code);
		} catch (NumberFormatException e) {
			throw new RuntimeException("'"+value+"' is not valid Payment means code.");
		}
	}
	
	// 876:	BG-16.BT-82 0..1 Payment Means
	void setPaymentMeansText(String text) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeSettlementPaymentMeans, text);
		getSpecifiedTradeSettlementPaymentMeans().getInformation().add(Text.create(text));
	}
	private List<String> getPaymentMeans() {
		List<String> res = new ArrayList<String>();
		if(getSpecifiedTradeSettlementPaymentMeans()==null) return res;
		getSpecifiedTradeSettlementPaymentMeans().getInformation().forEach(text -> {
			res.add(Text.create(text).getValue());
		});
		return res;
	}
	String getPaymentMeansText() {
		List<String> res = getPaymentMeans();
		return res.isEmpty() ? null : res.get(0);
	}

	// 886: BT-8 0..1 Value added tax point date code
	void setTaxPointDateCode(String code) {
		checkTradeTax();
		tradeTax.setTaxPointDateCode(code);
	}
	String getTaxPointDateCode() {
		return tradeTax==null ? null : tradeTax.getTaxPointDateCode();
	}

	// 925: BT-20 0..1 PAYMENT TERMS
	void addPaymentTerm(String description) {
		SCopyCtor.getInstance().newFieldInstance(this, "specifiedTradePaymentTerms", description);
		getSpecifiedTradePaymentTerms().getDescription().add(Text.create(description));
	}
	void setPaymentTerms(List<String> paymentTerms) {
		paymentTerms.forEach(description -> {
			addPaymentTerm(description);
		});
	}
	List<String> getPaymentTerms() {
		List<String> res = new ArrayList<String>();
		if(getSpecifiedTradePaymentTerms()==null) return res;
		getSpecifiedTradePaymentTerms().getDescription().forEach(text -> {
			res.add(Text.create(text).getValue());
		});
		return res;
	}

	// 942: BT-19 0..1 Buyer accounting reference
	void setBuyerAccountingReference(Reference textReference) {
		SCopyCtor.getInstance().set(this, "receivableSpecifiedTradeAccountingAccount", textReference);
	}
	Reference getBuyerAccountingReference() {
		return getReceivableSpecifiedTradeAccountingAccount()==null ? null : new ID(getReceivableSpecifiedTradeAccountingAccount().getID());
	}

}
