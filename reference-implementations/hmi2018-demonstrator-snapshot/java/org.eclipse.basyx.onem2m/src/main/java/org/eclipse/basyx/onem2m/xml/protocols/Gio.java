//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.16 at 09:48:33 AM CET 
//


package org.eclipse.basyx.onem2m.xml.protocols;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.onem2m.org/xml/protocols}flexContainerResource"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="gion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="gios" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="giip" type="{http://www.onem2m.org/xml/protocols}listOfDataLinks" minOccurs="0"/&gt;
 *         &lt;element name="giop" type="{http://www.onem2m.org/xml/protocols}listOfDataLinks" minOccurs="0"/&gt;
 *         &lt;element name="giil" type="{http://www.onem2m.org/xml/protocols}listOfDataLinks" minOccurs="0"/&gt;
 *         &lt;element name="giol" type="{http://www.onem2m.org/xml/protocols}listOfDataLinks" minOccurs="0"/&gt;
 *         &lt;choice minOccurs="0"&gt;
 *           &lt;element name="ch" type="{http://www.onem2m.org/xml/protocols}childResourceRef" maxOccurs="unbounded"/&gt;
 *           &lt;choice maxOccurs="unbounded"&gt;
 *             &lt;element ref="{http://www.onem2m.org/xml/protocols}smd"/&gt;
 *             &lt;element ref="{http://www.onem2m.org/xml/protocols}sub"/&gt;
 *           &lt;/choice&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "gion",
    "gios",
    "giip",
    "giop",
    "giil",
    "giol",
    "ch",
    "smdOrSub"
})
public class Gio
    extends FlexContainerResource
{

    @XmlElement(required = true)
    protected String gion;
    @XmlElement(required = true)
    protected String gios;
    protected ListOfDataLinks giip;
    protected ListOfDataLinks giop;
    protected ListOfDataLinks giil;
    protected ListOfDataLinks giol;
    protected List<ChildResourceRef> ch;
    @XmlElements({
        @XmlElement(name = "smd", namespace = "http://www.onem2m.org/xml/protocols", type = Smd.class),
        @XmlElement(name = "sub", namespace = "http://www.onem2m.org/xml/protocols", type = Sub.class)
    })
    protected List<RegularResource> smdOrSub;

    /**
     * Gets the value of the gion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGion() {
        return gion;
    }

    /**
     * Sets the value of the gion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGion(String value) {
        this.gion = value;
    }

    /**
     * Gets the value of the gios property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGios() {
        return gios;
    }

    /**
     * Sets the value of the gios property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGios(String value) {
        this.gios = value;
    }

    /**
     * Gets the value of the giip property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfDataLinks }
     *     
     */
    public ListOfDataLinks getGiip() {
        return giip;
    }

    /**
     * Sets the value of the giip property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfDataLinks }
     *     
     */
    public void setGiip(ListOfDataLinks value) {
        this.giip = value;
    }

    /**
     * Gets the value of the giop property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfDataLinks }
     *     
     */
    public ListOfDataLinks getGiop() {
        return giop;
    }

    /**
     * Sets the value of the giop property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfDataLinks }
     *     
     */
    public void setGiop(ListOfDataLinks value) {
        this.giop = value;
    }

    /**
     * Gets the value of the giil property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfDataLinks }
     *     
     */
    public ListOfDataLinks getGiil() {
        return giil;
    }

    /**
     * Sets the value of the giil property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfDataLinks }
     *     
     */
    public void setGiil(ListOfDataLinks value) {
        this.giil = value;
    }

    /**
     * Gets the value of the giol property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfDataLinks }
     *     
     */
    public ListOfDataLinks getGiol() {
        return giol;
    }

    /**
     * Sets the value of the giol property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfDataLinks }
     *     
     */
    public void setGiol(ListOfDataLinks value) {
        this.giol = value;
    }

    /**
     * Gets the value of the ch property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ch property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCh().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChildResourceRef }
     * 
     * 
     */
    public List<ChildResourceRef> getCh() {
        if (ch == null) {
            ch = new ArrayList<ChildResourceRef>();
        }
        return this.ch;
    }

    /**
     * Gets the value of the smdOrSub property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the smdOrSub property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSmdOrSub().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Smd }
     * {@link Sub }
     * 
     * 
     */
    public List<RegularResource> getSmdOrSub() {
        if (smdOrSub == null) {
            smdOrSub = new ArrayList<RegularResource>();
        }
        return this.smdOrSub;
    }

}