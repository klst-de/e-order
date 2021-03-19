package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.ebXml.reflection.Mapper;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.api.SupportingDocument;

import un.unece.uncefact.codelist.standard.unece.referencetypecode.d19b.ReferenceTypeCodeContentType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.DocumentCodeType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.FormattedDateTimeType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.ReferenceCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ReferencedDocumentType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.BinaryObjectType;

public class ReferencedDocument extends ReferencedDocumentType implements SupportingDocument {
// in CII: implements BG24_AdditionalSupportingDocs, PrecedingInvoice
// in CIO: orderLine AdditionalReferencedDocument
/*
 * BG-3  REFERENZ AUF DIE VORAUSGEGANGENE RECHNUNG gibt es in CIO nicht
 * aber BG-24  ECHNUNGSBEGRÜNDENDE UNTERLAGEN
BG-24 + 0..n ADDITIONAL SUPPORTING DOCUMENTS
BG-24.BT-122
..
BG-24.BT-125

ebenfalls genutzt in BG.25.BT-128 0..1 Objektkennung, siehe SupplyChainTradeLineItem.setLineObjectID

/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument
 
Beispiel:

   <ram:AdditionalReferencedDocument>
        <ram:IssuerAssignedID>ADD_REF_DOC_ID</ram:IssuerAssignedID>  <!-- BG-24.BT-122 1..1 Supporting document reference
        <ram:URIID>ADD_REF_DOC_URIID</ram:URIID>                     <!-- BG-24.BT-124
        <ram:TypeCode>916</ram:TypeCode>
        <ram:Name>ADD_REF_DOC_Desc</ram:Name>                        <!-- BG-24.BT-123 0..1 Supporting document description
   </ram:AdditionalReferencedDocument>
   <ram:AdditionalReferencedDocument>
        <ram:IssuerAssignedID>TENDER_ID</ram:IssuerAssignedID>
        <ram:TypeCode>50</ram:TypeCode>
   </ram:AdditionalReferencedDocument>
   <ram:AdditionalReferencedDocument>
        <ram:IssuerAssignedID>OBJECT_ID</ram:IssuerAssignedID>
        <ram:TypeCode>130</ram:TypeCode>
        <ram:ReferenceTypeCode>AWV</ram:ReferenceTypeCode>
   </ram:AdditionalReferencedDocument
 
	
 */
	// super member:
//    protected IDType issuerAssignedID;
//    protected IDType uriid;
//    protected IDType lineID;
//    protected DocumentCodeType typeCode;
//    protected TextType name;
//    protected BinaryObjectType attachmentBinaryObject;
//    protected ReferenceCodeType referenceTypeCode;
//    protected FormattedDateTimeType formattedIssueDateTime;
	
	// factory for BG-3
//	@Override
//	public PrecedingInvoice createPrecedingInvoiceReference(String docRefId, Timestamp ts) {
//		return create(docRefId, ts);
//	}

	// factory for BG-24
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, String description, String uri) {
		ReferencedDocument rd = create(docRefId, description);
		rd.setExternalDocumentLocation(uri);
		return rd;
	}
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, String description, byte[] content, String mimeCode, String filename) {
		ReferencedDocument rd = create(docRefId, description);
		rd.setAttachedDocument(content, mimeCode, filename);
		return rd;
	}

	static ReferencedDocument create(String docRefId, String description) {
		ReferencedDocument rd = new ReferencedDocument(docRefId, description);
		return rd;
	}
	
	static ReferencedDocument create(String docRefId, Timestamp ts) {
		ReferencedDocument rd = new ReferencedDocument(docRefId, (String)null, (String)null);
		rd.setDate(ts);
		return rd;
	}
	
	static ReferencedDocument create(String docRefId, String code, String referenceTypeCode) {
		return new ReferencedDocument(docRefId, code, referenceTypeCode);
	}

	static ReferencedDocument create() {
		return new ReferencedDocument(null); 
	}
	// copy factory
	static ReferencedDocument create(ReferencedDocumentType object) {
		if(object instanceof ReferencedDocumentType && object.getClass()!=ReferencedDocumentType.class) {
			// object is instance of a subclass of ReferencedDocumentType, but not ReferencedDocumentType itself
			return (ReferencedDocument)object;
		} else {
			return new ReferencedDocument(object); 
		}
	}

	// copy ctor
	private ReferencedDocument(ReferencedDocumentType object) {
		super();
		if(object!=null) {
			CopyCtor.invokeCopy(this, object);
		}
	}

	private ReferencedDocument(String docRefId, String description) {
		super();
		setDocumentReference(new ID(docRefId));
		setSupportingDocumentDescription(description);
	}

	// BG.25.BT-128 : To be used for line object identifier (TypeCode value = 130)
	private ReferencedDocument(String docRefId, String code, String referenceTypeCode) {
		super();
		setDocumentReference(new ID(docRefId));
		setDocumentCode(code);
		setReferenceCode(referenceTypeCode);
	}

	// code ==  50 : isValidatedPricedTender() ==> BT-17
	// code == 130 : isInvoicingDataSheet()    ==> BT-18
	private void setDocumentCode(String code) {
		if(code==null) return;
		DocumentCodeType documentCode = new DocumentCodeType();
		documentCode.setValue(code);
		super.setTypeCode(documentCode);
	}
	private String getDocumentCode() {
		DocumentCodeType documentCode = super.getTypeCode();
		if(documentCode==null) {
			// ===> isPrecedingInvoice
			//super.getIssuerAssignedID()
			//super.getFormattedIssueDateTime();
		}
		return documentCode.getValue();
	}

