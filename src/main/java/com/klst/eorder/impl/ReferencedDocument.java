package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.ebXml.reflection.Mapper;
import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.SupportingDocument;

import un.unece.uncefact.codelist.standard.unece.referencetypecode.d20a.ReferenceTypeCodeContentType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.DocumentCodeType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.FormattedDateTimeType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.ReferenceCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.ReferencedDocumentType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.BinaryObjectType;

/**
 * Objects of this class are used
 * <p>
 * at document level 
 * <br>in 561: BT-17 0..1 TENDER OR LOT REFERENCE         typeCode==50
 * <br>in 564: BT-18 0..1 (OBJECT IDENTIFIER FOR INVOICE) typeCode==130
 * <br>in 549: BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS typeCode==916
 * <p>
 * at document line level:
 * <br>in  79: 0..n ADDITIONAL REFERENCED PRODUCT DOCUMENT in SpecifiedTradeProduct
 * <br>in 141: 0..n ADDITIONAL REFERENCED DOCUMENT in SpecifiedLineTradeAgreement
 * <br>in 154: BG.25.BT-128 0..1 line object identifier
 * <p>
 * The super class {@code ReferencedDocumentType} contains
 * <blockquote><pre>
 *     protected IDType issuerAssignedID;                      // 550
 *     protected IDType uriid;                                 // 551
 *     protected IDType lineID;                                // 144
 *     protected DocumentCodeType typeCode;                    // 552, 82, 145
 *     protected TextType name;                                // 553
 *     protected BinaryObjectType attachmentBinaryObject;      // 554,555,556
 *     protected ReferenceCodeType referenceTypeCode;
 *     protected FormattedDateTimeType formattedIssueDateTime;
 * </pre></blockquote>
 * <p>
 * {@code uriid}, Xml name = "URIID" is used alternatively with {@code attachmentBinaryObject}.
 * <p>
 * {@code lineID} is used only in {@code _141_AdditionalReferencedDocs}.
 * <p>
 * {@code referenceTypeCode} is used only in {@code object identifier}.
 * 
 * @see com.klst.eorder.api.SupportingDocumentFactory#createSupportigDocument
 * @see com.klst.eorder.api._141_AdditionalReferencedDocs
 * @see com.klst.eorder.api._79_AdditionalReferencedProductDocs
 */
