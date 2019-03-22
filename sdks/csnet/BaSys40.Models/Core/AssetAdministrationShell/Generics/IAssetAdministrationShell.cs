using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Core.Views;
using BaSys40.Models.Export;
using BaSys40.Models.Semantics;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
   
    public interface IAssetAdministrationShell : IIdentifiable, IModelElement, IHasDataSpecification
    {
        [JsonProperty, DataMember(EmitDefaultValue = false, IsRequired = false, Name = "derivedFrom")]
        IReference<IAssetAdministrationShell> DerivedFrom { get; }

        [ExportAsReference(KeyElements.Asset), DataMember(EmitDefaultValue = false, IsRequired = false, Name = "asset")]
        IAsset Asset { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "submodels")]
        ElementContainer<ISubmodel> Submodels { get; set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "views")]
        ElementContainer<IView> Views { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "conceptDictionaries")]
        List<ConceptDictionary> ConceptDictionaries { get; }
    }
}
