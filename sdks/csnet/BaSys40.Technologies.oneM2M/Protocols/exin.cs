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
public partial class exin : regularResource {
    
    private execReqArgsListType exraField;
    
    private List<object> itemsField;
    
    public System.Nullable<int> Exs {get; set;}

    public System.Nullable<int> Exr {get; set;}

    public System.Nullable<bool> Exd {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> ExdSpecified {get; set;}

    public string Ext {get; set;}

    public System.Nullable<int> Exm {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> ExmSpecified {get; set;}

    public string Exf {get; set;}

    public string Exy {get; set;}

    public System.Nullable<int> Exn {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> ExnSpecified {get; set;}

    
    public execReqArgsListType Exra {
        get {
            if ((this.exraField == null)) {
                this.exraField = new execReqArgsListType();
            }
            return this.exraField;
        }
        set {
            this.exraField = value;
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
