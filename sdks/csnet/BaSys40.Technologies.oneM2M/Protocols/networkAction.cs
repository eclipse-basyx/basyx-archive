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
public enum networkAction {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Cellularregistration = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Cellularattach = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    Cellularpdpctxact = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    Cellularsms = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    Default = 5,
}
}
