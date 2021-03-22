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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}NET_VALUE_GOODS"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}TOTAL_TAX"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}TOTAL_AMOUNT"/>
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
    "netvaluegoods",
    "totaltax",
    "totalamount"
})
@XmlRootElement(name = "ORIGINAL_INVOICE_SUMMARY")
public class ORIGINALINVOICESUMMARY {

    @XmlElement(name = "NET_VALUE_GOODS", required = true)
    protected BigDecimal netvaluegoods;
    @XmlElement(name = "TOTAL_TAX", required = true)
    protected TOTALTAX totaltax;
    @XmlElement(name = "TOTAL_AMOUNT", required = true)
    protected BigDecimal totalamount;

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

}
