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
public enum logStatus {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Started = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Stopped = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    Unknown = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    NotPresent = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    Error = 5,
}
}
