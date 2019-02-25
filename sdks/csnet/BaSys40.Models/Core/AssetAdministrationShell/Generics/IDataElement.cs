using BaSys40.Models.Core.Extensions.References;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IDataElement : ISubmodelElement, IValue
    {
    }
    public interface IDataElement<out TValue> : IDataElement, IValue<TValue>
    { }
}
