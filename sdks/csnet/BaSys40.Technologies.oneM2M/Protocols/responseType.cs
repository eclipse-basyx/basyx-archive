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
public enum responseType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    NonBlockingRequestSynch = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    NonBlockingRequestAsynch = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    BlockingRequest = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    FlexBlocking = 4,
}
}
