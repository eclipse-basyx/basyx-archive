using BaSys40.Models.Core.Identification;

namespace BaSys40.Models.Core.Extensions.References
{
    public class ModelKey : Key
    {
        public ModelKey(KeyElements type, KeyType idType, string value) : base(type, idType, value, true)
        { }
    }
}
