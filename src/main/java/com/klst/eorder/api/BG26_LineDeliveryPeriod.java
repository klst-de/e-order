package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.IPeriod;
import com.klst.edoc.untdid.DateTimeFormats;

/**
 * BG-26 Line DELIVERY or PICK UP
 * DATE or PERIOD with BT-134 line period start date and BT-135 line period end date
 * <p>
 * The Requested Date or Period on which Delivery or Pick up is requested.
 * Or a group of business terms providing information about the delivery date or period relevant for the Order line.
 * <p>
 * Requested Delivery Date
 * Requested Delivery Period, at least 1 StartDate or 1 EndDate
 * <p>
 * Cardinality: 	0..1 / extended 0..n
 * <br>EN16931-ID: 	BG-26
 * <br>Rule ID:
 * <br>Order-X-No: 	284ff (Pick up) , 297ff (Delivery)
 * <p>
 * BT-134 and BT-135
 * Cardinality: 	0..1
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG26_LineDeliveryPeriod extends DeliveryPeriod, PickupPeriod  {

	// 285: Pick up Date
	public void setPickupDate(Timestamp timestamp);
	default void setPickupDate(String ymd) {
		if(ymd!=null) setPickupDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getPickupDateAsTimestamp();

	// (extended) 288: Unit Quantity to be delivered in this event
//	public void setPickupUnitQuantity(IQuantity quantity);
//	public IQuantity getPickupUnitQuantity();

	// 298: Requested Delivery Date
	public void setDeliveryDate(Timestamp timestamp);
	default void setDeliveryDate(String ymd) {
		if(ymd!=null) setDeliveryDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getDeliveryDateAsTimestamp();
	
	// (extended) 301: Unit Quantity to be delivered in this event
//	public void setDeliveryUnitQuantity(IQuantity quantity);
//	public IQuantity getDeliveryUnitQuantity();
	
	/*
TODO: für (extended)
public void addDelivery(IQuantity quantity, Timestamp timestamp);
public void addDelivery(IQuantity quantity, IPeriod period);
	 */
}
