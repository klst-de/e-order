package com.klst.eorder.impl;

import java.math.BigDecimal;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.edoc.untdid.TaxTypeCode;
import com.klst.eorder.api.ITaxCategory;

import un.unece.uncefact.codelist.standard.unece.dutyortaxorfeecategorycode.d20a.DutyorTaxorFeeCategoryCodeContentType;
import un.unece.uncefact.codelist.standard.unece.dutytaxfeetypecode.d20a.DutyTaxFeeTypeCodeContentType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.TaxCategoryCodeType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.TaxTypeCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradeTaxType;

public class TradeTax extends TradeTaxType implements ITaxCategory {

	/**
	 * {@inheritDoc}
	 */
	// implements ITaxCategoryFactory
	@Override
	public ITaxCategory createTaxCategory(TaxTypeCode taxType, TaxCategoryCode taxCode, BigDecimal taxRate) {
		return create(taxType, taxCode, taxRate);
	}
	static TradeTax create(TaxTypeCode taxType, TaxCategoryCode taxCode, BigDecimal taxRate) {
		return new TradeTax(taxType.getValue(), taxCode, taxRate);
	}

	static TradeTax create() {
		return create((TradeTaxType)null);
	}
	// copy factory
	static TradeTax create(TradeTaxType object) {
		// @see https://stackoverflow.com/questions/2699788/java-is-there-a-subclassof-like-instanceof
		if(object instanceof TradeTaxType && object.getClass()!=TradeTaxType.class) {
			// object is instance of a subclass of TradeTaxType, but not TradeTaxType itself
			return (TradeTax)object;
		} else {
			return new TradeTax(object); 
		}
	}

//	private static final Logger LOG = Logger.getLogger(TradeTax.class.getName());

	private TradeTax(Amount taxableAmount, Amount taxAmount, TaxCategoryCode taxCode, BigDecimal taxRate) {
		setTaxBaseAmount(taxableAmount);
		setCalculatedTaxAmount(taxAmount);
		setTaxCategoryCodeAndRate(taxCode, taxRate);
		
	}
	
	private TradeTax(String taxType, TaxCategoryCode taxCode, BigDecimal taxRate) {
		super();
		setTaxType(taxType);
		setTaxCategoryCode(taxCode);
		setTaxPercentage(taxRate);
	}
	
