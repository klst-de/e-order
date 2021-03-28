package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.klst.edoc.api.IAmount;

public class Amount implements IAmount {

	static Amount create(BigDecimal amount) {
		return new Amount(null, amount);
	}
	static Amount create(String currencyID, BigDecimal amount) {
		return new Amount(currencyID, amount);
	}
	
	// Der Betrag wird mit zwei Nachkommastellen angegeben. ==> setScale(2, RoundingMode.HALF_UP)
	public static final int SCALE = 2;
	private int scale = SCALE;

	private BigDecimal amount;
	
	private Amount(String currencyID, BigDecimal amount) {
		this.amount = amount;
	}
	
	Amount(String currencyID, int scale, RoundingMode roundingMode, BigDecimal amount) {
		this(currencyID, amount);
		this.scale = scale;
		amount.setScale(this.scale, IAmount.roundingMode);
	}

	// factory
	@Override
	public IAmount createAmount(String currencyID, BigDecimal amount) {
		return create(currencyID, amount);
	}

	@Override
	public BigDecimal getValue(RoundingMode roundingMode) {
		return amount.setScale(SCALE, roundingMode);
	}

	@Override
	public String getCurrencyID() {
		return null;
	}

	@Override
	public String toString() {
		return getCurrencyID()==null ? ""+getValue(roundingMode) : getCurrencyID() + getValue(roundingMode);
	}

}
