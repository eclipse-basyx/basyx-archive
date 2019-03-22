using System;
using System.Collections.Generic;
using System.Globalization;

namespace oneM2MClient
{
    public class oneM2M
    {
        public const String NAMESPACE = "m2m";
        public const String PRIMITIVE_PACKAGE = "oneM2MClient.Protocols.";
        public const String NAMESPACE_DELIMITER = ":";
        public const String ERROR_INDICATOR = "error";

        public enum Operation : int
        {
            NOOPERATION = 0,
            CREATE = 1,
            RETRIEVE = 2,
            UPDATE = 3,
            DELETE = 4,
            NOTIFY = 5
        }

        public enum ConsistencyStrategy : int
        {
            ABANDON_MEMBER = 1,
            ABANDON_GROUP = 2,
            SET_MIXED = 3
        }


        public enum EncodingType : int
        {
            PLAIN = 0,
            BASE64_ON_STRING = 1,
            BASE64_ON_BINARY = 2
        }

        public enum EventType : int
        {
            DATAOPERATION = 1,
            STORAGEBASED = 2,
            TIMERBASED = 3
        }

        public enum ExecModeType : int
        {
            IMMEDIATEONCE = 1,
            IMMEDIATEREPEAT = 2,
            RANDOMONCE = 3,
            RANDOMREPEAT = 4
        }

        public enum ExecStatusType : int
        {
            INITIATED = 1,
            PENDING = 2,
            FINISHED = 3,
            CANCELLING = 4,
            CANCELLED = 5,
            STATUS_NON_CANCELLABLE = 6
        }

        public enum LocationSource : int
        {
            NETWORK_BASED = 1,
            DEVICE_BASED = 2,
            SHARING_BASED = 3
        }

        public enum LogStatus : int
        {
            STARTED = 1,
            STOPPED = 2,
            UNKNOWN = 3,
            NOT_PRESENT = 4,
            ERROR = 5
        }


        public enum LogTypeId : int
        {
            SYSTEM = 1,
            SECURITY = 2,
            EVENT = 3,
            TRACE = 4,
            PANIC = 5
        }

        public enum MemberType : int
        {
            ACCESS_CONTROL_POLICY = 1,
            AE = 2,
            CONTAINER = 3,
            CONTENT_INSTANCE = 4,
            CSE_BASE = 5,
            DELIVERY = 6,
            EVENT_CONFIG = 7,
            EXEC_INSTANCE = 8,
            GROUP = 9,
            LOCATION_POLICY = 10,
            M2M_SERVICE_SUBSCRIPTION = 11,
            MGMT_CMD = 12,
            MGMT_OBJ = 13,
            NODE = 14,
            POLLING_CHANNEL = 15,
            REMOTE_CSE = 16,
            REQUEST = 17,
            SCHEDULE = 18,
            SERVICE_SUBSCRIBED_APP_RULE = 19,
            SERVICE_SUBSCRIBED_NODE = 20,
            STATS_COLLECT = 21,
            STATS_CONFIG = 22,
            SUBSCRIPTION = 23,
            MIXED = 24
        }

        public enum MgmtDefinition : int
        {
            FIRMWARE = 1001,
            SOFTWARE = 1002,
            MEMORY = 1003,
            AREA_NWK_INFO = 1004,
            AREA_NWK_DEVICE_INFO = 1005,
            BATTERY = 1006,
            DEVICE_INFO = 1007,
            DEVICE_CAPABILITY = 1008,
            REBOOT = 1009,
            EVENT_LOG = 1010,
            CMDH_POLICY = 1011,
            ACTIVE_CMDH_POLICY = 1012,
            CMDH_DEFAULTS = 1013,
            CMDH_DEF_EC_VALUE = 1014,
            CMDH_EC_DEF_PARAM_VALUES = 1015,
            CMDH_LIMITS = 1016,
            CMDH_NETWORK_ACCESS_RULES = 1017,
            CMDH_NW_ACCESS_RULE = 1018,
            CMDH_BUFFER = 1019,
            UNSPECIFIED = 0
        }

        public enum NotificationContentType : int
        {
            MODIFIED_ATTRIBUTES = 1,
            WHOLE_RESOURCE = 2,
            REFERENCE_ONLY = 3
        }

        public enum PendingNotification : int
        {
            SEND_LATEST = 1,
            SEND_ALL_PENDING = 2
        }

        public enum RequestStatus : int
        {
            COMPLETED = 1,
            FAILED = 2,
            PENDING = 3,
            FORWARDED = 4
        }

        public enum ResourceStatus : int
        {
            CHILD_CREATED = 1,
            CHILD_DELETED = 2,
            UPDATED = 3,
            DELETED = 4
        }

        public class SRoleId
        {
            private readonly String newName;
            private readonly String original;

            public static readonly SRoleId SOFTWARE_MANAGEMENT = new SRoleId("01-001", "SOFTWARE_MANAGEMENT");
            public static readonly SRoleId DEVICE_CONFIGURATION = new SRoleId("02-001", "DEVICE_CONFIGURATION");
            public static readonly SRoleId DEVICE_DIAGNOSTICS_AND_MANAGEMENT = new SRoleId("02-002", "DEVICE_DIAGNOSTICS_AND_MANAGEMENT");
            public static readonly SRoleId DEVICE_FIRMWARE_MANAGEMENT = new SRoleId("02-003", "DEVICE_FIRMWARE_MANAGEMENT");
            public static readonly SRoleId DEVICE_TOPOLOGY = new SRoleId("02-004", "DEVICE_TOPOLOGY");
            public static readonly SRoleId LOCATION = new SRoleId("03-001", "LOCATION");
            public static readonly SRoleId BASIC_DATA = new SRoleId("04-001", "BASIC_DATA");
            public static readonly SRoleId ONBOARDING = new SRoleId("05-001", "ONBOARDING");
            public static readonly SRoleId SECURITY_ADMINISTRATION = new SRoleId("06-001", "SECURITY_ADMINISTRATION");
            public static readonly SRoleId GROUPS_MANAGEMENT = new SRoleId("07-001", "GROUPS_MANAGEMENT");
            public static readonly SRoleId EVENT_COLLECTION = new SRoleId("08-001", "EVENT_COLLECTION");

            private SRoleId(String original, String newName)
            {
                this.newName = newName;
                this.original = original;
            }

            public String value()
            {
                return original;
            }
        }

        public enum StatModelType : int
        {
            EVENTBASED = 1
        }

        public enum StatsRuleStatusType : int
        {
            ACTIVE = 1,
            INACTIVE = 2,
        }

