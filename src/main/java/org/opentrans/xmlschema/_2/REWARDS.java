//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import java.math.BigDecimal;
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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REWARDS_POINTS"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REWARDS_SUMMARY" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REWARDS_SYSTEM"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REWARDS_DESCR" minOccurs="0"/>
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
    "rewardspoints",
    "rewardssummary",
    "rewardssystem",
    "rewardsdescr"
})
@XmlRootElement(name = "REWARDS")
public class REWARDS {

    @XmlElement(name = "REWARDS_POINTS", required = true)
    protected BigDecimal rewardspoints;
    @XmlElement(name = "REWARDS_SUMMARY")
    protected BigDecimal rewardssummary;
    @XmlElement(name = "REWARDS_SYSTEM", required = true)
    protected REWARDSSYSTEM rewardssystem;
    @XmlElement(name = "REWARDS_DESCR")
    protected REWARDSDESCR rewardsdescr;

    /**
     * Gets the value of the rewardspoints property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getREWARDSPOINTS() {
        return rewardspoints;
    }

    /**
     * Sets the value of the rewardspoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setREWARDSPOINTS(BigDecimal value) {
        this.rewardspoints = value;
    }

    /**
     * Gets the value of the rewardssummary property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getREWARDSSUMMARY() {
        return rewardssummary;
    }

    /**
     * Sets the value of the rewardssummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setREWARDSSUMMARY(BigDecimal value) {
        this.rewardssummary = value;
    }

    /**
     * Gets the value of the rewardssystem property.
     * 
     * @return
     *     possible object is
     *     {@link REWARDSSYSTEM }
     *     
     */
    public REWARDSSYSTEM getREWARDSSYSTEM() {
        return rewardssystem;
    }

    /**
     * Sets the value of the rewardssystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link REWARDSSYSTEM }
     *     
     */
    public void setREWARDSSYSTEM(REWARDSSYSTEM value) {
        this.rewardssystem = value;
    }

    /**
     * Gets the value of the rewardsdescr property.
     * 
     * @return
     *     possible object is
     *     {@link REWARDSDESCR }
     *     
     */
    public REWARDSDESCR getREWARDSDESCR() {
        return rewardsdescr;
    }

    /**
     * Sets the value of the rewardsdescr property.
     * 
     * @param value
     *     allowed object is
     *     {@link REWARDSDESCR }
     *     
     */
    public void setREWARDSDESCR(REWARDSDESCR value) {
        this.rewardsdescr = value;
    }

}
