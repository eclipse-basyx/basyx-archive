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
public partial class cinA : announcedSubordinateResource {
    
    private List<object> itemsField;
    
    public System.Nullable<int> St {get; set;}

    public string Cnf {get; set;}

    public System.Nullable<int> Cs {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CsSpecified {get; set;}

    public string Or {get; set;}

    public object Con {get; set;}

    
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
