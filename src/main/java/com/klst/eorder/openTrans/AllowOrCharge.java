package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.ALLOWORCHARGE;
import org.opentrans.xmlschema._2.ALLOWORCHARGEDESCR;
import org.opentrans.xmlschema._2.ALLOWORCHARGEVALUE;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.edoc.untdid.TaxTypeCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.ITaxCategory;

/* implements BG-27 LINE ALLOWANCES, BG-28 LINE CHARGES

    protected BigInteger alloworchargesequence;
    protected List<ALLOWORCHARGENAME> alloworchargename;       NOT USED
     // Kurzname des Zu- oder Abschlages (z.B. Fracht, Verpackung, ...)
    protected String alloworchargetype;                    ==> Reasoncode
    protected List<ALLOWORCHARGEDESCR> alloworchargedescr; ==> ReasonText
    protected ALLOWORCHARGEVALUE alloworchargevalue;       ==> AmountWithoutTax+percentage
     protected Float aocpercentagefactor;                  ==> percentage
     protected Float aocmonetaryamount;                    ==> AmountWithoutTax
     protected AOCORDERUNITSCOUNT aocorderunitscount; // Dreingabe oder eine Draufgabe
     protected String aocadditionalitems;             // Angabe, welche Produkte zusätzlich zur Bestellung mitgeliefert werden.
    protected Float alloworchargebase;                     ==> AssessmentBase
    protected String type;                           required el of{"allowance","surcharge"}

 */
public class AllowOrCharge extends ALLOWORCHARGE implements AllowancesAndCharges {

	private static final String NO_TRADETAX_ELEMENT = "No TradeTax.";
	private static final Logger LOG = Logger.getLogger(AllowOrCharge.class.getName());

