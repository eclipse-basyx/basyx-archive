using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Semantics
{
    public interface IHasDataSpecification
    {
        [IgnoreDataMember]
        IConceptDescription ConceptDescription { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "embeddedDataSpecifications")]
        List<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; }
    }
}
