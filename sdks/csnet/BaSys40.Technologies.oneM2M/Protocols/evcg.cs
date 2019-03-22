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
public partial class evcg : regularResource {
    
    private List<object> itemsField;
    
    public string Cr {get; set;}

    public string Evi {get; set;}

    public System.Nullable<int> Evt {get; set;}

    public string Evs {get; set;}

    public string Eve {get; set;}

    public string Opt {get; set;}

    public System.Nullable<int> Ds {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> DsSpecified {get; set;}

    
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
