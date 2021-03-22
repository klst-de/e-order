//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import java.math.BigInteger;
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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGE_SEQUENCE" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGE_NAME" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGE_TYPE" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGE_DESCR" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGE_VALUE" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ALLOW_OR_CHARGE_BASE" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.bmecat.org/bmecat/2005}dtSTRING">
 *             &lt;enumeration value="allowance"/>
 *             &lt;enumeration value="surcharge"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "alloworchargesequence",
    "alloworchargename",
    "alloworchargetype",
    "alloworchargedescr",
    "alloworchargevalue",
    "alloworchargebase"
})
@XmlRootElement(name = "ALLOW_OR_CHARGE")
public class ALLOWORCHARGE {

    @XmlElement(name = "ALLOW_OR_CHARGE_SEQUENCE")
    protected BigInteger alloworchargesequence;
    @XmlElement(name = "ALLOW_OR_CHARGE_NAME")
    protected List<ALLOWORCHARGENAME> alloworchargename;
    @XmlElement(name = "ALLOW_OR_CHARGE_TYPE")
    protected String alloworchargetype;
    @XmlElement(name = "ALLOW_OR_CHARGE_DESCR")
    protected List<ALLOWORCHARGEDESCR> alloworchargedescr;
    @XmlElement(name = "ALLOW_OR_CHARGE_VALUE")
    protected ALLOWORCHARGEVALUE alloworchargevalue;
    @XmlElement(name = "ALLOW_OR_CHARGE_BASE")
    protected Float alloworchargebase;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the alloworchargesequence property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getALLOWORCHARGESEQUENCE() {
        return alloworchargesequence;
    }

    /**
     * Sets the value of the alloworchargesequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setALLOWORCHARGESEQUENCE(BigInteger value) {
        this.alloworchargesequence = value;
    }

    /**
     * Gets the value of the alloworchargename property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alloworchargename property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getALLOWORCHARGENAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ALLOWORCHARGENAME }
     * 
     * 
     */
    public List<ALLOWORCHARGENAME> getALLOWORCHARGENAME() {
        if (alloworchargename == null) {
            alloworchargename = new ArrayList<ALLOWORCHARGENAME>();
        }
        return this.alloworchargename;
    }

    /**
     * Gets the value of the alloworchargetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALLOWORCHARGETYPE() {
        return alloworchargetype;
    }

    /**
     * Sets the value of the alloworchargetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALLOWORCHARGETYPE(String value) {
        this.alloworchargetype = value;
    }

    /**
     * Gets the value of the alloworchargedescr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alloworchargedescr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getALLOWORCHARGEDESCR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ALLOWORCHARGEDESCR }
     * 
     * 
     */
    public List<ALLOWORCHARGEDESCR> getALLOWORCHARGEDESCR() {
        if (alloworchargedescr == null) {
            alloworchargedescr = new ArrayList<ALLOWORCHARGEDESCR>();
        }
        return this.alloworchargedescr;
    }

    /**
     * Gets the value of the alloworchargevalue property.
     * 
     * @return
     *     possible object is
     *     {@link ALLOWORCHARGEVALUE }
     *     
     */
    public ALLOWORCHARGEVALUE getALLOWORCHARGEVALUE() {
        return alloworchargevalue;
    }

    /**
     * Sets the value of the alloworchargevalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALLOWORCHARGEVALUE }
     *     
     */
    public void setALLOWORCHARGEVALUE(ALLOWORCHARGEVALUE value) {
        this.alloworchargevalue = value;
    }

    /**
     * Gets the value of the alloworchargebase property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getALLOWORCHARGEBASE() {
        return alloworchargebase;
    }

    /**
     * Sets the value of the alloworchargebase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setALLOWORCHARGEBASE(Float value) {
        this.alloworchargebase = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
