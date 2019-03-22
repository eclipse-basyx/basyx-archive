using BaSys40.Models.Connectivity;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataSpecifications
{
    [DataContract, DataSpecification("www.basys.org/DataSpecifications/EndpointSpecification")]
    public class EndpointSpecification : IEmbeddedDataSpecification
    {
        public IReference HasDataSpecification => new Reference(
        new GlobalKey(KeyElements.GlobalReference, KeyType.URI, "www.basys.org/DataSpecifications/EndpointSpecification"));
        public IDataSpecificationContent DataSpecificationContent { get; set; }
    }

    [DataContract]
    public class EndpointSpecificationContent : IDataSpecificationContent
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "endpoints")]
        public List<IEndpoint> Endpoints { get; set; }
    }
}
