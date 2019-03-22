using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    public class ElementContainer<T> : List<T>, IElementContainer<string, T> where T : IReferable
    {
        public Dictionary<string, string> MetaData { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string IdShort { get; set; }
        public string Category { get; set; }
        public List<Description> Descriptions { get; set; }
        public IReference Parent { get; set; }
        public ModelType ModelElementType => ModelType.DataElementCollection;

        public T this[string idShort] => this.Find(e => e.IdShort == idShort);

        public virtual IResult<T> Create(T element)
        {
            Add(element);            
            return new Result<T>(true, element);
        }
        public virtual IResult<ElementContainer<T>> RetrieveAll()
        {
            if (Count == 0)
                return new Result<ElementContainer<T>>(true, new EmptyMessage());
            else
                return new Result<ElementContainer<T>>(true, this);
        }


        public virtual IResult<T> Retrieve(string id)
        {
            var element = this[id];
            if (element != null)
                return new Result<T>(true, element);
            else
                return new Result<T>(false, new NotFoundMessage());
        }

        public virtual IResult<T> Update(string id, T element)
        {
            var index = FindIndex(e => e.IdShort == id);
            if (index != -1)
            {
                this[index] = element;
                return new Result<T>(true, element);
            }
            return new Result<T>(false, new NotFoundMessage());
        }

        public virtual IResult Delete(string id)
        {
            var index = FindIndex(e => e.IdShort == id);
            if (index != -1)
            {
                RemoveAt(index);
                return new Result(true);
            }
            return new Result(false, new NotFoundMessage());
        }
    }
}
