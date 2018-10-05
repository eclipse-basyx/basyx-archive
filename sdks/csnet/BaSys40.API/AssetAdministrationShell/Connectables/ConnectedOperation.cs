using BaSys40.API.Agents;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedOperation : IConnectableOperation
    {
        public IOperationDescription Operation { get; }
        private IAssetAdministrationShell AssetAdministrationShell { get; }
        private ISubModel SubModel { get; }

        public event MethodCalledEventHandler OnCallMethod;

        private readonly ISubModelAgent serviceImpl;

        public ConnectedOperation(ISubModelAgent service, IAssetAdministrationShell aas, ISubModel subModel, IOperationDescription operationDescription)
        {
            AssetAdministrationShell = aas;
            SubModel = subModel;
            Operation = operationDescription;

            serviceImpl = service;
        }

        public IResult InvokeLocal(List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout)
        {
            object result;
            if (OnCallMethod != null)
                result = OnCallMethod.Invoke(this, inputArguments, out outputArguments);
            else
            {
                result = null;
                outputArguments = null; 
            }
            return new Result(result != null, result, result?.GetType());  
        }

        public IResult InvokeRemote(List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout)
        {
            var result = serviceImpl.InvokeOperation(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, Operation.Identification.Id, inputArguments, out outputArguments, timeout);
            return result;
        }
    }
}
