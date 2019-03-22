using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes
{
    [DataContract]
    public class File : DataElement<string>, IFile
    {
        public override ModelType ModelType => ModelType.File;
        public string MimeType { get; set; }
        public File() : this(null) { }
        [JsonConstructor]
        public File(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications) { }
    }
}
