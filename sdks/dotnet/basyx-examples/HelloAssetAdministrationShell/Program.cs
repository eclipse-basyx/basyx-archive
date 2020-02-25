/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License 1.0 which is available at
* https://www.eclipse.org/org/documents/edl-v10.html
*
* 
*******************************************************************************/
using BaSyx.AAS.Server.Http;
using NLog;

namespace HelloAssetAdministrationShell
{
    class Program
    {
        //Enable logging
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        static void Main(string[] args)
        {
            logger.Info("Starting component Http-Rest interface...");

            //Initialize generic Http-REST interface
            AssetAdministrationShellHttpServer loader = new AssetAdministrationShellHttpServer();

            //Instantiate Asset Administration Shell Service
            HelloAssetAdministrationShellService shellService = new HelloAssetAdministrationShellService();

            //Assign Shell Service to generic Http-Rest interface
            loader.SetServiceProvider(shellService);
            
            //Run Http-Rest interface with specific Shell Service implementation
            loader.Run();
        }
    }
}