	// copy ctor
	private TradeTax(TradeTaxType object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[TaxBaseAmount:");
		stringBuilder.append(getTaxBaseAmount()==null ? "null" : getTaxBaseAmount());
		stringBuilder.append(", CalculatedTaxAmount:");
		stringBuilder.append(getCalculatedTaxAmount()==null ? "null" : getCalculatedTaxAmount());
		stringBuilder.append(", TaxType:");
		stringBuilder.append(getTaxType()==null ? "null" : getTaxType());
		stringBuilder.append(", TaxCategoryCode:");
		stringBuilder.append(getTaxCategoryCode()==null ? "null" : getTaxCategoryCode());
		stringBuilder.append(", %rate:");
		stringBuilder.append(getTaxPercentage()==null ? "null" : getTaxPercentage());
//		if(this.getTaxPointDate()!=null) {
//			stringBuilder.append(", TaxPointDate:");                   // BT-7 0..1
//			stringBuilder.append(this.getTaxPointDateAsTimestamp());			
//		}
		if(this.getTaxPointDateCode()!=null) {
			stringBuilder.append(", TaxPointDateCode:");               // BT-8 0..1
			stringBuilder.append(this.getTaxPointDateCode());			
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	void setTaxPointDateCode(String code) {
		if(code==null) return;		
		SCopyCtor.getInstance().set(this, "dueDateTypeCode", code);
	}
	String getTaxPointDateCode() {
		return super.getDueDateTypeCode()==null ? null : getDueDateTypeCode().getValue();		
	}
	
	// BT-116 1..1 BasisAmount Steuerbasisbetrag
	private void setTaxBaseAmount(IAmount amount) {
		if(amount!=null) super.setBasisAmount((Amount)amount);
	}
	private IAmount getTaxBaseAmount() {
		return super.getBasisAmount()==null ? null : Amount.create(getBasisAmount());
	}

	// BT-117 1..1 CalculatedAmount Kategoriespezifischer Steuerbetrag
	private void setCalculatedTaxAmount(IAmount amount) {
		if(amount!=null) super.setCalculatedAmount((Amount)amount);
	}
	private IAmount getCalculatedTaxAmount() {
		return super.getCalculatedAmount()==null ? null : Amount.create(getCalculatedAmount());
	}

	// ALLOWANCES (BG-20.BT-95-0) and CHARGES (BG-21.BT-102-0)
	// BG-23.BT-118-0
	@Override
	public void setTaxType(String code) {
		if(code==null) return;		
//		SCopyCtor.getInstance().set(this, "typeCode", code);
		TaxTypeCodeType ttc = new TaxTypeCodeType();
		ttc.setValue(DutyTaxFeeTypeCodeContentType.fromValue(code));
		super.setTypeCode(ttc);
	}
	@Override
	public String getTaxType() {
		return super.getTypeCode()==null ? null : super.getTypeCode().getValue().value();
	}

	// BT-95, BT-102 (mandatory) Document level allowance/charge VAT category code
	// BG-23.BT-118 (mandatory)
	@Override
	public void setTaxCategoryCode(String code) {
		if(code==null) return;		
//		SCopyCtor.getInstance().set(this, "categoryCode", code);
		TaxCategoryCodeType tcc = new TaxCategoryCodeType();
		tcc.setValue(DutyorTaxorFeeCategoryCodeContentType.fromValue(code));
		super.setCategoryCode(tcc);
	}
	@Override
	public TaxCategoryCode getTaxCategoryCode() {	
		return TaxCategoryCode.getEnum(super.getCategoryCode().getValue().value());
	}

	/* BT-118 1..1 CategoryCode
	 * BT-119 0..1 RateApplicablePercent, wg BR-DE-14 1.1
	 * 
	 * (non-Javadoc)
	 * @see com.klst.einvoice.CoreInvoiceVatBreakdown#setTaxCategoryAndRate(com.klst.untdid.codelist.TaxCategoryCode, java.math.BigDecimal)
	 */
//	@Override
	public void setTaxCategoryCodeAndRate(TaxCategoryCode codeEnum, BigDecimal taxRate) {
		setTaxCategoryAndRate(TaxTypeCode.VAT, codeEnum, taxRate);
	}
	/* 
	 * BT-118-0 TypeCode Code der Umsatzsteuerkategorie, Hinweis: Fester Wert = "VAT"
	 * BT-118   CategoryCode
	 * BT-119   RateApplicablePercent, EN16931 0..1 (optional), wg BR-DE-14 1.1
	 */
	void setTaxCategoryAndRate(String type, TaxCategoryCode code, BigDecimal rate) {
		setTaxType(type);	
		setTaxCategoryCode(code.getValue());
		setTaxPercentage(rate);
	}

	// BT-96, BT-103 0..1 Document level allowance/charge VAT rate
	// BG-23.BT-119
	@Override
	public void setTaxPercentage(BigDecimal taxRate) {
		if(taxRate!=null) super.setRateApplicablePercent(new Percent(taxRate));
	}

	@Override
	public BigDecimal getTaxPercentage() {
		return super.getRateApplicablePercent()==null? null : getRateApplicablePercent().getValue();
	}

	/**
	 * VAT exemption reason text (BT-120) and code (BT-121)
	 *
	 * @see com.klst.einvoice.VatBreakdown#setTaxExemption(java.lang.String, java.lang.String)
	 */
//	@Override
	public void setTaxExemption(String text, String code) {
		if(text!=null) {
			super.setExemptionReason(Text.create(text));
		}
		if(code!=null) {
			SCopyCtor.getInstance().set(this, "exemptionReasonCode", code);
		}
	}

//	@Override
	public String getTaxExemptionReasonText() {
		return super.getExemptionReason()==null ? null : super.getExemptionReason().getValue();
	}

//	@Override
	public String getTaxExemptionReasonCode() {
		return super.getExemptionReasonCode()==null ? null : super.getExemptionReasonCode().getValue();
	}

}
