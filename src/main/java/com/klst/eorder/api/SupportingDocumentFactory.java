package com.klst.eorder.api;

public interface SupportingDocumentFactory {

	/**
	 * use this to create an External document reference
	 * 
	 * @param docRefId    - BG-24.BT-122 Supporting document reference
	 * @param description - BG-24.BT-123 Supporting document description
	 * @param uri         - location uri
	 * @return
	 * 
	 * @see BG24_AdditionalSupportingDocs
	 */
	public SupportingDocument createSupportigDocument(String docRefId, String description, String uri);

	/**
	 * use this to create an attached document reference
	 * 
	 * @param docRefId    - BG-24.BT-122 Supporting document reference
	 * @param description - BG-24.BT-123 Supporting document description
	 * @param content     - BG-24.BT-125 Attached document content
	 * @param mimeCode
	 * @param filename
	 * @return
	 * 
	 * @see BG24_AdditionalSupportingDocs
	 */
	public SupportingDocument createSupportigDocument(String docRefId, String description, byte[] content, String mimeCode, String filename);

}
