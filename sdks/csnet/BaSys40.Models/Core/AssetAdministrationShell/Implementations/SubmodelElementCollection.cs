using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class SubmodelElementCollection : DataElement<ElementContainer<ISubmodelElement>>
    {
        public override ModelType ModelType => ModelType.SubmodelElementCollection;
        public override ElementContainer<ISubmodelElement> Value { get => base.Value; set => base.Value = value; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "allowDuplicates")]
        public bool AllowDuplicates { get; set; } = false;

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "ordered")]
        public bool Ordered { get; set; } = false;

        public SubmodelElementCollection() : this(null) { }

        [JsonConstructor]
        public SubmodelElementCollection(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications)
        { }

    }
}
