package com.klst.eorder.impl;

import com.klst.edoc.api.Identifier;

import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;

/*
 
Component                 | Use         	Primitive Type | Example
Content                   : Mandatory 		String         | abc:123-DEF
Scheme identifier         : Conditional 	String         | GLN
Scheme version identifier : Conditional 	String         | 1.0          // nicht in unqualifieddatatype._103
 
 */
public class ID extends IDType implements Identifier /*, Reference*/ {

	public ID(String content, String schemeID) {
		super();
		setContent(content);
		setSchemeIdentifier(schemeID);
	}

	public ID(String content) {
		this(content, null);
	}
		
	// copy ctor
	public ID(IDType id) {
		this(id.getValue(), id.getSchemeID());
	}
	
	public String toString() {
		return "["+(super.getSchemeID()==null ? "" : "SchemeID="+getSchemeID()+", ")
			+"\""+super.getValue()+"\"]";
	}

	@Override
	public void setContent(String content) {
		super.setValue(content);
	}

	@Override
	public String getContent() {
		return super.getValue();
	}

	@Override
	public void setSchemeIdentifier(String schemeId) {
		if(schemeId==null) return;
		super.setSchemeID(schemeId);
	}

	@Override
	public String getSchemeIdentifier() {
		return super.getSchemeID();
	}

}
