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
public partial class deletionContexts {
    
    private List<string> todField;
    
    private List<locationRegion> lrField;
    
    public List<string> Tod {
        get {
            if ((this.todField == null)) {
                this.todField = new List<string>();
            }
            return this.todField;
        }
        set {
            this.todField = value;
        }
    }
    
    public List<locationRegion> Lr {
        get {
            if ((this.lrField == null)) {
                this.lrField = new List<locationRegion>();
            }
            return this.lrField;
        }
        set {
            this.lrField = value;
        }
    }
}
}
