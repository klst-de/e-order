package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.Mapper;
import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.edoc.untdid.TaxTypeCode;
import com.klst.eorder.api.ITaxCategory;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.TradeTaxType;

public class TradeTax extends TradeTaxType implements ITaxCategory {

//	/**
//	 * {@inheritDoc}
//	 */
//	// implements VatBreakdownFactory
//	@Override
//	public VatBreakdown createVATBreakDown(Amount taxableAmount, Amount taxAmount, TaxCategoryCode taxCode, BigDecimal taxRate) {
//		return create(taxableAmount, taxAmount, taxCode, taxRate);
//	}
//	static TradeTax create(Amount taxableAmount, Amount taxAmount, TaxCategoryCode taxCode, BigDecimal taxRate) {
//		return new TradeTax(taxableAmount, taxAmount, taxCode, taxRate);
//	}

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

	private static final Logger LOG = Logger.getLogger(TradeTax.class.getName());

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
	private TradeTax(TradeTaxType doc) {
		super();
		if(doc!=null) {
			SCopyCtor.getInstance().invokeCopy(this, doc);
			LOG.config("copy ctor:"+this);
		}
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

	// BT-7 0..1 Value added tax point date
	//  Die Verwendung von BT-7 und BT-8 schließt sich gegenseitig aus.
//	public void setTaxPointDate(Timestamp ts) {
//		if(ts!=null) super.setTaxPointDate(DateTimeFormatStrings.toDate(ts));
//	}
//	public Timestamp getTaxPointDateAsTimestamp() {
//		DateType date = super.getTaxPointDate();
//		return date==null ? null : DateTimeFormats.ymdToTs(date.getDateString().getValue());		
//	}
	
	// BT-8 0..1 Value added tax point date code
	//  Die Verwendung von BT-7 und BT-8 schließt sich gegenseitig aus.
/*
Anwendung: 
Die in der Norm zitierten semantischen Werte, die durch die Werte 3, 35, 432 in UNTDID 2005 repräsentiert werden, 
werden auf die folgenden Werte von UNTDID2475 abgebildet, das ist die von CII 16B unterstützte relevante Codeliste:
- 5: Ausstellungsdatum des Rechnungsbelegs
- 29: Liefertermin, Ist-Zustand
- 72: Bis heute bezahlt

In Deutschland ist das Liefer- und Leistungsdatum maßgebend (BT-72) SupplyChainTradeTransaction/ApplicableHeaderTradeDelivery/ActualDeliverySupplyChainEvent/OccurrenceDateTime/DateTimeString).
. Codeliste: UNTDID 2475 Untermenge

keine Beispiele für Tests!

 */
//	0 .. n ApplicableTradeTax Umsatzsteueraufschlüsselung            BG-23
//	0 .. 1 DueDateTypeCode Code für das Datum der Steuerfälligkeit   BT-8
	public void setTaxPointDateCode(String code) {
		if(code==null) return;		
		Mapper.set(this, "dueDateTypeCode", code);
	}
	public String getTaxPointDateCode() {
		return super.getDueDateTypeCode()==null ? null : getDueDateTypeCode().getValue();		
	}
	
	// BT-116 1..1 BasisAmount Steuerbasisbetrag
//	@Override
	public void setTaxBaseAmount(IAmount amount) {
		if(amount!=null) super.setBasisAmount((Amount)amount);
	}
//	@Override
	public IAmount getTaxBaseAmount() {
		return super.getBasisAmount()==null ? null : Amount.create(getBasisAmount());
	}

	// BT-117 1..1 CalculatedAmount Kategoriespezifischer Steuerbetrag
//	@Override
	public void setCalculatedTaxAmount(IAmount amount) {
		if(amount!=null) super.setCalculatedAmount((Amount)amount);
	}

//	@Override
	public IAmount getCalculatedTaxAmount() {
		return super.getCalculatedAmount()==null ? null : Amount.create(getCalculatedAmount());
	}

	// ALLOWANCES (BG-20.BT-95-0) and CHARGES (BG-21.BT-102-0)
	// BG-23.BT-118-0
	@Override
	public void setTaxType(String code) {
		if(code==null) return;		
		Mapper.set(this, "typeCode", code);
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
		Mapper.set(this, "categoryCode", code);
	}
	@Override
	public TaxCategoryCode getTaxCategoryCode() {
		return TaxCategoryCode.valueOf(super.getCategoryCode().getValue().value());
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
			Mapper.set(this, "exemptionReasonCode", code);
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
