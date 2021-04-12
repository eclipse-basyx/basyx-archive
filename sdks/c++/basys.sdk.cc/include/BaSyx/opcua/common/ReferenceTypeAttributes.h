#ifndef REFERENCETYPE_ATTRIBUTES_H
#define REFERENCETYPE_ATTRIBUTES_H

#include <string>
#include <BaSyx/opcua/client/open62541Client.h>

namespace basyx
{
    namespace opcua
    {
        class ReferenceTypeAttributes
        {
        public:
            ReferenceTypeAttributes() = default;

            ReferenceTypeAttributes(const std::string& t_displayName,
                const std::string& t_description,
                const std::string& t_locale);

            virtual ~ReferenceTypeAttributes();

            ReferenceTypeAttributes& operator=(ReferenceTypeAttributes&& t_other) noexcept;

            ReferenceTypeAttributes& operator=(const ReferenceTypeAttributes& t_other);

            UA_ReferenceTypeAttributes& getUA_ReferenceTypeAttributes() const;

        private:
            UA_ReferenceTypeAttributes* m_attributes = nullptr;
        };
    }
}
#endif