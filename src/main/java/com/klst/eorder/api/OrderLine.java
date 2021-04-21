package com.klst.eorder.api;

import java.math.BigDecimal;
import java.util.List;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.impl.TradeProductInstance;

/**
 * ORDER LINE
 * <p>
 * A group of business terms providing information on individual order lines.
 * <p>
 * Cardinality: 1..n (mandatory)
 * <br>EN16931-ID: 	BG-25
 * <br>Rule ID: 	BR-16 
 * <br>Order-X-No: 	33
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> for EN_16931_1_2017 rule and request IDs
 */
public interface OrderLine extends OrderLineFactory, OrderNoteFactory, 
	BG26_LineDeliveryPeriod, 
	BG27_LineLevelAllowences, 
	BG28_LineLevelCharges,
	BG29_PriceDetails, 
	BG30_LineVATInformation, 
	BG31_ItemInformation, 
	BG32_ItemAttributes {

	/**
	 * Line identifier
	 * <p>
	 * A unique identifier for the individual line.
	 * <p>
	 * Cardinality: 1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-126
	 * <br>Rule ID: 	BR-21
	 * <br>Order-X-No: 	35
	 * 
	 * @return id Identifier
	 */
//	public void setId(String id); // use factory
	public String getId();

	/**
	 * Line status code
	 * <p>
	 * To be chosen from the entries in UNTDID 1229, Action code, in particular:
1 : Order  line ADDED
3 : Order  line CHANGED
5 : Order line ACCEPTED WITHOUT AMENDMENT
6 : Order line ACCEPTED WITH AMENDMENT
7 : Order  line NOT ACCEPTED
42 : Order  line ALREADY DELIVERED

	 * TODO nur OOR OOC
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>Order-X-No: 	36
	 * 
	 * @return status code
	 */
	public void setStatus(String status);
	public String getStatus();
	
	/**
	 * line note
	 * <p>
	 * A textual note that gives unstructured information that is relevant to the line.
	 * <p>
	 * Cardinality: 0..1 (optional), extended: 0..n
	 * <br>EN16931-ID: 	BG-25.BT-127
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R28
	 * <br>Order-X-No: 	37
	 * 
	 * @return List of Notes
	 */
	// in Order-X V.0 wird 0..n vorgeschlagen, auch für CII
	public List<OrderNote> getNotes();
	
	// factory method aus OrderNoteFactory
//	public OrderNote createNote(String subjectCode, String content);
	default OrderNote createNote(String content) {
		return createNote((String)null, content);
	}

	/**
	 * Add Note to Line
	 * 
	 * @param note
	 */
	public void addNote(OrderNote note);
	
	/**
	 * Add Note to Line
	 * 
	 * @param subjectCode
	 * @param content
	 * 
	 * @see OrderNoteFactory#createNote(String, String)
	 * @see #addNote
	 */
//	38  SCT_LINE	EXTENDED  Line Note Content Code                               TODO
//	39  SCT_LINE	BASIC	  Line Note Content
//	40  SCT_LINE	BASIC	  Line Note Subject Code
	default void addNote(String subjectCode, String content) {
		addNote(createNote(subjectCode, content));
	}
	/**
	 * Add Note to Line
	 * 
	 * @param content
	 * 
	 * @see #addNote(String, String)
	 */
	default void addNote(String content) {
		addNote((String)null, content);
	}
	
//  41: BG-31 SpecifiedTradeProduct ----------------------------------------------
	
	/**
	 * Item (Trade Product) ID
 	 * <p>
	 * A unique identifier for this trade product.
	 * <p>
	 * Cardinality: 	0..n (optional)
	 * <br>Order-X-No: 	42
	 * 
	 * @param id
	 */
	public void setProductID(String id);
	public String getProductID();

	/*
	 * GlobalID Kennung eines Artikels nach registriertem Schema
	 * CII:
	 * BG-31    1 .. 1   SpecifiedTradeProduct
	 * BT-157   0 .. 1   GlobalID
	 * BT-157-1          required schemeID
	 * Codeliste: ISO 6523 :
	 * 0002 : SIRENE (F) 9 characters ("SIREN"); 14=9+5 ("SIRET")
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
	 * <br>Order-X-No: 	43+44
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
	 * Item Seller's identifier (optional BT in BG-31 PRODUCT)
 	 * <p>
	 * An identifier, assigned by the Seller, for the item.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-155
	 * <br>Rule ID: 	
	 * <br>Order-X-No: 	45
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
	 * <br>Order-X-No: 	46
	 * 
	 * @param Identifier
	 */
	public void setBuyerAssignedID(String id);
	public String getBuyerAssignedID();
	
	// 47  SCT_LINE	EXTENDED  Item (Trade Product) Industry Assigned ID
//	Gerhard : Yes, in UBL they use a StandardItemID which in fact means the “basic” not alternative ID. 
//	And not what I thought an Industry “Standard” ID, therefore we could consider it not to use
//	public void setIndustryID(String id);
//	public String getIndustryID();

	// 48  SCT_LINE	EXTENDED  Item (Trade Product) Model Name ID
//	public void setModelID(String id);
//	public String getModelID();

	/**
	 * Item name (optional BT in BG-31 PRODUCT)
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-153 
	 * <br>Rule ID: 	BR-25
	 * <br>Order-X-No: 	49
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
	 * <br>Order-X-No: 	50
	 * 
	 * @param Text
	 */
	public void setDescription(String text);
	public String getDescription();

	// TODO	51  SCT_LINE	COMFORT	  Item (Trade Product) Batch ID (lot ID)
	public void setBatchID(String id);
	public String getBatchID();

	// TODO	52  SCT_LINE	COMFORT	  Item (Trade Product) Brand Name
//	public void setBrandName(String name);
//	public String getBrandName();

	// TODO	53  SCT_LINE	EXTENDED  Item (Trade Product) Model Name
//	public void setModelName(String name);
//	public String getModelName();

	// 54: BG-32 0..n ITEM ATTRIBUTES
	
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
	 * <br>Order-X-No: 	60
	 * 
	 * @param classCode,     BT-158   1..1
	 * @param listID,        BT-158-1 1..1 The identification scheme shall be chosen from the entries in UNTDID 7143
	 * @param listVersionID, BT-158-2 0..1 Scheme version identifier - The version of the identification scheme.
	 * @param idText         optional Product Classification Class Name 0..1
	 */
//	public IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID, String idText);
//	public void addClassificationIdentifier(IdentifierExt id);
//	default void addClassificationIdentifier(String code, String listID, String listVersionID, String name) {
//		addClassificationIdentifier(createClassificationIdentifier(code, listID, listVersionID, name));
//	}
//	public List<IdentifierExt> getClassifications();

	/**
	 * add Item (Trade Product) Instance
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	65
	 * 
	 * @param batchId - The unique batch identifier for this trade product instance
	 * @param serialId - The unique supplier assigned serial identifier for this trade product instance
	 */
//	66  SCT_LINE	COMFORT	  Item (Trade Product) Instances Batch ID
//	67  SCT_LINE	COMFORT	  Item (Trade Product) Instances Supplier Serial ID
	public void addTradeProductInstance(String batchId, String serialId);	
	public List<TradeProductInstance> getTradeProductInstances();

	/**
	 * set Supply Chain Packaging
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	68
	 * 
	 * @param code - The code specifying the type of packaging.
	 *               To be chosen from the entries in UNTDID 7065 Package type description code
	 * @param width - Measure
	 * @param length - Measure
	 * @param height - Measure
	 */
	public void setPackaging(String code, IQuantity width, IQuantity length, IQuantity height);
	/**
	 * get Supply Chain Packaging Code
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	69
	 * 
	 * @return The code specifying the type of packaging.
	 */
	public String getPackagingCode();
	/**
	 * get Supply Chain Packaging Dimension Width
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>Order-X-No: 	70: Packaging Dimension, 71: Width, 72: Width UnitCode
	 * 
	 * @return Measure aka IQuantity
	 */
	public IQuantity getPackagingWidth();
//	73  SCT_LINE	COMFORT	  Packaging Dimension Length
//	74  SCT_LINE	COMFORT	  Packaging Dimension Length UnitCode
//	75  SCT_LINE	COMFORT	  Packaging Dimension Height
//	76  SCT_LINE	COMFORT	  Packaging Dimension Height UnitCode
	public IQuantity getPackagingLength();
	public IQuantity getPackagingHeight();
	
	// 77,78: BG31_ItemInformation#setCountryOfOrigin
//	public void setCountryOfOrigin(String code);
//	public String getCountryOfOrigin();


//	--------------------------------
	/**
	 * line object identifier
	 * <p>
	 * An identifier for an object on which the line is based, given by the Seller.
	 * It may be a subscription number, telephone number, meter point etc., as applicable.  
	 * <p>
	 * A group of business terms providing information about additional supporting documents 
	 * substantiating the claims made in the order.
	 * <p>
	 * The additional supporting documents can be used for both referencing a document number which is expected 
	 * to be known by the receiver, an external document (referenced by a URL) 
	 * or as an embedded document (such as a time report in pdf). 
	 * The option to link to an external document will be needed, 
	 * for example in the case of large attachments and/or when sensitive information, 
	 * e.g. person-related services, has to be separated from the order itself.
	 * <p>
	 * A Object Identifier (BT-128) MUST have an ID/IssuerAssignedID
	 * <p>
	 * Cardinality:     0..1 (optional)
	 * <br>EN16931-ID: 	BG.25.BT-128, BT-128-0, BT-128-1
	 * <br>Rule ID: 	
	 * <br>Order-X-No: 	154
	 * 
	 * @param Identifier
	 * @param typeCode for an Object Identifier MUST be present and equal to 130 (UNTDID 1001 : InvoicingDataSheet)
	 * @param schemeCode (optional) if it may be not clear for the receiver what scheme is used for the identifier, 
	 * a conditional scheme identifier should be used that shall be chosen from the UNTDID 1153 code list entries.
	 */
//	 * A Line MUST NOT HAVE more than 1 Object Identifier BT-128 ==> Cardinality 0..1
	public void setLineObjectID(String id, String typeCode, String schemeCode);
	default void setLineObjectID(String id) {
		setLineObjectID(id, DocumentNameCode.InvoicingDataSheet.getValueAsString(), null);
	}
	default void setLineObjectID(String id, String schemeCode) {
		setLineObjectID(id, DocumentNameCode.InvoicingDataSheet.getValueAsString(), schemeCode);
	}
	default void setLineObjectIdentifier(Identifier id) {
		setLineObjectID(id.getContent(), DocumentNameCode.InvoicingDataSheet.getValueAsString(), id.getSchemeIdentifier());
	}
	public Identifier getLineObjectIdentifier(); // Identifier.Content == id , .SchemeIdentifier == schemeCode

	/**
	 * LINE TRADE DELIVERY Requested Quantity
	 * 
	 * The quantity, at line level, requested for this trade delivery.
	 * Unit of measure Code for Requested quantity.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <br>EN16931-ID: 	BT-129 (decimal quantity) + BT-130 (unitCode) 
	 * <br>Rule ID: 	BR-22
	 * <br>Order-X-No: 	207
	 * 
	 * @return Quantity
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
	 * <br>Order-X-No: 	127 
	 * 
	 * @param reference id
	 */
	// der Verweis auf die ursprüngliche ID ist in CIO überflüssig, nur in CIOR/CIOC
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
	 * 318: BG-27 0..n LINE ALLOWANCES
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage);	
	/**
	 * 326: BG-28 0..n LINE CHARGES
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage);
		
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
