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
public partial class aggregatedRequestReq {
    
    private primitiveContent pcField;
    
    private metaInformation miField;
    
    public System.Nullable<int> Op {get; set;}

    public string To {get; set;}

    public string Fr {get; set;}

    public string Rqi {get; set;}

    
    public primitiveContent Pc {
        get {
            if ((this.pcField == null)) {
                this.pcField = new primitiveContent();
            }
            return this.pcField;
        }
        set {
            this.pcField = value;
        }
    }
    
    public metaInformation Mi {
        get {
            if ((this.miField == null)) {
                this.miField = new metaInformation();
            }
            return this.miField;
        }
        set {
            this.miField = value;
        }
    }
}
}
