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
public partial class e2eSecInfo {
    
    private receiverESPrimRandObject esroField;
    
    public string Esf {get; set;}

    public string Escert {get; set;}

    
    public receiverESPrimRandObject Esro {
        get {
            if ((this.esroField == null)) {
                this.esroField = new receiverESPrimRandObject();
            }
            return this.esroField;
        }
        set {
            this.esroField = value;
        }
    }
}
}
