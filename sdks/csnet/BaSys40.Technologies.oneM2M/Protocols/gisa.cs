namespace oneM2MClient.Protocols
{
using System;
using System.Diagnostics;
using System.Xml.Serialization;
using System.Collections;
using System.Xml.Schema;
using System.ComponentModel;
using System.Xml;
using System.Collections.Generic;
public partial class gisa : announcedFlexContainerResource {
    
    private List<dataLink> giipField;
    
    private List<dataLink> giopField;
    
    private List<object> itemsField;
    
    public string Gisn {get; set;}

    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=1)]
    [System.Xml.Serialization.XmlArrayItemAttribute("dle", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<dataLink> Giip {
        get {
            if ((this.giipField == null)) {
                this.giipField = new List<dataLink>();
            }
            return this.giipField;
        }
        set {
            this.giipField = value;
        }
    }
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=2)]
    [System.Xml.Serialization.XmlArrayItemAttribute("dle", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<dataLink> Giop {
        get {
            if ((this.giopField == null)) {
                this.giopField = new List<dataLink>();
            }
            return this.giopField;
        }
        set {
            this.giopField = value;
        }
    }
    
    public List<object> Items {
        get {
            if ((this.itemsField == null)) {
                this.itemsField = new List<object>();
            }
            return this.itemsField;
        }
        set {
            this.itemsField = value;
        }
    }
}
}
