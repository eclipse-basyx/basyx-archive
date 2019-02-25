using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System.Collections.Generic;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public class ConceptDictionary : IReferable
    {
        public string IdShort { get; set; }

        public string Category { get; set; }

        public List<Description> Descriptions { get; set; }

        public IReference Parent { get; set; }

        public Dictionary<string, string> MetaData { get; set; }

        public List<IReference<IConceptDescription>> ConceptDescriptions { get; set; }
    }
}