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
public enum logTypeId {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    System = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Security = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    Event = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    Trace = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    Panic = 5,
}
}
