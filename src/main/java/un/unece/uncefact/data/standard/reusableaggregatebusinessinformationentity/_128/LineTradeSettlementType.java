//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.17 at 10:18:46 PM CEST 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Line Trade Settlement
 * 
 * <p>Java class for LineTradeSettlementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineTradeSettlementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApplicableTradeTax" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}TradeTaxType" minOccurs="0"/>
 *         &lt;element name="SpecifiedTradeAllowanceCharge" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}TradeAllowanceChargeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SpecifiedTradeSettlementLineMonetarySummation" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}TradeSettlementLineMonetarySummationType"/>
 *         &lt;element name="ReceivableSpecifiedTradeAccountingAccount" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}TradeAccountingAccountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeSettlementType", propOrder = {
    "applicableTradeTax",
    "specifiedTradeAllowanceCharge",
    "specifiedTradeSettlementLineMonetarySummation",
    "receivableSpecifiedTradeAccountingAccount"
})
public class LineTradeSettlementType {

    @XmlElement(name = "ApplicableTradeTax")
    protected TradeTaxType applicableTradeTax;
    @XmlElement(name = "SpecifiedTradeAllowanceCharge")
    protected List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;
    @XmlElement(name = "SpecifiedTradeSettlementLineMonetarySummation", required = true)
    protected TradeSettlementLineMonetarySummationType specifiedTradeSettlementLineMonetarySummation;
    @XmlElement(name = "ReceivableSpecifiedTradeAccountingAccount")
    protected TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount;

    /**
     * Gets the value of the applicableTradeTax property.
     * 
     * @return
     *     possible object is
     *     {@link TradeTaxType }
     *     
     */
    public TradeTaxType getApplicableTradeTax() {
        return applicableTradeTax;
    }

    /**
     * Sets the value of the applicableTradeTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeTaxType }
     *     
     */
    public void setApplicableTradeTax(TradeTaxType value) {
        this.applicableTradeTax = value;
    }

    /**
     * Gets the value of the specifiedTradeAllowanceCharge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specifiedTradeAllowanceCharge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecifiedTradeAllowanceCharge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TradeAllowanceChargeType }
     * 
     * 
     */
    public List<TradeAllowanceChargeType> getSpecifiedTradeAllowanceCharge() {
        if (specifiedTradeAllowanceCharge == null) {
            specifiedTradeAllowanceCharge = new ArrayList<TradeAllowanceChargeType>();
        }
        return this.specifiedTradeAllowanceCharge;
    }

    /**
     * Gets the value of the specifiedTradeSettlementLineMonetarySummation property.
     * 
     * @return
     *     possible object is
     *     {@link TradeSettlementLineMonetarySummationType }
     *     
     */
    public TradeSettlementLineMonetarySummationType getSpecifiedTradeSettlementLineMonetarySummation() {
        return specifiedTradeSettlementLineMonetarySummation;
    }

    /**
     * Sets the value of the specifiedTradeSettlementLineMonetarySummation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeSettlementLineMonetarySummationType }
     *     
     */
    public void setSpecifiedTradeSettlementLineMonetarySummation(TradeSettlementLineMonetarySummationType value) {
        this.specifiedTradeSettlementLineMonetarySummation = value;
    }

    /**
     * Gets the value of the receivableSpecifiedTradeAccountingAccount property.
     * 
     * @return
     *     possible object is
     *     {@link TradeAccountingAccountType }
     *     
     */
    public TradeAccountingAccountType getReceivableSpecifiedTradeAccountingAccount() {
        return receivableSpecifiedTradeAccountingAccount;
    }

    /**
     * Sets the value of the receivableSpecifiedTradeAccountingAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeAccountingAccountType }
     *     
     */
    public void setReceivableSpecifiedTradeAccountingAccount(TradeAccountingAccountType value) {
        this.receivableSpecifiedTradeAccountingAccount = value;
    }

}