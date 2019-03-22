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
public enum locationSource {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Networkbased = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Devicebased = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    Sharingbased = 3,
}
}
