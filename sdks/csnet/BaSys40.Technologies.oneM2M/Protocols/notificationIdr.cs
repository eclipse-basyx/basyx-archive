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
public partial class notificationIdr {
    
    private filterCriteria fcField;
    
    public string Org {get; set;}

    
    public filterCriteria Fc {
        get {
            if ((this.fcField == null)) {
                this.fcField = new filterCriteria();
            }
            return this.fcField;
        }
        set {
            this.fcField = value;
        }
    }
}
}
