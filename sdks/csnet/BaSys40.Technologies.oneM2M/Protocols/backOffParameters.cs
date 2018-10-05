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
public partial class backOffParameters {
    
    private List<backOffParametersBops> bopsField;
    
    public List<backOffParametersBops> Bops {
        get {
            if ((this.bopsField == null)) {
                this.bopsField = new List<backOffParametersBops>();
            }
            return this.bopsField;
        }
        set {
            this.bopsField = value;
        }
    }
}
}
