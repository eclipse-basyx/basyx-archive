#ifndef AAS_API_NODEID_HELPERS_H
#define AAS_API_NODEID_HELPERS_H

#include <BaSyx/opcua/aas/metamodel/AASMetamodel.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            class AASApiNodeIdHelpers
            {
            public:
                template<typename CONNECTOR_TYPE>
                static NodeId filterNodesWithIdentfier(CONNECTOR_TYPE& t_connector,
                    const std::vector<NodeId>& t_nodes,
                    const std::string& t_identifier)
                {

                    std::string idVal;
                    std::string idTypeVal;
                    UA_StatusCode status;

                    Services<CONNECTOR_TYPE> services(t_connector);

                    for (auto& node : t_nodes)
                    {
                        auto IdNodeId = services.translateBrowsePathToNodeId(
                            metamodel::IAASIdentifiableType::getIdentificationIdPath(t_connector.getNamespaceIndexDefault(), t_connector, node),
                            node
                        );

                        status = services.readValue(IdNodeId, idVal);

                        if (status != UA_STATUSCODE_GOOD)
                        {
                            return NodeId::nullNode();
                        }

                        if (idVal == t_identifier) {
                            return NodeId(node.getUANode());
                        }
                    }
                    return NodeId::nullNode();
                }

                template<typename CONNECTOR_TYPE>
                static NodeId filterNodesWithIdShort(CONNECTOR_TYPE& t_connector,
                    const std::vector<NodeId>& t_nodes,
                    const std::string& t_idShort)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    for (auto& node : t_nodes)
                    {
                        /* Ignore the node types other than AASAssetAdminstrationShellType and AASSubmodeType */
                        if (!(services.getHasTypeDefinition(node) == metamodel::AASSubModelType::getNodeId(t_connector.getNamespaceIndexDefault())) ||
                            !(services.getHasTypeDefinition(node) == metamodel::AASSubModelType::getNodeId(t_connector.getNamespaceIndexDefault())))
                        {
                            return NodeId::nullNode();
                        }

                        auto idShort = shared::string::getInstanceName(services.getBrowseNameFromNodeId(node).getText());

                        if (idShort.empty())
                        {
                            return NodeId::nullNode();
                        }

                        if (idShort == t_idShort)
                        {
                            return node;
                        }
                    }
                    return NodeId::nullNode();
                }

                template<typename CONNECTOR_TYPE>
                static NodeId getAssetAdministrationShellNode(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string& t_identifier)
                {

                    UA_StatusCode status;

                    Services<CONNECTOR_TYPE> services(t_connector);

                    auto aasNodes = services.getChildReferencesWithType(
                        t_rootNode,
                        metamodel::AASAssetAdministrationShellType::getNodeId(t_connector.getNamespaceIndexDefault()),
                        NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                    );

                    return filterNodesWithIdentfier(t_connector, aasNodes, t_identifier);
                }

                template<typename CONNECTOR_TYPE>
                static bool doesSubModelExists(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_aasNode,
                    const std::string& t_subModelIdentifier)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    auto subModelNodes = services.getChildReferencesWithType(
                        t_aasNode,
                        metamodel::AASSubModelType::getNodeId(t_connector.getNamespaceIndexDefault()),
                        NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                    );

                    return !filterNodesWithIdShort(t_connector, subModelNodes, t_subModelIdentifier).isNull();
                }

                template<typename CONNECTOR_TYPE>
                static NodeId getSubModelNode(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string& t_aasIdentifier,
                    const std::string& t_submodelIdshort)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    auto aasNode = getAssetAdministrationShellNode(t_connector, t_rootNode, t_aasIdentifier);

                    if (aasNode.isNull()) {
                        return NodeId::nullNode();
                    }

                    auto subModelNodes{
                        services.getChildReferencesWithType(
                            aasNode,
                            metamodel::AASSubModelType::getNodeId(t_connector.getNamespaceIndexDefault()),
                            NodeId::numeric(UA_NS0ID_HASCOMPONENT))
                    };

                    return filterNodesWithIdShort(t_connector, subModelNodes, t_submodelIdshort);
                }

                template<typename CONNECTOR_TYPE>
                static std::vector<NodeId> getAllSubModelNodes(CONNECTOR_TYPE& t_connector, const NodeId& t_aasNode)
                {

                    Services<CONNECTOR_TYPE> services(t_connector);

                    auto subModelNodes{
                        services.getChildReferencesWithType(
                        t_aasNode,
                        metamodel::AASSubModelType::getNodeId(t_connector.getNamespaceIndexDefault()),
                        NodeId::numeric(UA_NS0ID_HASCOMPONENT))
                    };

                    return subModelNodes;
                }

                template<typename CONNECTOR_TYPE>
                static std::vector<NodeId> getAllPropertyNodes(CONNECTOR_TYPE& t_connector, const NodeId& t_submodelNode)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    return  services.getChildReferencesWithType(
                        t_submodelNode,
                        metamodel::AASPropertyType::getNodeId(t_connector.getNamespaceIndexDefault()),
                        NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                    );
                }

                template<typename CONNECTOR_TYPE>
                static NodeId getPropertyNode(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_submodelNode,
                    const std::string& t_idShort)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    UA_StatusCode status;

                    auto nodes = getAllPropertyNodes(t_connector, t_submodelNode);

                    return  AASApiNodeIdHelpers::filterSubModelElementNodes(
                        t_connector,
                        nodes,
                        metamodel::AASPropertyType::AttrNames::InstanceName,
                        t_idShort
                    );
                }

                template<typename CONNECTOR_TYPE>
                static std::vector<NodeId> getAllOperationNodes(CONNECTOR_TYPE& t_connector, const NodeId& t_submodelNode)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    return  services.getChildReferencesWithType(
                        t_submodelNode,
                        metamodel::AASOperationType::getNodeId(t_connector.getNamespaceIndexDefault()),
                        NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                    );
                }

                //FIXME: Remove this in future
                static NodeId getSubModelNode(const std::tuple<NodeId, NodeId, NodeId>& t_operationNode)
                {
                    return NodeId::nullNode();
                }

                template<typename CONNECTOR_TYPE>
                static NodeId getOperationMethodNode(CONNECTOR_TYPE& t_connector, const NodeId& t_operationNode)
                {

                    return t_connector.translateBrowsePathToNodeId(
                        { t_connector.getBrowseNameFromNodeId(t_operationNode),
                        {t_connector.getNamespaceIndexDefault(), metamodel::AASOperationType::AttrNames::InstanceName} },
                        t_operationNode
                    );
                }

                static NodeId getOperationObjectNode(const std::tuple<NodeId, NodeId, NodeId>& t_operationNode)
                {
                    return NodeId::nullNode();
                }

                static NodeId getOperationMethodNode(const std::tuple<NodeId, NodeId, NodeId>& t_operationNode)
                {
                    return NodeId::nullNode();
                }

                template<typename CONNECTOR_TYPE>
                static bool isOperationNodesEmpty(CONNECTOR_TYPE& t_connector, const std::tuple<NodeId, NodeId, NodeId>& t_operationNode)
                {

                    auto subModelNode = getSubModelNode(t_operationNode);
                    auto operationObjectNode = getOperationObjectNode(t_operationNode);
                    auto operationMethodNode = getOperationMethodNode(t_operationNode);

                    return (subModelNode.isNull() && operationObjectNode.isNull() && operationMethodNode.isNull());
                }

                template<typename CONNECTOR_TYPE>
                static NodeId filterSubModelElementNodes(CONNECTOR_TYPE& t_connector,
                    const std::vector<NodeId>& t_submodelElementNodes,
                    const std::string& t_instanceName,
                    const std::string& t_idShort)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    if (t_submodelElementNodes.empty())
                    {
                        return NodeId::nullNode();
                    }

                    BrowseName subModelElementBrowseName
                    {
                        t_connector.getNamespaceIndexDefault(),
                        shared::string::createPropertyName(t_instanceName, t_idShort)
                    };

                    for (const auto& submodelElementNode : t_submodelElementNodes)
                    {
                        auto browseName = services.getBrowseNameFromNodeId(submodelElementNode);

                        if (browseName == subModelElementBrowseName) {
                            return submodelElementNode;
                        }
                    }

                    return NodeId::nullNode();
                }
            };
        }
    }
}
#endif