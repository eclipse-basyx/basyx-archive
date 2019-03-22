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
public partial class eventNotificationCriteria {
    
    private List<int> omField;
    
    private List<attribute> atrField;
    
    private List<int> netField;
    
    private missingData mdField;
    
    public string Crb {get; set;}

    public string Cra {get; set;}

    public string Ms {get; set;}

    public string Us {get; set;}

    public System.Nullable<int> Sts {get; set;}

    public System.Nullable<int> Stb {get; set;}

    public string Exb {get; set;}

    public string Exa {get; set;}

    public System.Nullable<int> Sza {get; set;}

    public System.Nullable<int> Szb {get; set;}
    
    public List<int> Om {
        get {
            if ((this.omField == null)) {
                this.omField = new List<int>();
            }
            return this.omField;
        }
        set {
            this.omField = value;
        }
    }
    
    public List<attribute> Atr {
        get {
            if ((this.atrField == null)) {
                this.atrField = new List<attribute>();
            }
            return this.atrField;
        }
        set {
            this.atrField = value;
        }
    }
    
    public List<int> Net {
        get {
            if ((this.netField == null)) {
                this.netField = new List<int>();
            }
            return this.netField;
        }
        set {
            this.netField = value;
        }
    }
    
    public missingData Md {
        get {
            if ((this.mdField == null)) {
                this.mdField = new missingData();
            }
            return this.mdField;
        }
        set {
            this.mdField = value;
        }
    }
}
}
