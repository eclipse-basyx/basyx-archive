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
public partial class aeA : announcedResource {
    
   
    private List<object> itemsField;
    
    public string Apn {get; set;}

    public string Api {get; set;}

    public string Aei {get; set;}

    public string Poa {get; set;}

    public string Or {get; set;}

    public string Nl {get; set;}

    public System.Nullable<bool> Rr {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> RrSpecified {get; set;}

    public string Csz {get; set;}

    
    public e2eSecInfo Esi { get; set; }
    
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
