package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.ORDERRESPONSE;
import org.opentrans.xmlschema._2.ORDERRESPONSEITEM;
import org.opentrans.xmlschema._2.ORDERRESPONSEITEMLIST;
import org.opentrans.xmlschema._2.ORDERRESPONSESUMMARY;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.BG22_DocumentTotals;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.impl.UnitPriceAmount;

/* in super:
"orderresponseheader",
  elemente:
   CONTROLINFO       controlinfo;
   ORDERRESPONSEINFO orderresponseinfo;
"orderresponseitemlist" mit List<ORDERRESPONSEITEM> orderresponseitem
"orderresponsesummary" mit orderresponseinfo
protected String version;
*/
public class OrderResponse extends ORDERRESPONSE implements DefaultOrder {

	@Override  // implements BusinessPartyFactory
	public BusinessParty createParty(String name, String tradingName, PostalAddress address, ContactInfo contact) {
		return Party.create(name, tradingName, address, contact);
	}
	
	@Override // implements PostalAddressFactory
	public PostalAddress createAddress(String countryCode, String postalCode, String city) {
		return Party.create().createAddress(countryCode, postalCode, city);
	}

	@Override // implements CoreOrderFactory
	public CoreOrder createOrder(String profile, String processType, DocumentNameCode code) {
		return create(profile, processType, code);
	}

	OrderResponse create(String profile, String processType, DocumentNameCode code) {
		return new OrderResponse(profile, processType, code);
	}

	public static OrderResponse create() {
		return create((ORDERRESPONSE)null);
	}
	// copy factory
	static OrderResponse create(ORDERRESPONSE object) {
		if(object instanceof ORDERRESPONSE && object.getClass()!=ORDERRESPONSE.class) {
			// object is instance of a subclass of ORDERRESPONSE, but not ORDERRESPONSE itself
			return (OrderResponse)object;
		} else {
			return new OrderResponse(object); 
		}
	}

	private static final Logger LOG = Logger.getLogger(OrderResponse.class.getName());

	OrderResponseHeader orderHeader;
	OrderResponseInfo orderInfo;

	// ctor public, damit dynamisches cast im Test möglich ist
	public OrderResponse(ORDERRESPONSE doc) {
//		LOG.info("Version:"+doc.getVersion());
		SCopyCtor.getInstance().invokeCopy(this, doc);
		
		orderHeader = OrderResponseHeader.create(super.getORDERRESPONSEHEADER(), this);
		orderInfo = orderHeader.createOrderResponseInfo();
	}
	private OrderResponse(String profile, String processType, DocumentNameCode documentNameCode) {
		// alle 3 param nicht in OT
		// profile, aka Customization, BG-2.BT-24
		// processType, BG-2.BT-23
		// documentNameCode, BT-3 get liefert Order
		super();
		LOG.info("Version:"+super.getVersion());
		setVersion("2.1"); // required
		orderHeader = OrderResponseHeader.create(super.getORDERRESPONSEHEADER(), this);
		super.setORDERRESPONSEHEADER(orderHeader);
		orderInfo = orderHeader.createOrderResponseInfo();
		orderHeader.setORDERRESPONSEINFO(orderInfo);
	}

	// 9: Order number BT-1 Identifier (mandatory) - A unique identification of the Order.
	@Override
	public void setId(String id) {
		orderInfo.setId(id);
//		orderHeader.setORDERRESPONSEINFO(orderInfo);
	}
	@Override
	public String getId() {
		return orderInfo.getId();
	}

	@Override
	public List<OrderLine> getLines() {
		List<ORDERRESPONSEITEM> lines = super.getORDERRESPONSEITEMLIST().getORDERRESPONSEITEM();
		List<OrderLine> resultLines = new ArrayList<OrderLine>(lines.size());
		lines.forEach(line -> {
			resultLines.add(OrderResponseItem.create(line, this));
		});
		return resultLines;
	}
	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount, IAmount priceAmount,
			String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		return OrderResponseItem.create(this, id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
	}
	@Override
	public void addLine(OrderLine line) {
		if(super.getORDERRESPONSEITEMLIST()==null) {		
			super.setORDERRESPONSEITEMLIST(new ORDERRESPONSEITEMLIST());
		}
		super.getORDERRESPONSEITEMLIST().getORDERRESPONSEITEM().add((OrderResponseItem)line);
	}

	// 11: BT-3 The Document TypeCode
	@Override
	public DocumentNameCode getDocumentCode() {
		return DocumentNameCode.OrderResponse;
	}

