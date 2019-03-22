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
    public partial class responseTypeInfo
    {

        private List<string> nuField;
        public System.Nullable<int> Rtv { get; set; }

        public List<string> Nu
        {
            get
            {
                if ((this.nuField == null))
                {
                    this.nuField = new List<string>();
                }
                return this.nuField;
            }
            set
            {
                this.nuField = value;
            }
        }

    }
}
