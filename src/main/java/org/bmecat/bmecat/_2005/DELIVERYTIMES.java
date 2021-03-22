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
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.bmecat.org/bmecat/2005}TERRITORY" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{http://www.bmecat.org/bmecat/2005}AREA_REFS" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}TIME_SPAN" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}LEADTIME" minOccurs="0"/>
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
    "territory",
    "arearefs",
    "timespan",
    "leadtime"
})
@XmlRootElement(name = "DELIVERY_TIMES")
public class DELIVERYTIMES {

    @XmlElement(name = "TERRITORY")
    protected List<String> territory;
    @XmlElement(name = "AREA_REFS")
    protected AREAREFS arearefs;
    @XmlElement(name = "TIME_SPAN", required = true)
    protected List<TypeTIMESPAN> timespan;
    @XmlElement(name = "LEADTIME")
    protected Float leadtime;

    /**
     * Gets the value of the territory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the territory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTERRITORY().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTERRITORY() {
        if (territory == null) {
            territory = new ArrayList<String>();
        }
        return this.territory;
    }

    /**
     * Gets the value of the arearefs property.
     * 
     * @return
     *     possible object is
     *     {@link AREAREFS }
     *     
     */
    public AREAREFS getAREAREFS() {
        return arearefs;
    }

    /**
     * Sets the value of the arearefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AREAREFS }
     *     
     */
    public void setAREAREFS(AREAREFS value) {
        this.arearefs = value;
    }

    /**
     * Gets the value of the timespan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timespan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTIMESPAN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeTIMESPAN }
     * 
     * 
     */
    public List<TypeTIMESPAN> getTIMESPAN() {
        if (timespan == null) {
            timespan = new ArrayList<TypeTIMESPAN>();
        }
        return this.timespan;
    }

    /**
     * Gets the value of the leadtime property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getLEADTIME() {
        return leadtime;
    }

    /**
     * Sets the value of the leadtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setLEADTIME(Float value) {
        this.leadtime = value;
    }

}
