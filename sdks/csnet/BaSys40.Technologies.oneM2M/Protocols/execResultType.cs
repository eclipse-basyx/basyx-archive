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
public enum execResultType {
    
    [System.Xml.Serialization.XmlEnumAttribute("1")]
    STATUS_REQUEST_UNSUPPORTED = 1,
    
    [System.Xml.Serialization.XmlEnumAttribute("2")]
    STATUS_REQUESTDENIED = 2,
    
    [System.Xml.Serialization.XmlEnumAttribute("3")]
    STATUS_CANCELLATION_DENIED = 3,
    
    [System.Xml.Serialization.XmlEnumAttribute("4")]
    STATUS_INTERNAL_ERROR = 4,
    
    [System.Xml.Serialization.XmlEnumAttribute("5")]
    STATUS_INVALID_ARGUMENTS = 5,
    
    [System.Xml.Serialization.XmlEnumAttribute("6")]
    STATUS_RESOURCES_EXCEEDED = 6,
    
    [System.Xml.Serialization.XmlEnumAttribute("7")]
    STATUS_FILE_TRANSFER_FAILED = 7,
    
    [System.Xml.Serialization.XmlEnumAttribute("8")]
    STATUS_FILE_TRANSFER_SERVER_AUTHENTICATION_FAILURE = 8,
    
    [System.Xml.Serialization.XmlEnumAttribute("9")]
    STATUS_UNSUPPORTED_PROTOCOL = 9,
    
    [System.Xml.Serialization.XmlEnumAttribute("10")]
    STATUS_UPLOAD_FAILED = 10,
    
    [System.Xml.Serialization.XmlEnumAttribute("11")]
    STATUS_FILE_TRANSFER_FAILED_MULTICAST_GROUP_UNABLE_JOIN = 11,
    
    [System.Xml.Serialization.XmlEnumAttribute("12")]
    STATUS_FILE_TRANSFER_FAILED_SERVER_CONTACT_FAILED = 12,
    
    [System.Xml.Serialization.XmlEnumAttribute("13")]
    STATUS_FILE_TRANSFER_FAILED_FILE_ACCESS_FAILED = 13,
    
    [System.Xml.Serialization.XmlEnumAttribute("14")]
    STATUS_FILE_TRANSFER_FAILED_DOWNLOAD_INCOMPLETE = 14,
    
    [System.Xml.Serialization.XmlEnumAttribute("15")]
    STATUS_FILE_TRANSFER_FAILED_FILE_CORRUPTED = 15,
    
    [System.Xml.Serialization.XmlEnumAttribute("16")]
    STATUS_FILE_TRANSFER_FILE_AUTHENTICATION_FAILURE = 16,
    
    [System.Xml.Serialization.XmlEnumAttribute("19")]
    STATUS_FILE_TRANSFER_WINDOW_EXCEEDED = 19,
    
    [System.Xml.Serialization.XmlEnumAttribute("20")]
    STATUS_INVALID_UUID_FORMAT = 20,
    
    [System.Xml.Serialization.XmlEnumAttribute("21")]
    STATUS_UNKNOWN_EXECUTION_ENVIRONMENT = 21,
    
    [System.Xml.Serialization.XmlEnumAttribute("22")]
    STATUS_DISABLED_EXECUTION_ENVIRONMENT = 22,
    
    [System.Xml.Serialization.XmlEnumAttribute("23")]
    STATUS_EXECUTION_ENVIRONMENT_MISMATCH = 23,
    
    [System.Xml.Serialization.XmlEnumAttribute("24")]
    STATUS_DUPLICATE_DEPLOYMENT_UNIT = 24,
    
    [System.Xml.Serialization.XmlEnumAttribute("25")]
    STATUS_SYSTEM_RESOURCES_EXCEEDED = 25,
    
    [System.Xml.Serialization.XmlEnumAttribute("26")]
    STATUS_UNKNOWN_DEPLOYMENT_UNIT = 26,
    
    [System.Xml.Serialization.XmlEnumAttribute("27")]
    STATUS_INVALID_DEPLOYMENT_UNIT_STATE = 27,
    
    [System.Xml.Serialization.XmlEnumAttribute("28")]
    STATUS_INVALID_DEPLOYMENT_UNIT_UPDATE_DOWNGRADE_DISALLOWED = 28,
    
    [System.Xml.Serialization.XmlEnumAttribute("29")]
    STATUS_INVALID_DEPLOYMENT_UNIT_UPDATE_UPGRADE_DISALLOWED = 29,
    
    [System.Xml.Serialization.XmlEnumAttribute("30")]
    STATUS_INVALID_DEPLOYMENT_UNIT_UPDATE_VERSION_EXISTS = 30,
}
}
