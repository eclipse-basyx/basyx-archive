#ifndef OBJECTTYPE_ATTRIBUTES_H
#define OBJECTTYPE_ATTRIBUTES_H

#include <string>
#include <BaSyx/opcua/client/open62541Client.h>

namespace basyx
{
    namespace opcua
    {
        class ObjectTypeAttributes
        {
        public:
            ObjectTypeAttributes() = default;

            ObjectTypeAttributes(const std::string& t_displayName,
                const std::string& t_description,
                const std::string& t_locale);

            virtual ~ObjectTypeAttributes();

            ObjectTypeAttributes& operator=(ObjectTypeAttributes&& t_other) noexcept;

            ObjectTypeAttributes& operator=(const ObjectTypeAttributes& t_other);

            UA_ObjectTypeAttributes& getUA_ObjectTypeAttributes() const;

        private:
            UA_ObjectTypeAttributes* m_attributes = nullptr;
        };
    }
}

#endif