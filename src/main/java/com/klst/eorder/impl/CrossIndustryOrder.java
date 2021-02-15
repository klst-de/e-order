package com.klst.eorder.impl;

import java.util.List;
import java.util.logging.Logger;

import com.klst.einvoice.reflection.CopyCtor;
import com.klst.eorder.BG1_OrderNote;
import com.klst.eorder.BG2_ProcessControl;
import com.klst.eorder.OrderNote;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.DocumentContextParameterType;
import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IndicatorType;

public class CrossIndustryOrder extends SCRDMCCBDACIOMessageStructureType 
	implements BG1_OrderNote, BG2_ProcessControl {

	private static final Logger LOG = Logger.getLogger(CrossIndustryOrder.class.getName());
	
	public CrossIndustryOrder(SCRDMCCBDACIOMessageStructureType doc) {
		super();
		if(doc!=null) {
			CopyCtor.invokeCopy(this, doc);
			LOG.info("copy ctor:"+this);
		}
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

//	@Override
	public String getProcessType() {
		DocumentContextParameterType documentContextParameter = super.getExchangedDocumentContext().getBusinessProcessSpecifiedDocumentContextParameter();
		return documentContextParameter==null ? null : new ID(documentContextParameter.getID()).getContent();
	}

//	@Override
	public String getCustomization() { // aka Profile
		DocumentContextParameterType documentContextParameterType = 
				super.getExchangedDocumentContext().getGuidelineSpecifiedDocumentContextParameter();
		return documentContextParameterType==null ? null : new ID(documentContextParameterType.getID()).getContent();
	}

//	----------------------------------
/*
	<rsm:ExchangedDocument>
		<ram:ID>PO123456789</ram:ID>
		<ram:Name>Doc Name</ram:Name>
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
//	@Override
	public String getId() {
		return super.getExchangedDocument().getID().getValue();
	}

	public String getName() {
		return super.getExchangedDocument().getName().getValue();
	}
	
//	@Override
	public String getTypeCode() {
		return super.getExchangedDocument().getTypeCode().getValue();
	}
//	static DocumentNameCode getTypeCode(CrossIndustryInvoiceType doc) {
//		return DocumentNameCode.valueOf(doc.getExchangedDocument().getTypeCode());
//	}

	// TODO
//	public Timestamp getIssueDateAsTimestamp() {
//		return getIssueDateAsTimestamp(this);
//	}
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
	public String toString() {
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
		
		stringBuilder.append(", SupplyChainTradeTransaction:");
		stringBuilder.append(super.getSupplyChainTradeTransaction()==null ? "null" : getSupplyChainTradeTransaction());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
