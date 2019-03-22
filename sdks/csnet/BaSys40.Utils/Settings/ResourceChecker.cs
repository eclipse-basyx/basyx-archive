using NLog;
using System;
using System.IO;
using System.Reflection;

namespace BaSys40.Utils.Settings
{
    public static class ResourceChecker
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();

        /// <summary>
        /// Prüft ob eine notwendige Ressource vorhanden ist oder nicht
        /// </summary>
        /// <param name="resourceName">Name der Ressource inkl. Pfad (z.B. ApplicationStartupPath + "\\WcfClientEndpoints.config")</param>
        /// <param name="create">Gibt an ob die Ressource notfalls angelegt werden soll</param>
        /// <returns>
        /// true = Ressource ist vorhanden
        /// false = Ressource nicht vorhanden und/oder kann nicht angelegt werden
        /// </returns>
        public static bool CheckResource(Assembly sourceAssembly, string nameSpace, string resourceName, bool create)
        {
            if (File.Exists(resourceName) || File.Exists(Path.Combine(Path.GetDirectoryName(Assembly.GetCallingAssembly().Location), resourceName)))
                return true;
            else if (create)
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
        /// Schreibt eine eingebette Ressource in eine Datei im gleichen Pfad die gerade ausführende Applikation
        /// </summary>
        /// <param name="resourceName">Name der Ressource inkl. Pfad (z.B. ApplicationStartupPath + "\\WcfClientEndpoints.config")</param>
        /// <returns>
        /// true = Ressource wurde erfolgreich angelegt
        /// false = Ressource konnte nicht angelegt werden
        /// </returns>
        public static bool WriteEmbeddedRessourceToFile(Assembly sourceAssembly, string nameSpace, string resourceName, string destinationFilename)
        {
            try
            {
                Stream configStream = sourceAssembly.GetManifestResourceStream(string.Join(".", nameSpace, resourceName));
                if (configStream == null)
                    throw new FileNotFoundException("Resource '" + resourceName + "' not found");

                string filePath = string.IsNullOrEmpty(destinationFilename) ? Path.Combine(Path.GetDirectoryName(Assembly.GetCallingAssembly().Location), resourceName) : destinationFilename;

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
