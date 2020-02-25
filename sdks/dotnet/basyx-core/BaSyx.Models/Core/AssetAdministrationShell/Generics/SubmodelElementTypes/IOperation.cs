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
using BaSyx.Utils.ResultHandling.ResultTypes;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes
{
    public delegate OperationResult MethodCalledHandler(IOperation operation, IOperationVariableSet inputArguments, IOperationVariableSet outputArguments);

    public interface IOperation : ISubmodelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "inputVariables")]
        IOperationVariableSet InputVariables { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "outputVariables")]
        IOperationVariableSet OutputVariables { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "inoutputVariables")]
        IOperationVariableSet InOutputVariables { get; set; }


        [IgnoreDataMember]       
        MethodCalledHandler OnMethodCalled { get; }
    }   
}
