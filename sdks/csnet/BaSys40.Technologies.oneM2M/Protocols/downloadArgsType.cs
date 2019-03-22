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
public partial class downloadArgsType {
    
    private List<anyArgType> anyField;
    
    public string Ftyp {get; set;}

    public string Url {get; set;}

    public string Unm {get; set;}

    public string Pwd {get; set;}

    public System.Nullable<int> Fsi {get; set;}

    public string Tgf {get; set;}

    public System.Nullable<int> Dss {get; set;}

    public string Surl {get; set;}

    public string Stt {get; set;}

    public string Cpt {get; set;}

    
    public List<anyArgType> Any {
        get {
            if ((this.anyField == null)) {
                this.anyField = new List<anyArgType>();
            }
            return this.anyField;
        }
        set {
            this.anyField = value;
        }
    }
}
}
