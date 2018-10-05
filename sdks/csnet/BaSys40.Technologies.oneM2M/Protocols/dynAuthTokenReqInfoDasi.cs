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
public partial class dynAuthTokenReqInfoDasi {
    
    private dynAuthDasRequest daqField;
    
    public string Uri {get; set;}

    public string Sdr {get; set;}

    
    public dynAuthDasRequest Daq {
        get {
            if ((this.daqField == null)) {
                this.daqField = new dynAuthDasRequest();
            }
            return this.daqField;
        }
        set {
            this.daqField = value;
        }
    }
}
}
