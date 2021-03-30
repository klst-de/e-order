package com.klst.eorder.api;

import java.util.List;

import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;

/**
 * BG-31 ITEM INFORMATION
 * <p>
 * A group of business terms providing information about the goods and services ordered
 * <p>
 * Cardinality: 	1..1
 * <br>EN16931-ID: 	BG-31
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG31_ItemInformation {

	/**
	 * Item name
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BG-31.BT-153 
	 * 
	 * @param text Text
	 */
	/* OT: DESCRIPTION_SHORT
	 * Dieses Element enthält die Kurzbeschreibung/-bezeichnung des Produktes. 
	 * Grundsätzlich soll diese Beschreibung kurz und innerhalb der ersten 40 Zeichen eindeutig und aussagekräftig sein, 
	 * da in der weiteren Verwendung viele Anwendungssysteme nur 40 Zeichen verarbeiten können (Beispiel: SAP-OCI, SAP R/3).
	 * 
	 * Ausführliche Kurzbeschreibungen bieten Vorteile bei Suchanfragen von weitestgehend ähnlichen Produkten. 
	 * Bereits in der ersten Trefferliste sind diese dann differenzierbar.
	 * Auf Abkürzungen von wesentlichen Produkteigenschaften sollte generell verzichtet werden 
	 * (z.B. schw. statt schwarz). 
	 * Abkürzungen von Organisationen oder Standards können selbstverständlich verwendet werden (z.B. DIN A4, VDE).
	 */
//	public void setItemName(String text); // use OrderLineFactory
	public String getItemName();

	/**
	 * Item description
 	 * <p>
	 * The Item description allows for describing the item and its features in more detail than the Item name.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-154
	 * 
	 * @param Text
	 */
	/* OT: DESCRIPTION_LONG
	 * Dieses Element enthält die Langbeschreibung des Produktes.
	 */
	public void setDescription(String text);
	public String getDescription();

	/**
	 * Item Seller's identifier
 	 * <p>
	 * An identifier, assigned by the Seller, for the item.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-155
	 * 
	 * @param Identifier
	 */
	/*
	 * OT: SUPPLIER_PID + 'type'
	 * Dieses Element enthält die Artikelnummer des Lieferanten. Sie ist damit maßgeblich für die Bestellung. 
	 * In Geschäftsdokumenten eines Lieferanten identifiziert sie das Produkt eindeutig. 
	 * In Multi-Lieferantendokumenten dagegen ist die Kombination 
	 * aus Artikelnummer SUPPLIER_PID und SUPPLIER_IDREF der Identifikator.
	 */
	public void setSellerAssignedID(String id);
	public String getSellerAssignedID();

	/**
	 * Item Buyer's identifier
 	 * <p>
	 * An identifier, assigned by the Buyer, for the item.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-156
	 * 
	 * @param Identifier
	 */
	/*
	 * OT: BUYER_PID + 'type'
	 * Dieses Element enthält die Artikelnummer des einkaufenden Unternehmens, 
	 * Das Attribut "type" legt Art der Artikelnummer fest:
	 *  buyer_specific
	 *  ean
	 *  gtin
	 *  upc
	 *  Benutzerdefinierter Wert im Format: \w{1,50}
	 *
	 * Bei Mehrfachverwendung müssen die Werte des Attributes "type" unterschiedlich sein
	 */
	public void setBuyerAssignedID(String id);
	public String getBuyerAssignedID();

	/**
	 * Item standard (aka global) identifier
 	 * <p>
	 * An item identifier based on a registered scheme.
	 * <p>
	 * Cardinality: 	0..n (optional)
	 * <br>EN16931-ID: 	BG-31.BT-157 BG-31.BT-157-1
	 * 
	 * @param globalID
	 * @param schemeID, The identification scheme shall be identified from the entries of the list published by the ISO/IEC 6523 maintenance agency.
	 */
	/* OT: INTERNATIONAL_PID + 'type'
	 * Angabe einer internationalen Artikelnummer (z.B. EAN). 
	 * Der zugrunde liegende Standards bzw. die vergebende Organisation wird durch das Attribute 'type' benannt
	 * 
	 * GlobalID Kennung eines Artikels nach registriertem Schema ('type')
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
	public Identifier createStandardIdentifier(String globalID, String schemeID);
	public void addStandardIdentifier(Identifier id);
	default void addStandardIdentifier(String globalID, String schemeID) {
		addStandardIdentifier(createStandardIdentifier(globalID, schemeID));
	}
	public List<Identifier> getStandardIdentifier();

	/**
	 * Item classification identifier
 	 * <p>
	 * A code for classifying the item by its type or nature.
	 * Classification codes are used to allow grouping of similar items for a various purposes 
	 * e.g. public procurement (CPV), e-Commerce (UNSPSC) etc.
	 * <p>
	 * Cardinality: 	0..n (optional)
	 * <br>EN16931-ID: 	BG-31.BT-158, BT-158-1, BT-158-2
	 * 
	 * @param classCode     BT-158   1..1
	 * @param listID        BT-158-1 1..1 The identification scheme shall be chosen from the entries in UNTDID 7143
	 * @param listVersionID BT-158-2 0..1 The version of the identification scheme
	 * @param idText        Product Classification Class Name 0..1
	 */
	/* OT: PRODUCT_FEATURES
	 * Mit diesem Element können Produkte durch Merkmale beschrieben und/oder 
	 * (2) Klassifikationssystemen zugeordnet werden.
	 * Das (2) System wird referenziert (REFERENCE_FEATURE_SYSTEM_NAME); 
	 * anschließend folgt die Angabe der zugehörigen Gruppe im Element REFERENCE_FEATURE_GROUP_ID 
	 * oder REFERENCE_FEATURE_GROUP_NAME. 
	 */
	public IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID, String idText);
	public void addClassificationIdentifier(IdentifierExt id);
	default void addClassificationIdentifier(String code, String listID, String listVersionID, String name) {
		addClassificationIdentifier(createClassificationIdentifier(code, listID, listVersionID, name));
	}
	public List<IdentifierExt> getClassifications();

	/**
	 * Item country of origin
 	 * <p>
	 * The code identifying the country from which the item originates.
	 * The lists of valid countries are registered with the EN ISO 3166-1 Maintenance agency, 
	 * “Codes for the representation of names of countries and their subdivisions”.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-31.BT-159
	 * 
	 * @param code
	 */
	public void setCountryOfOrigin(String code);
	public String getCountryOfOrigin();

}
