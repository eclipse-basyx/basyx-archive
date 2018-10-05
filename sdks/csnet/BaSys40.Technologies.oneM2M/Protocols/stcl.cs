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
public partial class stcl : regularResource {
    
    private List<string> cpField;
    
    private List<object> itemsField;
    
    public string Cr {get; set;}

    public string Sci {get; set;}

    public string Cei {get; set;}

    public string Cdi {get; set;}

    public System.Nullable<int> Srs {get; set;}

    public System.Nullable<int> Sm {get; set;}

    public string Evi {get; set;}

    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=6)]
    [System.Xml.Serialization.XmlArrayItemAttribute("sce", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<string> Cp {
        get {
            if ((this.cpField == null)) {
                this.cpField = new List<string>();
            }
            return this.cpField;
        }
        set {
            this.cpField = value;
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
