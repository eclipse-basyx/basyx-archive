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
public enum operation {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Create = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Retrieve = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    Update = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    Delete = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    Notify = 5,
}
}
