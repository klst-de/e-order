//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.14 at 10:16:57 PM CET 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.AmountType;


/**
 * Trade Settlement Header Monetary Summation
 * 
 * <p>Java class for TradeSettlementHeaderMonetarySummationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeSettlementHeaderMonetarySummationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LineTotalAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType"/>
 *         &lt;element name="ChargeTotalAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" minOccurs="0"/>
 *         &lt;element name="AllowanceTotalAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" minOccurs="0"/>
 *         &lt;element name="TaxBasisTotalAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType"/>
 *         &lt;element name="TaxTotalAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RoundingAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" minOccurs="0"/>
 *         &lt;element name="GrandTotalAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" minOccurs="0"/>
 *         &lt;element name="TotalPrepaidAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" minOccurs="0"/>
 *         &lt;element name="DuePayableAmount" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}AmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeSettlementHeaderMonetarySummationType", propOrder = {
    "lineTotalAmount",
    "chargeTotalAmount",
    "allowanceTotalAmount",
    "taxBasisTotalAmount",
    "taxTotalAmount",
    "roundingAmount",
    "grandTotalAmount",
    "totalPrepaidAmount",
    "duePayableAmount"
})
public class TradeSettlementHeaderMonetarySummationType {

    @XmlElement(name = "LineTotalAmount", required = true)
    protected AmountType lineTotalAmount;
    @XmlElement(name = "ChargeTotalAmount")
    protected AmountType chargeTotalAmount;
    @XmlElement(name = "AllowanceTotalAmount")
    protected AmountType allowanceTotalAmount;
    @XmlElement(name = "TaxBasisTotalAmount", required = true)
    protected AmountType taxBasisTotalAmount;
    @XmlElement(name = "TaxTotalAmount")
    protected List<AmountType> taxTotalAmount;
    @XmlElement(name = "RoundingAmount")
    protected AmountType roundingAmount;
    @XmlElement(name = "GrandTotalAmount")
    protected AmountType grandTotalAmount;
    @XmlElement(name = "TotalPrepaidAmount")
    protected AmountType totalPrepaidAmount;
    @XmlElement(name = "DuePayableAmount")
    protected AmountType duePayableAmount;

    /**
     * Gets the value of the lineTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getLineTotalAmount() {
        return lineTotalAmount;
    }

    /**
     * Sets the value of the lineTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setLineTotalAmount(AmountType value) {
        this.lineTotalAmount = value;
    }

    /**
     * Gets the value of the chargeTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getChargeTotalAmount() {
        return chargeTotalAmount;
    }

    /**
     * Sets the value of the chargeTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setChargeTotalAmount(AmountType value) {
        this.chargeTotalAmount = value;
    }

    /**
     * Gets the value of the allowanceTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getAllowanceTotalAmount() {
        return allowanceTotalAmount;
    }

    /**
     * Sets the value of the allowanceTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setAllowanceTotalAmount(AmountType value) {
        this.allowanceTotalAmount = value;
    }

    /**
     * Gets the value of the taxBasisTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTaxBasisTotalAmount() {
        return taxBasisTotalAmount;
    }

    /**
     * Sets the value of the taxBasisTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTaxBasisTotalAmount(AmountType value) {
        this.taxBasisTotalAmount = value;
    }

    /**
     * Gets the value of the taxTotalAmount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxTotalAmount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxTotalAmount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AmountType }
     * 
     * 
     */
    public List<AmountType> getTaxTotalAmount() {
        if (taxTotalAmount == null) {
            taxTotalAmount = new ArrayList<AmountType>();
        }
        return this.taxTotalAmount;
    }

    /**
     * Gets the value of the roundingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getRoundingAmount() {
        return roundingAmount;
    }

    /**
     * Sets the value of the roundingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setRoundingAmount(AmountType value) {
        this.roundingAmount = value;
    }

    /**
     * Gets the value of the grandTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getGrandTotalAmount() {
        return grandTotalAmount;
    }

    /**
     * Sets the value of the grandTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setGrandTotalAmount(AmountType value) {
        this.grandTotalAmount = value;
    }

    /**
     * Gets the value of the totalPrepaidAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTotalPrepaidAmount() {
        return totalPrepaidAmount;
    }

    /**
     * Sets the value of the totalPrepaidAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTotalPrepaidAmount(AmountType value) {
        this.totalPrepaidAmount = value;
    }

    /**
     * Gets the value of the duePayableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getDuePayableAmount() {
        return duePayableAmount;
    }

    /**
     * Sets the value of the duePayableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setDuePayableAmount(AmountType value) {
        this.duePayableAmount = value;
    }

}
