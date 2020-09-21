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
using BaSyx.Models.Core.AssetAdministrationShell.References;
using System;
using BaSyx.Models.Core.Common;

namespace BaSyx.Models.Core.Attributes
{
    [AttributeUsage(AttributeTargets.Property, Inherited = false, AllowMultiple = false)]
    public sealed class PropertyAttribute : Attribute
    {
        public Property Property { get; }

        public string Category
        {
            get
            {
                return Property.Category;
            }
            set
            {
                Property.Category = value;
            }
        }

        public PropertyAttribute(string idShort, DataObjectTypes propertyType)
        {
            Property = new Property(idShort, new DataType(DataObjectType.Parse(propertyType)));
        }

        public PropertyAttribute(string idShort, DataObjectTypes propertyType, string semanticId, KeyElements semanticKeyElement, KeyType semanticKeyType, bool semanticIdLocal)
        {
            Property = new Property(idShort, new DataType(DataObjectType.Parse(propertyType)))
            {
                SemanticId = new Reference(new Key(semanticKeyElement, semanticKeyType, semanticId, semanticIdLocal)) 
            };
        }
    }
}
