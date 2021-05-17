package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.BG4_Seller;
import com.klst.eorder.api.BG7_Buyer;
import com.klst.eorder.api.SupportingDocument;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.HeaderTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.ReferencedDocumentType;

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
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	// 344: BT-10 0..1 Buyer reference
	public void setBT10_BuyerReference(String reference) {
		if(reference==null) return;
		super.setBuyerReference(Text.create(reference));
	}
	public String getBuyerReferenceValue() {
		return super.getBuyerReference()==null ? null : getBuyerReference().getValue();	
	}
	
	// 634: BT-11 0..1 procuring project
/*
               <ram:SpecifiedProcuringProject>
                    <ram:ID>PROJECT_ID</ram:ID>
                    <ram:Name>Project Reference</ram:Name>
               </ram:SpecifiedProcuringProject>
 */
	public void setProjectReference(String id, String name) {
		SCopyCtor.getInstance().newFieldInstance(this, "specifiedProcuringProject", id);
		SCopyCtor.getInstance().set(getSpecifiedProcuringProject(), "id", id);
		SCopyCtor.getInstance().set(getSpecifiedProcuringProject(), "name", name);
	}
	public Reference getProjectReference() {
		return getSpecifiedProcuringProject()==null ? null 
				: new ID( getSpecifiedProcuringProject().getName()==null ? "" : getSpecifiedProcuringProject().getName().getValue()
						, getSpecifiedProcuringProject().getID()==null ? null : getSpecifiedProcuringProject().getID().getValue()
						);
	}

	// 539: BT-12 0..1 Contract reference
	public void setContractReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "contractReferencedDocument", id);
		SCopyCtor.getInstance().set(getContractReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	public String getContractReference() {
		if(getContractReferencedDocument()==null) return null;
		return getContractReferencedDocument().getIssuerAssignedID()==null ? null : getContractReferencedDocument().getIssuerAssignedID().getValue();
	}
	
	// 524: BT-14 0..1 SALES ORDER REFERENCED DOCUMENT
	public void setOrderReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "sellerOrderReferencedDocument", id);
		SCopyCtor.getInstance().set(getSellerOrderReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	public String getOrderReference() {
		if(getSellerOrderReferencedDocument()==null) return null;
		return getSellerOrderReferencedDocument().getIssuerAssignedID()==null ? null : getBuyerOrderReferencedDocument().getIssuerAssignedID().getValue();
	}
	
	// 529: BT-13 0..1 Purchase order reference
	public void setPurchaseOrderReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "buyerOrderReferencedDocument", id);
		SCopyCtor.getInstance().set(getBuyerOrderReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	public String getPurchaseOrderReference() {
		if(getBuyerOrderReferencedDocument()==null) return null;
		return getBuyerOrderReferencedDocument().getIssuerAssignedID()==null ? null : getBuyerOrderReferencedDocument().getIssuerAssignedID().getValue();
	}
	
	
	// 561: BT-17 0..1 Tender or lot reference
/* Beispiel:
               <ram:AdditionalReferencedDocument>
                    <ram:IssuerAssignedID>TENDER_ID</ram:IssuerAssignedID>
                    <ram:TypeCode>50</ram:TypeCode>
               </ram:AdditionalReferencedDocument>
               
   ram:AdditionalReferencedDocument wird für BT-17 mit <ram:TypeCode>50 verwendet
                                     und für BG-24 mit <ram:TypeCode>916
   
   BR: ON profiles BASIC OR COMFORT, the Order MUST NOT HAVE more than 1 Tender or Lot Reference on Header Level
   
   nicht public, CrossIndustryOrder delegiert hierhin
 */
	void setTenderOrLotReference(String docRefId) {
		ReferencedDocument rd = ReferencedDocument.create(docRefId, DocumentNameCode.ValidatedPricedTender.getValueAsString()
				, (String)null);
		super.getAdditionalReferencedDocument().add(rd);
	}
	String getTenderOrLotReference() {
		List<ReferencedDocumentType> list = super.getAdditionalReferencedDocument();
		List<SupportingDocument> res = new ArrayList<SupportingDocument>(list.size());
		list.forEach(rd -> {
			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
			if(referencedDocument.isValidatedPricedTender()) res.add(referencedDocument);
		});
		return res.isEmpty() ? null : res.get(0).getDocumentReference().getName();
	}

	// 564: BT-18 0..1 (OBJECT IDENTIFIER FOR INVOICE)
	void setInvoicedObject(String name, String schemeID) {
		ReferencedDocument rd = ReferencedDocument.create(name, DocumentNameCode.InvoicingDataSheet.getValueAsString()
				, schemeID);
		super.getAdditionalReferencedDocument().add(rd);
	}
	public String getInvoicedObject() {
		List<ReferencedDocumentType> list = super.getAdditionalReferencedDocument();
		List<SupportingDocument> res = new ArrayList<SupportingDocument>(list.size());
		list.forEach(rd -> {
			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
			if(referencedDocument.isInvoicingDataSheet()) res.add(referencedDocument);
		});
		return res.isEmpty() ? null : res.get(0).getDocumentReference().getName();
	}
	public Identifier getInvoicedObjectIdentifier() {
		List<ReferencedDocumentType> list = super.getAdditionalReferencedDocument();
		List<SupportingDocument> res = new ArrayList<SupportingDocument>(list.size());
		list.forEach(rd -> {
			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
			if(referencedDocument.isInvoicingDataSheet()) res.add(referencedDocument);
		});
		return res.isEmpty() ? null : res.get(0).getDocumentReference();
	}

	// 345: BG-4 1..1 SELLER @see BG4_Seller
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
	
	// 390: BG-7 1..1 BUYER @see BG7_Buyer
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
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, deliveryType);
		SCopyCtor.getInstance().set(getApplicableTradeDeliveryTerms(), "deliveryTypeCode", deliveryType);
	}
	void setDeliveryFunctionCode(String functionCode) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, functionCode);
		SCopyCtor.getInstance().set(getApplicableTradeDeliveryTerms(), "functionCode", functionCode);
	}

	String getDeliveryType() {
		if(getApplicableTradeDeliveryTerms()==null) return null;
		return getApplicableTradeDeliveryTerms().getDeliveryTypeCode()==null ? null : getApplicableTradeDeliveryTerms().getDeliveryTypeCode().getValue();
	}
	String getDeliveryFunctionCode() {
		if(getApplicableTradeDeliveryTerms()==null) return null;
		return getApplicableTradeDeliveryTerms().getFunctionCode()==null ? null : getApplicableTradeDeliveryTerms().getFunctionCode().getValue();
	}

	// 534: 0..1 QUOTATION REFERENCE
	void setQuotationReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "quotationReferencedDocument", id);
		SCopyCtor.getInstance().set(getQuotationReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getQuotationReference() {
		if(getQuotationReferencedDocument()==null) return null;
		return getQuotationReferencedDocument().getIssuerAssignedID()==null ? null : getQuotationReferencedDocument().getIssuerAssignedID().getValue();
	}

	// 614: 0..1 BLANKET ORDER REFERENCE, not in CII
	void setBlanketOrderReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "blanketOrderReferencedDocument", id);
		SCopyCtor.getInstance().set(getBlanketOrderReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getBlanketOrderReference() {
		if(getBlanketOrderReferencedDocument()==null) return null;
		return getBlanketOrderReferencedDocument().getIssuerAssignedID()==null ? null : getBlanketOrderReferencedDocument().getIssuerAssignedID().getValue();
	}

	// 619: 0..1 PREVIOUS ORDER REFERENCE, not in CII
	void setPreviousOrderReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "previousOrderReferencedDocument", id);
		SCopyCtor.getInstance().set(getPreviousOrderReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getPreviousOrderReference() {
		if(getPreviousOrderReferencedDocument()==null) return null;
		return getPreviousOrderReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderReferencedDocument().getIssuerAssignedID().getValue();
	}

	// 624: 0..1 PREVIOUS ORDER CHANGE REFERENCED DOCUMENT, not in CIO/220
	void setPreviousOrderChangeReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "previousOrderChangeReferencedDocument", id);
		SCopyCtor.getInstance().set(getPreviousOrderChangeReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getPreviousOrderChangeReference() {
		if(getPreviousOrderChangeReferencedDocument()==null) return null;
		return getPreviousOrderChangeReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderChangeReferencedDocument().getIssuerAssignedID().getValue();
	}
	
	// 629: 0..1 PREVIOUS ORDER RESPONSE REFERENCED DOCUMENT, not in CIO/220
	void setPreviousOrderResponseReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, "previousOrderResponseReferencedDocument", id);
		SCopyCtor.getInstance().set(getPreviousOrderResponseReferencedDocument(), FIELD_issuerAssignedID, id);
	}
	String getPreviousOrderResponseReference() {
		if(getPreviousOrderResponseReferencedDocument()==null) return null;
		return getPreviousOrderResponseReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderResponseReferencedDocument().getIssuerAssignedID().getValue();
	}

	// BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS
	public void addSupportigDocument(SupportingDocument supportigDocument) {
		super.getAdditionalReferencedDocument().add((ReferencedDocument)supportigDocument);
	}
	public List<SupportingDocument> getAdditionalSupportingDocuments() {
		List<ReferencedDocumentType> list = super.getAdditionalReferencedDocument();
		List<SupportingDocument> res = new ArrayList<SupportingDocument>(list.size());
		list.forEach(rd -> {
			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
			if(referencedDocument.isRelatedDocument()) res.add(referencedDocument);
		});
		return res;
	}

}
