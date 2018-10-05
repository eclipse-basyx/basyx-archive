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
public partial class batA : announcedMgmtResource {
    
    private List<object> itemsField;
    
    public uint Btl {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> BtlSpecified {get; set;}

    public System.Nullable<int> Bts {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> BtsSpecified {get; set;}

    
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
