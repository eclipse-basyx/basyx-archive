#include "gtest/gtest.h"
#include <BaSyx/opcua/common/NodeCommon.h>

namespace basyx
{
    namespace tests
    {
        namespace opcua
        {
            class OPCUAClientServicesTest : public ::testing::Test
            {
                std::unique_ptr<basyx::opcua::Server> m_server;
            public:
                virtual void SetUp()
                {
                    using namespace basyx::opcua;

                    m_server = util::make_unique<basyx::opcua::Server>(7000, shared::Namespaces::BASYX_NS_URI);

                    m_server->initialize();

                    m_server->runInBackground();

                    /* Wait until the server is Up*/
                    while (!m_server->isServerUp());
                }

                virtual void TearDown()
                {
                    m_server->abort();
                }
            };

            TEST_F(OPCUAClientServicesTest, getChildReferences)
            {
                using namespace basyx::opcua;

                Client client("opc.tcp://localhost:7000", shared::Namespaces::BASYX_NS_URI);

                client.connect();

                Services<Client> services(client);


                auto children = services.getChildReferences(
                    NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, 85),
                    NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, UA_NS0ID_ORGANIZES)
                );

                ASSERT_EQ(children.size(), 2);


                auto iter = std::find_if(children.begin(), children.end(), [](const NodeId& node){
                    return (NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, 23470) == node);
                });

                ASSERT_TRUE(iter != children.end());

                iter = std::find_if(children.begin(), children.end(), [](const NodeId& node){
                    return (NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, 23470) == node);
                });

                ASSERT_TRUE(iter != children.end());

                children.clear();

                children = services.getAllChildReferences(NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, 85));

                ASSERT_EQ(children.size(), 3);
                
                iter = std::find_if(children.begin(), children.end(), [](const NodeId& node){
                    return (NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, 61) == node);
                });

                ASSERT_TRUE(iter != children.end());

                iter = std::find_if(children.begin(), children.end(), [](const NodeId& node){
                    return (NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, 23470) == node);
                });

                ASSERT_TRUE(iter != children.end());
                
                iter = std::find_if(children.begin(), children.end(), [](const NodeId& node){
                    return (NodeId(UA_NodeIdType::UA_NODEIDTYPE_NUMERIC, 2253) == node);
                });

                ASSERT_TRUE(iter != children.end());

                client.disconnect();
            }
        }
    }
}