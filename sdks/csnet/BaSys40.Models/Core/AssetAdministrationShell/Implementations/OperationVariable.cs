using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class OperationVariable : SubmodelElement, IOperationVariable
    {
        public int? Index { get; set; }
        public virtual DataType DataType { get; set; }
        public override ModelType ModelType => ModelType.OperationVariable;
        public new Kind? Kind => Enums.Kind.Type;

        public OperationVariable() : base()
        { }
        public OperationVariable(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications)
        { }
    }

    [DataContract]
    public class OperationVariable<TInnerType> : OperationVariable
    {
        public override DataType DataType => DataType.GetDataTypeFromSystemTypes(typeof(TInnerType));
    }
}
