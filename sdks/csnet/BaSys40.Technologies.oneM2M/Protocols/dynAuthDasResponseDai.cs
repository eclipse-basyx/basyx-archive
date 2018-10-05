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
public partial class dynAuthDasResponseDai {
    
    private List<accessControlRule> gpField;
    
    public string Pl {get; set;}

    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=0)]
    [System.Xml.Serialization.XmlArrayItemAttribute("acr", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<accessControlRule> Gp {
        get {
            if ((this.gpField == null)) {
                this.gpField = new List<accessControlRule>();
            }
            return this.gpField;
        }
        set {
            this.gpField = value;
        }
    }
}
}
