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
public enum resourceType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    AccessControlPolicy = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    AE = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    Container = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    ContentInstance = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    CSEBase = 5,
    
    [System.Xml.Serialization.XmlEnumAttribute("6")]
    Delivery = 6,
    
    [System.Xml.Serialization.XmlEnumAttribute("7")]
    EventConfig = 7,
    
    [System.Xml.Serialization.XmlEnumAttribute("8")]
    ExecInstance = 8,
    
    [System.Xml.Serialization.XmlEnumAttribute("9")]
    Group = 9,
    
    [System.Xml.Serialization.XmlEnumAttribute("10")]
    LocationPolicy = 10,
    
    [System.Xml.Serialization.XmlEnumAttribute("11")]
    M2mServiceSubscriptionProfile = 11,
    
    [System.Xml.Serialization.XmlEnumAttribute("12")]
    MgmtCmd = 12,
    
    [System.Xml.Serialization.XmlEnumAttribute("13")]
    MgmtObj = 13,
    
    [System.Xml.Serialization.XmlEnumAttribute("14")]
    Node = 14,
    
    [System.Xml.Serialization.XmlEnumAttribute("15")]
    PollingChannel = 15,
    
    [System.Xml.Serialization.XmlEnumAttribute("16")]
    RemoteCSE = 16,
    
    [System.Xml.Serialization.XmlEnumAttribute("17")]
    Request = 17,
    
    [System.Xml.Serialization.XmlEnumAttribute("18")]
    Schedule = 18,
    
    [System.Xml.Serialization.XmlEnumAttribute("19")]
    ServiceSubscribedAppRule = 19,
    
    [System.Xml.Serialization.XmlEnumAttribute("20")]
    ServiceSubscribedNode = 20,
    
    [System.Xml.Serialization.XmlEnumAttribute("21")]
    StatsCollect = 21,
    
    [System.Xml.Serialization.XmlEnumAttribute("22")]
    StatsConfig = 22,
    
    [System.Xml.Serialization.XmlEnumAttribute("23")]
    Subscription = 23,
    
    [System.Xml.Serialization.XmlEnumAttribute("24")]
    SemanticDescriptor = 24,
    
    [System.Xml.Serialization.XmlEnumAttribute("25")]
    NotificationTargetMgmtPolicyRef = 25,
    
    [System.Xml.Serialization.XmlEnumAttribute("26")]
    NotificationTargetPolicy = 26,
    
    [System.Xml.Serialization.XmlEnumAttribute("27")]
    PolicyDeletionRules = 27,
    
    [System.Xml.Serialization.XmlEnumAttribute("28")]
    FlexContainer = 28,
    
    [System.Xml.Serialization.XmlEnumAttribute("29")]
    TimeSeries = 29,
    
    [System.Xml.Serialization.XmlEnumAttribute("30")]
    TimeSeriesInstance = 30,
    
    [System.Xml.Serialization.XmlEnumAttribute("31")]
    Role = 31,
    
    [System.Xml.Serialization.XmlEnumAttribute("32")]
    Token = 32,
    
    [System.Xml.Serialization.XmlEnumAttribute("33")]
    TrafficPattern = 33,
    
    [System.Xml.Serialization.XmlEnumAttribute("34")]
    DynamicAuthorizationConsultation = 34,
    
    [System.Xml.Serialization.XmlEnumAttribute("10001")]
    AccessControlPolicyAnnc = 10001,
    
    [System.Xml.Serialization.XmlEnumAttribute("10002")]
    AEAnnc = 10002,
    
    [System.Xml.Serialization.XmlEnumAttribute("10003")]
    ContainerAnnc = 10003,
    
    [System.Xml.Serialization.XmlEnumAttribute("10004")]
    ContentInstanceAnnc = 10004,
    
    [System.Xml.Serialization.XmlEnumAttribute("10009")]
    GroupAnnc = 10009,
    
    [System.Xml.Serialization.XmlEnumAttribute("10010")]
    LocationPolicyAnnc = 10010,
    
    [System.Xml.Serialization.XmlEnumAttribute("10013")]
    MgmtObjAnnc = 10013,
    
    [System.Xml.Serialization.XmlEnumAttribute("10014")]
    NodeAnnc = 10014,
    
    [System.Xml.Serialization.XmlEnumAttribute("10016")]
    RemoteCSEAnnc = 10016,
    
    [System.Xml.Serialization.XmlEnumAttribute("10018")]
    ScheduleAnnc = 10018,
    
    [System.Xml.Serialization.XmlEnumAttribute("10024")]
    SemanticDescriptorAnnc = 10024,
    
    [System.Xml.Serialization.XmlEnumAttribute("10028")]
    FlexContainerAnnc = 10028,
    
    [System.Xml.Serialization.XmlEnumAttribute("10029")]
    TimeSeriesAnnc = 10029,
    
    [System.Xml.Serialization.XmlEnumAttribute("10030")]
    TimeSeriesInstanceAnnc = 10030,
    
    [System.Xml.Serialization.XmlEnumAttribute("10033")]
    TrafficPatternAnnc = 10033,
    
    [System.Xml.Serialization.XmlEnumAttribute("10034")]
    DynamicAuthorizationConsultationAnnc = 10034,
}
}
