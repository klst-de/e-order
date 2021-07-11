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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.bmecat.bmecat._2005.DtCURRENCIES;
import org.bmecat.bmecat._2005.INTERNATIONALRESTRICTIONS;
import org.bmecat.bmecat._2005.LANGUAGE;
import org.bmecat.bmecat._2005.MIMEROOT;
import org.bmecat.bmecat._2005.TRANSPORT;


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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}QUOTATION_ID"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}QUOTATION_DATE"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}VALID_START_DATE" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}VALID_END_DATE" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}DELIVERY_DATE" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}LANGUAGE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}MIME_ROOT" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}PARTIES"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}ORDER_PARTIES_REFERENCE"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}DOCEXCHANGE_PARTIES_REFERENCE" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}AGREEMENT" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}CATALOG_REFERENCE" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CURRENCY"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}PAYMENT" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}TERMS_AND_CONDITIONS" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}TRANSPORT" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}INTERNATIONAL_RESTRICTIONS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}TERRITORY" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}MIME_INFO" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}REMARKS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}HEADER_UDX" minOccurs="0"/>
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
    "quotationid",
    "quotationdate",
    "validstartdate",
    "validenddate",
    "deliverydate",
    "language",
    "mimeroot",
    "parties",
    "orderpartiesreference",
    "docexchangepartiesreference",
    "agreement",
    "catalogreference",
    "currency",
    "payment",
    "termsandconditions",
    "transport",
    "internationalrestrictions",
    "territory",
    "mimeinfo",
    "remarks",
    "headerudx"
})
@XmlRootElement(name = "QUOTATION_INFO")
public class QUOTATIONINFO {

    @XmlElement(name = "QUOTATION_ID", required = true)
    protected String quotationid;
    @XmlElement(name = "QUOTATION_DATE", required = true)
    protected String quotationdate;
    @XmlElement(name = "VALID_START_DATE", namespace = "http://www.bmecat.org/bmecat/2005")
    protected String validstartdate;
    @XmlElement(name = "VALID_END_DATE", namespace = "http://www.bmecat.org/bmecat/2005")
    protected String validenddate;
    @XmlElement(name = "DELIVERY_DATE")
    protected DELIVERYDATE deliverydate;
    @XmlElement(name = "LANGUAGE", namespace = "http://www.bmecat.org/bmecat/2005")
    protected List<LANGUAGE> language;
    @XmlElement(name = "MIME_ROOT", namespace = "http://www.bmecat.org/bmecat/2005")
    protected MIMEROOT mimeroot;
    @XmlElement(name = "PARTIES", required = true)
    protected PARTIES parties;
    @XmlElement(name = "ORDER_PARTIES_REFERENCE", required = true)
    protected ORDERPARTIESREFERENCE orderpartiesreference;
    @XmlElement(name = "DOCEXCHANGE_PARTIES_REFERENCE")
    protected DOCEXCHANGEPARTIESREFERENCE docexchangepartiesreference;
    @XmlElement(name = "AGREEMENT")
    protected List<AGREEMENT> agreement;
    @XmlElement(name = "CATALOG_REFERENCE")
    protected CATALOGREFERENCE catalogreference;
    @XmlElement(name = "CURRENCY", namespace = "http://www.bmecat.org/bmecat/2005", required = true)
    @XmlSchemaType(name = "string")
    protected DtCURRENCIES currency;
    @XmlElement(name = "PAYMENT")
    protected PAYMENT payment;
    @XmlElement(name = "TERMS_AND_CONDITIONS")
    protected String termsandconditions;
    @XmlElement(name = "TRANSPORT", namespace = "http://www.bmecat.org/bmecat/2005")
    protected TRANSPORT transport;
    @XmlElement(name = "INTERNATIONAL_RESTRICTIONS", namespace = "http://www.bmecat.org/bmecat/2005")
    protected List<INTERNATIONALRESTRICTIONS> internationalrestrictions;
    @XmlElement(name = "TERRITORY", namespace = "http://www.bmecat.org/bmecat/2005")
    protected List<String> territory;
    @XmlElement(name = "MIME_INFO")
    protected MIMEINFO mimeinfo;
    @XmlElement(name = "REMARKS")
    protected List<REMARKS> remarks;
    @XmlElement(name = "HEADER_UDX")
    protected UdxHEADER headerudx;

    /**
     * Gets the value of the quotationid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUOTATIONID() {
        return quotationid;
    }

    /**
     * Sets the value of the quotationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUOTATIONID(String value) {
        this.quotationid = value;
    }

    /**
     * Gets the value of the quotationdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUOTATIONDATE() {
        return quotationdate;
    }

    /**
     * Sets the value of the quotationdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUOTATIONDATE(String value) {
        this.quotationdate = value;
    }

    /**
     * Gets the value of the validstartdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALIDSTARTDATE() {
        return validstartdate;
    }

    /**
     * Sets the value of the validstartdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALIDSTARTDATE(String value) {
        this.validstartdate = value;
    }

    /**
     * Gets the value of the validenddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALIDENDDATE() {
        return validenddate;
    }

    /**
     * Sets the value of the validenddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALIDENDDATE(String value) {
        this.validenddate = value;
    }

    /**
     * Gets the value of the deliverydate property.
     * 
     * @return
     *     possible object is
     *     {@link DELIVERYDATE }
     *     
     */
    public DELIVERYDATE getDELIVERYDATE() {
        return deliverydate;
    }

