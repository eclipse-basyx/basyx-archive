using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Extensions;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes
{
    public interface IProperty : IDataElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueId")]
        IReference ValueId { get; set; }
    }

    public interface IProperty<T> : IProperty, IValue<T>
    { }
}
