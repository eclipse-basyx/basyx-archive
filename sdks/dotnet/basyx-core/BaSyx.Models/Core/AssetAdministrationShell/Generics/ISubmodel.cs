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
using BaSyx.Models.Core.AssetAdministrationShell.Constraints;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Semantics;
using BaSyx.Models.Core.Common;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Generics
{
    public interface ISubmodel : IIdentifiable, IHasKind, IHasSemantics, IModelElement, IHasDataSpecification, IQualifiable
    {
        [IgnoreDataMember]
        IEnumerable<IProperty> Properties { get; }

        [IgnoreDataMember]
        IEnumerable<IOperation> Operations { get; }

        [IgnoreDataMember]
        IEnumerable<IEvent> Events { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "submodelElements")]
        IElementContainer<ISubmodelElement> SubmodelElements { get; }
    }
}
