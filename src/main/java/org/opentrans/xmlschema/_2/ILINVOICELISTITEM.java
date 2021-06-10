//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.bmecat.bmecat._2005.DtCURRENCIES;


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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}INVOICE_REFERENCE"/>
 *         &lt;sequence>
 *           &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}FOREIGN_CURRENCY" minOccurs="0"/>
 *           &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}FOREIGN_AMOUNT" minOccurs="0"/>
 *           &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}EXCHANGE_RATE" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}NET_VALUE_GOODS"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}NET_VALUE_EXTRA" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGES_FIX" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}TOTAL_TAX"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}TOTAL_AMOUNT"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REWARDS" maxOccurs="unbounded" minOccurs="0"/>
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
    "invoicereference",
    "foreigncurrency",
    "foreignamount",
    "exchangerate",
    "netvaluegoods",
    "netvalueextra",
    "alloworchargesfix",
    "totaltax",
    "totalamount",
    "rewards"
})
@XmlRootElement(name = "IL_INVOICE_LIST_ITEM")
public class ILINVOICELISTITEM {

    @XmlElement(name = "INVOICE_REFERENCE", required = true)
    protected INVOICEREFERENCE invoicereference;
    @XmlElement(name = "FOREIGN_CURRENCY")
    @XmlSchemaType(name = "string")
    protected DtCURRENCIES foreigncurrency;
    @XmlElement(name = "FOREIGN_AMOUNT")
    protected BigDecimal foreignamount;
    @XmlElement(name = "EXCHANGE_RATE")
    protected BigDecimal exchangerate;
    @XmlElement(name = "NET_VALUE_GOODS", required = true)
    protected BigDecimal netvaluegoods;
    @XmlElement(name = "NET_VALUE_EXTRA")
    protected BigDecimal netvalueextra;
    @XmlElement(name = "ALLOW_OR_CHARGES_FIX")
    protected ALLOWORCHARGESFIX alloworchargesfix;
    @XmlElement(name = "TOTAL_TAX", required = true)
    protected TOTALTAX totaltax;
    @XmlElement(name = "TOTAL_AMOUNT", required = true)
    protected BigDecimal totalamount;
    @XmlElement(name = "REWARDS")
    protected List<REWARDS> rewards;

    /**
     * Gets the value of the invoicereference property.
     * 
     * @return
     *     possible object is
     *     {@link INVOICEREFERENCE }
     *     
     */
    public INVOICEREFERENCE getINVOICEREFERENCE() {
        return invoicereference;
    }

    /**
     * Sets the value of the invoicereference property.
     * 
     * @param value
     *     allowed object is
     *     {@link INVOICEREFERENCE }
     *     
     */
    public void setINVOICEREFERENCE(INVOICEREFERENCE value) {
        this.invoicereference = value;
    }

    /**
     * Gets the value of the foreigncurrency property.
     * 
     * @return
     *     possible object is
     *     {@link DtCURRENCIES }
     *     
     */
    public DtCURRENCIES getFOREIGNCURRENCY() {
        return foreigncurrency;
    }

    /**
     * Sets the value of the foreigncurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link DtCURRENCIES }
     *     
     */
    public void setFOREIGNCURRENCY(DtCURRENCIES value) {
        this.foreigncurrency = value;
    }

    /**
     * Gets the value of the foreignamount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFOREIGNAMOUNT() {
        return foreignamount;
    }

    /**
     * Sets the value of the foreignamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFOREIGNAMOUNT(BigDecimal value) {
        this.foreignamount = value;
    }

    /**
     * Gets the value of the exchangerate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEXCHANGERATE() {
        return exchangerate;
    }

    /**
     * Sets the value of the exchangerate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEXCHANGERATE(BigDecimal value) {
        this.exchangerate = value;
    }

    /**
     * Gets the value of the netvaluegoods property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNETVALUEGOODS() {
        return netvaluegoods;
    }

    /**
     * Sets the value of the netvaluegoods property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNETVALUEGOODS(BigDecimal value) {
        this.netvaluegoods = value;
    }

    /**
     * Gets the value of the netvalueextra property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNETVALUEEXTRA() {
        return netvalueextra;
    }

    /**
     * Sets the value of the netvalueextra property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNETVALUEEXTRA(BigDecimal value) {
        this.netvalueextra = value;
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

    /**
     * Gets the value of the totaltax property.
     * 
     * @return
     *     possible object is
     *     {@link TOTALTAX }
     *     
     */
    public TOTALTAX getTOTALTAX() {
        return totaltax;
    }

    /**
     * Sets the value of the totaltax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOTALTAX }
     *     
     */
    public void setTOTALTAX(TOTALTAX value) {
        this.totaltax = value;
    }

    /**
     * Gets the value of the totalamount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALAMOUNT() {
        return totalamount;
    }

    /**
     * Sets the value of the totalamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALAMOUNT(BigDecimal value) {
        this.totalamount = value;
    }

    /**
     * Gets the value of the rewards property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rewards property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getREWARDS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link REWARDS }
     * 
     * 
     */
    public List<REWARDS> getREWARDS() {
        if (rewards == null) {
            rewards = new ArrayList<REWARDS>();
        }
        return this.rewards;
    }

}