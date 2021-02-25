package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.BusinessPartyFactory;
import com.klst.edoc.api.IContact;
import com.klst.edoc.api.IContactFactory;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.PostalAddressFactory;
import com.klst.edoc.api.Reference;
import com.klst.untdid.codelist.DateTimeFormats;
import com.klst.untdid.codelist.DocumentNameCode;

public interface CoreOrder extends CoreOrderFactory, BG1_OrderNote, BG2_ProcessControl,
	PostalAddressFactory, IContactFactory, BusinessPartyFactory {

	/**
	 * Invoice number   - A unique identification of the Invoice.
	 * <p>
	 * The sequential number required in Article 226(2) of the directive 2006/112/EC [2],
	 * to uniquely identify the Invoice within the business context, time-frame, operating systems and records of the Seller. 
	 * It may be based on one or more series of numbers, which may include alphanumeric characters. 
	 * No identification scheme is to be used.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-1
	 * <br>Rule ID: 	BR-2
	 * <br>Request ID: 	R56
	 * 
	 * @param id Identifier
	 */
	public void setId(String id);
	public String getId();

	/**
	 * Invoice issue date
	 * <p>
	 * The date when the Invoice was issued
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-2
	 * <br>Rule ID: 	BR-3
	 * <br>Request ID: 	R56
	 * 
	 * @param timestamp
	 */
	public void setIssueDate(Timestamp timestamp);
	public Timestamp getIssueDateAsTimestamp();
	
	/**
	 * Invoice issue date
	 * 
	 * @param ymd - String in UNTDID 2379 Format "102" : CCYYMMDD = "yyyyMMdd" or "yyyy-MM-dd" 
	 * @see #setIssueDate(Timestamp)
	 */
	default void setIssueDate(String ymd) {
		if(ymd!=null) setIssueDate(DateTimeFormats.ymdToTs(ymd));
	}

	/**
	 * The Document TypeCode (BT-3) MUST be:
- 220 for an ORDER
- 230 for an ORDER CHANGE
- 231 for an ORDER RESPONSE
	 * 
	 * 
	 * Invoice type code - A code specifying the functional type of the Invoice.
	 * <p>
	 * Commercial invoices and credit notes are defined according the entries in UNTDID 1001.
	 * Other entries of UNTDID 1001 with specific invoices or credit notes may be used if applicable.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-3
	 * <br>Rule ID: 	BR-4, BR-DE-17 :  
	 * Mit dem Element "Invoice type code" sollen ausschließlich folgende Codes aus der Codeliste UNTDID 1001 übermittelt werden: 
	 *     326 (Partial invoice), 
	 *     380 (Commercial invoice), 
	 *     384 (Corrected invoice), 
	 *     389 (Self-billed invoice) und 
	 *     381 (Credit note),
	 *     875 (Partial construction invoice), 
	 *     876 (Partial final construction invoice), 
	 *     877 (Final construction invoice)
	 * <br>Request ID: 	R44
	 * 
	 * @param code
	 */
//	void setTypeCode(DocumentNameCode code); // in factory
	public DocumentNameCode getDocumentCode();

	/**
	 * Invoice currency code
	 * <p>
	 * The currency in which all Invoice amounts are given, 
	 * except for the Total VAT amount in accounting currency.
	 * Only one currency shall be used in the Invoice, 
	 * except for the Invoice total VAT amount in accounting currency (BT-111) 
	 * in accordance with article 230 of Directive 2006/112/EC on VAT.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-5
	 * <br>Rule ID: 	BR-5
	 * <br>Request ID: 	R54, R47
	 * 
	 * @param isoCode
	 * <p>
	 * The lists of valid currencies are registered with the ISO 4217 Maintenance Agency “Codes for the representation of currencies and funds”.
	 */
	public void setDocumentCurrency(String isoCurrencyCode);
	public String getDocumentCurrency();
	
	// TODO BT-6 VAT accounting currency code
	//      BT-7 BT-7-0
	//      BT-8
	//      BT-9
	// BT-9 (Payment due date) & BT-20
	
	/**
	 * The identifier is defined by the Buyer (e.g. contact ID, department, office id, project code), 
	 * but provided by the Seller in the order.
	 * 
	 * @param reference
	 */
	// BT-10
	public void setBuyerReference(String reference);
	public String getBuyerReferenceValue();
	
	// TODO:
	// BT-11, (Projektname BT-11-0)
	// ...
	
	/**
	 * Buyer accounting reference
	 * <p>
	 * A textual value that specifies where to book the relevant data into the Buyer's financial accounts.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>EN16931-ID: 	BT-19
	 * <br>Rule ID: 	 
	 * <br>Request ID: 	R2, R4
	 * 
	 * @param textReference
	 */
	// BT-19 + 0..1 Buyer accounting reference
	public void setBuyerAccountingReference(Reference textReference);
	public Reference getBuyerAccountingReference();

	// BG-4 + 1..1 SELLER @see BG4_Seller
	public void setSeller(String name, PostalAddress address, IContact contact, String companyId, String companyLegalForm);
	public void setSeller(BusinessParty party);
	public BusinessParty getSeller();

	// BG-7 + 1..1 BUYER @see BG7_Buyer
	public void setBuyer(String name, PostalAddress address, IContact contact);
	public void setBuyer(BusinessParty party);
	public BusinessParty getBuyer();
	
	public void setShipToParty(String name, PostalAddress address, IContact contact);
	public void setShipToParty(BusinessParty party);
	public BusinessParty getShipToParty();

	public void setShipFromParty(String name, PostalAddress address, IContact contact);
	public void setShipFromParty(BusinessParty party);
	public BusinessParty getShipFromParty();

}
