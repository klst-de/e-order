package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.CopyCtor;
import com.klst.ebXml.reflection.Mapper;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AllowancesAndCharges;

import un.unece.uncefact.codelist.standard.unece.dutyortaxorfeecategorycode.d19b.DutyorTaxorFeeCategoryCodeContentType;
import un.unece.uncefact.codelist.standard.unece.dutytaxfeetypecode.d19b.DutyTaxFeeTypeCodeContentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeAllowanceChargeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IndicatorType;

public class TradeAllowanceCharge extends TradeAllowanceChargeType implements AllowancesAndCharges {
	
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}

	// super member:
//    protected IndicatorType chargeIndicator;
//    protected NumericType sequenceNumeric;
//    protected PercentType calculationPercent;
//    protected AmountType basisAmount;
//    protected QuantityType basisQuantity;
//    protected AmountType actualAmount;
//    protected AllowanceChargeReasonCodeType reasonCode;
//    protected TextType reason;
//    protected TradeTaxType categoryTradeTax;                      TODO

	private static final String NO_TRADETAX_ELEMENT = "No TradeTax. Expected one element.";
	private static final Logger LOG = Logger.getLogger(TradeAllowanceCharge.class.getName());

	static TradeAllowanceCharge create(boolean chargeIndicator, IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		return new TradeAllowanceCharge(chargeIndicator, amount, baseAmount, percentage);
	}
	static TradeAllowanceCharge create() {
		return new TradeAllowanceCharge(null);
	}
	// copy factory
	static TradeAllowanceCharge create(TradeAllowanceChargeType object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof TradeAllowanceChargeType && object.getClass()!=TradeAllowanceChargeType.class) {
			// object is instance of a subclass of TradeAllowanceChargeType, but not TradeAllowanceChargeType itself
			return (TradeAllowanceCharge)object;
		} else {
			return new TradeAllowanceCharge(object); 
		}
	}

	private TradeAllowanceCharge(boolean chargeIndicator, IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		super();
		setChargeIndicator(chargeIndicator);
		setAmountWithoutTax(amount);
		setAssessmentBase(baseAmount);
		setPercentage(percentage);
	}
	// copy ctor
	private TradeAllowanceCharge(TradeAllowanceChargeType tradeAllowanceCharge) {
		super();
		if(tradeAllowanceCharge!=null) {
			CopyCtor.invokeCopy(this, tradeAllowanceCharge);
//			if(getCategoryTradeTax().isEmpty()) {
//				LOG.warning(NO_TRADETAX_ELEMENT);
//			}
			LOG.fine("copy ctor:"+this);
		}
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder().append("[");
		if(isAllowance()) stringBuilder.append("ALLOWANCE");
		if(isCharge()) stringBuilder.append("CHARGE"); // BG-21
		
		stringBuilder.append(", AmountWithoutTax:"); // BT-99
		stringBuilder.append(getAmountWithoutTax()==null ? "null" : getAmountWithoutTax());
		stringBuilder.append(", AssessmentBase:");   // BT-100
		stringBuilder.append(getAssessmentBase()==null ? "null" : getAssessmentBase());
		stringBuilder.append(", %rate:");            // BT-101
		stringBuilder.append(getPercentage()==null ? "null" : getPercentage());
		
//		stringBuilder.append(", tax:");   // BT-102-0
//		stringBuilder.append(getTaxType()==null ? "null" : getTaxType());
//		stringBuilder.append("/");        // BT-102
//		stringBuilder.append(getTaxCategoryCode()==null ? "null" : getTaxCategoryCode());
//		stringBuilder.append(", tax%:");  // BT-103
//		stringBuilder.append(getTaxPercentage()==null ? "null" : getTaxPercentage());
		
		stringBuilder.append(", Reasoncode:"); // BT-104
		stringBuilder.append(getReasoncode()==null ? "null" : getReasoncode());
		stringBuilder.append(", ReasonText:"); // BT-105
		stringBuilder.append(getReasonText()==null ? "null" : getReasonText());
		
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	@Override
	public void setChargeIndicator(boolean indicator) {
		Mapper.set(this, "chargeIndicator", indicator);		
	}
	
	@Override
	public boolean isAllowance() {
		IndicatorType indicator = super.getChargeIndicator();
		return indicator!=null && indicator.isIndicator().equals(AllowancesAndCharges.ALLOWANCE);
	}

	@Override
	public boolean isCharge() {
		IndicatorType indicator = super.getChargeIndicator();
		return indicator!=null && indicator.isIndicator().equals(AllowancesAndCharges.CHARGE);
	}

	// BT-92, BT-99 (mandatory) Document level allowance/charge amount
	@Override
	public void setAmountWithoutTax(IAmount amount) {
		if(amount!=null) super.setActualAmount((Amount)amount);
	}

	@Override
	public IAmount getAmountWithoutTax() {
		return super.getActualAmount()==null ? null : Amount.create(getActualAmount());
	}

	// BT-93, BT100 (optional) Document level allowance/charge base amount
	@Override
	public void setAssessmentBase(IAmount amount) {
		if(amount!=null) super.setBasisAmount((Amount)amount);
	}

	@Override
	public IAmount getAssessmentBase() {
		return super.getBasisAmount()==null ? null : Amount.create(getBasisAmount());
	}

	// BT-94, BT101 (optional) Document level allowance/charge percentage
	@Override
	public void setPercentage(BigDecimal percentage) {
		if(percentage==null) return;
		super.setCalculationPercent(new Percent(percentage));
	}

	@Override
	public BigDecimal getPercentage() {
		return super.getCalculationPercent()==null ? null : getCalculationPercent().getValue();
	}

	// BT-95-0, BT-102-0
	@Override
	public void setTaxType(String type) {
		// TODO type muss in enum DutyTaxFeeTypeCodeContentType sein!!!
		Mapper.newFieldInstance(this, "categoryTradeTax", type);
		Mapper.set(getCategoryTradeTax(), "typeCode", DutyTaxFeeTypeCodeContentType.fromValue(type));	
	}
	@Override // liefert in CII immer "VAT", in CIO andere aus DutyTaxFeeTypeCodeContentType
	public String getTaxType() {
		if(super.getCategoryTradeTax()==null) return null;
		TradeTax tradeTax = TradeTax.create(getCategoryTradeTax());
		return tradeTax.getTaxType();
	}

	// BT-95, BT-102 1..1 (mandatory) Document level allowance/charge VAT category code
//	@Override
//	public void setTaxCategoryCode(TaxCategoryCode code) {
//		setTaxCategoryCode(code.getValue());
//	}
	@Override
	public void setTaxCategoryCode(String code) {
		Mapper.newFieldInstance(this, "categoryTradeTax", code);
		Mapper.set(getCategoryTradeTax(), "typeCode", DutyorTaxorFeeCategoryCodeContentType.fromValue(code));		
	}

	@Override
	public TaxCategoryCode getTaxCategoryCode() {
		if(super.getCategoryTradeTax()==null) return null;
		TradeTax tradeTax = TradeTax.create(getCategoryTradeTax());
		return tradeTax.getTaxCategoryCode();
	}

	// BT-96, BT-103 0..1 Document level allowance/charge VAT rate
	@Override
	public void setTaxPercentage(BigDecimal percentage) {
		if(percentage==null) return;
		Mapper.newFieldInstance(this, "categoryTradeTax", percentage);
		Mapper.set(getCategoryTradeTax(), "rateApplicablePercent", percentage);		
	}

	@Override
	public BigDecimal getTaxPercentage() {
		if(super.getCategoryTradeTax()==null) return null;
		TradeTax tradeTax = TradeTax.create(getCategoryTradeTax());
		return tradeTax.getTaxPercentage();
	}

	// BT-97, BT-104 0..1 Document level allowance/charge reason
	@Override
	public void setReasonText(String text) {
		if(text==null) return;		
		Mapper.set(this, "reason", text);
	}

	@Override
	public String getReasonText() {
		return super.getReason()==null? null : getReason().getValue();
	}

	// BT-98, BT-103 0..1 Document level allowance/charge reason code
	@Override
	public void setReasoncode(String code) {
		if(code==null) return; 
		Mapper.set(this, "reasonCode", code);
	}

	@Override
	public String getReasoncode() {
		return super.getReasonCode()==null? null : getReasonCode().getValue();
	}

}
