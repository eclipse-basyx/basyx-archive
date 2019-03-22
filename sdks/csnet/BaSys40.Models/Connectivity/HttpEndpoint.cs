using Newtonsoft.Json;
using System;
using System.Runtime.Serialization;

namespace BaSys40.Models.Connectivity
{
    public class HttpEndpoint : IEndpoint
    {
        public string Address { get; }

        [IgnoreDataMember]
        public Uri Url { get; }

        public string Type => EndpointType.HTTP;

        public IEndpointSecurity Security { get; set;}

        [JsonConstructor]
        public HttpEndpoint(string address)
        {
            address = address ?? throw new ArgumentNullException("url");
            Url = new Uri(address);
            Address = Url.ToString();
        }


    }
}
