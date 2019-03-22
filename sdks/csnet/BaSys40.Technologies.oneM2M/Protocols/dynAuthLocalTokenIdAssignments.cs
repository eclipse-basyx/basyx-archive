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
public partial class dynAuthLocalTokenIdAssignments {
    
    private List<dynAuthLocalTokenIdAssignmentsLtia> ltiaField;
    
    public List<dynAuthLocalTokenIdAssignmentsLtia> Ltia {
        get {
            if ((this.ltiaField == null)) {
                this.ltiaField = new List<dynAuthLocalTokenIdAssignmentsLtia>();
            }
            return this.ltiaField;
        }
        set {
            this.ltiaField = value;
        }
    }
}
}
