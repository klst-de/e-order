//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}DELIVERY_START_DATE"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}DELIVERY_END_DATE"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" default="fixed">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.bmecat.org/bmecat/2005}dtSTRING">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="20"/>
 *             &lt;enumeration value="optional"/>
 *             &lt;enumeration value="fixed"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "deliverystartdate",
    "deliveryenddate"
})
@XmlRootElement(name = "DELIVERY_DATE")
public class DELIVERYDATE {

    @XmlElement(name = "DELIVERY_START_DATE", required = true)
    protected String deliverystartdate;
    @XmlElement(name = "DELIVERY_END_DATE", required = true)
    protected String deliveryenddate;
    @XmlAttribute(name = "type")
    protected String type;

    /**
     * Gets the value of the deliverystartdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDELIVERYSTARTDATE() {
        return deliverystartdate;
    }

    /**
     * Sets the value of the deliverystartdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDELIVERYSTARTDATE(String value) {
        this.deliverystartdate = value;
    }

    /**
     * Gets the value of the deliveryenddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDELIVERYENDDATE() {
        return deliveryenddate;
    }

    /**
     * Sets the value of the deliveryenddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDELIVERYENDDATE(String value) {
        this.deliveryenddate = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        if (type == null) {
            return "fixed";
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
