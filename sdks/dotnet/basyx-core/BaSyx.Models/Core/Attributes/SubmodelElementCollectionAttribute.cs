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
using BaSyx.Models.Core.AssetAdministrationShell.References;
using System;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;

namespace BaSyx.Models.Core.Attributes
{
    [AttributeUsage(AttributeTargets.Property, Inherited = false, AllowMultiple = false)]
    public sealed class SubmodelElementCollectionAttribute : Attribute
    {
        public SubmodelElementCollection SubmodelElementCollection { get; }
        public bool Ordered
        {
            get
            {
                return SubmodelElementCollection.Ordered;
            }
            set
            {
                SubmodelElementCollection.Ordered = value;
            }
        }

        public bool AllowDuplicates
        {
            get
            {
                return SubmodelElementCollection.AllowDuplicates;
            }
            set
            {
                SubmodelElementCollection.AllowDuplicates = value;
            }
        }


        public string Category
        {
            get
            {
                return SubmodelElementCollection.Category;
            }
            set
            {
                SubmodelElementCollection.Category = value;
            }
        }

        public SubmodelElementCollectionAttribute(string idShort)
        {
            SubmodelElementCollection = new SubmodelElementCollection(idShort);
        }

        public SubmodelElementCollectionAttribute(string idShort, string semanticId, KeyElements semanticKeyElement, KeyType semanticKeyType, bool semanticIdLocal)
        {
            SubmodelElementCollection = new SubmodelElementCollection(idShort)
            {
                SemanticId = new Reference(new Key(semanticKeyElement, semanticKeyType, semanticId, semanticIdLocal)) 
            };
        }
    }
}
