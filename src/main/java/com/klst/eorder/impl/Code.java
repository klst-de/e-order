package com.klst.eorder.impl;

import com.klst.edoc.api.IdentifierExt;

import un.unece.uncefact.data.standard.unqualifieddatatype._128.CodeType;

/* similar to Identifier, with version identifier
 
Component                 | Use         	Primitive Type | Example
Content aka code          : Mandatory 		String         | abc:123-DEF
Scheme/List identifier    : Conditional 	String         | GLN
List version identifier   : Conditional 	String         | 1.0          // nicht in unqualifieddatatype._103
 
 */
public class Code extends CodeType implements IdentifierExt {

	public Code(String code, String schemeID, String versionID) {
		super();
		setContent(code);
		setSchemeIdentifier(schemeID);
		setVersionIdentifier(versionID);
	}

	public Code(String code) {
		this(code, null, null);
	}
		
	public Code(String code, String schemeID) {
		this(code, schemeID, null);
	}
		
	// copy ctor
	public Code(CodeType id) {
		this(id.getValue(), id.getListID(), id.getListVersionID());
	}
	
	public String toString() {
		return "["+(super.getListID()==null ? "" : "ListID="+getListID()+", ")
				  +(super.getListVersionID()==null ? "" : "VersionID="+getListVersionID()+", ")
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
		super.setListID(schemeId);
	}

	@Override
	public String getSchemeIdentifier() {
		return super.getListID();
	}

	@Override
	public void setVersionIdentifier(String versionID) {
		if(versionID==null) return;
		super.setListVersionID(versionID);
	}

	@Override
	public String getVersionIdentifier() {
		return super.getListVersionID();
	}

	@Override // not implemented
	public void setIdText(String idText) {
		// not implemented, to implement superclass must be ProductClassificationType
	}
	@Override // not implemented
	public String getIdText() {
		return null;		
	}

}
