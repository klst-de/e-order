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
 *         &lt;choice>
 *           &lt;element ref="{http://www.bmecat.org/bmecat/2005}FVALUE" maxOccurs="unbounded"/>
 *           &lt;element ref="{http://www.bmecat.org/bmecat/2005}VALUE_IDREF" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}SUPPLIER_AID_SUPPLEMENT"/>
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
    "fvalue",
    "valueidref",
    "supplieraidsupplement"
})
@XmlRootElement(name = "VARIANT")
public class VARIANT {

    @XmlElement(name = "FVALUE")
    protected List<FVALUE> fvalue;
    @XmlElement(name = "VALUE_IDREF")
    protected List<String> valueidref;
    @XmlElement(name = "SUPPLIER_AID_SUPPLEMENT", required = true)
    protected String supplieraidsupplement;

    /**
     * Gets the value of the fvalue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fvalue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFVALUE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FVALUE }
     * 
     * 
     */
    public List<FVALUE> getFVALUE() {
        if (fvalue == null) {
            fvalue = new ArrayList<FVALUE>();
        }
        return this.fvalue;
    }

    /**
     * Gets the value of the valueidref property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueidref property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVALUEIDREF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getVALUEIDREF() {
        if (valueidref == null) {
            valueidref = new ArrayList<String>();
        }
        return this.valueidref;
    }

    /**
     * Gets the value of the supplieraidsupplement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUPPLIERAIDSUPPLEMENT() {
        return supplieraidsupplement;
    }

    /**
     * Sets the value of the supplieraidsupplement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUPPLIERAIDSUPPLEMENT(String value) {
        this.supplieraidsupplement = value;
    }

}
