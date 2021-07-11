package com.klst.eorder.openTrans;

import org.bmecat.bmecat._2005.TypePARTYID;

import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.Reference;

/* BMEcat TypePARTYID
 
Component                 | Use         	Primitive Type | Example
value                     : Mandatory 		String         | abc:123-DEF
type                      : Conditional 	String         | GLN
- nA -                    : Conditional 	String         | 1.0          // nicht in OT

Vordefinierte Werte für das Attribut "type" / aka schemeID:
buyer_specific
customer_specific
iln : International Location Number 
gln : Global Location Number, entspricht ICD 0209 : GS1 identification keys
party_specific
supplier_specific
Benutzerdefinierter Wert, z.B. ICD 0204 für Leitweg-ID 
 */
public class PartyID extends TypePARTYID implements Identifier, Reference {

	public PartyID(String content, String schemeID) {
		super();
		setContent(content);
		setSchemeIdentifier(schemeID);
	}

	public PartyID(String content) {
		this(content, null);
	}
		
	// copy ctor
	public PartyID(TypePARTYID id) {
		this(id.getValue(), id.getType());
	}
	
	public String toString() {
		return "["+(super.getType()==null ? "" : "SchemeID="+getType()+", ")
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
		super.setType(schemeId);
	}

	@Override
	public String getSchemeIdentifier() {
		return super.getType();
	}

}
