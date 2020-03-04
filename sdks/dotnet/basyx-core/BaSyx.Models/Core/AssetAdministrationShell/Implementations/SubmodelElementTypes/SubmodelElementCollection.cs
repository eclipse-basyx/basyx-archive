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
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.Common;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes
{
    [DataContract]
    public class SubmodelElementCollection : SubmodelElement, ISubmodelElementCollection
    {
        public override ModelType ModelType => ModelType.SubmodelElementCollection;
        public IElementContainer<ISubmodelElement> Value { get; set; }
        public bool AllowDuplicates { get; set; } = false;
        public bool Ordered { get; set; } = false;

        public SubmodelElementCollection() : base() 
        {
            Value = new ElementContainer<ISubmodelElement>();
        }    
    }
}
