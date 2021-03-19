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
 * <br>Request ID: 	R36 / multiple attached or referenced documents at document level
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG24_AdditionalSupportingDocs extends SupportingDocumentFactory {

	// BG-24 + 0..n ADDITIONAL SUPPORTING DOCUMENTS
	// BG-24.BT-122 ++ 1..1 Supporting document reference
	// BG-24.BT-122-0  1..1 TypeCode
	// BG-24.BT-123 ++ 0..1 Supporting document description
	// BG-24.BT-124 ++ 0..1 External document location
	// BG-24.BT-125 ++ 0..1 Attached document
	
	public void addSupportigDocument(SupportingDocument supportigDocument);
	
	/**
	 * use this to add an attached document
	 * 
	 * @param docRefId    - BG-24.BT-122 Supporting document reference
	 * @param description - BG-24.BT-123 Supporting document description
	 * @param content     - BG-24.BT-125 Attached document content
	 * @param mimeCode
	 * @param filename
	 */
	default void addSupportigDocument(String docRefId, String description, byte[] content, String mimeCode, String filename) {
		addSupportigDocument(createSupportigDocument(docRefId, description, content, mimeCode, filename));
	}
	
	/**
	 * use this to add an External document
	 * 
	 * @param docRefId    - BG-24.BT-122 Supporting document reference
	 * @param description - BG-24.BT-123 Supporting document description
	 * @param uri locationUri
	 */
	default void addSupportigDocument(String docRefId, String description, String uri) {
		addSupportigDocument(createSupportigDocument(docRefId, description, uri));
	}
	
	public List<SupportingDocument> getAdditionalSupportingDocuments();

}
