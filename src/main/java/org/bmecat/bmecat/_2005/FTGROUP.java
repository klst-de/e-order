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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_GROUP_ID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_GROUP_NAME" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_GROUP_DESCR" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_GROUP_PARENT_ID" maxOccurs="unbounded" minOccurs="0"/>
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
    "ftgroupid",
    "ftgroupname",
    "ftgroupdescr",
    "ftgroupparentid"
})
@XmlRootElement(name = "FT_GROUP")
public class FTGROUP {

    @XmlElement(name = "FT_GROUP_ID", required = true)
    protected String ftgroupid;
    @XmlElement(name = "FT_GROUP_NAME")
    protected List<FTGROUPNAME> ftgroupname;
    @XmlElement(name = "FT_GROUP_DESCR")
    protected List<FTGROUPDESCR> ftgroupdescr;
    @XmlElement(name = "FT_GROUP_PARENT_ID")
    protected List<String> ftgroupparentid;

    /**
     * Gets the value of the ftgroupid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTGROUPID() {
        return ftgroupid;
    }

    /**
     * Sets the value of the ftgroupid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTGROUPID(String value) {
        this.ftgroupid = value;
    }

    /**
     * Gets the value of the ftgroupname property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftgroupname property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTGROUPNAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTGROUPNAME }
     * 
     * 
     */
    public List<FTGROUPNAME> getFTGROUPNAME() {
        if (ftgroupname == null) {
            ftgroupname = new ArrayList<FTGROUPNAME>();
        }
        return this.ftgroupname;
    }

    /**
     * Gets the value of the ftgroupdescr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftgroupdescr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTGROUPDESCR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTGROUPDESCR }
     * 
     * 
     */
    public List<FTGROUPDESCR> getFTGROUPDESCR() {
        if (ftgroupdescr == null) {
            ftgroupdescr = new ArrayList<FTGROUPDESCR>();
        }
        return this.ftgroupdescr;
    }

    /**
     * Gets the value of the ftgroupparentid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftgroupparentid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTGROUPPARENTID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFTGROUPPARENTID() {
        if (ftgroupparentid == null) {
            ftgroupparentid = new ArrayList<String>();
        }
        return this.ftgroupparentid;
    }

}
