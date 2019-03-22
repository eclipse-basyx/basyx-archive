using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes;
using BaSys40.Models.Core.Extensions.References;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes
{
    [DataContract]
    public class ReferenceElement : DataElement<IReference>, IReferenceElement<IReference>
    {
        public override ModelType ModelType => ModelType.ReferenceElement;

        public ReferenceElement() : this(null) { }
        [JsonConstructor]
        public ReferenceElement(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications) { }
    }
}
