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
public partial class sch : announceableSubordinateResource {
    
    private List<string> seField;
    
    private List<object> itemsField;
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=0)]
    [System.Xml.Serialization.XmlArrayItemAttribute("sce", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<string> Se {
        get {
            if ((this.seField == null)) {
                this.seField = new List<string>();
            }
            return this.seField;
        }
        set {
            this.seField = value;
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
