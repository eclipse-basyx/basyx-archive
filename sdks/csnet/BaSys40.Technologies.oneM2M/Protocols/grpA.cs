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
public partial class grpA : announcedResource {
    
    private List<object> itemsField;
    
    public System.Nullable<int> Mt {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MtSpecified {get; set;}

    public System.Nullable<int> Cnm {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CnmSpecified {get; set;}

    public System.Nullable<int> Mnm {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MnmSpecified {get; set;}

    public string Mid {get; set;}

    public string Macp {get; set;}

    public System.Nullable<bool> Mtv {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MtvSpecified {get; set;}

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
