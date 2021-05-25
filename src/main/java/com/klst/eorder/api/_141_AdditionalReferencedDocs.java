package com.klst.eorder.api;

import java.sql.Timestamp;
import java.util.List;

import com.klst.edoc.api.Reference;

/**
 * ADDITIONAL REFERENCED DOCUMENT
 * <p>
 * An additional document referenced in line trade agreement.
 * <p>
 * Cardinality: 	0..n
 * <br>Order-X-No: 	141	
 * 
 */
public interface _141_AdditionalReferencedDocs extends SupportingDocumentFactory {

	public void addReferencedDocument(SupportingDocument supportigDocument);
	
	/**
	 * use this to add an attached document
	 * 
	 * @param docRefId    - 142 document reference ID
	 * @param lineId      - 144 document Line ID
	 * @param description - 146 document description
	 * @param ts          - 151 Timestamp for the issuance
	 * @param content     - 147 Attached document content
	 * @param mimeCode    - 148
	 * @param filename    - 149
	 */
	default void addReferencedDocument(String docRefId, Reference lineId, String description, Timestamp ts
			, byte[] content, String mimeCode, String filename) {
		addReferencedDocument(createSupportigDocument(docRefId, lineId, description, ts, content, mimeCode, filename));
	}
	
	/**
	 * use this to add an External document
	 * 
	 * @param docRefId    - 142 document reference ID
	 * @param lineId      - 144 document Line ID
	 * @param description - 146 document description
	 * @param ts          - 151 Timestamp for the issuance
	 * @param uri         - 143 External document location Uri
	 */
	default void addReferencedDocument(String docRefId, Reference lineId, String description, Timestamp ts, String uri) {
		addReferencedDocument(createSupportigDocument(docRefId, lineId, description, ts, uri));
	}
	
	public List<SupportingDocument> getReferencedDocuments();

}
