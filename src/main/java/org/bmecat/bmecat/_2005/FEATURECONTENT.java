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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_DATATYPE"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_FACETS" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_VALUES" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_VALENCY" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_UNIT_IDREF" minOccurs="0"/>
 *           &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_UNIT" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_MANDATORY" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_ORDER" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_SYMBOL" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_SYNONYMS" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}MIME_INFO" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_SOURCE" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_NOTE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}FT_REMARK" maxOccurs="unbounded" minOccurs="0"/>
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
    "ftdatatype",
    "ftfacets",
    "ftvalues",
    "ftvalency",
    "ftunitidref",
    "ftunit",
    "ftmandatory",
    "ftorder",
    "ftsymbol",
    "ftsynonyms",
    "mimeinfo",
    "ftsource",
    "ftnote",
    "ftremark"
})
@XmlRootElement(name = "FEATURE_CONTENT")
public class FEATURECONTENT {

    @XmlElement(name = "FT_DATATYPE", required = true)
    protected String ftdatatype;
    @XmlElement(name = "FT_FACETS")
    protected FTFACETS ftfacets;
    @XmlElement(name = "FT_VALUES")
    protected FTVALUES ftvalues;
    @XmlElement(name = "FT_VALENCY", defaultValue = "univalent")
    protected String ftvalency;
    @XmlElement(name = "FT_UNIT_IDREF")
    protected String ftunitidref;
    @XmlElement(name = "FT_UNIT")
    protected String ftunit;
    @XmlElement(name = "FT_MANDATORY")
    protected String ftmandatory;
    @XmlElement(name = "FT_ORDER")
    protected BigInteger ftorder;
    @XmlElement(name = "FT_SYMBOL")
    protected List<FTSYMBOL> ftsymbol;
    @XmlElement(name = "FT_SYNONYMS")
    protected FTSYNONYMS ftsynonyms;
    @XmlElement(name = "MIME_INFO")
    protected MIMEINFO mimeinfo;
    @XmlElement(name = "FT_SOURCE")
    protected TypeSOURCE ftsource;
    @XmlElement(name = "FT_NOTE")
    protected List<FTNOTE> ftnote;
    @XmlElement(name = "FT_REMARK")
    protected List<FTREMARK> ftremark;

    /**
     * Gets the value of the ftdatatype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTDATATYPE() {
        return ftdatatype;
    }

    /**
     * Sets the value of the ftdatatype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTDATATYPE(String value) {
        this.ftdatatype = value;
    }

    /**
     * Gets the value of the ftfacets property.
     * 
     * @return
     *     possible object is
     *     {@link FTFACETS }
     *     
     */
    public FTFACETS getFTFACETS() {
        return ftfacets;
    }

    /**
     * Sets the value of the ftfacets property.
     * 
     * @param value
     *     allowed object is
     *     {@link FTFACETS }
     *     
     */
    public void setFTFACETS(FTFACETS value) {
        this.ftfacets = value;
    }

    /**
     * Gets the value of the ftvalues property.
     * 
     * @return
     *     possible object is
     *     {@link FTVALUES }
     *     
     */
    public FTVALUES getFTVALUES() {
        return ftvalues;
    }

    /**
     * Sets the value of the ftvalues property.
     * 
     * @param value
     *     allowed object is
     *     {@link FTVALUES }
     *     
     */
    public void setFTVALUES(FTVALUES value) {
        this.ftvalues = value;
    }

    /**
     * Gets the value of the ftvalency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTVALENCY() {
        return ftvalency;
    }

    /**
     * Sets the value of the ftvalency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTVALENCY(String value) {
        this.ftvalency = value;
    }

    /**
     * Gets the value of the ftunitidref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTUNITIDREF() {
        return ftunitidref;
    }

    /**
     * Sets the value of the ftunitidref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTUNITIDREF(String value) {
        this.ftunitidref = value;
    }

    /**
     * Gets the value of the ftunit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTUNIT() {
        return ftunit;
    }

    /**
     * Sets the value of the ftunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTUNIT(String value) {
        this.ftunit = value;
    }

    /**
     * Gets the value of the ftmandatory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTMANDATORY() {
        return ftmandatory;
    }

    /**
     * Sets the value of the ftmandatory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTMANDATORY(String value) {
        this.ftmandatory = value;
    }

    /**
     * Gets the value of the ftorder property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFTORDER() {
        return ftorder;
    }

    /**
     * Sets the value of the ftorder property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFTORDER(BigInteger value) {
        this.ftorder = value;
    }

    /**
     * Gets the value of the ftsymbol property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftsymbol property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTSYMBOL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTSYMBOL }
     * 
     * 
     */
    public List<FTSYMBOL> getFTSYMBOL() {
        if (ftsymbol == null) {
            ftsymbol = new ArrayList<FTSYMBOL>();
        }
        return this.ftsymbol;
    }

    /**
     * Gets the value of the ftsynonyms property.
     * 
     * @return
     *     possible object is
     *     {@link FTSYNONYMS }
     *     
     */
    public FTSYNONYMS getFTSYNONYMS() {
        return ftsynonyms;
    }

    /**
     * Sets the value of the ftsynonyms property.
     * 
     * @param value
     *     allowed object is
     *     {@link FTSYNONYMS }
     *     
     */
    public void setFTSYNONYMS(FTSYNONYMS value) {
        this.ftsynonyms = value;
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
     * Gets the value of the ftsource property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSOURCE }
     *     
     */
    public TypeSOURCE getFTSOURCE() {
        return ftsource;
    }

    /**
     * Sets the value of the ftsource property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSOURCE }
     *     
     */
    public void setFTSOURCE(TypeSOURCE value) {
        this.ftsource = value;
    }

    /**
     * Gets the value of the ftnote property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftnote property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTNOTE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTNOTE }
     * 
     * 
     */
    public List<FTNOTE> getFTNOTE() {
        if (ftnote == null) {
            ftnote = new ArrayList<FTNOTE>();
        }
        return this.ftnote;
    }

    /**
     * Gets the value of the ftremark property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftremark property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTREMARK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTREMARK }
     * 
     * 
     */
    public List<FTREMARK> getFTREMARK() {
        if (ftremark == null) {
            ftremark = new ArrayList<FTREMARK>();
        }
        return this.ftremark;
    }

}
