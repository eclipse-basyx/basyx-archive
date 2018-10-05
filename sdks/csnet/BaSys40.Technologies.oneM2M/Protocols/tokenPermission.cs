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
public partial class tokenPermission {
    
    private List<accessControlRule> pvField;
    
    public string Ris {get; set;}

    public string Rids {get; set;}

    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=1)]
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
}
}
