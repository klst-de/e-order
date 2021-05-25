package com.klst.eorder.api;

import java.util.List;

/**
 * ADDITIONAL REFERENCED PRODUCT DOCUMENT
 * <p>
 * An additional document referenced in line trade agreement.
 * <p>
 * Cardinality: 	0..n
 * <br>Order-X-No: 	79	
 * 
 */
public interface _79_AdditionalReferencedProductDocs extends SupportingDocumentFactory {

	/**
	 * @param code        - 82 Type Code, to be chosen from the entries in UNTDID 1001
	 * @param supportigDocument
	 */
	public void addReferencedProductDocument(String code, SupportingDocument supportigDocument);
	
	/**
	 * use this to add an attached document
	 * 
	 * @param docRefId    - 80 document reference
	 * @param code        - 82 Type Code, to be chosen from the entries in UNTDID 1001
	 * @param description - 83 document description
	 * @param content     - 84 Attached document content
	 * @param mimeCode    - 85
	 * @param filename    - 86
	 */
	default void addReferencedProductDocument(String docRefId, String code, String description, byte[] content, String mimeCode, String filename) {
		addReferencedProductDocument(code, createSupportigDocument(docRefId, description, content, mimeCode, filename));
	}
	
	/**
	 * use this to add an External document
	 * 
	 * @param docRefId    - 80 document reference
	 * @param code        - 82 Type Code, to be chosen from the entries in UNTDID 1001
	 * @param description - 83 document description
	 * @param uri         - 81 location URI
	 */
	default void addReferencedProductDocument(String docRefId, String code, String description, String uri) {
		addReferencedProductDocument(code, createSupportigDocument(docRefId, description, uri));
	}
	
	public List<SupportingDocument> getReferencedProductDocuments();

}
