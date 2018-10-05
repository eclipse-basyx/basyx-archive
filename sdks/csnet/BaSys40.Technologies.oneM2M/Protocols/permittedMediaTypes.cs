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
public enum permittedMediaTypes {
    
    [System.Xml.Serialization.XmlEnumAttribute("application/xml")]
    applicationxml,
    
    [System.Xml.Serialization.XmlEnumAttribute("application/json")]
    applicationjson,
    
    [System.Xml.Serialization.XmlEnumAttribute("application/cbor")]
    applicationcbor,
}
}