    /**
     * Sets the value of the deliverydate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DELIVERYDATE }
     *     
     */
    public void setDELIVERYDATE(DELIVERYDATE value) {
        this.deliverydate = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the language property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLANGUAGE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LANGUAGE }
     * 
     * 
     */
    public List<LANGUAGE> getLANGUAGE() {
        if (language == null) {
            language = new ArrayList<LANGUAGE>();
        }
        return this.language;
    }

    /**
     * Gets the value of the mimeroot property.
     * 
     * @return
     *     possible object is
     *     {@link MIMEROOT }
     *     
     */
    public MIMEROOT getMIMEROOT() {
        return mimeroot;
    }

    /**
     * Sets the value of the mimeroot property.
     * 
     * @param value
     *     allowed object is
     *     {@link MIMEROOT }
     *     
     */
    public void setMIMEROOT(MIMEROOT value) {
        this.mimeroot = value;
    }

    /**
     * Gets the value of the parties property.
     * 
     * @return
     *     possible object is
     *     {@link PARTIES }
     *     
     */
    public PARTIES getPARTIES() {
        return parties;
    }

    /**
     * Sets the value of the parties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PARTIES }
     *     
     */
    public void setPARTIES(PARTIES value) {
        this.parties = value;
    }

    /**
     * Gets the value of the orderpartiesreference property.
     * 
     * @return
     *     possible object is
     *     {@link ORDERPARTIESREFERENCE }
     *     
     */
    public ORDERPARTIESREFERENCE getORDERPARTIESREFERENCE() {
        return orderpartiesreference;
    }

    /**
     * Sets the value of the orderpartiesreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ORDERPARTIESREFERENCE }
     *     
     */
    public void setORDERPARTIESREFERENCE(ORDERPARTIESREFERENCE value) {
        this.orderpartiesreference = value;
    }

    /**
     * Gets the value of the docexchangepartiesreference property.
     * 
     * @return
     *     possible object is
     *     {@link DOCEXCHANGEPARTIESREFERENCE }
     *     
     */
    public DOCEXCHANGEPARTIESREFERENCE getDOCEXCHANGEPARTIESREFERENCE() {
        return docexchangepartiesreference;
    }

    /**
     * Sets the value of the docexchangepartiesreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link DOCEXCHANGEPARTIESREFERENCE }
     *     
     */
    public void setDOCEXCHANGEPARTIESREFERENCE(DOCEXCHANGEPARTIESREFERENCE value) {
        this.docexchangepartiesreference = value;
    }

    /**
     * Gets the value of the agreement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agreement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAGREEMENT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AGREEMENT }
     * 
     * 
     */
    public List<AGREEMENT> getAGREEMENT() {
        if (agreement == null) {
            agreement = new ArrayList<AGREEMENT>();
        }
        return this.agreement;
    }

    /**
     * Gets the value of the catalogreference property.
     * 
     * @return
     *     possible object is
     *     {@link CATALOGREFERENCE }
     *     
     */
    public CATALOGREFERENCE getCATALOGREFERENCE() {
        return catalogreference;
    }

    /**
     * Sets the value of the catalogreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link CATALOGREFERENCE }
     *     
     */
    public void setCATALOGREFERENCE(CATALOGREFERENCE value) {
        this.catalogreference = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link DtCURRENCIES }
     *     
     */
    public DtCURRENCIES getCURRENCY() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link DtCURRENCIES }
     *     
     */
    public void setCURRENCY(DtCURRENCIES value) {
        this.currency = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link PAYMENT }
     *     
     */
    public PAYMENT getPAYMENT() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link PAYMENT }
     *     
     */
    public void setPAYMENT(PAYMENT value) {
        this.payment = value;
    }

    /**
     * Gets the value of the termsandconditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTERMSANDCONDITIONS() {
        return termsandconditions;
    }

    /**
     * Sets the value of the termsandconditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTERMSANDCONDITIONS(String value) {
        this.termsandconditions = value;
    }

    /**
     * Gets the value of the transport property.
     * 
     * @return
     *     possible object is
     *     {@link TRANSPORT }
     *     
     */
    public TRANSPORT getTRANSPORT() {
        return transport;
    }

    /**
     * Sets the value of the transport property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRANSPORT }
     *     
     */
    public void setTRANSPORT(TRANSPORT value) {
        this.transport = value;
    }

    /**
     * Gets the value of the internationalrestrictions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the internationalrestrictions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINTERNATIONALRESTRICTIONS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link INTERNATIONALRESTRICTIONS }
     * 
     * 
     */
    public List<INTERNATIONALRESTRICTIONS> getINTERNATIONALRESTRICTIONS() {
        if (internationalrestrictions == null) {
            internationalrestrictions = new ArrayList<INTERNATIONALRESTRICTIONS>();
        }
        return this.internationalrestrictions;
    }

    /**
     * Gets the value of the territory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the territory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTERRITORY().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTERRITORY() {
        if (territory == null) {
            territory = new ArrayList<String>();
        }
        return this.territory;
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
     * Gets the value of the remarks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the remarks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getREMARKS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link REMARKS }
     * 
     * 
     */
    public List<REMARKS> getREMARKS() {
        if (remarks == null) {
            remarks = new ArrayList<REMARKS>();
        }
        return this.remarks;
    }

    /**
     * Gets the value of the headerudx property.
     * 
     * @return
     *     possible object is
     *     {@link UdxHEADER }
     *     
     */
    public UdxHEADER getHEADERUDX() {
        return headerudx;
    }

    /**
     * Sets the value of the headerudx property.
     * 
     * @param value
     *     allowed object is
     *     {@link UdxHEADER }
     *     
     */
    public void setHEADERUDX(UdxHEADER value) {
        this.headerudx = value;
    }

}
