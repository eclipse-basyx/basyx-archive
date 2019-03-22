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
public partial class metaInformation {
    
    private responseTypeInfo rtField;
    
    private filterCriteria fcField;
    
    public System.Nullable<int> Ty {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> TySpecified {get; set;}

    public string Nm {get; set;}

    public string Ot {get; set;}

    public string Rqet {get; set;}

    public string Rset {get; set;}

    public string Oet {get; set;}

    public string Rp {get; set;}

    public System.Nullable<int> Rcn {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> RcnSpecified {get; set;}

    public string Ec {get; set;}

    public System.Nullable<bool> Da {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> DaSpecified {get; set;}

    public string Gid {get; set;}

    public System.Nullable<int> Drt {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> DrtSpecified {get; set;}

    
    public responseTypeInfo Rt {
        get {
            if ((this.rtField == null)) {
                this.rtField = new responseTypeInfo();
            }
            return this.rtField;
        }
        set {
            this.rtField = value;
        }
    }
    
    public filterCriteria Fc {
        get {
            if ((this.fcField == null)) {
                this.fcField = new filterCriteria();
            }
            return this.fcField;
        }
        set {
            this.fcField = value;
        }
    }
}
}
