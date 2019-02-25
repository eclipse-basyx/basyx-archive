using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Identification
{
    [DataContract]
    public class AdministrativeInformation
    {
        [DataMember]
        public string Version { get; set; }
        [DataMember]
        public string Revision { get; set; }
    }
}