using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace BaSys40.Utils.Settings
{
    public class DirectoryWatcher
    {
        private readonly FileSystemWatcher fileSystemWatcher;
        private readonly string directoryPath;

        public delegate void DirectoryChanged(string fullPath);
        private readonly DirectoryChanged DirectoryChangedHandler;

        public DirectoryWatcher(string pathToDirectory, string filter, bool createIfNotExists, DirectoryChanged directoryChanged)
        {
            if (string.IsNullOrEmpty(pathToDirectory))
                throw new ArgumentNullException("pathToDirectory");
            else if (!Directory.Exists(pathToDirectory))
            {
                if (createIfNotExists)
                    Directory.CreateDirectory(pathToDirectory);
                else
                    throw new InvalidOperationException(pathToDirectory + "does not exist");
            }
            directoryPath = pathToDirectory;
            DirectoryChangedHandler = directoryChanged;

            fileSystemWatcher = new FileSystemWatcher();
            fileSystemWatcher.Path = pathToDirectory;
            fileSystemWatcher.Filter = filter;
            fileSystemWatcher.NotifyFilter = NotifyFilters.LastWrite;

            fileSystemWatcher.Changed += new FileSystemEventHandler(OnChanged);

            fileSystemWatcher.EnableRaisingEvents = true;
        }

        private void OnChanged(object sender, FileSystemEventArgs e)
        {
            Console.Out.WriteLine("File: " + e.FullPath + " " + e.ChangeType);
            try
            {
                fileSystemWatcher.EnableRaisingEvents = false;
                DirectoryChangedHandler(e.FullPath);
            }
            finally
            {
                fileSystemWatcher.EnableRaisingEvents = true;
            }
            
        }
    }
}
