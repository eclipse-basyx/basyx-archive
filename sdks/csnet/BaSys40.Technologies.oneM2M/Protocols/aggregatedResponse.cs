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
public partial class aggregatedResponse {
    
    private List<rsp> rspField;
    
    public string Ri {get; set;}

    
    public List<rsp> Rsp {
        get {
            if ((this.rspField == null)) {
                this.rspField = new List<rsp>();
            }
            return this.rspField;
        }
        set {
            this.rspField = value;
        }
    }
}
}
