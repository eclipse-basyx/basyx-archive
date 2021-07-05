/*******************************************************************************
* Copyright (c) 2020, 2021 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.Common;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Generics
{
    /// <summary>
    /// A property is a data element that has a single value. 
    /// </summary>
    public interface IProperty : ISubmodelElement, IValue
    {
        /// <summary>
        /// Reference to the global unqiue id of a coded value.  
        /// </summary>
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueId")]
        IReference ValueId { get; set; }
    }

    ///<inheritdoc cref="IProperty"/>
    public interface IProperty<TValue> : IProperty, IValue<TValue>
    {
        [IgnoreDataMember]
        new GetValueHandler<TValue> Get { get; }
        [IgnoreDataMember]
        new SetValueHandler<TValue> Set { get; }
    }
}
