package com.klst.eorder.openTrans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bmecat.bmecat._2005.DtCURRENCIES;
import org.opentrans.xmlschema._2.ORDERINFO;
import org.opentrans.xmlschema._2.PARTY;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.api.BG4_Seller;
import com.klst.eorder.api.BG7_Buyer;

public class OrderInfo extends ORDERINFO implements BG4_Seller, BG7_Buyer {

	// factory
	static OrderInfo create() {
		return new OrderInfo(null); 
	}
	
	// copy factory
	static OrderInfo create(ORDERINFO object) {
		if(object instanceof ORDERINFO && object.getClass()!=ORDERINFO.class) {
			// object is instance of a subclass of ORDERINFO, but not ORDERINFO itself
			return (OrderInfo)object;
		} else {
			return new OrderInfo(object); 
		}
	}

	private static final String FIELD_applicableTradeDeliveryTerms = "applicableTradeDeliveryTerms";
	private static final String FIELD_issuerAssignedID = "issuerAssignedID";
	
	// copy ctor
	private OrderInfo(ORDERINFO object) {
		super();
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	// Order number BT-1 Identifier (mandatory) - A unique identification of the Order.
	void setId(String id) {
		super.setORDERID(id);
	}
	String getId() {
		return super.getORDERID();
	}
	
	// Document issue date, BT-2 : <ORDER_DATE>2009-05-13T06:20:00+01:00</ORDER_DATE>
/* in doku dtDATETIME und nicht String:
dieser neue Datentyp löst die Datentypen dtDATETYPE, dtTIMETYPE und dtTIMEZONETYPE ab.
Beispiele:
2005-03-27T08:10:30+01:00 (entspricht: 27. März 2005, 08:10:30 Mitteleuropäische Zeit);
2005-03;
2005-03-27;
2005-03-27T08:10
 */
	void setIssueDate(Timestamp timestamp) {
		super.setORDERDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}
	Timestamp getIssueDateAsTimestamp() {
		return DateTimeFormats.dtDATETIMEToTs(super.getORDERDATE());
	}

	/* DELIVERYDATE
	 * 
	 * Zeitraum oder Zeitpunkt für das Lieferdatum (bzw. die Leistungserbringung). 
	 * Das Lieferdatum spezifiziert den Eingang der beauftragten Ware beim Einkäufer. 
	 * Wenn sich dieses Datum auf Positions-Ebene vom Datum auf Kopf-Ebene unterscheidet, 
	 * so gilt für diese Position das entsprechende Positions-Datum. 
	 * 
	 * Möchte man exakt einen Liefer-Zeitpunkt definieren, 
	 * zum Beispiel in der Wareneingangsbestätigung(RECEIPTACKNOWLEDGEMENT), 
	 * sollten beide Unterelemente, DELIVERY_DATE und DELIVERY_END_DATE, gleich befüllt werden.
	 */
	void setDeliveryPeriod(DeliveryDate period) {
		super.setDELIVERYDATE(period);
	}
	void setDeliveryDate(Timestamp timestamp) {
		setDeliveryPeriod(DeliveryDate.create(timestamp, timestamp));
	}
	Timestamp getDeliveryDateAsTimestamp() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return DateTimeFormats.dtDATETIMEToTs(getDELIVERYDATE().getDELIVERYSTARTDATE()); 
		}
		return null;
	}
	IPeriod getDeliveryPeriod() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return null;
		}
		// DELIVERYDATE ist Zeitraum
		return DeliveryDate.create(getDELIVERYDATE());
	}
	
	// BT-5 + 1..1 Invoice currency code
	void setDocumentCurrency(String isoCurrencyCode) {
		super.setCURRENCY(DtCURRENCIES.fromValue(isoCurrencyCode)); // enum DtCURRENCIES value
	}
	 String getDocumentCurrency() {
		 return getCURRENCY().value();
	 }

