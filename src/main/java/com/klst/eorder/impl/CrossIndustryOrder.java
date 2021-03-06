package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.MessageFunctionEnum;
import com.klst.edoc.untdid.PaymentMeansEnum;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG22_DocumentTotals;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;

import un.unece.uncefact.data.scrdmccbdaciomessagestructure._100.SCRDMCCBDACIOMessageStructureType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.DocumentCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.DocumentContextParameterType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.ExchangedDocumentContextType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.ExchangedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradeAllowanceChargeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.DateTimeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.IndicatorType;

public class CrossIndustryOrder extends SCRDMCCBDACIOMessageStructureType implements CoreOrder {

	// factory
	public static CoreOrder getFactory() {
		return new CrossIndustryOrder(null);
	}

	private static final Logger LOG = Logger.getLogger(CrossIndustryOrder.class.getName());
	
	SupplyChainTradeTransaction supplyChainTradeTransaction; 
	HeaderTradeAgreement applicableHeaderTradeAgreement;
	HeaderTradeDelivery applicableHeaderTradeDelivery; // <ram:ApplicableHeaderTradeDelivery>
	HeaderTradeSettlement applicableHeaderTradeSettlement; // <ram:ApplicableHeaderTradeSettlement>
	
	public CrossIndustryOrder(SCRDMCCBDACIOMessageStructureType doc) {
		SCopyCtor.getInstance().invokeCopy(this, doc);

		supplyChainTradeTransaction = SupplyChainTradeTransaction.create(super.getSupplyChainTradeTransaction(), this);
		applicableHeaderTradeAgreement = supplyChainTradeTransaction.createtHeaderTradeAgreement();
		applicableHeaderTradeDelivery = supplyChainTradeTransaction.createtHeaderTradeDelivery();
		applicableHeaderTradeSettlement = supplyChainTradeTransaction.createtHeaderTradeSettlement();
		if(super.getSupplyChainTradeTransaction()==null) {
			super.setSupplyChainTradeTransaction(supplyChainTradeTransaction);
			supplyChainTradeTransaction.setApplicableHeaderTradeAgreement(applicableHeaderTradeAgreement);
			supplyChainTradeTransaction.setApplicableHeaderTradeDelivery(applicableHeaderTradeDelivery);
			supplyChainTradeTransaction.setApplicableHeaderTradeSettlement(applicableHeaderTradeSettlement);
		}
		
	}

	/**
	 * factory method to create an order object, uses PROCESS CONTROL (BG-2) params
	 * 
	 * @param CustomizationID aka profile Identifier String, f.i. PROFILE_EN_16931, BG-2.BT-24
	 * @param processType (optional) Textstring identifying the business process context, BG-2.BT-23
	 * @param Invoice type code - A code specifying the functional type of the Invoice, BT-3
	 * 
	 * @return order object
	 * 
	 * @see BG2_ProcessControl#getCustomization()
	 * @see BG2_ProcessControl#getProcessType()
	 */
	@Override
	public CoreOrder createOrder(String profile, String processType, DocumentNameCode code) {
		return create(profile, processType, code);
	}
	public static CrossIndustryOrder create(String profile, String processType, DocumentNameCode code) {
		return new CrossIndustryOrder(profile, processType, code);
	}

