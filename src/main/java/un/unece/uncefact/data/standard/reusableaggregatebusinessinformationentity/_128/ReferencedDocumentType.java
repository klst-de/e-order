//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.17 at 10:18:46 PM CEST 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.DocumentCodeType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.FormattedDateTimeType;
import un.unece.uncefact.data.standard.qualifieddatatype._128.ReferenceCodeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.BinaryObjectType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.TextType;


/**
 * Referenced Document
 * 
 * <p>Java class for ReferencedDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReferencedDocumentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IssuerAssignedID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}IDType" minOccurs="0"/>
 *         &lt;element name="URIID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}IDType" minOccurs="0"/>
 *         &lt;element name="LineID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}IDType" minOccurs="0"/>
 *         &lt;element name="TypeCode" type="{urn:un:unece:uncefact:data:standard:QualifiedDataType:128}DocumentCodeType" minOccurs="0"/>
 *         &lt;element name="Name" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}TextType" minOccurs="0"/>
 *         &lt;element name="AttachmentBinaryObject" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}BinaryObjectType" minOccurs="0"/>
 *         &lt;element name="ReferenceTypeCode" type="{urn:un:unece:uncefact:data:standard:QualifiedDataType:128}ReferenceCodeType" minOccurs="0"/>
 *         &lt;element name="FormattedIssueDateTime" type="{urn:un:unece:uncefact:data:standard:QualifiedDataType:128}FormattedDateTimeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferencedDocumentType", propOrder = {
    "issuerAssignedID",
    "uriid",
    "lineID",
    "typeCode",
    "name",
    "attachmentBinaryObject",
    "referenceTypeCode",
    "formattedIssueDateTime"
})
public class ReferencedDocumentType {

    @XmlElement(name = "IssuerAssignedID")
    protected IDType issuerAssignedID;
    @XmlElement(name = "URIID")
    protected IDType uriid;
    @XmlElement(name = "LineID")
    protected IDType lineID;
    @XmlElement(name = "TypeCode")
    protected DocumentCodeType typeCode;
    @XmlElement(name = "Name")
    protected TextType name;
    @XmlElement(name = "AttachmentBinaryObject")
    protected BinaryObjectType attachmentBinaryObject;
    @XmlElement(name = "ReferenceTypeCode")
    protected ReferenceCodeType referenceTypeCode;
    @XmlElement(name = "FormattedIssueDateTime")
    protected FormattedDateTimeType formattedIssueDateTime;

    /**
     * Gets the value of the issuerAssignedID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getIssuerAssignedID() {
        return issuerAssignedID;
    }

    /**
     * Sets the value of the issuerAssignedID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setIssuerAssignedID(IDType value) {
        this.issuerAssignedID = value;
    }

    /**
     * Gets the value of the uriid property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getURIID() {
        return uriid;
    }

    /**
     * Sets the value of the uriid property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setURIID(IDType value) {
        this.uriid = value;
    }

    /**
     * Gets the value of the lineID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getLineID() {
        return lineID;
    }

    /**
     * Sets the value of the lineID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setLineID(IDType value) {
        this.lineID = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentCodeType }
     *     
     */
    public DocumentCodeType getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentCodeType }
     *     
     */
    public void setTypeCode(DocumentCodeType value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setName(TextType value) {
        this.name = value;
    }

    /**
     * Gets the value of the attachmentBinaryObject property.
     * 
     * @return
     *     possible object is
     *     {@link BinaryObjectType }
     *     
     */
    public BinaryObjectType getAttachmentBinaryObject() {
        return attachmentBinaryObject;
    }

    /**
     * Sets the value of the attachmentBinaryObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link BinaryObjectType }
     *     
     */
    public void setAttachmentBinaryObject(BinaryObjectType value) {
        this.attachmentBinaryObject = value;
    }

    /**
     * Gets the value of the referenceTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceCodeType }
     *     
     */
    public ReferenceCodeType getReferenceTypeCode() {
        return referenceTypeCode;
    }

    /**
     * Sets the value of the referenceTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceCodeType }
     *     
     */
    public void setReferenceTypeCode(ReferenceCodeType value) {
        this.referenceTypeCode = value;
    }

    /**
     * Gets the value of the formattedIssueDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link FormattedDateTimeType }
     *     
     */
    public FormattedDateTimeType getFormattedIssueDateTime() {
        return formattedIssueDateTime;
    }

    /**
     * Sets the value of the formattedIssueDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormattedDateTimeType }
     *     
     */
    public void setFormattedIssueDateTime(FormattedDateTimeType value) {
        this.formattedIssueDateTime = value;
    }

}