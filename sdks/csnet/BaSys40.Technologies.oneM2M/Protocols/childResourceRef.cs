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
public partial class childResourceRef {
    
    public string Nm {get; set;}

    public System.Nullable<int> Typ {get; set;}

    public string Spid {get; set;}

    [System.Xml.Serialization.XmlTextAttribute(DataType="anyURI")]
    public string Value {get; set;}

}
}