//	// BT-10 0..1 Buyer reference
//	public void setBT10_BuyerReference(String reference) {
//		if(reference==null) return;
//		super.setBuyerReference(Text.create(reference));
//	}
//	public String getBuyerReferenceValue() {
//		return super.getBuyerReference()==null ? null : getBuyerReference().getValue();	
//	}
//	
//	// BT-11 0..1 procuring project
///*
//               <ram:SpecifiedProcuringProject>
//                    <ram:ID>PROJECT_ID</ram:ID>
//                    <ram:Name>Project Reference</ram:Name>
//               </ram:SpecifiedProcuringProject>
// */
//	public void setProjectReference(String id, String name) {
//		Mapper.newFieldInstance(this, "specifiedProcuringProject", id);
//		Mapper.set(getSpecifiedProcuringProject(), "id", id);
//		Mapper.set(getSpecifiedProcuringProject(), "name", name);
//	}
//	public Reference getProjectReference() {
//		return getSpecifiedProcuringProject()==null ? null 
//				: new ID( getSpecifiedProcuringProject().getName()==null ? "" : getSpecifiedProcuringProject().getName().getValue()
//						, getSpecifiedProcuringProject().getID()==null ? null : getSpecifiedProcuringProject().getID().getValue()
//						);
//	}
//
//	// BT-12 + 0..1 Contract reference
//	public void setContractReference(String id) {
//		Mapper.newFieldInstance(this, "contractReferencedDocument", id);
//		Mapper.set(getContractReferencedDocument(), FIELD_issuerAssignedID, id);
//	}
//	public String getContractReference() {
//		if(getContractReferencedDocument()==null) return null;
//		return getContractReferencedDocument().getIssuerAssignedID()==null ? null : getContractReferencedDocument().getIssuerAssignedID().getValue();
//	}
//	
//	// BT-13 + 0..1 Purchase order reference
//	public void setPurchaseOrderReference(String id) {
//		Mapper.newFieldInstance(this, "buyerOrderReferencedDocument", id);
//		Mapper.set(getBuyerOrderReferencedDocument(), FIELD_issuerAssignedID, id);
//	}
//	public String getPurchaseOrderReference() {
//		if(getBuyerOrderReferencedDocument()==null) return null;
//		return getBuyerOrderReferencedDocument().getIssuerAssignedID()==null ? null : getBuyerOrderReferencedDocument().getIssuerAssignedID().getValue();
//	}
//	
//	
//	// BT-17 0..1 Tender or lot reference
///* Beispiel:
//               <ram:AdditionalReferencedDocument>
//                    <ram:IssuerAssignedID>TENDER_ID</ram:IssuerAssignedID>
//                    <ram:TypeCode>50</ram:TypeCode>
//               </ram:AdditionalReferencedDocument>
//               
//   ram:AdditionalReferencedDocument wird für BT-17 mit <ram:TypeCode>50 verwendet
//                                     und für BG-24 mit <ram:TypeCode>916
//   
//   BR: ON profiles BASIC OR COMFORT, the Order MUST NOT HAVE more than 1 Tender or Lot Reference on Header Level
//   
//   nicht public, CrossIndustryOrder delegiert hierhin
// */
//	void setTenderOrLotReference(String docRefId) {
//		ReferencedDocument rd = ReferencedDocument.create(docRefId, DocumentNameCode.ValidatedPricedTender.getValueAsString()
//				, (String)null);
//		super.getAdditionalReferencedDocument().add(rd);
//	}
//	String getTenderOrLotReference() {
//		List<ReferencedDocumentType> list = super.getAdditionalReferencedDocument();
//		List<SupportingDocument> res = new ArrayList<SupportingDocument>(list.size());
//		list.forEach(rd -> {
//			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
//			if(referencedDocument.isValidatedPricedTender()) res.add(referencedDocument);
//		});
//		return res.isEmpty() ? null : res.get(0).getDocumentReference().getName();
//	}
//
	// BG-4 + 1..1 SELLER @see BG4_Seller
	@Override
	public void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId, String companyLegalForm) {
//		BusinessParty party = TradeParty.create(name, null, address, contact);
//		party.setCompanyId(companyId);
//		party.setCompanyLegalForm(companyLegalForm);
//		setSeller(party);
	}
	@Override
	public void setSeller(BusinessParty party) {
		if(party==null) return;
//		super.setSellerTradeParty((TradeParty)party);
	}
	
	/**
	 * 
	 * @param partyrole, siehe doku: Zulässige Werte für das Element PARTY_ROLE

	 * @return
	 */
	private BusinessParty getParty(String partyrole) {
//		super.getPARTIES(); // required = true mit public List<PARTY> getPARTY() 
		// <PARTY_ROLE>buyer</PARTY_ROLE>
		// <PARTY_ROLE>seller</PARTY_ROLE> existiert nicht im Beispiel
		List<PARTY> bpList = super.getPARTIES().getPARTY();
		if(bpList.isEmpty()) return null;
		List<BusinessParty> resList = new ArrayList<BusinessParty>(bpList.size());
		// partyrole "buyer" or "seller"
		bpList.forEach(bp -> {
			bp.getPARTYROLE().forEach(role ->{
				if(partyrole.equals(role)) resList.add(Party.create(bp));
			});
		});
		return resList.isEmpty() ? null : resList.get(0);
	}
	@Override
	public BusinessParty getSeller() {
		return getParty("seller");
	}
	
	// BG-7 + 1..1 BUYER @see BG7_Buyer
	@Override
	public void setBuyer(String name, PostalAddress address, ContactInfo contact) {
//		BusinessParty party = TradeParty.create(name, null, address, contact);
//		setBuyer(party);
	}
	@Override
	public void setBuyer(BusinessParty party) {
//		if(party==null) return;
//		super.setBuyerTradeParty((TradeParty)party);
	}
	@Override
	public BusinessParty getBuyer() {
		return getParty("buyer");
	}

