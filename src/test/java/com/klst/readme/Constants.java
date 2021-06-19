package com.klst.readme;

import java.sql.Timestamp;

import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.impl.UnitPriceAmount;

import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

public class Constants {

	static final String TESTDIR = "src/test/resources/";
	static final String XML_SUFFIX = ".xml";

	static final String _HMS = " 00:00:00"; // Timestamp suffix to get yyyy-[m]m-[d]d hh:mm:ss[.f...]

	// countries + currency
	static final String CH  = ISOTwoletterCountryCodeContentType.CH.toString();
	static final String DE  = ISOTwoletterCountryCodeContentType.DE.toString();
	static final String FR  = ISOTwoletterCountryCodeContentType.FR.toString();
	static final String PL  = ISOTwoletterCountryCodeContentType.PL.toString();
	static final String CHF = "CHF";
	static final String EUR = "EUR";
	
	// UNTDID 7065 Package type description code verweist auf UN/ECE Recommendation 21, Annex V
	static final String woodenCase = "7B"; 
	static final String C62 = "C62";
	static final String MTK = "MTK"; // m²
	static final String MTR = "MTR"; // m Meter
	static final String PRD = "PRD"; // UNTDID 4451: Product information
	// UNTDID 4343 Response type code: Acknowledge - with detail and change; Acknowledge complete including changes.
	static final String  AC =  "AC"; // to request an Order Response
	// UNTDID 4053 + INCOTERMS DELIVERY CODE:
	static final String FCA = "FCA"; // Free Carrier (insert named place of delivery)
	// UNTDID 4055 DELIVERY MODE:
	static final String COLLECTED_BY_CUSTOMER = "4";
	static final String DELIVERED_BY_SUPPLIER = "7";
	
	// UNTDID 7143 Item type identification code
	static final String EAN_UNTDID_7143 = "EN"; // International Article Numbering Association (EAN)

	// Coding Systems aka ICD Schemas : 
	// System Information et Repertoire des Entreprise et des Etablissements: SIRENE
	static final String SIRENE 		= "0002"; // ICD Schema for SIRENE
	static final String EAN_LOCO 	= "0088"; // ICD Schema for EAN Location Code
	// Global Trade Item Number (GTIN), Alt. Names: EAN article number, UPC Code
	static final String GTIN 		= "0160";
	// Bis 2009 hieß die GTIN noch europäische Artikelnummer EAN. 
	// EAN und GTIN meinen also das Gleiche, es gibt aber kein ICD Schema für EAN
	static final String EAN 		= "ean"; // used in OpenTrans
	static final String GLN 		= "gln"; // Global Location Number used in OpenTrans
	static final String ILN 		= "iln"; // International Location Number (alias to GLN) used in OpenTrans

	class ExpectedBP {
		Identifier id;        // BP ID aka GlobalID
		Identifier companyId; // Company ID SpecifiedLegalOrganization
		String name;          // Registration Name
		String bpn;           // Business Name
		String vatId;         // VAT Registration ID starts with countryId
		Identifier uriId;     // Uri Universal Communication
		PostalAddress pa;
		String al1;           // Address Line 1
		String street;        // used in OT interface PostalAddressExt
		ContactInfo ci;
	}
	class ExpectedLine {
		String id;
		OrderNote note = null;
		IQuantity qty;
		UnitPriceAmount upa;
		IAmount lna;         // line net amount    / PRICE_LINE_AMOUNT
		IQuantity upq;       // UnitPriceQuantity  / bmecat:PRICE_QUANTITY
		String name;         // ItemName           / DESCRIPTION_SHORT
		String desc = null;  // description        / DESCRIPTION_LONG
		Identifier sid;      // StandardIdentifier / INTERNATIONAL_PID
		String sai;          // SellerAssignedID   / SUPPLIER_PID
		String bai;          // BuyerAssignedID    / BUYER_PID
		boolean pdi;         // PartialDeliveryIndicator
		Timestamp delivery  = null; // xxx / DELIVERY_DATE
		TaxCategoryCode tcc = null;
		Reference sddr = null; // SupportingDocument.DocumentReference / CUSTOMER_ORDER_REFERENCE
		Reference sdlr = null; // SupportingDocument.LineReference
		Timestamp sdts = null; // SupportingDocument.Date
	}

}
