package com.klst.eorder.openTrans;

import java.util.List;

import org.opentrans.xmlschema._2.AGREEMENT;
import org.opentrans.xmlschema._2.ORDERHEADER;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.eorder.api.CoreOrder;

public class OrderHeader extends ORDERHEADER {

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

	private void getXX() {
		/*
Im Element CONTROL_INFO werden Steuerinformationen für die automatische Verarbeitung des Geschäftsdokumentes hinterlegt.
Wird das Element CONTROL_INFO verwendet, so muss mindestens eines der nachfolgenden Elemente angegeben werden:
STOP_AUTOMATIC_PROCESSING : Unterbrechung der automatischen Verarbeitung im Zielsystem
GENERATOR_INFO            : Erstellerinformation
GENERATION_DATE           : Zeitstempel der Erstellung des Geschäftsdokuments
		 */
		super.getCONTROLINFO();
		/*
(Beschaffungsinformationen)
Im Element SOURCING_INFO werden Informationen über Beschaffungsaktivitäten zusammengefasst, die diesem Auftrag voraus gegangen sind. Wird das Element
SOURCING_INFO verwendet, so muss mindestens eines der nachfolgenden Elemente angegeben werden:
QUOTATION_ID      : Angebotssnummer des Lieferanten
AGREEMENT         : Referenz auf Rahmenvertrag
CATALOG_REFERENCE : Referenz auf einen (elektronischen) Produktkatalog
		 */
		super.getSOURCINGINFO();
	}
	
	// 539: BT-12 0..1 Contract reference / (Referenz auf Rahmenvertrag)
	void setContractReference(String id) {
		// 542: Contract Reference Date

	}
	String getContractReference() {
		if(super.getSOURCINGINFO()==null) return null;
		List<AGREEMENT> list = getSOURCINGINFO().getAGREEMENT(); // List<AGREEMENT>
		return list.isEmpty() ? null : list.get(0).getAGREEMENTID();
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
