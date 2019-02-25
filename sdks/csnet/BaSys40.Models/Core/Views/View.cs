using System.Collections.Generic;
using System.Runtime.Serialization;
using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;

namespace BaSys40.Models.Core.Views
{
    [DataContract]
    public class View : IView
    {
        public List<IReference> ContainedElements { get; set; }

        public IReference SemanticId { get; set; }

        public string IdShort { get; set; }

        public string Category { get; set; }

        public List<Description> Descriptions { get; set; }

        public IReference Parent { get; set; }
        public Dictionary<string, string> MetaData { get; set; }

        public ModelType ModelType => ModelType.View;
    }
}
