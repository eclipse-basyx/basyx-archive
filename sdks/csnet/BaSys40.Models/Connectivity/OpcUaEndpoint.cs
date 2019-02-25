using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Text;

namespace BaSys40.Models.Connectivity
{
    public class OpcUaEndpoint : IEndpoint
    {
        public string Address { get; }

        [IgnoreDataMember]
        public string BrowsePath { get; }
        [IgnoreDataMember]
        public string Authority { get; }
        public string Type => EndpointType.OPC_UA;

        public IEndpointSecurity Security { get; set; }

        [JsonConstructor]
        public OpcUaEndpoint(string address)
        {
            Address = address ?? throw new ArgumentNullException("address");
            var uri = new Uri(address);
            BrowsePath = uri.AbsolutePath;
            Authority = uri.Authority;
        }

    }
}
