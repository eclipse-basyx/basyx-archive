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
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using System;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;

namespace BaSyx.Models.Core.Attributes
{
    [AttributeUsage(AttributeTargets.All, Inherited = true, AllowMultiple = false)]
    public sealed class SubmodelElementAttribute : Attribute
    {
        public SubmodelElement SubmodelElement { get; }

        public string Category
        {
            get
            {
                return SubmodelElement.Category;
            }
            set
            {
                SubmodelElement.Category = value;
            }
        }

        public SubmodelElementAttribute(string idShort, ModelTypes modelType)
        {
            //ModelType modelType = new ModelType()
            //SubmodelElement = SubmodelElementFactory.CreateSubmodelElement(idShort, modelType)
                
            //    //new Property(idShort, new DataType(DataObjectType.Parse(propertyType)));
        }

        public SubmodelElementAttribute(string idShort, DataObjectTypes propertyType, string semanticId, KeyElements semanticKeyElement, KeyType semanticKeyType, bool semanticIdLocal)
        {
            SubmodelElement = new Property(idShort, new DataType(DataObjectType.Parse(propertyType)))
            {
                SemanticId = new Reference(new Key(semanticKeyElement, semanticKeyType, semanticId, semanticIdLocal)) 
            };
        }
    }
}
