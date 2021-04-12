#ifndef UTILITIES_H
#define UTILITIES_H

#include <vector>
#include <string>
#include <sstream>  
#include <functional>
#include <regex>
#include <string>
#include <type_traits>
#include <BaSyx/opcua/client/open62541Client.h>

namespace basyx
{
    namespace opcua
    {
        namespace shared
        {
            struct Namespaces
            {
                static constexpr char BASYX_NS_URI[] = "url://basyx";
                static constexpr char OPCUA_NS_URI[] = "http://opcfoundation.org/UA/";
            };

            template<class TYPE>
            constexpr int UATypeId(TYPE t_type)
            {
                    return  (typeid(t_type) == typeid(UA_Boolean)) ? UA_TYPES_BOOLEAN : (
                            (typeid(t_type) == typeid(UA_SByte)    ? UA_TYPES_SBYTE : (
                            (typeid(t_type) == typeid(UA_Byte)     ? UA_TYPES_BYTE : (
                            (typeid(t_type) == typeid(UA_Int16)    ? UA_TYPES_INT16 : (
                            (typeid(t_type) == typeid(UA_UInt16)   ? UA_TYPES_UINT16 : (
                            (typeid(t_type) == typeid(UA_Int32)    ? UA_TYPES_INT32 : (
                            (typeid(t_type) == typeid(UA_UInt32)   ? UA_TYPES_UINT32 : (
                            (typeid(t_type) == typeid(UA_Int64)    ? UA_TYPES_INT64 : (
                            (typeid(t_type) == typeid(UA_UInt64)   ? UA_TYPES_UINT64 : (
                            (typeid(t_type) == typeid(UA_Float)    ? UA_TYPES_FLOAT : (
                            (typeid(t_type) == typeid(UA_Double)   ? UA_TYPES_DOUBLE : (
                            (typeid(t_type) == typeid(UA_String)   ? UA_TYPES_STRING : (
                            (typeid(t_type) == typeid(std::string) ? UA_TYPES_STRING : 0
                             ))))))))))))))))))))))));
            }

            namespace string
            {
                std::vector<std::string>  split(const std::string& t_text, char t_delimiter);

                std::vector<std::string> splitBySlash(const std::string& t_text);

                std::vector<std::string> splitByColon(const std::string& t_text);

                bool startsWith(const std::string& t_text, const std::string& t_pattern);

                bool endsWith(const std::string& t_text, const std::string& t_pattern);

                void replaceAll(std::string& t_text, const std::string& t_pattern, const std::string& t_replacePattern);

                void replaceAllWithWhiteSpace(std::string& t_text, const std::string& t_pattern);

                bool contains(const std::string& t_text, const std::string& t_pattern);

                void chop(std::string& t_text);

                std::string UAStringToStdString(UA_String* t_uaString);

                std::string UAStringToStdString(const UA_String& t_uaString);

                UA_String UAStringFromStdString(const std::string& t_stdString);

                UA_LocalizedText LocalizedText(const std::string& t_locale, const std::string& t_text);

                std::string getInstanceName(const std::string& t_browseText);

                std::string createPropertyName(const std::string& t_propertyName, const std::string& t_instanceName);

            }

            namespace diag
            {
                std::string getErrorString(UA_StatusCode t_scode);

                bool isStatusBad(UA_StatusCode t_status);
            }
        }
    }
}
#endif