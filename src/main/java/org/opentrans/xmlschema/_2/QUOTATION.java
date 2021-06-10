//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}QUOTATION_HEADER"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}QUOTATION_ITEM_LIST"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}QUOTATION_SUMMARY"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.opentrans.org/XMLSchema/2.1}typeOPENTRANS_version" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "quotationheader",
    "quotationitemlist",
    "quotationsummary"
})
@XmlRootElement(name = "QUOTATION")
public class QUOTATION {

    @XmlElement(name = "QUOTATION_HEADER", required = true)
    protected QUOTATIONHEADER quotationheader;
    @XmlElement(name = "QUOTATION_ITEM_LIST", required = true)
    protected QUOTATIONITEMLIST quotationitemlist;
    @XmlElement(name = "QUOTATION_SUMMARY", required = true)
    protected QUOTATIONSUMMARY quotationsummary;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the quotationheader property.
     * 
     * @return
     *     possible object is
     *     {@link QUOTATIONHEADER }
     *     
     */
    public QUOTATIONHEADER getQUOTATIONHEADER() {
        return quotationheader;
    }

    /**
     * Sets the value of the quotationheader property.
     * 
     * @param value
     *     allowed object is
     *     {@link QUOTATIONHEADER }
     *     
     */
    public void setQUOTATIONHEADER(QUOTATIONHEADER value) {
        this.quotationheader = value;
    }

    /**
     * Gets the value of the quotationitemlist property.
     * 
     * @return
     *     possible object is
     *     {@link QUOTATIONITEMLIST }
     *     
     */
    public QUOTATIONITEMLIST getQUOTATIONITEMLIST() {
        return quotationitemlist;
    }

    /**
     * Sets the value of the quotationitemlist property.
     * 
     * @param value
     *     allowed object is
     *     {@link QUOTATIONITEMLIST }
     *     
     */
    public void setQUOTATIONITEMLIST(QUOTATIONITEMLIST value) {
        this.quotationitemlist = value;
    }

    /**
     * Gets the value of the quotationsummary property.
     * 
     * @return
     *     possible object is
     *     {@link QUOTATIONSUMMARY }
     *     
     */
    public QUOTATIONSUMMARY getQUOTATIONSUMMARY() {
        return quotationsummary;
    }

    /**
     * Sets the value of the quotationsummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link QUOTATIONSUMMARY }
     *     
     */
    public void setQUOTATIONSUMMARY(QUOTATIONSUMMARY value) {
        this.quotationsummary = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}