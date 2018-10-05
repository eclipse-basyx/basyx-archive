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
public partial class aggregatedNotification {
    
    private List<notification> sgnField;
    
    public List<notification> Sgn {
        get {
            if ((this.sgnField == null)) {
                this.sgnField = new List<notification>();
            }
            return this.sgnField;
        }
        set {
            this.sgnField = value;
        }
    }
}
}
