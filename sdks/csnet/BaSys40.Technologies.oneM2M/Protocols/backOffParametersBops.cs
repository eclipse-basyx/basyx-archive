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
public partial class backOffParametersBops {
    
    public System.Nullable<int> Nwa {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> NwaSpecified {get; set;}

    public System.Nullable<int> Ibt {get; set;}

    public System.Nullable<int> Abt {get; set;}

    public System.Nullable<int> Mbt {get; set;}

    public System.Nullable<int> Rbt {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> RbtSpecified {get; set;}

}
}
