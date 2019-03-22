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
public partial class andi : mgmtResource {
    
    private List<object> itemsField;
    
    public string Dvd {get; set;}

    public string Dvt {get; set;}

    public string Awi {get; set;}

    public System.Nullable<int> Sli {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> SliSpecified {get; set;}

    public System.Nullable<int> Sld {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> SldSpecified {get; set;}

    public string Ss {get; set;}

    public string Lnh {get; set;}

    
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
