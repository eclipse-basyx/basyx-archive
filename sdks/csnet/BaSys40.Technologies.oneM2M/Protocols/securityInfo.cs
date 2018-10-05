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
public partial class securityInfo {
    
    private dynAuthDasRequest daqField;
    
    private dynAuthDasResponse dresField;
    
    private receiverESPrimRandObject eroField;
    
    private List<byte> eckmField;
    
    public System.Nullable<int> Sit {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> SitSpecified {get; set;}

    public string Epo {get; set;}

    
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
    
    public dynAuthDasResponse Dres {
        get {
            if ((this.dresField == null)) {
                this.dresField = new dynAuthDasResponse();
            }
            return this.dresField;
        }
        set {
            this.dresField = value;
        }
    }
    
    public receiverESPrimRandObject Ero {
        get {
            if ((this.eroField == null)) {
                this.eroField = new receiverESPrimRandObject();
            }
            return this.eroField;
        }
        set {
            this.eroField = value;
        }
    }
    
    public List<byte> Eckm {
        get {
            if ((this.eckmField == null)) {
                this.eckmField = new List<byte>();
            }
            return this.eckmField;
        }
        set {
            this.eckmField = value;
        }
    }
}
}
