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
        public PackageService(IAssetAdministrationShellServiceProvider aasServiceProvider, IWebHostEnvironment environment)
        {
            shellServiceProvider = aasServiceProvider;
            hostingEnvironment = environment;
        }
#else
        private readonly IHostingEnvironment hostingEnvironment;
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
            string filename = shellServiceProvider.AssetAdministrationShell.IdShort + ".aasx";
            using (AASX aasx = new AASX(filename))
            {
                AssetAdministrationShellEnvironment_V2_0 env = new AssetAdministrationShellEnvironment_V2_0(shellServiceProvider.AssetAdministrationShell);
                aasx.AddEnvironment(shellServiceProvider.AssetAdministrationShell.Identification, env, ExportType.Xml);
      
                if (aasx != null)
                {
                    IFileProvider fileProvider = hostingEnvironment.ContentRootFileProvider;
                    foreach (var item in fileProvider.GetDirectoryContents("Content/aasx"))
                    {
                        if (item.IsDirectory)
                        {
                            foreach(var subItem in fileProvider.GetDirectoryContents("Content/aasx/" + item.Name))
                            {
                                if(subItem.Exists)
                                    aasx.AddFileToAASX("/aasx/" + item.Name + "/" + subItem.Name, subItem.PhysicalPath);
                            }
                        }
                        else
                        {
                            if(item.Exists)
                                aasx.AddFileToAASX("/aasx/" + item.Name, item.PhysicalPath);
                        }
                    }

                    var fileInfo = fileProvider.GetFileInfo(filename);
                    var fileResult = new PhysicalFileResult(fileInfo.PhysicalPath, "application/asset-administration-shell-package")
                    {
                        FileDownloadName = filename
                    };
                    return fileResult;
                }
            }
            return new BadRequestResult();
        }

#endregion



#region Helper Methods


#endregion
    }
}