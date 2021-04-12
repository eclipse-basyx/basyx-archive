#ifndef AAS_SUBMODEL_H
#define AAS_SUBMODEL_H

#include <BaSyx/opcua/aas/model/AASModelNode.h>
#include <BaSyx/opcua/aas/metamodel/AASSubModelType.h>
#include <BaSyx/opcua/aas/model/AASAssetAdministrationShell.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE = basyx::opcua::Client>
            class AASSubModel final : public AASModelNode<CONNECTOR_TYPE>
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASSubModel";
            public:
                virtual ~AASSubModel() = default;
                AASSubModel() = default;

                AASSubModel(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_parentAASNode,
                    const std::string& t_idShort,
                    const Identifier& t_identifier) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        NodeId::nullNode(),
                        t_parentAASNode,
                        t_idShort,
                        std::string(),
                        NodeDescription(),
                        loggerName),
                    m_identifier(t_identifier)
                {
                    this->setInstanceName(shared::string::createPropertyName(metamodel::AASSubModelType::AttrNames::InstanceName, t_idShort));
                }

                AASSubModel(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_subModelNode) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        t_subModelNode,
                        NodeId::nullNode(),
                        std::string(),
                        std::string(),
                        NodeDescription(),
                        loggerName)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);
                    this->setIdShort(shared::string::getInstanceName(services.getBrowseNameFromNodeId(t_subModelNode).getText()));
                    this->setInstanceName(shared::string::getInstanceName(services.getBrowseNameFromNodeId(t_subModelNode).getText()));
                }

                void createNew();

                void setAdminstrationVersion(const std::string& t_version);

                void setAdminstrationRevision(const std::string& t_revision);

                void setIdentificationId(const std::string& t_id);

                void setIdentificationIdType(const IdentifierType& t_idType);

                void setKind(const Kind& t_kind);

                std::string getAdminstrationVersion();

                std::string getAdminstrationRevision();

                std::string getIdentificationId();

                IdentifierType getIdentificationIdType();

                Kind getKind();

            private:
                Identifier m_identifier;
            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASSubModel<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            void AASSubModel<CONNECTOR_TYPE>::createNew()
            {
                NodeId self;
                UA_StatusCode scode;

                scode = this->getServices().addObjectNode(
                    this->getParentNodeId(),
                    metamodel::AASSubModelType::getNodeId(this->getConnector().getNamespaceIndexDefault()),
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
                this->setIdentificationId(m_identifier.getId());
                this->setIdentificationIdType(m_identifier.getIdType());
            }

            template<typename CONNECTOR_TYPE>
            void AASSubModel<CONNECTOR_TYPE>::setAdminstrationVersion(const std::string& t_version)
            {
                UA_StatusCode scode;

                auto adminVersionNode
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getAdministrationVersionPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                    )
                };

                scode = this->getServices().writeValue(adminVersionNode, t_version);

                if (scode != UA_STATUSCODE_GOOD) {
                    this->getLogger().error("setAdminstrationVersion -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASSubModel<CONNECTOR_TYPE>::setAdminstrationRevision(const std::string& t_revision)
            {
                UA_StatusCode scode;

                auto adminRevisonNode
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getAdministrationRevisionPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(),this->getNodeId()),
                        this->getNodeId())
                };

                scode = this->getServices().writeValue(adminRevisonNode, t_revision);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setAdminstrationRevision -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASSubModel<CONNECTOR_TYPE>::setIdentificationId(const std::string& t_id)
            {
                UA_StatusCode scode;

                auto identificationId
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getIdentificationIdPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId())
                };

                scode = this->getServices().writeValue(identificationId, t_id);

                if (scode != UA_STATUSCODE_GOOD) {
                    this->getLogger().error("setIdentificationId -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASSubModel<CONNECTOR_TYPE>::setIdentificationIdType(const IdentifierType& t_idType)
            {
                UA_StatusCode scode;
                auto identificationIdType
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getIdentificationIdTypePath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId())
                };
                scode = this->getServices().writeValue(identificationIdType, IdTypetoString(t_idType));

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setIdentificationIdType -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            std::string AASSubModel<CONNECTOR_TYPE>::getAdminstrationVersion()
            {
                std::string version;
                UA_StatusCode scode;

                auto adminVersionNode
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getAdministrationVersionPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                    )
                };

                scode = this->getServices().readValue(adminVersionNode, version);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getAdminstrationVersion -" + shared::diag::getErrorString(scode));
                }
                return version;
            }

            template<typename CONNECTOR_TYPE>
            std::string AASSubModel<CONNECTOR_TYPE>::getAdminstrationRevision()
            {
                std::string revision;
                std::string version;
                UA_StatusCode scode;

                auto adminRevisonNode
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getAdministrationRevisionPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                    )
                };

                scode = this->getServices().readValue(adminRevisonNode, revision);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getAdminstrationRevision -" + shared::diag::getErrorString(scode));
                }

                return revision;
            }

            template<typename CONNECTOR_TYPE>
            std::string AASSubModel<CONNECTOR_TYPE>::getIdentificationId()
            {
                std::string id;
                UA_StatusCode scode;

                auto identificationId
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getIdentificationIdPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                    )
                };
                scode = this->getServices().readValue(identificationId, id);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getIdentificationId -" + shared::diag::getErrorString(scode));
                }

                return id;
            }

            template<typename CONNECTOR_TYPE>
            IdentifierType AASSubModel<CONNECTOR_TYPE>::getIdentificationIdType()
            {
                std::string idType;
                UA_StatusCode scode;

                auto identificationIdType
                {
                    this->getServices().translateBrowsePathToNodeId(
                        metamodel::IAASIdentifiableType::getIdentificationIdTypePath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId())
                };

                scode = this->getServices().readValue(identificationIdType, idType);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getIdentificationIdType -" + shared::diag::getErrorString(scode));
                }

                return IdTypefromString(idType);
            }

            template<typename CONNECTOR_TYPE>
            void AASSubModel<CONNECTOR_TYPE>::setKind(const Kind& t_kind)
            {
                UA_StatusCode scode;

                auto kindNode
                {
                   this->getServices().translateBrowsePathToNodeId(
                       metamodel::AASSubModelType::getKindPath(this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };

                scode = this->getServices().writeValue(kindNode, KindtoString(t_kind));

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setKind -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            Kind AASSubModel<CONNECTOR_TYPE>::getKind()
            {
                std::string kindType;
                UA_StatusCode scode;

                auto kindNode
                {
                   this->getServices().translateBrowsePathToNodeId(
                       metamodel::AASSubModelType::getKindPath(this->getConnector().getNamespaceIndexdefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };

                scode = this->getServices().readValue(kindNode, kindType);
                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getKind -" + shared::diag::getErrorString(scode));
                }
                return KindfromString(kindType);
            }
        }
    }
}

#endif