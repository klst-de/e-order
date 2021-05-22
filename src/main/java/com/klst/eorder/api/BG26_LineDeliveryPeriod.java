package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IPeriodFactory;
import com.klst.edoc.untdid.DateTimeFormats;

/**
 * BG-26 DELIVERY DATE or PERIOD with BT-134 line period start date and BT-135 line period end date
 * <p>
 * The Requested Date or Period on which Pick up is requested.
 * Or a group of business terms providing information about the delivery date or period relevant for the Order line.
 * <p>
 * Requested Delivery Date
 * Requested Delivery Period, at least 1 StartDate or 1 EndDate
 * <p>
 * Cardinality: 	0..1
 * <br>EN16931-ID: 	BG-26
 * <br>Rule ID:
 * <br>Order-X-No: 	284ff (Pick up) , 297ff (Delivery)
 * <p>
 * BT-134 and BT-135
 * Cardinality: 	0..1
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG26_LineDeliveryPeriod extends IPeriodFactory {

	public void setDeliveryDate(Timestamp timestamp);
	default void setDeliveryDate(String ymd) {
		if(ymd!=null) setDeliveryDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getDeliveryDateAsTimestamp();

	public void setDeliveryPeriod(IPeriod period);
	default void setDeliveryPeriod(Timestamp start, Timestamp end) {
		setDeliveryPeriod(createPeriod(start, end));
	}
	default void setDeliveryPeriod(String ymdStart, String ymdEnd) {
		setDeliveryPeriod(createPeriod(ymdStart, ymdEnd));
	}
	public IPeriod getDeliveryPeriod();

	// Pick up:
	public void setPickupDate(Timestamp timestamp);
	default void setPickupDate(String ymd) {
		if(ymd!=null) setPickupDate(DateTimeFormats.ymdToTs(ymd));
	}
	public Timestamp getPickupDateAsTimestamp();

	public void setPickupPeriod(IPeriod period);
	default void setPickupPeriod(Timestamp start, Timestamp end) {
		setPickupPeriod(createPeriod(start, end));
	}
	default void setPickupPeriod(String ymdStart, String ymdEnd) {
		setPickupPeriod(createPeriod(ymdStart, ymdEnd));
	}
	public IPeriod getPickupPeriod();

}
