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
public enum batteryStatus {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    NORMAL = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    CHARGING = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    CHARGINGCOMPLETE = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    DAMAGED = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    LOWBATTERY = 5,
    
    [System.Xml.Serialization.XmlEnumAttribute("6")]
    NOTINSTALLED = 6,
    
    [System.Xml.Serialization.XmlEnumAttribute("7")]
    UNKNOWN = 7,
}
}
