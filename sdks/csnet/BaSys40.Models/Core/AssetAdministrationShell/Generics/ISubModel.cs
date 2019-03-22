using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Semantics;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    [ExportAsReference(KeyElements.Submodel, JsonSchemaIgnore = true)]
    public interface ISubmodel : IIdentifiable, ITypeable, IHasSemantics, IModelElement, IHasDataSpecification
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "dataElements")]
        ElementContainer<IDataElement> DataElements { get; set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "operations")]
        ElementContainer<IOperation> Operations { get; set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "events")]
        ElementContainer<IEvent> Events { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "submodelElements")]
        ElementContainer<ISubmodelElement> SubmodelElements { get; set; }
    }
}
