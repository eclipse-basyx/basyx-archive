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
public partial class tsi : announceableSubordinateResource {
    
    public string Dgt {get; set;}

    public string Con {get; set;}

    public System.Nullable<int> Snr {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> SnrSpecified {get; set;}

}
}
