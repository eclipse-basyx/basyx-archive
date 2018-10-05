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
public partial class cntA : announcedResource {
    
    private List<object> itemsField;
    
    public System.Nullable<int> St {get; set;}

    public System.Nullable<int> Mni {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MniSpecified {get; set;}

    public System.Nullable<int> Mbs {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MbsSpecified {get; set;}

    public System.Nullable<int> Mia {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MiaSpecified {get; set;}

    public System.Nullable<int> Cni {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CniSpecified {get; set;}

    public System.Nullable<int> Cbs {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CbsSpecified {get; set;}

    public string Li {get; set;}

    public string Or {get; set;}

    public System.Nullable<bool> Disr {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> DisrSpecified {get; set;}

    
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
