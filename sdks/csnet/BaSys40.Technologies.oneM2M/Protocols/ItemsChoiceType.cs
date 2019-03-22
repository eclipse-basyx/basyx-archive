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
public enum ItemsChoiceType {
    
    [System.Xml.Serialization.XmlEnumAttribute("##any:")]
    Item,
    
    [System.Xml.Serialization.XmlEnumAttribute("http://www.onem2m.org/xml/protocols:")]
    Item1,
}
}
