package com.klst.eorder.openTrans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.opentrans.xmlschema._2.ALLOWORCHARGE;
import org.opentrans.xmlschema._2.ALLOWORCHARGESFIX;
import org.opentrans.xmlschema._2.PRODUCTPRICEFIX;
import org.opentrans.xmlschema._2.TAXDETAILSFIX;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.edoc.untdid.TaxTypeCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG29_PriceDetails;
import com.klst.eorder.api.BG30_LineVATInformation;

/* Kandidat für BG-27, BG-28, BG-29, BG-30

BG-29 1..1 PRICE DETAILS :
BG-29.BT-146 1..1 UnitPriceAmount
BG-29.BT-147 0..1 PriceDiscount
BG-29.BT-148 0..1 GrossPrice
BG-29.BT-149 0..1 base quantity, UnitPriceQuantity
BG-29.BT-150 0..1 Item price unit
BG-30 1..1 LINE VAT INFORMATION :
BG-30.BT-151 1..1 item VAT category code
BG-30.BT-152 0..1 item VAT rate

BG-29.BT-146 : BigDecimal priceamount; , required = true
    protected ALLOWORCHARGESFIX alloworchargesfix;       ====> BG-27, BG-28
    protected List<PRICEFLAG> priceflag;  // Dieses Element bestimmt, inwieweit Fracht-, 
             Verpackungs- oder ähnliche Kosten in dem Artikelpreis enthalten sind.
              Bsp PRICEFLAG.type="incl_freight" TRUE :: Preis enthält Frachtkosten
                                                         ====> nicht in CIO
    protected List<TAXDETAILSFIX> taxdetailsfix;         ====> BG-30 LINE VAT INFORMATION
    protected BigDecimal pricequantity;
PRICEBASEFIX pricebasefix:
    protected float priceunitvalue;
    protected String priceunit , required = true
    protected Float priceunitfactor , defaultValue = "1"

Beispiel:
			<PRODUCT_PRICE_FIX>
				<bmecat:PRICE_AMOUNT>0.0</bmecat:PRICE_AMOUNT>   <!-- BG-29.BT-146 1..1 UnitPriceAmount
				<ALLOW_OR_CHARGES_FIX>
					<ALLOW_OR_CHARGE type="allowance">           <!-- BG-27
						<ALLOW_OR_CHARGE_SEQUENCE>1</ALLOW_OR_CHARGE_SEQUENCE>
						<ALLOW_OR_CHARGE_NAME>a</ALLOW_OR_CHARGE_NAME>
						<ALLOW_OR_CHARGE_TYPE>small_order</ALLOW_OR_CHARGE_TYPE>
						<ALLOW_OR_CHARGE_DESCR>a</ALLOW_OR_CHARGE_DESCR>
						<ALLOW_OR_CHARGE_VALUE>
							<AOC_PERCENTAGE_FACTOR>0.08</AOC_PERCENTAGE_FACTOR>
						</ALLOW_OR_CHARGE_VALUE>
					</ALLOW_OR_CHARGE>
					<ALLOW_OR_CHARGE type="allowance">
						<ALLOW_OR_CHARGE_SEQUENCE>2</ALLOW_OR_CHARGE_SEQUENCE>
						<ALLOW_OR_CHARGE_NAME>b</ALLOW_OR_CHARGE_NAME>
						<ALLOW_OR_CHARGE_TYPE>cash_discount</ALLOW_OR_CHARGE_TYPE>
						<ALLOW_OR_CHARGE_DESCR>a</ALLOW_OR_CHARGE_DESCR>
						<ALLOW_OR_CHARGE_VALUE>
							<AOC_PERCENTAGE_FACTOR>0.05</AOC_PERCENTAGE_FACTOR>
						</ALLOW_OR_CHARGE_VALUE>
						<!-- if you want to use another base for allowances or charges <ALLOW_OR_CHARGE_BASE></ALLOW_OR_CHARGE_BASE> -->
					</ALLOW_OR_CHARGE>
					<ALLOW_OR_CHARGES_TOTAL_AMOUNT>1111</ALLOW_OR_CHARGES_TOTAL_AMOUNT>
				</ALLOW_OR_CHARGES_FIX>
				<bmecat:PRICE_FLAG type="incl_assurance">TRUE</bmecat:PRICE_FLAG>
				<TAX_DETAILS_FIX>
					<bmecat:CALCULATION_SEQUENCE>1</bmecat:CALCULATION_SEQUENCE>
					<bmecat:TAX_CATEGORY>standard_rate</bmecat:TAX_CATEGORY>  <!-- BG-30.BT-151 1..1 item VAT category code
					<bmecat:TAX_TYPE>vat</bmecat:TAX_TYPE>
					<bmecat:TAX>0.0</bmecat:TAX>
					<TAX_AMOUNT>0.0</TAX_AMOUNT>
					<TAX_BASE>0.0</TAX_BASE>
					<bmecat:EXEMPTION_REASON>a</bmecat:EXEMPTION_REASON>
					<bmecat:JURISDICTION>a</bmecat:JURISDICTION>
				</TAX_DETAILS_FIX>
				<bmecat:PRICE_QUANTITY>1</bmecat:PRICE_QUANTITY>  <!-- BG-29.BT-149 0..1 base quantity
				<PRICE_BASE_FIX>
					<PRICE_UNIT_VALUE>1111</PRICE_UNIT_VALUE>
					<bmecat:PRICE_UNIT>04</bmecat:PRICE_UNIT>     <!-- BG-29.BT-150 0..1 Item price unit
					<bmecat:PRICE_UNIT_FACTOR>1</bmecat:PRICE_UNIT_FACTOR>
				</PRICE_BASE_FIX>
			</PRODUCT_PRICE_FIX>

 */
