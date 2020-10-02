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
using Microsoft.Extensions.DependencyInjection;
using Microsoft.OpenApi.Models;
using System;
using System.IO;
using System.Reflection;
using Microsoft.AspNetCore.Builder;
using BaSyx.Utils.AssemblyHandling;
using BaSyx.Components.Common;
using Swashbuckle.AspNetCore.SwaggerGen;
using System.Linq;

namespace BaSyx.Common.UI.Swagger
{
    public static class OpenApiInfos
    {
        #region Static OpenApi-Infos
        internal static readonly OpenApiInfo AssetAdministrationShell_OpenApiInfo = new OpenApiInfo
        {
            Version = "v1",
            Title = "BaSyx Asset Administration Shell HTTP REST-API",
            Description = "The full description of the generic BaSyx Asset Administration Shell HTTP REST-API",
            Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
            License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
        };

        internal static readonly OpenApiInfo AssetAdministrationShellRepository_OpenApiInfo = new OpenApiInfo
        {
            Version = "v1",
            Title = "BaSyx Asset Administration Shell Repository HTTP REST-API",
            Description = "The full description of the generic BaSyx Asset Administration Shell Repository HTTP REST-API",
            Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
            License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
        };

        internal static readonly OpenApiInfo Submodel_OpenApiInfo = new OpenApiInfo
        {
            Version = "v1",
            Title = "BaSyx Submodel HTTP REST-API",
            Description = "The full description of the generic BaSyx Submodel HTTP REST-API",
            Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
            License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
        };

        internal static readonly OpenApiInfo SubmodelRepository_OpenApiInfo = new OpenApiInfo
        {
            Version = "v1",
            Title = "BaSyx Submodel Repository HTTP REST-API",
            Description = "The full description of the generic BaSyx Submodel Repository HTTP REST-API",
            Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
            License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
        };

        internal static readonly OpenApiInfo Registry_OpenApiInfo = new OpenApiInfo
        {
            Version = "v1",
            Title = "BaSyx Registry HTTP REST-API",
            Description = "The full description of the BaSyx Registry HTTP REST-API",
            Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
            License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
        };

        internal static OpenApiInfo GetApiInfo(Interface interfaceType)
        {
            switch (interfaceType)
            {
                case Interface.AssetAdministrationShell:
                    return AssetAdministrationShell_OpenApiInfo;
                case Interface.AssetAdministrationShellRepository:
                    return AssetAdministrationShellRepository_OpenApiInfo;
                case Interface.Submodel:
                    return Submodel_OpenApiInfo;
                case Interface.SubmodelRepository:
                    return SubmodelRepository_OpenApiInfo;
                case Interface.AssetAdministrationShellRegistry:
                    return Registry_OpenApiInfo;
                case Interface.All:
                    return AssetAdministrationShell_OpenApiInfo;
                default:
                    return default;
            }
        }

        #endregion
    }

    public enum Interface
    {
        All,
        AssetAdministrationShell,
        AssetAdministrationShellRepository,
        Submodel,
        SubmodelRepository,
        AssetAdministrationShellRegistry
    }
    public static class SwaggerExtensions
    {
        public static void AddSwagger(this ServerApplication serverApp, Interface interfaceType)
        {
            OpenApiInfo info = OpenApiInfos.GetApiInfo(interfaceType);
            serverApp.ConfigureServices(services =>
            {
                services.AddSwaggerGen(c =>
                {
                    c.SwaggerDoc("v1", info);

                    // Set the comments path for the Swagger JSON and UI.
                    var xmlFile = $"{serverApp.ControllerAssembly.GetName().Name}.xml";
                    var xmlPath = Path.Combine(Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location), xmlFile);
                    if (EmbeddedResource.CheckOrWriteRessourceToFile(serverApp.ControllerAssembly, xmlPath))
                        c.IncludeXmlComments(xmlPath, true);

                    c.DocumentFilter<ControllerSelector>(interfaceType);
                });
                services.AddSwaggerGenNewtonsoftSupport();
            });

            serverApp.Configure(app =>
            {
                // Enable middleware to serve generated Swagger as a JSON endpoint.
                app.UseSwagger();

                // Enable middleware to serve swagger-ui (HTML, JS, CSS, etc.), specifying the Swagger JSON endpoint.
                app.UseSwaggerUI(c =>
                {
                    c.SwaggerEndpoint("/swagger/v1/swagger.json", info.Title);
                });
            });
        }

        internal protected class ControllerSelector : IDocumentFilter
        {
            private readonly Interface _interfaceType;
            private readonly string _interfaceName;
            public ControllerSelector(Interface interfaceType)
            {
                _interfaceType = interfaceType;
                _interfaceName = Enum.GetName(typeof(Interface), _interfaceType);
            }

            public void Apply(OpenApiDocument swaggerDoc, DocumentFilterContext context)
            {
                foreach (var apiDescription in context.ApiDescriptions)
                {
                    string name = apiDescription.ActionDescriptor.DisplayName;
                    if(!name.Contains(_interfaceName + "Controller"))
                    {
                        string route = "/" + apiDescription.ActionDescriptor.AttributeRouteInfo.Template;
                        swaggerDoc.Paths.Remove(route);                        
                    }
                }
                foreach (var tag in swaggerDoc.Tags.ToList())
                {
                    if(tag.Name != (_interfaceName + "Controller"))
                    {
                        swaggerDoc.Tags.Remove(tag);
                    }
                }
            }
        }
    }
}
