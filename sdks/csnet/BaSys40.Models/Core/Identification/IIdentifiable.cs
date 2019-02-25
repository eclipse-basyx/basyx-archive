using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Identification
{
    public interface IIdentifiable : IReferable
    {
        [JsonProperty(Order = -1), DataMember(Order = 1, EmitDefaultValue = false, IsRequired = false, Name = "identification")]
        Identifier Identification { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "administration")]
        AdministrativeInformation Administration { get; }
    }
}