public class ReferencedDocument extends ReferencedDocumentType implements SupportingDocument {
// in CII: implements BG24_AdditionalSupportingDocs, PrecedingInvoice
// in CIO: orderLine AdditionalReferencedDocument, ...
/*
 * BG-3  REFERENZ AUF DIE VORAUSGEGANGENE RECHNUNG gibt es in CIO nicht

aber 549: BG-24  ADDITIONAL SUPPORTING DOCUMENTS

ebenfalls genutzt in BG-25.BT-128 0..1 Objektkennung, 
siehe SupplyChainTradeLineItem.setLineObjectID

/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument

und 141ff ADDITIONAL REFERENCED DOCUMENT (in SpecifiedLineTradeAgreement) mit LineId
 
Beispiel: 
       <!-- document line level:
            <ram:AdditionalReferenceReferencedDocument>                       <!-- 79:
                 <ram:IssuerAssignedID>ADD_REF_PROD_ID</ram:IssuerAssignedID>
                 <ram:URIID>ADD_REF_PROD_URIID</ram:URIID>
                 <ram:TypeCode>6</ram:TypeCode>                                6     Product specification report
                 <ram:Name>ADD_REF_PROD_Desc</ram:Name>
            </ram:AdditionalReferenceReferencedDocument>
       </ram:SpecifiedTradeProduct>
       <ram:SpecifiedLineTradeAgreement>
           ...
            <ram:AdditionalReferencedDocument>                                <!-- 141:
                 <ram:IssuerAssignedID>ADD_REF_DOC_ID</ram:IssuerAssignedID>
                 <ram:URIID>ADD_REF_DOC_URIID</ram:URIID>
                 <ram:LineID>5</ram:LineID>
                 <ram:TypeCode>916</ram:TypeCode>
                 <ram:Name>ADD_REF_DOC_Desc</ram:Name>
            </ram:AdditionalReferencedDocument>
            <ram:AdditionalReferencedDocument>                                <!-- 154: BG.25.BT-128 0..1 line object identifier
                 <ram:IssuerAssignedID>OBJECT_125487</ram:IssuerAssignedID>
                 <ram:TypeCode>130</ram:TypeCode>
                 <ram:ReferenceTypeCode>AWV</ram:ReferenceTypeCode>
            </ram:AdditionalReferencedDocument>

   <ram:AdditionalReferencedDocument>
        <ram:IssuerAssignedID>ADD_REF_DOC_ID</ram:IssuerAssignedID>  <!-- 550:BG-24.BT-122 1..1 Supporting document reference
        <ram:URIID>ADD_REF_DOC_URIID</ram:URIID>                     <!-- 551:BG-24.BT-124
        <ram:TypeCode>916</ram:TypeCode>
        <ram:Name>ADD_REF_DOC_Desc</ram:Name>                        <!-- 554:BG-24.BT-123 0..1 Supporting document description
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
	
	// factory for BG-3
//	@Override
//	public PrecedingInvoice createPrecedingInvoiceReference(String docRefId, Timestamp ts) {
//		return create(docRefId, ts);
//	}

	// factory
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description, Timestamp ts, String uri) {
		ReferencedDocument rd = create(docRefId, lineId, description);
		rd.setExternalDocumentLocation(uri);
		return rd;
	}
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description, Timestamp ts
			, byte[] content, String mimeCode, String filename) {
		ReferencedDocument rd = create(docRefId, lineId, description);
		rd.setAttachedDocument(content, mimeCode, filename);
		return rd;
	}

	static ReferencedDocument create(String docRefId, Reference lineId, String description) {
		ReferencedDocument rd = new ReferencedDocument(docRefId, lineId, description);
		return rd;
	}
	
	static ReferencedDocument create(String docRefId, Timestamp ts) {
		ReferencedDocument rd = new ReferencedDocument(docRefId, (String)null, (String)null);
		rd.setDate(ts);
		return rd;
	}
	
	// factory for
	// 561: BT-17 0..1 Tender or lot reference
	// 564: BT-18 0..1 (OBJECT IDENTIFIER FOR INVOICE)
	// 154: BG.25.BT-128 : To be used for line object identifier (TypeCode value = 130)
	static ReferencedDocument create(String docRefId, String code, String referenceTypeCode) {
		return new ReferencedDocument(docRefId, code, referenceTypeCode);
	}

	// create a factory
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
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}

	private ReferencedDocument(String docRefId, Reference lineId, String description) {
		setDocumentCode(DocumentNameCode.RelatedDocument.getValueAsString());
		setDocumentReference(new ID(docRefId));
		setLineReference(lineId);
		setSupportingDocumentDescription(description);
	}

	private ReferencedDocument(String docRefId, String code, String referenceTypeCode) {
		setDocumentReference(new ID(docRefId));
		setDocumentCode(code);
		setReferenceCode(referenceTypeCode);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[DocumentCode:");
		stringBuilder.append(getDocumentCode()==null ? "null" : getDocumentCode());
		stringBuilder.append(", DocumentReference:");
		stringBuilder.append(getDocumentReference()==null ? "null" : getDocumentReference());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	// 550: BG-24.BT-122 1..1 Supporting document reference
	@Override
	public void setDocumentReference(Reference documentReference) {
		// Reference extends Identifier
		if(documentReference!=null) super.setIssuerAssignedID((ID)documentReference);
	}
	@Override
	public Reference getDocumentReference() {
		return super.getIssuerAssignedID()==null ? null : new ID(super.getIssuerAssignedID());
	}

	// 551: BG-24.BT-124 0..1 External document location
	@Override
	public void setExternalDocumentLocation(String locationUri) {
		if(locationUri==null) return;
		super.setURIID(new ID(locationUri));	
	}
	@Override
	public String getExternalDocumentLocation() {
		return super.getURIID()==null? null : getURIID().getValue();
	}

	// 144: document line reference
	@Override
	public void setLineReference(Reference lineReference) {
		if(lineReference!=null) super.setLineID((ID)lineReference);
	}
	@Override
	public Reference getLineReference() {
		return super.getLineID()==null ? null : new ID(super.getLineID());		
	}

	// 552, 82, 145
	@Override
	public String getDocumentCode() {
		DocumentCodeType documentCode = super.getTypeCode();
		if(documentCode==null) {
			// ===> isPrecedingInvoice
			//super.getIssuerAssignedID()
			//super.getFormattedIssueDateTime();
		}
		return documentCode.getValue();
	}
	// code == 916 :"ADDITIONAL SUPPORTING DOCUMENTS" ==> BG-24
	// code ==  50 : isValidatedPricedTender() ==> BT-17
	// code == 130 : isInvoicingDataSheet()    ==> BT-18
	@Override
	public void setDocumentCode(String code) {
		if(code==null) return;
		DocumentCodeType documentCode = new DocumentCodeType();
		documentCode.setValue(code);
		super.setTypeCode(documentCode);
	}
	boolean isRelatedDocument() {
		String typeCode = getDocumentCode();
		if(typeCode==null) return false;
		return typeCode.equals(DocumentNameCode.RelatedDocument.getValueAsString());
	}
	boolean isValidatedPricedTender() {
		String typeCode = getDocumentCode();
		if(typeCode==null) return false;
		return typeCode.equals(DocumentNameCode.ValidatedPricedTender.getValueAsString());
	}
	boolean isInvoicingDataSheet() {
		String typeCode = getDocumentCode();
		if(typeCode==null) return false;
		return typeCode.equals(DocumentNameCode.InvoicingDataSheet.getValueAsString());
	}

	// 553: BG-24.BT-123 0..1 Supporting document description
	@Override
	public void setSupportingDocumentDescription(String text) {
		Mapper.set(this, "name", text);
	}
	@Override
	public String getSupportingDocumentDescription() {
		return super.getName()==null ? null : getName().getValue();
	}

	// 554,555,556: BG-24.BT-125 0..1 Attached document
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

	// ReferenceTypeCode, Kennung des Schemas
	/*
	 * To be used for Object identifier (TypeCode value = 130)
	 * If it may be not clear for the receiver what scheme is used for the identifier,
	 * a conditional scheme identifier should be used that shall be chosen from the UNTDID 1153 code list entries.
	 */
	private void setReferenceCode(String code) {
		if(code==null) return;
		ReferenceCodeType referenceCode = new ReferenceCodeType();
		referenceCode.setValue(ReferenceTypeCodeContentType.fromValue(code));
		super.setReferenceTypeCode(referenceCode);
	}
	public String getReferenceCode() {
		return super.getReferenceTypeCode().getValue().value();
	}

	// BG-3.BT-26 0..1 Preceding Invoice issue date / implements PrecedingInvoice
	// 151 Timestamp for the issuance
	@Override
	public Timestamp getDateAsTimestamp() {
		if(super.getFormattedIssueDateTime()==null) return null;
		FormattedDateTimeType dateTime = getFormattedIssueDateTime(); // FormattedDateTimeType
		return dateTime==null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());		
	}
	@Override
	public void setDate(Timestamp ts) {
		if(ts==null) return;
		super.setFormattedIssueDateTime(DateTimeFormatStrings.toFormattedDateTime(ts));
	}

}
