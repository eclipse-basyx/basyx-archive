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
public partial class contentRef {
    
    private List<contentRefUrir> urirField;
    
    public List<contentRefUrir> Urir {
        get {
            if ((this.urirField == null)) {
                this.urirField = new List<contentRefUrir>();
            }
            return this.urirField;
        }
        set {
            this.urirField = value;
        }
    }
}
}
