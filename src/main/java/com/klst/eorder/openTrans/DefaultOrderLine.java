package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.bmecat.bmecat._2005.FNAME;
import org.bmecat.bmecat._2005.FVALUE;
import org.opentrans.xmlschema._2.FEATURE;
import org.opentrans.xmlschema._2.ORDERITEM;
import org.opentrans.xmlschema._2.ORDERRESPONSEITEM;
import org.opentrans.xmlschema._2.PRODUCTFEATURES;

import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.ISupplyChainEvent;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;
import com.klst.eorder.impl.TradeProductInstance;
import com.klst.eorder.impl.UnitPriceAmount;

/* Vergleich fields in
class ORDERITEMLIST {
    protected List<ORDERITEM> orderitem;
class ORDERCHANGEITEMLIST:
    protected List<ORDERITEM> orderitem;
==> sind gleich. Also nur Vergleich fields in

ORDERRESPONSEITEM ist Untermenge von ORDERITEM, denn:
ORDERITEM													ORDERRESPONSEITEM
 String partialshipmentallowed; 							fehlt
 SOURCINGINFO sourcinginfo;     							fehlt
 List<CUSTOMERORDERREFERENCE> customerorderreference;       fehlt ...
 ACCOUNTINGINFO accountinginfo;
 TRANSPORT transport;
 List<INTERNATIONALRESTRICTIONS> internationalrestrictions;
 */
public interface DefaultOrderLine extends OrderLine {

