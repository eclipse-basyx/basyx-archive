#ifndef AAS_API_SUBMODEL_ELEMENT_H
#define AAS_API_SUBMODEL_ELEMENT_H

#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/Utilities.h>
#include <BaSyx/opcua/aas/api/AASApiNode.h>
#include <BaSyx/opcua/aas/model/AASProperty.h>
#include <BaSyx/opcua/aas/metamodel/AASOperationType.h>
#include <BaSyx/opcua/aas/api/AASApiMetamodelHelpers.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE>
            class AASApiSubModelElement final : public AASApiNode<CONNECTOR_TYPE>
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASApiSubModelElement";
            private:
                /* FIXME: Add  all types */
                template<typename TYPE>
                struct PropertyTypeAssertCondition
                {
                    static constexpr bool value{ true };
                };

                template<typename IElementType>
                using IElementContainer = basyx::submodel::api::IElementContainer<IElementType>;

                template<typename IElementType>
                using ElementContainer = basyx::submodel::map::ElementContainer<IElementType>;

                template<typename IElementType>
                using ElementContainer_t = basyx::submodel::map::ElementContainer<IElementType>;

            public:
                explicit AASApiSubModelElement(CONNECTOR_TYPE& t_connector, NodeId t_rootNode) :
                    AASApiNode<CONNECTOR_TYPE>(t_connector, t_rootNode, loggerName) {}

                virtual ~AASApiSubModelElement() = default;

                template<typename TYPE>
                ApiResponse addProperty(const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier,
                    const Property_t<TYPE>& t_property);

                template<typename TYPE>
                ApiResponse addProperty(const NodeId& t_parentNode,
                    const Property_t<TYPE>& t_property);


                template<typename TYPE>
                ApiResponse updateProperty(const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier,
                    const std::string& t_idShort,
                    const TYPE& t_value);

                ApiResponse deleteSubmodelElement(const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier,
                    const std::string& t_idShort);

                std::shared_ptr<IProperty> getProperty(const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier,
                    const std::string& t_idShort);

                std::shared_ptr<IOperation> getOperation(const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier,
                    const std::string& t_idShort);

                Collection_t<IOperation>  getAllOperations(const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier);

                std::string getPropertyValueType(const NodeId & t_rootNode,
                    const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier,
                    const std::string& t_idShort);

            private:

                template<typename TYPE>
                std::shared_ptr<IProperty> buildPropertyFromOpcua(std::shared_ptr<AASProperty<CONNECTOR_TYPE>> t_propertyOPCUA);

                std::tuple<NodeId, NodeId, NodeId> getOperationNodesTuple(const std::string& t_aasIdentifier,
                    const std::string& t_subModelIdentifier,
                    const std::string& t_idShort);
            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASApiSubModelElement<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE> template<typename TYPE>
            inline ApiResponse AASApiSubModelElement<CONNECTOR_TYPE>::addProperty(const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier,
                const Property_t<TYPE>& t_property)
            {
                static_assert(PropertyTypeAssertCondition<TYPE>::value, "Type not supported ");

                auto subModelNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(),
                    this->getRootNode(),
                    t_aasIdentifier,
                    t_subModelIdentifier);

                if (subModelNode.isNull()) {

                    std::string errStr("subModelNode [" +
                        t_subModelIdentifier + "] not found"
                    );

                    this->getLogger().error(errStr);

                    return ApiResponse::NOT_OK_NOT_FOUND;
                }

                auto ret{ addProperty(subModelNode, t_property) };

                if (ret == ApiResponse::OK)
                {
                    this->getLogger().trace(
                        "AssetAdministraionShell [" + t_aasIdentifier + "]" +
                        " SubModel [" + t_subModelIdentifier + "]" +
                        " Property [" + t_property.getIdShort() + "] added"
                    );
                }

                return ret;
            }

            template<typename CONNECTOR_TYPE> template<typename TYPE>
            inline ApiResponse AASApiSubModelElement<CONNECTOR_TYPE>::addProperty(const NodeId & t_parentNode,
                const Property_t<TYPE>& t_property)
            {
                auto propValue = t_property.getValue();

                auto propertyOPCUA{
                    std::make_shared<AASProperty<CONNECTOR_TYPE>>(
                        this->getConnector(),
                        t_parentNode,
                        t_property.getIdShort()
                    )
                };
                propertyOPCUA->createNew();
                propertyOPCUA->setValue(propValue);
                propertyOPCUA->setReferableCategory((t_property.getCategory()) ? *t_property.getCategory() : std::string());
                propertyOPCUA->setKind(t_property.getKind());

                if (t_property.getValueId())
                {
                    auto valId = t_property.getValueId();

                    for (auto& key : valId->getKeys())
                    {
                        propertyOPCUA->addValueIdKeys(key);
                    }
                }

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE> template<typename TYPE>
            inline ApiResponse AASApiSubModelElement<CONNECTOR_TYPE>::updateProperty(const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier,
                const std::string & t_idShort,
                const TYPE & t_value)
            {
                static_assert(PropertyTypeAssertCondition<TYPE>::value, "Type not supported for Property ");

                UA_StatusCode status;

                auto subModelNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(), this->getRootNode(), t_aasIdentifier, t_subModelIdentifier);

                if (subModelNode.isNull())
                {
                    this->getLogger().error("Submodel node cannot be resolved");

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                for (auto& propertyNode : this->getServices().getChildReferencesWithType(
                    subModelNode,
                    metamodel::AASPropertyType::getNodeId(this->getConnector().getNamespaceIndexDefault()),
                    NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                ))
                {
                    std::string idShort;

                    auto idShortNode = this->getServices().translateBrowsePathToNodeId(
                        metamodel::AASSubModelElementType::getIdShortPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), propertyNode),
                        propertyNode
                    );

                    if (idShortNode.isNull())
                    {
                        this->getLogger().error("SubModelElement idShort node cannot be resolved");

                        return ApiResponse::NOT_OK_INTERNAL_ERROR;
                    }

                    status = this->getServices().readValue(idShortNode, idShort);

                    if (idShort == t_idShort)
                    {
                        auto valueNode = this->getServices().translateBrowsePathToNodeId(
                            metamodel::AASPropertyType::getValuePath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), propertyNode),
                            propertyNode
                        );

                        if (idShortNode.isNull())
                        {
                            this->getLogger().error("SubmodelElement value node cannot be resolved");

                            return ApiResponse::NOT_OK_INTERNAL_ERROR;
                        }

                        status = this->getServices().writeValue(valueNode, t_value);

                        if (status != UA_STATUSCODE_GOOD)
                        {
                            this->getLogger().error("SubmodelElement value update failed - " +
                                basyx::opcua::shared::diag::getErrorString(status)
                            );

                            return ApiResponse::NOT_OK_INTERNAL_ERROR;
                        }
                    }
                }

                this->getLogger().trace(
                    "AssetAdministraionShell [" + t_aasIdentifier + "]" +
                    " SubModel [" + t_subModelIdentifier + "]" +
                    " Property [" + t_idShort + "] updated "
                );

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            inline std::string AASApiSubModelElement<CONNECTOR_TYPE>::getPropertyValueType(const NodeId & t_rootNode,
                const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier,
                const std::string & t_idShort)
            {
                std::string ret;

                auto smNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(), t_rootNode, t_aasIdentifier, t_subModelIdentifier);

                if (smNode.isNull())
                {
                    this->getLogger().error("Submodel node cannot be resolved");
                    return ret;
                }

                auto propertyNode = AASApiNodeIdHelpers::getPropertyNode(this->getConnector(), smNode, t_idShort);

                if (propertyNode.isNull())
                    return ret;

                auto valueTypeNode = this->getServices().getChildReferencesWithBrowseName(
                    propertyNode,
                    BrowseName(this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_ValueType),
                    NodeId::numeric(UA_NS0ID_HASPROPERTY)
                );

                if (valueTypeNode.isNull())
                    return ret;

                auto status = this->getServices().readValue(valueTypeNode, ret);

                return ret;

            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASApiSubModelElement<CONNECTOR_TYPE>::deleteSubmodelElement(const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier,
                const std::string & t_idShort)
            {
                auto submodelNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(),
                    this->getRootNode(),
                    t_aasIdentifier,
                    t_subModelIdentifier);

                if (submodelNode.isNull())
                {
                    this->getLogger().error("SubmodelElement cannot be deleted : Submodel [" +
                        t_idShort + "] node not found"
                    );

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                for (auto& submodelElementNode : this->getServices().getAllChildReferences(submodelNode))
                {
                    std::string idShort = shared::string::getInstanceName(
                        this->getServices().getBrowseNameFromNodeId(submodelElementNode).getText()
                    );

                    if (idShort == t_idShort)
                    {
                        auto status = this->getServices().deleteNode(submodelElementNode);

                        if (status != UA_STATUSCODE_GOOD)
                        {
                            this->getLogger().error("SubmodelElement cannot be deleted : Failed to delete node");

                            return ApiResponse::NOT_OK_INTERNAL_ERROR;
                        }
                    }
                }

                this->getLogger().info("Submodel [" +
                    t_subModelIdentifier + "] SubmodelElement [" + t_idShort + "] deleted"
                );

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            std::shared_ptr<IOperation> AASApiSubModelElement<CONNECTOR_TYPE>::getOperation(const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier,
                const std::string & t_idShort)
            {
                //TODO: Operation concept has to be finalized
                return nullptr;
            }

            template<typename CONNECTOR_TYPE>
            Collection_t<IOperation> AASApiSubModelElement<CONNECTOR_TYPE>::getAllOperations(const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier)
            {
                //TODO: Operation concept has to be finalized
                Collection_t<IOperation> ret;
                return ret;
            }

            template<typename CONNECTOR_TYPE>
            std::tuple<NodeId, NodeId, NodeId> AASApiSubModelElement<CONNECTOR_TYPE>::getOperationNodesTuple(
                const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier,
                const std::string & t_idShort)
            {
                UA_StatusCode scode;

                auto ret = std::make_tuple(NodeId::nullNode(), NodeId::nullNode(), NodeId::nullNode());

                auto subModelNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(), this->getRootNode(), t_aasIdentifier, t_subModelIdentifier);

                if (subModelNode.isNull())
                {
                    this->getLogger().error("updateProperty.SubModelNode [" +
                        t_subModelIdentifier + "] not found"
                    );
                    return ret;
                }

                auto operationNodes
                {
                    this->getServices().getChildReferencesWithType(
                        subModelNode,
                        metamodel::AASOperationType::getNodeId(this->getConnector().getNamespaceIndexDefault()),
                        NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                    )
                };

                if (operationNodes.empty())
                {
                    this->getLogger().warn("No Operations found");
                    return ret;
                }

                auto operationObjectNode
                {
                    AASApiNodeIdHelpers::filterSubModelElementNodes(
                        this->getConnector(),
                        operationNodes,
                        metamodel::AASOperationType::AttrNames::InstanceName,
                        t_idShort
                    )
                };

                if (operationObjectNode.isNull())
                {
                    this->getLogger().warn("AASOperation not found - " + t_idShort);
                    ret;
                }

                auto operationMethodNode{ this->getServices().translateBrowsePathToNodeId(
                    {this->getConnector().getBrowseNameFromNodeId(operationObjectNode),
                    {this->getConnector().getNamespaceIndexDefault(), metamodel::AASOperationType::AttrNames::InstanceName}},
                      operationObjectNode
                    )
                };

                if (operationMethodNode.isNull())
                {
                    this->getLogger().error("No Methods found");
                    return ret;
                }

                ret = std::make_tuple(subModelNode, operationObjectNode, operationMethodNode);

                return ret;
            }

            //FIXME : If not necessary remove in the future
            template<typename CONNECTOR_TYPE> template<typename TYPE>
            inline std::shared_ptr<IProperty> AASApiSubModelElement<CONNECTOR_TYPE>::buildPropertyFromOpcua(std::shared_ptr<AASProperty<CONNECTOR_TYPE>> t_propertyOPCUA)
            {
                static_assert(PropertyTypeAssertCondition<TYPE>::value, "Type not supported ");

                if (t_propertyOPCUA)
                {
                    auto prop = std::make_shared<Property_t<TYPE>>(t_propertyOPCUA->getReferableIdShort());

                    Reference keys(t_propertyOPCUA->getValueIdKeys());
                    prop->setValueId(keys);
                    //prop->setValue(t_propertyOPCUA->getValue());
                    prop->setValueType(t_propertyOPCUA->getValueType());
                    prop->setCategory(t_propertyOPCUA->getReferableCategory());

                    return prop;
                }
                return nullptr;
            }

            //FIXME : If not necessary, remove in future
            template<typename CONNECTOR_TYPE>
            std::shared_ptr<IProperty> AASApiSubModelElement<CONNECTOR_TYPE>::getProperty(const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier,
                const std::string & t_idShort)
            {
                UA_StatusCode scode;

                auto subModelNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(), this->getRootNode(), t_aasIdentifier, t_subModelIdentifier);

                if (subModelNode.isNull())
                {
                    this->getLogger().error("updateProperty.SubModelNode [" +
                        t_subModelIdentifier + "] not found"
                    );
                }

                auto propertyNodes
                {
                    this->getServices().getChildReferencesWithType(
                        subModelNode,
                        metamodel::AASPropertyType::getNodeId(this->getConnector().getNamespaceIndexDefault()),
                        NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                    )
                };

                auto nodeToRead
                {
                    AASApiNodeIdHelpers::filterSubModelElementNodes(
                        this->getConnector(),
                        propertyNodes,
                        metamodel::AASPropertyType::AttrNames::InstanceName,
                        t_idShort
                    )
                };

                auto propertyOPCUA
                {
                    std::make_shared<AASProperty<CONNECTOR_TYPE>>(
                        this->getConnector(),
                        subModelNode,
                        t_idShort
                    )
                };

                propertyOPCUA->setNodeId(nodeToRead);

                auto propValueType{ propertyOPCUA->getValueType() };

                auto valueNode
                {
                   this->getServices().getChildReferencesWithBrowseName(
                       nodeToRead,
                       BrowseName(this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_Value),
                       NodeId::numeric(UA_NS0ID_HASPROPERTY)
                   )
                };

                return nullptr;
            }
        }
    }
}
#endif