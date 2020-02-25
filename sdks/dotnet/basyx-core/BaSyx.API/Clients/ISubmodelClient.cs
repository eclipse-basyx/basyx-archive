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
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;

namespace BaSyx.API.Clients
{
    public interface ISubmodelClient
    {
        IResult<ISubmodel> RetrieveSubmodel();

        #region SubmodelElement - CRUD-Operations
        IResult<ISubmodelElement> CreateSubmodelElement(ISubmodelElement submodelElement);

        IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements();

        IResult<ISubmodelElement> RetrieveSubmodelElement(string submodelElementId);

        IResult<IValue> RetrieveSubmodelElementValue(string submodelElementId);

        IResult UpdateSubmodelElement(string submodelElementId, ISubmodelElement submodelElement);

        IResult DeleteSubmodelElement(string submodelElementId);
        #endregion

        #region Operation - CRUD-Operations
        IResult<IOperation> CreateOperation(IOperation operation);

        IResult<IElementContainer<IOperation>> RetrieveOperations();

        IResult<IOperation> RetrieveOperation(string operationId);

        IResult DeleteOperation(string operationId);

        IResult InvokeOperation(string operationId, IOperationVariableSet inputArguments, IOperationVariableSet outputArguments, int timeout);
        #endregion

        #region Property - CRUD-Operations
        IResult<IProperty> CreateProperty(IProperty property);

        IResult<IElementContainer<IProperty>> RetrieveProperties();

        IResult<IProperty> RetrieveProperty(string propertyId);

        IResult<IValue> RetrievePropertyValue(string propertyId);

        IResult UpdatePropertyValue(string propertyId, IValue propertyValue);

        IResult DeleteProperty(string propertyId);
        #endregion

        #region Event - CRUD-Operations
        IResult<IEvent> CreateEvent(IEvent eventable);

        IResult<IElementContainer<IEvent>> RetrieveEvents();

        IResult<IEvent> RetrieveEvent(string eventId);

        IResult DeleteEvent(string eventId);
        #endregion
    }
}
