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
public partial class scheduleEntries {
    
    private List<string> sceField;
    
    public List<string> Sce {
        get {
            if ((this.sceField == null)) {
                this.sceField = new List<string>();
            }
            return this.sceField;
        }
        set {
            this.sceField = value;
        }
    }
}
}
