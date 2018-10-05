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
public enum filterUsage {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Discovery = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    ConditionalRetrieval = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    IPEOndemandDiscovery = 3,
}
}
