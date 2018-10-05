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
public enum notificationEventType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    UpdateofResource = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    DeleteofResource = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    CreateofDirectChildResource = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    DeleteofDirectChildResource = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    RetrieveofContainerResourceWithNoChildResource = 5,
}
}
