using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Semantics;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IConceptDescription : IIdentifiable, IHasDataSpecification, IModelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "isCaseOf")]
        List<IReference> IsCaseOf { get; }
    }
}
