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
public enum requestStatus {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    COMPLETED = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    FAILED = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    PENDING = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    FORWARDED = 4,
}
}
