package com.klst.eorder.impl;

import java.lang.reflect.Field;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.ebXml.reflection.Mapper;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IContact;
import com.klst.edoc.api.PostalAddress;
import com.klst.eorder.api.BG4_Seller;
import com.klst.eorder.api.BG7_Buyer;

import un.unece.uncefact.data.standard.qualifieddatatype._103.DeliveryTermsFunctionCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.HeaderTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeDeliveryTermsType;

public class HeaderTradeAgreement extends HeaderTradeAgreementType implements BG4_Seller, BG7_Buyer {

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

	// BG-4 + 1..1 SELLER @see BG4_Seller
	@Override
	public void setSeller(String name, PostalAddress address, IContact contact, String companyId, String companyLegalForm) {
		BusinessParty party = TradeParty.create(name, null, address, contact);
		party.setCompanyId(companyId);
		party.setCompanyLegalForm(companyLegalForm);
		setSeller(party);
	}
	public void setSeller(BusinessParty party) {
		if(party==null) return;
		super.setSellerTradeParty((TradeParty)party);
	}
	public BusinessParty getSeller() {
		return super.getSellerTradeParty()==null ? null : TradeParty.create(super.getSellerTradeParty());
	}
	
	// BG-7 + 1..1 BUYER @see BG7_Buyer
	@Override
	public void setBuyer(String name, PostalAddress address, IContact contact) {
		BusinessParty party = TradeParty.create(name, null, address, contact);
		setBuyer(party);
	}
	@Override
	public void setBuyer(BusinessParty party) {
		if(party==null) return;
		super.setBuyerTradeParty((TradeParty)party);
	}
	@Override
	public BusinessParty getBuyer() {
		return super.getBuyerTradeParty()==null ? null : TradeParty.create(super.getBuyerTradeParty());
	}

	private static final String FIELD_applicableTradeDeliveryTerms = "applicableTradeDeliveryTerms";
	void setDeliveryType(String deliveryType) {
		Mapper.newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, deliveryType);
		Mapper.set(getApplicableTradeDeliveryTerms(), "deliveryTypeCode", deliveryType);
	}
	void setDeliveryFunctionCode(String functionCode) {
		Mapper.newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, functionCode);
		Mapper.set(getApplicableTradeDeliveryTerms(), "functionCode", functionCode);
	}

}
