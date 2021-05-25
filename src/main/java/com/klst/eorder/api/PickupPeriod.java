package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IPeriodFactory;

/**
 * REQUESTED PICK UP PERIOD
 *  with start date and end date
 * <p>
 * A Requested Period on which Pick up is requested,
 * pick up is mutually exclusive with Delivery
 * <p>
 * Cardinality: 	0..1
 * <br>Order-X-No: 	781 and 290 (at Order line level)
 * 
 * @see DeliveryPeriod
 */
public interface PickupPeriod extends IPeriodFactory {

	// createPeriod in IPeriodFactory
	
	public void setPickupPeriod(IPeriod period);
	
	default void setPickupPeriod(Timestamp start, Timestamp end) {
		setPickupPeriod(createPeriod(start, end));
	}
	default void setPickupPeriod(String ymdStart, String ymdEnd) {
		setPickupPeriod(createPeriod(ymdStart, ymdEnd));
	}
	
	public IPeriod getPickupPeriod();

}
