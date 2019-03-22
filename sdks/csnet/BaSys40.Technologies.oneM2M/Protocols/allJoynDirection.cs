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
public enum allJoynDirection {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    AllJoyn_to_oneM2M = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    OneM2M_to_AllJoyn = 2,
}
}
