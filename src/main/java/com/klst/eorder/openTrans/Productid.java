package com.klst.eorder.openTrans;

import java.util.ArrayList;
import java.util.List;

import org.bmecat.bmecat._2005.BUYERPID;
import org.bmecat.bmecat._2005.DESCRIPTIONLONG;
import org.bmecat.bmecat._2005.DESCRIPTIONSHORT;
import org.bmecat.bmecat._2005.INTERNATIONALPID;
import org.bmecat.bmecat._2005.SUPPLIERPID;
import org.opentrans.xmlschema._2.PRODUCTID;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;
import com.klst.eorder.api.BG31_ItemInformation;
import com.klst.eorder.openTrans.reflection.Mapper;

/* Kandidat für
BG-31 ++ 1..1 ITEM INFORMATION            ------> ram:SpecifiedTradeProduct
erl  BT-153 +++ 1..1 Artikelname
erl  BT-154 +++ 0..1 Artikelbeschreibung
     BT-155 +++ 0..1 Artikelkennung des Verkäufers
     BT-156 +++ 0..1 Artikelkennung des Käufers
     BT-157 +++ 0..1 Kennung eines Artikels nach registriertem Schema
     BT-158 +++ 0..n Kennung der Artikelklassifizierung
     BT-159 +++ 0..1 Artikelherkunftsland

    "supplierpid",           BT-155
    "supplieridref",
    "configcodefix",
    "lotnumber",
    "serialnumber",
    "internationalpid",      BT-157
    "buyerpid",              BT-156
    "descriptionshort",      BT-153
    "descriptionlong",       BT-154
    "manufacturerinfo",
    "producttype"
                             BT-158 evtl. in ../PRODUCT_FEATURES
                             BT-159 abbildbar in ../PRODUCT_FEATURES 
 */
public class Productid extends PRODUCTID implements BG31_ItemInformation {

	static Productid create() {
		return new Productid(null); 
	}
	// copy factory
	static Productid create(PRODUCTID object) {
		Productid res;
		if(object instanceof PRODUCTID && object.getClass()!=PRODUCTID.class) {
			// object is instance of a subclass of PRODUCTID, but not PRODUCTID itself
			res = (Productid)object;
		} else {
			res = new Productid(object); 
		}
		return res;
	}

	// copy ctor
	private Productid(PRODUCTID object) {
		super();
		if(object!=null) {
			SCopyCtor.getInstance().invokeCopy(this, object);
		}
	}
	
	// BG-31.BT-153 1..1 SpecifiedTradeProduct.Name
	void setItemName(String text) {
		Mapper.add(getDESCRIPTIONSHORT(), new DESCRIPTIONSHORT(), text);
	}
	@Override
	public String getItemName() {
		return getDESCRIPTIONSHORT().isEmpty() ? null : getDESCRIPTIONSHORT().get(0).getValue();
	}

	// BG-31.BT-154 0..1 Item description
	@Override
	public void setDescription(String text) {
		Mapper.add(getDESCRIPTIONLONG(), new DESCRIPTIONLONG(), text);
	}
	@Override
	public String getDescription() {
		return getDESCRIPTIONLONG().isEmpty() ? null : getDESCRIPTIONLONG().get(0).getValue();
	}
	
	// BG-31.BT-155 0..1 SpecifiedTradeProduct.sellerAssignedID
	@Override
	public void setSellerAssignedID(String id) {
		SUPPLIERPID supplierpid = new SUPPLIERPID();
		supplierpid.setValue(id);
		super.setSUPPLIERPID(supplierpid);
		// äquivalent:
//		Mapper.set(this, "supplierpid", id); // BUG TODO
	}
	@Override
	public String getSellerAssignedID() {
		return super.getSUPPLIERPID()==null ? null : getSUPPLIERPID().getValue();
	}
	
	// BG-31.BT-156 0..1 <bmecat:BUYER_PID type="buyer_specific">907216725</bmecat:BUYER_PID>
	@Override
	public void setBuyerAssignedID(String id) {
//		Mapper.add(getBUYERPID(), new BUYERPID(), id); // ??? TODO
		BUYERPID buyerpid = new BUYERPID();
		buyerpid.setValue(id);
		super.getBUYERPID().add(buyerpid);
	}
	@Override
	public String getBuyerAssignedID() {
		return getBUYERPID().isEmpty() ? null : getBUYERPID().get(0).getValue();
	}

	// BG-31.BT-157 0..1 <bmecat:INTERNATIONAL_PID type="ean">7611577104836</bmecat:INTERNATIONAL_PID>
	/**
	 * INTERNATIONAL_PID
	 * (Internationale Artikelnummer) namespace: BMECAT
	 * Dieses Element dient Übertragung einer internationalen Artikelnummer (z.B. EAN) zu dem Produkt. 
	 * Der zugrunde liegende Standards bzw. die vergebende Organisation wird durch das Attribute 'type' angegeben
	 */
	class INTERNATIONAL_PID extends INTERNATIONALPID implements Identifier {

		INTERNATIONAL_PID(String content, String type) {
			setContent(content);
			setSchemeIdentifier(type);
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
		public void setSchemeIdentifier(String type) {
			if(type!=null) super.setType(type);
		}

		@Override
		public String getSchemeIdentifier() {
			return super.getType();
		}
		
		@Override
		public String toString() {
			return "["+(super.getType()==null ? "" : "SchemeID="+getSchemeIdentifier()+", ")
				+"\""+super.getValue()+"\"]";
		}
	}
	@Override
	public Identifier createStandardIdentifier(String globalID, String schemeID) {
		return new INTERNATIONAL_PID(globalID, schemeID);
	}
	
	@Override
	public void addStandardIdentifier(Identifier id) {
		super.getINTERNATIONALPID().add((INTERNATIONAL_PID)id);
	}
	@Override
	public List<Identifier> getStandardIdentifier() {
		if(getINTERNATIONALPID().isEmpty()) return null;
		List<INTERNATIONALPID> list = getINTERNATIONALPID();
		List<Identifier> result = new ArrayList<Identifier>(list.size());
		list.forEach(id -> {
			result.add(new INTERNATIONAL_PID(id.getValue(), id.getType()));
		});
		return result;
	}
	
	@Override
	public IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID, String idText) {
		return null;
	}
	@Override
	public void addClassificationIdentifier(IdentifierExt id) {
	}
	@Override
	public List<IdentifierExt> getClassifications() {
		return null;
	}
	
	@Override
	public void setCountryOfOrigin(String code) {
	}
	@Override
	public String getCountryOfOrigin() {
		return null;
	}
	
}
