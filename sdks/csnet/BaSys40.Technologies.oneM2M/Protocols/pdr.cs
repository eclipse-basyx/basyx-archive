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
public partial class pdr : regularResource {
    
    private deletionContexts drField;
    
    private List<object> itemsField;
    
    public System.Nullable<int> Drr {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> DrrSpecified {get; set;}

    
    public deletionContexts Dr {
        get {
            if ((this.drField == null)) {
                this.drField = new deletionContexts();
            }
            return this.drField;
        }
        set {
            this.drField = value;
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
