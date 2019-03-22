using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Connectivity
{
    public interface IAddressable
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "endpoints")]
        List<IEndpoint> Endpoints { get; }
    }

    [JsonConverter(typeof(EndpointConverter))]
    public interface IEndpoint
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "address")]
        string Address { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "type")]
        string Type { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "security")]
        IEndpointSecurity Security { get; }
    }

    public interface IEndpointSecurity
    {

    }
}
