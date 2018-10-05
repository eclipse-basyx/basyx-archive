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
public enum cseTypeID {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    INCSE = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    MNCSE = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    ASNCSE = 3,
}
}
