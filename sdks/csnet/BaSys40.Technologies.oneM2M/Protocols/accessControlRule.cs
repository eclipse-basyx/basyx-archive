namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    public partial class accessControlRule
    {

        private List<accessControlRuleAcco> accoField;

        private List<string> acorField;

        public List<string> Acor
        {
            get
            {
                if ((this.acorField == null))
                {
                    this.acorField = new List<string>();
                }
                return this.acorField;
            }
            set
            {
                this.acorField = value;
            }
        }

        public int? Acop { get; set; }

        public bool? Acaf { get; set; }

        public List<accessControlRuleAcco> Acco
        {
            get
            {
                if ((this.accoField == null))
                {
                    this.accoField = new List<accessControlRuleAcco>();
                }
                return this.accoField;
            }
            set
            {
                this.accoField = value;
            }
        }
    }
}
