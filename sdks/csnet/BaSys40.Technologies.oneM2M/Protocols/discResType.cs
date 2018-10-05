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
public enum discResType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Structured = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Unstructured = 2,
}
}
