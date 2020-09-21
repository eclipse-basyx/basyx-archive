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
using BaSyx.Utils.ResultHandling;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Communication;

namespace BaSyx.API.Clients
{
    public interface ISubmodelClient
    {
        IResult<ISubmodel> RetrieveSubmodel();

        IResult<ISubmodelElement> CreateSubmodelElement(string rootSubmodelElementPath, ISubmodelElement submodelElement);

        IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements();

        IResult<ISubmodelElement> RetrieveSubmodelElement(string pathToSubmodelElement);

        IResult<IValue> RetrieveSubmodelElementValue(string pathToSubmodelElement);

        IResult UpdateSubmodelElementValue(string pathToSubmodelElement, IValue value);

        IResult UpdateSubmodelElement(string pathToSubmodelElement, ISubmodelElement submodelElement);

        IResult DeleteSubmodelElement(string pathToSubmodelElement);

        /// <summary>
        /// Invokes a specific Operation synchronously
        /// </summary>
        /// <param name="pathToOperation">IdShort-Path to the Operation</param>
        /// <param name="invocationRequest">Request-Parameters for the invocation</param>
        /// <returns></returns>
        IResult<InvocationResponse> InvokeOperation(string pathToOperation, InvocationRequest invocationRequest);

        /// <summary>
        /// Invokes a specific Operation asynchronously
        /// </summary>
        /// <param name="pathToOperation">IdShort-Path to the Operation</param>
        /// <param name="invocationRequest">Request-Parameters for the invocation</param>
        /// <returns></returns>
        IResult<CallbackResponse> InvokeOperationAsync(string pathToOperation, InvocationRequest invocationRequest);

        /// <summary>
        /// Returns the Invocation Result of specific Operation
        /// </summary>
        /// <param name="pathToOperation">IdShort-Path to the Operation</param>
        /// <param name="requestId">Request-Id</param>
        /// <returns></returns>
        IResult<InvocationResponse> GetInvocationResult(string pathToOperation, string requestId);
    }
}