//	void setDeliveryType(String deliveryType) {
//		Mapper.newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, deliveryType);
//		Mapper.set(getApplicableTradeDeliveryTerms(), "deliveryTypeCode", deliveryType);
//	}
//	void setDeliveryFunctionCode(String functionCode) {
//		Mapper.newFieldInstance(this, FIELD_applicableTradeDeliveryTerms, functionCode);
//		Mapper.set(getApplicableTradeDeliveryTerms(), "functionCode", functionCode);
//	}
//
//	String getDeliveryType() {
//		if(getApplicableTradeDeliveryTerms()==null) return null;
//		return getApplicableTradeDeliveryTerms().getDeliveryTypeCode()==null ? null : getApplicableTradeDeliveryTerms().getDeliveryTypeCode().getValue();
//	}
//	String getDeliveryFunctionCode() {
//		if(getApplicableTradeDeliveryTerms()==null) return null;
//		return getApplicableTradeDeliveryTerms().getFunctionCode()==null ? null : getApplicableTradeDeliveryTerms().getFunctionCode().getValue();
//	}
//
//	void setQuotationReference(String id) {
//		Mapper.newFieldInstance(this, "quotationReferencedDocument", id);
//		Mapper.set(getQuotationReferencedDocument(), FIELD_issuerAssignedID, id);
//	}
//	String getQuotationReference() {
//		if(getQuotationReferencedDocument()==null) return null;
//		return getQuotationReferencedDocument().getIssuerAssignedID()==null ? null : getQuotationReferencedDocument().getIssuerAssignedID().getValue();
//	}
//
//	void setBlanketOrderReference(String id) {
//		Mapper.newFieldInstance(this, "blanketOrderReferencedDocument", id);
//		Mapper.set(getBlanketOrderReferencedDocument(), FIELD_issuerAssignedID, id);
//	}
//	String getBlanketOrderReference() {
//		if(getBlanketOrderReferencedDocument()==null) return null;
//		return getBlanketOrderReferencedDocument().getIssuerAssignedID()==null ? null : getBlanketOrderReferencedDocument().getIssuerAssignedID().getValue();
//	}
//
//	void setPreviousOrderReference(String id) {
//		Mapper.newFieldInstance(this, "previousOrderReferencedDocument", id);
//		Mapper.set(getPreviousOrderReferencedDocument(), FIELD_issuerAssignedID, id);
//	}
//	String getPreviousOrderReference() {
//		if(getPreviousOrderReferencedDocument()==null) return null;
//		return getPreviousOrderReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderReferencedDocument().getIssuerAssignedID().getValue();
//	}
//
//	// in Doc Zeile 571 : N (not in CIO/220)
//	void setPreviousOrderChangeReference(String id) {
//		Mapper.newFieldInstance(this, "previousOrderChangeReferencedDocument", id);
//		Mapper.set(getPreviousOrderChangeReferencedDocument(), FIELD_issuerAssignedID, id);
//	}
//	String getPreviousOrderChangeReference() {
//		if(getPreviousOrderChangeReferencedDocument()==null) return null;
//		return getPreviousOrderChangeReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderChangeReferencedDocument().getIssuerAssignedID().getValue();
//	}
//	
//	// in Doc Zeile 576 : N (not in CIO/220)
//	void setPreviousOrderResponseReference(String id) {
//		Mapper.newFieldInstance(this, "previousOrderResponseReferencedDocument", id);
//		Mapper.set(getPreviousOrderResponseReferencedDocument(), FIELD_issuerAssignedID, id);
//	}
//	String getPreviousOrderResponseReference() {
//		if(getPreviousOrderResponseReferencedDocument()==null) return null;
//		return getPreviousOrderResponseReferencedDocument().getIssuerAssignedID()==null ? null : getPreviousOrderResponseReferencedDocument().getIssuerAssignedID().getValue();
//	}
//
//	// BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS
//	public void addSupportigDocument(SupportingDocument supportigDocument) {
//		super.getAdditionalReferencedDocument().add((ReferencedDocument)supportigDocument);
//	}
//	public List<SupportingDocument> getAdditionalSupportingDocuments() {
//		List<ReferencedDocumentType> list = super.getAdditionalReferencedDocument();
//		List<SupportingDocument> res = new ArrayList<SupportingDocument>(list.size());
//		list.forEach(rd -> {
//			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
//			if(referencedDocument.isRelatedDocument()) res.add(referencedDocument);
//		});
//		return res;
//	}

}