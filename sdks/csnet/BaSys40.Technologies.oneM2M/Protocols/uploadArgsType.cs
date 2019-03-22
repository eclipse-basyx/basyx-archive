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
public partial class uploadArgsType {
    
    private List<anyArgType> anyField;
    
    public string Ftyp {get; set;}

    public string Url {get; set;}

    public string Unm {get; set;}

    public string Pwd {get; set;}

    
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
