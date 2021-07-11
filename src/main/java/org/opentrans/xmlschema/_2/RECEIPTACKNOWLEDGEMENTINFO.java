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
import org.bmecat.bmecat._2005.LANGUAGE;
import org.bmecat.bmecat._2005.MIMEROOT;
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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}RECEIPTACKNOWLEDGEMENT_ID"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}RECEIPTACKNOWLEDGEMENT_DATE" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}RECEIPT_DATE"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}LANGUAGE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}MIME_ROOT" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}PARTIES"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}SUPPLIER_IDREF"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}BUYER_IDREF" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}SHIPMENT_PARTIES_REFERENCE"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}DOCEXCHANGE_PARTIES_REFERENCE" minOccurs="0"/>
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
    "receiptacknowledgementid",
    "receiptacknowledgementdate",
    "receiptdate",
    "language",
    "mimeroot",
    "parties",
    "supplieridref",
    "buyeridref",
    "shipmentpartiesreference",
    "docexchangepartiesreference",
    "mimeinfo",
    "remarks",
    "headerudx"
})
@XmlRootElement(name = "RECEIPTACKNOWLEDGEMENT_INFO")
public class RECEIPTACKNOWLEDGEMENTINFO {

    @XmlElement(name = "RECEIPTACKNOWLEDGEMENT_ID", required = true)
    protected String receiptacknowledgementid;
    @XmlElement(name = "RECEIPTACKNOWLEDGEMENT_DATE")
    protected String receiptacknowledgementdate;
    @XmlElement(name = "RECEIPT_DATE", required = true)
    protected String receiptdate;
    @XmlElement(name = "LANGUAGE", namespace = "http://www.bmecat.org/bmecat/2005")
    protected List<LANGUAGE> language;
    @XmlElement(name = "MIME_ROOT", namespace = "http://www.bmecat.org/bmecat/2005")
    protected MIMEROOT mimeroot;
    @XmlElement(name = "PARTIES", required = true)
    protected PARTIES parties;
    @XmlElement(name = "SUPPLIER_IDREF", namespace = "http://www.bmecat.org/bmecat/2005", required = true)
    protected TypePARTYID supplieridref;
    @XmlElement(name = "BUYER_IDREF", namespace = "http://www.bmecat.org/bmecat/2005")
    protected TypePARTYID buyeridref;
    @XmlElement(name = "SHIPMENT_PARTIES_REFERENCE", required = true)
    protected SHIPMENTPARTIESREFERENCE shipmentpartiesreference;
    @XmlElement(name = "DOCEXCHANGE_PARTIES_REFERENCE")
    protected DOCEXCHANGEPARTIESREFERENCE docexchangepartiesreference;
    @XmlElement(name = "MIME_INFO")
    protected MIMEINFO mimeinfo;
    @XmlElement(name = "REMARKS")
    protected List<REMARKS> remarks;
    @XmlElement(name = "HEADER_UDX")
    protected UdxHEADER headerudx;

    /**
     * Gets the value of the receiptacknowledgementid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECEIPTACKNOWLEDGEMENTID() {
        return receiptacknowledgementid;
    }

    /**
     * Sets the value of the receiptacknowledgementid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECEIPTACKNOWLEDGEMENTID(String value) {
        this.receiptacknowledgementid = value;
    }

    /**
     * Gets the value of the receiptacknowledgementdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECEIPTACKNOWLEDGEMENTDATE() {
        return receiptacknowledgementdate;
    }

    /**
     * Sets the value of the receiptacknowledgementdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECEIPTACKNOWLEDGEMENTDATE(String value) {
        this.receiptacknowledgementdate = value;
    }

    /**
     * Gets the value of the receiptdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECEIPTDATE() {
        return receiptdate;
    }

    /**
     * Sets the value of the receiptdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECEIPTDATE(String value) {
        this.receiptdate = value;
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
     * Gets the value of the supplieridref property.
     * 
     * @return
     *     possible object is
     *     {@link TypePARTYID }
     *     
     */
    public TypePARTYID getSUPPLIERIDREF() {
        return supplieridref;
    }

    /**
     * Sets the value of the supplieridref property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypePARTYID }
     *     
     */
    public void setSUPPLIERIDREF(TypePARTYID value) {
        this.supplieridref = value;
    }

    /**
     * Gets the value of the buyeridref property.
     * 
     * @return
     *     possible object is
     *     {@link TypePARTYID }
     *     
     */
    public TypePARTYID getBUYERIDREF() {
        return buyeridref;
    }

    /**
     * Sets the value of the buyeridref property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypePARTYID }
     *     
     */
    public void setBUYERIDREF(TypePARTYID value) {
        this.buyeridref = value;
    }

    /**
     * Gets the value of the shipmentpartiesreference property.
     * 
     * @return
     *     possible object is
     *     {@link SHIPMENTPARTIESREFERENCE }
     *     
     */
    public SHIPMENTPARTIESREFERENCE getSHIPMENTPARTIESREFERENCE() {
        return shipmentpartiesreference;
    }

    /**
     * Sets the value of the shipmentpartiesreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SHIPMENTPARTIESREFERENCE }
     *     
     */
    public void setSHIPMENTPARTIESREFERENCE(SHIPMENTPARTIESREFERENCE value) {
        this.shipmentpartiesreference = value;
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
