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
public partial class setOfAcrs {
    
    private List<accessControlRule> acrField;
    
    public List<accessControlRule> Acr {
        get {
            if ((this.acrField == null)) {
                this.acrField = new List<accessControlRule>();
            }
            return this.acrField;
        }
        set {
            this.acrField = value;
        }
    }
}
}
