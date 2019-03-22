using System;
using System.IO;

namespace BaSys40.Utils.Settings
{
    public class FileWatcher
    {
        private readonly FileSystemWatcher fileSystemWatcher;
        private readonly string filePath;

        public delegate void FileChanged(string fullPath);
        private readonly FileChanged FileChangedHandler;

        public FileWatcher(string pathToFile, FileChanged fileChanged)
        {
            if (string.IsNullOrEmpty(pathToFile))
                throw new ArgumentNullException("pathToFile");
            else if (!File.Exists(pathToFile))
                throw new InvalidOperationException(pathToFile + "does not exist");

            filePath = pathToFile;
            FileChangedHandler = fileChanged;

            fileSystemWatcher = new FileSystemWatcher();
            fileSystemWatcher.Path = Path.GetDirectoryName(pathToFile);
            fileSystemWatcher.Filter = Path.GetFileName(pathToFile);
            fileSystemWatcher.NotifyFilter = NotifyFilters.LastWrite;

            fileSystemWatcher.Changed += new FileSystemEventHandler(OnChanged);

            fileSystemWatcher.EnableRaisingEvents = true;
        }

        private void OnChanged(object sender, FileSystemEventArgs e)
        {
            Console.Out.WriteLine("File: " + e.FullPath + " " + e.ChangeType);
            if (e.ChangeType == WatcherChangeTypes.Changed && filePath == e.FullPath)
                FileChangedHandler(e.FullPath);
        }
    }
}
