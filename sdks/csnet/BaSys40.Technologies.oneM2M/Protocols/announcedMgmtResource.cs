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
public partial class announcedMgmtResource : announcedResource {
    
    public System.Nullable<int> Mgd {get; set;}

    public string Obis {get; set;}

    public string Obps {get; set;}

    public string Dc {get; set;}

}
}
