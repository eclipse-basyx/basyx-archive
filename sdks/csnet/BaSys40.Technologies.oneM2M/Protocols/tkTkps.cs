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
public partial class tkTkps {
    
    private tokenPermission pmField;
    
    public tokenPermission Pm {
        get {
            if ((this.pmField == null)) {
                this.pmField = new tokenPermission();
            }
            return this.pmField;
        }
        set {
            this.pmField = value;
        }
    }
}
}