	/* 14: Document issue date, BT-2  Date (mandatory) 
	 * Das Datum, an dem der Beleg ausgestellt wurde.
	 */
	@Override
	public void setIssueDate(Timestamp timestamp) {
		orderInfo.setIssueDate(timestamp);
	}
	@Override
	public Timestamp getIssueDateAsTimestamp() {
		return orderInfo.getIssueDateAsTimestamp();
	}
	
	// 345: BG-4 1..1 SELLER @see BG4_Seller
//	@Override
//	public void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId,
//			String companyLegalForm) {
//		// TODO Auto-generated method stub	
////		orderInfo.setSeller(name, address, contact, companyLegalForm);
//	}
	@Override
	public void setSeller(BusinessParty party) {
		orderInfo.setSeller(party);
	}
	@Override
	public BusinessParty getSeller() {
		return orderInfo.getSeller();
	}

	// 390: BG-7 1..1 BUYER @see BG7_Buyer
//	@Override
//	public void setBuyer(String name, PostalAddress address, ContactInfo contact) {
//		// TODO Auto-generated method stub	
////		orderInfo.setBuyer(name, address, contact);
//	}
	@Override
	public void setBuyer(BusinessParty party) {
		orderInfo.setBuyer(party);
	}
	@Override
	public BusinessParty getBuyer() {
		return orderInfo.getBuyer();
	}

//	@Override
//	public void setShipToParty(String name, PostalAddress address, ContactInfo contact) {
//		// TODO Auto-generated method stub	
//	}
	@Override
	public void setShipTo(BusinessParty party) {
		orderInfo.setShipToParty(party);
	}
	@Override
	public BusinessParty getShipTo() {
		return orderInfo.getShipToParty();
	}

//	@Override
//	public void setBillTo(String name, PostalAddress address, ContactInfo contact) {
//		// TODO Auto-generated method stub		
//	}
	@Override
	public void setBillTo(BusinessParty party) {
		orderInfo.setBillTo(party);
	}
	@Override
	public BusinessParty getBillTo() {
		return orderInfo.getBillTo();
	}

	// 767: BG-14 0..1 DELIVERY DATE
	@Override
	public void setDeliveryDate(Timestamp timestamp) {
		orderInfo.setDeliveryDate(timestamp);
	}
	@Override
	public Timestamp getDeliveryDateAsTimestamp() {
		return orderInfo.getDeliveryDateAsTimestamp();
	}
	@Override // factory
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return DeliveryDate.create(start, end);
	}	
	// 770: BG-14 0..1 DELIVERY PERIOD
	@Override
	public void setDeliveryPeriod(IPeriod period) {
		orderInfo.setDeliveryPeriod((DeliveryDate)period);
	}
	@Override
	public IPeriod getDeliveryPeriod() {
		return orderInfo.getDeliveryPeriod();
	}

	// 790: BT-5 1..1 Document currency code
	@Override
	public void setDocumentCurrency(String isoCurrencyCode) {
		orderInfo.setDocumentCurrency(isoCurrencyCode);
	}
	@Override
	public String getDocumentCurrency() {
		return orderInfo.getDocumentCurrency();
	}

	// 927: BG-22 DOCUMENT TOTALS 1..1 - mandatory BT-106, BT-109, BT-112
	// in ORDERSUMMARY gibt es nur protected BigDecimal totalamount
	// aber TODO required = true : protected BigInteger totalitemnum
	@Override
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive) {
		// TODO class xxx extends ORDERSUMMARY implements BG22_DocumentTotals
		if(super.getORDERRESPONSESUMMARY()==null) {
			ORDERRESPONSESUMMARY ors = new ORDERRESPONSESUMMARY();
//		    protected BigInteger totalitemnum; TODO required = true
//		    protected BigDecimal totalamount;
//		    protected ALLOWORCHARGESFIX alloworchargesfix;
			ors.setTOTALAMOUNT(lineNet.getValue());
			ors.setTOTALITEMNUM(BigInteger.valueOf(getLines().size()));
			super.setORDERRESPONSESUMMARY(ors);
		}
		return null;
	}

	// 928: BG-22.BT-106 - 1..1/1..1
	@Override
	public IAmount getLineNetTotal() {
		return super.getORDERRESPONSESUMMARY()==null ? null : Amount.create(getORDERRESPONSESUMMARY().getTOTALAMOUNT());
	}

}
