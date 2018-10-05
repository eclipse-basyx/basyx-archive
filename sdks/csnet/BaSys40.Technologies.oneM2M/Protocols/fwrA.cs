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
public partial class fwrA : announcedMgmtResource {
    
    private actionStatus udsField;
    
    private List<object> itemsField;
    
    public string Vr {get; set;}

    public string Fwn {get; set;}

    public string Url {get; set;}

    public System.Nullable<bool> Ud {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> UdSpecified {get; set;}

    
    public actionStatus Uds {
        get {
            if ((this.udsField == null)) {
                this.udsField = new actionStatus();
            }
            return this.udsField;
        }
        set {
            this.udsField = value;
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
