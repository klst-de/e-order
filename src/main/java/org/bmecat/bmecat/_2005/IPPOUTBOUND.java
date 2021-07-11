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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}IPP_OUTBOUND_FORMAT"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}IPP_OUTBOUND_PARAMS" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}IPP_URI" maxOccurs="unbounded"/>
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
    "ippoutboundformat",
    "ippoutboundparams",
    "ippuri"
})
@XmlRootElement(name = "IPP_OUTBOUND")
public class IPPOUTBOUND {

    @XmlElement(name = "IPP_OUTBOUND_FORMAT", required = true)
    protected String ippoutboundformat;
    @XmlElement(name = "IPP_OUTBOUND_PARAMS")
    protected IPPOUTBOUNDPARAMS ippoutboundparams;
    @XmlElement(name = "IPP_URI", required = true)
    protected List<IPPURI> ippuri;

    /**
     * Gets the value of the ippoutboundformat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPPOUTBOUNDFORMAT() {
        return ippoutboundformat;
    }

    /**
     * Sets the value of the ippoutboundformat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPPOUTBOUNDFORMAT(String value) {
        this.ippoutboundformat = value;
    }

    /**
     * Gets the value of the ippoutboundparams property.
     * 
     * @return
     *     possible object is
     *     {@link IPPOUTBOUNDPARAMS }
     *     
     */
    public IPPOUTBOUNDPARAMS getIPPOUTBOUNDPARAMS() {
        return ippoutboundparams;
    }

    /**
     * Sets the value of the ippoutboundparams property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPPOUTBOUNDPARAMS }
     *     
     */
    public void setIPPOUTBOUNDPARAMS(IPPOUTBOUNDPARAMS value) {
        this.ippoutboundparams = value;
    }

    /**
     * Gets the value of the ippuri property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ippuri property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIPPURI().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IPPURI }
     * 
     * 
     */
    public List<IPPURI> getIPPURI() {
        if (ippuri == null) {
            ippuri = new ArrayList<IPPURI>();
        }
        return this.ippuri;
    }

}
