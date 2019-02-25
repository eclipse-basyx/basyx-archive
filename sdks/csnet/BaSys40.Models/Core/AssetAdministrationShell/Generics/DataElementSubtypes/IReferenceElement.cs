using BaSys40.Models.Core.Extensions.References;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes
{
    public interface IReferenceElement<out T> : IDataElement<T> where T : IReference
    { }
}

