//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}PAYMENT_DATE"/>
 *           &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}DAYS"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}DISCOUNT_FACTOR" minOccurs="0"/>
 *           &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGES_FIX" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "paymentdate",
    "days",
    "discountfactor",
    "alloworchargesfix"
})
@XmlRootElement(name = "TIME_FOR_PAYMENT")
public class TIMEFORPAYMENT {

    @XmlElement(name = "PAYMENT_DATE")
    protected String paymentdate;
    @XmlElement(name = "DAYS")
    protected BigInteger days;
    @XmlElement(name = "DISCOUNT_FACTOR")
    protected Float discountfactor;
    @XmlElement(name = "ALLOW_OR_CHARGES_FIX")
    protected ALLOWORCHARGESFIX alloworchargesfix;

    /**
     * Gets the value of the paymentdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAYMENTDATE() {
        return paymentdate;
    }

    /**
     * Sets the value of the paymentdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYMENTDATE(String value) {
        this.paymentdate = value;
    }

    /**
     * Gets the value of the days property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDAYS() {
        return days;
    }

    /**
     * Sets the value of the days property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDAYS(BigInteger value) {
        this.days = value;
    }

    /**
     * Gets the value of the discountfactor property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDISCOUNTFACTOR() {
        return discountfactor;
    }

    /**
     * Sets the value of the discountfactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDISCOUNTFACTOR(Float value) {
        this.discountfactor = value;
    }

    /**
     * Gets the value of the alloworchargesfix property.
     * 
     * @return
     *     possible object is
     *     {@link ALLOWORCHARGESFIX }
     *     
     */
    public ALLOWORCHARGESFIX getALLOWORCHARGESFIX() {
        return alloworchargesfix;
    }

    /**
     * Sets the value of the alloworchargesfix property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALLOWORCHARGESFIX }
     *     
     */
    public void setALLOWORCHARGESFIX(ALLOWORCHARGESFIX value) {
        this.alloworchargesfix = value;
    }

}
