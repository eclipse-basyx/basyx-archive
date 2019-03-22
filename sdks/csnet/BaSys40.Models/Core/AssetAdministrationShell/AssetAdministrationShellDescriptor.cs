using BaSys40.Models.Connectivity;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell
{
    [DataContract]
    public class AssetAdministrationShellDescriptor : IServiceDescriptor
    {
        public Identifier Identification { get; set; }       
        public Dictionary<string, string> MetaData { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string IdShort { get; set; }
        public string Category { get; set; }
        public List<Description> Descriptions { get; set; }
        public List<IEndpoint> Endpoints { get; }
        [IgnoreDataMember]
        public IReference Parent => null;

        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "asset")]
        public IAsset Asset { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "submodels")]
        public List<SubmodelDescriptor> Submodels { get; set; }

        public AssetAdministrationShellDescriptor(params IEndpoint[] endpoints)
        {
            Endpoints = endpoints?.ToList();
        }

        [JsonConstructor]
        public AssetAdministrationShellDescriptor(List<IEndpoint> endpoints) 
        {
            Endpoints = endpoints;
        }

        public AssetAdministrationShellDescriptor(IAssetAdministrationShell aas, params IEndpoint[] endpoints) : this(endpoints)
        {
            this.Identification = aas.Identification;
            this.MetaData = aas.MetaData;
            this.Administration = aas.Administration;
            this.IdShort = aas.IdShort;
            this.Category = aas.Category;
            this.Descriptions = aas.Descriptions;
            this.Asset = aas.Asset;

            if (aas.Submodels?.Count > 0)
            {
                this.Submodels = new List<SubmodelDescriptor>();
                foreach (var submodel in aas.Submodels)
                {
                    this.Submodels.Add(new SubmodelDescriptor(submodel, endpoints));
                }
            }
        }
    }
}
