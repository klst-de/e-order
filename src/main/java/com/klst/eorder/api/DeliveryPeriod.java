package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IPeriodFactory;

/**
 * DELIVERY PERIOD with start date and end date
 * <p>
 * The Requested Period on which Delivery is requested, 
 * mutually exclusive with Pick up aka Despatch
 * <p>
 * Requested Delivery Period, at least 1 StartDate or 1 EndDate
 * <p>
 * Cardinality: 	0..1
 * <br>Order-X-No: 	770 and 303 (at Order line level)
 * 
 *  @see PickupPeriod
 */
public interface DeliveryPeriod extends IPeriodFactory {

	// createPeriod in IPeriodFactory
	
	public void setDeliveryPeriod(IPeriod period);
	
	default void setDeliveryPeriod(Timestamp start, Timestamp end) {
		setDeliveryPeriod(createPeriod(start, end));
	}
	default void setDeliveryPeriod(String ymdStart, String ymdEnd) {
		setDeliveryPeriod(createPeriod(ymdStart, ymdEnd));
	}
	
	public IPeriod getDeliveryPeriod();

}
