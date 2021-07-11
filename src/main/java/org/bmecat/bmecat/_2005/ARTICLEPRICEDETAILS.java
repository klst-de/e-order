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
 *         &lt;choice>
 *           &lt;sequence minOccurs="0">
 *             &lt;element ref="{http://www.bmecat.org/bmecat/2005}VALID_START_DATE" minOccurs="0"/>
 *             &lt;element ref="{http://www.bmecat.org/bmecat/2005}VALID_END_DATE" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;element name="DATETIME" maxOccurs="2" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://www.bmecat.org/bmecat/2005}typeDATETIME">
 *                   &lt;attribute name="type" use="required">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.bmecat.org/bmecat/2005}dtSTRING">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="20"/>
 *                         &lt;enumeration value="valid_start_date"/>
 *                         &lt;enumeration value="valid_end_date"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/attribute>
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}DAILY_PRICE" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ARTICLE_PRICE" maxOccurs="unbounded"/>
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
    "validstartdate",
    "validenddate",
    "datetime",
    "dailyprice",
    "articleprice"
})
@XmlRootElement(name = "ARTICLE_PRICE_DETAILS")
public class ARTICLEPRICEDETAILS {

    @XmlElement(name = "VALID_START_DATE")
    protected String validstartdate;
    @XmlElement(name = "VALID_END_DATE")
    protected String validenddate;
    @XmlElement(name = "DATETIME")
    protected List<ARTICLEPRICEDETAILS.DATETIME> datetime;
    @XmlElement(name = "DAILY_PRICE")
    protected String dailyprice;
    @XmlElement(name = "ARTICLE_PRICE", required = true)
    protected List<ARTICLEPRICE> articleprice;

    /**
     * Gets the value of the validstartdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALIDSTARTDATE() {
        return validstartdate;
    }

    /**
     * Sets the value of the validstartdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALIDSTARTDATE(String value) {
        this.validstartdate = value;
    }

    /**
     * Gets the value of the validenddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALIDENDDATE() {
        return validenddate;
    }

    /**
     * Sets the value of the validenddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALIDENDDATE(String value) {
        this.validenddate = value;
    }

    /**
     * Gets the value of the datetime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datetime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDATETIME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ARTICLEPRICEDETAILS.DATETIME }
     * 
     * 
     */
    public List<ARTICLEPRICEDETAILS.DATETIME> getDATETIME() {
        if (datetime == null) {
            datetime = new ArrayList<ARTICLEPRICEDETAILS.DATETIME>();
        }
        return this.datetime;
    }

    /**
     * Gets the value of the dailyprice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDAILYPRICE() {
        return dailyprice;
    }

    /**
     * Sets the value of the dailyprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDAILYPRICE(String value) {
        this.dailyprice = value;
    }

    /**
     * Gets the value of the articleprice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the articleprice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getARTICLEPRICE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ARTICLEPRICE }
     * 
     * 
     */
    public List<ARTICLEPRICE> getARTICLEPRICE() {
        if (articleprice == null) {
            articleprice = new ArrayList<ARTICLEPRICE>();
        }
        return this.articleprice;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.bmecat.org/bmecat/2005}typeDATETIME">
     *       &lt;attribute name="type" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.bmecat.org/bmecat/2005}dtSTRING">
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="20"/>
     *             &lt;enumeration value="valid_start_date"/>
     *             &lt;enumeration value="valid_end_date"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DATETIME
        extends TypeDATETIME
    {

        @XmlAttribute(name = "type", required = true)
        protected String type;

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
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

}
