//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}CONTROL_INFO" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}SOURCING_INFO" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ORDER_INFO"/>
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
    "controlinfo",
    "sourcinginfo",
    "orderinfo"
})
@XmlRootElement(name = "ORDER_HEADER")
public class ORDERHEADER {

    @XmlElement(name = "CONTROL_INFO")
    protected CONTROLINFO controlinfo;
    @XmlElement(name = "SOURCING_INFO")
    protected SOURCINGINFO sourcinginfo;
    @XmlElement(name = "ORDER_INFO", required = true)
    protected ORDERINFO orderinfo;

    /**
     * Gets the value of the controlinfo property.
     * 
     * @return
     *     possible object is
     *     {@link CONTROLINFO }
     *     
     */
    public CONTROLINFO getCONTROLINFO() {
        return controlinfo;
    }

    /**
     * Sets the value of the controlinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CONTROLINFO }
     *     
     */
    public void setCONTROLINFO(CONTROLINFO value) {
        this.controlinfo = value;
    }

    /**
     * Gets the value of the sourcinginfo property.
     * 
     * @return
     *     possible object is
     *     {@link SOURCINGINFO }
     *     
     */
    public SOURCINGINFO getSOURCINGINFO() {
        return sourcinginfo;
    }

    /**
     * Sets the value of the sourcinginfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOURCINGINFO }
     *     
     */
    public void setSOURCINGINFO(SOURCINGINFO value) {
        this.sourcinginfo = value;
    }

    /**
     * Gets the value of the orderinfo property.
     * 
     * @return
     *     possible object is
     *     {@link ORDERINFO }
     *     
     */
    public ORDERINFO getORDERINFO() {
        return orderinfo;
    }

    /**
     * Sets the value of the orderinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ORDERINFO }
     *     
     */
    public void setORDERINFO(ORDERINFO value) {
        this.orderinfo = value;
    }

}