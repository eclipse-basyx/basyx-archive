#ifndef SUBMODEL_PROVIDER_HELPERS_H
#define SUBMODEL_PROVIDER_HELPERS_H

#include <BaSyx/vab/core/util/VABPath.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/aas/metamodel/AASMetamodelAliases.h>
#include <BaSyx/opcua/aas/api/AASApiSubModel.h>
#include <BaSyx/opcua/aas/api/AASApiMetamodelHelpers.h>
#include <BaSyx/opcua/aas/api/AASApiSubModelElement.h>
#include <BaSyx/opcua/aas/provider/AASModelProviderCommon.h>
#include <BaSyx/opcua/aas/provider/AASProviderApiParseHelpers.h>
#include <BaSyx/opcua/typesmap/TypesTransformer.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            using namespace basyx::xsd_types;
            using namespace basyx::submodel::simple;

            class AASSubmodelProviderHelpers
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASSubmodelProvider";
                static constexpr const char PropPostFix[] = "_prop";
            public:

                template<typename CONNECTOR_TYPE>
                static ApiResponse createSubModel(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers & t_id,
                    const basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse readSubModelIdentifier(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers & t_id,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse readSubModelValues(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers & t_id,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse readSubmodels(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse readSubModelElement(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse readSubModelElements(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers & t_id,
                    basyx::object & t_body);

                //template<typename CONNECTOR_TYPE>
                //static ApiResponse readSubModelElementIdShort(CONNECTOR_TYPE& t_connector,
                //	const NodeId& t_rootNode,
                //	const identifiers & t_id,
                //	basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse readProperty(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers & t_id,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                inline ApiResponse updateSubModel(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers & t_id,
                    basyx::object & t_smBody);

                template<typename CONNECTOR_TYPE>
                static ApiResponse readPropertyValue(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    basyx::object& t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse writePropertyValue(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse getAllProperties(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    basyx::object& t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse getAllOperations(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static basyx::object fetchAllSubModelElements(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string& t_identifierAAS,
                    const std::string& t_identifierSM);

                template<typename CONNECTOR_TYPE>
                static ApiResponse invokeOperation(const identifiers& t_id,
                    const NodeId& t_rootNode,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse deleteSubModel(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers & t_id);

                template<typename CONNECTOR_TYPE>
                static ApiResponse createSubmodelelement(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    const basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse createProperty(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    const basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse createOperation(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id,
                    const basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse deleteSubmodelElement(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const identifiers& t_id);

            };

            constexpr char AASSubmodelProviderHelpers::loggerName[];
            constexpr char AASSubmodelProviderHelpers::PropPostFix[];

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::createSubModel(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                const basyx::object & t_body)
            {
                basyx::object body{ t_body };

                auto modelNameObj = body.getProperty(Element::modelType)
                    .getProperty(Element::name);

                if (modelNameObj.IsError())
                {
                    basyx::log::topic(loggerName).error(modelNameObj.getErrorMessage());

                    return ApiResponse::NOT_OK_MALFORMED_BODY;
                }

                if (modelNameObj.Get<std::string&>() != Element::submodel)
                {
                    basyx::log::topic(loggerName).error("Submodel body is malformed");

                    return ApiResponse::NOT_OK_MALFORMED_BODY;
                }

                auto identifierObj = body.getProperty(Element::identifier);

                const std::string& idShort = body.getProperty(Element::idShort).Get<std::string&>();

                if (idShort != std::get<SM_ID>(t_id))
                {
                    basyx::log::topic(loggerName).error("Submodel idshort in the path and model are not same");

                    return ApiResponse::NOT_OK_MALFORMED_REQ;
                }
                
                auto submodel = AASApiMetamodelHelpers::buildSubModelFromObject(body);

                if (submodel == nullptr)
                {
                    basyx::log::topic(loggerName).error("AssetAdministrationShell [" +
                        std::get<AAS_ID>(t_id) + "] Submodel [" + std::get<SM_ID>(t_id) + "] SubmodelElements cannot be extracted");

                    return ApiResponse::NOT_OK_MALFORMED_BODY;
                }

                std::shared_ptr<SubModel> subModelToApi = std::move(submodel);

                /* FIXME : use the Submodel constructor here
                This will remove the functions buildSubModelFromObject() completely
                auto subModelToApi{ std::make_shared<SubModel>(body
                    idShort,
                    AASProviderApiParseHelpers::buildIdentifers(body.getProperty(Element::identifier)),
                    body
                    )
                };
                */

                auto api{ util::make_unique<AASApiSubModel<CONNECTOR_TYPE>>(t_connector, t_rootNode) };

                return api->addSubModel(std::get<AAS_ID>(t_id), std::move(subModelToApi));
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::readSubModelIdentifier(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                auto api = util::make_unique<AASApiSubModel<CONNECTOR_TYPE>>(t_connector, t_rootNode);

                auto submodel{ api->getSubModel(std::get<AAS_ID>(t_id), std::get<SM_ID>(t_id)) };

                if (submodel == nullptr)
                {
                    return ApiResponse::NOT_OK_NOT_FOUND;
                }

                if (auto smObj{ dynamic_cast<SubModel*>(submodel.get()) })
                {
                    t_body = smObj->getMap();
                }

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::readSubModelValues(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id, basyx::object & t_body)
            {
                basyx::object body;

                auto response = readSubModelElement(t_connector, t_rootNode, t_id, body);

                if (response != ApiResponse::OK)
                    return response;

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::readSubmodels(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                auto aasNode{ AASApiNodeIdHelpers::getAssetAdministrationShellNode(t_connector, t_rootNode, std::get<AAS_ID>(t_id)) };

                if (aasNode.isNull())
                {
                    basyx::log::topic(loggerName).error("AssetAdmintrationShell not found");

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                auto api = util::make_unique<AASApiSubModel<CONNECTOR_TYPE>>(
                    t_connector,
                    t_rootNode);

                auto submodels{ api->getAllSubModels(std::get<AAS_ID>(t_id)) };

                auto smList = basyx::object::make_list<basyx::object>();

                for (const auto& submodel : submodels)
                {
                    if (auto smObj{ dynamic_cast<SubModel*>(submodel.get()) })
                    {
                        smList.insert(smObj->getMap());
                    }
                    else
                    {
                        basyx::log::topic(loggerName).error("Submodel identifier cannot be read");

                        return ApiResponse::NOT_OK_MALFORMED_BODY;
                    }
                }

                t_body = smList;

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::readSubModelElement(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                t_body = basyx::object::make_null();

                auto subModelElements = fetchAllSubModelElements<CONNECTOR_TYPE>(t_connector,
                    t_rootNode,
                    std::get<AAS_ID>(t_id),
                    std::get<SM_ID>(t_id)
                    );

                if (subModelElements.getError() == basyx::object::error::ProviderException)
                {
                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }
                else if (subModelElements.getError() == basyx::object::error::ProviderException)
                {
                    return ApiResponse::NOT_OK_NOT_FOUND;
                }

                t_body = subModelElements.getProperty(std::get<SM_ELE_ID>(t_id));

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::readSubModelElements(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                auto aasNode{ AASApiNodeIdHelpers::getAssetAdministrationShellNode(t_connector, t_rootNode, std::get<AAS_ID>(t_id)) };

                if (aasNode.isNull())
                {
                    basyx::log::topic(loggerName).error("AssetAdmintrationShell not found");

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                auto api = util::make_unique<AASApiSubModel<CONNECTOR_TYPE>>(t_connector, t_rootNode);

                auto submodel{ api->getSubModel(std::get<AAS_ID>(t_id), std::get<SM_ID>(t_id)) };

                if (auto smObj{ dynamic_cast<SubModel*>(submodel.get()) })
                {
                    t_body = smObj->getMap().getProperty(Element::submodelElements);

                    return ApiResponse::OK;
                }

                basyx::log::topic(loggerName).error("Submodel identifier cannot be read");

                return ApiResponse::NOT_OK_INTERNAL_ERROR;
            }

            //template<typename CONNECTOR_TYPE>
            //inline ApiResponse AASSubmodelProviderHelpers::readSubModelElementIdShort(CONNECTOR_TYPE & t_connector,
            //	const NodeId & t_rootNode,
            //	const identifiers & t_id,
            //	basyx::object & t_body)
            //{
            //	basyx::object body;
            //
            //	auto response = readSubModelElement(t_connector, t_rootNode, t_id, body);
            //
            //	if (response != ApiResponse::OK)
            //	{
            //		return response;
            //	}
            //
            //	t_body = body.getProperty(std::get<SM_ELE_ID>(t_id));
            //
            //	return response;
            //}

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::readProperty(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                return readSubModelElementIdShort(t_connector, t_rootNode, t_id, t_body);
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::updateSubModel(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_smBody)
            {
                auto deleteResponse = deleteSubModel(t_connector, t_id);

                if (deleteResponse != ApiResponse::OK)
                {
                    return deleteResponse;
                }

                return createSubModel(t_connector, t_id, t_rootNode, t_smBody);
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::readPropertyValue(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                basyx::object body;

                auto response = readSubModelElement(t_connector, t_rootNode, t_id, body);

                if (response != ApiResponse::OK)
                {
                    return response;
                }

                t_body = body.getProperty(Element::value);

                return response;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::writePropertyValue(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                using namespace basyx::xsd_types;
                using namespace basyx::submodel::simple;

                basyx::object body{ t_body };

                if (body.IsError() || body.IsNull())
                    return ApiResponse::NOT_OK_MALFORMED_BODY;

                auto& identifierTup = t_id;

                auto api = util::make_unique<AASApiSubModelElement<CONNECTOR_TYPE>>(t_connector, t_rootNode);

                auto aasId{ std::get<AAS_ID>(identifierTup) };
                auto smId{ std::get<SM_ID>(identifierTup) };
                auto smEleId{ std::get<SM_ELE_ID>(identifierTup) };

                auto valueType = api->getPropertyValueType(t_rootNode, aasId, smId, smEleId);

                if (valueType == metamodel::AASPropertyType::TypeName::bool_)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<bool>(t_body.Get<bool>()));

                }
                else if (valueType == metamodel::AASPropertyType::TypeName::int16)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<int16_t>(t_body.Get<int16_t>()));
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::int32)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<int32_t>(t_body.Get<int32_t>()));
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::float_)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<float>(t_body.Get<float>()));
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::double_)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<double>(t_body.Get<double>()));
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::string_)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, t_body.Get<std::string>());
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::int64)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<int64_t>(t_body.Get<int64_t>()));
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::uint16)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<uint16_t>(t_body.Get<uint16_t>()));
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::uint32)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<uint32_t>(t_body.Get<uint32_t>()));
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::uint64)
                {
                    return api->updateProperty(aasId,
                        smId, smEleId, TypesTransformer::numeral_cast<uint64_t>(t_body.Get<uint64_t>()));
                }
                else if ((valueType == metamodel::AASPropertyType::TypeName::anyUri) ||
                    (valueType == metamodel::AASPropertyType::TypeName::date) ||
                    (valueType == metamodel::AASPropertyType::TypeName::dateTime) ||
                    (valueType == metamodel::AASPropertyType::TypeName::dayTimeDuration) ||
                    (valueType == metamodel::AASPropertyType::TypeName::yearMonthDuration) ||
                    (valueType == metamodel::AASPropertyType::TypeName::time) ||
                    (valueType == metamodel::AASPropertyType::TypeName::gYearMonth) ||
                    (valueType == metamodel::AASPropertyType::TypeName::gYear) ||
                    (valueType == metamodel::AASPropertyType::TypeName::gMonthDay) ||
                    (valueType == metamodel::AASPropertyType::TypeName::gDay) ||
                    (valueType == metamodel::AASPropertyType::TypeName::gMonth))
                {

                    return api->updateProperty(aasId,
                        smId, smEleId, t_body.Get<std::string>());
                }

                return ApiResponse::NOT_SUPPORTED;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::getAllProperties(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                auto subModelElements = fetchAllSubModelElements(t_connector, std::get<AAS_ID>(t_id), std::get<SM_ID>(t_id));

                if (subModelElements.isError() || subModelElements.isNull())
                    return ApiResponse::NOT_OK_MALFORMED_BODY;

                auto ret = basyx::object::make_list<basyx::object>();

                for (const auto& subModelElement : subModelElements.template Get<basyx::object::object_map_t>())
                {
                    auto smElementObj = basyx::object(subModelElement.second);

                    auto modelName = smElementObj.getProperty(Element::modelType)
                        .getProperty(Element::name).Get<std::string>();
                    if (modelName == Element::property)
                    {
                        ret.insert(subModelElement.second);
                    }
                }

                t_body = ret;

                if (t_body.IsNull())
                {
                    basyx::log::topic(loggerName).error(" response data is null");

                    return ApiResponse::NOT_OK_EMPTY_DATA;
                }
                else if (t_body.IsError())
                {
                    basyx::log::topic(loggerName).error(" error response -" + t_body.getErrorMessage());

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }
                else
                {
                    return ApiResponse::OK;
                }
            }
            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::getAllOperations(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                basyx::object & t_body)
            {
                auto subModelElements = fetchAllSubModelElements(
                    t_connector,
                    std::get<AAS_ID>(t_id),
                    std::get<SM_ID>(t_id)
                );

                if (subModelElements.isError() || subModelElements.isNull())
                    return ApiResponse::NOT_OK_MALFORMED_BODY;

                auto ret = basyx::object::make_list<basyx::object>();

                for (const auto& subModelElement : subModelElements.template Get<basyx::object::object_map_t>())
                {
                    auto smElementObj = basyx::object(subModelElement.second);

                    auto modelName = smElementObj.getProperty(Element::modelType)
                        .getProperty(Element::name).Get<std::string>();
                    if (modelName == Element::operation)
                    {
                        ret.insert(subModelElement.second);
                    }
                }

                t_body = ret;

                if (t_body.IsNull())
                {
                    basyx::log::topic(loggerName).error(" response data is null");

                    return ApiResponse::NOT_OK_EMPTY_DATA;
                }
                else if (t_body.IsError())
                {
                    basyx::log::topic(loggerName).error(" error response -" + t_body.getErrorMessage());

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }
                else
                {
                    return ApiResponse::OK;
                }
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProviderHelpers::fetchAllSubModelElements(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const std::string & t_identifierAAS,
                const std::string & t_identifierSM)
            {
                auto aasNode{ AASApiNodeIdHelpers::getAssetAdministrationShellNode(t_connector, t_rootNode, t_identifierAAS) };

                if (aasNode.isNull())
                {
                    basyx::log::topic(loggerName).error("AssetAdmintrationShell node cannot be resolved");

                    return basyx::object::make_error(basyx::object::error::PropertyNotFound);
                }

                auto api = util::make_unique<AASApiSubModel<CONNECTOR_TYPE>>(t_connector, t_rootNode);

                auto sm = api->getSubModel(t_identifierAAS, t_identifierSM);

                if (sm == nullptr)
                {
                    basyx::log::topic(loggerName).error("Submodel node cannot be resolved");

                    return basyx::object::make_error(basyx::object::error::PropertyNotFound);
                }

                auto smList = basyx::object::make_object_list();

                if (auto smObj{ (dynamic_cast<SubModel*>(sm.get())) })
                {
                    return smObj->getMap().getProperty(Element::submodelElements);
                }
                else
                {
                    return basyx::object::make_error(basyx::object::error::ProviderException);
                }
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::invokeOperation(const identifiers & t_id,
                const NodeId & t_rootNode,
                basyx::object & t_body)
            {
                return ApiResponse::NOT_IMPLEMENTED;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::deleteSubModel(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id)
            {
                auto aasNode{ AASApiNodeIdHelpers::getAssetAdministrationShellNode(t_connector, t_rootNode, std::get<AAS_ID>(t_id)) };

                if (aasNode.isNull())
                {
                    basyx::log::topic(loggerName).error("AssetAdmintrationShell not found");

                    return ApiResponse::NOT_OK_INTERNAL_ERROR;
                }

                auto api = util::make_unique<AASApiSubModel<CONNECTOR_TYPE>>(t_connector, t_rootNode);

                return api->deleteSubModel(std::get<AAS_ID>(t_id), std::get<SM_ID>(t_id));
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::createSubmodelelement(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                const basyx::object & t_body)
            {
                basyx::object body{ t_body };

                if (std::get<SM_ELE_ID>(t_id) != body.getProperty(Element::idShort).Get<std::string>())
                {
                    basyx::log::topic(loggerName).error("IdShort in the model and body should be the same!");

                    return ApiResponse::NOT_OK_MALFORMED_REQ;
                }

                auto modelType{ body.getProperty(Element::modelType) };

                if (modelType.getError() != basyx::object::error::PropertyNotFound)
                {
                    auto name{ modelType.getProperty(Element::name) };

                    if (name.getError() != basyx::object::error::PropertyNotFound)
                    {
                        auto nameValue = name.Get<std::string>();

                        if (nameValue == Element::property)
                        {
                            return createProperty(t_connector, t_rootNode, t_id, t_body);
                        }
                        else
                        {
                            /* Remaining Submodelelements here*/
                            ApiResponse::NOT_IMPLEMENTED;
                        }
                    }
                }
                return ApiResponse::NOT_OK_MALFORMED_REQ;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::createProperty(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                const basyx::object & t_body)
            {

                basyx::object body{ t_body };

                auto& identifierTup = t_id;

                const std::string& idShort = body.getProperty(Element::idShort).Get<std::string&>();

                const std::string& kind = body.getProperty(Element::kind).Get<std::string&>();

                basyx::object valueObj = body.getProperty(Element::value);

                auto& valueType{ body.getProperty(Element::valueType).Get <std::string&>() };

                auto api = util::make_unique<AASApiSubModelElement<CONNECTOR_TYPE>>(t_connector, t_rootNode);

                auto keys{ AASApiMetamodelHelpers::buildKeysFromObject(body) };

                if (valueType == metamodel::AASPropertyType::TypeName::bool_)
                {
                    Property_t<bool> prop(idShort);

                    prop.setValue(valueObj.Get<bool>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::int16)
                {
                    Property_t<int16_t> prop(idShort);

                    prop.setValue(valueObj.Get<int16_t>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::int32)
                {
                    Property_t<int32_t> prop(idShort);

                    prop.setValue(valueObj.Get<int32_t>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::float_)
                {
                    Property_t<float> prop(idShort);

                    prop.setValue(valueObj.Get<float>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::double_)
                {
                    Property_t<double> prop(idShort);

                    prop.setValue(valueObj.Get<double>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::string_)
                {
                    Property_t<std::string> prop(idShort);

                    prop.setValue(valueObj.Get<std::string>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::int64)
                {
                    Property_t<int64_t> prop(idShort);

                    prop.setValue(valueObj.Get<int64_t>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::uint16)
                {
                    Property_t<uint16_t> prop(idShort);

                    prop.setValue(valueObj.Get<uint16_t>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::uint32)
                {
                    Property_t<uint32_t> prop(idShort);

                    prop.setValue(valueObj.Get<uint32_t>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::uint64)
                {
                    Property_t<uint64_t> prop(idShort);

                    prop.setValue(valueObj.Get<uint64_t>());

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);

                }
                else if (valueType == metamodel::AASPropertyType::TypeName::anyUri)
                {
                    Property_t<AnyURI> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<AnyURI>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);

                }
                else if (valueType == metamodel::AASPropertyType::TypeName::date)
                {
                    Property_t<Date> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<Date>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::dateTime)
                {
                    Property_t<DateTime> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<DateTime>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::dayTimeDuration)
                {
                    Property_t<DayTimeDuration> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<DayTimeDuration>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::yearMonthDuration)
                {
                    Property_t<YearMonthDuration> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<YearMonthDuration>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::time)
                {
                    Property_t<Time> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<Time>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::gYearMonth)
                {
                    Property_t<GYearMonth> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<GYearMonth>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::gYear)
                {
                    Property_t<GYear> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<GYear>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::gMonthDay)
                {
                    Property_t<GMonthDay> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<GMonthDay>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::gDay)
                {
                    Property_t<GDay> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<GDay>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }
                else if (valueType == metamodel::AASPropertyType::TypeName::gMonth)
                {
                    Property_t<GMonth> prop(idShort);

                    auto& value = valueObj.Get<std::string&>();

                    prop.setValue(xsd_type<GMonth>::fromXSDRepresentation(value));

                    return api->addProperty(std::get<AAS_ID>(identifierTup), std::get<SM_ID>(identifierTup), prop);
                }

                return ApiResponse::NOT_OK_NOT_FOUND;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::createOperation(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id,
                const basyx::object & t_body)
            {
                return ApiResponse::NOT_IMPLEMENTED;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASSubmodelProviderHelpers::deleteSubmodelElement(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const identifiers & t_id)
            {
                auto api = util::make_unique<AASApiSubModelElement<CONNECTOR_TYPE>>(t_connector, t_rootNode);

                return api->deleteSubmodelElement(std::get<AAS_ID>(t_id), std::get<SM_ID>(t_id), std::get<SM_ELE_ID>(t_id));

            }
        }
    }
}
#endif