package com.klst.eorder.openTrans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.AGREEMENT;
import org.opentrans.xmlschema._2.ORDERHEADER;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.eorder.api.CoreOrder;

public class OrderHeader extends ORDERHEADER {

	private static final Logger LOG = Logger.getLogger(OrderHeader.class.getName());
	
	static OrderHeader create() {
		return new OrderHeader(null); 
	}
	// copy factory
	static OrderHeader create(ORDERHEADER object, CoreOrder order) {
		OrderHeader res;
		if(object instanceof ORDERHEADER && object.getClass()!=ORDERHEADER.class) {
			// object is instance of a subclass of ORDERHEADER, but not ORDERHEADER itself
			res = (OrderHeader)object;
		} else {
			res = new OrderHeader(object); 
		}
		res.order = order;
		return res;
	}

	private CoreOrder order;

	// copy ctor
	private OrderHeader(ORDERHEADER object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

//	private void getXX() {
		/*
Im Element CONTROL_INFO werden Steuerinformationen für die automatische Verarbeitung des Geschäftsdokumentes hinterlegt.
Wird das Element CONTROL_INFO verwendet, so muss mindestens eines der nachfolgenden Elemente angegeben werden:
STOP_AUTOMATIC_PROCESSING : Unterbrechung der automatischen Verarbeitung im Zielsystem
GENERATOR_INFO            : Erstellerinformation
GENERATION_DATE           : Zeitstempel der Erstellung des Geschäftsdokuments
		 */
//		super.getCONTROLINFO();
		/*
(Beschaffungsinformationen)
Im Element SOURCING_INFO werden Informationen über Beschaffungsaktivitäten zusammengefasst, die diesem Auftrag voraus gegangen sind. Wird das Element
SOURCING_INFO verwendet, so muss mindestens eines der nachfolgenden Elemente angegeben werden:
QUOTATION_ID      : Angebotssnummer des Lieferanten
AGREEMENT         : Referenz auf Rahmenvertrag
CATALOG_REFERENCE : Referenz auf einen (elektronischen) Produktkatalog
		 */
//		super.getSOURCINGINFO();
//	}

	private void addAgreement(String agreementType, String id, Timestamp ts) {
		super.getSOURCINGINFO().getAGREEMENT().add(
				SourcingInfoAgreement.create(agreementType, id, ts)
				);
	}
	private List<SourcingInfoAgreement> getAgreement(String agreementType) {
		List<SourcingInfoAgreement> res = new ArrayList<SourcingInfoAgreement>();
		if(super.getSOURCINGINFO()==null) return res;
		
		List<AGREEMENT> list = getSOURCINGINFO().getAGREEMENT();
		if(list.isEmpty()) return res;
		
		list.forEach(agreement -> {
			if(agreementType.equals(agreement.getType())) {
				res.add(SourcingInfoAgreement.create(agreement));
			}
		});
		return res;
	}
	
	// 524: BT-14 0..1 SALES ORDER REFERENCED DOCUMENT
	void setOrderReference(String id, Timestamp ts) {
		addAgreement(SourcingInfoAgreement.SUPPLIER, id, ts);
	}
	String getOrderReference() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.SUPPLIER);
		return list.isEmpty() ? null : list.get(0).getAGREEMENTID();
	}
	Timestamp getOrderDate() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.SUPPLIER);
		if(list.isEmpty()) return null;
		String dt = list.get(0).getAGREEMENTID();
		return dt==null ? null : DateTimeFormats.dtDATETIMEToTs(dt);
	}

	// 529: BT-13 0..1 Purchase order reference
