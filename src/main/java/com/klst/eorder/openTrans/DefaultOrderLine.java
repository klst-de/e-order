package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

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

public interface DefaultOrderLine extends OrderLine {

	@Override
	default OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount,
			String itemName, TaxCategoryCode codeEnum, BigDecimal percent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default OrderNote createNote(String subjectCode, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addReferencedProductDocument(String code, SupportingDocument supportigDocument) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<SupportingDocument> getReferencedProductDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, byte[] content, String mimeCode, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void addReferencedDocument(SupportingDocument supportigDocument) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<SupportingDocument> getReferencedDocuments() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
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

	@Override
	default void addItemAttribute(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default Properties getItemAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

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
		// TODO Auto-generated method stub
		return null;
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

	@Override
	default AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	default boolean isPartialDeliveryAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

}
