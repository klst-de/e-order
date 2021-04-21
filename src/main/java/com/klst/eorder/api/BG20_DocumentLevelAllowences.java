package com.klst.eorder.api;

import java.math.BigDecimal;
import java.util.List;

import com.klst.edoc.api.IAmount;

/**
 * 888: BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
 * <br>
 * 903: BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
 * <br>
 * 318: BG-27 0..n LINE ALLOWANCES / ABSCHLÄGE
 * <br>
 * 326: BG-28 0..n LINE CHARGES / ZUSCHLÄGE
 * <p>
 * Cardinality: 	0..n
 * <br>EN16931-ID: 	BG-20 / BG-21 / BG-27 / BG-28
 * <br>Rule ID: 	
 * <br>Order-X-No: 	888, 903, 318, 326
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 *
 */
public interface BG20_DocumentLevelAllowences extends AllowancesOrChargesFactory {

	/**
	 * add an allowance or charge
	 * 
	 * @param allowanceOrCharge
	 */
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge);
	
	public List<AllowancesAndCharges> getAllowancesAndCharges();

	default void addAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		addAllowanceCharge(createAllowance(amount, baseAmount, percentage));
	}
	default void addCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		addAllowanceCharge(createCharge(amount, baseAmount, percentage));
	}

}
