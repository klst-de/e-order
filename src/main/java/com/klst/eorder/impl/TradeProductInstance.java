package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;

import com.klst.ebXml.reflection.SCopyCtor;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradeProductInstanceType;

//65  SCT_LINE	COMFORT	  Item (Trade Product) Instances
//66  SCT_LINE	COMFORT	  Item (Trade Product) Instances Batch ID
//67  SCT_LINE	COMFORT	  Item (Trade Product) Instances Supplier Serial ID
public class TradeProductInstance extends TradeProductInstanceType {

	static TradeProductInstance create(String batchId, String serialId) {
		if(batchId==null && serialId==null) return null;
		return new TradeProductInstance(batchId, serialId);
	}

	static List<TradeProductInstance> getTradeProductInstances(List<TradeProductInstanceType> list) {
		List<TradeProductInstance> result = new ArrayList<TradeProductInstance>(list.size());
		list.forEach(note -> {
			result.add(TradeProductInstance.create(note));
		});
		return result;
	}

	// copy factory
	static TradeProductInstance create(TradeProductInstanceType object) {
		if(object instanceof TradeProductInstanceType && object.getClass()!=TradeProductInstanceType.class) {
			// object is instance of a subclass of TradeProductInstanceType, but not TradeProductInstanceType itself
			return (TradeProductInstance)object;
		} else {
			return new TradeProductInstance(object); 
		}
	}
	
	private TradeProductInstance(String batchId, String serialId) {
		super();
		setBatchId(batchId);
		setSerialId(serialId);
	}
	// copy ctor
	private TradeProductInstance(TradeProductInstanceType object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}

	public String getBatchId() {
		return super.getBatchID()==null ? null : getBatchID().getValue();
	}

	void setBatchId(String batchId) {
		if(batchId==null) return; 
		super.setBatchID(new ID(batchId));
	}

	public String getSerialId() {
		return super.getSerialID()==null ? null : getSerialID().getValue();
	}

	void setSerialId(String serialId) {
		if(serialId==null) return; 
		super.setSerialID(new ID(serialId));
	}
	
	public String toString() {
		return "[BatchID="+(getBatchId()==null ? "null" : getBatchId())
			+", SerialID="+(getSerialId()==null ? "null" : getSerialId())+"]";
	}


}
