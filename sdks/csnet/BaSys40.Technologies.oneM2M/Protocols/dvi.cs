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
public partial class dvi : mgmtResource {
    
    private List<object> itemsField;
    
    public string Dlb {get; set;}

    public string Man {get; set;}

    public string Mod {get; set;}

    public string Dty {get; set;}

    public string Fwv {get; set;}

    public string Swv {get; set;}

    public string Hwv {get; set;}

    
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
