package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;

/**
 * Interface SupplyChainEvent is used in order lines
 * with DELIVERY or PICK UP.
 * In BASIC and COMFORT the cardinality is 0..1 and there is no need for event Unit Quantity
 * because the requested quantity equals to line ordered quantity.
 * In extended profile you can define more then one delivery or pick up events.
 * Example:
 * You order 100 items and request 3 delivery events:
 * in January you need 30 items, in April 20, and the remaining 50 can be delivered in the period May to October.
 * <p>
 * Cardinality: 	0..1 / extended 0..n
 * <br>Order-X-No: 	284ff (Pick up) , 297ff (Delivery)
 *
 */
public interface ISupplyChainEvent extends ISupplyChainEventFactory {

	public Timestamp getOccurrenceDateAsTimestamp();
	
	public IQuantity getQuantity();

	public IPeriod getOccurrencePeriod();

}
