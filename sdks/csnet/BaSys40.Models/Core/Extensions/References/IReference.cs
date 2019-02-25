using BaSys40.Models.Core.Identification;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Extensions.References
{
    public interface IReference
    {
        [IgnoreDataMember]
        IKey First { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "keys")]
        List<IKey> Keys { get;}
    }

    public interface IReference<out T> : IReference where T : IReferable
    { }
}
