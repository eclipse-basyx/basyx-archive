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
public partial class accessControlRuleAcco {
    
    private List<string> actwField;
    
    private accessControlRuleAccoAcip acipField;
    
    private locationRegion aclrField;
    
    public List<string> Actw {
        get {
            if ((this.actwField == null)) {
                this.actwField = new List<string>();
            }
            return this.actwField;
        }
        set {
            this.actwField = value;
        }
    }
    
    public accessControlRuleAccoAcip Acip {
        get {
            if ((this.acipField == null)) {
                this.acipField = new accessControlRuleAccoAcip();
            }
            return this.acipField;
        }
        set {
            this.acipField = value;
        }
    }
    
    public locationRegion Aclr {
        get {
            if ((this.aclrField == null)) {
                this.aclrField = new locationRegion();
            }
            return this.aclrField;
        }
        set {
            this.aclrField = value;
        }
    }
}
}
