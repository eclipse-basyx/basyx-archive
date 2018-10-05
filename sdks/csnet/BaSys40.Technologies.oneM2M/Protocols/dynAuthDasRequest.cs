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
public partial class dynAuthDasRequest {
    
    private dynAuthDasRequestOip oipField;
    
    private locationRegion oloField;
    
    public string Org {get; set;}

    public System.Nullable<int> Trt {get; set;}

    public System.Nullable<int> Op {get; set;}

    public string Orid {get; set;}

    public string Rts {get; set;}

    public string Trid {get; set;}

    public string Ppl {get; set;}

    public string Rfa {get; set;}

    public string Tids {get; set;}

    
    public dynAuthDasRequestOip Oip {
        get {
            if ((this.oipField == null)) {
                this.oipField = new dynAuthDasRequestOip();
            }
            return this.oipField;
        }
        set {
            this.oipField = value;
        }
    }
    
    public locationRegion Olo {
        get {
            if ((this.oloField == null)) {
                this.oloField = new locationRegion();
            }
            return this.oloField;
        }
        set {
            this.oloField = value;
        }
    }
}
}
