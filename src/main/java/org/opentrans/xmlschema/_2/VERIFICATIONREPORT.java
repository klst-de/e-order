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


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}VERIFICATION_ATTACHMENT"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}VERIFICATION_PROTOCOL"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}VERIFICATION_XMLREPORT"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "verificationattachment",
    "verificationprotocol",
    "verificationxmlreport"
})
@XmlRootElement(name = "VERIFICATION_REPORT")
public class VERIFICATIONREPORT {

    @XmlElement(name = "VERIFICATION_ATTACHMENT")
    protected VERIFICATIONATTACHMENT verificationattachment;
    @XmlElement(name = "VERIFICATION_PROTOCOL")
    protected VERIFICATIONPROTOCOL verificationprotocol;
    @XmlElement(name = "VERIFICATION_XMLREPORT")
    protected VERIFICATIONXMLREPORT verificationxmlreport;

    /**
     * Gets the value of the verificationattachment property.
     * 
     * @return
     *     possible object is
     *     {@link VERIFICATIONATTACHMENT }
     *     
     */
    public VERIFICATIONATTACHMENT getVERIFICATIONATTACHMENT() {
        return verificationattachment;
    }

    /**
     * Sets the value of the verificationattachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link VERIFICATIONATTACHMENT }
     *     
     */
    public void setVERIFICATIONATTACHMENT(VERIFICATIONATTACHMENT value) {
        this.verificationattachment = value;
    }

    /**
     * Gets the value of the verificationprotocol property.
     * 
     * @return
     *     possible object is
     *     {@link VERIFICATIONPROTOCOL }
     *     
     */
    public VERIFICATIONPROTOCOL getVERIFICATIONPROTOCOL() {
        return verificationprotocol;
    }

    /**
     * Sets the value of the verificationprotocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link VERIFICATIONPROTOCOL }
     *     
     */
    public void setVERIFICATIONPROTOCOL(VERIFICATIONPROTOCOL value) {
        this.verificationprotocol = value;
    }

    /**
     * Gets the value of the verificationxmlreport property.
     * 
     * @return
     *     possible object is
     *     {@link VERIFICATIONXMLREPORT }
     *     
     */
    public VERIFICATIONXMLREPORT getVERIFICATIONXMLREPORT() {
        return verificationxmlreport;
    }

    /**
     * Sets the value of the verificationxmlreport property.
     * 
     * @param value
     *     allowed object is
     *     {@link VERIFICATIONXMLREPORT }
     *     
     */
    public void setVERIFICATIONXMLREPORT(VERIFICATIONXMLREPORT value) {
        this.verificationxmlreport = value;
    }

}