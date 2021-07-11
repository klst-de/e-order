//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.bmecat.bmecat._2005.MANUFACTURERTYPEDESCR;
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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}MANUFACTURER_IDREF"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}MANUFACTURER_PID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}MANUFACTURER_TYPE_DESCR" maxOccurs="unbounded" minOccurs="0"/>
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
    "manufactureridref",
    "manufacturerpid",
    "manufacturertypedescr"
})
@XmlRootElement(name = "MANUFACTURER_INFO")
public class MANUFACTURERINFO {

    @XmlElement(name = "MANUFACTURER_IDREF", namespace = "http://www.bmecat.org/bmecat/2005", required = true)
    protected TypePARTYID manufactureridref;
    @XmlElement(name = "MANUFACTURER_PID", namespace = "http://www.bmecat.org/bmecat/2005", required = true)
    protected String manufacturerpid;
    @XmlElement(name = "MANUFACTURER_TYPE_DESCR", namespace = "http://www.bmecat.org/bmecat/2005")
    protected List<MANUFACTURERTYPEDESCR> manufacturertypedescr;

    /**
     * Gets the value of the manufactureridref property.
     * 
     * @return
     *     possible object is
     *     {@link TypePARTYID }
     *     
     */
    public TypePARTYID getMANUFACTURERIDREF() {
        return manufactureridref;
    }

    /**
     * Sets the value of the manufactureridref property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypePARTYID }
     *     
     */
    public void setMANUFACTURERIDREF(TypePARTYID value) {
        this.manufactureridref = value;
    }

    /**
     * Gets the value of the manufacturerpid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANUFACTURERPID() {
        return manufacturerpid;
    }

    /**
     * Sets the value of the manufacturerpid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANUFACTURERPID(String value) {
        this.manufacturerpid = value;
    }

    /**
     * Gets the value of the manufacturertypedescr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the manufacturertypedescr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMANUFACTURERTYPEDESCR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MANUFACTURERTYPEDESCR }
     * 
     * 
     */
    public List<MANUFACTURERTYPEDESCR> getMANUFACTURERTYPEDESCR() {
        if (manufacturertypedescr == null) {
            manufacturertypedescr = new ArrayList<MANUFACTURERTYPEDESCR>();
        }
        return this.manufacturertypedescr;
    }

}
