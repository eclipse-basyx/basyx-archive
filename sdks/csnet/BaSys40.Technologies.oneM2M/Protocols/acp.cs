namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    using System.ComponentModel;

    public partial class acp : announceableSubordinateResource
    {
        private List<object> itemsField;

        [TypeConverter(typeof(ExpandableObjectConverter))]
        public pv Pv { get; set; }
        [TypeConverter(typeof(ExpandableObjectConverter))]
        public pv Pvs { get; set; }

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

    public class pv
    {
        private List<accessControlRule> acrField;

        public List<accessControlRule> Acr
        {
            get
            {
                if ((this.acrField == null))
                {
                    this.acrField = new List<accessControlRule>();
                }
                return this.acrField;
            }
            set
            {
                this.acrField = value;
            }
        }
    }
}
