using BaSys40.Models.Core.Identification;
using BaSys40.Models.Core.Constraints;
using BaSys40.Models.Semantics;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface ISubmodelElement : IHasSemantics, IQualifiable, IReferable, ITypeable, IModelElement, IHasDataSpecification
    { }
}
