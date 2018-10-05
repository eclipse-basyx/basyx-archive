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
public partial class operationResult {
    
    private primitiveContent pcField;
    
    public System.Nullable<int> Rsc {get; set;}

    public string Rqi {get; set;}

    public string To {get; set;}

    public string Fr {get; set;}

    public string Ot {get; set;}

    public string Rset {get; set;}

    public string Ec {get; set;}

    public System.Nullable<int> Cnst {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CnstSpecified {get; set;}

    public System.Nullable<int> Cnot {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CnotSpecified {get; set;}

    
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
}
}
