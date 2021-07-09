#ifndef SUBMODEL_NODE_MANAGER_H
#define SUBMODEL_NODE_MANAGER_H

#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/Utilities.h>
#include <BaSyx/opcua/aas/metamodel/AASSubModelType.h>
#include <BaSyx/opcua/aas/node/ModelNodeManager.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            using Submodel = basyx::submodel::map::SubModel;

            template<typename CONNECTOR_TYPE>
            class SubmodelNodeManager final: public ModelNodeManager
            {
            public:

                SubmodelNodeManager() = delete;

                SubmodelNodeManager(CONNECTOR_TYPE& t_connector, const NodeId& t_parent):
                    m_services(Services<CONNECTOR_TYPE>(t_connector))
                {
                    using namespace metamodel;

                    int ns = m_services.getNameSpaceIndex(shared::Namespaces::BASYX_NS_URI);

                    this->m_metamodel_node  = AASSubModelType::getNodeId(ns);

                    this->m_parent_node = t_parent;
                }

                virtual UA_StatusCode create(const std::string& t_idShort , NodeId& t_outNode,
                    const LocalizedText& t_description) const override;

                virtual UA_StatusCode retrieve(const std::string& t_idShort, NodeId& t_outNode) const override;

                virtual UA_StatusCode remove(const std::string& t_idShort) const override;

                virtual std::vector<std::tuple<NodeId, std::string>> retrieveAll() const override;

            private:
                mutable opcua::Services<CONNECTOR_TYPE> m_services;
            };

            /* Adds a SM node with the given idShort */
            template<typename CONNECTOR_TYPE>
            inline UA_StatusCode SubmodelNodeManager<CONNECTOR_TYPE>::create(const std::string & t_identifier,
                NodeId & t_outNode,
                const LocalizedText& t_description) const
            {
                // IdShort should not be empty
                if (t_identifier.empty())
                    return UA_STATUSCODE_BADINVALIDARGUMENT;

                // Prepare the attributes of the Node
                auto nsIdx = m_services.getNameSpaceIndex(opcua::shared::Namespaces::BASYX_NS_URI);

                auto smMetaName = submodel::ModelTypes_::to_string(submodel::ModelTypes::Submodel);

                auto smDisplayName = std::string(smMetaName) + ":" + t_identifier;

                QualifiedName smBrowseName(nsIdx, smDisplayName);

                // TODO : Adapt the Langstring set to multiple languages
                ObjectAttributes smOAttr(smDisplayName, t_description.getText(), t_description.getLocale());

                // Instantiate the AssetAdministrationShell object node
                return m_services.addObjectNode(
                    this->m_parent_node, this->m_metamodel_node, NodeId::numeric(UA_NS0ID_HASCOMPONENT),
                    smBrowseName, smOAttr, t_outNode
                );
            }

            /* Retrieves the SM node with the given idShort */
            template<typename CONNECTOR_TYPE>
            inline UA_StatusCode SubmodelNodeManager<CONNECTOR_TYPE>::retrieve(const std::string & t_idShort, NodeId & t_outNode) const
            {
                using namespace aas::metamodel;

                t_outNode = NodeId::nullNode();

                auto nsIdx = m_services.getNameSpaceIndex(opcua::shared::Namespaces::BASYX_NS_URI);

                // Get the children
                std::vector<NodeId> smNodes = m_services.getChildReferences(
                    this->m_parent_node, NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                );

                // Filter the node
                for (const NodeId& node : smNodes)
                {
                    if (m_services.getHasTypeDefinition(node) == this->m_metamodel_node)
                    {
                        std::string identifier;

                        BrowseName smBrowseName = m_services.getBrowseNameFromNodeId(node);

                        if(smBrowseName.getText().empty())
                            return UA_STATUSCODE_BADNOTFOUND;

                        if (shared::string::getInstanceName(smBrowseName.getText()) == t_idShort)
                        {
                            t_outNode = node;

                            return UA_STATUSCODE_GOOD;
                        }                 
                    }
                }
                return UA_STATUSCODE_BADNOTFOUND;
            }

            /* Removes the SM node with the given idShort */
            template<typename CONNECTOR_TYPE>
            inline UA_StatusCode SubmodelNodeManager<CONNECTOR_TYPE>::remove(const std::string & t_idShort) const
            {
                NodeId smNode;
                
                UA_StatusCode status = retrieve(t_idShort, smNode);

                if (smNode.isNull())
                    return UA_STATUSCODE_BADNOTFOUND;

                
                if (status != UA_STATUSCODE_GOOD)
                    return status;


                return m_services.deleteNode(smNode);
            }

            /* Retrieves all the SM <node, idShort> tuple */
            template<typename CONNECTOR_TYPE>
            inline std::vector<std::tuple<NodeId, std::string>> SubmodelNodeManager<CONNECTOR_TYPE>::retrieveAll() const
            {
                std::vector<std::tuple<NodeId, std::string>> smNodeIdIdentTuple;

                // Get the children
                std::vector<NodeId> childNodes = m_services.getChildReferences(
                    this->m_parent_node, NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                );

                // Filter the node
                for (const NodeId& node : childNodes)
                {
                    if (m_services.getHasTypeDefinition(node) == this->m_metamodel_node)
                    {
                        BrowseName smBrowseName = m_services.getBrowseNameFromNodeId(node);

                        smNodeIdIdentTuple.emplace_back(std::make_tuple(node, shared::string::getInstanceName(smBrowseName.getText())));
                    }
                }

                return smNodeIdIdentTuple;
            }
}
    }
}
#endif