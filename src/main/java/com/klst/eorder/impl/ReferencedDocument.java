package com.klst.eorder.impl;

import java.sql.Timestamp;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.api.CoreOrder;

import un.unece.uncefact.codelist.standard.unece.referencetypecode.d19b.ReferenceTypeCodeContentType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.DocumentCodeType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.FormattedDateTimeType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.ReferenceCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ReferencedDocumentType;

public class ReferencedDocument extends ReferencedDocumentType {
// in CII: implements BG24_AdditionalSupportingDocs, PrecedingInvoice
// in CIO: orderLine AdditionalReferencedDocument
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

	private ReferencedDocument(String docRefId, String code, String referenceTypeCode) {
		super();
//		setSupportingDocumentReference(docRefId);
		super.setIssuerAssignedID(new ID(docRefId));
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

}
