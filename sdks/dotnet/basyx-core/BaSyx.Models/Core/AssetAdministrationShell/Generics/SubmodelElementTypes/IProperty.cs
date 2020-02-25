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
using BaSyx.Models.Core.Common;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes
{
    public delegate IValue GetPropertyValueHandler(IProperty property);
    public delegate void SetPropertyValueHandler(IProperty property, IValue value);

    public delegate TValue GetPropertyValueHandler<TValue>(IProperty property);
    public delegate void SetPropertyValueHandler<TValue>(IProperty property, TValue value);

    public interface IProperty : ISubmodelElement, IValue
    {
        [IgnoreDataMember]
        GetPropertyValueHandler Get { get; }
        [IgnoreDataMember]
        SetPropertyValueHandler Set { get; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueId")]
        IReference ValueId { get; set; }
    }

    public interface IProperty<TValue> : IProperty, IValue<TValue>
    {
        [IgnoreDataMember]
        new GetPropertyValueHandler<TValue> Get { get; }
        [IgnoreDataMember]
        new SetPropertyValueHandler<TValue> Set { get; }
    }
}
