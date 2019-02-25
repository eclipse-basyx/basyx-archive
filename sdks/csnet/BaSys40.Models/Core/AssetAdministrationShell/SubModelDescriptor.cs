using BaSys40.Models.Connectivity;
using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Semantics;
using BaSys40.Utils.PathHandling;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell
{
    [DataContract]
    public class SubmodelDescriptor : IServiceDescriptor, IHasSemantics, ITypeable
    {
        public Identifier Identification { get; set; }
        public Dictionary<string, string> MetaData { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string IdShort { get; set; }
        public string Category { get; set; }
        public List<Description> Descriptions { get; set; }
        public IReference SemanticId { get; set; }
        public Kind? Kind { get; set; }
        public List<IEndpoint> Endpoints { get; private set; }
        [IgnoreDataMember]
        public IReference Parent => null;

        public SubmodelDescriptor(string idShort, params IEndpoint[] endpoints)
        {
            IdShort = idShort;
            HandleEndpoints(endpoints);
        }

        [JsonConstructor]
        public SubmodelDescriptor(string idShort, List<IEndpoint> endpoints)
        {
            IdShort = idShort;
            if(endpoints != null)
                HandleEndpoints(endpoints.ToArray());
        }

        public SubmodelDescriptor(ISubmodel submodel, params IEndpoint[] endpoints)
        {
            Identification = submodel.Identification;
            MetaData = submodel.MetaData;
            Administration = submodel.Administration;
            IdShort = submodel.IdShort;
            Category = submodel.Category;
            Descriptions = submodel.Descriptions;
            SemanticId = submodel.SemanticId;
            Kind = submodel.Kind;

            HandleEndpoints(endpoints);
        }

        private void HandleEndpoints(IEndpoint[] endpoints)
        {
            if (endpoints == null)
                return; 

            var newEndpoints = new List<IEndpoint>();
            foreach (var endpoint in endpoints)
            {
                if (!endpoint.Address.Contains("submodels"))
                {
                    string address = Path.GetFormattedEndpoint(endpoint.Address, "submodels", IdShort);
                    var newEndpoint = EndpointFactory.CreateEndpoint(endpoint.Type, address, endpoint.Security);
                    newEndpoints.Add(newEndpoint);
                }
                else
                    newEndpoints.Add(endpoint);
            }
            Endpoints = newEndpoints;
        }

        
    }
}