/**
 * PRODUCT_PRICE_FIX (Festgesetzter Produktpreis)
 * 
 * Dieses Element definiert einen festgelegten Preis zu dem Produkt. 
 * In Gegensatz zum Element PRICE_LINE_AMOUNT enthält der festgelegte Produktpreis keine variablen Entscheidungsmöglichkeiten, 
 * wie Staffelpreise (abhängig von der Bestellmenge) oder verschiedene Preisgebiete. 
 * PRODUCT_PRICE_FIX beschreibt also einen Wert und nicht ein Preisbildungsregelwerk. 
 * 
 * Das Element sollte nur in Geschäftsdokumenten genutzt werden, 
 * in denen typischerweise Preisinformationen enthalten sind (wie Rechnung etc.).
 */
public class Productpricefix extends PRODUCTPRICEFIX implements BG29_PriceDetails, BG30_LineVATInformation {

	static Productpricefix create() {
		return new Productpricefix(null); 
	}
	// copy factory
	static Productpricefix create(PRODUCTPRICEFIX object) {
		Productpricefix res;
		if(object instanceof PRODUCTPRICEFIX && object.getClass()!=PRODUCTPRICEFIX.class) {
			// object is instance of a subclass of PRODUCTPRICEFIX, but not PRODUCTPRICEFIX itself
			res = (Productpricefix)object;
		} else {
			res = new Productpricefix(object); 
		}
		return res;
	}

	private static final Logger LOG = Logger.getLogger(Productpricefix.class.getName());

	// copy ctor
	private Productpricefix(PRODUCTPRICEFIX object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}
	
/* BG-27 0..n LINE ALLOWANCES / ABSCHLÄGE ================================================= >>

public class ALLOWORCHARGESFIX {

    protected List<ALLOWORCHARGE> alloworcharge; required
    protected Float alloworchargestotalamount;

			<PRODUCT_PRICE_FIX>
				<bmecat:PRICE_AMOUNT>0.0</bmecat:PRICE_AMOUNT>
				<ALLOW_OR_CHARGES_FIX>
					<ALLOW_OR_CHARGE type="allowance">                          <!-- BG-27 ABSCHLAG
						<ALLOW_OR_CHARGE_SEQUENCE>1</ALLOW_OR_CHARGE_SEQUENCE>
						<ALLOW_OR_CHARGE_NAME>a</ALLOW_OR_CHARGE_NAME>
						<ALLOW_OR_CHARGE_TYPE>small_order</ALLOW_OR_CHARGE_TYPE>
						<ALLOW_OR_CHARGE_DESCR>a</ALLOW_OR_CHARGE_DESCR>        <!-- // BG-27.BT-139 ReasonText
						<ALLOW_OR_CHARGE_VALUE>
							<AOC_PERCENTAGE_FACTOR>0.08</AOC_PERCENTAGE_FACTOR> <!-- // BG-27.BT-138 Percentage
						</ALLOW_OR_CHARGE_VALUE>
					</ALLOW_OR_CHARGE>

 */
	void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		if(allowanceOrCharge==null) return; // defensiv
		
		ALLOWORCHARGESFIX acf = getALLOWORCHARGESFIX();
		if(acf==null) {
			acf = new ALLOWORCHARGESFIX();
			setALLOWORCHARGESFIX(acf);
		}
		
