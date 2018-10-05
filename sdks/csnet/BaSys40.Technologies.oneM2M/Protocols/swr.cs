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
public partial class swr : mgmtResource {
    
    private actionStatus insField;
    
    private actionStatus actsField;
    
    private List<object> itemsField;
    
    public string Vr {get; set;}

    public string Swn {get; set;}

    public string Url {get; set;}

    public System.Nullable<bool> In {get; set;}

    public System.Nullable<bool> Un {get; set;}

    public System.Nullable<bool> Act {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> ActSpecified {get; set;}

    public System.Nullable<bool> Dea {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> DeaSpecified {get; set;}

    
    public actionStatus Ins {
        get {
            if ((this.insField == null)) {
                this.insField = new actionStatus();
            }
            return this.insField;
        }
        set {
            this.insField = value;
        }
    }
    
    public actionStatus Acts {
        get {
            if ((this.actsField == null)) {
                this.actsField = new actionStatus();
            }
            return this.actsField;
        }
        set {
            this.actsField = value;
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
