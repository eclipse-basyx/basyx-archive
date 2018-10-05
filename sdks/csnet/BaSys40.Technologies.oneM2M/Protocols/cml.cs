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
public partial class cml : mgmtResource {
    
    public System.Nullable<int> Od {get; set;}

    public string Ror {get; set;}

    public object Rct {get; set;}

    public System.Nullable<bool> Rctn {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> RctnSpecified {get; set;}

    public object Rch {get; set;}

    public string Lec {get; set;}

    public string Lqet {get; set;}

    public string Lset {get; set;}

    public string Loet {get; set;}

    public string Lrp {get; set;}

    public cmlLda Lda {get; set;}

}
}
