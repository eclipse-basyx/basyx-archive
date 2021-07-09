#ifndef PROPERTY_NODE_MANAGER_H
#define PROPERTY_NODE_MANAGER_H

#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/Utilities.h>
#include <BaSyx/opcua/aas/metamodel/AASPropertyType.h>
#include <BaSyx/opcua/aas/node/ModelNodeManager.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename T> using Property = basyx::submodel::map::Property<T>;

            template<typename CONNECTOR_TYPE>
            class PropertyNodeManager final: public ModelNodeManager
            {
            public:
                PropertyNodeManager() = delete;

                PropertyNodeManager(CONNECTOR_TYPE& t_connector, const NodeId& t_parent):
                    m_services(Services<CONNECTOR_TYPE>(t_connector))
                {
                    using namespace metamodel;

                    int ns = m_services.getNameSpaceIndex(shared::Namespaces::BASYX_NS_URI);

                    this->m_metamodel_node = AASPropertyType::getNodeId(ns);

                    this->m_parent_node = t_parent;
                }

                virtual UA_StatusCode create(const std::string& t_idShort, NodeId& t_outNode, const LocalizedText& t_description) const override;

                virtual UA_StatusCode retrieve(const std::string& t_idShort, NodeId& t_outNode) const override;

                virtual UA_StatusCode remove(const std::string& t_idShort) const override;

                virtual std::vector<std::tuple<NodeId, std::string>> retrieveAll() const override;

            private:
                mutable opcua::Services<CONNECTOR_TYPE> m_services;
            };


            /* Adds a Property node with the given idShort */
            template<typename CONNECTOR_TYPE>
            inline UA_StatusCode PropertyNodeManager<CONNECTOR_TYPE>::create(const std::string & t_idShort,
                NodeId & t_outNode,
                const LocalizedText& t_description) const
            {
                // IdShort should not be empty
                if (t_idShort.empty())
                    return UA_STATUSCODE_BADINVALIDARGUMENT;

                // Prepare the attributes of the Node
                auto nsIdx = m_services.getNameSpaceIndex(opcua::shared::Namespaces::BASYX_NS_URI);

                auto propMetaName = submodel::ModelTypes_::to_string(submodel::ModelTypes::Submodel);

                auto propDisplayName = std::string(propMetaName) + ":" + t_idShort;

                QualifiedName propBrowseName(nsIdx, propDisplayName);

                // TODO : Adapt the Langstring set to multiple languages
                ObjectAttributes propOAttr(propDisplayName, t_description.getText(), t_description.getLocale());

                // Instantiate the Property object node
                return m_services.addObjectNode(
                    this->m_parent_node, this->m_metamodel_node, NodeId::numeric(UA_NS0ID_HASCOMPONENT),
                    propBrowseName, propOAttr, t_outNode
                );
            }

            /*  Retrieves Property node with the given idShort */
            template<typename CONNECTOR_TYPE>
            inline UA_StatusCode PropertyNodeManager<CONNECTOR_TYPE>::retrieve(const std::string & t_idShort, NodeId & t_outNode) const
            {
                using namespace aas::metamodel;

                t_outNode = NodeId::nullNode();

                auto nsIdx = m_services.getNameSpaceIndex(opcua::shared::Namespaces::BASYX_NS_URI);

                // Get the children
                std::vector<NodeId> propNodes = m_services.getChildReferences(
                    this->m_parent_node, NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                );

                // Filter the node
                for (const NodeId& node : propNodes)
                {
                    if (m_services.getHasTypeDefinition(node) == this->m_metamodel_node)
                    {
                        std::string identifier;

                        BrowseName smBrowseName = m_services.getBrowseNameFromNodeId(node);

                        if (smBrowseName.getText().empty())
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

            /* Deletes the Property node with the given idShort */
            template<typename CONNECTOR_TYPE>
            inline UA_StatusCode PropertyNodeManager<CONNECTOR_TYPE>::remove(const std::string & t_idShort) const
            {
                NodeId propNode;
                
                UA_StatusCode status = retrieve(t_idShort, propNode);

                if (propNode.isNull())
                    return UA_STATUSCODE_BADNOTFOUND;

                
                if (status != UA_STATUSCODE_GOOD)
                    return status;


                return m_services.deleteNode(propNode);
            }


    	    /* Retrieve all the Property <node, idShort> tuple */
            template<typename CONNECTOR_TYPE>
            inline std::vector<std::tuple<NodeId, std::string>> PropertyNodeManager<CONNECTOR_TYPE>::retrieveAll() const
            {
                using namespace aas::metamodel;

                std::vector<std::tuple<NodeId, std::string>> propNodeIdIdentTuple;

                // Get the children
                std::vector<NodeId> childNodes = m_services.getChildReferences(
                    this->m_parent_node, NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                );

                // Filter the node
                for (const NodeId& node : childNodes)
                {
                    if (m_services.getHasTypeDefinition(node) == this->m_metamodel_node)
                    {
                        BrowseName propBrowseName = m_services.getBrowseNameFromNodeId(node);

                        propNodeIdIdentTuple.emplace_back(std::make_tuple(node, shared::string::getInstanceName(propBrowseName.getText())));
                    }
                }

                return propNodeIdIdentTuple;
            }
        }
    }
}
#endif