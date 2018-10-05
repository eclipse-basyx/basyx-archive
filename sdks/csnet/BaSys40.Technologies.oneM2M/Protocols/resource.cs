namespace oneM2MClient.Protocols
{
    using System.ComponentModel;
    using System.Collections.Generic;
    public partial class resource
    {

        public int? Ty { get; set; }

        public string Ri { get; set; }

        public string Pi { get; set; }

        public string Ct { get; set; }

        public string Lt { get; set; }

        private List<string> lblField;

        public List<string> Lbl
        {
            get
            {
                if ((this.lblField == null))
                {
                    this.lblField = new List<string>();
                }
                return this.lblField;
            }
            set
            {
                this.lblField = value;
            }
        }

        public string Rn { get; set; }

    }
}
