using oneM2MClient.Utils.ResultHandling;

namespace oneM2MClient.Client
{
    public interface IClient
    {
        bool IsAlive { get; }

        ClientFactory.Protocol Protocol { get; }

        void Start();

        void Stop();

        Result<Response> Send(Request request);
    }
}
