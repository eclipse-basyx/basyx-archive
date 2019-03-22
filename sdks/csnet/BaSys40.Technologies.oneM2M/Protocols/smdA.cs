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
public partial class smdA : announcedResource {
    
    private List<byte> dspField;
    
    private List<object> itemsField;
    
    public string Dcrp {get; set;}

    public string Soe {get; set;}

    public string Or {get; set;}

    public string Rels {get; set;}

    
    public List<byte> Dsp {
        get {
            if ((this.dspField == null)) {
                this.dspField = new List<byte>();
            }
            return this.dspField;
        }
        set {
            this.dspField = value;
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
