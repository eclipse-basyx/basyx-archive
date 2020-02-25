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
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;
using BaSyx.Utils.ResultHandling;

namespace BaSyx.API.Components
{
    public interface ISubmodelRegistry
    {
        IResult<ISubmodelDescriptor> CreateSubmodel(string aasId, ISubmodelDescriptor submodel);
        IResult<IElementContainer<ISubmodelDescriptor>> RetrieveSubmodels(string aasId);
        IResult<ISubmodelDescriptor> RetrieveSubmodel(string aasId, string submodelId);
        IResult DeleteSubmodel(string aasId, string submodelId);
    }
}