        public enum Status : int
        {
            SUCCESSFUL = 1,
            FAILURE = 2,
            IN_PROCESS = 3,
        }

        public enum StdEventCats : int
        {
            DEFAULT = 1,
            IMMEDIATE = 2,
            BEST_EFFORT = 3,
            LATEST = 4,
            UNKNOWN
        }

        public enum ResultContent : int
        {
            NOTHING = 0,
            ATTRIBUTES = 1,
            HIERARCHICAL_ADDRESS = 2,
            HIERARCHICAL_ADDRESS_AND_ATTRIBUTES = 3,
            ATTRIBUTES_AND_CHILD_RESOURCES = 4,
            ATTRIBUTES_AND_CHILD_RESOURCE_REFERENCES = 5,
            CHILD_RESOURCE_REFERENCES = 6,
            ORIGINAL_RESOURCE = 7
        }

        public enum ResponseType : int
        {
            NON_BLOCKING_REQUEST_SYNCH = 1,
            NON_BLOCKING_REQUEST_ASYNCH = 2,
            BLOCKING_REQUEST = 3
        }

        public enum ExecResultType : int
        {
            STATUS_REQUEST_UNSUPPORTED = 1,
            STATUS_REQUEST_DENIED = 2,
            STATUS_CANCELLATION_DENIED = 3,
            STATUS_INTERNAL_ERROR = 4,
            STATUS_INVALID_ARGUMENTS = 5,
            STATUS_RESOURCES_EXCEEDED = 6,
            STATUS_FILE_TRANSFER_FAILED = 7,
            STATUS_FILE_TRANSFER_SERVER_AUTHENTICATION_FAILURE = 8,
            STATUS_UNSUPPORTED_PROTOCOL = 9,
            STATUS_UPLOAD_FAILED = 10,
            STATUS_FILE_TRANSFER_FAILED_MULTICAST_GROUP_UNABLE_JOIN = 11,
            STATUS_FILE_TRANSFER_FAILED_SERVER_CONTACT_FAILED = 12,
            STATUS_FILE_TRANSFER_FAILED_FILE_ACCESS_FAILED = 13,
            STATUS_FILE_TRANSFER_FAILED_DOWNLOAD_INCOMPLETE = 14,
            STATUS_FILE_TRANSFER_FAILED_FILE_CORRUPTED = 15,
            STATUS_FILE_TRANSFER_FILE_AUTHENTICATION_FAILURE = 16,
            //todo duplicated with 8,9
            //    STATUS_FILE_TRANSFER_FAILED( BigInteger.valueOf(17,
            //    STATUS_FILE_TRANSFER_SERVER_AUTHENTICATION_FAILURE = 18,
            STATUS_FILE_TRANSFER_WINDOW_EXCEEDED = 19,
            STATUS_INVALID_UUID_FORMAT = 20,
            STATUS_UNKNOWN_EXECUTION_ENVIRONMENT = 21,
            STATUS_DISABLED_EXECUTION_ENVIRONMENT = 22,
            STATUS_EXECUTION_ENVIRONMENT_MISMATCH = 23,
            STATUS_DUPLICATE_DEPLOYMENT_UNIT = 24,
            STATUS_SYSTEM_RESOURCES_EXCEEDED = 25,
            STATUS_UNKNOWN_DEPLOYMENT_UNIT = 26,
            STATUS_INVALID_DEPLOYMENT_UNIT_STATE = 27,
            STATUS_INVALID_DEPLOYMENT_UNIT_UPDATE_DOWNGRADE_DISALLOWED = 28,
            STATUS_INVALID_DEPLOYMENT_UNIT_UPDATE_UPGRADE_DISALLOWED = 29,
            STATUS_INVALID_DEPLOYMENT_UNIT_UPDATE_VERSION_EXISTS = 30
        }


        public enum DiscResType : int
        {
            STRUCTURED = 1,
            UNSTRUCTURED = 2
        }

        public enum CseTypeId : int
        {
            IN_CSE = 1,
            MN_CSE = 2,
            ASN_CSE = 3
        }

        public enum ResponseStatusCodes : int
        {
            ACCEPTED = 1000,
            OK = 2000,
            CREATED = 2001,
            DELETED = 2002,
            CHANGED = 2004,
            BAD_REQUEST = 4000,
            NOT_FOUND = 4004,
            OPERATION_NOT_ALLOWED = 4005,
            REQUEST_TIMEOUT = 4008,
            SUBSCRIPTION_CREATOR_HAS_NO_PRIVILEGE_ = 4101,
            CONTENTS_UNACCEPTABLE = 4102,
            ACCESS_DENIED = 4103,
            GROUP_REQUEST_IDENTIFIER_EXISTS = 4104,
            CONFLICT = 4105,
            INTERNAL_SERVER_ERROR = 5000,
            NOT_IMPLEMENTED = 5001,
            TARGET_NOT_REACHABLE = 5103,
            NO_PRIVILEGE = 5105,
            ALREADY_EXISTS = 5106,
            TARGET_NOT_SUBSCRIBABLE = 5203,
            SUBSCRIPTION_VERIFICATION_INITIATION_FAILED = 5204,
            SUBSCRIPTION_HOST_HAS_NO_PRIVILEGE = 5205,
            NON_BLOCKING_REQUEST_NOT_SUPPORTED = 5206,
            EXTENAL_OBJECT_NOT_REACHABLE = 6003,
            EXTENAL_OBJECT_NOT_FOUND = 6005,
            MAX_NUMBERF_OF_MEMBER_EXCEEDED = 6010,
            MEMBER_TYPE_INCONSISTENT = 6011,
            MGMT_SESSION_CANNOT_BE_ESTABLISHED = 6020,
            MGMT_SESSION_ESTABLISHMENT_TIMEOUT = 6021,
            INVALID_CMDTYPE = 6022,
            INVALID_ARGUMENTS = 6023,
            INSUFFICIENT_ARGUMENTS = 6024,
            MGMT_CONVERSION_ERROR = 6025,
            MGMT_CANCELATION_FAILURE = 6026,
            ALREADY_COMPLETE = 6028,
            COMMAND_NOT_CANCELLABLE = 6029,
            DEFAULT
        }

        public enum AccessControlOperations : int
        {
            CREATE = 1,
            RETRIEVE = 2,
            UPDATE = 4,
            DELETE = 8,
            DISCOVERY = 16,
            NOTIFY = 32,
            DEFAULT
        }

        public enum CmdType : int
        {
            RESET = 1,
            REBOOT = 2,
            UPLOAD = 3,
            DOWNLOAD = 4,
            SOFTWAREINSTALL = 5,
            SOFTWAREUNINSTALL = 6,
            SOFTWAREUPDATE = 7
        }

