#ifndef SUBMODEL_PROVIDER_H
#define SUBMODEL_PROVIDER_H

#include <BaSyx/shared/object.h>
#include <BaSyx/log/log.h>
#include <BaSyx/shared/types.h>
#include <BaSyx/vab/core/IModelProvider.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/aas/provider/AASSubmodelProviderHelpers.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE>
            class AASSubmodelProvider : public vab::core::IModelProvider
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASSubmodelProvider";
            public:

                AASSubmodelProvider(CONNECTOR_TYPE& t_connector, const NodeId& t_rootNode)
                    : m_connector(t_connector), m_rootNode(t_rootNode), m_logger(basyx::log(loggerName)) {}

                /* REST GET */
                virtual basyx::object getModelPropertyValue(const std::string& t_path) override
                {
                    auto obj{ basyx::object::make_null() };

                    return parseSubmodelApi(t_path, RequestType::GET, obj);
                }

                /* REST PUT */
                virtual basyx::object::error setModelPropertyValue(const std::string& path, const basyx::object t_newValue) override
                {
                    auto value{ t_newValue };

                    return  parseSubmodelApi(path, RequestType::CREATE, value).getError();
                }

                /* REST POST */
                virtual basyx::object::error createValue(const std::string& t_path, const basyx::object t_newValue) override
                {
                    return basyx::object::error::None;
                }

                /* IGNORED */
                virtual basyx::object::error deleteValue(const std::string& t_path, basyx::object t_deletedValue) override
                {
                    return basyx::object::error::None;
                }

                /* REST DELETE */
                virtual basyx::object::error deleteValue(const std::string& t_path) override
                {
                    auto obj{ basyx::object::make_null() };

                    return parseSubmodelApi(t_path, RequestType::DELETE_, obj).getError();
                }

                /* REST POST */
                virtual basyx::object invokeOperation(const std::string& t_path, basyx::object t_parameters) override
                {
                    return basyx::object::make_null();
                }

            private:
                basyx::object parseSubmodelApi(const std::string& t_path, RequestType t_reqType, basyx::object& t_body);

                basyx::object handleInvoke(const identifiers& t_ids, basyx::object & t_body);

                basyx::object handleSubmodelElementsValue(const identifiers& t_ids, RequestType t_reqType, basyx::object & t_body);

                basyx::object handleSubmodelElementsidShort(const identifiers& t_ids, RequestType t_reqType, basyx::object & t_body);

                basyx::object handleSubmodelElements(const identifiers& t_ids, basyx::object & t_body);

                basyx::object handleSubmodelValues(const identifiers& t_ids);

                basyx::object handleSubmodelSubmodel(const identifiers& t_ids, basyx::object & t_body);

                basyx::object handleSubmodelsIdShort(const identifiers& t_ids, RequestType t_reqType, basyx::object & t_body);

                basyx::object handleSubmodels(const identifiers& t_ids, basyx::object & t_body);

            private:
                CONNECTOR_TYPE& m_connector;
                NodeId m_rootNode;
                basyx::log m_logger;
            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASSubmodelProvider<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::parseSubmodelApi(const std::string & t_path,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_path.empty())
                {
                    return basyx::object::make_error(basyx::object::error::MalformedRequest, "vab path is empty");
                }

                vab::core::VABPath path{ t_path };

                auto ids{ AASProviderApiParseHelpers::parseIdentifiers(vab::core::VABPath(t_path)) };

                if (AASProviderApiParseHelpers::isAPISubmodelElementsInvoke(path))
                {
                    /* INVOKE */
                    /* API : shells/{aasID}/aas/submodels/{submodelIdshort}/submodel/submodelElements/{seIdShortOperation}/invoke */
                    return handleInvoke(ids, t_body);
                }
                else if (AASProviderApiParseHelpers::isAPISubmodelElementsValue(path))
                {
                    /* API : shells/{aasID}/aas/submodels/{submodelIdshort}/submodel/submodelElements/{seIdShort}/value */
                    /* GET UPDATE */
                    return handleSubmodelElementsValue(ids, t_reqType, t_body);

                }
                else if (AASProviderApiParseHelpers::isAPISubmodelElementsIdShort(path))
                {
                    /* API : shells/{aasID}/aas/submodels/{submodelIdshort}/submodel/submodelElements/{seIdShort} */
                    /* CREATE GET DELETE */
                    return handleSubmodelElementsidShort(ids, t_reqType, t_body);
                }
                else if (AASProviderApiParseHelpers::isAPISubmodelElements(path))
                {
                    /* API : shells/{aasID}/aas/submodels/{submodelIdshort}/submodel/submodelElements*/
                    /* GET */
                    return handleSubmodelElements(ids, t_body);
                }
                else if (AASProviderApiParseHelpers::isAPISubmodelValues(path))
                {
                    /* API : shells/{aasID}/aas/submodels/{submodelIdshort}/submodel/values */
                    /* GET */
                    return handleSubmodelValues(ids);
                }
                else if (AASProviderApiParseHelpers::isAPISubmodelsSubmodel(path))
                {
                    /* API : shells/{aasID}/aas/submodels/{submodelIdshort}/submodel */
                    /*  GET */
                    return handleSubmodelSubmodel(ids, t_body);
                }
                else if (AASProviderApiParseHelpers::isAPISubmodelsIdShort(path))
                {
                    /* API : shells/{aasID}/aas/submodels/{submodelIdshort} */
                    /* CREATE GET DELETE */
                    return handleSubmodelsIdShort(ids, t_reqType, t_body);

                }
                /* API : shells/{aasID}/aas/submodels*/
                /* GET */
                else if (AASProviderApiParseHelpers::isAPISubmodels(path))
                {
                    return handleSubmodels(ids, t_body);
                }

                return basyx::object::make_error(basyx::object::error::PropertyNotFound);
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleInvoke(const identifiers& t_ids, basyx::object & t_body)
            {
                return basyx::object::make_error(basyx::object::error::PropertyNotFound);
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleSubmodelElementsValue(const identifiers& t_ids,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_reqType == RequestType::GET)
                {
                    auto response = AASSubmodelProviderHelpers::readPropertyValue(
                        m_connector,
                        m_rootNode,
                        t_ids,
                        t_body
                    );

                    if (response != ApiResponse::OK)
                    {
                        return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                    }
                    else
                    {
                        return t_body;
                    }
                }
                else if (t_reqType == RequestType::CREATE) // UPDATE is mapped to CREATE
                {
                    auto response = AASSubmodelProviderHelpers::writePropertyValue(
                        m_connector,
                        m_rootNode,
                        t_ids,
                        t_body
                    );

                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                }
                else
                {
                    return basyx::object::make_error(basyx::object::error::PropertyNotFound);
                }
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleSubmodelElementsidShort(const identifiers & t_ids,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_reqType == RequestType::CREATE)
                {
                    auto response = AASSubmodelProviderHelpers::createSubmodelelement(
                        m_connector,
                        m_rootNode,
                        t_ids,
                        t_body
                    );

                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                }
                else if (t_reqType == RequestType::GET)
                {

                    auto response = AASSubmodelProviderHelpers::readSubModelElement(
                        m_connector,
                        m_rootNode,
                        t_ids,
                        t_body
                    );

                    if (response != ApiResponse::OK)
                    {
                        return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                    }
                    else
                    {
                        return t_body;
                    }
                }
                else if (t_reqType == RequestType::DELETE_)
                {
                    auto response = AASSubmodelProviderHelpers::deleteSubmodelElement(m_connector, m_rootNode, t_ids);

                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                }
                return basyx::object::make_error(basyx::object::error::ProviderException);
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleSubmodelElements(const identifiers & t_ids,
                basyx::object & t_body)
            {

                auto response = AASSubmodelProviderHelpers::readSubModelElements(
                    m_connector,
                    m_rootNode,
                    t_ids,
                    t_body
                );

                if (response != ApiResponse::OK)
                {
                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                }
                else
                {
                    return t_body;
                }
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleSubmodelValues(const identifiers & t_ids)
            {
                basyx::object list_body = basyx::object::make_list<basyx::object>();

                auto obj{ basyx::object::make_null() };

                auto submodelElements = handleSubmodelElements(t_ids, obj);

                for (auto& submodelElement : submodelElements.template Get<basyx::object::object_map_t>())
                {
                    auto idShort = submodelElement.first;

                    auto value = submodelElement.second.getProperty(Element::value);

                    if (!value.IsError())
                    {
                        auto values_map = basyx::object::make_map();

                        values_map.insertKey(Element::idShort, idShort);
                        values_map.insertKey(Element::value, value);
                        values_map.insertKey(Element::valueType, submodelElement.second.getProperty(Element::valueType));

                        list_body.insert(std::move(values_map));
                    }
                }
                return list_body;
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleSubmodelSubmodel(const identifiers & t_ids,
                basyx::object & t_body)
            {

                auto response = AASSubmodelProviderHelpers::readSubModelIdentifier(
                    m_connector,
                    m_rootNode,
                    t_ids,
                    t_body
                );

                if (response != ApiResponse::OK)
                {
                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                }
                else
                {
                    return t_body;
                }
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleSubmodelsIdShort(const identifiers & t_ids,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_reqType == RequestType::CREATE)
                {                                      
                    auto response = AASSubmodelProviderHelpers::createSubModel<CONNECTOR_TYPE>(
                        m_connector,
                        m_rootNode,
                        t_ids,
                        t_body);

                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));

                }
                else if (t_reqType == RequestType::GET)
                {
                    auto response = AASSubmodelProviderHelpers::readSubModelIdentifier(
                        m_connector,
                        m_rootNode,
                        t_ids,
                        t_body
                    );

                    if (response != ApiResponse::OK)
                    {
                        return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                    }
                    else
                    {
                        return t_body;
                    }
                }
                else if (t_reqType == RequestType::DELETE_)
                {
                    auto response = AASSubmodelProviderHelpers::deleteSubModel(
                        m_connector,
                        m_rootNode,
                        t_ids
                    );

                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                }
                else
                {
                    return basyx::object::make_error(basyx::object::error::PropertyNotFound);
                }
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASSubmodelProvider<CONNECTOR_TYPE>::handleSubmodels(const identifiers & t_ids,
                basyx::object & t_body)
            {
                auto response = AASSubmodelProviderHelpers::readSubmodels<CONNECTOR_TYPE>(m_connector, m_rootNode, t_ids, t_body);

                if (response != ApiResponse::OK)
                {
                    return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                }
                else
                {
                    return t_body;
                }
            }
        }
    }
}

#endif