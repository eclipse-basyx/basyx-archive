using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class Event : SubmodelElement, IEvent
    {
        public DataType DataType { get; set; }

        public override ModelType ModelType => ModelType.Event;

        public Event() : base()
        { }
        [JsonConstructor]
        public Event(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications) 
            : base(conceptDescription, embeddedDataSpecifications)
        { }
    }
}