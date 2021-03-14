//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.14 at 10:16:57 PM CET 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.AllowanceChargeReasonCodeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.AmountType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IndicatorType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.NumericType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.PercentType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.QuantityType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.TextType;


/**
 * Trade Allowance/Charge
 * 
 * <p>Java class for TradeAllowanceChargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeAllowanceChargeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChargeIndicator" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IndicatorType"/>
 *         &lt;element name="SequenceNumeric" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}NumericType" minOccurs="0"/>
 *         &lt;element name="CalculationPercent" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}PercentType" minOccurs="0"/>
 *         &lt;element name="BasisAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" minOccurs="0"/>
 *         &lt;element name="BasisQuantity" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}QuantityType" minOccurs="0"/>
 *         &lt;element name="ActualAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType"/>
 *         &lt;element name="ReasonCode" type="{urn:un:unece:uncefact:data:standard:QualifiedDataType:103}AllowanceChargeReasonCodeType" minOccurs="0"/>
 *         &lt;element name="Reason" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="CategoryTradeTax" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}TradeTaxType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeAllowanceChargeType", propOrder = {
    "chargeIndicator",
    "sequenceNumeric",
    "calculationPercent",
    "basisAmount",
    "basisQuantity",
    "actualAmount",
    "reasonCode",
    "reason",
    "categoryTradeTax"
})
public class TradeAllowanceChargeType {

    @XmlElement(name = "ChargeIndicator", required = true)
    protected IndicatorType chargeIndicator;
    @XmlElement(name = "SequenceNumeric")
    protected NumericType sequenceNumeric;
    @XmlElement(name = "CalculationPercent")
    protected PercentType calculationPercent;
    @XmlElement(name = "BasisAmount")
    protected AmountType basisAmount;
    @XmlElement(name = "BasisQuantity")
    protected QuantityType basisQuantity;
    @XmlElement(name = "ActualAmount", required = true)
    protected AmountType actualAmount;
    @XmlElement(name = "ReasonCode")
    protected AllowanceChargeReasonCodeType reasonCode;
    @XmlElement(name = "Reason")
    protected TextType reason;
    @XmlElement(name = "CategoryTradeTax")
    protected TradeTaxType categoryTradeTax;

    /**
     * Gets the value of the chargeIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link IndicatorType }
     *     
     */
    public IndicatorType getChargeIndicator() {
        return chargeIndicator;
    }

    /**
     * Sets the value of the chargeIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndicatorType }
     *     
     */
    public void setChargeIndicator(IndicatorType value) {
        this.chargeIndicator = value;
    }

    /**
     * Gets the value of the sequenceNumeric property.
     * 
     * @return
     *     possible object is
     *     {@link NumericType }
     *     
     */
    public NumericType getSequenceNumeric() {
        return sequenceNumeric;
    }

    /**
     * Sets the value of the sequenceNumeric property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumericType }
     *     
     */
    public void setSequenceNumeric(NumericType value) {
        this.sequenceNumeric = value;
    }

    /**
     * Gets the value of the calculationPercent property.
     * 
     * @return
     *     possible object is
     *     {@link PercentType }
     *     
     */
    public PercentType getCalculationPercent() {
        return calculationPercent;
    }

    /**
     * Sets the value of the calculationPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link PercentType }
     *     
     */
    public void setCalculationPercent(PercentType value) {
        this.calculationPercent = value;
    }

    /**
     * Gets the value of the basisAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getBasisAmount() {
        return basisAmount;
    }

    /**
     * Sets the value of the basisAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setBasisAmount(AmountType value) {
        this.basisAmount = value;
    }

    /**
     * Gets the value of the basisQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getBasisQuantity() {
        return basisQuantity;
    }

    /**
     * Sets the value of the basisQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setBasisQuantity(QuantityType value) {
        this.basisQuantity = value;
    }

    /**
     * Gets the value of the actualAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getActualAmount() {
        return actualAmount;
    }

    /**
     * Sets the value of the actualAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setActualAmount(AmountType value) {
        this.actualAmount = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link AllowanceChargeReasonCodeType }
     *     
     */
    public AllowanceChargeReasonCodeType getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllowanceChargeReasonCodeType }
     *     
     */
    public void setReasonCode(AllowanceChargeReasonCodeType value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setReason(TextType value) {
        this.reason = value;
    }

    /**
     * Gets the value of the categoryTradeTax property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTaxType }
     *     
     */
    public TradeTaxType getCategoryTradeTax() {
        return categoryTradeTax;
    }

    /**
     * Sets the value of the categoryTradeTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTaxType }
     *     
     */
    public void setCategoryTradeTax(TradeTaxType value) {
        this.categoryTradeTax = value;
    }

}