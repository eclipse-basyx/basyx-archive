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
public enum mgmtDefinition {
    
    [System.Xml.Serialization.XmlEnumAttribute("0")]
    Unspecified = 0,
    
    [System.Xml.Serialization.XmlEnumAttribute("1001")]
    Firmware = 1001,
    
    [System.Xml.Serialization.XmlEnumAttribute("1002")]
    Software = 1002,
    
    [System.Xml.Serialization.XmlEnumAttribute("1003")]
    Memory = 1003,
    
    [System.Xml.Serialization.XmlEnumAttribute("1004")]
    AreaNwkInfo = 1004,
    
    [System.Xml.Serialization.XmlEnumAttribute("1005")]
    AreaNwkDeviceInfo = 1005,
    
    [System.Xml.Serialization.XmlEnumAttribute("1006")]
    Battery = 1006,
    
    [System.Xml.Serialization.XmlEnumAttribute("1007")]
    DeviceInfo = 1007,
    
    [System.Xml.Serialization.XmlEnumAttribute("1008")]
    DeviceCapability = 1008,
    
    [System.Xml.Serialization.XmlEnumAttribute("1009")]
    Reboot = 1009,
    
    [System.Xml.Serialization.XmlEnumAttribute("1010")]
    EventLog = 1010,
    
    [System.Xml.Serialization.XmlEnumAttribute("1011")]
    CmdhPolicy = 1011,
    
    [System.Xml.Serialization.XmlEnumAttribute("1012")]
    ActiveCmdhPolicy = 1012,
    
    [System.Xml.Serialization.XmlEnumAttribute("1013")]
    CmdhDefaults = 1013,
    
    [System.Xml.Serialization.XmlEnumAttribute("1014")]
    CmdhDefEcValue = 1014,
    
    [System.Xml.Serialization.XmlEnumAttribute("1015")]
    CmdhEcDefParamValues = 1015,
    
    [System.Xml.Serialization.XmlEnumAttribute("1016")]
    CmdhLimits = 1016,
    
    [System.Xml.Serialization.XmlEnumAttribute("1017")]
    CmdhNetworkAccessRules = 1017,
    
    [System.Xml.Serialization.XmlEnumAttribute("1018")]
    CmdhNwAccessRule = 1018,
    
    [System.Xml.Serialization.XmlEnumAttribute("1019")]
    CmdhBuffer = 1019,
}
}
