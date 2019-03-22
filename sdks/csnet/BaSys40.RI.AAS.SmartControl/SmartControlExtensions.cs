using BaSys40.API.AssetAdministrationShell;
using BaSys40.API.AssetAdministrationShell.Connectables;
using BaSys40.API.Platform;
using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using Microsoft.Extensions.DependencyInjection;
using NLog;
using NLog.Web;
using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;

namespace BaSys40.RI.AAS.SmartControl.Extensions
{
    public static class SmartControlExtensions
    {
        private static SmartControlSettings Settings;
        private static Logger logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();
        private static IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider;
        private static SmartControlHandler smartControlHandler;

        static SmartControlExtensions()
        {
            string settingsPath = Path.Combine(Path.GetDirectoryName(Assembly.GetEntryAssembly().Location), SmartControlSettings.FileName);
            Settings = SmartControlSettings.LoadSettings(settingsPath);
        }
        public static IServiceCollection ConfigureSmartControl(this IServiceCollection services, IAssetAdministrationShellServiceProvider aasServiceProvider, SmartControlSettings settings = null)
        {
            Settings = settings ?? Settings;
            assetAdministrationShellServiceProvider = aasServiceProvider;
            smartControlHandler = new SmartControlHandler(Settings.CallbackEndpointUrl);

            var aas_Deleted = SmartControl.Instance.DeleteAssetAdministrationShell(aasServiceProvider.AssetAdministrationShell.IdShort);
            logger.Info($"AAS {aasServiceProvider.AssetAdministrationShell.IdShort} deleted - Success: " + aas_Deleted.Success);
            var aas_Created = SmartControl.Instance.CreateAssetAdministrationShell(aasServiceProvider.AssetAdministrationShell);
            logger.Info($"AAS {aasServiceProvider.AssetAdministrationShell.IdShort} created - Success: " + aas_Created.Success);

            var connectedAAS = new ConnectedAssetAdministrationShell(SmartControl.Instance);
            connectedAAS.BindTo(aasServiceProvider.AssetAdministrationShell);

            foreach (var submodel in aasServiceProvider.AssetAdministrationShell.Submodels)
            {
                var success = connectedAAS.CreateSubmodel(submodel);
                logger.Info($"Submodel {submodel.IdShort} created - Success: " + success);
                var connectedSubmodel = new ConnectedSubmodel(SmartControl.Instance, aasServiceProvider.AssetAdministrationShell);
                connectedSubmodel.BindTo(submodel);
                connectedAAS.RegisterSubmodelServiceProvider(submodel.IdShort, connectedSubmodel);

                foreach (var op in connectedSubmodel.RetrieveOperations().Entity)
                {
                    var connectedOp = new ConnectedOperation(SmartControl.Instance, aasServiceProvider.AssetAdministrationShell, submodel, op);
                    smartControlHandler.RegisterMethodCalledEventHandler(connectedSubmodel, connectedOp, GenericMethodHandler);
                }

                foreach (var de in connectedSubmodel.RetrieveDataElements().Entity)
                {
                    var connectedDataElement = new ConnectedDataElement(SmartControl.Instance, aasServiceProvider.AssetAdministrationShell, submodel, de);
                    smartControlHandler.RegisterGetPropertyValueHandler(connectedSubmodel, connectedDataElement, GenericGetValueHandler);
                    smartControlHandler.RegisterSetPropertyValueHandler(connectedSubmodel, connectedDataElement, GenericSetValueHandler);

                    assetAdministrationShellServiceProvider.GetSubmodelServiceProvider(submodel.IdShort).SubscribeUpdates(de.IdShort, value =>
                    {
                        SmartControl.Instance.UpdateDataElementValue(aasServiceProvider.AssetAdministrationShell.IdShort, submodel.IdShort, de.IdShort, value);
                    });
                }
            }

            services.AddSingleton<IAssetAdministrationShellAggregator, SmartControl>(sp => { return SmartControl.Instance; });
            services.AddSingleton<IAssetAdministrationShellServiceProvider, ConnectedAssetAdministrationShell>(sp => { return connectedAAS; });

            return services;
        }

        private static void GenericSetValueHandler(IConnectableDataElement dataElement, IValue value)
        {
            var handler = assetAdministrationShellServiceProvider.GetSubmodelServiceProvider(dataElement.Submodel.IdShort)
                .RetrieveDataElementHandler(dataElement.DataElement.IdShort);

            handler?.SetHandler?.Invoke(dataElement.DataElement, value);
        }

        private static IValue GenericGetValueHandler(IConnectableDataElement dataElement)
        {
            var handler = assetAdministrationShellServiceProvider.GetSubmodelServiceProvider(dataElement.Submodel.IdShort)
                 .RetrieveDataElementHandler(dataElement.DataElement.IdShort);

            return handler?.GetHandler?.Invoke(dataElement.DataElement);
        }

        private static OperationResult GenericMethodHandler(IConnectableOperation operation, List<IArgument> inputArguments, List<IArgument> outputArguments)
        {
            var del = assetAdministrationShellServiceProvider.GetSubmodelServiceProvider(operation.Submodel.IdShort)
                 .RetrieveMethodDelegate(operation.Operation.IdShort);

            if (del is MethodCalledHandler mch)
                return mch.Invoke(operation.Operation, inputArguments, outputArguments);
            else
            {
                if (inputArguments == null || inputArguments.Count == 0)
                    del.DynamicInvoke();
                else
                {
                    object[] args = new object[inputArguments.Count];
                    for (int i = 0; i < args.Length; i++)
                    {
                        args[i] = inputArguments[i].Value;
                    }
                    del.DynamicInvoke(args);
                }
            }

            return new OperationResult(true);
        }

        public static void Shutdown()
        {
            if(!string.IsNullOrEmpty(assetAdministrationShellServiceProvider?.AssetAdministrationShell?.IdShort))
                SmartControl.Instance.DeleteAssetAdministrationShell(assetAdministrationShellServiceProvider.AssetAdministrationShell.IdShort);

            smartControlHandler.Dispose();
        }
    }
}
