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
public enum encodingType {
    
    [System.Xml.Serialization.XmlEnumAttribute("0")]
    Plain = 0,
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Base64String = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Base64Binary = 2,
}
}
