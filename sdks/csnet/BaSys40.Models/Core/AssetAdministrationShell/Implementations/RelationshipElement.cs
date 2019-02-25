using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    public class RelationshipElement : SubmodelElement, IRelationshipElement
    {
        public override ModelType ModelType => ModelType.RelationshipElement;

        public Reference<IReferable> First { get; set; }

        public Reference<IReferable> Second { get; set; }
    }
}
