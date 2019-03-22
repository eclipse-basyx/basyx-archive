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
public partial class dynAuthTokenReqInfo {
    
    private List<dynAuthTokenReqInfoDasi> dasiField;
    
    public List<dynAuthTokenReqInfoDasi> Dasi {
        get {
            if ((this.dasiField == null)) {
                this.dasiField = new List<dynAuthTokenReqInfoDasi>();
            }
            return this.dasiField;
        }
        set {
            this.dasiField = value;
        }
    }
}
}
