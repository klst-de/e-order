package com.klst.eorder.api;

import java.math.BigDecimal;

import com.klst.edoc.untdid.TaxCategoryCode;

/**
 * LINE VAT INFORMATION
 * <p>
 * A group of business terms providing information about the VAT applicable for the goods and services 
 * <p>
 * Cardinality: 	1..1
 * <br>EN16931-ID: 	BG-30
 * <br>Order-X-No: 	311
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG30_LineVATInformation {

	/**
	 * item VAT category code
	 * <p>
	 * The VAT category code for the item.
	 * <p>
	 * Cardinality: 	1..1 (mandatory)
	 * <br>EN16931-ID: 	BG-30.BT-151, BT-151-0
	 * <br>Order-X-No: 	315
	 * 
	 * @param codeEnum
	 */
/*
               <ram:SpecifiedLineTradeSettlement>
                    <ram:ApplicableTradeTax>
                         <ram:TypeCode>VAT</ram:TypeCode>
                         <ram:CategoryCode>S</ram:CategoryCode>
                         <ram:RateApplicablePercent>20.00</ram:RateApplicablePercent>
                    </ram:ApplicableTradeTax>

 */
	public void setTaxCategory(TaxCategoryCode codeEnum);
	public TaxCategoryCode getTaxCategory(); 
	
	/**
	 * item VAT rate
	 * <p>
	 * The VAT rate, represented as percentage that applies to the item.
	 * <p>
	 * Cardinality: 	0..1 (optional)
	 * <br>EN16931-ID: 	BG-30.BT-152
	 * <br>Order-X-No: 	317
	 * 
	 * @param taxRate
	 */
	public void setTaxRate(BigDecimal taxRate); 
	public BigDecimal getTaxRate(); 

	default void setTaxCategoryAndRate(TaxCategoryCode codeEnum, BigDecimal percent) {
		setTaxCategory(codeEnum);
		if(percent!=null) setTaxRate(percent); 
	}

}
