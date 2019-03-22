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
public partial class tokenClaimSet {
    
    private List<tokenPermission> tkpsField;
    
    public string Vr {get; set;}

    public string Tkid {get; set;}

    public string Tkhd {get; set;}

    public string Tkis {get; set;}

    public string Tknb {get; set;}

    public string Tkna {get; set;}

    public string Tknm {get; set;}

    public string Tkau {get; set;}

    public string Tkex {get; set;}

    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=8)]
    [System.Xml.Serialization.XmlArrayItemAttribute("pm", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<tokenPermission> Tkps {
        get {
            if ((this.tkpsField == null)) {
                this.tkpsField = new List<tokenPermission>();
            }
            return this.tkpsField;
        }
        set {
            this.tkpsField = value;
        }
    }
}
}
