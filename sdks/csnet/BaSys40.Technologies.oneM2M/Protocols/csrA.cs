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
public partial class csrA : announcedResource {
    
    private e2eSecInfo esiField;
    
    private List<object> itemsField;
    
    public System.Nullable<int> Cst {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CstSpecified {get; set;}

    public string Poa {get; set;}

    public string Cb {get; set;}

    public string Csi {get; set;}

    public System.Nullable<bool> Rr {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> RrSpecified {get; set;}

    public string Nl {get; set;}

    
    public e2eSecInfo Esi {
        get {
            if ((this.esiField == null)) {
                this.esiField = new e2eSecInfo();
            }
            return this.esiField;
        }
        set {
            this.esiField = value;
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
