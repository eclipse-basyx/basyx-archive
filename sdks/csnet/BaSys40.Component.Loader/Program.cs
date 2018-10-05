using BaSys40.API.Components;
using BaSys40.API.Platform;
using BaSys40.RI.AAS.SmartControl;
using BaSys40.Utils.Settings;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading;
using System.Threading.Tasks;

namespace BaSys40.Component.Loader
{
    public class Program
    {
        static DirectoryWatcher assemblyFolderWatcher;
        static IAssetAdministrationShellRegistry registry;
        static IAssetAdministrationShellManager manager;
        static IAssetAdministrationShellHandler handler;

        static Dictionary<string, CancellationTokenSource> updaters = new Dictionary<string, CancellationTokenSource>();
        static void Main(string[] args)
        {
            Initialize();

            string loaderPath = Path.Combine(Path.GetDirectoryName(Assembly.GetEntryAssembly().Location), "Loader");
            var files = Directory.GetFiles(loaderPath);
            foreach (var file in files)
            {
                LoadIComponentConnectorAssembly(file);
            }

            assemblyFolderWatcher = new DirectoryWatcher(loaderPath, "*.*", true, LoadIComponentConnectorAssembly);

            while (!Console.KeyAvailable)
            {
                Console.Write("Type Q to quit application: ");
                var key = Console.ReadLine();

                if (key == "Q")
                    Environment.Exit(0);
            }
        }

        public static void Initialize()
        {
            registry = SmartControl.Instance;
            manager = SmartControl.Instance;
            handler = new SmartControlHandler(8544);
        }

        public static void LoadIComponentConnectorAssembly(string fullPath)
        {
            try
            {
                Console.WriteLine("New file detected: " + fullPath);
                var assembly = Assembly.LoadFrom(fullPath);

                var allTypes = assembly.GetTypes();
                var types = (from type in allTypes
                             where (type.GetInterfaces().Contains(typeof(IComponentConnector)) && !type.IsAbstract)
                             select type).ToList();

                foreach (var type in types)
                {
                    IComponentConnector instance = (IComponentConnector)Activator.CreateInstance(type);

                    //retrieve AAS from component
                    var aas = instance.GenerateAssetAdministrationShell();
                    Console.WriteLine("AAS with Id '" + aas.Identification.Id + "' generated");

                    //delete if AAS is already registered
                    instance.Unregister(registry, aas.Identification.Id);

                    //register AAS
                    var registered = instance.Register(registry, aas);
                    if(registered.Success)
                        Console.WriteLine("Registered successfully");
                    else
                    {
                        Console.WriteLine("Registration failed:");
                        foreach (var message in registered.Messages)
                            Console.WriteLine(message.ToString());
                        
                    }
                    var created = instance.CreateStructure(manager, handler, aas);

                    if (created.Success)
                        Console.WriteLine("Created successfully");
                    else
                    {
                        Console.WriteLine("Creation failed:");
                        foreach (var message in created.Messages)
                            Console.WriteLine(message.ToString());

                        return;
                    }
                    updaters.Add(aas.Identification.Id, new CancellationTokenSource());
                    Task.Factory.StartNew(async () =>
                    {
                        while (!updaters[aas.Identification.Id].IsCancellationRequested)
                        {
                            var exists = manager.RetrieveAssetAdministrationShell(aas.Identification.Id);
                            if (!exists.Success || exists.Entity == null)
                                instance.CreateStructure(manager, handler, aas);

                            await Task.Delay(10000);
                        }
                    }, updaters[aas.Identification.Id].Token, TaskCreationOptions.LongRunning, TaskScheduler.Default);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception while loading assembly ': " + fullPath + "' - Message: " +e.Message);
            }
        }
    }
}
