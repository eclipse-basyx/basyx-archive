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
using BaSyx.Models.Core.AssetAdministrationShell.Semantics;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Constraints
{
    public interface IQualifier : IConstraint, IHasSemantics
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "type")]
        string Type { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        object Value { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueId")]
        IReference ValueId { get; }
    }
}