// ReferenceTypeCode Kennung des Schemas BT-18-1
// SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument/ram:ReferenceTypeCode
/*
To be used for Object identifier (TypeCode value = 130)
If it may be not clear for the receiver what scheme is used for the identifier, 
a conditional scheme identifier should be used that shall be chosen from the UNTDID 1153 code list [6] entries.
 */
	private void setReferenceCode(String code) {
		if(code==null) return;
		ReferenceCodeType referenceCode = new ReferenceCodeType();
//		referenceCode.setValue(code); // setValue(enum ReferenceTypeCodeContentType value)
		referenceCode.setValue(ReferenceTypeCodeContentType.fromValue(code));
		super.setReferenceTypeCode(referenceCode);
	}
	public String getReferenceCode() {
		return super.getReferenceTypeCode().getValue().value();
	}

	// BG-3.BT-26 ++ 0..1 Preceding Invoice issue date / implements PrecedingInvoice
//	@Override
	public Timestamp getDateAsTimestamp() {
		if(super.getFormattedIssueDateTime()==null) return null;
		FormattedDateTimeType dateTime = getFormattedIssueDateTime(); // FormattedDateTimeType
		return dateTime==null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());		
	}
//	@Override
	public void setDate(Timestamp ts) {
		if(ts==null) return;
		super.setFormattedIssueDateTime(DateTimeFormatStrings.toFormattedDateTime(ts));
	}

	// --------------------------------------- BG-24
	// BG-24.BT-122 1..1 Supporting document reference
	@Override
	public void setDocumentReference(Reference documentReference) {
		// Reference extends Identifier
		if(documentReference!=null) super.setIssuerAssignedID((ID)documentReference);
	}

	@Override
	public Reference getDocumentReference() {
		return super.getIssuerAssignedID()==null ? null : new ID(super.getIssuerAssignedID());
	}

//	/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument/ram:Name
	// BG-24.BT-123 0..1 Supporting document description
	@Override
	public void setSupportingDocumentDescription(String text) {
		Mapper.set(this, "name", text);
	}
	@Override
	public String getSupportingDocumentDescription() {
		return super.getName()==null ? null : getName().getValue();
	}

	// BG-24.BT-124 0..1 External document location
// /rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument/ram:URIID
	@Override
	public void setExternalDocumentLocation(String locationUri) {
		if(locationUri==null) return;
		super.setURIID(new ID(locationUri));	
	}
	@Override
	public String getExternalDocumentLocation() {
		return super.getURIID()==null? null : getURIID().getValue();
	}

	// BG-24.BT-125 0..1 Attached document
	@Override
	public void setAttachedDocument(byte[] content, String mimeCode, String filename) {
		BinaryObjectType e = new BinaryObjectType();
		e.setValue(content);
		e.setMimeCode(mimeCode);
		e.setFilename(filename);
		super.setAttachmentBinaryObject(e);
	}
	@Override
	public byte[] getAttachedDocument() {
		BinaryObjectType binaryObjects = super.getAttachmentBinaryObject();
		return binaryObjects==null ? null : binaryObjects.getValue();
	}
	@Override
	public String getAttachedDocumentMimeCode() {
		BinaryObjectType binaryObjects = super.getAttachmentBinaryObject();
		return binaryObjects==null ? null : binaryObjects.getMimeCode();
	}
	@Override
	public String getAttachedDocumentFilename() {
		BinaryObjectType binaryObjects = super.getAttachmentBinaryObject();
		return binaryObjects==null ? null : binaryObjects.getFilename();	
	}

//	/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument/ram:TypeCode
//	==> private void setDocumentCode(String code)

}
