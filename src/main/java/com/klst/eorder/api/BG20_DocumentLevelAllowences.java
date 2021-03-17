package com.klst.eorder.api;

import java.math.BigDecimal;
import java.util.List;

import com.klst.edoc.api.IAmount;

/**
 * BG-20 0..n DOCUMENT LEVEL ALLOWANCES / ABSCHLÄGE
 * <br>
 * BG-21 0..n DOCUMENT LEVEL CHARGES / ZUSCHLÄGE
 * <br>
 * BG-27 0..n LINE ALLOWANCES / ABSCHLÄGE
 * <br>
 * BG-28 0..n LINE CHARGES / ZUSCHLÄGE
 * <p>
 * Cardinality: 	0..n
 * <br>EN16931-ID: 	BG-20 / BG-21 / BG-27 / BG-28
 * <br>Rule ID: 	
 * <br>Request ID: 	R15, R18
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
/* example 
           <ram:ApplicableHeaderTradeSettlement>
           ...
              <ram:SpecifiedTradeAllowanceCharge>
                    <ram:ChargeIndicator>
                         <udt:Indicator>false</udt:Indicator>
                    </ram:ChargeIndicator>
                    <ram:CalculationPercent>10.00</ram:CalculationPercent>
                    <ram:BasisAmount>310</ram:BasisAmount>
                    <ram:ActualAmount>31.00</ram:ActualAmount>
                    <ram:ReasonCode>64</ram:ReasonCode>
                    <ram:Reason>SPECIAL AGREEMENT</ram:Reason>
                    <ram:CategoryTradeTax>
                         <ram:TypeCode>VAT</ram:TypeCode>
                         <ram:CategoryCode>S</ram:CategoryCode>
                         <ram:RateApplicablePercent>20.00</ram:RateApplicablePercent>
                    </ram:CategoryTradeTax>
               </ram:SpecifiedTradeAllowanceCharge>
               <ram:SpecifiedTradeAllowanceCharge>
                    <ram:ChargeIndicator>
                         <udt:Indicator>false</udt:Indicator>
                    </ram:ChargeIndicator>
                    <ram:CalculationPercent>10.00</ram:CalculationPercent>
                    <ram:BasisAmount>210.00</ram:BasisAmount>
                    <ram:ActualAmount>21.00</ram:ActualAmount>
                    <ram:ReasonCode>79</ram:ReasonCode>
                    <ram:Reason>FREIGHT SERVICES</ram:Reason>
                    <ram:CategoryTradeTax>
                         <ram:TypeCode>VAT</ram:TypeCode>
                         <ram:CategoryCode>S</ram:CategoryCode>
                         <ram:RateApplicablePercent>20.00</ram:RateApplicablePercent>
                    </ram:CategoryTradeTax>
               </ram:SpecifiedTradeAllowanceCharge>
...
 */
public interface BG20_DocumentLevelAllowences extends AllowancesOrChargesFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage);	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage);
	
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
