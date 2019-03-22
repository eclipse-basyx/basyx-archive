using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System;
using System.Collections.Generic;

namespace BaSys40.API.AssetAdministrationShell
{
    public delegate OperationResult MethodCalledEventHandler(IConnectableOperation operation, List<IArgument> inputArguments, List<IArgument> outputArguments);

    public delegate OperationResult MethodCalledHandler(IOperation operation, List<IArgument> inputArguments, List<IArgument> outputArguments);

    public class OperationResult : Result
    {
        public OperationResult(bool success) : this(success, null)
        {}
        public OperationResult(Exception e) : base(e)
        { }
        public OperationResult(bool success, List<IMessage> messages) : base(success, messages)
        { }
    }
}
