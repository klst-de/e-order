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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REMITTANCEADVICE_HEADER"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REMITTANCEADVICE_ITEM_LIST"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REMITTANCEADVICE_SUMMARY"/>
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
    "remittanceadviceheader",
    "remittanceadviceitemlist",
    "remittanceadvicesummary"
})
@XmlRootElement(name = "REMITTANCEADVICE")
public class REMITTANCEADVICE {

    @XmlElement(name = "REMITTANCEADVICE_HEADER", required = true)
    protected REMITTANCEADVICEHEADER remittanceadviceheader;
    @XmlElement(name = "REMITTANCEADVICE_ITEM_LIST", required = true)
    protected REMITTANCEADVICEITEMLIST remittanceadviceitemlist;
    @XmlElement(name = "REMITTANCEADVICE_SUMMARY", required = true)
    protected REMITTANCEADVICESUMMARY remittanceadvicesummary;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the remittanceadviceheader property.
     * 
     * @return
     *     possible object is
     *     {@link REMITTANCEADVICEHEADER }
     *     
     */
    public REMITTANCEADVICEHEADER getREMITTANCEADVICEHEADER() {
        return remittanceadviceheader;
    }

    /**
     * Sets the value of the remittanceadviceheader property.
     * 
     * @param value
     *     allowed object is
     *     {@link REMITTANCEADVICEHEADER }
     *     
     */
    public void setREMITTANCEADVICEHEADER(REMITTANCEADVICEHEADER value) {
        this.remittanceadviceheader = value;
    }

    /**
     * Gets the value of the remittanceadviceitemlist property.
     * 
     * @return
     *     possible object is
     *     {@link REMITTANCEADVICEITEMLIST }
     *     
     */
    public REMITTANCEADVICEITEMLIST getREMITTANCEADVICEITEMLIST() {
        return remittanceadviceitemlist;
    }

    /**
     * Sets the value of the remittanceadviceitemlist property.
     * 
     * @param value
     *     allowed object is
     *     {@link REMITTANCEADVICEITEMLIST }
     *     
     */
    public void setREMITTANCEADVICEITEMLIST(REMITTANCEADVICEITEMLIST value) {
        this.remittanceadviceitemlist = value;
    }

    /**
     * Gets the value of the remittanceadvicesummary property.
     * 
     * @return
     *     possible object is
     *     {@link REMITTANCEADVICESUMMARY }
     *     
     */
    public REMITTANCEADVICESUMMARY getREMITTANCEADVICESUMMARY() {
        return remittanceadvicesummary;
    }

    /**
     * Sets the value of the remittanceadvicesummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link REMITTANCEADVICESUMMARY }
     *     
     */
    public void setREMITTANCEADVICESUMMARY(REMITTANCEADVICESUMMARY value) {
        this.remittanceadvicesummary = value;
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