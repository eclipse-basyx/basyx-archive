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
public enum contentStatus {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    PARTIAL_CONTENT = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    FULL_CONTENT = 2,
}
}
