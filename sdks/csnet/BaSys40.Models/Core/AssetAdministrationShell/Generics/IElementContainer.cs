using BaSys40.Models.Core.Identification;
using System.Collections.Generic;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IElementContainer<Identifier, Elements> : IList<Elements>
    {
        Elements this[Identifier id] { get; }
    }
}
