package com.klst.eorder.api;

import java.sql.Timestamp;
import java.util.List;

import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
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
 * 
 * @see ISupplyChainEvent
 */
public interface BG26_LineDeliveryOrPickup extends DeliveryPeriod, PickupPeriod, ISupplyChainEventFactory {

	// 285: Pick up Date, use this in BASIC and COMFORT
	public void setPickupDate(Timestamp timestamp);
	default void setPickupDate(String ymd) {
		if(ymd!=null) setPickupDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getPickupDateAsTimestamp();

	// 298: Requested Delivery Date, use this in BASIC and COMFORT
	public void setDeliveryDate(Timestamp timestamp);
	default void setDeliveryDate(String ymd) {
		if(ymd!=null) setDeliveryDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getDeliveryDateAsTimestamp();
	
	// 284 (Pick up) 0..1 / extended: 0..n
	public List<ISupplyChainEvent> getPickupEvents();
	public void addPickupEvent(ISupplyChainEvent supplyChainEvent);
	default void addPickupEvent(IQuantity quantity, Timestamp timestamp) {
		addPickupEvent(createSupplyChainEvent(quantity, timestamp));
	}
	default void addPickupEvent(IQuantity quantity, IPeriod period) {
		addPickupEvent(createSupplyChainEvent(quantity, period));
	}
	
	// 297 (Delivery)  0..1 / extended: 0..n
	public List<ISupplyChainEvent> getDeliveryEvents();
	public void addDeliveryEvent(ISupplyChainEvent supplyChainEvent);
	default void addDeliveryEvent(IQuantity quantity, Timestamp timestamp) {
		addDeliveryEvent(createSupplyChainEvent(quantity, timestamp));
	}
	default void addDeliveryEvent(IQuantity quantity, IPeriod period) {
		addDeliveryEvent(createSupplyChainEvent(quantity, period));
	}
	
}
