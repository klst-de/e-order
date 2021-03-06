package com.klst.eorder.api;

import com.klst.edoc.untdid.TaxTypeCode;

/*
Tax TypeCode : Code der Umsatzsteuerkategorie
. CII Datentyp: qdt:TaxTypeCodeType
. Beschreibung: Codierte Bezeichnung einer Umsatzsteuerkategorie
. Hinweis: Fester Wert = "VAT"
. Kardinalität: 1 .. 1
. EN16931-ID: BT-118-0

Codeliste: UNTDID 5153 Eingeschränkt
In der EN 16931 wird nur die Steuerart „Umsatzsteuer“ mit dem Code „VAT“ unterstützt. 
Sollen andere Steuerarten angegeben wie beispielsweise eine Versicherungssteuer oder eine Mineralölsteuer werden, 
muss das EXTENDED Profil genutzt werden. 
Der Code für die Steuerart muss dann der Codeliste UNTDID 5153 entnommen werden.

in CIO muss type in enum DutyTaxFeeTypeCodeContentType sein, also nicht nur VAT, 
sondern 50 andere Steuern auch, z.B. AAD   Tobacco tax
 */
public interface ITaxType {

	/**
	 * BG-23.BT-118-0 Tax TypeCode, 
	 * also used in EN16931-IDs BG-20.BT-95-0 , BG-21.BT-102-0 and BG-30.BT-151-0
	 * 
	 * @param type according to UNTDID 5153, typically VAT
	 * @exception IllegalArgumentException if tax code is invalid
	 * 
	 * @see com.klst.edoc.untdid.TaxTypeCode
	 */
	public void setTaxType(String type);
	default void setTaxType(TaxTypeCode code) {
		setTaxType(code.getValue());
	}
	public String getTaxType();

}
