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
using Newtonsoft.Json;
using System.Runtime.Serialization;
using BaSyx.Models.Core.Common;

namespace BaSyx.Models.Core.AssetAdministrationShell.Identification
{
    public interface IReferable
    {
        [JsonProperty(Order = -2), DataMember(Order = 0, EmitDefaultValue = false, IsRequired = false, Name = "idShort")]
        string IdShort { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "category")]
        string Category { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "description")]
        LangStringSet Description { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "parent")]
        IReference Parent { get; }       
    }
}
