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
import org.bmecat.bmecat._2005.TypePARTYID;


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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}VERIFICATION_PARTY_IDREF"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}VERIFICATION_SUCCESS"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}VERIFICATION_REPORT"/>
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
    "verificationpartyidref",
    "verificationsuccess",
    "verificationreport"
})
@XmlRootElement(name = "VERIFICATION")
public class VERIFICATION {

    @XmlElement(name = "VERIFICATION_PARTY_IDREF", required = true)
    protected TypePARTYID verificationpartyidref;
    @XmlElement(name = "VERIFICATION_SUCCESS", required = true)
    protected String verificationsuccess;
    @XmlElement(name = "VERIFICATION_REPORT", required = true)
    protected VERIFICATIONREPORT verificationreport;

    /**
     * Gets the value of the verificationpartyidref property.
     * 
     * @return
     *     possible object is
     *     {@link TypePARTYID }
     *     
     */
    public TypePARTYID getVERIFICATIONPARTYIDREF() {
        return verificationpartyidref;
    }

    /**
     * Sets the value of the verificationpartyidref property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypePARTYID }
     *     
     */
    public void setVERIFICATIONPARTYIDREF(TypePARTYID value) {
        this.verificationpartyidref = value;
    }

    /**
     * Gets the value of the verificationsuccess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVERIFICATIONSUCCESS() {
        return verificationsuccess;
    }

    /**
     * Sets the value of the verificationsuccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVERIFICATIONSUCCESS(String value) {
        this.verificationsuccess = value;
    }

    /**
     * Gets the value of the verificationreport property.
     * 
     * @return
     *     possible object is
     *     {@link VERIFICATIONREPORT }
     *     
     */
    public VERIFICATIONREPORT getVERIFICATIONREPORT() {
        return verificationreport;
    }

    /**
     * Sets the value of the verificationreport property.
     * 
     * @param value
     *     allowed object is
     *     {@link VERIFICATIONREPORT }
     *     
     */
    public void setVERIFICATIONREPORT(VERIFICATIONREPORT value) {
        this.verificationreport = value;
    }

}
