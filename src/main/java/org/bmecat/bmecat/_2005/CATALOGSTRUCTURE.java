//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.bmecat.bmecat._2005;

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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}GROUP_ID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}GROUP_NAME" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}GROUP_DESCRIPTION" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}PARENT_ID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}GROUP_ORDER" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}MIME_INFO" minOccurs="0"/>
 *         &lt;element name="USER_DEFINED_EXTENSIONS" type="{http://www.bmecat.org/bmecat/2005}udxCATALOGGROUP" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}KEYWORD" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.bmecat.org/bmecat/2005}dtSTRING">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="20"/>
 *             &lt;enumeration value="leaf"/>
 *             &lt;enumeration value="node"/>
 *             &lt;enumeration value="root"/>
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
    "groupid",
    "groupname",
    "groupdescription",
    "parentid",
    "grouporder",
    "mimeinfo",
    "userdefinedextensions",
    "keyword"
})
@XmlRootElement(name = "CATALOG_STRUCTURE")
public class CATALOGSTRUCTURE {

    @XmlElement(name = "GROUP_ID", required = true)
    protected String groupid;
    @XmlElement(name = "GROUP_NAME", required = true)
    protected List<GROUPNAME> groupname;
    @XmlElement(name = "GROUP_DESCRIPTION")
    protected List<GROUPDESCRIPTION> groupdescription;
    @XmlElement(name = "PARENT_ID", required = true)
    protected String parentid;
    @XmlElement(name = "GROUP_ORDER")
    protected BigInteger grouporder;
    @XmlElement(name = "MIME_INFO")
    protected MIMEINFO mimeinfo;
    @XmlElement(name = "USER_DEFINED_EXTENSIONS")
    protected UdxCATALOGGROUP userdefinedextensions;
    @XmlElement(name = "KEYWORD")
    protected List<KEYWORD> keyword;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the groupid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGROUPID() {
        return groupid;
    }

    /**
     * Sets the value of the groupid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGROUPID(String value) {
        this.groupid = value;
    }

    /**
     * Gets the value of the groupname property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupname property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGROUPNAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GROUPNAME }
     * 
     * 
     */
    public List<GROUPNAME> getGROUPNAME() {
        if (groupname == null) {
            groupname = new ArrayList<GROUPNAME>();
        }
        return this.groupname;
    }

    /**
     * Gets the value of the groupdescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupdescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGROUPDESCRIPTION().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GROUPDESCRIPTION }
     * 
     * 
     */
    public List<GROUPDESCRIPTION> getGROUPDESCRIPTION() {
        if (groupdescription == null) {
            groupdescription = new ArrayList<GROUPDESCRIPTION>();
        }
        return this.groupdescription;
    }

    /**
     * Gets the value of the parentid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARENTID() {
        return parentid;
    }

    /**
     * Sets the value of the parentid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARENTID(String value) {
        this.parentid = value;
    }

    /**
     * Gets the value of the grouporder property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGROUPORDER() {
        return grouporder;
    }

    /**
     * Sets the value of the grouporder property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGROUPORDER(BigInteger value) {
        this.grouporder = value;
    }

    /**
     * Gets the value of the mimeinfo property.
     * 
     * @return
     *     possible object is
     *     {@link MIMEINFO }
     *     
     */
    public MIMEINFO getMIMEINFO() {
        return mimeinfo;
    }

    /**
     * Sets the value of the mimeinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MIMEINFO }
     *     
     */
    public void setMIMEINFO(MIMEINFO value) {
        this.mimeinfo = value;
    }

    /**
     * Gets the value of the userdefinedextensions property.
     * 
     * @return
     *     possible object is
     *     {@link UdxCATALOGGROUP }
     *     
     */
    public UdxCATALOGGROUP getUSERDEFINEDEXTENSIONS() {
        return userdefinedextensions;
    }

    /**
     * Sets the value of the userdefinedextensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link UdxCATALOGGROUP }
     *     
     */
    public void setUSERDEFINEDEXTENSIONS(UdxCATALOGGROUP value) {
        this.userdefinedextensions = value;
    }

    /**
     * Gets the value of the keyword property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyword property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKEYWORD().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KEYWORD }
     * 
     * 
     */
    public List<KEYWORD> getKEYWORD() {
        if (keyword == null) {
            keyword = new ArrayList<KEYWORD>();
        }
        return this.keyword;
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