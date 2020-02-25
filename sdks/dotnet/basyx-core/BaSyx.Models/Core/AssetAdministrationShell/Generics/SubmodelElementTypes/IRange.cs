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
    public interface IRange : ISubmodelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "min")]
        IValue Min { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "max")]
        IValue Max { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueType")]
        DataType ValueType { get; set; }
    }
}
