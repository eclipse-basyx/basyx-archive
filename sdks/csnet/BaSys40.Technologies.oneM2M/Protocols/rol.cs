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
public partial class rol : regularResource {
    
    private List<object> itemsField;
    
    public string Rlid {get; set;}

    public string Tkis {get; set;}

    public string Tkhd {get; set;}

    public string Tknb {get; set;}

    public string Tkna {get; set;}

    public string Rlnm {get; set;}

    public string Rltl {get; set;}

    
    public List<object> Items {
        get {
            if ((this.itemsField == null)) {
                this.itemsField = new List<object>();
            }
            return this.itemsField;
        }
        set {
            this.itemsField = value;
        }
    }
}
}
