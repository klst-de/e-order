//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.bmecat.bmecat._2005;

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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}AGREEMENT_IDREF"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}AGREEMENT_LINE_IDREF" minOccurs="0"/>
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
    "agreementidref",
    "agreementlineidref"
})
@XmlRootElement(name = "AGREEMENT_REF")
public class AGREEMENTREF {

    @XmlElement(name = "AGREEMENT_IDREF", required = true)
    protected String agreementidref;
    @XmlElement(name = "AGREEMENT_LINE_IDREF")
    protected String agreementlineidref;

    /**
     * Gets the value of the agreementidref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAGREEMENTIDREF() {
        return agreementidref;
    }

    /**
     * Sets the value of the agreementidref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAGREEMENTIDREF(String value) {
        this.agreementidref = value;
    }

    /**
     * Gets the value of the agreementlineidref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAGREEMENTLINEIDREF() {
        return agreementlineidref;
    }

    /**
     * Sets the value of the agreementlineidref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAGREEMENTLINEIDREF(String value) {
        this.agreementlineidref = value;
    }

}
