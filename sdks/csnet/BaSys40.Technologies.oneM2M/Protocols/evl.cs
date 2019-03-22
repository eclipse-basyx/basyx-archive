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
public partial class evl : mgmtResource {
    
    private List<object> itemsField;
    
    public System.Nullable<int> Lgt {get; set;}

    public string Lgd {get; set;}

    public System.Nullable<int> Lgst {get; set;}

    public System.Nullable<bool> Lga {get; set;}

    public System.Nullable<bool> Lgo {get; set;}

    
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
