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
public partial class aggregatedRequest {
    
    private List<aggregatedRequestReq> reqField;
    
    public List<aggregatedRequestReq> Req {
        get {
            if ((this.reqField == null)) {
                this.reqField = new List<aggregatedRequestReq>();
            }
            return this.reqField;
        }
        set {
            this.reqField = value;
        }
    }
}
}
