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
using BaSyx.Models.Core.Common;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes
{
    public interface ISubmodelElementCollection : ISubmodelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "allowDuplicates")]
        bool AllowDuplicates { get; set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "ordered")]
        bool Ordered { get; set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        IElementContainer<ISubmodelElement> Value { get; set; }
    }
}
