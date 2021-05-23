package com.klst.eorder.api;

import java.sql.Timestamp;

import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;

public interface ISupplyChainEventFactory {

	public ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, Timestamp timestamp);
	public ISupplyChainEvent createSupplyChainEvent(IQuantity quantity, IPeriod period);

}
