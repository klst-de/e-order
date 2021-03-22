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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}HOLDER"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}BANK_ACCOUNT"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}BANK_CODE" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}BANK_NAME" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}BANK_COUNTRY" minOccurs="0"/>
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
    "holder",
    "bankaccount",
    "bankcode",
    "bankname",
    "bankcountry"
})
@XmlRootElement(name = "ACCOUNT")
public class ACCOUNT {

    @XmlElement(name = "HOLDER", required = true)
    protected String holder;
    @XmlElement(name = "BANK_ACCOUNT", required = true)
    protected BANKACCOUNT bankaccount;
    @XmlElement(name = "BANK_CODE")
    protected BANKCODE bankcode;
    @XmlElement(name = "BANK_NAME")
    protected String bankname;
    @XmlElement(name = "BANK_COUNTRY")
    protected String bankcountry;

    /**
     * Gets the value of the holder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHOLDER() {
        return holder;
    }

    /**
     * Sets the value of the holder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHOLDER(String value) {
        this.holder = value;
    }

    /**
     * Gets the value of the bankaccount property.
     * 
     * @return
     *     possible object is
     *     {@link BANKACCOUNT }
     *     
     */
    public BANKACCOUNT getBANKACCOUNT() {
        return bankaccount;
    }

    /**
     * Sets the value of the bankaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BANKACCOUNT }
     *     
     */
    public void setBANKACCOUNT(BANKACCOUNT value) {
        this.bankaccount = value;
    }

    /**
     * Gets the value of the bankcode property.
     * 
     * @return
     *     possible object is
     *     {@link BANKCODE }
     *     
     */
    public BANKCODE getBANKCODE() {
        return bankcode;
    }

    /**
     * Sets the value of the bankcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BANKCODE }
     *     
     */
    public void setBANKCODE(BANKCODE value) {
        this.bankcode = value;
    }

    /**
     * Gets the value of the bankname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKNAME() {
        return bankname;
    }

    /**
     * Sets the value of the bankname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKNAME(String value) {
        this.bankname = value;
    }

    /**
     * Gets the value of the bankcountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKCOUNTRY() {
        return bankcountry;
    }

    /**
     * Sets the value of the bankcountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKCOUNTRY(String value) {
        this.bankcountry = value;
    }

}
