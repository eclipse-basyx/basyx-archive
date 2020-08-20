using BaSyx.AAS.Client.Http;
using BaSyx.Utils.Network;
using System;
using System.Threading.Tasks;

namespace BaSyx.Discovery.mDNS
{
    public static class Discovery
    {
        /// <summary>
        /// Returns an AssetAdministrationShell-Http-Client if found via mDNS Discovery
        /// </summary>
        /// <param name="aasId">The Asset Administration Shell's unique id to look for</param>
        /// <param name="timeout">Timeout in ms until it stops looking</param>
        /// <returns></returns>
        public static async Task<AssetAdministrationShellHttpClient> GetHttpClientByShellIdAsync(string aasId, int timeout)
        {
            AssetAdministrationShellHttpClient client = null;
            DiscoveryServer discoveryServer = new DiscoveryServer(ServiceTypes.AAS_SERVICE_TYPE);

            EventHandler<ServiceInstanceEventArgs> eventHandler = async(sender, e) =>
            {
                var found = e.TxtRecords.Find(f => f.Contains(aasId));
                if (found != null)
                    foreach (var server in e.Servers)
                    {
                        bool pingable = await NetworkUtils.PingHostAsync(server.Address.ToString());
                        if (pingable)
                        {
                            var endpointRecord = e.TxtRecords.Find(f => f.Contains(server.Address.ToString()));
                            if (endpointRecord.Contains("="))
                            {
                                string[] splitted = endpointRecord.Split(new char[] { '=' }, StringSplitOptions.None);
                                Uri endpoint = new Uri(splitted[1]);
                                client = new AssetAdministrationShellHttpClient(endpoint);
                            }
                        }
                    }
            };

            discoveryServer.ServiceInstanceDiscovered += eventHandler;
            discoveryServer.Start();

            Task timeoutTask = Task.Delay(timeout);

            while (client == null && !timeoutTask.IsCompleted)
                await Task.Delay(100);

            discoveryServer.ServiceInstanceDiscovered -= eventHandler;
            discoveryServer.Stop();
            
            return client;
        }        
    }
}
