package com.klst.eorder.api;

import java.math.BigDecimal;

import com.klst.edoc.api.IAmount;

/**
 * Common Interface for ALLOWANCES and CHARGES
 *
 * <p>
 * 888: BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
 * <br>
 * 903: BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
 * <br>
 * 318: BG-27 0..n LINE ALLOWANCES / ABSCHLÄGE
 * <br>
 * 326: BG-28 0..n LINE CHARGES / ZUSCHLÄGE
 * <br>
 * 162: BG-29.BT-147 0..1 Item price discount aka ALLOWANCE
 * <p>
 * Cardinality: 	0..n
 * <br>EN16931-ID: 	BG-20 / BG-21 / BG-27 / BG-28 / BG-29.BT-147
 * <br>Rule ID: 	BR-31, BR-36
 * <br>Order-X-No: 	888, 903, 318, 326, 162
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface AllowancesAndCharges extends AllowancesOrChargesFactory, ITaxCategory {
	
	public static final boolean ALLOWANCE = false;
	public static final boolean CHARGE = true;

	/**
	 * binary indicator for ALLOWANCES resp. CHARGES
	 * <p>
	 * EN16931-ID: BG-20-00, BG-21-00, BG-27-00, BG-28-00, BG-29.BT-147
	 * <p>
	 * Order-X-No: 890, 905, 320, 328, 164
	 * 
	 * @param indicator - use {@link #ALLOWANCE} resp. {@link #CHARGE}
	 */
	public void setChargeIndicator(boolean indicator);
	
	/**
	 * check for ALLOWANCES
	 * 
	 * @return true for Order-X-No 890, 320, 164
	 * <br> otherwise false (905, 328 or indicator is not set)
	 */
	public boolean isAllowance();
	/**
	 * check for CHARGES
	 * 
	 * @return true for Order-X-No 905, 328
	 * <br> otherwise false (890, 320, 164 or indicator is not set)
	 */
	public boolean isCharge();
	
	/**
	 * The amount of an allowance or charge, without VAT.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <p>
	 * EN16931-ID: BG-20.BT-92, BG-21.BT-99, BG-27.BT-136, BG-28.BT-141
	 * <br>
	 * Order-X-No: 896, 911, 323, 331, 167
	 * 
	 * @param amount
	 */
	/*
	 * Geschäftsregel: BR-31 Abschläge auf Dokumentenebene
	 * Jeder Abschlag auf Dokumentenebene (BG-20) muss einen Abschlagsbetrag auf der Dokumentenebene (BT-92) haben.
	 * <p>
	 * Geschäftsregel: BR-36 Zuschläge auf Dokumentenebene
	 * Jeder Zuschlag auf Dokumentenebene (BG-21) muss einen Betrag des Zuschlags auf der Dokumentenebene (BT99) haben.
	 */
	// Amount umbenennen wg. Namenskollision in UBL // in AmountWithoutTax
	public void setAmountWithoutTax(IAmount amount);
	public IAmount getAmountWithoutTax(); 

	/**
	 * (optional) allowance or charge base amount
	 * <p>
	 * The base amount that may be used, in conjunction with the allowance/charge percentage, 
	 * to calculate the document level allowance/charge amount.
	 * <p>
	 * (for 166): The monetary value that is the basis on which this trade price discount 
	 * is calculated.

	 * <p>
	 * EN16931-ID: BG-20.BT-93, BG-21.BT-100, BG-27.BT-137, BG-28.BT-142
	 * <br>
	 * Order-X-No: 893, 908, 322, 330, 166
	 * 
	 * @param amount
	 */
	public void setAssessmentBase(IAmount amount);
	public IAmount getAssessmentBase(); 

	/**
	 * (optional) allowance or charge percentage
	 * <p>
	 * The percentage that may be used, in conjunction with the allowance/charge base amount, 
	 * to calculate the allowance/charge amount.
	 * <p>
	 * (for 166): The percentage applied to calculate this trade price discount.
	 * <p>
	 * EN16931-ID: BG-20.BT-94, BG-21.BT-101, BG-27.BT-138, BG-28.BT-143
	 * <br>
	 * Order-X-No: 892, 907, 321, 329, 165
	 * 
	 * @param percentage
	 */
	public void setPercentage(BigDecimal percentage);
	public BigDecimal getPercentage();