	private CrossIndustryOrder(String customization, String processType, DocumentNameCode documentNameCode) {
		this(null);
//		setProcessControl(customization, processType);
		super.setExchangedDocumentContext(new ExchangedDocumentContextType());
		DocumentContextParameterType guidelineSpecifiedDocumentContextParameter = new DocumentContextParameterType();
		guidelineSpecifiedDocumentContextParameter.setID(new ID(customization));
		super.getExchangedDocumentContext().setGuidelineSpecifiedDocumentContextParameter(guidelineSpecifiedDocumentContextParameter);
		if(processType!=null) { // optional
			DocumentContextParameterType businessProcessSpecifiedDocumentContextParameter = new DocumentContextParameterType();
			businessProcessSpecifiedDocumentContextParameter.setID(new ID(processType));
			super.getExchangedDocumentContext().setBusinessProcessSpecifiedDocumentContextParameter(businessProcessSpecifiedDocumentContextParameter);
		}
		
		super.setExchangedDocument(new ExchangedDocumentType());
		DocumentCodeType documentCode = new DocumentCodeType();
		documentCode.setValue(documentNameCode.getValueAsString());
		super.getExchangedDocument().setTypeCode(documentCode);
	}

/*
	<rsm:ExchangedDocumentContext>
		<ram:TestIndicator>
			<udt:Indicator>false</udt:Indicator>
		</ram:TestIndicator>
		<ram:BusinessProcessSpecifiedDocumentContextParameter>
			<ram:ID>A1</ram:ID>
		</ram:BusinessProcessSpecifiedDocumentContextParameter>
		<ram:GuidelineSpecifiedDocumentContextParameter>
			<ram:ID>urn:order-x.eu:1p0:basic</ram:ID>
		</ram:GuidelineSpecifiedDocumentContextParameter>
	</rsm:ExchangedDocumentContext>
 */
	@Override
	public void setTestIndicator(boolean indicator) {
		SCopyCtor.getInstance().set(getExchangedDocumentContext(), "testIndicator", indicator);
	}
	@Override
	public boolean isTest() {
		IndicatorType indicator = super.getExchangedDocumentContext().getTestIndicator();
		return indicator!=null && indicator.isIndicator().equals(TEST);
	}

	@Override
	public String getProcessType() {
		DocumentContextParameterType documentContextParameter = super.getExchangedDocumentContext().getBusinessProcessSpecifiedDocumentContextParameter();
		return documentContextParameter==null ? null : new ID(documentContextParameter.getID()).getContent();
	}

	@Override
	public String getCustomization() { // aka Profile
		DocumentContextParameterType documentContextParameterType = 
				super.getExchangedDocumentContext().getGuidelineSpecifiedDocumentContextParameter();
		return documentContextParameterType==null ? null : new ID(documentContextParameterType.getID()).getContent();
	}

/*
	<rsm:ExchangedDocument>
		<ram:ID>PO123456789</ram:ID>
		<ram:Name>Doc Name</ram:Name>      <!-- wird in einvoice nicht verwendet -->
		<ram:TypeCode>220</ram:TypeCode>
		<ram:IssueDateTime>
			<udt:DateTimeString format="102">20200331</udt:DateTimeString>
		</ram:IssueDateTime>
		<ram:CopyIndicator>
			<udt:Indicator>false</udt:Indicator>
		</ram:CopyIndicator>
		<ram:PurposeCode>9</ram:PurposeCode>
		<ram:RequestedResponseTypeCode>AC</ram:RequestedResponseTypeCode>
		<ram:IncludedNote>
			<ram:Content>Content of Note</ram:Content>
			<ram:SubjectCode>AAI</ram:SubjectCode>
		</ram:IncludedNote>
	</rsm:ExchangedDocument>
 */
	// 9: Invoice number BT-1 Identifier (mandatory) - A unique identification of the Invoice.
	@Override
	public void setId(String id) {
		super.getExchangedDocument().setID(new ID(id)); // No identification scheme is to be used.
	}
	@Override
	public String getId() {
		return super.getExchangedDocument().getID().getValue();
	}

	// 10: ExchangedDocument.Name wird in einvoice nicht verwendet
	public String getName() {
		return super.getExchangedDocument().getName().getValue();
	}

	// 11: BT-3 The Document TypeCode
//	@Override // für toString
	private String getTypeCode() {
		return super.getExchangedDocument().getTypeCode().getValue();
	}
	@Override
	public DocumentNameCode getDocumentCode() {
		return DocumentNameCode.valueOf(super.getExchangedDocument().getTypeCode());
	}

