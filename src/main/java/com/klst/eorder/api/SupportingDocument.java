package com.klst.eorder.api;

import com.klst.edoc.api.Reference;

public interface SupportingDocument extends SupportingDocumentFactory {

	/**
	 * set business term BG-24.BT-122 (mandatory): Supporting document reference
	 * <p>
	 * Business rule BR-52: Each Additional supporting document (BG-24) shall contain a 
	 *                      Supporting document reference (BT-122).
	 * @param documentReference, an identifier of the supporting document.
	 */
	public void setDocumentReference(Reference documentReference);
	public Reference getDocumentReference();
	
	/**
	 * Supporting document description
	 * <p>
	 * A description of the supporting document. Such as: timesheet, usage report etc.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>ID: 		BG-24.BT-123
	 * <br>Rule ID: 	 
	 * <br>Req.ID: 	R36
	 * 
	 * @param text
	 */
	public void setSupportingDocumentDescription(String text);
	public String getSupportingDocumentDescription();

	/**
	 * External document location
	 * <p>
	 * The URL (Uniform Resource Locator) that identifies where the external document is located.
	 * A means of locating the resource including its primary access mechanism, e.g. http:// or ftp://.
	 * 
	 * External document location shall be used if the Buyer requires additional information to support the Invoice.
	 * External documents do not form part of the invoice. Risks can be involved when accessing external documents.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>ID: 		BG-24.BT-124
	 * <br>Rule ID: 	 
	 * <br>Req.ID: 	R36
	 * 
	 * @param url
	 */
	public void setExternalDocumentLocation(String location);
	public String getExternalDocumentLocation();

	/**
	 * Attached document
	 * An attached document embedded as binary object or sent together with the invoice.
	 * 
	 * Attached document is used when documentation shall be stored with the Invoice 
	 * for future reference or audit purposes.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>ID: 		BG-24.BT-125
	 * <br>Rule ID: 	 
	 * <br>Req.ID:  R35
	 * 
	 * @param binary content BT-125
	 * @param mimeCode BT-125-1 The mime code of the attached document
	 * @param filename BT-125-2 The file name of the attached document
	 */
	public void setAttachedDocument(byte[] doc, String mimeCode, String filename);
	public byte[] getAttachedDocument();
	public String getAttachedDocumentMimeCode();
	public String getAttachedDocumentFilename();

}
