#ifndef AAS_AGGREGATOR_PROVIDER_HELPERS_H
#define AAS_AGGREGATOR_PROVIDER_HELPERS_H

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>
#include <BaSyx/vab/core/util/VABPath.h>
#include <BaSyx/opcua/aas/provider/AASProviderApiParseHelpers.h>
#include <BaSyx/opcua/aas/metamodel/AASMetamodelAliases.h>
#include <BaSyx/opcua/aas/api/AASApiAssetAdminstrationShell.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            class AASAggregatorProviderHelpers
            {
                static  constexpr const char* loggerName = "basyx::opcua::aas::AASAggregatorProvider";
            public:

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleReadAASList(CONNECTOR_TYPE& t_connector, const NodeId& t_rootNode, basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleAASList(CONNECTOR_TYPE& t_connector, const NodeId& t_rootNode, RequestType t_reqType, basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleReadAASIdentifier(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string& t_aasIdentifier, basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleCreateAAS(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string& t_aasIdFromPath,
                    const basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleAASListIdentifierAAS(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string & t_path,
                    RequestType t_reqType,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleAASListIdentifier(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string & t_aasIdentifier,
                    RequestType t_reqType,
                    basyx::object & t_body);

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleUpdateAAS(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string& t_aasIdentifier,
                    basyx::object & t_aasBody);

                template<typename CONNECTOR_TYPE>
                static ApiResponse handleDeleteAAS(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_rootNode,
                    const std::string & t_path);
            };

            //template<typename CONNECTOR_TYPE>
            //constexpr char AASAggregatorProviderHelpers<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASAggregatorProviderHelpers::handleAASListIdentifierAAS(CONNECTOR_TYPE& t_connector,
                const NodeId& t_rootNode,
                const std::string & t_path,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_reqType == RequestType::CREATE)
                {
                    basyx::log::topic(loggerName).error("API not supported");

                    return ApiResponse::NOT_SUPPORTED;
                }
                else if (t_reqType == RequestType::GET)
                {
                    return handleReadAASIdentifier(t_connector, t_rootNode, t_path, t_body);
                }
                else if (t_reqType == RequestType::DELETE_)
                {
                    basyx::log::topic(loggerName).error("API not supported");

                    return ApiResponse::NOT_SUPPORTED;
                }
                else if (t_reqType == RequestType::INVOKE)
                {
                    basyx::log::topic(loggerName).error("API not supported");

                    return ApiResponse::NOT_SUPPORTED;
                }
                return ApiResponse::UNKNOWN;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASAggregatorProviderHelpers::handleAASListIdentifier(CONNECTOR_TYPE & t_connector,
                const NodeId& t_rootNode,
                const std::string & t_aasIdentifier,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_reqType == RequestType::INVOKE)
                {
                    basyx::log::topic(loggerName).error("API does not support Invocation");

                    return ApiResponse::NOT_SUPPORTED;
                }
                else if (t_reqType == RequestType::CREATE)
                {
                    return handleCreateAAS<CONNECTOR_TYPE>(t_connector, t_rootNode, t_aasIdentifier, t_body);
                }
                else if (t_reqType == RequestType::GET)
                {
                    return handleReadAASIdentifier(t_connector, t_rootNode, t_aasIdentifier, t_body);
                }
                //else if (t_reqType == RequestType::UPDATE)
                //{
                //	return handleUpdateAAS(t_connector, t_rootNode, t_aasIdentifier, t_body);
                //}
                else if (t_reqType == RequestType::DELETE_)
                {
                    return handleDeleteAAS<CONNECTOR_TYPE>(t_connector, t_rootNode, t_aasIdentifier);
                }
                return ApiResponse::UNKNOWN;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASAggregatorProviderHelpers::handleReadAASIdentifier(CONNECTOR_TYPE& t_connector,
                const NodeId& t_rootNode,
                const std::string & t_aasIdentifier,
                basyx::object & t_body)
            {
                auto api{ util::make_unique<AASApiAssetAdminstrationShell<CONNECTOR_TYPE>>(t_connector, t_rootNode) };

                if (auto aas = api->getAssetAdministrationShell(t_aasIdentifier))
                {
                    t_body = dynamic_cast<AssetAdministrationShell*>(aas.get())->getMap();

                    return ApiResponse::OK;
                }
                else
                {
                    basyx::log::topic(loggerName).error("AssetAdministrationShells could not be retrieved");

                    return ApiResponse::NOT_OK_NOT_FOUND;
                }
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASAggregatorProviderHelpers::handleAASList(CONNECTOR_TYPE& t_connector,
                const NodeId& t_rootNode,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if ((t_reqType == RequestType::CREATE) ||
                    (t_reqType == RequestType::DELETE_) ||
                    (t_reqType == RequestType::INVOKE) ||
                    (t_reqType == RequestType::UPDATE))
                {
                    return ApiResponse::NOT_SUPPORTED;
                }
                else if (t_reqType == RequestType::GET)
                {
                    return handleReadAASList(t_connector, t_rootNode, t_body);
                }
                return ApiResponse::UNKNOWN;
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASAggregatorProviderHelpers::handleCreateAAS(CONNECTOR_TYPE& t_connector,
                const NodeId& t_rootNode,
                const std::string& t_aasIdFromPath,
                const basyx::object & t_body)
            {

                basyx::object body{ t_body };

                auto administration = body.getProperty(Element::administration);

                auto modelNameObj{ body.getProperty(Element::modelType).getProperty(Element::name) };

                if (modelNameObj.IsError())
                {
                    basyx::log::topic(loggerName).error("AssetAdministrationShell body is malformed");

                    return ApiResponse::NOT_OK_MALFORMED_BODY;
                }

                if ((modelNameObj.Get<std::string&>() != Element::AssetAdministrationShell))
                {
                    basyx::log::topic(loggerName).error("AssetAdministrationShell body is malformed");

                    return ApiResponse::NOT_OK_MALFORMED_BODY;
                }

                auto identifier{ AASApiMetamodelHelpers::buildIdentifersFromObject(body.getProperty(Element::identifier)) };

                if (identifier.getId() != t_aasIdFromPath)
                {
                    basyx::log::topic(loggerName).error("AssetAdministrationShell identifier in the path and model are not same");

                    ApiResponse::NOT_OK_MALFORMED_REQ;
                }

                const std::string& idShort = body.getProperty(Element::idShort).Get<std::string&>();

                Asset dummyAsset(std::string(), Identifier::Custom(std::string()));

                auto aas{ std::make_shared<AssetAdministrationShell>(idShort, identifier, dummyAsset) };

                auto api{ util::make_unique<AASApiAssetAdminstrationShell<CONNECTOR_TYPE>>(t_connector, t_rootNode) };

                if (!administration.IsError())
                {
                    aas->setAdministrativeInformation(
                        basyx::submodel::map::AdministrativeInformation(administration.getProperty(Element::version).Get<std::string&>(),
                            administration.getProperty(Element::revision).Get<std::string&>()
                        )
                    );
                }

                return api->createAssetAdministrationShell(std::move(aas));
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASAggregatorProviderHelpers::handleReadAASList(CONNECTOR_TYPE& t_connector,
                const NodeId& t_rootNode,
                basyx::object & t_body)
            {

                auto api{ util::make_unique<AASApiAssetAdminstrationShell<CONNECTOR_TYPE>>(t_connector, t_rootNode) };

                auto aases{ api->getAllAssetAdministrationShells() };

                if (aases.empty())
                {
                    t_body = basyx::object::make_null();

                    return ApiResponse::NOT_OK_EMPTY_DATA;
                }

                auto retList{ basyx::object::make_list<basyx::object>() };

                for (const auto& aas_ : aases)
                {
                    retList.insert(dynamic_cast<AssetAdministrationShell*>(aas_.get())->getMap());
                }

                t_body = retList;

                return ApiResponse::OK;
            }

            template<typename CONNECTOR_TYPE>
            ApiResponse AASAggregatorProviderHelpers::handleDeleteAAS(CONNECTOR_TYPE& t_connector,
                const NodeId& t_rootNode,
                const std::string & t_path)
            {
                using namespace basyx::opcua::shared::string;

                auto path{ t_path };

                replaceAllWithWhiteSpace(path, Element::shells);

                auto api{ util::make_unique<AASApiAssetAdminstrationShell<CONNECTOR_TYPE>>(t_connector, t_rootNode) };

                return api->deleteAssetAdministrationShell(path);
            }

            template<typename CONNECTOR_TYPE>
            inline ApiResponse AASAggregatorProviderHelpers::handleUpdateAAS(CONNECTOR_TYPE & t_connector,
                const NodeId & t_rootNode,
                const std::string & t_aasIdentifier,
                basyx::object & t_aasBody)
            {
                auto deleteResponse{ handleDeleteAAS<CONNECTOR_TYPE>(t_connector, t_rootNode, t_aasIdentifier) };

                if (deleteResponse != ApiResponse::OK)
                {
                    return deleteResponse;
                }

                return handleCreateAAS<CONNECTOR_TYPE>(t_connector, t_rootNode, t_aasIdentifier, t_aasBody);
            }
        }
    }
}
#endif