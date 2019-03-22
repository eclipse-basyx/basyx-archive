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
public enum responseStatusCode {
    
    [System.Xml.Serialization.XmlEnumAttribute("1000")]
    ACCEPTED = 1000,
    
    [System.Xml.Serialization.XmlEnumAttribute("2000")]
    OK = 2000,
    
    [System.Xml.Serialization.XmlEnumAttribute("2001")]
    CREATED = 2001,
    
    [System.Xml.Serialization.XmlEnumAttribute("2002")]
    DELETED = 2002,
    
    [System.Xml.Serialization.XmlEnumAttribute("2004")]
    UPDATED = 2004,
    
    [System.Xml.Serialization.XmlEnumAttribute("4000")]
    BAD_REQUEST = 4000,
    
    [System.Xml.Serialization.XmlEnumAttribute("4004")]
    NOT_FOUND = 4004,
    
    [System.Xml.Serialization.XmlEnumAttribute("4005")]
    OPERATION_NOT_ALLOWED = 4005,
    
    [System.Xml.Serialization.XmlEnumAttribute("4008")]
    REQUEST_TIMEOUT = 4008,
    
    [System.Xml.Serialization.XmlEnumAttribute("4101")]
    SUBSCRIPTION_CREATOR_HAS_NO_PRIVILEGE = 4101,
    
    [System.Xml.Serialization.XmlEnumAttribute("4102")]
    CONTENTS_UNACCEPTABLE = 4102,
    
    [System.Xml.Serialization.XmlEnumAttribute("4103")]
    ORIGINATOR_HAS_NO_PRIVILEGE = 4103,
    
    [System.Xml.Serialization.XmlEnumAttribute("4104")]
    GROUP_REQUEST_IDENTIFIER_EXISTS = 4104,
    
    [System.Xml.Serialization.XmlEnumAttribute("4105")]
    CONFLICT = 4105,
    
    [System.Xml.Serialization.XmlEnumAttribute("4106")]
    ORIGINATOR_HAS_NOT_REGISTERED = 4106,
    
    [System.Xml.Serialization.XmlEnumAttribute("4107")]
    SECURITY_ASSOCIATION_REQUIRED = 4107,
    
    [System.Xml.Serialization.XmlEnumAttribute("4108")]
    INVALID_CHILD_RESOURCE_TYPE = 4108,
    
    [System.Xml.Serialization.XmlEnumAttribute("4109")]
    NO_MEMBERS = 4109,
    
    [System.Xml.Serialization.XmlEnumAttribute("4110")]
    GROUP_MEMBER_TYPE_INCONSISTENT = 4110,
    
    [System.Xml.Serialization.XmlEnumAttribute("4111")]
    ESPRIM_UNSUPPORTED_OPTION = 4111,
    
    [System.Xml.Serialization.XmlEnumAttribute("4112")]
    ESPRIM_UNKNOWN_KEY_ID = 4112,
    
    [System.Xml.Serialization.XmlEnumAttribute("4113")]
    GESPRIM_UNKNOWN_ORIG_RAND_ID = 4113,
    
    [System.Xml.Serialization.XmlEnumAttribute("4114")]
    ESPRIM_UNKNOWN_RECV_RAND_ID = 4114,
    
    [System.Xml.Serialization.XmlEnumAttribute("4115")]
    GESPRIM_BAD_MAC = 4115,
    
    [System.Xml.Serialization.XmlEnumAttribute("5000")]
    INTERNAL_SERVER_ERROR = 5000,
    
    [System.Xml.Serialization.XmlEnumAttribute("5001")]
    NOT_IMPLEMENTED = 5001,
    
    [System.Xml.Serialization.XmlEnumAttribute("5103")]
    TARGET_NOT_REACHABLE = 5103,
    
    [System.Xml.Serialization.XmlEnumAttribute("5105")]
    RECEIVER_HAS_NO_PRIVILEGE = 5105,
    
    [System.Xml.Serialization.XmlEnumAttribute("5106")]
    ALREADY_EXISTS = 5106,
    
    [System.Xml.Serialization.XmlEnumAttribute("5203")]
    TARGET_NOT_SUBSCRIBABLE = 5203,
    
    [System.Xml.Serialization.XmlEnumAttribute("5204")]
    SUBSCRIPTION_VERIFICATION_INITIATION_FAILED = 5204,
    
    [System.Xml.Serialization.XmlEnumAttribute("5205")]
    SUBSCRIPTION_HOST_HAS_NO_PRIVILEGE = 5205,
    
    [System.Xml.Serialization.XmlEnumAttribute("5206")]
    NON_BLOCKING_REQUEST_NOT_SUPPORTED = 5206,
    
    [System.Xml.Serialization.XmlEnumAttribute("5207")]
    NOT_ACCEPTABLE = 5207,
    
    [System.Xml.Serialization.XmlEnumAttribute("5208")]
    DISCOVERY_DENIED_BY_IPE = 5208,
    
    [System.Xml.Serialization.XmlEnumAttribute("5209")]
    GROUP_MEMBERS_NOT_RESPONDED = 5209,
    
    [System.Xml.Serialization.XmlEnumAttribute("5210")]
    ESPRIM_DECRYPTION_ERROR = 5210,
    
    [System.Xml.Serialization.XmlEnumAttribute("5211")]
    ESPRIM_ENCRYPTION_ERROR = 5211,
    
    [System.Xml.Serialization.XmlEnumAttribute("5212")]
    SPARQL_UPDATE_ERROR = 5212,
    
    [System.Xml.Serialization.XmlEnumAttribute("6003")]
    EXTERNAL_OBJECT_NOT_REACHABLE = 6003,
    
    [System.Xml.Serialization.XmlEnumAttribute("6005")]
    EXTERNAL_OBJECT_NOT_FOUND = 6005,
    
    [System.Xml.Serialization.XmlEnumAttribute("6010")]
    MAX_NUMBER_OF_MEMBER_EXCEEDED = 6010,
    
    [System.Xml.Serialization.XmlEnumAttribute("6020")]
    MGMT_SESSION_CANNOT_BE_ESTABLISHED = 6020,
    
    [System.Xml.Serialization.XmlEnumAttribute("6021")]
    MGMT_SESSION_ESTABLISHMENT_TIMEOUT = 6021,
    
    [System.Xml.Serialization.XmlEnumAttribute("6022")]
    INVALID_CMDTYPE = 6022,
    
    [System.Xml.Serialization.XmlEnumAttribute("6023")]
    INVALID_ARGUMENTS = 6023,
    
    [System.Xml.Serialization.XmlEnumAttribute("6024")]
    INSUFFICIENT_ARGUMENTS = 6024,
    
    [System.Xml.Serialization.XmlEnumAttribute("6025")]
    MGMT_CONVERSION_ERROR = 6025,
    
    [System.Xml.Serialization.XmlEnumAttribute("6026")]
    MGMT_CANCELLATION_FAILED = 6026,
    
    [System.Xml.Serialization.XmlEnumAttribute("6028")]
    ALREADY_COMPLETE = 6028,
    
    [System.Xml.Serialization.XmlEnumAttribute("6029")]
    MGMT_COMMAND_NOT_CANCELLABLE = 6029,
}
}
