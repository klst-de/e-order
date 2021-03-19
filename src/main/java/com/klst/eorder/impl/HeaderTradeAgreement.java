package com.klst.eorder.impl;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.ebXml.reflection.Mapper;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.PostalAddress;
import com.klst.eorder.api.BG4_Seller;
import com.klst.eorder.api.BG7_Buyer;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.HeaderTradeAgreementType;

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

	private static final String FIELD_applicableTradeDeliveryTerms = "applicableTradeDeliveryTerms";
	private static final String FIELD_issuerAssignedID = "issuerAssignedID";
	
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

	// BT-12 + 0..1 Contract reference
	public void setContractReference(String id) {
		Mapper.newFieldInstance(this, "contractReferencedDocument", id);
		Mapper.set(getContractReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	public String getContractReference() {
		if(getContractReferencedDocument()==null) return null;
		return getContractReferencedDocument().getIssuerAssignedID()==null ? null : getContractReferencedDocument().getIssuerAssignedID().getValue();
	}
	
	// BT-13 + 0..1 Purchase order reference
	public void setPurchaseOrderReference(String id) {
		Mapper.newFieldInstance(this, "buyerOrderReferencedDocument", id);
		Mapper.set(getBuyerOrderReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	public String getPurchaseOrderReference() {
		if(getBuyerOrderReferencedDocument()==null) return null;
		return getBuyerOrderReferencedDocument().getIssuerAssignedID()==null ? null : getBuyerOrderReferencedDocument().getIssuerAssignedID().getValue();
	}

	// BG-4 + 1..1 SELLER @see BG4_Seller
	@Override
	public void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId, String companyLegalForm) {
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
	public void setBuyer(String name, PostalAddress address, ContactInfo contact) {
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

	void setDeliveryType(String deliveryType) {
		Mapper.newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, deliveryType);
		Mapper.set(getApplicableTradeDeliveryTerms(), "deliveryTypeCode", deliveryType);
	}
	void setDeliveryFunctionCode(String functionCode) {
		Mapper.newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, functionCode);
		Mapper.set(getApplicableTradeDeliveryTerms(), "functionCode", functionCode);
	}

	String getDeliveryType() {
		if(getApplicableTradeDeliveryTerms()==null) return null;
		return getApplicableTradeDeliveryTerms().getDeliveryTypeCode()==null ? null : getApplicableTradeDeliveryTerms().getDeliveryTypeCode().getValue();
	}
	String getDeliveryFunctionCode() {
		if(getApplicableTradeDeliveryTerms()==null) return null;
		return getApplicableTradeDeliveryTerms().getFunctionCode()==null ? null : getApplicableTradeDeliveryTerms().getFunctionCode().getValue();
	}

	void setQuotationReference(String id) {
		Mapper.newFieldInstance(this, "quotationReferencedDocument", id);
		Mapper.set(getQuotationReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getQuotationReference() {
		if(getQuotationReferencedDocument()==null) return null;
		return getQuotationReferencedDocument().getIssuerAssignedID()==null ? null : getQuotationReferencedDocument().getIssuerAssignedID().getValue();
	}

	void setBlanketOrderReference(String id) {
		Mapper.newFieldInstance(this, "blanketOrderReferencedDocument", id);
		Mapper.set(getBlanketOrderReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getBlanketOrderReference() {
		if(getBlanketOrderReferencedDocument()==null) return null;
		return getBlanketOrderReferencedDocument().getIssuerAssignedID()==null ? null : getBlanketOrderReferencedDocument().getIssuerAssignedID().getValue();
	}

	void setPreviousOrderReference(String id) {
		Mapper.newFieldInstance(this, "previousOrderReferencedDocument", id);
		Mapper.set(getPreviousOrderReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getPreviousOrderReference() {
		if(getPreviousOrderReferencedDocument()==null) return null;
		return getPreviousOrderReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderReferencedDocument().getIssuerAssignedID().getValue();
	}

	// in Doc Zeile 571 : N (not in CIO/220)
	void setPreviousOrderChangeReference(String id) {
		Mapper.newFieldInstance(this, "previousOrderChangeReferencedDocument", id);
		Mapper.set(getPreviousOrderChangeReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getPreviousOrderChangeReference() {
		if(getPreviousOrderChangeReferencedDocument()==null) return null;
		return getPreviousOrderChangeReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderChangeReferencedDocument().getIssuerAssignedID().getValue();
	}
	
	// in Doc Zeile 576 : N (not in CIO/220)
	void setPreviousOrderResponseReference(String id) {
		Mapper.newFieldInstance(this, "previousOrderResponseReferencedDocument", id);
		Mapper.set(getPreviousOrderResponseReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getPreviousOrderResponseReference() {
		if(getPreviousOrderResponseReferencedDocument()==null) return null;
		return getPreviousOrderResponseReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderResponseReferencedDocument().getIssuerAssignedID().getValue();
	}

}
