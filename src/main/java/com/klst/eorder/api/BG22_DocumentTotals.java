package com.klst.eorder.api;

import com.klst.edoc.api.IAmount;

/**
 * BG-22 DOCUMENT TOTALS
 * <p>
 * A group of business terms providing the monetary totals
 * <p>
 * Cardinality: 	1..1
 * <br>EN16931-ID: 	BG-22
 * <br>Rule ID: 	
 * <br>Request ID: 	R40
 * 
 * @see <a href="https://standards.cen.eu">standards.cen.eu</a> (en)EN_16931_1_2017 for rule and request IDs
 */
public interface BG22_DocumentTotals {

	/**
	 * mandatory total amounts of the order
	 * 
	 * @param lineNet : Sum of line net amount
	 * @param taxExclusive : total amount without VAT, aka Tax Basis
	 * @param taxInclusive : total amount with VAT, aka Grand Total
	 */
	// factory:
	public BG22_DocumentTotals createTotals(IAmount lineNet, IAmount taxExclusive, IAmount taxInclusive);
/*
			<ram:SpecifiedTradeSettlementHeaderMonetarySummation>
				<ram:LineTotalAmount>310.00</ram:LineTotalAmount>                <!-- LineNetTotal
				<ram:ChargeTotalAmount>21.00</ram:ChargeTotalAmount>             <!-- ChargesTotal
				<ram:AllowanceTotalAmount>31.00</ram:AllowanceTotalAmount>       <!-- AllowancesTotal
				<ram:TaxBasisTotalAmount>300.00</ram:TaxBasisTotalAmount>        <!-- TotalTaxExclusive
				<ram:TaxTotalAmount currencyID="EUR">60.00</ram:TaxTotalAmount>  <!-- TaxTotal
				<ram:GrandTotalAmount>360.00</ram:GrandTotalAmount>              <!-- TotalTaxInclusive
			</ram:SpecifiedTradeSettlementHeaderMonetarySummation>

 */
	
	/**
	 * Order Sum of lines Total Amount
	 * <p>
	 * Be careful if there are document level Charges or allowances 
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BT-106
	 * <br>Rule ID:     BR-12 , BR-CO-10      	
	 * <br>Request ID: 	R40
	 * 
	 * @return Amount
	 */
	public IAmount getLineNetTotal(); 
	
	/**
	 * Total amount without VAT, aka Tax Basis
	 * <p>
	 * The total amount of the Invoice without VAT.
	 * The total amount without VAT is the Sum of line net amount 
	 *     minus Sum of allowances on document level plus Sum of charges on document level.
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BT-109
	 * <br>Rule ID:     BR-13 , BR-CO-13      	
	 * <br>Request ID: 	R40
	 * 
	 * @return Amount
	 */
	public IAmount getTotalTaxExclusive();
	
	/**
	 * Invoice total amount with VAT
	 * <p>
	 * The total amount of the Invoice with VAT.
	 * The Invoice total amount with VAT is the Invoice total amount without VAT plus the Invoice total VAT amount.
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BT-112
	 * <br>Rule ID:     BR-14 , BR-CO-15      	
	 * <br>Request ID: 	R40, R67
	 * 
	 * @return Amount
	 */
//	public IAmount getInvoiceTotalTaxInclusive();
	public IAmount getTotalTaxInclusive();
	
	/**
	 * Amount due for payment
	 * <p>
	 * The outstanding amount that is requested to be paid.
	 * This amount is the Invoice total amount with VAT minus the paid amount that has been paid in advance.
	 * <p>
	 * The amount is zero in case of a fully paid Invoice. 
	 * The amount may be negative in that case the Seller owes the amount to the Buyer.
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BT-115
	 * <br>Rule ID:     BR-15 , BR-CO-16      	
	 * <br>Request ID: 	R40, R59, R68
	 *
	 * @return Amount
	 */
//	public IAmount getDuePayable(); 
	
	/**
	 * Sum of all allowances on document level in the Invoice.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>ID: BT-107
	 * <br>Req.ID: R19, R40
	 * 
	 * @param amount , Allowances on line level are included in the Invoice line net amount which is summed up into the Sum of Invoice line net amount.
	 */
	public void setAllowancesTotal(IAmount amount);
	public IAmount getAllowancesTotal(); 
	
	/**
	 * Sum of all charges on document level in the Invoice.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>ID: BT-108
	 * <br>Req.ID: R19,R40
	 * 
	 * @param amount , Charges on line level are included in the Invoice line net amount which is summed up into the Sum of Invoice line net amount.
	 */
	public void setChargesTotal(IAmount amount);
	public IAmount getChargesTotal(); 
	
	/**
	 * The total VAT amount for the Invoice.
	 * <p>
	 * The Invoice total VAT amount is the sum of all VAT category tax amounts.
	 * <p>
	 * Cardinality:     0..1 (optional)
	 * <br>EN16931-ID: 	BT-110
	 * <br>Rule ID:     BR-53 , BR-CO-14      	
	 * <br>Req.ID:      R40, R49
	 * 
	 * @return Amount
	 */
//	public IAmount getInvoiceTax(); 
//	public void setInvoiceTax(IAmount amount);
	public void setTaxTotal(IAmount amount); 
	public IAmount getTaxTotal(); 
	
	/**
	 * Invoice total VAT amount in accounting currency
	 * <p>
	 * The VAT total amount expressed in the accounting currency accepted or required in the country of the Seller.
	 * <p>
	 * To be used when the VAT accounting currency (BT-6) differs from the Invoice currency code (BT-5) 
	 * in accordance with article 230 of Directive 2006/112 / EC on VAT.
	 * The VAT amount in accounting currency is not used in the calculation of the Invoice totals.
	 * <p>
	 * Cardinality: 	0..1 / 1..2 ZUGFeRD-2.0-Spezifikation-TA page 117
	 * <br>EN16931-ID: 	BT-111
	 * <br>Rule ID:     BR-53 , BR-CO-14      	
	 * <br>Request ID: 	R54
	 *
	 * @return Amount
	 */
//	public IAmount getInvoiceTaxInAccountingCurrency(); 
//	public void setInvoiceTaxInAccountingCurrency(IAmount amount);
	
	/**
	 * The sum of amounts which have been paid in advance.
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>ID: BT-113
	 * <br>Req.ID: R40, R66 
	 * 
	 * @param amount , This amount is subtracted from the invoice total amount with VAT to calculate the amount due for payment.                                                                
	 */
//	public void setPrepaid(IAmount amount);
//	public IAmount getPrepaid(); 
	
	/**
	 * Rounding amount  
	 * <p>
	 * Cardinality: 0..1 (optional)
	 * <br>ID: BT-114
	 * <br>Req.ID: R40
	 * 
	 * @param amount , The amount to be added to the invoice total to round the amount to be paid.
	 */
//	public void setRounding(IAmount amount);
//	public IAmount getRounding(); 
	
}
