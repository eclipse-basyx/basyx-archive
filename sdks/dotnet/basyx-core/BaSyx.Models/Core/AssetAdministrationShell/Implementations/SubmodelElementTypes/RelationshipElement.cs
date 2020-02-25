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
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes
{
    public class RelationshipElement : SubmodelElement, IRelationshipElement
    {
        public override ModelType ModelType => ModelType.RelationshipElement;

        public IReference<IReferable> First { get; set; }

        public IReference<IReferable> Second { get; set; }

        public RelationshipElement() : base() { }     
    }
}
