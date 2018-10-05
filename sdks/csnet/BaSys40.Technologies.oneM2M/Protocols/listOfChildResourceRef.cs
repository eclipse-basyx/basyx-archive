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
public partial class listOfChildResourceRef {
    
    private List<childResourceRef> chField;
    
    public List<childResourceRef> Ch {
        get {
            if ((this.chField == null)) {
                this.chField = new List<childResourceRef>();
            }
            return this.chField;
        }
        set {
            this.chField = value;
        }
    }
}
}