		acf.getALLOWORCHARGE().add((ALLOWORCHARGE)allowanceOrCharge);
		// TODO optional, in order-x API nicht vorgesehen:
		// berechnen und setzen Float alloworchargestotalamount
/* dtFloat alloworchargestotalamount, Fließkommazahl in 64-bit nach IEEE Standard 754 Dezimaltrennzeichen ist der Punkt. 
	Es ist kein Trennzeichen zum Abgrenzen von 1000er-Stellen erlaubt.
	Beispiele:
.314159265358979E+1
15.4

Summe über alle Zu- und Abschläge, die zu einem Geldbetrag führen.
Bei der Berechnung werden diejenigen Zu- und Abschläge berücksichtigt, deren ALLOW_OR_CHARGE_VALUE einen Geldbetrag beinhalten 
(aocmonetaryamount) oder sich über einen Prozentsatz (aocpercentagefactor) herleiten lassen

 */
	}
	List<AllowancesAndCharges> getAllowancesAndCharges() {
		if(super.getALLOWORCHARGESFIX()==null) return new ArrayList<AllowancesAndCharges>();
		List<ALLOWORCHARGE> allowOrChargeList = super.getALLOWORCHARGESFIX().getALLOWORCHARGE();
		List<AllowancesAndCharges> result = new ArrayList<AllowancesAndCharges>(allowOrChargeList.size());
		allowOrChargeList.forEach(aoc -> {
			AllowOrCharge allowOrCharge = AllowOrCharge.create(aoc);
			LOG.info("allowOrCharge:"+allowOrCharge);
//			if("allowance".equals(aoc.getALLOWORCHARGETYPE()) { ..;
//			if("surcharge") ...		
//			aoc.getALLOWORCHARGEVALUE(); mit
//	Float   "aocpercentagefactor",        ==> Percentage
//	Float	"aocmonetaryamount",          ==> AmountWithoutTax
//		    "aocorderunitscount",
//		    "aocadditionalitems"
//                                        
//			aoc.getALLOWORCHARGEBASE();  // ==> AssessmentBase
			result.add(allowOrCharge);
		});		
		return result;
		
	}

	// 170: 0..1 Item price charge
	/*
	 * in OT wird kein Unterschied zwischen Zuschlägen (326: BG-28) und PriceCharge (170) gemacht
	 */
	@Override
	public void setPriceCharge(AllowancesAndCharges charge) {
		if(charge==null) return; // defensiv
		
		// Zuschlag je Einheit muss zuerst berechnet werden:
		((ALLOWORCHARGE)charge).setALLOWORCHARGESEQUENCE(BigInteger.ZERO);
		if(((ALLOWORCHARGE)charge).getALLOWORCHARGETYPE()==null) {
			// Benutzerdefinierter Wert im Format: [\w\-\.]{1,30} :
			((ALLOWORCHARGE)charge).setALLOWORCHARGETYPE("Item-price-charge");
		}
		
		addAllowanceCharge(charge);
	}
	@Override
	public AllowancesAndCharges getPriceCharge() {
		List<AllowancesAndCharges> res = new ArrayList<AllowancesAndCharges>();
		List<AllowancesAndCharges> list = getAllowancesAndCharges();
		list.forEach(aoc -> {		
			if( BigInteger.ZERO.equals(((ALLOWORCHARGE)aoc).getALLOWORCHARGESEQUENCE()) ) res.add(aoc);
		});
		return res.isEmpty() ? null : res.get(0);
	}

