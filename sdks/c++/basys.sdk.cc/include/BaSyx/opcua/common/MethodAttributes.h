#ifndef METHOD_ATTRIBUTES_H
#define METHOD_ATTRIBUTES_H

#include <string>
#include <BaSyx/opcua/client/open62541Client.h>

namespace basyx
{
    namespace opcua
    {
        class MethodAttributes
        {
        public:
            MethodAttributes() = default;

            MethodAttributes(const std::string& t_displayName,
                const std::string& t_description,
                const std::string& t_locale);

            virtual ~MethodAttributes();

            MethodAttributes& operator=(MethodAttributes&& t_other) noexcept;

            MethodAttributes& operator=(const MethodAttributes& t_other);

            UA_MethodAttributes& getUA_MethodAttributes() const;

        private:
            UA_MethodAttributes* m_attributes = nullptr;
        };
    }
}

#endif