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
public enum stdEventCats {
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Immediate = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    BestEffort = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    Latest = 4,
}
}