//ITaxCategory:
//	// BT-95-0, BT-102-0 (CII)
//	/**
//	 * {@inheritDoc}
//	 * 
//	 * @see com.klst.untdid.codelist.TaxTypeCode
//	 */
//	@Override
//	public void setTaxType(String code);
//	/**
//	 * {@inheritDoc}
//	 * 
//	 * @see com.klst.untdid.codelist.TaxTypeCode
//	 */
//	@Override
//	public String getTaxType();
//	
//	/**
//	 * BT-95, BT-102 (mandatory) Document level allowance/charge VAT category code
//	 * <p>
//	 * A coded identification of what VAT category applies to the document level allowance/charge.
//	 * 
//	 * <br>Rule ID: 	BR-32
//	 * <br>Request ID: 	R15, R16, R45, R48
//	 * 
//	 * @param code
//	 */
//	/* Codeliste: UNTDID 5153 Eingeschränkt
//	 * In der EN 16931 wird nur die Steuerart „Umsatzsteuer“ mit dem Code „VAT“ unterstützt.
//	 * 
//	 * Sollen andere Steuerarten angegeben wie 
//	 * beispielsweise eine Versicherungssteuer oder eine Mineralölsteuer werden, 
//	 * muss das EXTENDED Profil genutzt werden. 
//	 * Der Code für die Steuerart muss dann der Codeliste UNTDID 5153 entnommen werden.
//	 * https://service.unece.org/trade/untdid/d01b/trsd/trsdtax.htm
//	 * 
//	 * Geschäftsregel: BR-32 Abschläge auf Dokumentenebene
//	 *  Jeder Abschlag auf Dokumentenebene (BG-20) muss einen Code für die für diesen Abschlag 
//	 *  geltende Umsatzsteuer auf Dokumentenebene (BT-95) haben
//	 */
//	@Override
//	public void setTaxCategoryCode(String code);
//	@Override
//	public void setTaxCategoryCode(TaxCategoryCode code);
//	@Override
//	public TaxCategoryCode getTaxCategoryCode();
//	
//	/**
//	 * BT-96, BT-103 0..1 Document level allowance/charge VAT rate
//	 * <p>
//	 * The VAT rate, represented as percentage that applies to the document level allowance/charge.
//	 * 
//	 * <br>Request ID: 	R15, R16, R45, R48
//	 * 
//	 * @param percentage
//	 */
//	@Override
//	public void setTaxPercentage(BigDecimal percentage);
//	@Override
//	public BigDecimal getTaxPercentage();

	/**
	 * (optional) allowance or charge reason
	 * <p>
	 * The reason for the allowance/charge, expressed as text.
	 * <p>
	 * EN16931-ID: BG-20.BT-97, BG-21.BT-104, BG-27.BT-139, BG-28.BT-144
	 * <br>
	 * Order-X-No: 898, 913, 325, 333, 169
	 * 
	 * @param text
	 */
	/*

. Geschäftsregel: BR-33 Abschläge auf Dokumentenebene
Jeder Abschlag auf Dokumentenebene (BG-20) muss einen Grund für diesen Abschlag auf Dokumentenebene (BT-97) 
oder einen Code für den Grund für diesen Abschlag auf Dokumentenebene (BT-98) haben.

. Geschäftsregel: BR-CO-22 Zuschläge auf Dokumentenebene
Jeder Zuschlag auf Dokumentenebene (BG-21) muss einen Grund für den Zuschlag auf Dokumentenebene (BT-104) 
oder einen Code des Grundes für den Zuschlag auf Dokumentenebene (BT-105) oder beides enthalten.

. Geschäftsregel: BR-CO-21 Abschläge auf Dokumentenebene
Jeder Abschlag auf Dokumentenebene (BG-20) muss einen Grund für diesen Abschlag auf Dokumentenebene (BT-97) 
oder einen Code für den Grund für diesen Abschlag auf Dokumentenebene (BT-98) oder beides enthalten.

. Geschäftsregel: BR-CO-5 Abschläge auf Dokumentenebene
Der Code des Grundes für den Abschlag auf Dokumentenebene (BT-98) 
und der Grund für den Abschlag auf Dokumentenebene (BT-97) müssen dieselbe Zuschlagsart anzeigen.

. Geschäftsregel: BR-38 Zuschläge auf Dokumentenebene
Jeder Zuschlag auf Dokumentenebene (BG-21) muss einen Grund für den Zuschlag auf Dokumentenebene (BT-104) 
oder einen Code des Grundes für den Zuschlag auf Dokumentenebene (BT-105) haben.

	 */
	public void setReasonText(String text);
	public String getReasonText();
	
	/**
	 * (optional) allowance or charge reason code
	 * <p>
	 * The reason for the allowance/charge, expressed as a code.
	 * <br>Use entries of the UNTDID 5189 code list. 
	 * The allowance/charge reason code and the allowance/charge reason 
	 * shall indicate the same allowance/charge reason.
	 * <p>
	 * EN16931-ID: BG-20.BT-98, BG-21.BT-103, BG-27.BT-140, BG-28.BT-144
	 * <br>
	 * Order-X-No: 897, 912, 324, 332, 168
	 * 
	 * @param code as String
	 */
	/*

. Codeliste: UNTDID 7161 Vollständige Liste
AA    Advertising
AAA   Telecommunication
AAC   Technical modification
...
ABL   Additional packaging
...
ZZZ   Mutually defined

. Codeliste: UNTDID 5189 Eingeschränkt
Include PEPPOL subset:
41 - Bonus for works ahead of schedule
42 - Other bonus
60 - Manufacturer’s consumer discount
62 - Due to military status
63 - Due to work accident
64 - Special agreement
65 - Production error discount
66 - New outlet discount
67 - Sample discount
68 - End-of-range discount
70 - Incoterm discount
71 - Point of sales threshold allowance
88 - Material surcharge/deduction
95 - Discount
100 - Special rebate
102 - Fixed long term
103 - Temporary
104 - Standard
105 - Yearly turnover

	 */
	public void setReasoncode(String code);
	public String getReasoncode();
	
}
