#include <BaSyx/opcua/common/ObjectAttributes.h>

namespace basyx
{
    namespace opcua
    {
        ObjectAttributes::ObjectAttributes(const std::string& t_displayName,
            const std::string& t_description,
            const std::string& t_locale)
        {
            m_attributes = UA_ObjectAttributes_new();

            *m_attributes = UA_ObjectAttributes_default;

            m_attributes->description = UA_LOCALIZEDTEXT_ALLOC(t_locale.c_str(), t_description.c_str());
            m_attributes->displayName = UA_LOCALIZEDTEXT_ALLOC(t_locale.c_str(), t_displayName.c_str());
        }

        ObjectAttributes::~ObjectAttributes()
        {
            UA_ObjectAttributes_delete(m_attributes);
        }

        ObjectAttributes & ObjectAttributes::operator=(ObjectAttributes && t_other) noexcept
        {
            if (m_attributes != nullptr)
            {
                UA_ObjectAttributes_delete(m_attributes);
            }

            *m_attributes = UA_ObjectAttributes_default;

            UA_ObjectAttributes_copy(&t_other.getUA_ObjectAttributes(), m_attributes);

            return *this;
        }

        ObjectAttributes & ObjectAttributes::operator=(const ObjectAttributes & t_other)
        {
            if (m_attributes != nullptr)
            {
                UA_ObjectAttributes_delete(m_attributes);
            }

            *m_attributes = UA_ObjectAttributes_default;

            UA_ObjectAttributes_copy(&t_other.getUA_ObjectAttributes(), m_attributes);

            return *this;
        }

        UA_ObjectAttributes & ObjectAttributes::getUA_ObjectAttributes() const
        {
            return *m_attributes;
        }
    }
}
