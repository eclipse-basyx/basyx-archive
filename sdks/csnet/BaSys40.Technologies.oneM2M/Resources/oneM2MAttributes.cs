using System;

namespace oneM2MClient.Resources
{
    [AttributeUsage(AttributeTargets.Field | AttributeTargets.Property, AllowMultiple = false, Inherited = true)]
    public class AttributeAttribute : Attribute
    {
        public RequestOptionality CreateOptionality { get; set; }
        public RequestOptionality UpdateOptionality { get; set; }
        public string LongName { get; set; }
        public string ShortName { get; set; }
        public string DataType { get; set; }

        public string DefaultValue { get; set; }

        public enum RequestOptionality
        {
            Optional,
            Mandatory,
            NotProvided
        }

    }

    [AttributeUsage(AttributeTargets.Class, AllowMultiple = false, Inherited = true)]
    public class ChildResourcesAttribute : Attribute
    {
        public Type ChildResourceType { get; set; }
        public string ChildResourceName { get; set; }
        public MultiplicityType Multiplicity { get; set; }

        public enum MultiplicityType
        {
            ZeroToOne,
            ZeroToN,
            One            
        }
    }
}
