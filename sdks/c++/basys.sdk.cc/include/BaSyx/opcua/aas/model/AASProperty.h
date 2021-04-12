#ifndef AAS_PROPERTY_H
#define AAS_PROPERTY_H

#include <BaSyx/opcua/aas/metamodel/AASPropertyType.h>
#include <BaSyx/opcua/aas/metamodel/DictionaryEntryFragmentedId.h>
#include <BaSyx/opcua/aas/metamodel/DictionaryEntryIdShort.h>
#include <BaSyx/opcua/aas/metamodel/AASReference.h>
#include <BaSyx/opcua/aas/model/AASModelNode.h>
#include <BaSyx/opcua/aas/model/AASSubModel.h>
#include <BaSyx/opcua/aas/model/AASSubModelElement.h>
#include <BaSyx/opcua/aas/api/AASApiMetamodelHelpers.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE = basyx::opcua::Client>
            class AASProperty final : public AASModelNode<CONNECTOR_TYPE>
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASProperty";
            public:
                ~AASProperty() = default;
                AASProperty() = delete;

                AASProperty(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_parentSubmodelNode,
                    const std::string& t_idShort) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        NodeId::nullNode(),
                        t_parentSubmodelNode,
                        t_idShort,
                        std::string(),
                        NodeDescription(),
                        loggerName)
                {
                    this->setInstanceName(shared::string::createPropertyName(metamodel::AASPropertyType::AttrNames::InstanceName, t_idShort));
                }

                AASProperty(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_propertyNode,
                    const NodeId& t_parentSubmodelNode) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        t_propertyNode,
                        t_parentSubmodelNode,
                        std::string(),
                        std::string(),
                        NodeDescription(),
                        loggerName)
                {
                    this->setIdShort(shared::string::createPropertyName(metamodel::AASPropertyType::AttrNames::InstanceName, getReferableIdShort()));
                    this->setInstanceName(getReferableIdShort());
                }

                std::string getReferableIdShort();

                std::string getReferableCategory();

                Kind getKind();

                std::string getValueType();

                std::vector<Key> getValueIdKeys();

                void createNew();

                std::string getValue();

                template<typename TYPE>
                void setValue(const TYPE& t_value);


                void setReferableIdShort(const std::string& t_idShort);

                void setReferableCategory(const std::string& t_idShort);

                void setKind(const Kind& t_kind);

                void setValueType(const std::string& t_valueType);

                void addValueIdKeys(KeyElements keyElement, bool t_isLocal, KeyType t_idType, const std::string& t_value);

                void addValueIdKeys(const Key& t_key);

                void removeAllValueIdKeys();

                template<typename TYPE>
                static void setPropertyAttributes(Property_t<TYPE>* t_property,
                    TYPE t_value,
                    const std::string& t_valueType,
                    const std::string& t_category,
                    Reference& t_reference);

            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASProperty<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::createNew()
            {
                NodeId self;
                UA_StatusCode scode;
                std::string kind;
                std::string instanceName = metamodel::AASPropertyType::AttrNames::InstanceName;

                scode = this->getServices().addObjectNode(
                    this->getParentNodeId(),
                    metamodel::AASPropertyType::getNodeId(this->getConnector().getNamespaceIndexDefault()),
                    NodeId::numeric(UA_NS0ID_HASCOMPONENT),
                    QualifiedName(this->getConnector().getNamespaceIndexDefault(), this->getInstanceName()),
                    ObjectAttributes(this->getInstanceName(), this->getInstanceName(), this->getNodeDescription().getLangCode()),
                    self
                );

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("createNew -" + shared::diag::getErrorString(scode));
                    return;
                }

                this->setNodeId(self);
                setReferableIdShort(this->getIdShort());
            }

            template<typename CONNECTOR_TYPE>
            inline std::string AASProperty<CONNECTOR_TYPE>::getValueType()
            {
                std::string value;
                UA_StatusCode scode;

                auto valueTypeNode
                {
                   this->getServices().getChildReferencesWithBrowseName(
                       this->getNodeId(),
                       BrowseName(this->getConnector().getNamespaceIndexDefault(),
                           metamodel::AASPropertyType::AttrNames::BrowseText_ValueType),
                       NodeId::numeric(UA_NS0ID_HASPROPERTY)
                   )
                };

                scode = this->getServices().readValue(valueTypeNode, value);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getValueType -" + shared::diag::getErrorString(scode));
                }
                return value;
            }

            template<typename CONNECTOR_TYPE>
            inline std::vector<Key> AASProperty<CONNECTOR_TYPE>::getValueIdKeys()
            {
                UA_StatusCode scode;
                std::vector<Key> ret;
                std::string valueIdString;

                auto valueNodeId = this->getServices().getChildReferencesWithBrowseName(
                    this->getNodeId(),
                    { this->getConnector().getNamespaceIndexDefault(),
                    metamodel::AASPropertyType::AttrNames::BrowseText_ReferenceValueId },
                    NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                );

                if (valueNodeId.isNull())
                {
                    this->getLogger().error("getValueIdKeys valueIdNodeId is null");
                    return ret;
                }

                for (auto& keysNodeId : this->getServices().getChildReferences(valueNodeId, NodeId::numeric(UA_NS0ID_HASPROPERTY)))
                {
                    scode = this->getServices().readValue(keysNodeId, valueIdString);

                    if (UA_STATUSCODE_GOOD != scode)
                    {
                        this->getLogger().error("readValue - " +
                            (basyx::opcua::shared::diag::getErrorString(scode))
                        );
                        return ret;
                    }
                    ret.push_back(KeyFromString(valueIdString));
                }
                return ret;
            }

            template<typename CONNECTOR_TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::removeAllValueIdKeys()
            {

                UA_StatusCode scode;

                auto valueNodeId = this->getConnector().getHasComponentReferenceWithBrowseName(
                    this->getNodeId(),
                    { this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_ReferenceValueId }
                );

                if (valueNodeId.isNull()) {
                    this->getLogger().error("removeAllValueIdKeys valueIdNodeId is null");
                    return;
                }

                for (auto& keysNodeId : this->getServices().getChildReferences(valueNodeId, NodeId::numeric(UA_NS0ID_HASPROPERTY)))
                {
                    scode = this->getServices().deleteNodeWithReferences(keysNodeId);

                    if (UA_STATUSCODE_GOOD != scode)
                    {
                        this->getLogger().error("deleteNodeWithReferences - " +
                            (basyx::opcua::shared::diag::getErrorString(scode)));
                    }
                    else
                    {
                        this->getLogger().info("deleteNodeWithReferences keys deleted");
                    }
                }
            }


            template<typename CONNECTOR_TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::addValueIdKeys(const Key& t_key)
            {
                addValueIdKeys(t_key.getType(), t_key.isLocal(), t_key.getIdType(), t_key.getValue());
            }

            template<typename CONNECTOR_TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::addValueIdKeys(KeyElements t_keyElement,
                bool t_isLocal,
                KeyType t_idType,
                const std::string& t_valueId)
            {

                UA_StatusCode scode = UA_STATUSCODE_GOOD;

                NodeId keyNodeId;

                ExpandedNodeId keyTypeRef;

                auto valueIdNode = this->getServices().getChildReferencesWithBrowseName(
                    this->getNodeId(),
                    BrowseName(this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_ReferenceValueId),
                    NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                );

                if (valueIdNode.isNull()) {
                    this->getLogger().error("addValueIdKeys.translateBrowsePathToNodeId is null");
                    return;
                }

                scode = this->getServices().addVariableNode(
                    valueIdNode,
                    NodeId::numeric(UA_NS0ID_HASPROPERTY),
                    NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
                    QualifiedName(this->getServices().getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_Value),
                    VariableAttributes(UA_TYPES_STRING,
                        metamodel::AASReferenceType::AttrNames::DisplayName_Keys,
                        metamodel::AASReferenceType::AttrNames::DisplayName_Keys, "en-US"),
                    keyNodeId
                );

                if (UA_STATUSCODE_GOOD != scode) {
                    this->getLogger().error("Variable node ValueIdKeys cannot be added for Property - " +
                        (basyx::opcua::shared::diag::getErrorString(scode))
                    );
                    return;
                }

                scode = this->getServices().writeValue(
                    keyNodeId,
                    KeyToString(t_keyElement, t_isLocal, t_idType, t_valueId)
                    );

                if (UA_STATUSCODE_GOOD != scode) {
                    this->getLogger().error("addValueIdKeys.writeValueString - " +
                        (basyx::opcua::shared::diag::getErrorString(scode))
                    );
                    return;
                }

                if (t_idType == KeyType::IdShort)
                {
                    keyTypeRef = ExpandedNodeId::string(
                        this->getConnector().getNamespaceIndexDefault(),
                        metamodel::DictionaryEntryIdShort::AttrNames::NodeHandle
                    );
                }
                else if (t_idType == KeyType::FragementId)
                {
                    keyTypeRef = ExpandedNodeId::string(
                        this->getConnector().getNamespaceIndexDefault(),
                        metamodel::DictionaryEntryFragmentedId::AttrNames::NodeHandle
                    );
                }
                else if (t_idType == KeyType::Custom)
                {
                    keyTypeRef = ExpandedNodeId::numeric(UA_NS0ID_DICTIONARYENTRYTYPE);
                }
                else if (t_idType == KeyType::IRDI)
                {
                    keyTypeRef = ExpandedNodeId::numeric(UA_NS0ID_IRDIDICTIONARYENTRYTYPE);
                }
                else
                {
                    keyTypeRef = ExpandedNodeId::numeric(UA_NS0ID_URIDICTIONARYENTRYTYPE);
                }

                scode = this->getServices().addForwardReference(
                    keyNodeId,
                    metamodel::AASReference::getNodeId(this->getConnector().getNamespaceIndexDefault()),
                    keyTypeRef,
                    UA_NODECLASS_OBJECT
                );

                if (UA_STATUSCODE_GOOD != scode)
                {
                    this->getLogger().error("addValueIdKeys.addForwardReference - " +
                        (basyx::opcua::shared::diag::getErrorString(scode))
                    );
                }
            }

            template<typename CONNECTOR_TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::setReferableIdShort(const std::string& t_idShort)
            {
                AASSubModelElement<CONNECTOR_TYPE> smElement(this->getConnector(), this->getNodeId());
                smElement.setReferableIdShort(t_idShort);
            }

            template<typename CONNECTOR_TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::setReferableCategory(const std::string& t_category)
            {
                AASSubModelElement<CONNECTOR_TYPE> smElement(this->getConnector(), this->getNodeId());
                smElement.setReferableCategory(t_category);
            }

            template<typename CONNECTOR_TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::setKind(const Kind& t_kind)
            {
                AASSubModelElement<CONNECTOR_TYPE> smElement(this->getConnector(), this->getNodeId());
                smElement.setKind(t_kind);
            }

            template<typename CONNECTOR_TYPE>
            inline Kind AASProperty<CONNECTOR_TYPE>::getKind()
            {
                AASSubModelElement<CONNECTOR_TYPE> smElement(this->getConnector(), this->getNodeId());
                return smElement.getKind();
            }

            template<typename CONNECTOR_TYPE>
            inline std::string AASProperty<CONNECTOR_TYPE>::getReferableCategory()
            {
                AASSubModelElement<CONNECTOR_TYPE> smElement(this->getConnector(), this->getNodeId());
                return smElement.getReferableCategory();
            }

            template<typename CONNECTOR_TYPE>
            inline std::string AASProperty<CONNECTOR_TYPE>::getReferableIdShort()
            {
                AASSubModelElement<CONNECTOR_TYPE> smElement(this->getConnector(), this->getNodeId());
                return smElement.getReferableIdShort();
            }
            
            template<typename CONNECTOR_TYPE>
            inline std::string AASProperty<CONNECTOR_TYPE>::getValue()
            {
                std::string value;
                UA_StatusCode scode;

                NodeId valueNode
                {
                   this->getServices().getChildReferencesWithBrowseName(
                       this->getNodeId(),
                       BrowseName(this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_Value),
                       NodeId::numeric(UA_NS0ID_HASPROPERTY)
                   )
                };

                scode = this->getServices().readValue(valueNode, value);
                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getValue -" + (basyx::opcua::shared::diag::getErrorString(scode)));
                }
                return value;
            }

            template<typename CONNECTOR_TYPE> template<typename TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::setValue(const TYPE & t_value)
            {
                using namespace basyx::xsd_types;
                NodeId valueNode;
                UA_StatusCode scode = UA_STATUSCODE_GOOD;

                valueNode = this->getServices().getChildReferencesWithBrowseName(
                    this->getNodeId(),
                    BrowseName(this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_Value),
                    NodeId::numeric(UA_NS0ID_HASPROPERTY)
                );

                if (valueNode.isNull())
                {
                    scode = this->getServices().addVariableNode(
                        this->getNodeId(),
                        NodeId::numeric(UA_NS0ID_HASPROPERTY),
                        NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
                        QualifiedName(this->getServices().getConnector().getNamespaceIndexDefault(),
                            metamodel::AASPropertyType::AttrNames::BrowseText_Value),
                        VariableAttributes(
                            //shared::meta::UATypeId(t_value),
                            UA_TYPES_STRING,
                            //AASApiMetamodelHelpers::BasyxToUATypeMap(t_value,)
                            metamodel::AASPropertyType::AttrNames::DisplayName_Value,
                            metamodel::AASPropertyType::AttrNames::Description_Value,
                            "en-US"
                        ),
                        valueNode
                    );
                }

                if (valueNode.isNull()) {
                    this->getLogger().error("failed to create Value node");
                }

                auto value = AASApiMetamodelHelpers::TransformValueToString<TYPE>(t_value);
                //TypesTransformer::numeral_cast<TYPE>(t_value);

                scode = this->getServices().writeValue(valueNode, value);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("Value cannot be written - " + shared::diag::getErrorString(scode));
                }

                auto valueTypeNode
                {
                   this->getServices().getChildReferencesWithBrowseName(
                       this->getNodeId(),
                       BrowseName(this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_ValueType),
                       NodeId::numeric(UA_NS0ID_HASPROPERTY)
                   )
                };

                if (valueNode.isNull()) {
                    this->getLogger().error("Value cannot be resolved");
                }

                scode = this->getServices().writeValue(valueTypeNode, xsd_type<TYPE>::getDataTypeDef());

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setValueType -" + shared::diag::getErrorString(scode));
                    return;
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASProperty<CONNECTOR_TYPE>::setValueType(const std::string& t_valueType)
            {
                UA_StatusCode scode;

                auto valueTypeNode
                {
                   this->getServices().getChildReferencesWithBrowseName(
                       this->getNodeId(),
                       BrowseName(this->getConnector().getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_ValueType),
                       NodeId::numeric(UA_NS0ID_HASPROPERTY)
                   )
                };

                scode = this->getServices().writeValue(valueTypeNode, t_valueType);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setValueType -" + shared::diag::getErrorString(scode));
                    return;
                }
            }

            template<typename CONNECTOR_TYPE> template<typename TYPE>
            inline void AASProperty<CONNECTOR_TYPE>::setPropertyAttributes(Property_t<TYPE>* t_property,
                TYPE t_value,
                const std::string & t_valueType,
                const std::string & t_category,
                Reference & t_reference)
            {

                t_property->setValue(t_value);
                t_property->setValueType(t_valueType);
                t_property->setCategory(t_category);
                if (!t_reference.empty())
                {
                    t_property->setValueId(t_reference);
                }
            }

        }
    }
}

#endif