	@Override
	public ITaxCategory createTaxCategory(TaxTypeCode taxType, TaxCategoryCode taxCode, BigDecimal taxRate) {
		LOG.warning(NO_TRADETAX_ELEMENT);
		return null;
//		return TradeTax.create(taxType, taxCode, taxRate);
	}

	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}

	static AllowOrCharge create(boolean chargeIndicator, IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return new AllowOrCharge(chargeIndicator, amount, baseAmount, percentage);
	}
	static AllowOrCharge create() {
		return new AllowOrCharge(null);
	}
	// copy factory
	static AllowOrCharge create(ALLOWORCHARGE object) {
		if(object instanceof ALLOWORCHARGE && object.getClass()!=ALLOWORCHARGE.class) {
			// object is instance of a subclass of ALLOWORCHARGE, but not ALLOWORCHARGE itself
			return (AllowOrCharge)object;
		} else {
			return new AllowOrCharge(object); 
		}
	}
	
	private AllowOrCharge(boolean chargeIndicator, IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		setChargeIndicator(chargeIndicator);
		setAmountWithoutTax(amount);
		setAssessmentBase(baseAmount);
		setPercentage(percentage);
	}
	// copy ctor
	private AllowOrCharge(ALLOWORCHARGE doc) {
		SCopyCtor.getInstance().invokeCopy(this, doc);
	}

	public static final String ALLOWANCE = "allowance";
	public static final String CHARGE = "surcharge";
	@Override
	public void setChargeIndicator(boolean isCharge) {
		super.setType(isCharge ? CHARGE : ALLOWANCE);
	}
	@Override
	public boolean isAllowance() {
		return super.getType().equals(ALLOWANCE);
	}
	@Override
	public boolean isCharge() {
		return super.getType().equals(CHARGE);
	}

	@Override
	public void setTaxCategoryCode(String code) {
	}
	@Override
	public TaxCategoryCode getTaxCategoryCode() {
		return null;
	}

	@Override
	public void setTaxPercentage(BigDecimal taxRate) {
	}
	@Override
	public BigDecimal getTaxPercentage() {
		return null;
	}

	@Override
	public void setTaxType(String type) {
	}
	@Override
	public String getTaxType() {
		return null;
	}

	private void setAmountAndPercentage(IAmount amount, BigDecimal percentage) {
		if(amount==null && percentage==null) return;
		ALLOWORCHARGEVALUE acf = getALLOWORCHARGEVALUE();
		if(acf==null) {
			acf = new ALLOWORCHARGEVALUE();
			setALLOWORCHARGEVALUE(acf);
		}
		if(amount!=null) getALLOWORCHARGEVALUE().setAOCMONETARYAMOUNT(amount.getValue().floatValue());
		if(percentage!=null) getALLOWORCHARGEVALUE().setAOCPERCENTAGEFACTOR(percentage.floatValue());
	}

	// BG-27.BT-136 1..1 Betrag des Abschlags , in OT 0..1
	@Override
	public void setAmountWithoutTax(IAmount amount) {
		if(amount==null) return;
		setAmountAndPercentage(amount, null);
	}

	@Override
	public IAmount getAmountWithoutTax() {
		if(super.getALLOWORCHARGEVALUE()==null) return null;
		return getALLOWORCHARGEVALUE().getAOCMONETARYAMOUNT()==null ? null
				: Amount.create(BigDecimal.valueOf(getALLOWORCHARGEVALUE().getAOCMONETARYAMOUNT()));
		// Float f;             BigDecimal.valueOf(f);
	}

	// BG-27.BT-137 0..1 Grundbetrag des Abschlags
	@Override
	public void setAssessmentBase(IAmount amount) {
		if(amount==null) return;
		super.setALLOWORCHARGEBASE(amount.getValue().floatValue());
	}

	@Override
	public IAmount getAssessmentBase() {
		return super.getALLOWORCHARGEBASE()==null ? null : Amount.create(BigDecimal.valueOf(getALLOWORCHARGEBASE()));
	}

	// BG-27.BT-138 0..1 prozentualer Abschlag
	@Override
	public void setPercentage(BigDecimal percentage) {
		if(percentage==null) return;
		setAmountAndPercentage(null, percentage);
	}

	@Override
	public BigDecimal getPercentage() {
		if(super.getALLOWORCHARGEVALUE()==null) return null;
		return getALLOWORCHARGEVALUE().getAOCPERCENTAGEFACTOR()==null ? null
				: BigDecimal.valueOf(getALLOWORCHARGEVALUE().getAOCPERCENTAGEFACTOR());
	}

	// BG-27.BT-139 0..1 Grund für den Abschlag
	// Order-X-No: 898, 913, 325, 333, 169(Item price discount Reason)
	@Override
	public void setReasonText(String text) {
		SCopyCtor.getInstance().add(super.getALLOWORCHARGEDESCR(), new ALLOWORCHARGEDESCR(), text);
	}

	@Override
	public String getReasonText() {
		if(super.getALLOWORCHARGEDESCR().isEmpty()) return null;
		return getALLOWORCHARGEDESCR().get(0).getValue();
	}

	static final String RABATE = "rebate";
	static boolean isRabate(ALLOWORCHARGE aoc) {
		return RABATE.equals(aoc.getALLOWORCHARGETYPE());
	}
	
	// 169: BG-27.BT-140 0..1 Code für den Grund aus UNTDID 5189 Codeliste in CIO, "rebate" in OT
	// 324: Abschlag pro Position                           5189 Allowance or charge identification code
	// 176: Zuschlag pro Einheit  / Item price charge 
	// 332: Zuschlag pro Position / CHARGE                  7161 Special service description code
	/*
	 * Zu- oder Abschlagstyp ALLOW_OR_CHARGE_TYPE
	 * Typisierung, um welche Art von Zu- oder Abschlag es sich handelt (z.B. Fracht, Porto und Verpackung, ...).
	 * Benutzerdefinierter Wert/Code oder abroad,administration, ... ,
	 *  project_bonus,overpackaging,rebate,recycling,small_order,special_work_times,toll
	 */
	@Override
	public void setReasoncode(String code) {
		super.setALLOWORCHARGETYPE(code);
	}
	@Override
	public String getReasoncode() {
		return super.getALLOWORCHARGETYPE();
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder().append("[");
		if(isAllowance()) stringBuilder.append(ALLOWANCE); // BG-27
		if(isCharge()) stringBuilder.append(CHARGE);       // BG-28
		
		stringBuilder.append(", AmountWithoutTax:"); // BG-27.BT-136
		stringBuilder.append(getAmountWithoutTax()==null ? "null" : getAmountWithoutTax());
		stringBuilder.append(", AssessmentBase:");   // BG-27.BT-137
		stringBuilder.append(getAssessmentBase()==null ? "null" : getAssessmentBase());
		stringBuilder.append(", %rate:");            // BG-27.BT-138
		stringBuilder.append(getPercentage()==null ? "null" : getPercentage());
		
//		stringBuilder.append(", tax:");   // BT-102-0
//		stringBuilder.append(getTaxType()==null ? "null" : getTaxType());
//		stringBuilder.append("/");        // BT-102
//		stringBuilder.append(getTaxCategoryCode()==null ? "null" : getTaxCategoryCode());
//		stringBuilder.append(", tax%:");  // BT-103
//		stringBuilder.append(getTaxPercentage()==null ? "null" : getTaxPercentage());
		
		stringBuilder.append(", Reasoncode:"); // BG-27.BT-140
		stringBuilder.append(getReasoncode()==null ? "null" : getReasoncode());
		stringBuilder.append(", ReasonText:"); // BG-27.BT-139
		stringBuilder.append(getReasonText()==null ? "null" : getReasonText());
		
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
