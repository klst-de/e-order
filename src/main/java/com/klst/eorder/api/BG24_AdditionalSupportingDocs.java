package com.klst.eorder.api;

import java.util.List;

/**
 * BG-24 ADDITIONAL SUPPORTING DOCUMENTS
 * <p>
 * A group of business terms providing information about additional supporting documents 
 * substantiating the claims made in the Order.
 * <p>
 * The additional supporting documents can be used for both referencing a document number which is expected to be known by the receiver, 
 * an external document (referenced by a URL) or as an embedded document (such as a time report in pdf).
 * The option to link to an external document will be needed, for example in the case of large attachments and/or when sensitive information, 
 * e.g. person-related services, has to be separated from the Order itself.
 * <p>
 * Cardinality: 	0..n
 * <br>EN16931-ID: 	BG-24
 * <br>Rule ID: 	
 * <br>Order-X-No: 	549
 */
public interface BG24_AdditionalSupportingDocs extends SupportingDocumentFactory {

	public void addSupportigDocument(SupportingDocument supportigDocument);
	
	/**
	 * use this to add an attached document
	 * 
	 * @param docRefId    - 550 document reference
	 * @param description - 553 document description
	 * @param content     - 554 Attached document content
	 * @param mimeCode    - 555
	 * @param filename    - 556
	 */
	default void addSupportigDocument(String docRefId, String description, byte[] content, String mimeCode, String filename) {
		addSupportigDocument(createSupportigDocument(docRefId, description, content, mimeCode, filename));
	}
	
	/**
	 * use this to add an External document
	 * 
	 * @param docRefId    - 550 document reference
	 * @param description - 553 document description
	 * @param uri         - 551 External document location URI
	 */
	default void addSupportigDocument(String docRefId, String description, String uri) {
		addSupportigDocument(createSupportigDocument(docRefId, description, uri));
	}
	
	public List<SupportingDocument> getAdditionalSupportingDocuments();

}
