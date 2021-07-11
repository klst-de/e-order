package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UnitPriceAmount extends Amount {

	// in EN 16931-1:2017/A1:2019 + AC:2020 entfällt die Einschränkung:
	//  „Typ repräsentiert eine Fließkommazahl ohne Limitierung der Anzahl an Nachkommastellen.“ 
	public static final int SCALE = 4;
	
	public UnitPriceAmount(BigDecimal amount) {
		this(null, amount);
	}

	public UnitPriceAmount(String currencyID, BigDecimal amount) {
		super(currencyID, SCALE, RoundingMode.HALF_UP, amount);
	}

}
