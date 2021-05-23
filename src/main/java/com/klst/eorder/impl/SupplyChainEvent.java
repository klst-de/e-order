package com.klst.eorder.impl;

import com.klst.ebXml.reflection.SCopyCtor;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SupplyChainEventType;

public class SupplyChainEvent extends SupplyChainEventType {

	// copy factory
	static SupplyChainEvent create(SupplyChainEventType object) {
		if(object instanceof SupplyChainEventType && object.getClass()!=SupplyChainEventType.class) {
			// object is instance of a subclass of SupplyChainEventType, but not SupplyChainEventType itself
			return (SupplyChainEvent)object;
		} else {
			return new SupplyChainEvent(object); 
		}
	}

	// copy ctor
	private SupplyChainEvent(SupplyChainEventType object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

}
