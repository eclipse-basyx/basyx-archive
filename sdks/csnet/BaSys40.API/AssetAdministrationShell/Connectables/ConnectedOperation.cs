using BaSys40.API.Platform.Agents;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedOperation : IConnectableOperation
    {
        public IOperation Operation { get; }
        public IAssetAdministrationShell AssetAdministrationShell { get; }
        public ISubmodel Submodel { get; }

        public event MethodCalledEventHandler OnCallMethod;

        private readonly ISubmodelAgent serviceImpl;

        public ConnectedOperation(ISubmodelAgent service, IAssetAdministrationShell aas, ISubmodel submodel, IOperation operation)
        {
            AssetAdministrationShell = aas;
            Submodel = submodel;
            Operation = operation;

            serviceImpl = service;
        }

        public IResult InvokeLocal(List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout)
        {
            object result;
            if (OnCallMethod != null)
                result = OnCallMethod.Invoke(this, inputArguments, outputArguments);
            else
            {
                result = null;
                outputArguments = null; 
            }
            return new Result(result != null, result, result?.GetType());  
        }

        public IResult InvokeRemote(List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout)
        {
            var result = serviceImpl.InvokeOperation(AssetAdministrationShell.IdShort, Submodel.IdShort, Operation.IdShort, inputArguments, outputArguments, timeout);
            return result;
        }
    }
}
