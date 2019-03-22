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
public partial class dvc : mgmtResource {
    
    private actionStatus casField;
    
    private List<object> itemsField;
    
    public string Can {get; set;}

    public System.Nullable<bool> Att {get; set;}

    public System.Nullable<bool> Cus {get; set;}

    public System.Nullable<bool> Ena {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> EnaSpecified {get; set;}

    public System.Nullable<bool> Dis {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> DisSpecified {get; set;}

    
    public actionStatus Cas {
        get {
            if ((this.casField == null)) {
                this.casField = new actionStatus();
            }
            return this.casField;
        }
        set {
            this.casField = value;
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
