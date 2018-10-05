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
public enum execStatusType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    INITIATED = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    PENDING = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    FINISHED = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    CANCELLING = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    CANCELLED = 5,
    
    [System.Xml.Serialization.XmlEnumAttribute("6")]
    STATUS_NON_CANCELLABLE = 6,
}
}
