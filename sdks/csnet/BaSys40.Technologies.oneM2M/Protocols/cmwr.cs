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
public partial class cmwr : mgmtResource {
    
    private List<backOffParametersBops> bopField;
    
    private mgmtLinkRef cmlkField;
    
    public string Ttn {get; set;}

    public System.Nullable<int> Mrv {get; set;}

    public System.Nullable<int> Swt {get; set;}

    public object Ohc {get; set;}

    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=3)]
    [System.Xml.Serialization.XmlArrayItemAttribute("bops", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<backOffParametersBops> Bop {
        get {
            if ((this.bopField == null)) {
                this.bopField = new List<backOffParametersBops>();
            }
            return this.bopField;
        }
        set {
            this.bopField = value;
        }
    }
    
    public mgmtLinkRef Cmlk {
        get {
            if ((this.cmlkField == null)) {
                this.cmlkField = new mgmtLinkRef();
            }
            return this.cmlkField;
        }
        set {
            this.cmlkField = value;
        }
    }
}
}
