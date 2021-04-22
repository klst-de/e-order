package com.klst.eorder.api;

/**
 * 318: BG-27 0..n LINE ALLOWANCES / ABSCHLÄGE
 * <p>
 * A group of business terms providing information about allowances applicable 
 * to the individual order line.
 * 
 * @see BG20_DocumentLevelAllowences
 * @see AllowancesAndCharges
 * 319: {@link AllowancesAndCharges#isAllowance()}
 * 320: {@link AllowancesAndCharges#ALLOWANCE}
 * 321: {@link AllowancesAndCharges#getPercentage()}
 * 322: {@link AllowancesAndCharges#getAssessmentBase()}
 * 323: {@link AllowancesAndCharges#getAmountWithoutTax()}
 * 324: {@link AllowancesAndCharges#getReasonText()}
 * 325: {@link AllowancesAndCharges#getReasoncode()}
 */
public interface BG27_LineLevelAllowences extends BG20_DocumentLevelAllowences {

}
