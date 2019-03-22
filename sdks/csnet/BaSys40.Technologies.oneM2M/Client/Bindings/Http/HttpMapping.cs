using System;
using System.Collections.Generic;
using static oneM2MClient.oneM2M;

namespace oneM2MClient.Client.Bindings
{
    public static class HttpMapping
    {
        public static readonly String NAME = "http";

        public static class MIME //hier war ein extends von OneM2M.MIME
        {
            public static readonly String APPLICATION_XML = "application/xml";
            public static readonly String APPLICATION_JSON = "application/json";
        }

        public static class Header
        {
            public static readonly String HOST = "host";
            public static readonly String ACCEPT = "accept";
            public static readonly String CONTENT_TYPE = "content-type";
            public static readonly String CONTENT_LOCATION = "content-location";
            public static readonly String CONTENT_LENGTH = "content-length";
            public static readonly String ETAG = "etag";
            public static readonly String X_M2M_ORIGIN = "X-M2M-Origin";
            public static readonly String X_M2M_RI = "X-M2M-RI";
            public static readonly String X_M2M_NM = "X-M2M-NM";
            public static readonly String X_M2M_GID = "X-M2M-GID";
            public static readonly String X_M2M_RTU = "X-M2M-RTU";
            public static readonly String X_M2M_OT = "X-M2M-OT";
            public static readonly String X_M2M_RST = "X-M2M-RST";
            public static readonly String X_M2M_RET = "X-M2M-RET";
            public static readonly String X_M2M_OET = "X-M2M-OET";
            public static readonly String X_M2M_EC = "X-M2M-EC";
            public static readonly String X_M2M_RSC = "X-M2M-RSC";

            private static Dictionary<String, String> _map = new Dictionary<string, string>();

            static Header()
            {
                _map.Add(Name.FROM, X_M2M_ORIGIN);
                _map.Add(Name.REQUEST_IDENTIFIER, X_M2M_RI);
                _map.Add(Name.NAME, X_M2M_NM);
                _map.Add(Name.ORIGINATING_TIMESTAMP, X_M2M_OT);
                _map.Add(Name.REQUEST_EXPIRATION_TIMESTAMP, X_M2M_RET);
                _map.Add(Name.RESULT_EXPIRATION_TIMESTAMP, X_M2M_RST);
                _map.Add(Name.OPERATION_EXECUTION_TIME, X_M2M_OET);
                _map.Add(Name.RESPONSE_TYPE_VALUE, X_M2M_RTU);
                _map.Add(Name.EVENT_CATEGORY, X_M2M_EC);
                _map.Add(Name.RESPONSE_STATUS_CODE, X_M2M_RSC);
                _map.Add(Name.GROUP_REQUEST_IDENTIFIER, X_M2M_GID);
            }

            public static String map(String name)
            {
                return _map.ContainsKey(name) ? _map[name] : name;
            }
        }
    }
}
