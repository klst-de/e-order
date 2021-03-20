package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG22_DocumentTotals;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;

import un.unece.uncefact.data.standard.qualifieddatatype._103.DocumentCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.DocumentContextParameterType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ExchangedDocumentContextType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ExchangedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeAllowanceChargeType;
import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.DateTimeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IndicatorType;

public class CrossIndustryOrder extends SCRDMCCBDACIOMessageStructureType 
	implements CoreOrder {

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
		super();
		if(doc!=null) {
			CopyCtor.invokeCopy(this, doc);
//			LOG.info("copy ctor:"+this); // toString liefert hier NPE wg.getLines
		}

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

		LOG.config("copy ctor:"+this);
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
			super.getExchangedDocumentContext().setBusinessProcessSpecifiedDocumentContextParameter(guidelineSpecifiedDocumentContextParameter);
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
	public static final boolean PROD = false;
	public static final boolean TEST = true;
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

//	----------------------------------
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
	// Invoice number BT-1 Identifier (mandatory) - A unique identification of the Invoice.
	@Override
	public void setId(String id) {
		super.getExchangedDocument().setID(new ID(id)); // No identification scheme is to be used.
	}
	
	@Override
	public String getId() {
		return super.getExchangedDocument().getID().getValue();
	}

	/* Document issue date, BT-2  Date (mandatory) 
	 * Das Datum, an dem der Beleg ausgestellt wurde.
	 */
	@Override
	public void setIssueDate(Timestamp ts) {
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		super.getExchangedDocument().setIssueDateTime(dateTime);
	}

	@Override
	public Timestamp getIssueDateAsTimestamp() {
		DateTimeType dateTime = super.getExchangedDocument().getIssueDateTime();
		return DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}

	// ExchangedDocument.Name wird in einvoice nicht verwendet
	public String getName() {
		return super.getExchangedDocument().getName().getValue();
	}
	
//	@Override // für toString
	private String getTypeCode() {
		return super.getExchangedDocument().getTypeCode().getValue();
	}
	@Override
	public DocumentNameCode getDocumentCode() {
		return DocumentNameCode.valueOf(super.getExchangedDocument().getTypeCode());
	}

	// TODO ram:CopyIndicator 
	//    , ram:PurposeCode 
	//    , ram:RequestedResponseTypeCode , 
	
	//ram:IncludedNote
	@Override // factory
	public OrderNote createNote(String subjectCode, String content) {
		// delegieren:
		return Note.create(subjectCode, content);
	}

	@Override // setter
	public void addNote(OrderNote note) {
		super.getExchangedDocument().getIncludedNote().add((Note)note);
	}

	@Override // getter
	public List<OrderNote> getOrderNotes() {
		// delegieren:
		return Note.getNotes(super.getExchangedDocument().getIncludedNote());
	}	

	// BT-5 + 1..1 Invoice currency code
	@Override
	public void setDocumentCurrency(String isoCurrencyCode) {
		// delegieren:
		applicableHeaderTradeSettlement.setDocumentCurrency(isoCurrencyCode);
	}

	@Override
	public String getDocumentCurrency() {
		return applicableHeaderTradeSettlement.getDocumentCurrency();
	}

	// BT-10
	@Override
	public void setBuyerReference(String reference) {
		applicableHeaderTradeAgreement.setBT10_BuyerReference(reference);
	}
	@Override
	public String getBuyerReferenceValue() {
		return applicableHeaderTradeAgreement.getBuyerReferenceValue();
	}
	
	// BT-12 + 0..1 Contract reference
	@Override
	public void setContractReference(String id) {
		applicableHeaderTradeAgreement.setContractReference(id);	
	}
	@Override
	public String getContractReference() {
		return applicableHeaderTradeAgreement.getContractReference();	
	}

	// BT-13 + 0..1 Purchase order reference
	@Override
	public void setPurchaseOrderReference(String id) {
		applicableHeaderTradeAgreement.setPurchaseOrderReference(id);	
	}
	@Override
	public String getPurchaseOrderReference() {
		return applicableHeaderTradeAgreement.getPurchaseOrderReference();	
	}

	// BT-17 + 0..1 Tender or lot reference
	@Override
	public void setTenderOrLotReference(String docRefId) {
		applicableHeaderTradeAgreement.setTenderOrLotReference(docRefId);	
	}
	@Override
	public String getTenderOrLotReference() {
		return applicableHeaderTradeAgreement.getTenderOrLotReference();	
	}

	// BT-19 + 0..1 Buyer accounting reference
	@Override
	public void setBuyerAccountingReference(Reference textReference) {
		applicableHeaderTradeSettlement.setBuyerAccountingReference(textReference);
	}
	@Override
	public Reference getBuyerAccountingReference() {
		return applicableHeaderTradeSettlement.getBuyerAccountingReference();
	}

	// BG-4 + 1..1 SELLER @see BG4_Seller
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

	// BG-7 + 1..1 BUYER @see BG7_Buyer
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

	@Override
	public void setShipToParty(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = createParty(name, address, contact);
		setShipToParty(party);
	}
	@Override
	public void setShipToParty(BusinessParty party) {
		applicableHeaderTradeDelivery.setShipToParty(party);
	}
	@Override
	public BusinessParty getShipToParty() {
		return applicableHeaderTradeDelivery.getShipToParty();
	}

	@Override
	public void setShipFromParty(String name, PostalAddress address, ContactInfo contact) {
		BusinessParty party = createParty(name, address, contact);
		setShipFromParty(party);
	}
	@Override
	public void setShipFromParty(BusinessParty party) {
		applicableHeaderTradeDelivery.setShipFromParty(party);
	}
	@Override
	public BusinessParty getShipFromParty() {
		return applicableHeaderTradeDelivery.getShipFromParty();
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

	@Override
	public void setQuotationReference(String id) {
		applicableHeaderTradeAgreement.setQuotationReference(id);
	}
	@Override
	public String getQuotationReference() {
		return applicableHeaderTradeAgreement.getQuotationReference();
	}

	@Override
	public void setBlanketOrderReference(String id) {
		applicableHeaderTradeAgreement.setBlanketOrderReference(id);
	}
	@Override
	public String getBlanketOrderReference() {
		return applicableHeaderTradeAgreement.getBlanketOrderReference();
	}

	@Override
	public void setPreviousOrderReference(String id) {
		applicableHeaderTradeAgreement.setPreviousOrderReference(id);
	}

	@Override
	public String getPreviousOrderReference() {
		return applicableHeaderTradeAgreement.getPreviousOrderReference();
	}

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
	
	// BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
	// BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// delegieren:
		return TradeAllowanceCharge.create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// delegieren:
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
	
	// BG-22 DOCUMENT TOTALS 1..1 - mandatory BT-106, BT-109, BT-112
	@Override
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		TradeSettlementHeaderMonetarySummation documentTotals = TradeSettlementHeaderMonetarySummation.create(lineNet, taxExclusive, taxInclusive);
		applicableHeaderTradeSettlement.setSpecifiedTradeSettlementHeaderMonetarySummation(documentTotals);
		return documentTotals;
	}

	// BG-22.BT-106 - 1..1/1..1
	@Override
	public IAmount getLineNetTotal() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getLineNetTotal();
	}

	// BG-22.BT-109 - 1..1/1..1
	@Override
	public IAmount getTotalTaxExclusive() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getTotalTaxExclusive();
	}

	// BG-22.BT-112 - 1..1/1..1
	@Override
	public IAmount getTotalTaxInclusive() {
		TradeSettlementHeaderMonetarySummation tshms =
		TradeSettlementHeaderMonetarySummation.create(applicableHeaderTradeSettlement.getSpecifiedTradeSettlementHeaderMonetarySummation());
		return tshms.getTotalTaxInclusive();
	}

	// BG-22.BT-107 - 1..1/0..1 (optional
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

	// BG-22.BT-108 - 1..1/0..1 (optional
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

	// BG-22.BT-110 - 1..1/0..1 (optional
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

	// BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS
	public SupportingDocument createSupportigDocument(String docRefId, String description, String uri) {
		// delegieren
		ReferencedDocument rd = ReferencedDocument.create(docRefId, description);
		rd.setExternalDocumentLocation(uri);
		return rd;
	}
	public SupportingDocument createSupportigDocument(String docRefId, String description, byte[] content, String mimeCode, String filename) {
		// delegieren
		ReferencedDocument rd = ReferencedDocument.create(docRefId, description);
		rd.setAttachedDocument(content, mimeCode, filename);
		return rd;
	}
	public void addSupportigDocument(SupportingDocument supportigDocument) {
		applicableHeaderTradeAgreement.addSupportigDocument(supportigDocument);
	}
	public List<SupportingDocument> getAdditionalSupportingDocuments() {
		return applicableHeaderTradeAgreement.getAdditionalSupportingDocuments();
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
			IAmount priceAmount, String itemName) {
		// delegieren:
		return SupplyChainTradeLineItem.create(this, id, (Quantity)quantity, (Amount)lineTotalAmount, (UnitPriceAmount)priceAmount, itemName);
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
		
		stringBuilder.append(", SupplyChainTradeTransaction:");
		stringBuilder.append(super.getSupplyChainTradeTransaction()==null ? "null" : getSupplyChainTradeTransaction());
		stringBuilder.append("]");
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
