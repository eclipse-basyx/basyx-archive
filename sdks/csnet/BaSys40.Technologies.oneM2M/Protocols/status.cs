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
public enum status {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Successful = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Failure = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    InProcess = 3,
}
}