        public enum BatteryStatus : int
        {
            NORMAL = 1,
            CHARGING = 2,
            CHARGING_COMPLETE = 3,
            DAMAGED = 4,
            LOW_BATTERY = 5,
            NOT_INSTALLED = 6,
            UNKNOWN = 7
        }

        public enum ResourceType : int
        {
            NoResource = 0,
            AccessControlPolicy = 1,
            ApplicationEntity = 2,
            Container = 3,
            ContentInstance = 4,
            CSEBase = 5,
            Delivery = 6,
            EventConfig = 7,
            ExecInstance = 8,
            Group = 9,
            LocationPolicy = 10,
            M2MServiceSubscriptionProfile = 11,
            MgmtCmd = 12,
            MgmtObj = 13,
            Node = 14,
            PollingChannel = 15,
            RemoteCSE = 16,
            Request = 17,
            Schedule = 18,
            ServiceSubscribedAppRule = 19,
            ServiceSubscribedNode = 20,
            StatsCollect = 21,
            StatsConfig = 22,
            Subscription = 23,
            AccessControlPolicyAnnouncable = 10001,
            ApplicationEntityAnnouncable = 10002,
            ContainerAnnouncable = 10003,
            ContentInstanceAnnouncable = 10004,
            GroupAnnouncable = 10009,
            LocationPolicyAnnouncable = 10010,
            MgmtObjAnnouncable = 10013,
            NodeAnnouncable = 10014,
            RemoteCSEAnnouncable = 10016,
            ScheduleAnnouncable = 10018
        }

        public enum FilterUsage : int
        {
            NOFILTER,
            DISCOVERY_CRITERIA = 1,
            CONDITONAL_RETRIEVAL = 2
        }
        public static class MIME
        {
            public const string VND_ONEM2M_RES_XML = "vnd.onem2m-res+xml";
            public const string VND_ONEM2M_RES_JSON = "vnd.onem2m-res+json";
            public const string VND_ONEM2M_NTFY_XML = "vnd.onem2m-ntfy+xml";
            public const string VND_ONEM2M_NTFY_JSON = "vnd.onem2m-ntfy+json";
            public const string VND_ONEM2M_PREQ_XML = "vnd.onem2m-PREQ+xml";
            public const string VND_ONEM2M_PREQ_JSON = "vnd.onem2m-PREQ+json";
            public const string VND_ONEM2M_PRSP_XML = "vnd.onem2m-PRSP+xml";
            public const string VND_ONEM2M_PRSP_JSON = "vnd.onem2m-PRSP+json";
        }

