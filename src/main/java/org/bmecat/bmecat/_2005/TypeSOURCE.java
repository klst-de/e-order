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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSOURCE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSOURCE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}SOURCE_NAME" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}SOURCE_URI" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}PARTY_IDREF" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSOURCE", propOrder = {
    "sourcename",
    "sourceuri",
    "partyidref"
})
public class TypeSOURCE {

    @XmlElement(name = "SOURCE_NAME")
    protected List<SOURCENAME> sourcename;
    @XmlElement(name = "SOURCE_URI")
    protected String sourceuri;
    @XmlElement(name = "PARTY_IDREF")
    protected TypePARTYID partyidref;

    /**
     * Gets the value of the sourcename property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourcename property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSOURCENAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SOURCENAME }
     * 
     * 
     */
    public List<SOURCENAME> getSOURCENAME() {
        if (sourcename == null) {
            sourcename = new ArrayList<SOURCENAME>();
        }
        return this.sourcename;
    }

    /**
     * Gets the value of the sourceuri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCEURI() {
        return sourceuri;
    }

    /**
     * Sets the value of the sourceuri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCEURI(String value) {
        this.sourceuri = value;
    }

    /**
     * Gets the value of the partyidref property.
     * 
     * @return
     *     possible object is
     *     {@link TypePARTYID }
     *     
     */
    public TypePARTYID getPARTYIDREF() {
        return partyidref;
    }

    /**
     * Sets the value of the partyidref property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypePARTYID }
     *     
     */
    public void setPARTYIDREF(TypePARTYID value) {
        this.partyidref = value;
    }

}
