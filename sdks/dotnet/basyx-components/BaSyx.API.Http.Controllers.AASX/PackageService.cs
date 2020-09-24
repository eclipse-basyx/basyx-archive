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
using Microsoft.AspNetCore.Mvc;
using BaSyx.Utils.ResultHandling;
using BaSyx.API.Components;
using BaSyx.Models.Export;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.FileProviders;
using System.IO;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;

namespace BaSyx.API.Http.Controllers.PackageService
{
    /// <summary>
    /// The AASX Package Service
    /// </summary>
    public class PackageService : Controller
    {
        private readonly IAssetAdministrationShellServiceProvider shellServiceProvider;

#if NETCOREAPP3_1
        private readonly IWebHostEnvironment hostingEnvironment;

        /// <summary>
        /// Constructor for the AASX-Package Services Controller
        /// </summary>
        /// <param name="aasServiceProvider">The Asset Administration Shell Service Provider implementation provided by the dependency injection</param>
        /// <param name="environment">The Hosting Environment provided by the dependency injection</param>
        public PackageService(IAssetAdministrationShellServiceProvider aasServiceProvider, IWebHostEnvironment environment)
        {
            shellServiceProvider = aasServiceProvider;
            hostingEnvironment = environment;
        }
#else
        private readonly IHostingEnvironment hostingEnvironment;

        /// <summary>
        /// Constructor for the AASX-Package Services Controller
        /// </summary>
        /// <param name="aasServiceProvider">The Asset Administration Shell Service Provider implementation provided by the dependency injection</param>
        /// <param name="environment">The Hosting Environment provided by the dependency injection</param>
        public PackageService(IAssetAdministrationShellServiceProvider aasServiceProvider, IHostingEnvironment environment)
        {
            shellServiceProvider = aasServiceProvider;
            hostingEnvironment = environment;
        }
#endif

        #region REST-Interface AASX-Package

        /// <summary>
        /// Retrieves the full AASX Package
        /// </summary>
        /// <returns>AASX Package as download</returns>
        /// <response code="200">Success</response>
        /// <response code="400">Bad Request</response>       
        [HttpGet("aasx", Name = "GetAASXPackage")]
        [ProducesResponseType(typeof(Result), 400)]
        public IActionResult GetAASXPackage()
        {
            IAssetAdministrationShell aas = shellServiceProvider.GetBinding();
            string aasxFileName = aas.IdShort + ".aasx";
            string aasxFilePath = Path.Combine(hostingEnvironment.ContentRootPath, aasxFileName);
            IFileProvider fileProvider = hostingEnvironment.ContentRootFileProvider;

            using (AASX aasx = new AASX(aasxFilePath))
            {
                AssetAdministrationShellEnvironment_V2_0 env = new AssetAdministrationShellEnvironment_V2_0(aas);
                aasx.AddEnvironment(aas.Identification, env, ExportType.Xml);

                foreach (var item in fileProvider.GetDirectoryContents("aasx"))
                {
                    if (item.IsDirectory)
                    {
                        foreach (var subItem in fileProvider.GetDirectoryContents("aasx/" + item.Name))
                        {
                            if (subItem.Exists)
                                aasx.AddFileToAASX("/aasx/" + item.Name + "/" + subItem.Name, subItem.PhysicalPath);
                        }
                    }
                    else
                    {
                        if (item.Exists)
                            aasx.AddFileToAASX("/aasx/" + item.Name, item.PhysicalPath);
                    }
                }

            }
            var fileInfo = fileProvider.GetFileInfo(aasxFileName);
            var fileResult = new PhysicalFileResult(fileInfo.PhysicalPath, "application/asset-administration-shell-package")
            {
                FileDownloadName = aasxFileName
            };
            return fileResult;           
        }

        #endregion

        #region Helper Methods


        #endregion
    }
}