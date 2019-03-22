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
public partial class listOfDataLinks {
    
    private List<dataLink> dleField;
    
    public List<dataLink> Dle {
        get {
            if ((this.dleField == null)) {
                this.dleField = new List<dataLink>();
            }
            return this.dleField;
        }
        set {
            this.dleField = value;
        }
    }
}
}
