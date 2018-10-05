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
public partial class gioa : announcedFlexContainerResource {
    
    private List<dataLink> giipField;
    
    private List<dataLink> giopField;
    
    private List<dataLink> giilField;
    
    private List<dataLink> giolField;
    
    private List<object> itemsField;
    
    public string Gion {get; set;}

    public string Gios {get; set;}

    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=2)]
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
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=3)]
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
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=4)]
    [System.Xml.Serialization.XmlArrayItemAttribute("dle", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<dataLink> Giil {
        get {
            if ((this.giilField == null)) {
                this.giilField = new List<dataLink>();
            }
            return this.giilField;
        }
        set {
            this.giilField = value;
        }
    }
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=5)]
    [System.Xml.Serialization.XmlArrayItemAttribute("dle", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<dataLink> Giol {
        get {
            if ((this.giolField == null)) {
                this.giolField = new List<dataLink>();
            }
            return this.giolField;
        }
        set {
            this.giolField = value;
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
