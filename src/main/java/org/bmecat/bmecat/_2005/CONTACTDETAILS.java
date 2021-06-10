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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CONTACT_ID"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CONTACT_NAME" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FIRST_NAME" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}TITLE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}ACADEMIC_TITLE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CONTACT_ROLE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CONTACT_DESCR" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}PHONE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FAX" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}URL" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}EMAILS" minOccurs="0"/>
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
    "contactid",
    "contactname",
    "firstname",
    "title",
    "academictitle",
    "contactrole",
    "contactdescr",
    "phone",
    "fax",
    "url",
    "emails"
})
@XmlRootElement(name = "CONTACT_DETAILS")
public class CONTACTDETAILS {

    @XmlElement(name = "CONTACT_ID", required = true)
    protected String contactid;
    @XmlElement(name = "CONTACT_NAME", required = true)
    protected List<CONTACTNAME> contactname;
    @XmlElement(name = "FIRST_NAME")
    protected List<FIRSTNAME> firstname;
    @XmlElement(name = "TITLE")
    protected List<TITLE> title;
    @XmlElement(name = "ACADEMIC_TITLE")
    protected List<ACADEMICTITLE> academictitle;
    @XmlElement(name = "CONTACT_ROLE")
    protected List<CONTACTROLE> contactrole;
    @XmlElement(name = "CONTACT_DESCR")
    protected List<CONTACTDESCR> contactdescr;
    @XmlElement(name = "PHONE")
    protected List<PHONE> phone;
    @XmlElement(name = "FAX")
    protected List<FAX> fax;
    @XmlElement(name = "URL")
    protected String url;
    @XmlElement(name = "EMAILS")
    protected EMAILS emails;

    /**
     * Gets the value of the contactid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTID() {
        return contactid;
    }

    /**
     * Sets the value of the contactid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTID(String value) {
        this.contactid = value;
    }

    /**
     * Gets the value of the contactname property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactname property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONTACTNAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CONTACTNAME }
     * 
     * 
     */
    public List<CONTACTNAME> getCONTACTNAME() {
        if (contactname == null) {
            contactname = new ArrayList<CONTACTNAME>();
        }
        return this.contactname;
    }

    /**
     * Gets the value of the firstname property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the firstname property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFIRSTNAME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FIRSTNAME }
     * 
     * 
     */
    public List<FIRSTNAME> getFIRSTNAME() {
        if (firstname == null) {
            firstname = new ArrayList<FIRSTNAME>();
        }
        return this.firstname;
    }

    /**
     * Gets the value of the title property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the title property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTITLE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TITLE }
     * 
     * 
     */
    public List<TITLE> getTITLE() {
        if (title == null) {
            title = new ArrayList<TITLE>();
        }
        return this.title;
    }

    /**
     * Gets the value of the academictitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the academictitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getACADEMICTITLE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ACADEMICTITLE }
     * 
     * 
     */
    public List<ACADEMICTITLE> getACADEMICTITLE() {
        if (academictitle == null) {
            academictitle = new ArrayList<ACADEMICTITLE>();
        }
        return this.academictitle;
    }

    /**
     * Gets the value of the contactrole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactrole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONTACTROLE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CONTACTROLE }
     * 
     * 
     */
    public List<CONTACTROLE> getCONTACTROLE() {
        if (contactrole == null) {
            contactrole = new ArrayList<CONTACTROLE>();
        }
        return this.contactrole;
    }

    /**
     * Gets the value of the contactdescr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactdescr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONTACTDESCR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CONTACTDESCR }
     * 
     * 
     */
    public List<CONTACTDESCR> getCONTACTDESCR() {
        if (contactdescr == null) {
            contactdescr = new ArrayList<CONTACTDESCR>();
        }
        return this.contactdescr;
    }

    /**
     * Gets the value of the phone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPHONE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PHONE }
     * 
     * 
     */
    public List<PHONE> getPHONE() {
        if (phone == null) {
            phone = new ArrayList<PHONE>();
        }
        return this.phone;
    }

    /**
     * Gets the value of the fax property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fax property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFAX().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FAX }
     * 
     * 
     */
    public List<FAX> getFAX() {
        if (fax == null) {
            fax = new ArrayList<FAX>();
        }
        return this.fax;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the emails property.
     * 
     * @return
     *     possible object is
     *     {@link EMAILS }
     *     
     */
    public EMAILS getEMAILS() {
        return emails;
    }

    /**
     * Sets the value of the emails property.
     * 
     * @param value
     *     allowed object is
     *     {@link EMAILS }
     *     
     */
    public void setEMAILS(EMAILS value) {
        this.emails = value;
    }

}