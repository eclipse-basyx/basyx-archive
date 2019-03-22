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
    public partial class ae : announceableResource
    {

        public string Apn { get; set; }

        public string Api { get; set; }

        public string Aei { get; set; }

        public string Poa { get; set; }

        public string Or { get; set; }

        public string Nl { get; set; }

        public bool? Rr { get; set; }

        public string Csz { get; set; }

        [TypeConverter(typeof(ExpandableObjectConverter))]
        public e2eSecInfo Esi { get; set; }

    }
}
