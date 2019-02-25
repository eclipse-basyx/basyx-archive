using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Semantics;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    [ExportAsReference(KeyElements.Asset, JsonSchemaIgnore = true)]
    public interface IAsset : IIdentifiable, ITypeable, IHasSemantics, IModelElement, IHasDataSpecification
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "assetIdentificationModel")]
        IReference<ISubmodel> AssetIdentificationModel { get; set; }
    }
}
