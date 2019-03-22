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
public enum contentSecurity {
    
    [System.Xml.Serialization.XmlEnumAttribute("0")]
    ESDatahasnotbeenappliedtothecontentdata = 0,
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    ESDatausingJWEandorJWSwithCompactSerializationhasbeenappliedtothecontentdatawithnosubsequenttransferencoding = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    ESDatausingJWEandorJWSwithJSONSerializationhasbeenappliedtothecontentdatawithnosubsequenttransferencoding = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    ESDatausingJWEandorJWSwithJSONSerializationhasbeenappliedtothecontentdataandsubsequentbase64encodingsee9hasbeenapplied = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    XMLencryptionandorXMLSignaturehasbeenappliedtothecontentdatahasbeenappliedwithnosubsequenttransferencoding = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    XMLencryptionandorXMLSignaturehasbeenappliedtothecontentdataandsubsequentbase64encodingsee9hasbeenapplied = 5,
}
}
