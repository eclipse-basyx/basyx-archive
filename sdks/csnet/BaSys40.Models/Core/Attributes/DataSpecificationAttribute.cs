using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System;

namespace BaSys40.Models.Core.Attributes
{
    [AttributeUsage(AttributeTargets.Class, Inherited = false, AllowMultiple = true)]
    sealed class DataSpecificationAttribute : Attribute
    {
        readonly IReference reference;

        public DataSpecificationAttribute(string dataSpecificationReference)
        {
            reference = new Reference(
                new GlobalKey(KeyElements.GlobalReference, KeyType.URI, dataSpecificationReference));
        }

        public IReference Reference
        {
            get { return reference; }
        }
    }
}
