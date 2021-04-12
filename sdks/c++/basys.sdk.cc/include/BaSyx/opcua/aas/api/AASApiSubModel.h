#ifndef AAS_API_SUBMODEL_H
#define AAS_API_SUBMODEL_H

#include <BaSyx/opcua/client/Client.h>
#include <BaSyx/opcua/client/ClientServices.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/aas/metamodel/AASMetamodel.h>
#include <BaSyx/opcua/aas/model/AASSubModel.h>
#include <BaSyx/opcua/aas/model/AASSubModelElement.h>
#include <BaSyx/opcua/aas/model/AASProperty.h>
#include <BaSyx/opcua/aas/api/AASApiNode.h>
#include <BaSyx/opcua/aas/api/AASApiMetamodelHelpers.h>
#include <BaSyx/opcua/aas/api/AASApiSubModelHelpers.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE>
            class AASApiSubModel final : public AASApiNode<CONNECTOR_TYPE>
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASApiSubmodel";
            public:
                explicit AASApiSubModel(CONNECTOR_TYPE& t_connector, const NodeId& t_parentNode) :
                    AASApiNode<CONNECTOR_TYPE>(t_connector, t_parentNode, loggerName) {}

                virtual ~AASApiSubModel() = default;

                ApiResponse addSubModel(const std::string& t_aasIdentifier,
                    std::shared_ptr<SubModel> t_subModel);

                ApiResponse deleteSubModel(const std::string & t_aasIdentifier,
                    const std::string & t_subModelIdentifier);

                std::shared_ptr<basyx::submodel::api::ISubModel> getSubModel(const std::string & t_aasIdentifier,
                    const std::string & t_subModelIdShort);

                std::vector<std::shared_ptr<basyx::submodel::api::ISubModel>> getAllSubModels(const std::string& t_aasIdentifier);

                static void createSubModel(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const NodeId& t_parenAASNode,
                    const std::string& t_langCode,
                    const SubModel & t_subModel);

                static std::unique_ptr<basyx::submodel::api::ISubModel> extractSubModel(CONNECTOR_TYPE& t_connector,
                    const NodeId & t_submodelNode);
            };

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASApiSubModel<CONNECTOR_TYPE>::addSubModel(const std::string & t_aasIdentifier,
                std::shared_ptr<SubModel> t_subModel)
            {
                if (t_subModel)
                {
                    auto aasNode = AASApiNodeIdHelpers::getAssetAdministrationShellNode(this->getConnector(), this->getRootNode(), t_aasIdentifier);

                    /* In case if submodel is already existing , delete it (UPDATE) */
                    if (AASApiNodeIdHelpers::doesSubModelExists(this->getConnector(), aasNode, t_subModel->getIdShort()))
                    {
                        auto smNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(), this->getRootNode(), t_aasIdentifier, t_subModel->getIdShort());

                        auto status = this->getServices().deleteNode(smNode);

                        if (UA_STATUSCODE_GOOD != status)
                        {
                            basyx::log::topic(loggerName).error("AssetAdministrationShell [" +
                                t_aasIdentifier + "] Submodel [" + t_subModel->getIdShort() + "] cannot be deleted : " +
                                shared::diag::getErrorString(status));

                            return ApiResponse::NOT_OK_NOT_FOUND;
                        }

                        basyx::log::topic(loggerName).trace("AssetAdministrationShell [" +
                            t_aasIdentifier + "] Submodel [" + t_subModel->getIdShort() + "] deleted");

                    }

                    if (aasNode.isNull())
                    {
                        this->getLogger().error("AssetAdministrationShell [" + t_aasIdentifier + "] not found");

                        return ApiResponse::NOT_OK_NOT_FOUND;
                    }

                    createSubModel(this->getConnector(), this->getRootNode(), aasNode, "en-US", *t_subModel);

                    this->getLogger().trace("Submodel [" + t_subModel->getIdShort() + "] added");

                    return ApiResponse::OK;
                }

                this->getLogger().info("Submodel [" + t_subModel->getIdentification().getId() + "] empty");

                return ApiResponse::NOT_OK_EMPTY_DATA;
            }

            template<typename CONNECTOR_TYPE>
            constexpr char AASApiSubModel<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASApiSubModel<CONNECTOR_TYPE>::deleteSubModel(const std::string & t_aasIdentifier,
                const std::string & t_subModelIdentifier)
            {
                auto services = util::make_unique<Services<CONNECTOR_TYPE>>(this->getConnector());

                auto subModelNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(), this->getRootNode(), t_aasIdentifier, t_subModelIdentifier);

                if (subModelNode.isNull()) {

                    std::string errStr(
                        "AssetAdministrationShell [" +
                        t_aasIdentifier +
                        "] Submodel [" +
                        t_subModelIdentifier +
                        "]not found"
                    );

                    this->getLogger().error(errStr);

                    return ApiResponse::NOT_OK_EMPTY_DATA;
                }

                auto status = services->deleteNode(subModelNode);

                if (status != UA_STATUSCODE_GOOD)
                {

                    std::string errStr("AssetAdministrationShell [" +
                        t_aasIdentifier +
                        "] Submodel [" +
                        t_subModelIdentifier +
                        "] cannot be deleted - " +
                        std::string(UA_StatusCode_name(status)));

                    this->getLogger().error(errStr);

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                this->getLogger().trace("AssetAdministrationShell [" +
                    t_aasIdentifier +
                    "] Submodel [" +
                    t_subModelIdentifier +
                    "] deleted ");

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            inline std::shared_ptr<basyx::submodel::api::ISubModel> AASApiSubModel<CONNECTOR_TYPE>::getSubModel(const std::string & t_aasIdentifier,
                const std::string & t_submodelIdshort)
            {
                auto aasSubModelNode = AASApiNodeIdHelpers::getSubModelNode(this->getConnector(), this->getRootNode(), t_aasIdentifier, t_submodelIdshort);

                if (aasSubModelNode.isNull())
                {
                    this->getLogger().error("AssetAdministrationShell [" +
                        t_aasIdentifier +
                        "] Submodel [" +
                        t_submodelIdshort +
                        "] does not exists ");

                    return nullptr;
                }

                return extractSubModel(this->getConnector(), aasSubModelNode);
            }

            template<typename CONNECTOR_TYPE>
            inline std::vector<std::shared_ptr<basyx::submodel::api::ISubModel>> AASApiSubModel<CONNECTOR_TYPE>::getAllSubModels(const std::string & t_aasIdentifier)
            {
                std::vector<std::shared_ptr<basyx::submodel::api::ISubModel>> ret;

                auto aasNode = AASApiNodeIdHelpers::getAssetAdministrationShellNode(this->getConnector(), this->getRootNode(), t_aasIdentifier);

                if (aasNode.isNull())
                {
                    this->getLogger().error("AssetAdministrationShell [" +
                        t_aasIdentifier +
                        "] does not exists ");
                    return ret;
                }

                for (const auto& subModelNode : AASApiNodeIdHelpers::getAllSubModelNodes(this->getConnector(), aasNode))
                {
                    auto subModel = extractSubModel(this->getConnector(), subModelNode);
                    if (subModel != nullptr) {
                        ret.emplace_back(std::move(subModel));
                    }
                }
                return ret;
            }

            template<typename CONNECTOR_TYPE>
            inline void AASApiSubModel<CONNECTOR_TYPE>::createSubModel(CONNECTOR_TYPE& t_connector,
                const NodeId& t_node,
                const NodeId & t_parenAASNode,
                const std::string & t_langCode,
                const SubModel & t_subModel)
            {
                NodeId parentAASNode = t_parenAASNode;

                if (!t_parenAASNode.isNull())
                {
                    auto idShortSM = t_subModel.getIdShort();
                    /*Create AAS */
                    auto subModelOPCUA
                    {
                        std::make_shared<AASSubModel<CONNECTOR_TYPE>>(
                            t_connector,
                            t_parenAASNode,
                            idShortSM,
                            t_subModel.getIdentification()
                        )
                    };

                    subModelOPCUA->createNew();

                    auto versionVal = t_subModel.getAdministrativeInformation().getVersion();
                    auto revisionVal = t_subModel.getAdministrativeInformation().getRevision();
                    /* Set Administration */
                    if (versionVal != nullptr)
                        subModelOPCUA->setAdminstrationRevision(*versionVal);
                    if (revisionVal != nullptr)
                        subModelOPCUA->setAdminstrationVersion(*revisionVal);
                    /* Set Indentification */
                    subModelOPCUA->setIdentificationId(t_subModel.getIdentification().getId());
                    subModelOPCUA->setIdentificationIdType(t_subModel.getIdentification().getIdType());
                    /* Set Kind */
                    subModelOPCUA->setKind(t_subModel.getKind());

                    /* Handle different SubModelElement Types */
                    for (int i = 0; i < t_subModel.submodelElements().size(); ++i)
                    {
                        const auto & subModelElement = t_subModel.submodelElements().getElement(i);

                        /* Property SubModelElement*/
                        if (AASApiMetamodelHelpers::IsProperty(subModelElement))
                        {
                            auto propertyOPCUA
                            {
                                std::make_shared<AASProperty<CONNECTOR_TYPE>>(
                                    t_connector,
                                    subModelOPCUA->getNodeId(),
                                    subModelElement->getIdShort()
                                )
                            };

                            propertyOPCUA->createNew();
                            propertyOPCUA->setKind(subModelElement->getKind());
                            propertyOPCUA->setReferableIdShort(subModelElement->getIdShort());
                            propertyOPCUA->setReferableCategory((subModelElement->getCategory() == nullptr) ? std::string() :
                                *subModelElement->getCategory());

                            for (const auto& key : AASApiMetamodelHelpers::getReferenceKeys(subModelElement))
                            {
                                propertyOPCUA->addValueIdKeys(key);
                            }

                            AASApiSubModelHelpers::addPropertyToSubmodel(*propertyOPCUA.get(), subModelElement);
                        }
                        /* Other SubModelElements handling */
                    }
                }
            }

            template<typename CONNECTOR_TYPE>
            inline std::unique_ptr<basyx::submodel::api::ISubModel> AASApiSubModel<CONNECTOR_TYPE>::extractSubModel(CONNECTOR_TYPE& t_connector,
                const NodeId & t_submodelNode)
            {
                UA_StatusCode status = UA_STATUSCODE_GOOD;

                Services<CONNECTOR_TYPE> services(t_connector);

                std::vector<std::tuple<std::string, NodeId>> inputSignature, outputSignature;

                auto opcuaSubModel = std::make_shared<AASSubModel<CONNECTOR_TYPE>>(t_connector, t_submodelNode);

                auto subModel{
                    util::make_unique<SubModel>(
                        opcuaSubModel->getIdShort(),
                        Identifier(opcuaSubModel->getIdentificationIdType(), opcuaSubModel->getIdentificationId())
                    )
                };

                subModel->getAdministrativeInformation().setRevision(opcuaSubModel->getAdminstrationRevision());
                subModel->getAdministrativeInformation().setVersion(opcuaSubModel->getAdminstrationVersion());

                /* Handle SubmodelElements here */
                /* Properties */
                for (auto& propertyNode : AASApiNodeIdHelpers::getAllPropertyNodes(t_connector, t_submodelNode))
                {
                    auto propertyOPCUA = std::make_shared<AASProperty<CONNECTOR_TYPE>>(t_connector, propertyNode, t_submodelNode);

                    BrowsePath path({ services.getBrowseNameFromNodeId(propertyNode),
                        BrowseName(t_connector.getNamespaceIndexDefault(), metamodel::AASPropertyType::AttrNames::BrowseText_Value) });

                    auto propertyValueNode = services.translateBrowsePathToNodeId(path, propertyNode);

                    std::string smElementIdShort = propertyOPCUA->getReferableIdShort();
                    auto category = propertyOPCUA->getReferableCategory();
                    /* TODO : The below attributes cannot be set*/
                    //auto kind     = propertyOPCUA->getKind();
                    Reference reference(propertyOPCUA->getValueIdKeys());

                    auto valueType = propertyOPCUA->getValueType();

                    auto value = propertyOPCUA->getValue();

                    AASApiSubModelHelpers::updateSubmodelProperties(*subModel.get(), smElementIdShort, value, valueType, category, reference);

                }
                /* Other SubmodelElement should be managed here */
                return subModel;
            }

        }
    }
}
#endif