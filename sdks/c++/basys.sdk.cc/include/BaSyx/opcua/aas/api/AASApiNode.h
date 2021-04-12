#ifndef AAS_API_NODE_H
#define AAS_API_NODE_H

#include <BaSyx/log/log.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/aas/api/ApiResponse.h>
#include <BaSyx/opcua/aas/api/AASApiNodeIdHelpers.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE = basyx::opcua::Client>
            class AASApiNode
            {
            public:
                AASApiNode(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string& t_loggerName) :
                    m_connector(t_connector),
                    m_rootNode(t_rootNode.getUANode()),
                    m_logger(t_loggerName),
                    m_services(Services<CONNECTOR_TYPE>(t_connector)) {}

                virtual ~AASApiNode() = default;

                basyx::log& getLogger()
                {
                    return m_logger;
                }

                NodeId& getRootNode()
                {
                    return m_rootNode;
                }

                CONNECTOR_TYPE& getConnector()
                {
                    return m_services.getConnector();
                }

                Services<CONNECTOR_TYPE>& getServices()
                {
                    return m_services;
                }

            private:
                CONNECTOR_TYPE& m_connector;
                basyx::log m_logger;
                NodeId m_rootNode;
                Services<CONNECTOR_TYPE> m_services;
            };
        }
    }
}
#endif