	/* 14: Document issue date, BT-2  Date (mandatory) 
	 * Das Datum, an dem der Beleg ausgestellt wurde.
	 */
	@Override
	public void setIssueDate(Timestamp ts) {
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		super.getExchangedDocument().setIssueDateTime(dateTime);
	}
	@Override
	public Timestamp getIssueDateAsTimestamp() {
		return DateTimeFormatStrings.getDateAsTimestamp(super.getExchangedDocument().getIssueDateTime());
	}
	
	// 16: ram:CopyIndicator 
	@Override
	public void setCopyIndicator(boolean indicator) {
		SCopyCtor.getInstance().set(super.getExchangedDocument(), "copyIndicator", indicator);
	}
	@Override
	public boolean isCopy() {
		IndicatorType indicator = super.getExchangedDocument().getCopyIndicator();
		return indicator!=null && indicator.isIndicator().equals(COPY);
	}
	
	/**
	 * 18: 0..n LanguageID	
	 * A unique identifier for a language used in this exchanged document.	
	 * To be chosen from the entries in UNTDID 3453 / ISO 639-1: de, en, es, ...
	 */
	@Override
	public void addLanguage(String id) {
		super.getExchangedDocument().getLanguageID().add(new ID(id));
	}
	@Override
	public List<String> getLanguage() {
		List<String> res = new ArrayList<String>();
		if(super.getExchangedDocument().getLanguageID()==null) return res;
		getExchangedDocument().getLanguageID().forEach(id -> {
			res.add(new ID(id).getContent());
		});
		return res;
	}

	// 19: Purpose Code
	@Override
	public void setPurpose(MessageFunctionEnum code) {
		SCopyCtor.getInstance().set(getExchangedDocument(), "purposeCode", code.getValueAsString());
	}
	private String getPurpose() {
		if(super.getExchangedDocument().getPurposeCode()==null) return null;
		return getExchangedDocument().getPurposeCode().getValue();
	}
	@Override
	public MessageFunctionEnum getPurposeCode() {
		String value = getPurpose();
		if(value==null) return null;
		try {
			int code = Integer.parseInt(value);
			return MessageFunctionEnum.valueOf(code);
		} catch (NumberFormatException e) {
			throw new RuntimeException("'"+value+"' is not valid Message Function Code.");
		}
	}

	// 20: Requested Response Code
	@Override
	public void setRequestedResponse(String code) {
		SCopyCtor.getInstance().set(getExchangedDocument(), "requestedResponseTypeCode", code);
	}
	@Override
	public String getRequestedResponse() {
		if(super.getExchangedDocument().getRequestedResponseTypeCode()==null) return null;
		return getExchangedDocument().getRequestedResponseTypeCode().getValue();
	}

	// 21: BG-1
	@Override // factory
	public OrderNote createNote(String subjectCode, String content) {
		return Note.create(subjectCode, content);
	}
	@Override // setter
	public void addNote(OrderNote note) {
		super.getExchangedDocument().getIncludedNote().add((Note)note);
	}
	@Override // getter
	public List<OrderNote> getOrderNotes() {
		return Note.getNotes(super.getExchangedDocument().getIncludedNote());
	}	

	//------------------------------------

	// 344: BT-10 0..1 Buyer reference
	@Override
	public void setBuyerReference(String reference) {
		applicableHeaderTradeAgreement.setBT10_BuyerReference(reference);
	}
	@Override
	public String getBuyerReferenceValue() {
		return applicableHeaderTradeAgreement.getBuyerReferenceValue();
	}
	
