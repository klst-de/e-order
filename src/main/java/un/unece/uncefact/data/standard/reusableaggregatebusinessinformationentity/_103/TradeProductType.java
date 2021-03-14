//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.14 at 10:16:57 PM CET 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.TextType;


/**
 * Trade Product
 * 
 * <p>Java class for TradeProductType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeProductType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" minOccurs="0"/>
 *         &lt;element name="GlobalID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SellerAssignedID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" minOccurs="0"/>
 *         &lt;element name="BuyerAssignedID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" minOccurs="0"/>
 *         &lt;element name="IndustryAssignedID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" minOccurs="0"/>
 *         &lt;element name="ModelID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" minOccurs="0"/>
 *         &lt;element name="Name" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="Description" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="BatchID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" minOccurs="0"/>
 *         &lt;element name="BrandName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="ModelName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="ApplicableProductCharacteristic" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ProductCharacteristicType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DesignatedProductClassification" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ProductClassificationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IndividualTradeProductInstance" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}TradeProductInstanceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ApplicableSupplyChainPackaging" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}SupplyChainPackagingType" minOccurs="0"/>
 *         &lt;element name="OriginTradeCountry" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}TradeCountryType" minOccurs="0"/>
 *         &lt;element name="AdditionalReferenceReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ReferencedDocumentType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IncludedReferencedProduct" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ReferencedProductType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeProductType", propOrder = {
    "id",
    "globalID",
    "sellerAssignedID",
    "buyerAssignedID",
    "industryAssignedID",
    "modelID",
    "name",
    "description",
    "batchID",
    "brandName",
    "modelName",
    "applicableProductCharacteristic",
    "designatedProductClassification",
    "individualTradeProductInstance",
    "applicableSupplyChainPackaging",
    "originTradeCountry",
    "additionalReferenceReferencedDocument",
    "includedReferencedProduct"
})
public class TradeProductType {

    @XmlElement(name = "ID")
    protected IDType id;
    @XmlElement(name = "GlobalID")
    protected List<IDType> globalID;
    @XmlElement(name = "SellerAssignedID")
    protected IDType sellerAssignedID;
    @XmlElement(name = "BuyerAssignedID")
    protected IDType buyerAssignedID;
    @XmlElement(name = "IndustryAssignedID")
    protected IDType industryAssignedID;
    @XmlElement(name = "ModelID")
    protected IDType modelID;
    @XmlElement(name = "Name")
    protected TextType name;
    @XmlElement(name = "Description")
    protected TextType description;
    @XmlElement(name = "BatchID")
    protected IDType batchID;
    @XmlElement(name = "BrandName")
    protected TextType brandName;
    @XmlElement(name = "ModelName")
    protected TextType modelName;
    @XmlElement(name = "ApplicableProductCharacteristic")
    protected List<ProductCharacteristicType> applicableProductCharacteristic;
    @XmlElement(name = "DesignatedProductClassification")
    protected List<ProductClassificationType> designatedProductClassification;
    @XmlElement(name = "IndividualTradeProductInstance")
    protected List<TradeProductInstanceType> individualTradeProductInstance;
    @XmlElement(name = "ApplicableSupplyChainPackaging")
    protected SupplyChainPackagingType applicableSupplyChainPackaging;
    @XmlElement(name = "OriginTradeCountry")
    protected TradeCountryType originTradeCountry;
    @XmlElement(name = "AdditionalReferenceReferencedDocument")
    protected List<ReferencedDocumentType> additionalReferenceReferencedDocument;
    @XmlElement(name = "IncludedReferencedProduct")
    protected List<ReferencedProductType> includedReferencedProduct;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setID(IDType value) {
        this.id = value;
    }

    /**
     * Gets the value of the globalID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the globalID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGlobalID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IDType }
     * 
     * 
     */
    public List<IDType> getGlobalID() {
        if (globalID == null) {
            globalID = new ArrayList<IDType>();
        }
        return this.globalID;
    }

    /**
     * Gets the value of the sellerAssignedID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getSellerAssignedID() {
        return sellerAssignedID;
    }

    /**
     * Sets the value of the sellerAssignedID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setSellerAssignedID(IDType value) {
        this.sellerAssignedID = value;
    }

    /**
     * Gets the value of the buyerAssignedID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getBuyerAssignedID() {
        return buyerAssignedID;
    }

    /**
     * Sets the value of the buyerAssignedID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setBuyerAssignedID(IDType value) {
        this.buyerAssignedID = value;
    }

    /**
     * Gets the value of the industryAssignedID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getIndustryAssignedID() {
        return industryAssignedID;
    }

    /**
     * Sets the value of the industryAssignedID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setIndustryAssignedID(IDType value) {
        this.industryAssignedID = value;
    }

    /**
     * Gets the value of the modelID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getModelID() {
        return modelID;
    }

    /**
     * Sets the value of the modelID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setModelID(IDType value) {
        this.modelID = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDescription(TextType value) {
        this.description = value;
    }

    /**
     * Gets the value of the batchID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getBatchID() {
        return batchID;
    }

    /**
     * Sets the value of the batchID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setBatchID(IDType value) {
        this.batchID = value;
    }

    /**
     * Gets the value of the brandName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getBrandName() {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setBrandName(TextType value) {
        this.brandName = value;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setModelName(TextType value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the applicableProductCharacteristic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicableProductCharacteristic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicableProductCharacteristic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductCharacteristicType }
     * 
     * 
     */
    public List<ProductCharacteristicType> getApplicableProductCharacteristic() {
        if (applicableProductCharacteristic == null) {
            applicableProductCharacteristic = new ArrayList<ProductCharacteristicType>();
        }
        return this.applicableProductCharacteristic;
    }

    /**
     * Gets the value of the designatedProductClassification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the designatedProductClassification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDesignatedProductClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductClassificationType }
     * 
     * 
     */
    public List<ProductClassificationType> getDesignatedProductClassification() {
        if (designatedProductClassification == null) {
            designatedProductClassification = new ArrayList<ProductClassificationType>();
        }
        return this.designatedProductClassification;
    }

    /**
     * Gets the value of the individualTradeProductInstance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the individualTradeProductInstance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndividualTradeProductInstance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TradeProductInstanceType }
     * 
     * 
     */
    public List<TradeProductInstanceType> getIndividualTradeProductInstance() {
        if (individualTradeProductInstance == null) {
            individualTradeProductInstance = new ArrayList<TradeProductInstanceType>();
        }
        return this.individualTradeProductInstance;
    }

    /**
     * Gets the value of the applicableSupplyChainPackaging property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyChainPackagingType }
     *     
     */
    public SupplyChainPackagingType getApplicableSupplyChainPackaging() {
        return applicableSupplyChainPackaging;
    }

    /**
     * Sets the value of the applicableSupplyChainPackaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyChainPackagingType }
     *     
     */
    public void setApplicableSupplyChainPackaging(SupplyChainPackagingType value) {
        this.applicableSupplyChainPackaging = value;
    }

    /**
     * Gets the value of the originTradeCountry property.
     * 
     * @return
     *     possible object is
     *     {@link TradeCountryType }
     *     
     */
    public TradeCountryType getOriginTradeCountry() {
        return originTradeCountry;
    }

    /**
     * Sets the value of the originTradeCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeCountryType }
     *     
     */
    public void setOriginTradeCountry(TradeCountryType value) {
        this.originTradeCountry = value;
    }

    /**
     * Gets the value of the additionalReferenceReferencedDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalReferenceReferencedDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalReferenceReferencedDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferencedDocumentType }
     * 
     * 
     */
    public List<ReferencedDocumentType> getAdditionalReferenceReferencedDocument() {
        if (additionalReferenceReferencedDocument == null) {
            additionalReferenceReferencedDocument = new ArrayList<ReferencedDocumentType>();
        }
        return this.additionalReferenceReferencedDocument;
    }

    /**
     * Gets the value of the includedReferencedProduct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includedReferencedProduct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludedReferencedProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferencedProductType }
     * 
     * 
     */
    public List<ReferencedProductType> getIncludedReferencedProduct() {
        if (includedReferencedProduct == null) {
            includedReferencedProduct = new ArrayList<ReferencedProductType>();
        }
        return this.includedReferencedProduct;
    }

}
