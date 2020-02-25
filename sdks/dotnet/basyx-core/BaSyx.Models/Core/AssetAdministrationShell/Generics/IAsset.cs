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
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using System.Runtime.Serialization;
using BaSyx.Models.Core.AssetAdministrationShell.Semantics;
using BaSyx.Models.Core.AssetAdministrationShell.Enums;
using BaSyx.Models.Core.Common;

namespace BaSyx.Models.Core.AssetAdministrationShell.Generics
{
    public interface IAsset : IIdentifiable, IModelElement, IHasDataSpecification
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "assetIdentificationModel")]
        IReference<ISubmodel> AssetIdentificationModel { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "billOfMaterial")]
        IReference<ISubmodel> BillOfMaterial { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "kind")]
        AssetKind Kind { get; }
    }
}