	// 345: BG-4 1..1 SELLER @see BG4_Seller
	@Override
	public void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId, String companyLegalForm) {
		BusinessParty party = createParty(name, address, contact);
		party.setCompanyId(companyId);
		party.setCompanyLegalForm(companyLegalForm);
		setSeller(party);		
	}
	@Override
	public void setSeller(BusinessParty party) {
		applicableHeaderTradeAgreement.setSeller(party);
	}
	@Override
	public BusinessParty getSeller() {
		return applicableHeaderTradeAgreement.getSeller();
	}

	// 390: BG-7 1..1 BUYER @see BG7_Buyer
	@Override
	public void setBuyer(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = createParty(name, address, contact); // BT-44, BG-8, BG-9
		setBuyer(party);
	}
	@Override
	public void setBuyer(BusinessParty party) {
		applicableHeaderTradeAgreement.setBuyer(party);
	}
	@Override
	public BusinessParty getBuyer() {
		return applicableHeaderTradeAgreement.getBuyer();
	}

	// 524: BT-14 0..1 SALES ORDER REFERENCED DOCUMENT
	@Override
	public void setOrderReference(String docRefId, Timestamp timestamp) {
		applicableHeaderTradeAgreement.setOrderReference(docRefId, timestamp);	
	}
	@Override
	public String getOrderReference() {
		return applicableHeaderTradeAgreement.getOrderReference();	
	}
	@Override
	public Timestamp getOrderDate() {
		return applicableHeaderTradeAgreement.getOrderDate();	
	}
	
	// 529: BT-13 0..1 Purchase order reference
	@Override
	public void setPurchaseOrderReference(String docRefId, Timestamp timestamp) {
		applicableHeaderTradeAgreement.setPurchaseOrderReference(docRefId, timestamp);	
	}
	@Override
	public String getPurchaseOrderReference() {
		return applicableHeaderTradeAgreement.getPurchaseOrderReference();	
	}
	@Override
	public Timestamp getPurchaseOrderDate() {
		return applicableHeaderTradeAgreement.getPurchaseOrderDate();	
	}

	// 534: 0..1 QUOTATION REFERENCE, not in CII
	@Override
	public void setQuotationReference(String id, Timestamp timestamp) {
		applicableHeaderTradeAgreement.setQuotationReference(id, timestamp);
	}
	@Override
	public String getQuotationReference() {
		return applicableHeaderTradeAgreement.getQuotationReference();
	}
	@Override
	public Timestamp getQuotationDate() {
		return applicableHeaderTradeAgreement.getQuotationDate();	
	}

	// 539: BT-12 0..1 Contract reference
	@Override
	public void setContractReference(String id, Timestamp timestamp) {
		applicableHeaderTradeAgreement.setContractReference(id, timestamp);	
	}
	@Override
	public String getContractReference() {
		return applicableHeaderTradeAgreement.getContractReference();	
	}
	@Override
	public Timestamp getContractDate() {
		return applicableHeaderTradeAgreement.getContractDate();	
	}

	// 544: 0..1 REQUISITION REFERENCE, not in CII
	@Override
	public void setRequisitionReference(String id, Timestamp timestamp) {
		applicableHeaderTradeAgreement.setRequisitionReference(id, timestamp);
	}
	@Override
	public String getRequisitionReference() {
		return applicableHeaderTradeAgreement.getRequisitionReference();
	}
	@Override
	public Timestamp getRequisitionDate() {
		return applicableHeaderTradeAgreement.getRequisitionDate();	
	}

	// 549: BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description, Timestamp ts, String uri) {
		ReferencedDocument rd = ReferencedDocument.create(docRefId, lineId, description);
		rd.setExternalDocumentLocation(uri);
		return rd;
	}
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description, Timestamp ts
			, byte[] content, String mimeCode, String filename) {
		ReferencedDocument rd = ReferencedDocument.create(docRefId, lineId, description);
		rd.setAttachedDocument(content, mimeCode, filename);
		return rd;
	}
	public void addSupportigDocument(SupportingDocument supportigDocument) {
		applicableHeaderTradeAgreement.addSupportigDocument(supportigDocument);
	}
	public List<SupportingDocument> getAdditionalSupportingDocuments() {
		return applicableHeaderTradeAgreement.getAdditionalSupportingDocuments();
	}

	// 561: BT-17 0..1 Tender or lot reference
	@Override
	public void setTenderOrLotReference(String docRefId) {
		applicableHeaderTradeAgreement.setTenderOrLotReference(docRefId);	
	}
	@Override
	public String getTenderOrLotReference() {
		return applicableHeaderTradeAgreement.getTenderOrLotReference();	
	}

	// 564: BT-18 0..1 (OBJECT IDENTIFIER FOR INVOICE)
	@Override
	public void setInvoicedObject(String name, String schemeID) {
		applicableHeaderTradeAgreement.setInvoicedObject(name, schemeID);	
	}
	@Override
	public String getInvoicedObject() {
		return applicableHeaderTradeAgreement.getInvoicedObject();	
	}
	@Override
	public Identifier getInvoicedObjectIdentifier() {
		return applicableHeaderTradeAgreement.getInvoicedObjectIdentifier();	
	}
	
	// 614: 0..1 BLANKET ORDER REFERENCE, not in CII
	@Override
	public void setBlanketOrderReference(String id) {
		applicableHeaderTradeAgreement.setBlanketOrderReference(id);
	}
	@Override
	public String getBlanketOrderReference() {
		return applicableHeaderTradeAgreement.getBlanketOrderReference();
	}

	// 619: 0..1 PREVIOUS ORDER REFERENCE, not in CII
	@Override
	public void setPreviousOrderReference(String id) {
		applicableHeaderTradeAgreement.setPreviousOrderReference(id);
	}

	@Override
	public String getPreviousOrderReference() {
		return applicableHeaderTradeAgreement.getPreviousOrderReference();
	}

	// 624: 0..1 PREVIOUS ORDER CHANGE REFERENCED DOCUMENT, not in CII
	@Override
	public void setPreviousOrderChangeReference(String id) {
		if(getDocumentCode()==DocumentNameCode.Order) {
			LOG.warning("An Order (Document Type Code BT-3 = 220) MUST NOT contain a Previous Order Change Referenced Document");
		} else {
			applicableHeaderTradeAgreement.setPreviousOrderChangeReference(id);
		}
	}
	@Override
	public String getPreviousOrderChangeReference() {
		return applicableHeaderTradeAgreement.getPreviousOrderChangeReference();
	}

	// 629: 0..1 PREVIOUS ORDER RESPONSE REFERENCED DOCUMENT, not in CII
	@Override
	public void setPreviousOrderResponseReference(String id) {
		if(getDocumentCode()==DocumentNameCode.Order) {
			LOG.warning("An Order (Document Type Code BT-3 = 220) MUST NOT contain a Previous Order Response Referenced Document");
		} else {
			applicableHeaderTradeAgreement.setPreviousOrderResponseReference(id);
		}
	}
	@Override
	public String getPreviousOrderResponseReference() {
		return applicableHeaderTradeAgreement.getPreviousOrderResponseReference();
	}
	
	// 634: BT-11 0..1 procuring project
	@Override
	public void setProjectReference(String id, String name) {
		applicableHeaderTradeAgreement.setProjectReference(id, name);
	}
	@Override
	public Reference getProjectReference() {
		return applicableHeaderTradeAgreement.getProjectReference();
	}
	
	@Override
	public void setDeliveryTerms(String deliveryType, String functionCode) {
		applicableHeaderTradeAgreement.setDeliveryType(deliveryType);
		applicableHeaderTradeAgreement.setDeliveryFunctionCode(functionCode);
	}
	@Override
	public String getDeliveryType() {
		return applicableHeaderTradeAgreement.getDeliveryType();
	}
	@Override
	public String getDeliveryFunctionCode() {
		return applicableHeaderTradeAgreement.getDeliveryFunctionCode();
	}

	// 643: ShipToParty @see ShipTo
	@Override
	public void setShipTo(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = createParty(name, address, contact);
		setShipTo(party);
	}
	@Override
	public void setShipTo(BusinessParty party) {
		applicableHeaderTradeDelivery.setShipTo(party);
	}
	@Override
	public BusinessParty getShipTo() {
		return applicableHeaderTradeDelivery.getShipTo();
	}

	// 725: ShipFromParty @see ShipFrom
	@Override
	public void setShipFrom(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = createParty(name, address, contact);
		setShipFrom(party);
	}
	@Override
	public void setShipFrom(BusinessParty party) {
		applicableHeaderTradeDelivery.setShipFrom(party);
	}
	@Override
	public BusinessParty getShipFrom() {
		return applicableHeaderTradeDelivery.getShipFrom();
	}

	// 766: REQUESTED DELIVERY DATE or PERIOD
	// 767: Requested Delivery Date
	@Override
	public void setDeliveryDate(Timestamp ts) {
		applicableHeaderTradeDelivery.setDeliveryDate(ts);
	}
	@Override
	public Timestamp getDeliveryDateAsTimestamp() {
		return applicableHeaderTradeDelivery.getDeliveryDateAsTimestamp();
	}

	// 770: Requested Delivery Period
	@Override
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return Period.create(start, end);
	}
	@Override
	public void setDeliveryPeriod(IPeriod period) {
		applicableHeaderTradeDelivery.setDeliveryPeriod(period);
	}
	@Override
	public IPeriod getDeliveryPeriod() {
		return applicableHeaderTradeDelivery.getDeliveryPeriod();
	}

	// 777: Requested Pick up Date or Period
	// 778: Requested Pick up Date
	@Override
	public void setPickupDate(Timestamp ts) {
		applicableHeaderTradeDelivery.setPickupDate(ts);
	}
	@Override
	public Timestamp getPickupDateAsTimestamp() {
		return applicableHeaderTradeDelivery.getPickupDateAsTimestamp();
	}

	// 781: Requested Pick up Period
	@Override
	public void setPickupPeriod(IPeriod period) {
		applicableHeaderTradeDelivery.setPickupPeriod(period);
	}
	@Override
	public IPeriod getPickupPeriod() {
		return applicableHeaderTradeDelivery.getPickupPeriod();
	}
	
	// 789: BT-6 0..1 REQUESTED TAX CURRENCY IN INVOICE
	@Override
	public void setTaxCurrency(String isoCurrencyCode) {
		applicableHeaderTradeSettlement.setTaxCurrency(isoCurrencyCode);
	}
	@Override
	public String getTaxCurrency() {
		return applicableHeaderTradeSettlement.getTaxCurrency();
	}

	// 790: BT-5 1..1 Invoice currency code
	@Override
	public void setDocumentCurrency(String isoCurrencyCode) {
		applicableHeaderTradeSettlement.setDocumentCurrency(isoCurrencyCode);
	}
	@Override
	public String getDocumentCurrency() {
		return applicableHeaderTradeSettlement.getDocumentCurrency();
	}

	// 792: 0..1 (EXTENDED) INVOICER PARTY
	@Override
	public void setInvoicer(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = createParty(name, address, contact); // BT-44, BG-8, BG-9
		setInvoicer(party);
	}
	@Override
	public void setInvoicer(BusinessParty party) {
		applicableHeaderTradeSettlement.setInvoicer(party);
	}
	@Override
	public BusinessParty getInvoicer() {
		return applicableHeaderTradeSettlement.getInvoicer();
	}

	// 833: 0..1 (COMFORT) INVOICEE PARTY / The "BILL TO"
	@Override
	public void setBillTo(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = createParty(name, address, contact); // BT-44, BG-8, BG-9
		setBillTo(party);
	}
	@Override
	public void setBillTo(BusinessParty party) {
		applicableHeaderTradeSettlement.setBillTo(party);
	}
	@Override
	public BusinessParty getBillTo() {
		return applicableHeaderTradeSettlement.getBillTo();
	}

	// 875:	BG-16.BT-81 0..1 Payment Means Code
	public void setPaymentMeansEnum(PaymentMeansEnum code) {
		applicableHeaderTradeSettlement.setPaymentMeansEnum(code);
	}
	public PaymentMeansEnum getPaymentMeansEnum() {
		try {
			return applicableHeaderTradeSettlement.getPaymentMeansEnum();
		} catch (RuntimeException e) {
			LOG.warning(e.getMessage());
			return null;
		}
	}
	
	// 876:	BG-16.BT-82 0..1 Payment Means
	public void setPaymentMeansText(String text) {
		applicableHeaderTradeSettlement.setPaymentMeansText(text);
	}
	public String getPaymentMeansText() {
		return applicableHeaderTradeSettlement.getPaymentMeansText();
	}

	// 886: BT-8 0..1 Value added tax point date code
	@Override
	public void setTaxPointDateCode(String code) {
		applicableHeaderTradeSettlement.setTaxPointDateCode(code);
	}
	@Override
	public String getTaxPointDateCode() {
		return applicableHeaderTradeSettlement.getTaxPointDateCode();
	}
	
	// 888: BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
	// 903: BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return TradeAllowanceCharge.create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return TradeAllowanceCharge.create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}
	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		if(allowanceOrCharge==null) return; // optional
		applicableHeaderTradeSettlement.getSpecifiedTradeAllowanceCharge().add((TradeAllowanceCharge)allowanceOrCharge);
	}

	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		List<TradeAllowanceChargeType> allowanceChargeList = applicableHeaderTradeSettlement.getSpecifiedTradeAllowanceCharge();
		List<AllowancesAndCharges> res = new ArrayList<AllowancesAndCharges>(allowanceChargeList.size());
		allowanceChargeList.forEach(allowanceOrCharge -> {
			res.add(TradeAllowanceCharge.create(allowanceOrCharge));
		});
		return res;
	}
	
	// 925: BT-20 0..1 PAYMENT TERMS
	//rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram: SpecifiedTradePaymentTerms/ram:Description
	@Override
	public void addPaymentTerm(String description) {
		applicableHeaderTradeSettlement.addPaymentTerm(description);
	}
	@Override
	public void setPaymentTerms(List<String> paymentTerms) {
		applicableHeaderTradeSettlement.setPaymentTerms(paymentTerms);
	}
	@Override
	public List<String> getPaymentTerms() {
		return applicableHeaderTradeSettlement.getPaymentTerms();
	}

	// 927: BG-22 DOCUMENT TOTALS 1..1 - mandatory BT-106, BT-109, BT-112
	@Override
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		TradeSettlementHeaderMonetarySummation documentTotals = TradeSettlementHeaderMonetarySummation.create(lineNet, taxExclusive, taxInclusive);
		applicableHeaderTradeSettlement.setSpecifiedTradeSettlementHeaderMonetarySummation(documentTotals);
		return documentTotals;
	}

	// 928: BG-22.BT-106 - 1..1/1..1
	@Override
	public IAmount getLineNetTotal() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getLineNetTotal();
	}

	// 929: BG-22.BT-108 - 1..1/0..1 (optional
	@Override
	public void setChargesTotal(IAmount amount) {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		tshms.setChargesTotal(amount);
	}
	@Override
	public IAmount getChargesTotal() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getChargesTotal();
	}

	// 930: BG-22.BT-107 - 1..1/0..1 (optional
	@Override
	public void setAllowancesTotal(IAmount amount) {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		tshms.setAllowancesTotal(amount);
	}
	@Override
	public IAmount getAllowancesTotal() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getAllowancesTotal();
	}

	// 931: BG-22.BT-109 - 1..1/1..1
	@Override
	public IAmount getTotalTaxExclusive() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getTotalTaxExclusive();
	}

	// 933: BG-22.BT-110 - 1..1/0..1 (optional
	@Override
	public void setTaxTotal(IAmount amount) {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		tshms.setTaxTotal(amount);
	}
	@Override
	public IAmount getTaxTotal() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getTaxTotal();
	}

	// 937: BG-22.BT-112 - 1..1/1..1
	@Override
	public IAmount getTotalTaxInclusive() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getTotalTaxInclusive();
	}

	// 942: BT-19 0..1 Buyer accounting reference
	@Override
	public void setBuyerAccountingReference(Reference textReference) {
		applicableHeaderTradeSettlement.setBuyerAccountingReference(textReference);
	}
	@Override
	public Reference getBuyerAccountingReference() {
		return applicableHeaderTradeSettlement.getBuyerAccountingReference();
	}

