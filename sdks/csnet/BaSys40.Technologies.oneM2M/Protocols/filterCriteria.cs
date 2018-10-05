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
public partial class filterCriteria {
    
    private List<string> ctyField;
    
    private List<attribute> atrField;
    
    private List<string> smfField;
    
    public string Crb {get; set;}

    public string Cra {get; set;}

    public string Ms {get; set;}

    public string Us {get; set;}

    public System.Nullable<int> Sts {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> StsSpecified {get; set;}

    public System.Nullable<int> Stb {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> StbSpecified {get; set;}

    public string Exb {get; set;}

    public string Exa {get; set;}

        private List<string> lblField;

        public List<string> Lbl
        {
            get
            {
                if ((this.lblField == null))
                {
                    this.lblField = new List<string>();
                }
                return this.lblField;
            }
            set
            {
                this.lblField = value;
            }
        }

        public string Ty {get; set;}

    public System.Nullable<int> Sza {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> SzaSpecified {get; set;}

    public System.Nullable<int> Szb {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> SzbSpecified {get; set;}

    public System.Nullable<int> Fu {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> FuSpecified {get; set;}

    public System.Nullable<int> Lim {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> LimSpecified {get; set;}

    public System.Nullable<bool> Fo {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> FoSpecified {get; set;}

    public System.Nullable<int> Cfs {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> CfsSpecified {get; set;}

    public string Cfq {get; set;}

    public System.Nullable<int> Lvl {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> LvlSpecified {get; set;}

    public System.Nullable<int> Ofst {get; set;}

    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public System.Nullable<bool> OfstSpecified {get; set;}

    
    public List<string> Cty {
        get {
            if ((this.ctyField == null)) {
                this.ctyField = new List<string>();
            }
            return this.ctyField;
        }
        set {
            this.ctyField = value;
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
    
    public List<string> Smf {
        get {
            if ((this.smfField == null)) {
                this.smfField = new List<string>();
            }
            return this.smfField;
        }
        set {
            this.smfField = value;
        }
    }
}
}
