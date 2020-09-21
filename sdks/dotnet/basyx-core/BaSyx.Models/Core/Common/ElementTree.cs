using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Utils.ModelHandling;

namespace BaSyx.Models.Core.Common
{
    public class ElementTree : TreeBuilder<IReferable>
    {
        public ElementTree(IReferable referable) : base(referable.IdShort, referable)
        { }
    }
}
