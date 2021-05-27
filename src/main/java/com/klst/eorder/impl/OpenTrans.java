package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.OPENTRANS;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.MessageFunctionEnum;
import com.klst.edoc.untdid.PaymentMeansEnum;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG22_DocumentTotals;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;

public class OpenTrans extends OPENTRANS implements CoreOrder {

	private static final Logger LOG = Logger.getLogger(OpenTrans.class.getName());

	public OpenTrans(OPENTRANS doc) {
		SCopyCtor.getInstance().invokeCopy(this, doc);

		LOG.info("ORDER:"+super.getORDER());
		LOG.info("ORDERCHANGE:"+super.getORDERCHANGE());
	}

	@Override
	public CoreOrder createOrder(String profile, String processType, DocumentNameCode code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderNote> getOrderNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNote(OrderNote note) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrderNote createNote(String subjectCode, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomization() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId,
			String companyLegalForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSeller(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getSeller() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBuyer(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBuyer(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getBuyer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeliveryDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getDeliveryDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPickupDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getPickupDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeliveryPeriod(IPeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPeriod getDeliveryPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPickupPeriod(IPeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPeriod getPickupPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAmount getLineNetTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAmount getTotalTaxExclusive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAmount getTotalTaxInclusive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAllowancesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAmount getAllowancesTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChargesTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAmount getChargesTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxTotal(IAmount amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAmount getTaxTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSupportigDocument(SupportingDocument supportigDocument) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SupportingDocument> getAdditionalSupportingDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, byte[] content, String mimeCode, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderLine> getLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount,
			String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLine(OrderLine line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShipToParty(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShipToParty(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getShipToParty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShipFromParty(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShipFromParty(BusinessParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusinessParty getShipFromParty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactInfo createContactInfo(String contactName, String contactTel, String contactMail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessParty createParty(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTestIndicator(boolean indicator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIssueDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getIssueDateAsTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCopyIndicator(boolean indicator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCopy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentNameCode getDocumentCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLanguage(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPurpose(MessageFunctionEnum code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageFunctionEnum getPurposeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRequestedResponse(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRequestedResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDocumentCurrency(String isoCurrencyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDocumentCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxCurrency(String isoCurrencyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTaxCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxPointDateCode(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTaxPointDateCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBuyerReference(String reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBuyerReferenceValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProjectReference(String id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reference getProjectReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContractReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getContractReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getContractDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPurchaseOrderReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPurchaseOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getPurchaseOrderDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrderReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getOrderDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQuotationReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getQuotationReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getQuotationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRequisitionReference(String docRefId, Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRequisitionReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getRequisitionDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTenderOrLotReference(String docRefId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTenderOrLotReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInvoicedObject(String name, String schemeID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvoicedObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Identifier getInvoicedObjectIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBuyerAccountingReference(Reference textReference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reference getBuyerAccountingReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentMeansEnum getPaymentMeansEnum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaymentMeansEnum(PaymentMeansEnum code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPaymentMeansText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaymentMeansText(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPaymentTerm(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPaymentTerms(List<String> paymentTerms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getPaymentTerms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeliveryTerms(String deliveryType, String functionCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDeliveryType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeliveryFunctionCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBlanketOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBlanketOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreviousOrderReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPreviousOrderReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreviousOrderChangeReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPreviousOrderChangeReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreviousOrderResponseReference(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPreviousOrderResponseReference() {
		// TODO Auto-generated method stub
		return null;
	}

}
