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
public partial class cmp : mgmtResource {
    
    private List<mgmtLinkRef> cmlkField;
    
    public string Cpn {get; set;}

    
    public List<mgmtLinkRef> Cmlk {
        get {
            if ((this.cmlkField == null)) {
                this.cmlkField = new List<mgmtLinkRef>();
            }
            return this.cmlkField;
        }
        set {
            this.cmlkField = value;
        }
    }
}
}
