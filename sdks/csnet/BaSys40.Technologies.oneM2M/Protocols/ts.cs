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
public partial class ts : announceableResource {
    
    private List<object> itemsField;
    
    public System.Nullable<int> St {get; set;}

    public string Cr {get; set;}

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

    public System.Nullable<int> Cbs {get; set;}

    public System.Nullable<int> Pei {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> PeiSpecified {get; set;}

    public System.Nullable<bool> Mdd {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MddSpecified {get; set;}

    public System.Nullable<int> Mdn {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MdnSpecified {get; set;}

    public string Mdl {get; set;}

    public System.Nullable<int> Mdc {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MdcSpecified {get; set;}

    public System.Nullable<int> Mdt {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> MdtSpecified {get; set;}

    public string Or {get; set;}

    public string La {get; set;}

    public string Ol {get; set;}

    
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
