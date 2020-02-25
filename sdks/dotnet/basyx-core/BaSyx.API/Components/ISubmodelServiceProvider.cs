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
using BaSyx.Utils.Client;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Utils.ResultHandling;
using System;
using BaSyx.API.AssetAdministrationShell;
using BaSyx.API.Clients;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.Common;

namespace BaSyx.API.Components
{
    public interface ISubmodelServiceProvider : IServiceProvider<ISubmodel, ISubmodelDescriptor>, ISubmodelClient
    {
        ISubmodel Submodel { get; }

        void SubscribeUpdates(string propertyId, Action<IValue> updateFunction);
        void PublishUpdate(string propertyId, IValue value);
        IResult ThrowEvent(IPublishableEvent publishableEvent, string topic, Action<IMessagePublishedEventArgs> MessagePublished, byte qosLevel, bool retain);


        PropertyHandler RetrievePropertyHandler(string propertyId);
        void RegisterPropertyHandler(string propertyId, PropertyHandler handler);
        Delegate RetrieveMethodDelegate(string operationId);
        void RegisterMethodCalledHandler(string operationId, Delegate handler);
        void RegisterEventDelegate(string eventId, EventDelegate handler);
        void ConfigureEventHandler(IMessageClient messageClient);
    }
}
