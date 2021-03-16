package com.klst.eorder.api;

import java.util.List;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;

/**
 * ORDER LINE
 * <p>
 * A group of business terms providing information on individual order lines.
 * <p>
 * Cardinality: 1..n (mandatory)
 * <br>EN16931-ID: 	BG-25
 * <br>Rule ID: 	BR-16 
 * <br>Request ID: 	R17 R23 R27
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> for EN_16931_1_2017 rule and request IDs
 */
public interface OrderLine extends OrderLineFactory, OrderNoteFactory {

	/**
	 * Line identifier
	 * <p>
	 * A unique identifier for the individual line.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-126
	 * <br>Rule ID: 	BR-21
	 * <br>Request ID: 	R44
	 * 
	 * @param id Identifier
	 */
//	public void setId(String id); // use factory
	public String getId();

	/**
	 * line note
	 * <p>
	 * A textual note that gives unstructured information that is relevant to the line.
	 * <p>
	 * Cardinality: 0..1 (optional), extend to 0..n
	 * <br>EN16931-ID: 	BT-127
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R28
	 * 
	 * @param text Text
	 */
//	public void setNote(String text);
//	public String getNote();
	// in Order-X wird 0..n vorgeschlagen, auch für CII
	public List<OrderNote> getNotes();
	// factory methods aus OrderNoteFactory
	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderNote createNote(String subjectCode, String content);
	default OrderNote createNote(String content) {
		return createNote((String)null, content);
	}

	// setter
	public void addNote(OrderNote note);
	default void addNote(String subjectCode, String content) {
		addNote(createNote(subjectCode, content));
	}
	default void addNote(String content) {
		addNote((String)null, content);
	}
	


	/*
	 * A group of business terms providing information about additional supporting documents 
	 * substantiating the claims made in the order.
	 * <p>
	 * The additional supporting documents can be used for both referencing a document number which is expected 
	 * to be known by the receiver, an external document (referenced by a URL) 
	 * or as an embedded document (such as a time report in pdf). 
	 * The option to link to an external document will be needed, 
	 * for example in the case of large attachments and/or when sensitive information, 
	 * e.g. person-related services, has to be separated from the order itself.
	 * 
	 * A Line MUST NOT HAVE more than 1 Object Identifier BT-128 ===> also doch nur 0..1
	 * 
	 * A Object Identifier (BT-128) MUST have an ID



	
	 * Eine vom Verkäufer angegebene Kennung für einen Gegenstand, auf dem die Rechnungsposition basiert
	 * EN16931-ID: BT-128-0 1..1 TypeCode = "130" Rechnungsdatenblatt, UNTDID 1001 Untermenge
	 * EN16931-ID: BT-128-1 0..1 UNTDID 1153
//	0 .. 1 IssuerAssignedID Objektkennung auf Ebene der Rechnungsposition, Wert             	BT-128
//	1 .. 1 TypeCode Typ der Kennung für einen Gegenstand, auf dem die Rechnungsposition basiert BT-128-0
//	0 .. 1 ReferenceTypeCode Kennung des Schemas                                                BT-128-1	
	 */
	/*
	 * line object identifier
	 * <p>
	 * An identifier for an object on which the line is based, given by the Seller.
	 * It may be a subscription number, telephone number, meter point etc., as applicable.  
	 * <p>
	 * Cardinality:     0..1 (optional)
	 * <br>EN16931-ID: 	BG.25.BT-128, BT-128-0, BT-128-1
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R33
	 * 
	 * @param Identifier
	 * @param typecode for an Object Identifier MUST be present and equal to 130 (UNTDID 1001)
	 * @param schemeCode (optional) if it may be not clear for the receiver what scheme is used for the identifier, 
	 * a conditional scheme identifier should be used that shall be chosen from the UNTDID 1153 code list entries.
	 */
	public void setLineObjectID(String id, String typecode, String schemeCode);
//	public void setLineObjectID(String id);
//	public void setLineObjectID(String id, String schemeID);
//	public void setLineObjectIdentifier(GlobalIdentifier id);
	public Identifier getLineObjectIdentifier(); // Identifier.Content == id , .SchemeIdentifier == schemeCode

	/**
	 * LINE TRADE DELIVERY Quantity

	 * Quantity and UoM of items (goods or services) to be charged in the Order line.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-129 (decimal quantity) + BT-130 (unitCode) 
	 * <br>Rule ID: 	BR-22
	 * <br>Request ID: 	R14, R39, R56
	 * 
	 * @param Quantity
	 */
//	public void setQuantity(Quantity quantity); // use factory
	public IQuantity getQuantity();

	/**
	 * line net amount 
	 * <p>
	 * The total amount of the line. The amount is “net” without VAT, i.e.
	 * inclusive of line level allowances and charges as well as other relevant taxes.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-131
	 * <br>Rule ID: 	BR-24
	 * <br>Request ID: 	R39, R56, R14
	 * 
	 * @param Amount
	 */
//	public void setLineTotalAmount(Amount amount); // use factory
	public IAmount getLineTotalAmount();

