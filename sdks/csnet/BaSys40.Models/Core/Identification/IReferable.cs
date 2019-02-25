using BaSys40.Models.Core.Extensions.References;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Identification
{
    public interface IReferable
    {
        [JsonProperty(Order = -2), DataMember(Order = 0, EmitDefaultValue = false, IsRequired = false, Name = "idShort")]
        string IdShort { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "category")]
        string Category { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "descriptions")]
        List<Description> Descriptions { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "parent")]
        IReference Parent { get; }
        [IgnoreDataMember]
        Dictionary<string, string> MetaData { get; }
       
        //Reference GetReference(IdentifierType identifiertType); ToDo
    }
}
