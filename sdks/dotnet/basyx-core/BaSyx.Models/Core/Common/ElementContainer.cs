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
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Extensions;
using BaSyx.Utils.ResultHandling;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;

namespace BaSyx.Models.Core.Common
{
    public class ElementContainer<TElement> : List<TElement>, IElementContainer<TElement> where TElement : IReferable, IModelElement
    {
        public TElement this[string idShort] => this.Find(e => e.IdShort == idShort);

        public ElementContainer()
        { }

        public ElementContainer(IEnumerable<TElement> list) : base(list) { }

        public virtual IResult<TElement> Create(TElement element)
        {
            if (element == null)
                return new Result<TElement>(new ArgumentNullException(nameof(element)));

            if (this[element.IdShort] == null)
            {                
                Add(element);
                return new Result<TElement>(true, element);
            }
            else
                return new Result<TElement>(false, new ConflictMessage(element.IdShort));
        }
        public IResult<T> Create<T>(T element) where T : class, TElement
        {
            if (element == null)
                return new Result<T>(new ArgumentNullException(nameof(element)));

            if (this[element.IdShort] == null)
            {
                Add(element);
                return new Result<T>(true, element);
            }
            else
                return new Result<T>(false, new ConflictMessage(element.IdShort));
        }


        public virtual IResult<IElementContainer<TElement>> RetrieveAll()
        {
            if (Count == 0)
                return new Result<ElementContainer<TElement>>(true, new EmptyMessage());
            else
                return new Result<ElementContainer<TElement>>(true, this);
        }

        public virtual IResult<IElementContainer<T>> RetrieveAll<T>() where T : class, TElement
        {
            if (Count == 0)
                return new Result<IElementContainer<T>>(true, new EmptyMessage());
            else
            {
                ElementContainer<T> container = new ElementContainer<T>();
                foreach (var element in this)
                {
                    T tElement = element.ToModelElement<T>();
                    if (tElement != null)
                        container.Add(tElement);
                }

                if(container.Count > 0)
                    return new Result<IElementContainer<T>>(true, container);
                else
                    return new Result<IElementContainer<T>>(true, new EmptyMessage());
            }
        }


        public virtual IResult<TElement> Retrieve(string id)
        {
            if (string.IsNullOrEmpty(id))
                return new Result<TElement>(new ArgumentNullException(nameof(id)));

            var element = this[id];
            if (element != null)
                return new Result<TElement>(true, element);
            else
                return new Result<TElement>(false, new NotFoundMessage());
        }
        public IResult<T> Retrieve<T>(string id) where T : class, TElement
        {
            if (string.IsNullOrEmpty(id))
                return new Result<T>(new ArgumentNullException(nameof(id)));

            T element = this[id].ToModelElement<T>();
            if (element != null)
                return new Result<T>(true, element);
            else
                return new Result<T>(false, new NotFoundMessage());
        }

        public virtual IResult<TElement> CreateOrUpdate(string id, TElement element)
        {
            if (string.IsNullOrEmpty(id))
                return new Result<TElement>(new ArgumentNullException(nameof(id)));
            if (element == null)
                return new Result<TElement>(new ArgumentNullException(nameof(element)));

            var index = FindIndex(e => e.IdShort == id);
            if (index != -1)
            {
                this[index] = element;
                return new Result<TElement>(true, element);
            }
            else
                return Create(element);
        }


        public virtual IResult<TElement> Update(string id, TElement element)
        {
            if (string.IsNullOrEmpty(id))
                return new Result<TElement>(new ArgumentNullException(nameof(id)));
            if (element == null)
                return new Result<TElement>(new ArgumentNullException(nameof(element)));

            var index = FindIndex(e => e.IdShort == id);
            if (index != -1)
            {
                this[index] = element;
                return new Result<TElement>(true, element);
            }
            return new Result<TElement>(false, new NotFoundMessage());
        }

        public virtual IResult Delete(string id)
        {
            if (string.IsNullOrEmpty(id))
                return new Result<TElement>(new ArgumentNullException(nameof(id)));

            var index = FindIndex(e => e.IdShort == id);
            if (index != -1)
            {
                RemoveAt(index);
                return new Result(true);
            }
            return new Result(false, new NotFoundMessage());
        }

        public IElementContainer<TModelElementType> Filter<TModelElementType>(ModelType modelType) 
            where TModelElementType : IReferable, IModelElement, TElement
        {
            List<TModelElementType> modelElements = FindAll(p => p.ModelType == modelType)?.ConvertAll(c => (TModelElementType)c);
            if (modelElements != null)
                return new ElementContainer<TModelElementType>(modelElements);
            else
                return new ElementContainer<TModelElementType>();
        }

        public IEnumerable<TModelElementType> FilterAsReference<TModelElementType>(ModelType modelType)
            where TModelElementType : IReferable, IModelElement, TElement
        {
            return this.Where(w => w.ModelType == modelType).Cast<TModelElementType>();              
        }

        IElementContainer<TOutput> IElementContainer<TElement>.ConvertAll<TOutput>(Converter<TElement, TOutput> converter)
        {
            return new ElementContainer<TOutput>(this.ConvertAll(converter));
        }

       
    }

    public class PropertyContainer : ElementContainer<IProperty>
    {
        private readonly IElementContainer<ISubmodelElement> submodelElements;
        public PropertyContainer(IElementContainer<ISubmodelElement> submodelElementContainer)
        {
            submodelElements = submodelElementContainer;
        }

        public override IResult<IProperty> Create(IProperty element)
        {
            return submodelElements.Create(element);
        }
    }
    [JsonConverter(typeof(OperationVariableSetConverter))]
    public interface IOperationVariableSet : IList<IOperationVariable>
    {
        void Add(ISubmodelElement submodelElement);
        ISubmodelElement Get(string idShort);
        IElementContainer<ISubmodelElement> ToElementContainer();
        ISubmodelElement this[string idShort] { get; }
    }

    public class OperationVariableSet : List<IOperationVariable>, IOperationVariableSet
    {
        public OperationVariableSet()
        { }

        public OperationVariableSet(IEnumerable<IOperationVariable> list) : base(list) { }

        public ISubmodelElement this[string idShort] => this.Find(e => e.Value.IdShort == idShort)?.Value;

        public void Add(ISubmodelElement submodelElement)
        {
            base.Add(new OperationVariable() { Value = submodelElement });
        }

        public ISubmodelElement Get(string idShort)
        {
            return this[idShort];
        }

        public IElementContainer<ISubmodelElement> ToElementContainer()
        {
            return new ElementContainer<ISubmodelElement>(this.Cast<IOperationVariable>().Select(s => s.Value));
        }
    }
}
