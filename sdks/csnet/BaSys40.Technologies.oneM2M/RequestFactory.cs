using oneM2MClient.Client;

namespace oneM2MClient
{
    public static class RequestFactory
    {
        public static Request CreateRequest(IClient client, string clientName, string endpointAddress, string cseName, params string[] path)
        {
            if (client != null)
            {
                string joinedPath = string.Join("/", path).Replace("//", "/");

                switch (client.Protocol)
                {
                    case ClientFactory.Protocol.Http:
                        var httpRequest = new HttpRequest(clientName, endpointAddress, cseName, joinedPath);
                        httpRequest.SetPath(path);
                        return httpRequest;
                    case ClientFactory.Protocol.Mqtt:
                        var mqttRequest = new MqttRequest(clientName, endpointAddress, cseName, joinedPath);
                        return mqttRequest;

                    default:
                        return null;
                }
            }
            return null;
        }
    }
}
