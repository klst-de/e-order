package com.klst.eorder.openTrans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bmecat.bmecat._2005.DtCURRENCIES;
import org.opentrans.xmlschema._2.ORDERINFO;
import org.opentrans.xmlschema._2.ORDERRESPONSEINFO;
import org.opentrans.xmlschema._2.PARTY;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.ContactInfo;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.openTrans.Party.PartyRole;

public class OrderResponseInfo extends ORDERRESPONSEINFO {

	// copy factory
	static OrderResponseInfo create(ORDERRESPONSEINFO object) {
		if(object instanceof ORDERRESPONSEINFO && object.getClass()!=ORDERRESPONSEINFO.class) {
			// object is instance of a subclass of ORDERRESPONSEINFO, but not ORDERRESPONSEINFO itself
			return (OrderResponseInfo)object;
		} else {
			return new OrderResponseInfo(object); 
		}
	}
	
	// copy ctor
	private OrderResponseInfo(ORDERRESPONSEINFO object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	// Order number BT-1 Identifier (mandatory) - A unique identification of the Order.
	void setId(String id) {
		super.setORDERID(id);
	}
	String getId() {
		return super.getORDERID();
	}

	// Document issue date, BT-2 : <ORDER_DATE>2009-05-13T06:20:00+01:00</ORDER_DATE>
	void setIssueDate(Timestamp timestamp) {
		super.setORDERRESPONSEDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}
	Timestamp getIssueDateAsTimestamp() {
		return DateTimeFormats.dtDATETIMEToTs(super.getORDERRESPONSEDATE());
	}

	// DELIVERYDATE / Zeitraum oder Zeitpunkt
	void setDeliveryPeriod(DeliveryDate period) {
		super.setDELIVERYDATE(period);
	}
	void setDeliveryDate(Timestamp timestamp) {
		setDeliveryPeriod(DeliveryDate.create(timestamp, timestamp));
	}
	Timestamp getDeliveryDateAsTimestamp() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return DateTimeFormats.dtDATETIMEToTs(getDELIVERYDATE().getDELIVERYSTARTDATE()); 
		}
		return null;
	}
	IPeriod getDeliveryPeriod() {
		if(super.getDELIVERYDATE()==null) return null;
		if(getDELIVERYDATE().getDELIVERYSTARTDATE().equals(getDELIVERYDATE().getDELIVERYENDDATE())) {
			// DELIVERYDATE ist Zeitpunkt
			return null;
		}
		// DELIVERYDATE ist Zeitraum
		return DeliveryDate.create(getDELIVERYDATE());
	}
	
	// BT-5 + 1..1 Invoice currency code
	void setDocumentCurrency(String isoCurrencyCode) {
		super.setCURRENCY(DtCURRENCIES.fromValue(isoCurrencyCode)); // enum DtCURRENCIES value
	}
	String getDocumentCurrency() {
		return getCURRENCY().value();
	}

	// 345: BG-4 1..1 SELLER @see BG4_Seller
	void setSeller(String name, PostalAddress address, ContactInfo contact, String companyId,
			String companyLegalForm) {
		// TODO Auto-generated method stub	
	}
	void setSeller(BusinessParty party) {
		// TODO Auto-generated method stub	
	}
	BusinessParty getSeller() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.supplier);
	}

	// 390: BG-7 1..1 BUYER @see BG7_Buyer
	void setBuyer(String name, PostalAddress address, ContactInfo contact) {
		// TODO Auto-generated method stub	
//		BusinessParty party = TradeParty.create(name, null, address, contact);
//		setBuyer(party);
	}
	void setBuyer(BusinessParty party) {
//		if(party==null) return;
//		super.setBuyerTradeParty((TradeParty)party);
	}
	BusinessParty getBuyer() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.buyer);
	}

	BusinessParty getShipToParty() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.delivery);
	}
	
	BusinessParty getBillTo() {
		return Party.getParty(super.getPARTIES().getPARTY(), PartyRole.invoice_recipient);
	}

}