        public static class Name
        {
            public const string OPERATION = "op";
            public const string TO = "to";
            public const string FROM = "fr";
            public const string REQUEST_IDENTIFIER = "rqi";
            public const string RESOURCE_TYPE = "ty";
            public const string NAME = "nm";
            public const string PRIMITIVE_CONTENT = "pc";
            public const string ROLE = "rol";
            public const string ORIGINATING_TIMESTAMP = "ot";
            public const string REQUEST_EXPIRATION_TIMESTAMP = "rqet";
            public const string RESULT_EXPIRATION_TIMESTAMP = "rset";
            public const string OPERATION_EXECUTION_TIME = "oet";
            public const string RESPONSE_TYPE = "rt";
            public const string RESULT_PERSISTENCE = "rp";
            public const string RESULT_CONTENT = "rcn";
            public const string EVENT_CATEGORY = "ec";
            public const string DELIVERY_AGGREGATION = "da";
            public const string GROUP_REQUEST_IDENTIFIER = "gid";
            public const string FILTER_CRITERIA = "fc";
            public const string DISCOVERY_RESULT_TYPE = "drt";
            public const string RESPONSE_STATUS_CODE = "rsc";
            public const string REQUEST_PRIMITIVE = "rqp";
            public const string RESPONSE_PRIMITIVE = "rsp";
            public const string ACCESS_CONTROL_POLICY_IDS = "acpi";
            public const string ANNOUNCED_ATTRIBUTE = "aa";
            public const string ANNOUNCE_TO = "at";
            public const string CREATION_TIME = "ct";
            public const string EXPIRATION_TIME = "et";
            public const string LABELS = "lbl";
            public const string LINK = "lnk";
            public const string LAST_MODIFIED_TIME = "lt";
            public const string PARENT_ID = "pi";
            public const string RESOURCE_ID = "ri";
            public const string STATE_TAG = "st";
            public const string RESOURCE_NAME = "rn";
            public const string PRIVILEGES = "pv";
            public const string SELF_PRIVILEGES = "pvs";
            public const string APP_ID = "api";
            public const string AE_ID = "aei";
            public const string APP_NAME = "apn";
            public const string POINT_OF_ACCESS = "poa";
            public const string ONTOLOGY_REF = "or";
            public const string NODE_LINK = "nl";
            public const string CREATOR = "cr";
            public const string MAX_NR_OF_INSTANCES = "mni";
            public const string MAX_BYTE_SIZE = "mbs";
            public const string MAX_INSTANCE_AGE = "mia";
            public const string CURRENT_NR_OF_INSTANCES = "cni";
            public const string CURRENT_BYTE_SIZE = "cbs";
            public const string LOCATION_ID = "li";
            public const string CONTENT_INFO = "cnf";
            public const string CONTENT_SIZE = "cs";
            public const string CONTENT = "con";
            public const string CSE_TYPE = "cst";
            public const string CSE_ID = "csi";
            public const string SUPPORTED_RESOURCE_TYPE = "srt";
            public const string NOTIFICATION_CONGESTION_POLICY = "ncp";
            public const string SOURCE = "sr";
            public const string TARGET = "tg";
            public const string LIFESPAN = "ls";
            public const string EVENT_CAT = "eca";
            public const string DELIVERY_META_DATA = "dmd";
            public const string AGGREGATED_REQUEST = "arq";
            public const string EVENT_ID = "evi";
            public const string EVENT_TYPE = "evt";
            public const string EVENT_START = "evs";
            public const string EVENT_END = "eve";
            public const string OPERATION_TYPE = "opt";
            public const string DATA_SIZE = "ds";
            public const string EXEC_STATUS = "exs";
            public const string EXEC_RESULT = "exr";
            public const string EXEC_DISABLE = "exd";
            public const string EXEC_TARGET = "ext";
            public const string EXEC_MODE = "exm";
            public const string EXEC_FREQUENCY = "exf";
            public const string EXEC_DELAY = "exy";
            public const string EXEC_NUMBER = "exn";
            public const string EXEC_REQ_ARGS = "exra";
            public const string EXEC_ENABLE = "exe";
            public const string MEMBER_TYPE = "mt";
            public const string CURRENT_NR_OF_MEMBERS = "cnm";
            public const string MAX_NR_OF_MEMBERS = "mnm";
            public const string MEMBER_IDS = "mid";
            public const string MEMBERS_ACCESS_CONTROL_POLICY_IDS = "macp";
            public const string MEMBER_TYPE_VALIDATED = "mtv";
            public const string CONSISTENCY_STRATEGY = "csy";
            public const string GROUP_NAME = "gn";
            public const string LOCATION_SOURCE = "los";
            public const string LOCATION_UPDATE_PERIOD = "lou";
            public const string LOCATION_TARGET_ID = "lot";
            public const string LOCATION_SERVER = "lor";
            public const string LOCATION_CONTAINER_ID = "loi";
            public const string LOCATION_CONTAINER_NAME = "lon";
            public const string LOCATION_STATUS = "lost";
            public const string SERVICE_ROLES = "svr";
            public const string DESCRIPTION = "dc";
            public const string CMD_TYPE = "cmt";
            public const string MGMT_DEFINITION = "mgd";
            public const string OBJECT_IDS = "obis";
            public const string OBJECT_PATHS = "obps";
            public const string NODE_ID = "ni";
            public const string HOSTED_CSE_LINK = "hcl";
            public const string CSE_BASE = "cb";
            public const string M2M_EXT_ID = "mei";
            public const string TRIGGER_RECIPIENT_ID = "tri";
            public const string REQUEST_REACHABILITY = "rr";
            public const string ORIGINATOR = "org";
            public const string META_INFORMATION = "mi";
            public const string REQUEST_STATUS = "rs";
            public const string OPERATION_RESULT = "ors";
            public const string REQUEST_ID = "rid";
            public const string SCHEDULE_ELEMENT = "se";
            public const string DEVICE_IDENTIFIER = "di";
            public const string RULE_LINKS = "rlk";
            public const string STATS_COLLECT_ID = "sci";
            public const string COLLECTING_ENTITY_ID = "cei";
            public const string COLLECTED_ENTITY_ID = "cdi";
            public const string DEV_STATUS = "ss";
            public const string STATS_RULE_STATUS = "srs";
            public const string STAT_MODEL = "sm";
            public const string COLLECT_PERIOD = "cp";
            public const string EVENT_NOTIFICATION_CRITERIA = "enc";
            public const string EXPIRATION_COUNTER = "exc";
            public const string NOTIFICATION_URI = "nu";
            public const string GROUP_ID = "gpi";
            public const string NOTIFICATION_FORWARDING_URI = "nfu";
            public const string BATCH_NOTIFY = "bn";
            public const string RATE_LIMIT = "rl";
            public const string PRE_SUBSCRIPTION_NOTIFY = "psn";
            public const string PENDING_NOTIFICATION = "pn";
            public const string NOTIFICATION_STORAGE_PRIORITY = "nsp";
            public const string LATEST_NOTIFY = "ln";
            public const string NOTIFICATION_CONTENT_TYPE = "nct";
            public const string NOTIFICATION_EVENT_CAT = "nec";
            public const string SUBSCRIBER_URI = "su";
            public const string VERSION = "vr";
            public const string URL = "url";
            public const string URI = "uri";
            public const string UPDATE = "ud";
            public const string UPDATE_STATUS = "uds";
            public const string INSTALL = "in";
            public const string UNINSTALL = "un";
            public const string INSTALL_STATUS = "ins";
            public const string ACTIVATE = "act";
            public const string DEACTIVATE = "dea";
            public const string ACTIVE_STATUS = "acts";
            public const string MEM_AVAILABLE = "mma";
            public const string MEM_TOTAL = "mmt";
            public const string AREA_NWK_TYPE = "ant";
            public const string LIST_OF_DEVICES = "ldv";
            public const string DEV_ID = "dvd";
            public const string DEV_TYPE = "dvt";
            public const string AREA_NWK_ID = "awi";
            public const string SLEEP_INTERVAL = "sli";
            public const string SLEEP_DURATION = "sld";
            public const string LIST_OF_NEIGHBORS = "lnh";
            public const string BATTERY_LEVEL = "btl";
            public const string BATTERY_STATUS = "bts";
            public const string DEVICE_LABEL = "dlb";
            public const string MANUFACTURER = "man";
            public const string MODEL = "mod";
            public const string DEVICE_TYPE = "dty";
            public const string FW_VERSION = "fwv";
            public const string SW_VERSION = "swv";
            public const string HW_VERSION = "hwv";
            public const string CAPABILITY_NAME = "can";
            public const string ATTACHED = "att";
            public const string CAPABILITY_ACTION_STATUS = "cas";
            public const string ENABLE = "ena";
            public const string DISABLE = "dis";
            public const string CURRENT_STATE = "cus";
            public const string REBOOT = "rbo";
            public const string FACTORY_RESET = "far";
            public const string LOG_TYPE_ID = "lgt";
            public const string LOG_DATA = "lgd";
            public const string LOG_ACTION_STATUS = "lgs";
            public const string LOG_STATUS = "lgst";
            public const string LOG_START = "lga";
            public const string LOG_STOP = "lgo";
            public const string FIRMWARE_NAME = "fwn";
            public const string SOFTWARE_NAME = "swn";
            public const string CMDH_POLICY_NAME = "cpn";
            public const string MGMT_LINK = "cmlk";
            public const string ACTIVE_CMDH_POLICY_LINK = "acmlk";
            public const string ORDER = "od";
            public const string DEF_EC_VALUE = "dev";
            public const string REQUEST_ORIGIN = "ror";
            public const string REQUEST_CONTEXT = "rct";
            public const string REQUEST_CONTEXT_NOTIFICATION = "rctn";
            public const string REQUEST_CHARACTERISTICS = "rch";
            public const string APPLICABLE_EVENT_CATEGORIES = "aecs";
            public const string APPLICABLE_EVENT_CATEGORY = "aec";
            public const string DEFAULT_REQUEST_EXP_TIME = "dqet";
            public const string DEFAULT_RESULT_EXP_TIME = "dset";
            public const string DEFAULT_OP_EXEC_TIME = "doet";
            public const string DEFAULT_RESP_PERSISTENCE = "drp";
            public const string DEFAULT_DEL_AGGREGATION = "dda";
            public const string LIMITS_EVENT_CATEGORY = "lec";
            public const string LIMITS_REQUEST_EXP_TIME = "lqet";
            public const string LIMITS_RESULT_EXP_TIME = "lset";
            public const string LIMITS_OP_EXEC_TIME = "loet";
            public const string LIMITS_RESP_PERSISTENCE = "lrp";
            public const string LIMITS_DEL_AGGREGATION = "lda";
            public const string TARGET_NETWORK = "ttn";
            public const string MIN_REQ_VOLUME = "mrv";
            public const string BACK_OFF_PARAMETERS = "bop";
            public const string OTHER_CONDITIONS = "ohc";
            public const string MAX_BUFFER_SIZE = "mbfs";
            public const string STORAGE_PRIORITY = "sgp";
            public const string APPLICABLE_CRED_IDS = "apci";
            public const string ALLOWED_APP_IDS = "aai";
            public const string ALLOWED_AES = "aae";
            public const string ACCESS_CONTROL_POLICY = "acp";
            public const string ACCESS_CONTROL_POLICY_ANNC = "acpA";
            public const string AE = "ae";
            public const string AE_ANNC = "aeA";
            public const string CONTAINER = "cnt";
            public const string CONTAINER_ANNC = "cntA";
            public const string LATEST = "la";
            public const string OLDEST = "ol";
            public const string CONTENT_INSTANCE = "cin";
            public const string CONTENT_INSTANCE_ANNC = "cinA";
            public const string DELIVERY = "dlv";
            public const string EVENT_CONFIG = "evcg";
            public const string EXEC_INSTANCE = "exin";
            public const string FAN_OUT_POINT = "fopt";
            public const string GROUP = "grp";
            public const string GROUP_ANNC = "grpA";
            public const string LOCATION_POLICY = "lcp";
            public const string LOCATION_POLICY_ANNC = "lcpA";
            public const string M2M_SERVICE_SUBSCRIPTION_PROFILE = "mssp";
            public const string MGMT_CMD = "mgc";
            public const string MGMT_OBJ = "mgo";
            public const string MGMT_OBJ_ANNC = "mgoA";
            public const string NODE = "nod";
            public const string NODE_ANNC = "nodA";
            public const string POLLING_CHANNEL = "pch";
            public const string POLLING_CHANNEL_URI = "pcu";
            public const string REMOTE_CSE = "csr";
            public const string REMOTE_CSE_ANNC = "csrA";
            public const string REQUEST = "req";
            public const string SCHEDULE = "sch";
            public const string SCHEDULE_ANNC = "schA";
            public const string SERVICE_SUBSCRIBED_APP_RULE = "asar";
            public const string SERVICE_SUBSCRIBED_NODE = "svsn";
            public const string STATS_COLLECT = "stcl";
            public const string STATS_CONFIG = "stcg";
            public const string SUBSCRIPTION = "sub";
            public const string FIRMWARE = "fwr";
            public const string FIRMWARE_ANNC = "fwrA";
            public const string SOFTWARE = "swr";
            public const string SOFTWARE_ANNC = "swrA";
            public const string MEMORY = "mem";
            public const string MEMORY_ANNC = "memA";
            public const string AREA_NWK_INFO = "ani";
            public const string AREA_NWK_INFO_ANNC = "aniA";
            public const string AREA_NWK_DEVICE_INFO = "andi";
            public const string AREA_NWK_DEVICE_INFO_ANNC = "andiA";
            public const string BATTERY = "bat";
            public const string BATTERY_ANNC = "batA";
            public const string DEVICE_INFO = "dvi";
            public const string DEVICE_INFO_ANNC = "dviA";
            public const string DEVICE_CAPABILITY = "dvc";
            public const string DEVICE_CAPABILITY_ANNC = "dvcA";
            public const string REBOOT_ANNC = "rboA";
            public const string EVENT_LOG = "evl";
            public const string EVENT_LOG_ANNC = "evlA";
            public const string CMDH_POLICY = "cmp";
            public const string ACTIVE_CMDH_POLICY = "acmp";
            public const string CMDH_DEFAULTS = "cmdf";
            public const string CMDH_DEF_EC_VALUE = "cmdv";
            public const string CMDH_EC_DEF_PARAM_VALUES = "cmpv";
            public const string CMDH_LIMITS = "cml";
            public const string CMDH_NETWORK_ACCESS_RULES = "cmnr";
            public const string CMDH_NW_ACCESS_RULE = "cmwr";
            public const string CMDH_BUFFER = "cmbf";
            public const string CREATED_BEFORE = "crb";
            public const string CREATED_AFTER = "cra";
            public const string MODIFIED_SINCE = "ms";
            public const string UNMODIFIED_SINCE = "us";
            public const string STATE_TAG_SMALLER = "sts";
            public const string STATE_TAG_BIGGER = "stb";
            public const string EXPIRE_BEFORE = "exb";
            public const string EXPIRE_AFTER = "exa";
            public const string SIZE_ABOVE = "sza";
            public const string SIZE_BELOW = "szb";
            public const string CONTENT_TYPE = "cty";
            public const string LIMIT = "lim";
            public const string ATTRIBUTE = "atr";
            public const string RESOURCE_STATUS = "rss";
            public const string NOTIFICATION_EVENT_TYPE = "net";
            public const string OPERATION_MONITOR = "om";
            public const string REPRESENTATION = "rep";
            public const string FILTER_USAGE = "fu";
            public const string EVENT_CAT_TYPE = "ect";
            public const string EVENT_CAT_NO = "ecn";
            public const string NUMBER = "num";
            public const string DURATION = "dur";
            public const string NOTIFICATION = "sgn";
            public const string NOTIFICATION_EVENT = "nev";
            public const string VERIFICATION_REQUEST = "vrq";
            public const string SUBSCRIPTION_DELETION = "sud";
            public const string SUBSCRIPTION_REFERENCE = "sur";
            public const string ACCESS_ID = "aci";
            public const string MSISDN = "msd";
            public const string ACTION = "acn";
            public const string STATUS = "sus";
            public const string CHILD_RESOURCE = "ch";
            public const string ACCESS_CONTROL_RULE = "acr";
            public const string ACCESS_CONTROL_ORIGINATORS = "acor";
            public const string ACCESS_CONTROL_OPERATIONS = "acop";
            public const string ACCESS_CONTROL_CONTEXTS = "acco";
            public const string ACCESS_CONTROL_WINDOW = "actw";
            public const string ACCESS_CONTROL_IP_ADDRESSES = "acip";
            public const string IPV4_ADDRESSES = "ipv4";
            public const string IPV6_ADDRESSES = "ipv6";
            public const string ACCESS_CONTROL_LOCATION_REGION = "aclr";
            public const string COUNTRY_CODE = "accc";
            public const string CIRC_REGION = "accr";
            public const string VALUE = "val";
            public const string TYPE = "typ";
            public const string MAX_NR_OF_NOTIFY = "mnn";
            public const string TIME_WINDOW = "tww";
            public const string SCHEDULE_ENTRY = "sce";
            public const string AGGREGATED_NOTIFICATION = "agn";
            public const string ATTRIBUTE_LIST = "atrl";
            public const string AGGREGATED_RESPONSE = "agr";
            public const string RESOURCE = "rce";
            public const string URI_LIST = "uril";
            public const string ANY_ARG = "any";
            public const string FILE_TYPE = "ftyp";
            public const string USERNAME = "unm";
            public const string PASSWORD = "pwd";
            public const string FILESIZE = "fsi";
            public const string TARGET_FILE = "tgf";
            public const string DELAY_SECONDS = "dss";
            public const string SUCCESS_URL = "surl";
            public const string START_TIME = "stt";
            public const string COMPLETE_TIME = "cpt";
            public const string UUID = "uuid";
            public const string EXECUTION_ENV_REF = "eer";
            public const string RESET = "rst";
            public const string UPLOAD = "uld";
            public const string DOWNLOAD = "dld";
            public const string SOFTWARE_INSTALL = "swin";
            public const string SOFTWARE_UPDATE = "swup";
            public const string SOFTWARE_UNINSTALL = "swun";
            public const string TRACING_OPTION = "tcop";
            public const string TRACING_INFO = "tcin";
            public const string RESPONSE_TYPE_VALUE = "rtv";

