using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Identification
{
    [DataContract]
    public class Description
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "language")]
        public string Language { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "text")]
        public string Text { get; }

        public Description(string language, string text)
        {
            Language = language;
            Text = text;
        }
    }
}