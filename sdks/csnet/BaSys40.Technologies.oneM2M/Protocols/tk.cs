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
public partial class tk : regularResource {
    
    private tkTkps tkpsField;
    
    private List<object> itemsField;
    
    public string Tkid {get; set;}

    public string Tkob {get; set;}

    public string Vr {get; set;}

    public string Tkis {get; set;}

    public string Tkhd {get; set;}

    public string Tknb {get; set;}

    public string Tkna {get; set;}

    public string Tknm {get; set;}

    public string Tkau {get; set;}

    public string Tkex {get; set;}

    
    public tkTkps Tkps {
        get {
            if ((this.tkpsField == null)) {
                this.tkpsField = new tkTkps();
            }
            return this.tkpsField;
        }
        set {
            this.tkpsField = value;
        }
    }
    
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
