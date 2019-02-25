using BaSys40.Models.Core.Extensions.References;
using System;

namespace BaSys40.Models.Core.Attributes
{
    [AttributeUsage(AttributeTargets.Property | AttributeTargets.Class | AttributeTargets.Interface, Inherited = true, AllowMultiple = true)]
    public sealed class ExportAsReferenceAttribute : Attribute
    {
        readonly KeyElements keyElement;
        public ExportAsReferenceAttribute(KeyElements keyElement)
        {
            this.keyElement = keyElement;
        }

        public KeyElements KeyElement
        {
            get { return keyElement; }
        }

        public bool JsonSchemaIgnore { get; set; } = false;
    }
}
