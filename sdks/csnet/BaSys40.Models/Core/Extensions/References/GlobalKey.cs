using BaSys40.Models.Core.Identification;

namespace BaSys40.Models.Core.Extensions.References
{
    public class GlobalKey : Key
    {
        public GlobalKey(KeyElements type, KeyType idType, string value) : base(type, idType, value, false)
        { }
    }
}
