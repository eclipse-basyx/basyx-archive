using oneM2MClient.Protocols;
using System;

namespace oneM2MClient.Client
{
    public interface IRequest
    {
        rqp RequestPrimitive { get; set; }
        string ContentMIME { get; set; }
        string AcceptMIME { get; set; }
        int MillisecondsTimeout { get;  set;}

        string CSEName { get; set; }
        string EndpointAddress { get; set; }

        Uri RequestPath { get; set; }

        Request ClearRequest();
    }
}