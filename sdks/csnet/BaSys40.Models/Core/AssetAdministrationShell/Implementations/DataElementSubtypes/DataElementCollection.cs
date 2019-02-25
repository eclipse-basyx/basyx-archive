using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes
{
    [DataContract]
    public class DataElementCollection : DataElement<ElementContainer<IDataElement>>, IDataElementCollection
    {
        public override ModelType ModelType => ModelType.SubmodelElementCollection;
        public override ElementContainer<IDataElement> Value { get => base.Value; set => base.Value = value; }

        public DataElementCollection() : this(null) { }

        [JsonConstructor]
        public DataElementCollection(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications) { }
    }
}