	/**
	 * The unique identifier of a line in the Buyer Order referenced document (initial order document ID)
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BT-132
	 * <br>Rule ID: 	 
	 * <br>Request ID: 	R6
	 * 
	 * @param reference id
	 */
	// nicht in CIO, nur in CIOR/CIOC
	public void setOrderLineID(String id);
	public String getOrderLineID();
	
	/**
	 * line Buyer accounting reference
	 * <p>
	 * A textual value that specifies where to book the relevant data into the Buyer's financial accounts.
	 * If required, this reference shall be provided by the Buyer to the Seller prior to the issuing of the Order.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BT-133
	 * <br>Rule ID: 	 
	 * <br>Request ID: 	R3
	 * 
	 * @param Text
	 */
	// ram:ReceivableSpecifiedTradeAccountingAccount existiert nicht in BASIC
	public void setBuyerAccountingReference(String text);
	public String getBuyerAccountingReference();

	// BG-26 0..1 INVOICE LINE PERIOD with 
	//       BT-134 +++ 0..1 Invoice line period start date
	//       BT-135 +++ 0..1 Invoice line period end date
	
	/**
	 * add an allowance or charge
	 * <p>
	 * BG-27 0..n INVOICE LINE ALLOWANCES / ABSCHLÄGE
	 * <br>
	 * BG-28 0..n INVOICE LINE CHARGES / ZUSCHLÄGE
	 * 
	 * @param allowanceOrCharge
	 */
//	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge);
//	public List<AllowancesAndCharges> getAllowancesAndCharges();

	
	// BG-29 ++ 1..1 PRICE DETAILS // TODO move this Block to BG29_PriceDetails, order doc Zeile 170
	/**
	 * Item net price (mandatory part in PRICE DETAILS), exclusive of VAT, after subtracting item price discount.
	 * <p>
	 * The unit net price has to be equal with the Item gross price less the Item price discount.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <br>EN16931-ID: 	BG-29.BT-146 
	 * <br>Rule ID: 	BR-27
	 * <br>Request ID: 	R14
	 * 
	 * @return UnitPriceAmount
	 */
	public IAmount getUnitPriceAmount();
//
//	// 1..1 UnitPriceAmount BT-146 , UnitPriceQuantity BT-149-0 + BT-150-0 optional
//	public void setUnitPriceAmountAndQuantity(UnitPriceAmount unitPriceAmount, Quantity quantity);

	/**
	 * Item price discount
	 * <p>
	 * The total discount subtracted from the Item gross price to calculate the Item net price. 
	 * Only applies if the discount is provided per unit and if it is not included in the Item gross price.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-147
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R14
	 * 
	 * @return UnitPriceAmount
	 */
	// ram:SpecifiedLineTradeAgreement/
//	ram:GrossPriceProductTradePrice/ram:AppliedTradeAllowanceCharge/ram:ActualAmount existiert nicht
//	public UnitPriceAmount getPriceDiscount();

	/**
	 * Item gross price
	 * <p>
	 * The unit price, exclusive of VAT, before subtracting Item price discount.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-148
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R14
	 * 
	 * @return UnitPriceAmount
	 */
	// ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:ChargeAmount existiert nicht
//	public UnitPriceAmount getGrossPrice();
	
	/**
	 * Sets price discount and gross price
	 * 
	 * @param priceDiscount, BT-147
	 * @param grossPrice, BT-148
	 */
//	public void setUnitPriceAllowance(UnitPriceAmount priceDiscount, UnitPriceAmount grossPrice);
	
	// optional UnitPriceQuantity : BT-149-0 QuantityUnit 0..1 + BT-150-0 Quantity required
	/**
	 * Item price unit (BT-150) and base quantity (BT-149)
	 * <p>
	 * The number of item units to which the price applies.
	 * <br>Item price base quantity unit of measure code The unit of measure that applies to the Item price base quantity.
	 * The Item price base quantity unit of measure shall be the same as the Invoiced quantity unit of measure (BT-130).
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-29.BT-150 + BG-29.BT-149
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R14
	 * 
	 * @return UnitPriceQuantity
	 * @see #getQuantity
	 */
	public IQuantity getUnitPriceQuantity();
//	@Deprecated   // TODO remove in 3.X
//	default Quantity getBaseQuantity() {
//		return getUnitPriceQuantity();
//	}
	public void setUnitPriceQuantity(IQuantity quantity);
	
	// BG-30 ++ 1..1 LINE VAT INFORMATION
	/**
	 * Invoiced item VAT category code
	 * <p>
	 * The VAT category code for the invoiced item.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <br>EN16931-ID: 	BG-30, BT-151, BT-151-0
	 * <br>Rule ID: 	BR-CO-4
	 * <br>Request ID: 	R37, R45, R48, R55
	 * 
	 * @param Code
	 */
////	public void setTaxCategory(TaxCategoryCode codeEnum); // use ctor 
//	public TaxCategoryCode getTaxCategory(); 
//	// + 0..1 EN16931-ID: BT-152 Invoiced item VAT rate
////	public void setTaxCategoryAndRate(TaxCategoryCode codeEnum, BigDecimal percent); // use ctor
//	public BigDecimal getTaxRate(); 

