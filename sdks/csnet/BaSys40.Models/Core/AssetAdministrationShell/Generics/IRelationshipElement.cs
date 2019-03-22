using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IRelationshipElement : ISubmodelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "first")]
        Reference<IReferable> First { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "second")]
        Reference<IReferable> Second { get; }
    }
}
