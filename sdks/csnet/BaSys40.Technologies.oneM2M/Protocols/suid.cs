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
public enum suid {
    
    [System.Xml.Serialization.XmlEnumAttribute("10")]
    PreprovisionedsymmetrickeyintendedtobesharedwithaMEF = 10,
    
    [System.Xml.Serialization.XmlEnumAttribute("11")]
    PreprovisionedsymmetrickeyintendedtobesharedwithaMAF = 11,
    
    [System.Xml.Serialization.XmlEnumAttribute("12")]
    PreprovisionedsymmetrickeyintendedforuseinaSecurityAssociatedEstablishmentFrameworkSAEF = 12,
    
    [System.Xml.Serialization.XmlEnumAttribute("13")]
    PreprovisionedsymmetrickeyintendedforuseinEndtoEndSecurityofPrimitivesESPrim = 13,
    
    [System.Xml.Serialization.XmlEnumAttribute("14")]
    PreprovisionedsymmetrickeyintendedforusewithauthenticatedencryptionintheEncryptiononlyorNestedSignthenEncryptEndtoEndSecurityofDataESDataDataclasses = 14,
    
    [System.Xml.Serialization.XmlEnumAttribute("15")]
    PreprovisionedsymmetrickeyintendedforuseinSignatureonlyESDataSecurityClass = 15,
    
    [System.Xml.Serialization.XmlEnumAttribute("21")]
    SymmetrickeyprovisionedviaaRemoteSecurityProvisioningFrameworkRSPFandintendedtobesharedwithaMAF = 21,
    
    [System.Xml.Serialization.XmlEnumAttribute("22")]
    SymmetrickeyprovisionedviaaRSPFandintendedforuseinaSAEF = 22,
    
    [System.Xml.Serialization.XmlEnumAttribute("23")]
    SymmetrickeyprovisionedviaaRSPFandintendedforuseinESPrim = 23,
    
    [System.Xml.Serialization.XmlEnumAttribute("24")]
    SymmetrickeyprovisionedviaaRSPFandintendedforusewithauthenticatedencryptionintheEncryptiononlyorNestedSignthenEncryptESDataDataclasses = 24,
    
    [System.Xml.Serialization.XmlEnumAttribute("25")]
    SymmetrickeyprovisionedviaaRSPFandintendedforuseinSignatureonlyESDataSecurityClass = 25,
    
    [System.Xml.Serialization.XmlEnumAttribute("32")]
    MAFdistributedsymmetrickeyintendedforuseinaSAEF = 32,
    
    [System.Xml.Serialization.XmlEnumAttribute("33")]
    MAFdistributedsymmetrickeyintendedforuseinESPrim = 33,
    
    [System.Xml.Serialization.XmlEnumAttribute("34")]
    MAFdistributedsymmetrickeyintendedforusewithauthenticatedencryptionintheEncryptiononlyorNestedSignthenEncryptESDataDataclasses = 34,
    
    [System.Xml.Serialization.XmlEnumAttribute("35")]
    MAFdistributedsymmetrickeyintendedforuseinSignatureonlyESDataSecurityClass = 35,
    
    [System.Xml.Serialization.XmlEnumAttribute("40")]
    CertificateintendedtobesharedwithaMEF = 40,
    
    [System.Xml.Serialization.XmlEnumAttribute("41")]
    CertificateintendedtobesharedwithaMAF = 41,
    
    [System.Xml.Serialization.XmlEnumAttribute("42")]
    CertificateintendedforuseinaSecurityAssociatedEstablishmentFrameworkSAEF = 42,
    
    [System.Xml.Serialization.XmlEnumAttribute("43")]
    CertificateintendedforuseinEndtoEndSecurityofPrimitivesESPrim = 43,
    
    [System.Xml.Serialization.XmlEnumAttribute("44")]
    CertificateintendedforusewithauthenticatedencryptionintheEncryptiononlyorNestedSignthenEncryptEndtoEndSecurityofDataESDataDataclasses = 44,
    
    [System.Xml.Serialization.XmlEnumAttribute("45")]
    CertificateintendedforuseinSignatureonlyESDataSecurityClass = 45,
}
}
