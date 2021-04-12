#ifndef VARIABLE_ATTRIBUTES_H
#define VARIABLE_ATTRIBUTES_H

#include <string>
#include <BaSyx/opcua/client/open62541Client.h>
#include <BaSyx/opcua/common/NodeId.h>

namespace basyx
{
    namespace opcua
    {
        class VariableAttributes
        {
        public:
            VariableAttributes() = default;

            VariableAttributes(int32_t t_typeId,
                const std::string& t_displayName,
                const std::string& t_description,
                const std::string& t_locale) :
                VariableAttributes(NodeId(UA_TYPES[t_typeId].typeId),
                    t_displayName,
                    t_description,
                    t_locale) {}

            VariableAttributes(const NodeId &t_typeId,
                const std::string& t_displayName,
                const std::string& t_description,
                const std::string& t_locale);

            virtual ~VariableAttributes();

            VariableAttributes& operator=(VariableAttributes&& t_other) noexcept;

            VariableAttributes& operator=(const VariableAttributes& t_other);

            UA_VariableAttributes& getUA_VariableAttributes() const;

        private:
            UA_VariableAttributes* m_attributes = nullptr;
        };
    }
}

#endif