using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataSpecifications
{
    [DataContract, DataSpecification("www.admin-shell.io/DataSpecificationTemplates/DataSpecificationIEC61360")]
    public class DataSpecification_IEC61360 : IEmbeddedDataSpecification
    {
        public IReference HasDataSpecification => new Reference(
            new GlobalKey(KeyElements.GlobalReference, KeyType.URI, "www.admin-shell.io/DataSpecificationTemplates/DataSpecificationIEC61360"));
        public IDataSpecificationContent DataSpecificationContent { get; set; }
    }

    [DataContract]
    public class DataSpecification_IEC61360_Content : IDataSpecificationContent
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name ="dataType")]
        public string DataType { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "Definition")]
        public LangString Definition { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "preferredName")]
        public LangString PreferredName { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "shortName")]
        public string ShortName { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "sourceOfDefinition")]
        public LangString SourceOfDefinition { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "symbol")]
        public string Symbol { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "code")]
        public Code Code { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "unit")]
        public string Unit { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "unitId")]
        public IReference UnitId { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueFormat")]
        public string ValueFormat { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueList")]
        public List<object> ValueList { get; set; }
    }

    public enum Code
    {

    }

    public class LangString
    {
        public string Language { get; set; }
        public string Text { get; set; }
        public LangString(string language, string text)
        {
            Language = language;
            Text = text;
        }
    }
}
