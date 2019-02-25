using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes;
using System.Collections.Generic;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    public static class DataElementFactory
    {
        public static IDataElement CreateDataElement(IConceptDescription conceptDescription, List<IEmbeddedDataSpecification> embeddedDataSpecifications, DataObjectType modelType, DataType valueType = null)
        {
            if (modelType == ModelType.Property || modelType == DataObjectType.DateTime)
                return new Property(valueType, conceptDescription, embeddedDataSpecifications.ToArray());
            else if (modelType == ModelType.Blob)
                return new Blob(conceptDescription, embeddedDataSpecifications.ToArray());
            else if (modelType == ModelType.File)
                return new File(conceptDescription, embeddedDataSpecifications.ToArray());
            else if (modelType == ModelType.ReferenceElement)
                return new ReferenceElement(conceptDescription, embeddedDataSpecifications.ToArray());
            else if (modelType == ModelType.DataElementCollection)
                return new DataElementCollection(conceptDescription, embeddedDataSpecifications.ToArray());
            else
                return null;
        }
    }
}
