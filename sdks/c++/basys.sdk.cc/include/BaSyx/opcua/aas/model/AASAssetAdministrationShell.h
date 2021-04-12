#ifndef AAS_ASSET_ADMINISTRATION_SHELL_H
#define AAS_ASSET_ADMINISTRATION_SHELL_H

#include <BaSyx/opcua/aas/model/AASModelNode.h>
#include <BaSyx/opcua/aas/metamodel/AASAssetAdministrationShellType.h>
#include <BaSyx/opcua/aas/metamodel/IAASIdentifiableType.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE = basyx::opcua::Client>
            class AASAssetAdministrationShell : public AASModelNode<CONNECTOR_TYPE>
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASAssetAdministrationShell";
            public:
                ~AASAssetAdministrationShell() = default;
                AASAssetAdministrationShell() = delete;

                AASAssetAdministrationShell(CONNECTOR_TYPE& t_connector,
                    const std::string& t_idShort,
                    const Identifier& t_identifier,
                    const NodeDescription& t_description) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        NodeId::nullNode(),
                        NodeId::numeric(UA_NS0ID_OBJECTSFOLDER),
                        t_idShort,
                        std::string(),
                        t_description,
                        loggerName),
                    m_identifier(t_identifier) {}

                AASAssetAdministrationShell(CONNECTOR_TYPE& t_connector,
                    const std::string& t_idShort,
                    const Identifier& t_identifier) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        NodeId::nullNode(),
                        NodeId::numeric(UA_NS0ID_OBJECTSFOLDER),
                        t_idShort,
                        std::string(),
                        NodeDescription(),
                        loggerName),
                    m_identifier(t_identifier) {}

                AASAssetAdministrationShell(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_aasNode) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        t_aasNode,
                        NodeId::numeric(UA_NS0ID_OBJECTSFOLDER),
                        std::string(),
                        std::string(),
                        NodeDescription(),
                        loggerName)
                {
                    Services<CONNECTOR_TYPE> services(t_connector);

                    this->setIdShort(shared::string::getInstanceName(
                        services.getBrowseNameFromNodeId(t_aasNode).getText())
                    );
                }

                void createNew();
                void setAdminstrationVersion(const std::string& t_version);
                void setAdminstrationRevision(const std::string& t_revision);
                void setIdentificationId(const std::string& t_id);
                void setIdentificationIdType(const AASIdtype& t_idType);

                std::string getAdminstrationVersion();
                std::string getAdminstrationRevision();
                std::string getIdentificationId();
                AASIdtype getIdentificationIdType();

            private:
                Identifier m_identifier;
            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASAssetAdministrationShell<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            void AASAssetAdministrationShell<CONNECTOR_TYPE>::createNew()
            {
                NodeId self;
                UA_StatusCode scode;

                auto instanceName
                {
                    shared::string::createPropertyName(metamodel::AASAssetAdministrationShellType::AttrNames::InstanceName, this->getIdShort())
                };

                scode = this->getServices().addObjectNode(
                    this->getParentNodeId(),
                    metamodel::AASAssetAdministrationShellType::getNodeId(this->getConnector().getNamespaceIndexDefault()),
                    NodeId::numeric(UA_NS0ID_HASCOMPONENT),
                    QualifiedName(this->getConnector().getNamespaceIndexDefault(), instanceName),
                    ObjectAttributes(instanceName, instanceName, this->getNodeDescription().getLangCode()),
                    self
                );

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("createNew -" + shared::diag::getErrorString(scode));
                    return;
                    return;
                }
                this->setNodeId(self);
                this->setIdentificationId(m_identifier.getId());
                this->setIdentificationIdType(m_identifier.getIdType());
            }

            template<typename CONNECTOR_TYPE>
            void AASAssetAdministrationShell<CONNECTOR_TYPE>::setAdminstrationVersion(const std::string& t_version)
            {
                UA_StatusCode scode;

                NodeId adminVersionNode = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getAdministrationVersionPath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId()
                );

                scode = this->getServices().writeValue(adminVersionNode, t_version);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setAdminstrationVersion -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASAssetAdministrationShell<CONNECTOR_TYPE>::setAdminstrationRevision(const std::string& t_revision)
            {
                UA_StatusCode scode;

                auto adminRevisonNode = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getAdministrationRevisionPath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId()
                );

                scode = this->getServices().writeValue(adminRevisonNode, t_revision);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setAdminstrationRevision -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASAssetAdministrationShell<CONNECTOR_TYPE>::setIdentificationId(const std::string& t_id)
            {
                UA_StatusCode scode;
                auto identificationId = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getIdentificationIdPath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId()
                );

                scode = this->getServices().writeValue(identificationId, t_id);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setIdentificationId -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASAssetAdministrationShell<CONNECTOR_TYPE>::setIdentificationIdType(const AASIdtype& _idType)
            {
                UA_StatusCode scode;

                auto identificationIdType = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getIdentificationIdTypePath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId()
                );

                scode = this->getServices().writeValue(identificationIdType, IdTypetoString(_idType));

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("setIdentificationIdType -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            std::string AASAssetAdministrationShell<CONNECTOR_TYPE>::getAdminstrationVersion()
            {
                std::string version;
                UA_StatusCode scode;

                auto adminVersionNode = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getAdministrationVersionPath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId()
                );

                scode = this->getServices().readValue(adminVersionNode, version);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getAdminstrationVersion -" + shared::diag::getErrorString(scode));
                }
                return version;
            }

            template<typename CONNECTOR_TYPE>
            std::string AASAssetAdministrationShell<CONNECTOR_TYPE>::getAdminstrationRevision()
            {
                std::string revision;
                std::string version;
                UA_StatusCode scode;

                auto adminRevisonNode = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getAdministrationRevisionPath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId());

                scode = this->getServices().readValue(adminRevisonNode, revision);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getAdminstrationRevision -" + shared::diag::getErrorString(scode));
                }

                return revision;
            }

            template<typename CONNECTOR_TYPE>
            std::string AASAssetAdministrationShell<CONNECTOR_TYPE>::getIdentificationId()
            {
                std::string id;
                UA_StatusCode scode;

                auto identificationId = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getIdentificationIdPath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId());

                scode = this->getServices().readValue(identificationId, id);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getIdentificationId -" + shared::diag::getErrorString(scode));
                }
                return id;
            }

            template<typename CONNECTOR_TYPE>
            AASIdtype AASAssetAdministrationShell<CONNECTOR_TYPE>::getIdentificationIdType()
            {
                std::string idType;
                UA_StatusCode scode;

                auto identificationIdType = this->getServices().translateBrowsePathToNodeId(
                    metamodel::IAASIdentifiableType::getIdentificationIdTypePath(
                        this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                    this->getNodeId()
                );

                scode = this->getServices().readValue(identificationIdType, idType);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("getIdentificationIdType -" + shared::diag::getErrorString(scode));
                }
                return IdTypefromString(idType);
            }
        }
    }
}

#endif