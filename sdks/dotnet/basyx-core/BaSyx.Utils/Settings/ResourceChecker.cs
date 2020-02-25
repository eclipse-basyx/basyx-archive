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
using NLog;
using System;
using System.IO;
using System.Reflection;

namespace BaSyx.Utils.Settings
{
    public static class ResourceChecker
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();

        /// <summary>
        /// Checks whether a resource is available in the assembly
        /// </summary>
        /// <param name="resourceName">Name of the resource</param>
        /// <param name="createFile">If true, writes the resource to a file in the current executing directory</param>
        /// <returns>
        /// true = Resource was found
        /// false = Resource was not found
        /// </returns>
        public static bool CheckResourceAvailability(Assembly sourceAssembly, string nameSpace, string resourceName, bool createFile)
        {
            if (File.Exists(resourceName) ||
                File.Exists(Path.Combine(Path.GetDirectoryName(Assembly.GetCallingAssembly().Location), resourceName)) ||
                File.Exists(Path.Combine(Path.GetDirectoryName(AppDomain.CurrentDomain.BaseDirectory), resourceName)))
            {
                return true;
            }
            else if (createFile)
            {
                if (WriteEmbeddedRessourceToFile(sourceAssembly, nameSpace, resourceName, null))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        /// <summary>
        /// Writes an embedded resource to a file in the executing directory
        /// </summary>
        /// <param name="resourceName">Name of the embedded resourcre</param>
        /// <returns>
        /// true = Resource was written successfully
        /// false = Resource was not written successfully
        /// </returns>
        public static bool WriteEmbeddedRessourceToFile(Assembly sourceAssembly, string nameSpace, string resourceName, string destinationFilename)
        {
            try
            {
                Stream configStream = sourceAssembly.GetManifestResourceStream(string.Join(".", nameSpace, resourceName));
                if (configStream == null)
                    throw new FileNotFoundException("Resource '" + resourceName + "' not found");

                string filePath = string.IsNullOrEmpty(destinationFilename) ? Path.Combine(Path.GetDirectoryName(AppDomain.CurrentDomain.BaseDirectory), resourceName) : destinationFilename;

                using (var fileStream = File.Create(filePath))
                {
                    configStream.Seek(0, SeekOrigin.Begin);
                    configStream.CopyTo(fileStream);
                }
                return (true);
            }
            catch (Exception e)
            {
                logger.Error(e, "Error creating '" + resourceName + "' from embedded resource. Exception: " + e.ToString());
                return (false);
            }

        }
    }
}
