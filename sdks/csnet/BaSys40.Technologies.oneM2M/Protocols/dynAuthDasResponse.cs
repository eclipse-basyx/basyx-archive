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
public partial class dynAuthDasResponse {
    
    private dynAuthDasResponseDai daiField;
    
    public string Tkns {get; set;}

    
    public dynAuthDasResponseDai Dai {
        get {
            if ((this.daiField == null)) {
                this.daiField = new dynAuthDasResponseDai();
            }
            return this.daiField;
        }
        set {
            this.daiField = value;
        }
    }
}
}
