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
public partial class acpA : announcedSubordinateResource {
    
    private List<accessControlRule> pvField;
    
    private List<accessControlRule> pvsField;
    
    private List<object> itemsField;
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=0)]
    [System.Xml.Serialization.XmlArrayItemAttribute("acr", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<accessControlRule> Pv {
        get {
            if ((this.pvField == null)) {
                this.pvField = new List<accessControlRule>();
            }
            return this.pvField;
        }
        set {
            this.pvField = value;
        }
    }
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=1)]
    [System.Xml.Serialization.XmlArrayItemAttribute("acr", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<accessControlRule> Pvs {
        get {
            if ((this.pvsField == null)) {
                this.pvsField = new List<accessControlRule>();
            }
            return this.pvsField;
        }
        set {
            this.pvsField = value;
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
