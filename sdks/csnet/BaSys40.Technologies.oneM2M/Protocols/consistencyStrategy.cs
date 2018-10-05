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
public enum consistencyStrategy {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    ABANDON_MEMBER = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    ABANDON_GROUP = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    SET_MIXED = 3,
}
}
