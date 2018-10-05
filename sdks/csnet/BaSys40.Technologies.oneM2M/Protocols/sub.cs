namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    using System.ComponentModel;
    using oneM2MClient.Utils;

    public partial class sub : regularResource
    {
        private eventNotificationCriteria encField;

        private batchNotify bnField;

        private rateLimit rlField;

        public string Cr { get; set; }

        public int? Exc { get; set; }

        private List<string> nuField;
        
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

        public string Gpi { get; set; }

        public string Nfu { get; set; }

        public int? Psn { get; set; }

        public int? Pn { get; set; }

        public int? Nsp { get; set; }

        public bool? Ln { get; set; }

        public int? Nct { get; set; }

        public string Nec { get; set; }

        public string Su { get; set; }

        [TypeConverter(typeof(ExpandableObjectConverter))]
        public eventNotificationCriteria Enc
        {
            get
            {
                if ((this.encField == null))
                {
                    this.encField = new eventNotificationCriteria();
                }
                return this.encField;
            }
            set
            {
                this.encField = value;
            }
        }
        [TypeConverter(typeof(ExpandableObjectConverter))]
        public batchNotify Bn
        {
            get
            {
                if ((this.bnField == null))
                {
                    this.bnField = new batchNotify();
                }
                return this.bnField;
            }
            set
            {
                this.bnField = value;
            }
        }
        [TypeConverter(typeof(ExpandableObjectConverter))]
        public rateLimit Rl
        {
            get
            {
                if ((this.rlField == null))
                {
                    this.rlField = new rateLimit();
                }
                return this.rlField;
            }
            set
            {
                this.rlField = value;
            }
        }
    }
}
