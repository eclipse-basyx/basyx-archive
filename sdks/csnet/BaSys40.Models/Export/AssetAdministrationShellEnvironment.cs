using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Export
{
    [DataContract]
    public class AssetAdministrationShellEnvironment
    {
        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "assetAdministrationShells", Order = 0)]
        public List<IAssetAdministrationShell> AssetAdministrationShells { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "submodels", Order = 1)]
        public List<ISubmodel> Submodels { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "assets", Order = 2)]
        public List<IAsset> Assets { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "conceptDescriptions", Order = 3)]
        public List<IConceptDescription> ConceptDescriptions { get; }

        [JsonConstructor]
        protected AssetAdministrationShellEnvironment()
        {
            AssetAdministrationShells = new List<IAssetAdministrationShell>();
            Submodels = new List<ISubmodel>();
            Assets = new List<IAsset>();
            ConceptDescriptions = new List<IConceptDescription>();

        }

        public AssetAdministrationShellEnvironment(params IAssetAdministrationShell[] assetAdministrationShells)
        {
            AssetAdministrationShells = new List<IAssetAdministrationShell>();
            Submodels = new List<ISubmodel>();
            Assets = new List<IAsset>();
            ConceptDescriptions = new List<IConceptDescription>();

            foreach (var aas in assetAdministrationShells)
            {
                AssetAdministrationShells.Add(aas);
                Assets.Add(aas.Asset);
                if (aas.Submodels?.Count > 0)
                {
                    Submodels.AddRange(aas.Submodels);
                    foreach (var submodel in aas.Submodels)
                        if (submodel.SubmodelElements?.Count > 0)
                            foreach (var submodelElement in submodel.SubmodelElements)
                                if (submodelElement.ConceptDescription != null)
                                {                                    
                                    ConceptDescriptions.Add(submodelElement.ConceptDescription);
                                    (submodelElement as SubmodelElement).ConceptDescription = null;
                                    (submodelElement as SubmodelElement).EmbeddedDataSpecifications = null;
                                }
                }
            }
        }
    }
}
