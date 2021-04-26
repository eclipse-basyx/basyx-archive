#ifndef EXPANDED_NODE_ID_H
#define EXPANDED_NODE_ID_H

#include <string>
#include <BaSyx/opcua/client/open62541Client.h>

namespace basyx
{
    namespace opcua
    {
        class ExpandedNodeId
        {
        public:

            ExpandedNodeId();

            ExpandedNodeId(const UA_ExpandedNodeId& t_node);

            ExpandedNodeId(ExpandedNodeId&& t_other) noexcept;

            ExpandedNodeId(UA_NodeIdType t_class, uint16_t t_namespaceIndex, int32_t t_id);

            ExpandedNodeId(UA_NodeIdType t_class, uint16_t t_namespaceIndex, const std::string& t_id);

            ExpandedNodeId& operator=(ExpandedNodeId&& t_other) noexcept;

            ExpandedNodeId& operator=(const ExpandedNodeId& t_other);

            bool operator ==(ExpandedNodeId& t_other);

            bool operator !=(ExpandedNodeId& t_other);

            virtual ~ExpandedNodeId();

            std::string toString();

            UA_ExpandedNodeId& getUANode() const
            {
                return *m_node;
            }

            bool isNull();

            inline void release();

            static ExpandedNodeId nullNode();

            static ExpandedNodeId numeric(uint16_t t_namepsaceIdx, uint32_t t_id);

            static ExpandedNodeId numeric(uint32_t t_id);

            static ExpandedNodeId string(uint16_t t_namepsaceIdx, const std::string& t_id);

            static ExpandedNodeId string(const std::string& t_id);

        private:
            UA_ExpandedNodeId* m_node = nullptr;
        };
    }
}
#endif