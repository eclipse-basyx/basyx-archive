#include <BaSyx/opcua/common/ReferenceTypeAttributes.h>

namespace basyx
{
    namespace opcua
    {
        ReferenceTypeAttributes::ReferenceTypeAttributes(const std::string& t_displayName,
            const std::string& t_description,
            const std::string& t_locale)
        {
            m_attributes = UA_ReferenceTypeAttributes_new();

            *m_attributes = UA_ReferenceTypeAttributes_default;

            m_attributes->description = UA_LOCALIZEDTEXT_ALLOC(t_locale.c_str(), t_description.c_str());
            m_attributes->displayName = UA_LOCALIZEDTEXT_ALLOC(t_locale.c_str(), t_displayName.c_str());
        }

        ReferenceTypeAttributes::~ReferenceTypeAttributes()
        {
            UA_ReferenceTypeAttributes_delete(m_attributes);
        }

        ReferenceTypeAttributes & ReferenceTypeAttributes::operator=(ReferenceTypeAttributes && t_other) noexcept
        {
            if (m_attributes != nullptr)
            {
                UA_ReferenceTypeAttributes_delete(m_attributes);
            }

            *m_attributes = UA_ReferenceTypeAttributes_default;

            UA_ReferenceTypeAttributes_copy(&t_other.getUA_ReferenceTypeAttributes(), m_attributes);

            return *this;
        }

        ReferenceTypeAttributes & ReferenceTypeAttributes::operator=(const ReferenceTypeAttributes & t_other)
        {
            if (m_attributes != nullptr)
            {
                UA_ReferenceTypeAttributes_delete(m_attributes);
            }

            *m_attributes = UA_ReferenceTypeAttributes_default;

            UA_ReferenceTypeAttributes_copy(&t_other.getUA_ReferenceTypeAttributes(), m_attributes);

            return *this;
        }

        UA_ReferenceTypeAttributes & ReferenceTypeAttributes::getUA_ReferenceTypeAttributes() const
        {
            return *m_attributes;
        }
    }
}
