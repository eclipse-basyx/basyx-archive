using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Identification
{
    [DataContract]
    public enum KeyType : int
    {
        Custom = 0,
        URI = 1,
        IRDI = 2,
        IdShort = 3
    }
}
