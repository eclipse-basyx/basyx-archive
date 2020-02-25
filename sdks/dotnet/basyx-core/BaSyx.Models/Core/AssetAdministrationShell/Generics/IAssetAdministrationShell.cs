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
using BaSyx.Models.Core.AssetAdministrationShell.Semantics;
using Newtonsoft.Json;
using System.Runtime.Serialization;
using BaSyx.Models.Core.AssetAdministrationShell.Views;
using BaSyx.Models.Core.Common;

namespace BaSyx.Models.Core.AssetAdministrationShell.Generics
{

    public interface IAssetAdministrationShell : IIdentifiable, IModelElement, IHasDataSpecification
    {
        [JsonProperty, DataMember(EmitDefaultValue = false, IsRequired = false, Name = "derivedFrom")]
        IReference<IAssetAdministrationShell> DerivedFrom { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "asset")]
        IAsset Asset { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "submodels")]
        IElementContainer<ISubmodel> Submodels { get; set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "views")]
        IElementContainer<IView> Views { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "conceptDictionaries")]
        IElementContainer<IConceptDictionary> ConceptDictionaries { get; }
    }
}
