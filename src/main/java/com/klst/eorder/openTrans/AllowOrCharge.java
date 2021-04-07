package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.ALLOWORCHARGE;
import org.opentrans.xmlschema._2.ALLOWORCHARGEDESCR;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.edoc.untdid.TaxTypeCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.ITaxCategory;
import com.klst.eorder.openTrans.reflection.Mapper;

/* implements BG-27 LINE ALLOWANCES, BG-28 LINE CHARGES
 * 
 */
public class AllowOrCharge extends ALLOWORCHARGE implements AllowancesAndCharges {

	@Override
	public ITaxCategory createTaxCategory(TaxTypeCode taxType, TaxCategoryCode taxCode, BigDecimal taxRate) {
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

	private static final String NO_TRADETAX_ELEMENT = "No TradeTax. Expected one element.";
	private static final Logger LOG = Logger.getLogger(AllowOrCharge.class.getName());

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
		super();
		setChargeIndicator(chargeIndicator);
		setAmountWithoutTax(amount);
		setAssessmentBase(baseAmount);
		setPercentage(percentage);
	}
	// copy ctor
	private AllowOrCharge(ALLOWORCHARGE doc) {
		super();
		if(doc!=null) {
			SCopyCtor.getInstance().invokeCopy(this, doc);
		}
	}

	public static final String ALLOWANCE = "allowance";
	public static final String CHARGE = "surcharge";
	@Override
	public void setChargeIndicator(boolean isCharge) {
		super.setALLOWORCHARGETYPE(isCharge ? CHARGE : ALLOWANCE);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public TaxCategoryCode getTaxCategoryCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxPercentage(BigDecimal taxRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getTaxPercentage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTaxType() {
		// TODO Auto-generated method stub
		return null;
	}

	// BG-27.BT-136 1..1 Betrag des Abschlags , in OT 0..1
	@Override
	public void setAmountWithoutTax(IAmount amount) {
		Mapper.newFieldInstance(this, "alloworchargevalue", amount);
		Mapper.set(getALLOWORCHARGEVALUE(), "aocmonetaryamount", amount.getValue().floatValue());
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
		Mapper.set(this, "alloworchargebase", amount.getValue().floatValue());
	}

	@Override
	public IAmount getAssessmentBase() {
		return super.getALLOWORCHARGEBASE()==null ? null : Amount.create(BigDecimal.valueOf(getALLOWORCHARGEBASE()));
	}

	// BG-27.BT-138 0..1 prozentualer Abschlag
	@Override
	public void setPercentage(BigDecimal percentage) {
		Mapper.newFieldInstance(this, "alloworchargevalue", percentage);
		Mapper.set(getALLOWORCHARGEVALUE(), "aocpercentagefactor", percentage.floatValue());	
	}

	@Override
	public BigDecimal getPercentage() {
		if(super.getALLOWORCHARGEVALUE()==null) return null;
		return getALLOWORCHARGEVALUE().getAOCPERCENTAGEFACTOR()==null ? null
				: BigDecimal.valueOf(getALLOWORCHARGEVALUE().getAOCPERCENTAGEFACTOR());
	}

	// BG-27.BT-139 0..1 Grund für den Abschlag
	@Override
	public void setReasonText(String text) {
		Mapper.add(super.getALLOWORCHARGEDESCR(), new ALLOWORCHARGEDESCR(), text);
	}

	@Override
	public String getReasonText() {
		if(super.getALLOWORCHARGEDESCR().isEmpty()) return null;
		return getALLOWORCHARGEDESCR().get(0).getValue();
	}

	// BG-27.BT-140 0..1 Code für den Grund aus UNTDID 5189 Codeliste
	/*
	 * Zu- oder Abschlagstyp ALLOW_OR_CHARGE_TYPE
	 * Typisierung, um welche Art von Zu- oder Abschlag es sich handelt (z.B. Fracht, Porto und Verpackung, ...).
	 */
	@Override
	public void setReasoncode(String code) {
		Mapper.set(this, "alloworchargetype", code);		
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
		
		stringBuilder.append(", tax:");   // BT-102-0
		stringBuilder.append(getTaxType()==null ? "null" : getTaxType());
		stringBuilder.append("/");        // BT-102
		stringBuilder.append(getTaxCategoryCode()==null ? "null" : getTaxCategoryCode());
		stringBuilder.append(", tax%:");  // BT-103
		stringBuilder.append(getTaxPercentage()==null ? "null" : getTaxPercentage());
		
		stringBuilder.append(", Reasoncode:"); // BG-27.BT-140
		stringBuilder.append(getReasoncode()==null ? "null" : getReasoncode());
		stringBuilder.append(", ReasonText:"); // BG-27.BT-139
		stringBuilder.append(getReasonText()==null ? "null" : getReasonText());
		
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
