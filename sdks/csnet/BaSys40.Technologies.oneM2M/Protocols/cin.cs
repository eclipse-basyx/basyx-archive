namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    using System.ComponentModel;

    public partial class cin : announceableSubordinateResource
    {
        private List<contentRefUrir> conrField;

        public int? St { get; set; }

        public string Cr { get; set; }

        public string Cnf { get; set; }

        public int? Cs { get; set; }

        public string Or { get; set; }

        public string Con { get; set; }

        public List<contentRefUrir> Conr
        {
            get
            {
                if ((this.conrField == null))
                {
                    this.conrField = new List<contentRefUrir>();
                }
                return this.conrField;
            }
            set
            {
                this.conrField = value;
            }
        }
    }
}
