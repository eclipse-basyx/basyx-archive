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
public partial class execReqArgsListType {
    
    private List<object> itemsField;
    
    private List<ItemsChoiceType1> itemsElementNameField;
    
    [System.Xml.Serialization.XmlChoiceIdentifierAttribute("ItemsElementName")]
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
    
    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public List<ItemsChoiceType1> ItemsElementName {
        get {
            if ((this.itemsElementNameField == null)) {
                this.itemsElementNameField = new List<ItemsChoiceType1>();
            }
            return this.itemsElementNameField;
        }
        set {
            this.itemsElementNameField = value;
        }
    }
}
}
