namespace oneM2MClient.Protocols
{
    using System.Collections.Generic;
    public partial class primitiveContent
    {

        private List<object> itemsField;

        private List<ItemsChoiceType> itemsElementNameField;

        [System.Xml.Serialization.XmlAnyElementAttribute(Order = 0)]
        [System.Xml.Serialization.XmlAnyElementAttribute(Namespace = "http://www.onem2m.org/xml/protocols", Order = 0)]
        [System.Xml.Serialization.XmlChoiceIdentifierAttribute("ItemsElementName")]
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
        [System.Xml.Serialization.XmlIgnore()]
        public List<ItemsChoiceType> ItemsElementName
        {
            get
            {
                if ((this.itemsElementNameField == null))
                {
                    this.itemsElementNameField = new List<ItemsChoiceType>();
                }
                return this.itemsElementNameField;
            }
            set
            {
                this.itemsElementNameField = value;
            }
        }
    }
}
