namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    public partial class rsp
    {
        private primitiveContent pcField;

        private List<dynAuthLocalTokenIdAssignmentsLtia> atiField;

        private List<dynAuthTokenReqInfoDasi> tqfField;

        public int? Rsc { get; set; }

        public string Rqi { get; set; }

        public string To { get; set; }

        public string Fr { get; set; }

        public string Ot { get; set; }

        public string Rset { get; set; }

        public string Ec { get; set; }

        public int? Cnst { get; set; }

        public int? Cnot { get; set; }


        public primitiveContent Pc
        {
            get
            {
                if ((this.pcField == null))
                {
                    this.pcField = new primitiveContent();
                }
                return this.pcField;
            }
            set
            {
                this.pcField = value;
            }
        }

        [System.Xml.Serialization.XmlArrayAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 10)]
        [System.Xml.Serialization.XmlArrayItemAttribute("ltia", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = false)]
        public List<dynAuthLocalTokenIdAssignmentsLtia> Ati
        {
            get
            {
                if ((this.atiField == null))
                {
                    this.atiField = new List<dynAuthLocalTokenIdAssignmentsLtia>();
                }
                return this.atiField;
            }
            set
            {
                this.atiField = value;
            }
        }

        [System.Xml.Serialization.XmlArrayAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 11)]
        [System.Xml.Serialization.XmlArrayItemAttribute("dasi", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = false)]
        public List<dynAuthTokenReqInfoDasi> Tqf
        {
            get
            {
                if ((this.tqfField == null))
                {
                    this.tqfField = new List<dynAuthTokenReqInfoDasi>();
                }
                return this.tqfField;
            }
            set
            {
                this.tqfField = value;
            }
        }
    }
}
