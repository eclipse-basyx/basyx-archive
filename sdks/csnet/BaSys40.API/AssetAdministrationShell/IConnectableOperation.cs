using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.AssetAdministrationShell
{
    public interface IConnectableOperation
    {
        IOperationDescription Operation { get; }
        event MethodCalledEventHandler OnCallMethod;

        IResult InvokeLocal(List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout);
        IResult InvokeRemote(List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout);
    }
}