/* BG-29 1..1 PRICE DETAILS =============================================================== >>
 * 
 */
	// BG-29.BT-146 1..1 UnitPriceAmount
	@Override
	public IAmount getUnitPriceAmount() {
		return Amount.create(super.getPRICEAMOUNT());
	}
	void setUnitPriceAmount(IAmount unitPriceAmount) {
		super.setPRICEAMOUNT(unitPriceAmount.getValue()); // required
	}
	
	// 162: BG-29.BT-147 0..1 PriceDiscount / Rabatt je Einheit
	/* (aus EN 16931-1:2020-12): PriceDiscount BG-29.BT-147 dient zur Berechnung des Nettopreises (UnitPriceAmount BG-29.BT-146)
	 * Der Nettopreis ist mandatory : Nettopreis = Bruttopreis - Rabatt
	 * Bruttopreis und Rabatt sind optional. Diese beiden dienen nur der Information.
	 * 
	 * in OT wird kein Unterschied zwischen Abschlägen (318: BG-27) und Rabatt/PriceDiscount gemacht,
	 * und zwischen Zuschlägen (326: BG-28) und PriceCharge (170).
	 */
	@Override
	public void setPriceDiscount(AllowancesAndCharges priceDiscount) {
		if(priceDiscount==null) return; // defensiv
		
		// Rabatt je Einheit muss zuerst berechnet werden:
		((ALLOWORCHARGE)priceDiscount).setALLOWORCHARGESEQUENCE(BigInteger.ZERO);
		if(((ALLOWORCHARGE)priceDiscount).getALLOWORCHARGETYPE()==null) {
			((ALLOWORCHARGE)priceDiscount).setALLOWORCHARGETYPE(AllowOrCharge.RABATE);
		}
		
		addAllowanceCharge(priceDiscount);
	}
	@Override
	public AllowancesAndCharges getPriceDiscount() {
		List<AllowancesAndCharges> res = new ArrayList<AllowancesAndCharges>();
		List<AllowancesAndCharges> list = getAllowancesAndCharges();
		list.forEach(aoc -> {
			if( AllowOrCharge.isRabate((ALLOWORCHARGE)aoc) ) res.add(aoc);
		});
		return res.isEmpty() ? null : res.get(0);
	}
	

	// BG-29.BT-148 0..1 GrossPrice 
	/* Bruttopreis (nicht in OT), aber es lässt sich berechnen,
	 *  wenn Rabatt existiert: Bruttopreis = Nettopreis + Rabatt
	 */
	@Override
	public void setGrossPrice(IAmount grossPrice) {
	}
	@Override
	public IAmount getGrossPrice() {
//		return null;
		// Berechnung:
		AllowancesAndCharges priceDiscount = getPriceDiscount();
		if(priceDiscount==null) return null;
		IAmount discount = priceDiscount.getAmountWithoutTax();
		if(discount==null) return null;
		LOG.config("compute: UnitPriceAmount+Discount="+getUnitPriceAmount() + " + " + discount);
		return Amount.create(getUnitPriceAmount().getValue().add(discount.getValue()));
	}

	// BG-29.BT-149 0..1 base quantity, UnitPriceQuantity
	// BG-29.BT-150 0..1 Item price unit
	/*
	 * Wird dieses Element nicht angegeben, so bezieht sich der Preis auf die im Element ORDER_UNIT enthaltene Bestelleinheit. 
	 * Durch Angabe eines Vielfaches oder eines Bruchteils der Bestelleinheit kann davon abgewichen werden. 
	 * Beispiel: 10 mit Bestelleinheit Karton, d.h. der Preis bezieht sich auf 10 Kartons.
	 */
	@Override
	public IQuantity getUnitPriceQuantity() {
		return super.getPRICEQUANTITY()==null ? null : Quantity.create(getPRICEQUANTITY());
	}
	@Override
	public void setUnitPriceQuantity(IQuantity basisQuantity) {
		super.setPRICEQUANTITY(basisQuantity.getValue());
	}
	
