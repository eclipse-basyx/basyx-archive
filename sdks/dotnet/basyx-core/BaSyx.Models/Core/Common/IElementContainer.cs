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
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Extensions;
using BaSyx.Utils.ResultHandling;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;

namespace BaSyx.Models.Core.Common
{
    public interface IGenericElementContainer<TIdentifier, TElement> : IList<TElement>
    {
        TElement this[TIdentifier id] { get; }

        IResult<TElement> Create(TElement element);

        IResult<T> Create<T>(T element) where T : class, TElement;

        IResult<TElement> Retrieve(TIdentifier id);

        IResult<T> Retrieve<T>(TIdentifier id) where T : class, TElement;

        IResult<IQueryableElementContainer<T>> RetrieveAll<T>() where T : class, TElement;

        IResult<IQueryableElementContainer<T>> RetrieveAll<T>(Predicate<T> predicate) where T : class, TElement;

        IResult<TElement> CreateOrUpdate(TIdentifier id, TElement element);

        IResult<TElement> Update(TIdentifier id, TElement element);

        IResult Delete(TIdentifier id);
        
        void AddRange(IEnumerable<TElement> collection);
    }

    public interface IQueryableElementContainer<TElement> : IElementContainer<TElement>, IQueryable<TElement>
    {

    }

    [JsonConverter(typeof(ElementContainerConverter))]
    public interface IElementContainer<TElement> : IGenericElementContainer<string, TElement>
    {
        IResult<IQueryableElementContainer<TElement>> RetrieveAll();
        IResult<IQueryableElementContainer<TElement>> RetrieveAll(Predicate<TElement> predicate);

        IElementContainer<TOutput> ConvertAll<TOutput>(Converter<TElement, TOutput> converter) where TOutput : IReferable, IModelElement;
        IElementContainer<TModelElementType> Filter<TModelElementType>(ModelType modelType) where TModelElementType : IReferable, IModelElement, TElement;
        IEnumerable<TModelElementType> FilterAsReference<TModelElementType>(ModelType modelType) where TModelElementType : IReferable, IModelElement, TElement;
    }
}
