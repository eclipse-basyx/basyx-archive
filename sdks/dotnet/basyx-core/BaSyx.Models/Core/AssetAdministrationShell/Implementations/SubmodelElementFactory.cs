/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.Common;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations
{
    public static class SubmodelElementFactory
    {
        public static SubmodelElement CreateSubmodelElement(DataObjectType modelType, DataType valueType = null)
        {
            if (modelType == ModelType.Property || modelType == DataObjectType.DateTime)
                return new Property(valueType);
            if (modelType == ModelType.Operation)
                return new Operation();
            if (modelType == ModelType.Event)
                return new Event();
            else if (modelType == ModelType.Blob)
                return new Blob();
            else if (modelType == ModelType.File)
                return new File();
            else if (modelType == ModelType.MultiLanguageProperty)
                return new MultiLanguageProperty();
            else if (modelType == ModelType.ReferenceElement)
                return new ReferenceElement();
            else if (modelType == ModelType.RelationshipElement)
                return new RelationshipElement();
            else if (modelType == ModelType.SubmodelElementCollection)
                return new SubmodelElementCollection();
            else
                return null;
        }
    }
}
