package com.klst.readme;

import java.sql.Timestamp;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.impl.UnitPriceAmount;

public class Constants {

	static final String TESTDIR = "src/test/resources/";

	static final String _HMS = " 00:00:00"; // Timestamp suffix to get yyyy-[m]m-[d]d hh:mm:ss[.f...]

	static final String EUR = "EUR";
	static final String C62 = "C62";
	static final String MTK = "MTK"; // m²
	static final String PRD = "PRD"; // UNTDID 4451: Product information

	// Coding Systems aka ICD Schemas : 
	// System Information et Repertoire des Entreprise et des Etablissements: SIRENE
	static final String SIRENE 		= "0002"; // ICD Schema for SIRENE
	static final String EAN_LOCO 	= "0088"; // ICD Schema for EAN Location Code
	// Global Trade Item Number (GTIN), Alt. Names: EAN article number, UPC Code
	static final String GTIN 		= "0160";
	// Bis 2009 hieß die GTIN noch EAN (kurz für europäische Artikelnummer). 
	// EAN und GTIN meinen also das Gleiche.
	static final String EAN 		= "ean"; // used in OpenTrans

	class ExpectedLine {
		String id;
		OrderNote note = null;
		IQuantity qty;
		IAmount lna;         // line net amount    / PRICE_LINE_AMOUNT
		UnitPriceAmount upa;
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
