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

	// BG-24 ist auf Dokumentenebene
	// in OrderLine gibt es:
	// 79: BG-24 0..n ADDITIONAL SUPPORTING DOCUMENTS
	// 80: BG-24.BT-122    1..1 Supporting document reference
	// 82: BG-24.BT-122-0  1..1 TypeCode    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   130
	// 83: BG-24.BT-123    0..1 Supporting document description
	// 81: BG-24.BT-124    0..1 External document location
	// 84: BG-24.BT-125    0..1 Attached document
//		85  SCT_LINE	COMFORT	  Additional Referenced Product Document - Attached document Mime code
//		86  SCT_LINE	COMFORT	  Additional Referenced Product Document - Attached document Filename
	//
	// und 141ff ADDITIONAL REFERENCED DOCUMENT (in SpecifiedLineTradeAgreement) mit LineId
//	142 SCT_LINE_TA COMFORT	  Additional Referenced Document - ID
//	143 SCT_LINE_TA COMFORT	  Additional Referenced Document - External document location
//	144 SCT_LINE_TA COMFORT	  Additional Referenced Document + Line ID
	
//	145 SCT_LINE_TA COMFORT	  Additional Referenced Document - Type Code
//	To be chosen from the entries in UNTDID 1001
//	Use for "ADDITIONAL SUPPORTING DOCUMENTS" with TypeCode Value = 916,       >>>>>>    916
//	or for "OBJECT IDENTIFIER with Type Code Value = 130, "
//	or for "TENDER OR LOT REFERENCE" with Type Code Value = 50

//	146 SCT_LINE_TA COMFORT	  Additional Referenced Document - Description
//	147 SCT_LINE_TA COMFORT	  Additional Referenced Document - Attached document
//	148 SCT_LINE_TA COMFORT	  Additional Referenced Document - Attached document Mime code
//	149 SCT_LINE_TA COMFORT	  Additional Referenced Document- Attached document Filename
//	150 SCT_LINE_TA COMFORT	  Additional Referenced Document - Reference Type Code
//	151 SCT_LINE_TA EXTENDED  (Additional Referenced Document +  Date)
//	152 SCT_LINE_TA EXTENDED  Additional Referenced Document -  Date
//	153 SCT_LINE_TA EXTENDED  Date format
	
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
