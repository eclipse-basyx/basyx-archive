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
public partial class dlv : regularResource {
    
    private deliveryMetaData dmdField;
    
    private List<aggregatedRequestReq> arqField;
    
    private List<object> itemsField;
    
    public System.Nullable<int> St {get; set;}

    public string Sr {get; set;}

    public string Tg {get; set;}

    public string Ls {get; set;}

    public string Eca {get; set;}

    
    public deliveryMetaData Dmd {
        get {
            if ((this.dmdField == null)) {
                this.dmdField = new deliveryMetaData();
            }
            return this.dmdField;
        }
        set {
            this.dmdField = value;
        }
    }
    
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=6)]
    [System.Xml.Serialization.XmlArrayItemAttribute("req", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public List<aggregatedRequestReq> Arq {
        get {
            if ((this.arqField == null)) {
                this.arqField = new List<aggregatedRequestReq>();
            }
            return this.arqField;
        }
        set {
            this.arqField = value;
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
