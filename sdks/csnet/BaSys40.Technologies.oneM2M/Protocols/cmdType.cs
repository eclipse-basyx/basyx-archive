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
public enum cmdType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    RESET = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    REBOOT = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    UPLOAD = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    DOWNLOAD = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    SOFTWAREINSTALL = 5,
    
    [System.Xml.Serialization.XmlEnumAttribute("6")]
    SOFTWAREUNINSTALL = 6,
    
    [System.Xml.Serialization.XmlEnumAttribute("7")]
    SOFTWAREUPDATE = 7,
}
}