	// BG-31 PRODUCT 0..1 : A group of business terms providing information about the goods and services ordered.

	/**
	 * Item name (optional BT in BG-31 PRODUCT)
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-153 
	 * <br>Rule ID: 	BR-25
	 * <br>Request ID: 	R20, R56
	 * 
	 * @param text Text
	 */
//	public void setItemName(String text); // use factory
	public String getItemName();

	/**
	 * Item description (optional BT in BG-31 PRODUCT)
 	 * <p>
	 * The Item description allows for describing the item and its features in more detail than the Item name.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-154
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R20, R56
	 * 
	 * @param Text
	 */
	public void setDescription(String text);
	public String getDescription();

	/**
	 * Item Seller's identifier (optional BT in BG-31 PRODUCT)
 	 * <p>
	 * An identifier, assigned by the Seller, for the item.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-155
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R21, R56
	 * 
	 * @param Identifier
	 */
	public void setSellerAssignedID(String id);
	public String getSellerAssignedID();

	/**
	 * Item Buyer's identifier (optional BT in BG-31 PRODUCT)
 	 * <p>
	 * An identifier, assigned by the Buyer, for the item.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-156
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R21, R56, R22
	 * 
	 * @param Identifier
	 */
	public void setBuyerAssignedID(String id);
	public String getBuyerAssignedID();
	
	/*
	 * GlobalID Kennung eines Artikels nach registriertem Schema
	 * CII:
	 * BG-31    1 .. 1   SpecifiedTradeProduct
	 * BT-157   0 .. 1   GlobalID
	 * BT-157-1          required schemeID
	 * Codeliste: ISO 6523 :
	 * 0021 : SWIFT 
	 * 0088 : EAN 
	 * 0060 : DUNS
	 * 0160 : GTIN , Global Trade Item Number https://www.gs1.org/standards/gs1-application-standard-usage-isoiec-6523-international-code-designator-icd-0209/current-standard#2-Purpose+2-1-Principles
	 * 0177 : ODETTE automotive industry
	 */
	/**
	 * Item standard (aka global) identifier (optional BT in BG-31 PRODUCT)
 	 * <p>
	 * An item identifier based on a registered scheme.
	 * <p>
	 * Cardinality: 	0..n (optional)
	 * <br>EN16931-ID: 	BG-31.BT-157 BG-31.BT-157-1
	 * <br>Rule ID: 	CSCMUS GS1 : an Order must contain a GlobalID for the Product on line level
	 * <br>Request ID: 	R23, R56
	 * 
	 * @param globalID
	 * @param schemeID, The identification scheme shall be identified from the entries of the list published by the ISO/IEC 6523 maintenance agency.
	 */
	public Identifier createStandardIdentifier(String globalID, String schemeID);
	public void addStandardIdentifier(Identifier id);
	default void addStandardIdentifier(String globalID, String schemeID) {
		addStandardIdentifier(createStandardIdentifier(globalID, schemeID));
	}
	public List<Identifier> getStandardIdentifier();

	/**
	 * Item classification identifier (optional part in 1..1 BG-31 ITEM INFORMATION)
 	 * <p>
	 * A code for classifying the item by its type or nature.
	 * Classification codes are used to allow grouping of similar items for a various purposes 
	 * e.g. public procurement (CPV), e-Commerce (UNSPSC) etc.
	 * <p>
	 * Cardinality: 	0..n (optional)
	 * <br>EN16931-ID: 	BG-31.BT-158
	 * <br>Rule ID: 	BR-64
	 * <br>Request ID: 	R24
	 * 
	 * @param classCode,     BT-158   1..1
	 * @param listID,        BT-158-1 1..1 The identification scheme shall be chosen from the entries in UNTDID 7143
	 * @param listVersionID, BT-158-2 0..1 Scheme version identifier - The version of the identification scheme.
	 * @param idText         optional Product Classification Class Name 0..1
	 */
	public IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID, String idText);
	public void addClassificationIdentifier(IdentifierExt id);
	default void addClassificationIdentifier(String code, String listID, String listVersionID, String name) {
		addClassificationIdentifier(createClassificationIdentifier(code, listID, listVersionID, name));
	}
	public List<IdentifierExt> getClassifications();

	/**
	 * Item country of origin (optional part in 1..1 BG-31 ITEM INFORMATION)
 	 * <p>
	 * The code identifying the country from which the item originates.
	 * The lists of valid countries are registered with the EN ISO 3166-1 Maintenance agency, 
	 * “Codes for the representation of names of countries and their subdivisions”.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-159
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R29 
	 * 
	 * @param code
	 */
	public void setCountryOfOrigin(String code);
	public String getCountryOfOrigin();

	public static final boolean NO = false;
	public static final boolean YES = true;
	/**
	 * set Partial Delivery Allowed Indicator Value
	 * <p>
	 * The indication, at line level, of whether or not this trade delivery can be partially delivered.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * 
	 * @param indicator
	 */
	public void setPartialDeliveryIndicator(boolean indicator);
	public boolean isPartialDeliveryAllowed();

}
