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
public enum notificationTargetPolicyAction {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Acceptrequest = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Rejectrequest = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    Seekauthorizationfromsubscriptionoriginatorbeforeresponding = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    Informthesubscriptionoriginatorwithouttakinganyaction = 4,
}
}
