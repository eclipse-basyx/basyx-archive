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
public partial class notificationNev {
    
    private notificationNevOM omField;
    
    public object Rep {get; set;}

    public System.Nullable<int> Net {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> NetSpecified {get; set;}

    
    public notificationNevOM Om {
        get {
            if ((this.omField == null)) {
                this.omField = new notificationNevOM();
            }
            return this.omField;
        }
        set {
            this.omField = value;
        }
    }
}
}
