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
public partial class lcpA : announcedResource {
    
    public System.Nullable<int> Los {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> LosSpecified {get; set;}

    public string Lou {get; set;}

    public string Lot {get; set;}

    public string Lor {get; set;}

    public string Loi {get; set;}

    public string Lon {get; set;}

    public string Lost {get; set;}

}
}
