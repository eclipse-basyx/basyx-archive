namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    public partial class regularResource : resource
    {
        private List<string> acpiField;

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

        public string Et { get; set; }

        public string Daci { get; set; }


    }
}
