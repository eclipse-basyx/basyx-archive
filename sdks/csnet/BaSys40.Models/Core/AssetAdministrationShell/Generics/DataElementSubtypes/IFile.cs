using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes
{
    public interface IFile : IDataElement<string>
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "mimeType")]
        string MimeType { get; }
    }
}
