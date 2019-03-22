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
public enum resultContent {
    
    [System.Xml.Serialization.XmlEnumAttribute("0")]
    Nothing = 0,
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Attributes = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    HierarchicalAddress = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    HierarchicalAddressAttributes = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    AttributesChildResources = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    AttributesChildResourceReferences = 5,
    
    [System.Xml.Serialization.XmlEnumAttribute("6")]
    ChildResourceReferences = 6,
    
    [System.Xml.Serialization.XmlEnumAttribute("7")]
    OriginalResource = 7,
    
    [System.Xml.Serialization.XmlEnumAttribute("8")]
    ChildResources = 8,
}
}
