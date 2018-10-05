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
public enum accessControlOperations {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    Create = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    Retrieve = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    CreateRetrieve = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    Update = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    CreateUpdate = 5,
    
    [System.Xml.Serialization.XmlEnumAttribute("6")]
    RetrieveUpdate = 6,
    
    [System.Xml.Serialization.XmlEnumAttribute("7")]
    CreateRetrieveUpdate = 7,
    
    [System.Xml.Serialization.XmlEnumAttribute("8")]
    Delete = 8,
    
    [System.Xml.Serialization.XmlEnumAttribute("9")]
    CreateDelete = 9,
    
    [System.Xml.Serialization.XmlEnumAttribute("10")]
    RetrieveDelete = 10,
    
    [System.Xml.Serialization.XmlEnumAttribute("11")]
    CreateRetrieveDelete = 11,
    
    [System.Xml.Serialization.XmlEnumAttribute("12")]
    UpdateDelete = 12,
    
    [System.Xml.Serialization.XmlEnumAttribute("13")]
    CreateUpdateDelete = 13,
    
    [System.Xml.Serialization.XmlEnumAttribute("14")]
    RetrieveUpdateDelete = 14,
    
    [System.Xml.Serialization.XmlEnumAttribute("15")]
    CreateRetrieveUpdateDelete = 15,
    
    [System.Xml.Serialization.XmlEnumAttribute("16")]
    Notify = 16,
    
    [System.Xml.Serialization.XmlEnumAttribute("17")]
    CreateNotify = 17,
    
    [System.Xml.Serialization.XmlEnumAttribute("18")]
    RetrieveNotify = 18,
    
    [System.Xml.Serialization.XmlEnumAttribute("19")]
    CreateRetrieveNotify = 19,
    
    [System.Xml.Serialization.XmlEnumAttribute("20")]
    UpdateNotify = 20,
    
    [System.Xml.Serialization.XmlEnumAttribute("21")]
    CreateUpdateNotify = 21,
    
    [System.Xml.Serialization.XmlEnumAttribute("22")]
    RetrieveUpdateNotify = 22,
    
    [System.Xml.Serialization.XmlEnumAttribute("23")]
    CreateRetrieveUpdate2 = 23,
    
    [System.Xml.Serialization.XmlEnumAttribute("24")]
    DeleteNotify = 24,
    
    [System.Xml.Serialization.XmlEnumAttribute("25")]
    CreateDeleteNotify = 25,
    
    [System.Xml.Serialization.XmlEnumAttribute("26")]
    RetrieveDeleteNotify = 26,
    
    [System.Xml.Serialization.XmlEnumAttribute("27")]
    CreateRetrieveDeleteNotify = 27,
    
    [System.Xml.Serialization.XmlEnumAttribute("28")]
    UpdateDeleteNotify = 28,
    
    [System.Xml.Serialization.XmlEnumAttribute("29")]
    CreateUpdateDeleteNotify = 29,
    
    [System.Xml.Serialization.XmlEnumAttribute("30")]
    RetrieveUpdateDeleteNotify = 30,
    
    [System.Xml.Serialization.XmlEnumAttribute("31")]
    CreateRetrieveUpdateDeleteNotify = 31,
    
    [System.Xml.Serialization.XmlEnumAttribute("32")]
    Discover = 32,
    
    [System.Xml.Serialization.XmlEnumAttribute("33")]
    CreateDiscover = 33,
    
    [System.Xml.Serialization.XmlEnumAttribute("34")]
    RetrieveDiscover = 34,
    
    [System.Xml.Serialization.XmlEnumAttribute("35")]
    CreateRetrieveDiscover = 35,
    
    [System.Xml.Serialization.XmlEnumAttribute("36")]
    UpdateDiscover = 36,
    
    [System.Xml.Serialization.XmlEnumAttribute("37")]
    CreateUpdateDiscover = 37,
    
    [System.Xml.Serialization.XmlEnumAttribute("38")]
    RetrieveUpdateDiscover = 38,
    
    [System.Xml.Serialization.XmlEnumAttribute("39")]
    CreateRetrieveUpdateDiscover = 39,
    
    [System.Xml.Serialization.XmlEnumAttribute("40")]
    DeleteDiscover = 40,
    
    [System.Xml.Serialization.XmlEnumAttribute("41")]
    CreateDeleteDiscover = 41,
    
    [System.Xml.Serialization.XmlEnumAttribute("42")]
    RetrieveDeleteDiscover = 42,
    
    [System.Xml.Serialization.XmlEnumAttribute("43")]
    CreateRetrieveDeleteDiscover = 43,
    
    [System.Xml.Serialization.XmlEnumAttribute("44")]
    UpdateDeleteDiscover = 44,
    
    [System.Xml.Serialization.XmlEnumAttribute("45")]
    CreateUpdateDeleteDiscover = 45,
    
    [System.Xml.Serialization.XmlEnumAttribute("46")]
    RetrieveUpdateDeleteDiscover = 46,
    
    [System.Xml.Serialization.XmlEnumAttribute("47")]
    CreateRetrieveUpdateDeleteDiscover = 47,
    
    [System.Xml.Serialization.XmlEnumAttribute("48")]
    NotifyDiscover = 48,
    
    [System.Xml.Serialization.XmlEnumAttribute("49")]
    CreateNotifyDiscover = 49,
    
    [System.Xml.Serialization.XmlEnumAttribute("50")]
    RetrieveNotifyDiscover = 50,
    
    [System.Xml.Serialization.XmlEnumAttribute("51")]
    CreateRetrieveNotifyDiscover = 51,
    
    [System.Xml.Serialization.XmlEnumAttribute("52")]
    UpdateNotifyDiscover = 52,
    
    [System.Xml.Serialization.XmlEnumAttribute("53")]
    CreateUpdateNotifyDiscover = 53,
    
    [System.Xml.Serialization.XmlEnumAttribute("54")]
    RetrieveUpdateNotifyDiscover = 54,
    
    [System.Xml.Serialization.XmlEnumAttribute("55")]
    CreateRetrieveUpdateDiscover2 = 55,
    
    [System.Xml.Serialization.XmlEnumAttribute("56")]
    DeleteNotifyDiscover = 56,
    
    [System.Xml.Serialization.XmlEnumAttribute("57")]
    CreateDeleteNotifyDiscover = 57,
    
    [System.Xml.Serialization.XmlEnumAttribute("58")]
    RetrieveDeleteNotifyDiscover = 58,
    
    [System.Xml.Serialization.XmlEnumAttribute("59")]
    CreateRetrieveDeleteNotifyDiscover = 59,
    
    [System.Xml.Serialization.XmlEnumAttribute("60")]
    UpdateDeleteNotifyDiscover = 60,
    
    [System.Xml.Serialization.XmlEnumAttribute("61")]
    CreateUpdateDeleteNotifyDiscover = 61,
    
    [System.Xml.Serialization.XmlEnumAttribute("62")]
    RetrieveUpdateDeleteNotifyDiscover = 62,
    
    [System.Xml.Serialization.XmlEnumAttribute("63")]
    CreateRetrieveUpdateDeleteNotifyDiscover = 63,
}
}
