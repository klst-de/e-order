//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.bmecat.bmecat._2005;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ALLOWED_VALUE_ID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ALLOWED_VALUE_NAME" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ALLOWED_VALUE_VERSION" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ALLOWED_VALUE_SHORTNAME" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ALLOWED_VALUE_DESCR" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ALLOWED_VALUE_SYNONYMS" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ALLOWED_VALUE_SOURCE" minOccurs="0"/>
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
    "allowedvalueid",
    "allowedvaluename",
    "allowedvalueversion",
    "allowedvalueshortname",
    "allowedvaluedescr",
    "allowedvaluesynonyms",
    "allowedvaluesource"
})
@XmlRootElement(name = "ALLOWED_VALUE")
public class ALLOWEDVALUE {

    @XmlElement(name = "ALLOWED_VALUE_ID", required = true)
    protected String allowedvalueid;
    @XmlElement(name = "ALLOWED_VALUE_NAME", required = true)
    protected List<ALLOWEDVALUENAME> allowedvaluename;
    @XmlElement(name = "ALLOWED_VALUE_VERSION")
    protected TypeVERSION allowedvalueversion;
    @XmlElement(name = "ALLOWED_VALUE_SHORTNAME")
    protected List<ALLOWEDVALUESHORTNAME> allowedvalueshortname;
    @XmlElement(name = "ALLOWED_VALUE_DESCR")
    protected List<ALLOWEDVALUEDESCR> allowedvaluedescr;
    @XmlElement(name = "ALLOWED_VALUE_SYNONYMS")
    protected ALLOWEDVALUESYNONYMS allowedvaluesynonyms;
    @XmlElement(name = "ALLOWED_VALUE_SOURCE")
    protected TypeSOURCE allowedvaluesource;

    /**
     * Gets the value of the allowedvalueid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALLOWEDVALUEID() {
        return allowedvalueid;
    }

    /**
     * Sets the value of the allowedvalueid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALLOWEDVALUEID(String value) {
        this.allowedvalueid = value;
    }

    /**
     * Gets the value of the allowedvaluename property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowedvaluename property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getALLOWEDVALUENAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ALLOWEDVALUENAME }
     * 
     * 
     */
    public List<ALLOWEDVALUENAME> getALLOWEDVALUENAME() {
        if (allowedvaluename == null) {
            allowedvaluename = new ArrayList<ALLOWEDVALUENAME>();
        }
        return this.allowedvaluename;
    }

    /**
     * Gets the value of the allowedvalueversion property.
     * 
     * @return
     *     possible object is
     *     {@link TypeVERSION }
     *     
     */
    public TypeVERSION getALLOWEDVALUEVERSION() {
        return allowedvalueversion;
    }

    /**
     * Sets the value of the allowedvalueversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeVERSION }
     *     
     */
    public void setALLOWEDVALUEVERSION(TypeVERSION value) {
        this.allowedvalueversion = value;
    }

    /**
     * Gets the value of the allowedvalueshortname property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowedvalueshortname property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getALLOWEDVALUESHORTNAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ALLOWEDVALUESHORTNAME }
     * 
     * 
     */
    public List<ALLOWEDVALUESHORTNAME> getALLOWEDVALUESHORTNAME() {
        if (allowedvalueshortname == null) {
            allowedvalueshortname = new ArrayList<ALLOWEDVALUESHORTNAME>();
        }
        return this.allowedvalueshortname;
    }

    /**
     * Gets the value of the allowedvaluedescr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowedvaluedescr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getALLOWEDVALUEDESCR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ALLOWEDVALUEDESCR }
     * 
     * 
     */
    public List<ALLOWEDVALUEDESCR> getALLOWEDVALUEDESCR() {
        if (allowedvaluedescr == null) {
            allowedvaluedescr = new ArrayList<ALLOWEDVALUEDESCR>();
        }
        return this.allowedvaluedescr;
    }

    /**
     * Gets the value of the allowedvaluesynonyms property.
     * 
     * @return
     *     possible object is
     *     {@link ALLOWEDVALUESYNONYMS }
     *     
     */
    public ALLOWEDVALUESYNONYMS getALLOWEDVALUESYNONYMS() {
        return allowedvaluesynonyms;
    }

    /**
     * Sets the value of the allowedvaluesynonyms property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALLOWEDVALUESYNONYMS }
     *     
     */
    public void setALLOWEDVALUESYNONYMS(ALLOWEDVALUESYNONYMS value) {
        this.allowedvaluesynonyms = value;
    }

    /**
     * Gets the value of the allowedvaluesource property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSOURCE }
     *     
     */
    public TypeSOURCE getALLOWEDVALUESOURCE() {
        return allowedvaluesource;
    }

    /**
     * Sets the value of the allowedvaluesource property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSOURCE }
     *     
     */
    public void setALLOWEDVALUESOURCE(TypeSOURCE value) {
        this.allowedvaluesource = value;
    }

}
