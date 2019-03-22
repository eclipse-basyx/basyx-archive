using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes
{
    [DataContract]
    public class Blob : DataElement<byte[]>, IBlob
    {
        public override ModelType ModelType => ModelType.Blob;
        public string MimeType { get; set; }
        public Blob() : this(null) { }
        [JsonConstructor]
        public Blob(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications) { }
    }
}
