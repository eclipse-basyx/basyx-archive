namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    using System.ComponentModel;
    public partial class cb : resource
    {
        private e2eSecInfo esiField;

        private List<object> itemsField;

        private List<string> acpiField;

        private List<string> poaField;

        public List<string> Acpi
        {
            get
            {
                if ((this.acpiField == null))
                {
                    this.acpiField = new List<string>();
                }
                return this.acpiField;
            }
            set
            {
                this.acpiField = value;
            }
        }

        public string Cst { get; set; }

        public string Csi { get; set; }

        public List<int> Srt { get; set; }

        public List<string> Poa {
            get
            {
                if ((this.poaField == null))
                {
                    this.poaField = new List<string>();
                }
                return this.poaField;
            }
            set
            {
                this.poaField = value;
            }
        }

        public string Nl { get; set; }

        [TypeConverter(typeof(ExpandableObjectConverter))]
        public e2eSecInfo Esi
        {
            get
            {
                if ((this.esiField == null))
                {
                    this.esiField = new e2eSecInfo();
                }
                return this.esiField;
            }
            set
            {
                this.esiField = value;
            }
        }

        public List<object> Items
        {
            get
            {
                if ((this.itemsField == null))
                {
                    this.itemsField = new List<object>();
                }
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }
    }
}
