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
public enum esprimProtocolAndAlgID {
    
    [System.Xml.Serialization.XmlEnumAttribute("10")]
    JWECompactSerializationwithencA128GCMAESGCMusing128bitkey = 10,
    
    [System.Xml.Serialization.XmlEnumAttribute("11")]
    JWECompactSerializationwithencA192GCMAESGCMusing192bitkey = 11,
    
    [System.Xml.Serialization.XmlEnumAttribute("12")]
    JWECompactSerializationwithencA256GCMAESGCMusing256bitkey = 12,
}
}