            private static Dictionary<string, string> _longToShort = new Dictionary<string, string>();
            private static Dictionary<string, string> _shortToLong = new Dictionary<string, string>();

            private static String config;
            static Name()
            {
                config =
                             "e2eSecInfo=esi\n" +
                             "contentSerialization=csz\n" +
                             "disableRetrieval=dr\n" +
                             "contentRef=conr\n" +
                             "dynamicAuthorizationConsultationIDs=daci\n" +
                             "operation=op\n" +
                             "to=to\n" +
                             "from=fr\n" +
                             "requestIdentifier=rqi\n" +
                             "resourceType=ty\n" +
                             "name=nm\n" +
                             "primitiveContent=pc\n" +
                             "role=rol\n" +
                             "originatingTimestamp=ot\n" +
                             "requestExpirationTimestamp=rqet\n" +
                             "resultExpirationTimestamp=rset\n" +
                             "operationExecutionTime=oet\n" +
                             "responseType=rt\n" +
                             "resultPersistence=rp\n" +
                             "resultContent=rcn\n" +
                             "eventCategory=ec\n" +
                             "deliveryAggregation=da\n" +
                             "groupRequestIdentifier=gid\n" +
                             "filterCriteria=fc\n" +
                             "discoveryResultType=drt\n" +
                             "responseStatusCode=rsc\n" +
                             "requestPrimitive=rqp\n" +
                             "responsePrimitive=rsp\n" +
                             "accessControlPolicyIDs=acpi\n" +
                             "announcedAttribute=aa\n" +
                             "announceTo=at\n" +
                             "creationTime=ct\n" +
                             "expirationTime=et\n" +
                             "labels=lbl\n" +
                             "link=lnk\n" +
                             "lastModifiedTime=lt\n" +
                             "parentID=pi\n" +
                             "resourceID=ri\n" +
                             "stateTag=st\n" +
                             "resourceName=rn\n" +
                             "privileges=pv\n" +
                             "selfPrivileges=pvs\n" +
                             "appID=api\n" + //- removed
                             "aEID=aei\n" +
                             "appName=apn\n" +
                             "pointOfAccess=poa\n" +
                             "ontologyRef=or\n" +
                             "nodeLink=nl\n" +
                             "creator=cr\n" +
                             "maxNrOfInstances=mni\n" +
                             "maxByteSize=mbs\n" +
                             "maxInstanceAge=mia\n" +
                             "currentNrOfInstances=cni\n" +
                             "currentByteSize=cbs\n" +
                             "locationID=li\n" +
                             "contentInfo=cnf\n" +
                             "contentSize=cs\n" +
                             "content=con\n" +
                             "cseType=cst\n" +
                             "CSE-ID=csi\n" +
                             "supportedResourceType=srt\n" +
                             "notificationCongestionPolicy=ncp\n" +
                             "source=sr\n" +
                             "target=tg\n" +
                             "lifespan=ls\n" +
                             "eventCat=eca\n" +
                             "deliveryMetaData=dmd\n" +
                             "aggregatedRequest=arq\n" +
                             "eventID=evi\n" +
                             "eventType=evt\n" +
                             "eventStart=evs\n" +
                             "eventEnd=eve\n" +
                             "operationType=opt\n" +
                             "dataSize=ds\n" +
                             "execStatus=exs\n" +
                             "execResult=exr\n" +
                             "execDisable=exd\n" +
                             "execTarget=ext\n" +
                             "execMode=exm\n" +
                             "execFrequency=exf\n" +
                             "execDelay=exy\n" +
                             "execNumber=exn\n" +
                             "execReqArgs=exra\n" +
                             "execEnable=exe\n" +
                             "memberType=mt\n" +
                             "currentNrOfMembers=cnm\n" +
                             "maxNrOfMembers=mnm\n" +
                             "memberIDs=mid\n" +
                             "membersAccessControlPolicyIDs=macp\n" +
                             "memberTypeValidated=mtv\n" +
                             "consistencyStrategy=csy\n" +
                             "groupName=gn\n" +
                             "locationSource=los\n" +
                             "locationUpdatePeriod=lou\n" +
                             "locationTargetID=lot\n" +
                             "locationServer=lor\n" +
                             "locationContainerID=loi\n" +
                             "locationContainerName=lon\n" +
                             "locationStatus=lost\n" +
                             "serviceRoles=svr\n" +
                             "description=dc\n" +
                             "cmdType=cmt\n" +
                             "mgmtDefinition=mgd\n" +
                             "objectIDs=obis\n" +
                             "objectPaths=obps\n" +
                             "nodeID=ni\n" +
                             "hostedCSELink=hcl\n" +
                             "CSEBase=cb\n" +
                             "M2M-Ext-ID=mei\n" +
                             "Trigger-Recipient-ID=tri\n" +
                             "requestReachability=rr\n" +
                             "originator=org\n" +
                             "metaInformation=mi\n" +
                             "requestStatus=rs\n" +
                             "operationResult=ors\n" +
                             "requestID=rid\n" +
                             "scheduleElement=se\n" +
                             "deviceIdentifier=di\n" +
                             "ruleLinks=rlk\n" +
                             "statsCollectID=sci\n" +
                             "collectingEntityID=cei\n" +
                             "collectedEntityID=cdi\n" +
                             "devStatus=ss\n" +
                             "statsRuleStatus=srs\n" +
                             "statModel=sm\n" +
                             "collectPeriod=cp\n" +
                             "eventNotificationCriteria=enc\n" +
                             "expirationCounter=exc\n" +
                             "notificationURI=nu\n" +
                             "groupID=gpi\n" +
                             "notificationForwardingURI=nfu\n" +
                             "batchNotify=bn\n" +
                             "rateLimit=rl\n" +
                             "preSubscriptionNotify=psn\n" +
                             "pendingNotification=pn\n" +
                             "notificationStoragePriority=nsp\n" +
                             "latestNotify=ln\n" +
                             "notificationContentType=nct\n" +
                             "notificationEventCat=nec\n" +
                             "subscriberURI=su\n" +
                             "version=vr\n" +
                             "URL=url\n" +
                             "URI=uri\n" +
                             "update=ud\n" +
                             "updateStatus=uds\n" +
                             "install=in\n" +
                             "uninstall=un\n" +
                             "installStatus=ins\n" +
                             "activate=act\n" +
                             "deactivate=dea\n" +
                             "activeStatus=acts\n" +
                             "memAvailable=mma\n" +
                             "memTotal=mmt\n" +
                             "areaNwkType=ant\n" +
                             "listOfDevices=ldv\n" +
                             "devID=dvd\n" +
                             "devType=dvt\n" +
                             "areaNwkId=awi\n" +
                             "sleepInterval=sli\n" +
                             "sleepDuration=sld\n" +
                             "listOfNeighbors=lnh\n" +
                             "batteryLevel=btl\n" +
                             "batteryStatus=bts\n" +
                             "deviceLabel=dlb\n" +
                             "manufacturer=man\n" +
                             "model=mod\n" +
                             "deviceType=dty\n" +
                             "fwVersion=fwv\n" +
                             "swVersion=swv\n" +
                             "hwVersion=hwv\n" +
                             "capabilityName=can\n" +
                             "attached=att\n" +
                             "capabilityActionStatus=cas\n" +
                             "enable=ena\n" +
                             "disable=dis\n" +
                             "currentState=cus\n" +
                             "reboot=rbo\n" +
                             "factoryReset=far\n" +
                             "logTypeId=lgt\n" +
                             "logData=lgd\n" +
                             "logActionStatus=lgs\n" +
                             "logStatus=lgst\n" +
                             "logStart=lga\n" +
                             "logStop=lgo\n" +
                             "firmwareName=fwn\n" +
                             "softwareName=swn\n" +
                             "cmdhPolicyName=cpn\n" +
                             "mgmtLink=cmlk\n" +
                             "activeCmdhPolicyLink=acmlk\n" +
                             "order=od\n" +
                             "defEcValue=dev\n" +
                             "requestOrigin=ror\n" +
                             "requestContext=rct\n" +
                             "requestContextNotification=rctn\n" +
                             "requestCharacteristics=rch\n" +
                             "applicableEventCategories=aecs\n" +
                             "applicableEventCategory=aec\n" +
                             "defaultRequestExpTime=dqet\n" +
                             "defaultResultExpTime=dset\n" +
                             "defaultOpExecTime=doet\n" +
                             "defaultRespPersistence=drp\n" +
                             "defaultDelAggregation=dda\n" +
                             "limitsEventCategory=lec\n" +
                             "limitsRequestExpTime=lqet\n" +
                             "limitsResultExpTime=lset\n" +
                             "limitsOpExecTime=loet\n" +
                             "limitsRespPersistence=lrp\n" +
                             "limitsDelAggregation=lda\n" +
                             "targetNetwork=ttn\n" +
                             "minReqVolume=mrv\n" +
                             "backOffParameters=bop\n" +
                             "otherConditions=ohc\n" +
                             "maxBufferSize=mbfs\n" +
                             "storagePriority=sgp\n" +
                             "applicableCredIDs=apci\n" +
                             "allowedApp-IDs=aai\n" +
                             "allowedAEs=aae\n" +
                             "accessControlPolicy=acp\n" +
                             "accessControlPolicyAnnc=acpA\n" +
                             //"AE=ae\n" +
                             "applicationEntity=ae\n" +
                             "applicationEntityAnnouncable=aeA\n" +
                             //"AEAnnc=aeA\n" +
                             "container=cnt\n" +
                             "containerAnnc=cntA\n" +
                             "latest=la\n" +
                             "oldest=ol\n" +
                             "contentInstance=cin\n" +
                             "contentInstanceAnnc=cinA\n" +
                             "delivery=dlv\n" +
                             "eventConfig=evcg\n" +
                             "execInstance=exin\n" +
                             "fanOutPoint=fopt\n" +
                             "group=grp\n" +
                             "groupAnnc=grpA\n" +
                             "locationPolicy=lcp\n" +
                             "locationPolicyAnnc=lcpA\n" +
                             "m2mServiceSubscriptionProfile=mssp\n" +
                             "mgmtCmd=mgc\n" +
                             "mgmtObj=mgo\n" +
                             "mgmtObjAnnc=mgoA\n" +
                             "node=nod\n" +
                             "nodeAnnc=nodA\n" +
                             "pollingChannel=pch\n" +
                             "pollingChannelURI=pcu\n" +
                             "remoteCSE=csr\n" +
                             "remoteCSEAnnc=csrA\n" +
                             "request=req\n" +
                             "schedule=sch\n" +
                             "scheduleAnnc=schA\n" +
                             "serviceSubscribedAppRule=asar\n" +
                             "serviceSubscribedNode=svsn\n" +
                             "statsCollect=stcl\n" +
                             "statsConfig=stcg\n" +
                             "subscription=sub\n" +
                             "firmware=fwr\n" +
                             "firmwareAnnc=fwrA\n" +
                             "software=swr\n" +
                             "softwareAnnc=swrA\n" +
                             "memory=mem\n" +
                             "memoryAnnc=memA\n" +
                             "areaNwkInfo=ani\n" +
                             "areaNwkInfoAnnc=aniA\n" +
                             "areaNwkDeviceInfo=andi\n" +
                             "areaNwkDeviceInfoAnnc=andiA\n" +
                             "battery=bat\n" +
                             "batteryAnnc=batA\n" +
                             "deviceInfo=dvi\n" +
                             "deviceInfoAnnc=dviA\n" +
                             "deviceCapability=dvc\n" +
                             "deviceCapabilityAnnc=dvcA\n" +
                             "rebootAnnc=rboA\n" +
                             "eventLog=evl\n" +
                             "eventLogAnnc=evlA\n" +
                             "cmdhPolicy=cmp\n" +
                             "activeCmdhPolicy=acmp\n" +
                             "cmdhDefaults=cmdf\n" +
                             "cmdhDefEcValue=cmdv\n" +
                             "cmdhEcDefParamValues=cmpv\n" +
                             "cmdhLimits=cml\n" +
                             "cmdhNetworkAccessRules=cmnr\n" +
                             "cmdhNwAccessRule=cmwr\n" +
                             "cmdhBuffer=cmbf\n" +
                             "createdBefore=crb\n" +
                             "createdAfter=cra\n" +
                             "modifiedSince=ms\n" +
                             "unmodifiedSince=us\n" +
                             "stateTagSmaller=sts\n" +
                             "stateTagBigger=stb\n" +
                             "expireBefore=exb\n" +
                             "expireAfter=exa\n" +
                             "sizeAbove=sza\n" +
                             "sizeBelow=szb\n" +
                             "contentType=cty\n" +
                             "limit=lim\n" +
                             "attribute=atr\n" +
                             "resourceStatus=rss\n" +
                             "notificationEventType=net\n" +
                             "operationMonitor=om\n" +
                             "representation=rep\n" +
                             "filterUsage=fu\n" +
                             "eventCatType=ect\n" +
                             "eventCatNo=ecn\n" +
                             "number=num\n" +
                             "duration=dur\n" +
                             "notification=sgn\n" +
                             "notificationEvent=nev\n" +
                             "verificationRequest=vrq\n" +
                             "subscriptionDeletion=sud\n" +
                             "subscriptionReference=sur\n" +
                             "accessId=aci\n" +
                             "MSISDN=msd\n" +
                             "action=acn\n" +
                             "status=sus\n" +
                             "childResource=ch\n" +
                             "accessControlRule=acr\n" +
                             "accessControlOriginators=acor\n" +
                             "accessControlOperations=acop\n" +
                             "accessControlContexts=acco\n" +
                             "accessControlWindow=actw\n" +
                             "accessControlIpAddresses=acip\n" +
                             "ipv4Addresses=ipv4\n" +
                             "ipv6Addresses=ipv6\n" +
                             "accessControlLocationRegion=aclr\n" +
                             "countryCode=accc\n" +
                             "circRegion=accr\n" +
                             "value=val\n" +
                             "type=typ\n" +
                             "maxNrOfNotify=mnn\n" +
                             "timeWindow=tww\n" +
                             "scheduleEntry=sce\n" +
                             "aggregatedNotification=agn\n" +
                             "attributeList=atrl\n" +
                             "aggregatedResponse=agr\n" +
                             "resource=rce\n" +
                             "URIList=uril\n" +
                             "anyArg=any\n" +
                             "fileType=ftyp\n" +
                             "username=unm\n" +
                             "password=pwd\n" +
                             "filesize=fsi\n" +
                             "targetFile=tgf\n" +
                             "delaySeconds=dss\n" +
                             "successURL=surl\n" +
                             "startTime=stt\n" +
                             "completeTime=cpt\n" +
                             "UUID=uuid\n" +
                             "executionEnvRef=eer\n" +
                             "reset=rst\n" +
                             "upload=uld\n" +
                             "download=dld\n" +
                             "softwareInstall=swin\n" +
                             "softwareUpdate=swup\n" +
                             "softwareUninstall=swun\n" +
                             "tracingOption=tcop\n" +
                             "tracingInfo=tcin\n" +
                             "responseTypeValue=rtv";
                String[] combos = config.Split('\n');
                for (int i = 0; i < combos.Length; i++)
                {
                    String[] splitCombo = combos[i].Split('=');
                    _longToShort.Add(splitCombo[0], splitCombo[1]);
                    _shortToLong.Add(splitCombo[1], splitCombo[0]);
                }
            }
            public static string LongToShort(string longName)
            {
                return _longToShort.ContainsKey(longName) ? _longToShort[longName] : string.Empty;
            }

