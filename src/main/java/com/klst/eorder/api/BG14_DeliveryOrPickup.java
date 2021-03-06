package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.untdid.DateTimeFormats;

/**
 * BG-14 DELIVERY or PICK UP
 * DATE or PERIOD with BT-73 start date and BT-74 end date
 * <p>
 * The Requested Date or Period on which Delivery is requested, 
 * mutually exclusive with Pick up aka Despatch
 * <p>
 * Requested Delivery Date
 * Requested Delivery Period, at least 1 StartDate or 1 EndDate
 * <p>
 * Cardinality: 	0..1
 * <br>EN16931-ID: 	BG-14
 * <br>Rule ID:
 * <br>Order-X-No: 	766ff (Delivery) , 777ff (Pick up)
 * <p>
 * 771: BT-73 and 774: BT-74
 * Cardinality: 	0..1
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG14_DeliveryOrPickup extends DeliveryPeriod, PickupPeriod {

	// 767: Delivery Date
	public void setDeliveryDate(Timestamp timestamp);
	default void setDeliveryDate(String ymd) {
		if(ymd!=null) setDeliveryDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getDeliveryDateAsTimestamp();

	// 778: Pick up Date
	public void setPickupDate(Timestamp timestamp);
	default void setPickupDate(String ymd) {
		if(ymd!=null) setPickupDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getPickupDateAsTimestamp();

}
