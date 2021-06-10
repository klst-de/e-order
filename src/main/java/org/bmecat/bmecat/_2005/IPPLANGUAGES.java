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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}LANGUAGE" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="occurence" type="{http://www.bmecat.org/bmecat/2005}typeIPP_occurence" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "language"
})
@XmlRootElement(name = "IPP_LANGUAGES")
public class IPPLANGUAGES {

    @XmlElement(name = "LANGUAGE")
    protected List<LANGUAGE> language;
    @XmlAttribute(name = "occurence")
    protected TypeIPPOccurence occurence;

    /**
     * Gets the value of the language property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the language property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLANGUAGE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LANGUAGE }
     * 
     * 
     */
    public List<LANGUAGE> getLANGUAGE() {
        if (language == null) {
            language = new ArrayList<LANGUAGE>();
        }
        return this.language;
    }

    /**
     * Gets the value of the occurence property.
     * 
     * @return
     *     possible object is
     *     {@link TypeIPPOccurence }
     *     
     */
    public TypeIPPOccurence getOccurence() {
        return occurence;
    }

    /**
     * Sets the value of the occurence property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeIPPOccurence }
     *     
     */
    public void setOccurence(TypeIPPOccurence value) {
        this.occurence = value;
    }

}