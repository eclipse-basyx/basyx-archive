#ifndef AAS_API_ASSET_ADMINISTRATION_SHELL_H
#define AAS_API_ASSET_ADMINISTRATION_SHELL_H

#include <BaSyx/opcua/aas/api/AASApiNode.h>
#include <BaSyx/opcua/aas/api/AASApiSubModel.h>
#include <BaSyx/opcua/aas/model/AASAssetAdministrationShell.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE>
            class AASApiAssetAdminstrationShell final : public AASApiNode<CONNECTOR_TYPE>
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASApiAssetAdminstrationShell";
            public:
                explicit AASApiAssetAdminstrationShell(CONNECTOR_TYPE& t_connector, NodeId t_rootNode) :
                    AASApiNode<CONNECTOR_TYPE>(t_connector, t_rootNode, loggerName) {}

                virtual ~AASApiAssetAdminstrationShell() = default;

                ApiResponse createAssetAdministrationShell(std::shared_ptr<IAssetAdministrationShell> t_aas);

                ApiResponse deleteAssetAdministrationShell(const std::string& t_identifier);

                std::shared_ptr<IAssetAdministrationShell> getAssetAdministrationShell(const std::string& t_aasIdentifier);

                Collection_t<IAssetAdministrationShell> getAllAssetAdministrationShells();

            private:
                std::shared_ptr<IAssetAdministrationShell> extractAssetAdministrationShell(const NodeId& t_aasNode);
            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASApiAssetAdminstrationShell<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            ApiResponse AASApiAssetAdminstrationShell<CONNECTOR_TYPE>::createAssetAdministrationShell(std::shared_ptr<IAssetAdministrationShell> t_aas)
            {
                if (t_aas)
                {
                    auto aasNode = AASApiNodeIdHelpers::getAssetAdministrationShellNode(this->getConnector(), this->getRootNode(), t_aas->getIdentification().getId());

                    if (!aasNode.isNull())
                    {
                        auto status = this->getServices().deleteNode(aasNode);

                        if (status != UA_STATUSCODE_GOOD)
                        {
                            this->getLogger().error("AssetAdministrationShell [" + t_aas->getIdentification().getId() + "] cannot not be updated : " +
                                shared::diag::getErrorString(status));
                        }

                        this->getLogger().trace("AssetAdministrationShell [" + t_aas->getIdentification().getId() + "] is deleted");
                    }

                    auto aasOPCUA
                    {
                        util::make_unique<AASAssetAdministrationShell<CONNECTOR_TYPE>>(
                            this->getConnector(),
                            t_aas->getIdShort(),
                            t_aas->getIdentification()
                        )
                    };

                    if (aasOPCUA == nullptr)
                    {
                        this->getLogger().error("AssetAdministrationShell could not be created");

                        return ApiResponse::NOT_OK_INTERNAL_ERROR;
                    }

                    aasOPCUA->createNew();

                    auto versionVal = t_aas->getAdministrativeInformation().getVersion();
                    auto revisionVal = t_aas->getAdministrativeInformation().getRevision();

                    if (versionVal != nullptr)
                        aasOPCUA->setAdminstrationRevision(*versionVal);
                    if (revisionVal != nullptr)
                        aasOPCUA->setAdminstrationVersion(*revisionVal);

                    aasOPCUA->setIdentificationId(t_aas->getIdentification().getId());
                    aasOPCUA->setIdentificationIdType(t_aas->getIdentification().getIdType());

                    this->getLogger().info("AssetAdministrationShell [" +
                        t_aas->getIdentification().getId() +
                        "] created");

                    return ApiResponse::OK;
                }

                this->getLogger().error("AssetAdministrationShell could not be created - empty input");

                return ApiResponse::NOT_OK_MALFORMED_BODY;
            }

            template<typename CONNECTOR_TYPE>
            ApiResponse AASApiAssetAdminstrationShell<CONNECTOR_TYPE>::deleteAssetAdministrationShell(const std::string & t_identifier)
            {
                auto aasNode = AASApiNodeIdHelpers::getAssetAdministrationShellNode(this->getConnector(), this->getRootNode(), t_identifier);

                if (aasNode.isNull())
                {
                    this->getLogger().error("AssetAdministrationShell [" +
                        t_identifier +
                        "] not found");
                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                auto status = this->getServices().deleteNode(aasNode);

                if (status != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("AssetAdministrationShell[" +
                        t_identifier +
                        "] cannot be deleted " +
                        std::string(UA_StatusCode_name(status)));

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                this->getLogger().trace("AssetAdministrationShell [" + t_identifier + "] deleted");

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            std::shared_ptr<IAssetAdministrationShell> AASApiAssetAdminstrationShell<CONNECTOR_TYPE>::getAssetAdministrationShell(const std::string & t_aasIdentifier)
            {
                auto aasNode = AASApiNodeIdHelpers::getAssetAdministrationShellNode(this->getConnector(), this->getRootNode(), t_aasIdentifier);

                if (aasNode.isNull()) {
                    this->getLogger().error("AssetAdministrationShell [" +
                        t_aasIdentifier +
                        "] not found");
                    return nullptr;
                }

                return extractAssetAdministrationShell(aasNode);
            }

            template<typename CONNECTOR_TYPE>
            std::shared_ptr<IAssetAdministrationShell> AASApiAssetAdminstrationShell<CONNECTOR_TYPE>::extractAssetAdministrationShell(const NodeId & t_aasNode)
            {
                auto opcuaAAS = std::make_shared<AASAssetAdministrationShell<CONNECTOR_TYPE>>(this->getConnector(), t_aasNode);

                /* This will be replaced by the real AssetObject once Asset class is implmented in OPCUA */
                Asset dummyAsset(std::string(), Identifier::Custom(std::string()));

                auto aas
                {
                    std::make_shared<AssetAdministrationShell>(
                        opcuaAAS->getIdShort(),
                        Identifier(opcuaAAS->getIdentificationIdType(), opcuaAAS->getIdentificationId()),
                        dummyAsset
                    )
                };

                aas->getAdministrativeInformation().setRevision(opcuaAAS->getAdminstrationRevision());
                aas->getAdministrativeInformation().setVersion(opcuaAAS->getAdminstrationVersion());

                auto aasSubModelNodes = AASApiNodeIdHelpers::getAllSubModelNodes(this->getConnector(), t_aasNode);

                if (aasSubModelNodes.empty())
                {
                    this->getLogger().info("no subModels found");
                    return aas;
                }

                for (auto& aasSubModelNode : aasSubModelNodes)
                {
                    auto extractedSM = AASApiSubModel<CONNECTOR_TYPE>::extractSubModel(this->getConnector(), aasSubModelNode);

                    if (extractedSM)
                    {
                        aas->getSubmodels().addElement(std::move(extractedSM));
                    }
                    else
                    {
                        this->getLogger().error("input empty");
                    }
                }

                return aas;
            }

            template<typename CONNECTOR_TYPE>
            Collection_t<IAssetAdministrationShell> AASApiAssetAdminstrationShell<CONNECTOR_TYPE>::getAllAssetAdministrationShells()
            {
                std::vector<std::shared_ptr<IAssetAdministrationShell>> ret;

                auto aasNodes = this->getServices().getChildReferencesWithType(
                    this->getRootNode(),
                    metamodel::AASAssetAdministrationShellType::getNodeId(this->getConnector().getNamespaceIndexDefault()),
                    NodeId::numeric(UA_NS0ID_HASCOMPONENT)
                );

                for (auto& aasNode : aasNodes)
                {
                    auto aas = extractAssetAdministrationShell(aasNode);

                    if (aas != nullptr)
                    {
                        ret.push_back(std::move(aas));
                    }
                }

                return ret;
            }

        }
    }
}
#endif