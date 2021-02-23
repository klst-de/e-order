package com.klst.eorder.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.eorder.BG1_OrderNote;
import com.klst.eorder.BG2_ProcessControl;
import com.klst.eorder.OrderNote;
import com.klst.untdid.codelist.DateTimeFormats;
import com.klst.untdid.codelist.DocumentNameCode;

import un.unece.uncefact.data.standard.qualifieddatatype._103.DocumentCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.DocumentContextParameterType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ExchangedDocumentContextType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ExchangedDocumentType;
import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.DateTimeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IndicatorType;

public class CrossIndustryOrder extends SCRDMCCBDACIOMessageStructureType 
	implements BG1_OrderNote, BG2_ProcessControl {

	private static final Logger LOG = Logger.getLogger(CrossIndustryOrder.class.getName());
	
	SupplyChainTradeTransaction supplyChainTradeTransaction;
	HeaderTradeAgreement applicableHeaderTradeAgreement;
	
	public CrossIndustryOrder(SCRDMCCBDACIOMessageStructureType doc) {
		super();
		if(doc!=null) {
			CopyCtor.invokeCopy(this, doc);
//			LOG.info("copy ctor:"+this); // toString liefert hier NPE wg.getLines
		}
//		if(super.getSupplyChainTradeTransaction()==null) {
//			supplyChainTradeTransaction = SupplyChainTradeTransaction.create();
//			applicableHeaderTradeAgreement = HeaderTradeAgreement.create();
//			super.setSupplyChainTradeTransaction(supplyChainTradeTransaction);
//		} else {
//			supplyChainTradeTransaction = SupplyChainTradeTransaction.create(getSupplyChainTradeTransaction());
//			applicableHeaderTradeAgreement = supplyChainTradeTransaction.getHeaderTradeAgreement();	
//		}
		supplyChainTradeTransaction = SupplyChainTradeTransaction.create(super.getSupplyChainTradeTransaction());
		applicableHeaderTradeAgreement = supplyChainTradeTransaction.createtHeaderTradeAgreement();
		LOG.info("copy ctor:"+this);
/*
		if(super.getSupplyChainTradeTransaction()==null) {
			applicableHeaderTradeSettlement = ApplicableHeaderTradeSettlement.create();
			applicableHeaderTradeDelivery = ApplicableHeaderTradeDelivery.create();
			super.setSupplyChainTradeTransaction(new SupplyChainTradeTransactionType());
		} else {
			applicableHeaderTradeSettlement = ApplicableHeaderTradeSettlement.create(getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement());
			applicableHeaderTradeDelivery = ApplicableHeaderTradeDelivery.create(getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery());
		}
		getSupplyChainTradeTransaction().setApplicableHeaderTradeSettlement(applicableHeaderTradeSettlement);
		getSupplyChainTradeTransaction().setApplicableHeaderTradeDelivery(applicableHeaderTradeDelivery);

 */
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
//	public CoreInvoice createInvoice(String profile, String processType, DocumentNameCode code);
	public static CrossIndustryOrder createOrder(String profile, String processType, DocumentNameCode code) {
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
//	@Override
	public void setId(String id) {
		super.getExchangedDocument().setID(new ID(id)); // No identification scheme is to be used.
	}
	
//	@Override
	public String getId() {
		return super.getExchangedDocument().getID().getValue();
	}

	public String getName() { // ExchangedDocument.Name wird in einvoice nicht verwendet
		return super.getExchangedDocument().getName().getValue();
	}
	
//	@Override
	public String getTypeCode() {
		return super.getExchangedDocument().getTypeCode().getValue();
	}
	public DocumentNameCode getDocumentCode() {
		return DocumentNameCode.valueOf(super.getExchangedDocument().getTypeCode());
	}

	/* Document issue date, BT-2  Date (mandatory) 
	 * Das Datum, an dem der Beleg ausgestellt wurde.
	 */
//	@Override
	public void setIssueDate(Timestamp ts) {
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		super.getExchangedDocument().setIssueDateTime(dateTime);
	}

//	@Override
	public Timestamp getIssueDateAsTimestamp() {
		DateTimeType dateTime = super.getExchangedDocument().getIssueDateTime();
		return DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}

	// TODO ram:CopyIndicator , ram:PurposeCode , ram:RequestedResponseTypeCode , 
	
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
	public List<SupplyChainTradeLineItem> getLines() {
//		List<SupplyChainTradeLineItemType> lines = super.getSupplyChainTradeTransaction().getIncludedSupplyChainTradeLineItem();
//		List<SupplyChainTradeLineItem> resultLines = new ArrayList<SupplyChainTradeLineItem>(lines.size());
//		lines.forEach(line -> {
//			resultLines.add(SupplyChainTradeLineItem.create(line));
//		});
//		return resultLines;
		
		// delegieren:
		return this.supplyChainTradeTransaction.getLines();
	}

	public TradeParty getSeller() {
		return this.applicableHeaderTradeAgreement.getSeller();
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
		
		stringBuilder.append("\nSeller:");
		stringBuilder.append(getSeller()==null ? "null" : getSeller());
		
		this.getLines().forEach(line -> {
			stringBuilder.append("\nline:").append(line);
		});
		
		stringBuilder.append(", SupplyChainTradeTransaction:");
		stringBuilder.append(super.getSupplyChainTradeTransaction()==null ? "null" : getSupplyChainTradeTransaction());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
