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
public partial class req : regularResource {
    
    private metaInformation miField;
    
    private primitiveContent pcField;
    
    private operationResult orsField;
    
    private List<object> itemsField;
    
    public System.Nullable<int> St {get; set;}

    public System.Nullable<int> Op {get; set;}

    public string Tg {get; set;}

    public string Org {get; set;}

    public string Rid {get; set;}

    public System.Nullable<int> Rs {get; set;}

    
    public metaInformation Mi {
        get {
            if ((this.miField == null)) {
                this.miField = new metaInformation();
            }
            return this.miField;
        }
        set {
            this.miField = value;
        }
    }
    
    public primitiveContent Pc {
        get {
            if ((this.pcField == null)) {
                this.pcField = new primitiveContent();
            }
            return this.pcField;
        }
        set {
            this.pcField = value;
        }
    }
    
    public operationResult Ors {
        get {
            if ((this.orsField == null)) {
                this.orsField = new operationResult();
            }
            return this.orsField;
        }
        set {
            this.orsField = value;
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