	@Override
	default OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount,
			String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		if(this instanceof ORDERITEM) {
			return OrderItem.create(id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
		}
		if(this instanceof ORDERRESPONSEITEM) {
			return OrderResponseItem.create(id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
		}
		return null;
	}

	@Override
	default OrderNote createNote(String subjectCode, String content) {
		return Remarks.create(subjectCode, content);
	}

	/*
	 * ADDITIONAL REFERENCED PRODUCT DOCUMENT (Order-X-No: 79-86)
	 * ADDITIONAL REFERENCED DOCUMENT (Order-X-No: 141-149)
	 * <p>
	 * An additional document referenced in line trade agreement.
	 * <p>
	 * Cardinality: 	0..n
	 * 
	 * Der Unterschied zwischen 79ff und 141ff ist der param lineId, der nur für 141ff definiert ist.
	 * Somit die Implementierung in OrderItem CUSTOMER_ORDER_REFERENCE / (Kundenauftragsbezug)
	 * die von ADDITIONAL REFERENCED DOCUMENT, die param uri, content, mimeCode, filename sind nicht in OT abbildbar
	 */
	@Override
	default SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, String uri) {
		return CustomerOrderReference.create(docRefId, lineId, description, ts);
	}
	@Override
	default SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, byte[] content, String mimeCode, String filename) {
		return CustomerOrderReference.create(docRefId, lineId, description, ts);
	}
	@Override
	default void addReferencedDocument(SupportingDocument supportigDocument) {
	}
	@Override
	default List<SupportingDocument> getReferencedDocuments() {
		return null;
	}

	// 79ff
	@Override
	default void addReferencedProductDocument(String code, SupportingDocument supportigDocument) {
	}
	@Override
	default List<SupportingDocument> getReferencedProductDocuments() {
		return null;
	}

	@Override
	default void setPickupDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Timestamp getPickupDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setDeliveryDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Timestamp getDeliveryDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default List<ISupplyChainEvent> getPickupEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addPickupEvent(ISupplyChainEvent supplyChainEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<ISupplyChainEvent> getDeliveryEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addDeliveryEvent(ISupplyChainEvent supplyChainEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void setDeliveryPeriod(IPeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IPeriod getDeliveryPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IPeriod createPeriod(Timestamp start, Timestamp end) {
		return DeliveryDate.create(start, end);
	}

	@Override
	default void setPickupPeriod(IPeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IPeriod getPickupPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, Timestamp timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, IPeriod period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<AllowancesAndCharges> getAllowancesAndCharges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IAmount getUnitPriceAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default AllowancesAndCharges getPriceDiscount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPriceDiscount(AllowancesAndCharges allowance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default AllowancesAndCharges getPriceCharge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPriceCharge(AllowancesAndCharges charge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IAmount getGrossPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setGrossPrice(IAmount grossPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IQuantity getUnitPriceQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setUnitPriceQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void setTaxCategory(TaxCategoryCode codeEnum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default TaxCategoryCode getTaxCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setTaxRate(BigDecimal taxRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default BigDecimal getTaxRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID,
			String idText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addClassificationIdentifier(IdentifierExt id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<IdentifierExt> getClassifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setCountryOfOrigin(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getCountryOfOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	default FEATURE createFeature(String name, String value) {
		FEATURE feature = new FEATURE();
		
		FNAME fname = new FNAME();
		fname.setValue(name);
		feature.getFNAME().add(fname);
		
		FVALUE fvalue = new FVALUE();
		fvalue.setValue(value);
		feature.getFVALUE().add(fvalue);
		
		return feature;
	}

	default Properties getItemAttributes(PRODUCTFEATURES features) {
		if(features==null) return null;
		List<FEATURE> feature = features.getFEATURE();
		Properties result = new Properties();
		feature.forEach(f -> {
			if(f.getFNAME().isEmpty()) { // eindeutiger Name des Merkmals
				// nix tun
			} else {
				// FVALUE : Ausprägung(en) des referenzierten Merkmals
				if(f.getFVALUE().isEmpty()) {
					// in OT steht was von VARIANTS, dann aber doch nicht
				} else {
					result.put(f.getFNAME().get(0).getValue(), f.getFVALUE().get(0).getValue());
				}
			}
		});
		return result;
	}

	@Override
	default void addItemAttribute(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Properties getItemAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

/*
// funktional:
	Function<Object, String> getLineItemID = (Object s) -> {
		if(s instanceof ORDERITEM) {
			return ((ORDERITEM)s).getLINEITEMID();
		}
		if(s instanceof ORDERRESPONSEITEM) {
			return ((ORDERRESPONSEITEM)s).getLINEITEMID();
		}
		return null;
	};
	@Override
	default String getId() {
		return getLineItemID.apply(this);
	}

// konventionell:
	@Override
	default String getId() {
		if(this instanceof ORDERITEM) {
			return ((ORDERITEM)this).getLINEITEMID();
		}
		if(this instanceof ORDERRESPONSEITEM) {
			return ((ORDERRESPONSEITEM)this).getLINEITEMID();
		}
		return null;
	}
	
// gewählte implementierungsstrategie: 
	alle Methoden leer vorbelegt, bzw liefern null (getter),
	notwendige implementierungen in den subklassen OrderItem bzw. OrderResponseItem
	 
 */	
	@Override
	default String getId() {
		return null;
	}
	
	@Override
	default void setStatus(String status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default List<OrderNote> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addNote(OrderNote note) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void setProductID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getProductID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Identifier createStandardIdentifier(String globalID, String schemeID) {
		return Productid.create().createStandardIdentifier(globalID, schemeID);
	}

	@Override
	default void addStandardIdentifier(Identifier id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<Identifier> getStandardIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setSellerAssignedID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getSellerAssignedID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setBuyerAssignedID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getBuyerAssignedID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default String getItemName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setDescription(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setBatchID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getBatchID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addTradeProductInstance(String batchId, String serialId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<TradeProductInstance> getTradeProductInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPackaging(String code, IQuantity width, IQuantity length, IQuantity height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getPackagingCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IQuantity getPackagingWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IQuantity getPackagingLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IQuantity getPackagingHeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setSubstitutedProductID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getSubstitutedProductID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addSubstitutedIdentifier(Identifier id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<Identifier> getSubstitutedIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setSubstitutedSellerAssignedID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getSubstitutedSellerAssignedID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setSubstitutedBuyerAssignedID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getSubstitutedBuyerAssignedID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setSubstitutedProductName(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getSubstitutedProductName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setSubstitutedProductDescription(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getSubstitutedProductDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setLineObjectID(String id, String typeCode, String schemeCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Identifier getLineObjectIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IQuantity getQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setAgreedQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IQuantity getAgreedQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPackageQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IQuantity getPackageQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPerPackageQuantity(IQuantity quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default IQuantity getPerPackageQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default IAmount getLineTotalAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setOrderLineID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getOrderLineID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setQuotationID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getQuotationID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setQuotationLineID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getQuotationLineID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setBuyerAccountingReference(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getBuyerAccountingReference() {
		// TODO Auto-generated method stub
		return null;
	}

	// 318: BG-27 0..n LINE ALLOWANCES
	@Override
	default AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return AllowOrCharge.create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	
	// 326: BG-28 0..n LINE CHARGES
	@Override
	default AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return AllowOrCharge.create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}

	@Override
	default void setBlanketOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default String getBlanketOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void setPartialDeliveryIndicator(boolean indicator) {
	}
	@Override
	default boolean isPartialDeliveryAllowed() {
		return false;
	}

}