using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using System.Collections.Generic;
using System.Runtime.Serialization;
using Newtonsoft.Json;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class Operation : SubmodelElement, IOperation
    {
        public List<IOperationVariable> In { get; set; }
        public List<IOperationVariable> Out { get; set; }

        public override ModelType ModelType => ModelType.Operation;

        public Operation() : base()
        { }

        [JsonConstructor]
        public Operation(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications)
        { }
    }
}
