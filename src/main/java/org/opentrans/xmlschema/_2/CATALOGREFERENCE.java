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
import org.bmecat.bmecat._2005.CATALOGNAME;


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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CATALOG_ID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CATALOG_VERSION"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CATALOG_NAME" minOccurs="0"/>
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
    "catalogid",
    "catalogversion",
    "catalogname"
})
@XmlRootElement(name = "CATALOG_REFERENCE")
public class CATALOGREFERENCE {

    @XmlElement(name = "CATALOG_ID", namespace = "http://www.bmecat.org/bmecat/2005", required = true)
    protected String catalogid;
    @XmlElement(name = "CATALOG_VERSION", namespace = "http://www.bmecat.org/bmecat/2005", required = true)
    protected String catalogversion;
    @XmlElement(name = "CATALOG_NAME", namespace = "http://www.bmecat.org/bmecat/2005")
    protected CATALOGNAME catalogname;

    /**
     * Gets the value of the catalogid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCATALOGID() {
        return catalogid;
    }

    /**
     * Sets the value of the catalogid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCATALOGID(String value) {
        this.catalogid = value;
    }

    /**
     * Gets the value of the catalogversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCATALOGVERSION() {
        return catalogversion;
    }

    /**
     * Sets the value of the catalogversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCATALOGVERSION(String value) {
        this.catalogversion = value;
    }

    /**
     * Gets the value of the catalogname property.
     * 
     * @return
     *     possible object is
     *     {@link CATALOGNAME }
     *     
     */
    public CATALOGNAME getCATALOGNAME() {
        return catalogname;
    }

    /**
     * Sets the value of the catalogname property.
     * 
     * @param value
     *     allowed object is
     *     {@link CATALOGNAME }
     *     
     */
    public void setCATALOGNAME(CATALOGNAME value) {
        this.catalogname = value;
    }

}