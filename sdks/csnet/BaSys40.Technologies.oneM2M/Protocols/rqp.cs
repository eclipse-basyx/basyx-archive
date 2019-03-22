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
public partial class rqp {
    
    private primitiveContent pcField;
    
    private responseTypeInfo rtField;
    
    private filterCriteria fcField;
    
    public int? Op {get; set;}

    public string To {get; set;}

    public string Fr {get; set;}

    public string Rqi {get; set;}

    public int? Ty {get; set;}

    public string Rids {get; set;}

    public string Ot {get; set;}

    public string Rqet {get; set;}

    public string Rset {get; set;}

    public string Oet {get; set;}

    public string Rp {get; set;}

    public int? Rcn {get; set;}

    public string Ec {get; set;}

    public bool? Da {get; set;}

    public string Gid {get; set;}

    public int? Drt {get; set;}

    public string Tkns {get; set;}

    public string Tids {get; set;}

    public string Ltids {get; set;}

    public bool? Tqi {get; set;}

    public primitiveContent Pc {
        get {
            if ((this.pcField == null)) {
                this.pcField = new primitiveContent();
            }
            return this.pcField;
        }
        set {
            this.pcField = value;
        }
    }
    
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
