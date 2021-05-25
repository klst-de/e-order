package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.Reference;

/*
 * used in
 * - at document level 
 * - 561  : BT-17 0..1 TENDER OR LOT REFERENCE
 * - 564  : BT-18 0..1 (OBJECT IDENTIFIER FOR INVOICE)
 * - 549ff: BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS
 * 
 * - at document line level:
 * -  79ff: 0..n ADDITIONAL REFERENCED PRODUCT DOCUMENT in SpecifiedTradeProduct
 * - 141ff: 0..n ADDITIONAL REFERENCED DOCUMENT in SpecifiedLineTradeAgreement
 * - 154: BG.25.BT-128 0..1 line object identifier
 */
public interface SupportingDocument extends SupportingDocumentFactory {

	// 552, 82, 145
	public void setDocumentCode(String code);
	public String getDocumentCode();
	
	// 151
	public void setDate(Timestamp ts);
	public Timestamp getDateAsTimestamp();

	/**
	 * Additional supporting document reference
	 * <p>
	 * Cardinality:     1..1 (mandatory)
	 * <br>EN16931-ID: 	BG-24.BT-122
	 * <br>Rule ID: 	BR-52: Each Additional supporting document (BG-24) shall contain a document reference (BT-122).
	 * <br>Order-X-No: 	550, 80, 142
	 * 
	 * @param documentReference, an identifier of the supporting document.
	 */
	public void setDocumentReference(Reference documentReference);
	public Reference getDocumentReference();
	
	/**
	 * External document location
	 * <p>
	 * The URL (Uniform Resource Locator) that identifies where the external document is located.
	 * A means of locating the resource including its primary access mechanism, e.g. http:// or ftp://.
	 * 
	 * External document location shall be used if the Buyer requires additional information to support the document.
	 * External documents do not form part of the invoice. Risks can be involved when accessing external documents.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-24.BT-124
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	551, 81, 143
	 * 
	 * @param url
	 */
	public void setExternalDocumentLocation(String location);
	public String getExternalDocumentLocation();

	/**
	 * Additional supporting document line reference
	 * <p>
	 * Used in ADDITIONAL REFERENCED DOCUMENT (SpecifiedLineTradeAgreement)
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	144
	 * 
	 * @param lineReference
	 */
	public void setLineReference(Reference lineReference);
	public Reference getLineReference();

	/**
	 * Supporting document description
	 * <p>
	 * A description of the supporting document. Such as: timesheet, usage report etc.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-24.BT-123
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	553, 83, 146
	 * 
	 * @param text
	 */
	public void setSupportingDocumentDescription(String text);
	public String getSupportingDocumentDescription();

	/**
	 * Attached document
	 * An attached document embedded as binary object or sent together with the invoice.
	 * 
	 * Attached document is used when documentation shall be stored with the Invoice 
	 * for future reference or audit purposes.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-24.BT-125
	 * <br>Rule ID: 	 
	 * <br>Order-X-No: 	554,555,556, 84, 147
	 * 
	 * @param doc      - 554 binary content
	 * @param mimeCode - 555 The mime code of the attached document
	 * @param filename - 556 The file name of the attached document
	 */
	public void setAttachedDocument(byte[] doc, String mimeCode, String filename);
	public byte[] getAttachedDocument();
	public String getAttachedDocumentMimeCode();
	public String getAttachedDocumentFilename();

}
