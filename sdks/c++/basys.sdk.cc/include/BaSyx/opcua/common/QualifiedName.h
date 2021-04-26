#ifndef QUALIFIED_NAME_H
#define QUALIFIED_NAME_H

#include <string>
#include <BaSyx/opcua/client/open62541Client.h>

namespace basyx
{
    namespace opcua
    {
        class QualifiedName
        {
        public:
            QualifiedName() : QualifiedName(0, "") {}

            QualifiedName(uint16_t t_nameSpaceIndex, const std::string& t_text);

            QualifiedName(const UA_QualifiedName& t_node);

            ~QualifiedName();

            QualifiedName& operator=(QualifiedName&& t_other) noexcept;

            QualifiedName& operator=(const QualifiedName& t_other);

            bool operator ==(QualifiedName& t_other);

            bool operator !=(QualifiedName& t_other);

            UA_QualifiedName& getUA_QualifiedName() const
            {
                return *m_name;
            }

            std::string toString() const;

        private:
            UA_QualifiedName* m_name = nullptr;
        };
    }
}



#endif