/*
	<rsm:SupplyChainTradeTransaction>
		<ram:IncludedSupplyChainTradeLineItem>
... list
		</ram:IncludedSupplyChainTradeLineItem>
		
		<ram:ApplicableHeaderTradeAgreement>
			<ram:BuyerReference>BUYER_REF_BU123</ram:BuyerReference>
			<ram:SellerTradeParty>
			...
			</ram:SellerTradeParty>
			<ram:BuyerTradeParty>
			...
			</ram:BuyerTradeParty>
			<ram:ApplicableTradeDeliveryTerms>
		</ram:ApplicableHeaderTradeAgreement>
		
		<ram:ApplicableHeaderTradeDelivery>
		...
		</ram:ApplicableHeaderTradeDelivery>
		<ram:ApplicableHeaderTradeSettlement>
		...
		</ram:ApplicableHeaderTradeSettlement>

 */
	// BG-25 1..n ORDER LINE
	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, 
			IAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		// delegieren:
		return SupplyChainTradeLineItem.create(this, id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
	}

	@Override
	public void addLine(OrderLine line) {
		this.supplyChainTradeTransaction.getIncludedSupplyChainTradeLineItem().add((SupplyChainTradeLineItem)line);
	}

	public List<OrderLine> getLines() {
		// delegieren:
		return this.supplyChainTradeTransaction.getLines();
	}

	public String toString() {
		if(super.getExchangedDocumentContext()==null) {
			LOG.warning("***noch nicht initialisiert**");
			return "???";
		}
		super.getExchangedDocumentContext().getBusinessProcessSpecifiedDocumentContextParameter().getID();
		StringBuilder stringBuilder = new StringBuilder();
		
//		stringBuilder.append("[ExchangedDocumentContext:"); // rsm:ExchangedDocumentContext
//		stringBuilder.append(super.getExchangedDocumentContext()==null ? "null" : getExchangedDocumentContext());
		stringBuilder.append("[isTest:"+isTest()); 
		stringBuilder.append(", ProcessType:");
		stringBuilder.append(getProcessType()==null ? "null" : getProcessType());
		stringBuilder.append(", Profile:");
		stringBuilder.append(getCustomization()==null ? "null" : getCustomization());
		
		stringBuilder.append(", ExchangedDocument:");
//		stringBuilder.append(super.getExchangedDocument()==null ? "null" : getExchangedDocument());
		stringBuilder.append(" [ID:").append(getId());
		stringBuilder.append(", Name:").append(getName());
		stringBuilder.append(", TypeCode:").append(getTypeCode());
		getOrderNotes().forEach(note -> {
			stringBuilder.append(", note:").append(note);
		});
		
		stringBuilder.append("]");
		
//		BusinessParty seller = getSeller();
//		if(seller!=null) {
//			stringBuilder.append("\nSeller:");
//			stringBuilder.append(seller);
//		}
		
		this.getLines().forEach(line -> {
			stringBuilder.append("\nline:").append(line);
		});
		
//		stringBuilder.append(", SupplyChainTradeTransaction:");
//		stringBuilder.append(super.getSupplyChainTradeTransaction()==null ? "null" : getSupplyChainTradeTransaction());
		stringBuilder.append("\n]");
		return stringBuilder.toString();
	}

	// ----------------- factories to delegate
	@Override
	public BusinessParty createParty(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		return TradeParty.create(name, tradingName, address, contact); 
	}

	@Override
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		return TradeAddress.create(countryCode, postalCode, city);
	}

	@Override
	public ContactInfo createContactInfo(String contactName, String contactTel, String contactMail) {
		return TradeContact.create(contactName, contactTel, contactMail);
	}

}
