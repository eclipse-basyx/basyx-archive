using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell
{
    public delegate IValue GetDataElementValueEventHandler(IConnectableDataElement dataElement);
    public delegate void SetDataElementValueEventHandler(IConnectableDataElement dataElement, IValue value);

    public delegate IValue GetDataElementValueHandler(IDataElement dataElement);
    public delegate void SetDataElementValueHandler(IDataElement dataElement, IValue value);

    public class DataElementHandler
    {
        public string IdShort { get; }
        public GetDataElementValueHandler GetHandler { get; }
        public SetDataElementValueHandler SetHandler { get; private set; }
        public DataElementHandler(string idShort, GetDataElementValueHandler getHandler, SetDataElementValueHandler setHandler)
        {
            IdShort = idShort;
            GetHandler = getHandler;
            SetHandler = setHandler;
        }
    }

}
