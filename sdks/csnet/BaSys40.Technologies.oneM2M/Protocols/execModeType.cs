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
public enum execModeType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    IMMEDIATEONCE = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    IMMEDIATEREPEAT = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    RANDOMONCE = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    RANDOMREPEAT = 4,
}
}
