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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}IPP_PARAM_DEFINITION" maxOccurs="unbounded"/>
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
    "ippparamdefinition"
})
@XmlRootElement(name = "IPP_INBOUND_PARAMS")
public class IPPINBOUNDPARAMS {

    @XmlElement(name = "IPP_PARAM_DEFINITION", required = true)
    protected List<IPPPARAMDEFINITION> ippparamdefinition;

    /**
     * Gets the value of the ippparamdefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ippparamdefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIPPPARAMDEFINITION().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IPPPARAMDEFINITION }
     * 
     * 
     */
    public List<IPPPARAMDEFINITION> getIPPPARAMDEFINITION() {
        if (ippparamdefinition == null) {
            ippparamdefinition = new ArrayList<IPPPARAMDEFINITION>();
        }
        return this.ippparamdefinition;
    }

}