/* BG-30 1..1 LINE VAT INFORMATION =============================================================== >>

	List<TAXDETAILSFIX> // Angaben zu einer angewendeten Steuer
    protected BigInteger calculationsequence , defaultValue = "1"
    protected String taxcategory;
    protected String taxtype , defaultValue = "vat"
    protected BigDecimal tax;
    protected BigDecimal taxamount;
    protected BigDecimal taxbase;
    protected List<EXEMPTIONREASON> exemptionreason;
    protected List<JURISDICTION> jurisdiction;
	
	TAX_DETAILS_FIX (Festgelegte Steuerdetails)
	Dieses Element enthält Angaben zu einer angewendeten Steuer.
	
	TAX_TYPE:
	Dieses Element gibt an, um welche Steuer es sich handelt. 
	Die Angabe der Steuerart sollte wenn möglich in kodierter Form mit Hilfe von 
	gängigen internationalen Begriffen erfolgen (z.B. Mehrwertsteuer = VAT).
 */
	
	// 315: BG-30.BT-151 1..1 item VAT category code
	@Override
	public void setTaxCategory(TaxCategoryCode taxCategoryCode) {
		setTaxCategoryAndRate(taxCategoryCode, null);
	}
	// 317: BG-30.BT-152 0..1 item VAT rate
	@Override
	public void setTaxRate(BigDecimal taxRate) {
		// taxRate ohne TaxCategoryCode hat wenig Sinn
	}
	@Override
	public void setTaxCategoryAndRate(TaxCategoryCode taxCategoryCode, BigDecimal taxRate) {
		if(taxCategoryCode==null) return;

		// nur eine Steuerart VAT:
		if(getTAXDETAILSFIX().isEmpty()) {
			getTAXDETAILSFIX().add(new TAXDETAILSFIX());
		}
		TAXDETAILSFIX tdf = getTAXDETAILSFIX().get(0);
		
		tdf.setTAXTYPE("vat"); // default

		switch(taxCategoryCode) {
		case STANDARD_RATE :
		case REDUCED_RATE :
		case ZERO_RATE :
		case EXEMPTION :
			tdf.setTAXCATEGORY(taxCategoryCode.toString().toLowerCase());
			tdf.setTAX(taxRate);
			break;
		default:
			LOG.warning("no OpenTrans TAX_CATEGORY exists for "+taxCategoryCode);
			tdf.setTAXCATEGORY(taxCategoryCode.toString());
			tdf.setTAX(taxRate);
		}
	}	
	
	private TAXDETAILSFIX getVatDatails() {
		List<TAXDETAILSFIX> taxDatails = super.getTAXDETAILSFIX();
		if(taxDatails.isEmpty()) return null; // defensiv
		
		TAXDETAILSFIX vatDatails = null;
		for(int i=0; i<taxDatails.size(); i++) {
			TAXDETAILSFIX td = taxDatails.get(i);
			if(td.getTAXTYPE().toUpperCase().equals(TaxTypeCode.VAT)) {
				if(vatDatails!=null) {
					LOG.warning("mehrere VAT Eintäge in List<TAXDETAILSFIX>");
				}
				vatDatails = td;
			}
		}
		return vatDatails;
	}
	
	/* TAX_CATEGORY:
	 * Dieses Element gibt den Steuersatz in kodierter Form an. Es ist so möglich, 
	 * die Steuern auch über längere Zeiträume hinweg unabhängig vom aktuell gültigen Prozentsatz (TAX) anzugeben. 
	 * Die Angabe der Steuerkategorie sollte, wenn möglich, in kodierter Form 
	 * mit Hilfe von gängigen internationalen Begriffen erfolgen. 
	 * In der Liste der vordefinierten Werte für dieses Element finden sich bereits die Einträge, 
	 * die innerhalb der EU zur Angabe der Mehrwertsteuersätze verwendet werden sollen 
	 * (siehe auch http://europa.eu.int/comm/taxation_customs/taxation/vat/how_vat_works/rates/index_de.htm)
	 * https://ec.europa.eu/taxation_customs/sites/taxation/files/resources/documents/taxation/vat/how_vat_works/rates/vat_rates_en.pdf
	 * 
	 * Vordefinierte Werte für das Element TAX_CATEGORY:
Steuerbefreit           : exemption     Das Produkt ist von der Steuer befreit.
Zwischensatz            : parking_rate  Auf das Produkt ist ein Zwischensatz anzuwenden.
Ermäßigter Satz         : reduced_rate  Auf das Produkt ist ein reduzierter Steuersatz anzuwenden.
Normalsatz              : standard_rate Auf das Produkt ist der normale Steuersatz anzuwenden.
Stark ermäßigter Satz   : super_reduced_rate Auf das Produkt ist ein stark ermäßigter Steuersatz anzuwenden.
Nullsatz                : zero_rate Auf das Produkt ist der Nullsatz anzuwenden.
Andere Steuerkategorien : Benutzerdefinierter Wert im Format: [\w\-\.]{1,80}
Die Angabe der Steuerkategorie sollte wenn möglich in kodierter Form mit Hilfe von gängigen internationalen Begriffen erfolgen. Die Länge des Begriffs muss
mindestens 1 Zeichen und darf höchstens 80 Zeichen betragen.

	 */
	private static final Map<String, TaxCategoryCode> MAP_OT_TAXCATEGORY = Stream.of(
		    new AbstractMap.SimpleImmutableEntry<>("standard_rate", TaxCategoryCode.STANDARD_RATE),
		    new AbstractMap.SimpleImmutableEntry<>("exemption", TaxCategoryCode.EXEMPTION)
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	@Override
	public TaxCategoryCode getTaxCategory() {
		TAXDETAILSFIX vatDatails = getVatDatails();
		return vatDatails==null ? null : MAP_OT_TAXCATEGORY.get(vatDatails.getTAXCATEGORY());
	}
	
	/* TAX:
	 * Faktor für Steuer, der für diesen Preis gilt
	 * Beispiel: "0.16", entspricht 16%
	 */
	// 317: BG-30.BT-152 0..1 item VAT rate
	@Override
	public BigDecimal getTaxRate() {
		TAXDETAILSFIX vatDatails = getVatDatails();
		return vatDatails.getTAX();
	}

}