            public static string ShortTolong(string shortName)
            {
                return _shortToLong.ContainsKey(shortName) ? _shortToLong[shortName] : string.Empty;
            }
        }

        public static class Path
        {
            public static string toToPathMapping(string to)
            {
                if (string.IsNullOrEmpty(to))
                {
                    return string.Empty;
                }
                else if (to.StartsWith("//"))
                {
                    return "/_/" + to.Substring(2);
                }
                else if (to.StartsWith("/"))
                {
                    return "/~" + to;
                }
                else
                {
                    return "/" + to;
                }
            }

            public static string pathToToMapping(string path)
            {
                if (string.IsNullOrEmpty(path))
                {
                    return string.Empty;
                }
                else if (path.StartsWith("/_/"))
                {
                    return "//" + path.Substring(3);
                }
                else if (path.StartsWith("/~/"))
                    return path.Substring(2);
                else
                {
                    return path.Substring(1);
                }
            }
        }
        public class Time
        {
            private const String FORMAT = "yyyyMMdd:HHmmss";
            private const String SEPARATOR = "T";
            private const String FAKE_SEPARATOR = ":";

            private DateTime localDateTime;
            private long relativeTime;
            private bool isRelativetime;

            public Time(string localDateTime)
            {
                this.localDateTime = DateTime.Parse(localDateTime);
                isRelativetime = false;
            }

            public Time(DateTime localDateTime)
            {
                this.localDateTime = localDateTime;
                isRelativetime = false;
            }

            public Time(long relativeTime)
            {
                this.relativeTime = relativeTime;
                isRelativetime = true;
            }

            public static string Now
            {
                get
                {
                    DateTime localDateTime = DateTime.Now;
                    string time = localDateTime.ToString(FORMAT);
                    return time.Replace(FAKE_SEPARATOR, SEPARATOR);
                }
            }

            public static DateTime Parse(string timeStamp)
            {
                try
                {
                    timeStamp = timeStamp.Replace(SEPARATOR, FAKE_SEPARATOR);
                    return DateTime.ParseExact(timeStamp, FORMAT, CultureInfo.InvariantCulture);
                }
                catch (Exception e)
                {
                    throw new Exception("TimeStamp incorrect: '" + timeStamp + "' Exception-Message: " + e.ToString());
                }
            }

            public static string Format(DateTime localDateTime)
            {
                string time = localDateTime.ToString(FORMAT);
                return time.Replace(FAKE_SEPARATOR, SEPARATOR);
            }

            public override string ToString()
            {
                return isRelativetime ? relativeTime.ToString() : Format(localDateTime);
            }
        }
    }
}
