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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}UNIT_ID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}UNIT_NAME" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}UNIT_SHORTNAME" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}UNIT_DESCR" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}UNIT_CODE" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}UNIT_URI" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="system">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.bmecat.org/bmecat/2005}dtSTRING">
 *             &lt;maxLength value="20"/>
 *             &lt;minLength value="1"/>
 *             &lt;pattern value="si|unece|\w{1,20}"/>
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
    "unitid",
    "unitname",
    "unitshortname",
    "unitdescr",
    "unitcode",
    "unituri"
})
@XmlRootElement(name = "UNIT")
public class UNIT {

    @XmlElement(name = "UNIT_ID", required = true)
    protected String unitid;
    @XmlElement(name = "UNIT_NAME")
    protected List<UNITNAME> unitname;
    @XmlElement(name = "UNIT_SHORTNAME")
    protected List<UNITSHORTNAME> unitshortname;
    @XmlElement(name = "UNIT_DESCR")
    protected List<UNITDESCR> unitdescr;
    @XmlElement(name = "UNIT_CODE")
    protected String unitcode;
    @XmlElement(name = "UNIT_URI")
    protected String unituri;
    @XmlAttribute(name = "system")
    protected String system;

    /**
     * Gets the value of the unitid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITID() {
        return unitid;
    }

    /**
     * Sets the value of the unitid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITID(String value) {
        this.unitid = value;
    }

    /**
     * Gets the value of the unitname property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unitname property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUNITNAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UNITNAME }
     * 
     * 
     */
    public List<UNITNAME> getUNITNAME() {
        if (unitname == null) {
            unitname = new ArrayList<UNITNAME>();
        }
        return this.unitname;
    }

    /**
     * Gets the value of the unitshortname property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unitshortname property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUNITSHORTNAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UNITSHORTNAME }
     * 
     * 
     */
    public List<UNITSHORTNAME> getUNITSHORTNAME() {
        if (unitshortname == null) {
            unitshortname = new ArrayList<UNITSHORTNAME>();
        }
        return this.unitshortname;
    }

    /**
     * Gets the value of the unitdescr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unitdescr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUNITDESCR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UNITDESCR }
     * 
     * 
     */
    public List<UNITDESCR> getUNITDESCR() {
        if (unitdescr == null) {
            unitdescr = new ArrayList<UNITDESCR>();
        }
        return this.unitdescr;
    }

    /**
     * Gets the value of the unitcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITCODE() {
        return unitcode;
    }

    /**
     * Sets the value of the unitcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITCODE(String value) {
        this.unitcode = value;
    }

    /**
     * Gets the value of the unituri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITURI() {
        return unituri;
    }

    /**
     * Sets the value of the unituri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITURI(String value) {
        this.unituri = value;
    }

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystem(String value) {
        this.system = value;
    }

}