//	 * <br>Rule ID: 	In an Order type message (BT-3 = 220/Order), 
//	 *     if the Buyer Order Referenced Document ID (BT-13) is present, 
//	 *     it MUST be equal to Document ID (BT-1)
	public void setPurchaseOrderReference(String id, Timestamp ts) {
		if(DocumentNameCode.Order==order.getDocumentCode()) {
			if(order.getId().equals(id)) {
				LOG.warning("BR ignored: Buyer Order Referenced Document ID "+id);
				return;
			}
		}
		addAgreement(SourcingInfoAgreement.BUYER, id, ts);
	}
	public String getPurchaseOrderReference() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.BUYER);
		return list.isEmpty() ? null : list.get(0).getAGREEMENTID();
	}
	Timestamp getPurchaseOrderDate() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.BUYER);
		if(list.isEmpty()) return null;
		String dt = list.get(0).getAGREEMENTID();
		return dt==null ? null : DateTimeFormats.dtDATETIMEToTs(dt);
	}

	// 534: 0..1 QUOTATION REFERENCE
	void setQuotationReference(String id, Timestamp ts) {
		addAgreement(SourcingInfoAgreement.QUOTATION, id, ts);
	}
	String getQuotationReference() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.QUOTATION);
		return list.isEmpty() ? null : list.get(0).getAGREEMENTID();
	}
	Timestamp getQuotationDate() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.QUOTATION);
		if(list.isEmpty()) return null;
		String dt = list.get(0).getAGREEMENTID();
		return dt==null ? null : DateTimeFormats.dtDATETIMEToTs(dt);
	}
	
	// 539: BT-12 0..1 Contract reference / (Referenz auf Rahmenvertrag)
	void setContractReference(String id, Timestamp ts) {
		addAgreement(SourcingInfoAgreement.CONTRACT, id, ts);
	}
	String getContractReference() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.CONTRACT);
		return list.isEmpty() ? null : list.get(0).getAGREEMENTID();
	}
	Timestamp getContractDate() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.CONTRACT);
		if(list.isEmpty()) return null;
		String dt = list.get(0).getAGREEMENTID();
		return dt==null ? null : DateTimeFormats.dtDATETIMEToTs(dt);
	}

	// 544: 0..1 REQUISITION REFERENCE, not in CII
	public void setRequisitionReference(String id, Timestamp ts) {
		addAgreement(SourcingInfoAgreement.REQUISITION, id, ts);
	}
	public String getRequisitionReference() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.REQUISITION);
		return list.isEmpty() ? null : list.get(0).getAGREEMENTID();
	}
	Timestamp getRequisitionDate() {
		List<SourcingInfoAgreement> list = getAgreement(SourcingInfoAgreement.REQUISITION);
		if(list.isEmpty()) return null;
		String dt = list.get(0).getAGREEMENTID();
		return dt==null ? null : DateTimeFormats.dtDATETIMEToTs(dt);
	}
		

//	public List<OrderLine> getLines() {
//		List<SupplyChainTradeLineItemType> lines = super.getIncludedSupplyChainTradeLineItem();
//		List<OrderLine> resultLines = new ArrayList<OrderLine>(lines.size());
//		lines.forEach(line -> {
//			resultLines.add(SupplyChainTradeLineItem.create(line, this.order));
//		});
//		return resultLines;
//	}
	
//	public ControlInfo createtControlInfo() {
//		super.getCONTROLINFO(); // hat nur 3 elemente
//	}
	
//	public SourcingInfo createSourcingInfo() {
//		return OrderInfo.create(super.getSOURCINGINFO());
////	SOURCINGINFO	hat 3 elemente:
////	    protected String quotationid;
////	    protected List<AGREEMENT> agreement;
////	    protected CATALOGREFERENCE catalogreference;
//	}
	
	public OrderInfo createOrderInfo() {
		return OrderInfo.create(super.getORDERINFO());
	}

//	public HeaderTradeDelivery createtHeaderTradeDelivery() {
//		return HeaderTradeDelivery.create(getApplicableHeaderTradeDelivery());
//	}
//	
//	public HeaderTradeSettlement createtHeaderTradeSettlement() {
//		return HeaderTradeSettlement.create(getApplicableHeaderTradeSettlement());
//	}
	
}
