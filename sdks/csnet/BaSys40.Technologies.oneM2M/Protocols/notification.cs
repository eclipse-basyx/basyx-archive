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
public partial class notification {
    
    private notificationNev nevField;
    
    private notificationIdr idrField;
    
    public System.Nullable<bool> Vrq {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> VrqSpecified {get; set;}

    public System.Nullable<bool> Sud {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> SudSpecified {get; set;}

    public string Sur {get; set;}

    public string Cr {get; set;}

    public string Nfu {get; set;}

    
    public notificationNev Nev {
        get {
            if ((this.nevField == null)) {
                this.nevField = new notificationNev();
            }
            return this.nevField;
        }
        set {
            this.nevField = value;
        }
    }
    
    public notificationIdr Idr {
        get {
            if ((this.idrField == null)) {
                this.idrField = new notificationIdr();
            }
            return this.idrField;
        }
        set {
            this.idrField = value;
        }
    }
}
}
