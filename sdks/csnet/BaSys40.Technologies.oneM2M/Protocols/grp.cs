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
public partial class grp : announceableResource {
    
    private List<object> itemsField;
    
    public string Cr {get; set;}

    public System.Nullable<int> Mt {get; set;}

    public System.Nullable<int> Cnm {get; set;}

    public System.Nullable<int> Mnm {get; set;}

    public string Mid {get; set;}

    public string Macp {get; set;}

    public System.Nullable<bool> Mtv {get; set;}

    public System.Nullable<int> Csy {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CsySpecified {get; set;}

    public string Gn {get; set;}

    
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
