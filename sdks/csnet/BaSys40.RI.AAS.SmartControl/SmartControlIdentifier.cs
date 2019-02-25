namespace BaSys40.RI.AAS.SmartControl
{
    public partial class SmartControl
    {
        public const string SEPERATOR = ":";
        public const string ELEMENT_SEPERATOR = ",";
        public const string PATH_SEPERATOR = "/";

        public const string BASYS_TYPE_IDENTIFIER = "basys";
        public const string TYPE_IDENTIFIER = "td";
        public const string DATATYPE_IDENTIFIER = "dty";
        public const string RETURN_DATATYPE_IDENTIFIER = "rdty";

        public const int DEFAULT_TIMEOUT = 10000;

        public const string DEFAULT_FROM = "admin:admin";
        public const string DEFAULT_SUBSCRIPTION_NAME = "default_subscription";
        public const string DEFAULT_SUBSCRIPTION_URI = "http://127.0.0.1:8544";

        public const string INSTANCE = "inst";
        public const string TYPE = "ty";

        public const string RELATION = "rel";


        public static class AssetLabels
        {
            public const string ASSET_TYPE_DEFINITION = "atd";
            public const string ASSET_ID = "aid";
            public const string ASSET_KIND = "ak";
            public const string ASSET_UUID = "auuid";
        }

        public static class ElementType
        {
            public const string AAS_TYPE = "aas-ty";
            public const string PROPERTY_TYPE = "prop-ty";
            public const string OPERATION_TYPE = "op-ty";
            public const string SUBMODEL_TYPE = "sm-ty";
            public const string EVENT_TYPE = "evt-ty";
        }

        public static class ElementInstance
        {
            public const string AAS = "aas";
            public const string PROPERTY = "prop";
            public const string OPERATION = "op";
            public const string SUBMODEL = "sm";
            public const string EVENT = "evt";
            public const string SUBMODELS = "submodels";
        }

        public static class EventIdentifier
        {
            public const string ORIGINATOR = "orig";
            public const string SCHEMA_TYPE = "sc-ty";
            public const string EVENT_CATEGORY = "cat";
            public const string SCHEMA_CIN = "cin_schema";
            public const string SUBSCRIBER_SUB = "sub_subscriber";
        }

        public static class ContainerStrings
        {
            public const string GET = "GET";
            public const string SET = "SET";
            public const string DATA = "DATA";
            public const string REQUEST = "REQ";
            public const string PROCESSING = "PROC";
            public const string RESPONSE = "RESP";
            public const string SCHEMA = "SCHEMA";
        }

        public static class Labels
        {
            public const string READABLE = "rd";
            public const string WRITABLE = "wr";
            public const string EVENTABLE = "ev";
            public const string ID = "id";
            public const string DESCRIPTION = "desc";
            public const string DISPLAY_NAME = "dn";
            public const string COLLECTION = "col:map";
            public const string UUID = "uuid";
        }

        public static class ParameterStrings
        {
            public const string PARAM_IDENTIFIER = "par";
            public const string PARAM_SEPERATOR = "-";
            public const string PARAM_NAME = "nm";
            public const string PARAM_DATATYPE = "dty";
            public const string PARAM_LENGTH = "len";

            public const string PARAM_BRACKET_LEFT = "[";
            public const string PARAM_BRACKET_RIGHT = "]";

            public static string GetParameterLength()
            {
                return PARAM_IDENTIFIER + PARAM_SEPERATOR + PARAM_LENGTH;
            }

            public static string GetParameterName(int i)
            {
                return PARAM_IDENTIFIER + PARAM_SEPERATOR + i + PARAM_SEPERATOR + PARAM_NAME;
            }

            public static string GetParameterDataType(int i)
            {
                return PARAM_IDENTIFIER + PARAM_SEPERATOR + i + PARAM_SEPERATOR + PARAM_DATATYPE;
            }
        }

        public static class OneM2MStrings
        {
            public const string LATEST = "la";
        }

    }


}
