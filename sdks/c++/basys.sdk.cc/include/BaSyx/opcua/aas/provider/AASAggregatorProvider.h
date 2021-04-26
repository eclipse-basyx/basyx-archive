#ifndef AAS_AGGREGATOR_PROVIDER_H
#define AAS_AGGREGATOR_PROVIDER_H

#include <BaSyx/log/log.h>
#include <BaSyx/shared/types.h>
#include <BaSyx/shared/object.h>
#include <BaSyx/vab/core/IModelProvider.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/aas/provider/AASModelProviderCommon.h>
#include <BaSyx/opcua/aas/provider/AASAggregatorProviderHelpers.h>
#include <BaSyx/opcua/aas/provider/AASSubmodelProvider.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE = basyx::opcua::Client>
            class AASAggregatorProvider : public vab::core::IModelProvider
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASAggregatorProvider";
            public:

                AASAggregatorProvider(const std::string & t_endpoint, const NodeId & t_rootNode);

                AASAggregatorProvider(const std::string & t_endpoint,
                    std::unique_ptr<IModelProvider> t_submodelProvider,
                    const NodeId & t_rootNode);

                ~AASAggregatorProvider();

                /* REST GET */
                virtual basyx::object getModelPropertyValue(const std::string& t_path) override
                {
                    if (t_path.empty())
                    {
                        m_logger.error("Emtpy path");

                        return basyx::object::make_error(basyx::object::error::MalformedRequest);
                    }

                    auto obj{ basyx::object::make_null() };

                    return parseAggregatorApi(t_path, RequestType::GET, obj);
                }

                /* REST PUT */
                virtual basyx::object::error setModelPropertyValue(const std::string& t_path, const basyx::object t_newValue) override
                {
                    if (t_newValue.IsNull())
                    {
                        m_logger.error("Body cannot be null");

                        return basyx::object::error::MalformedRequest;
                    }
                    else if (t_path.empty())
                    {
                        m_logger.error("Emtpy path");

                        return basyx::object::error::MalformedRequest;
                    }

                    auto value{ t_newValue };

                    return  parseAggregatorApi(t_path, RequestType::CREATE, value).getError();
                }

                /* REST POST */
                virtual basyx::object::error createValue(const std::string& t_path, const basyx::object t_newValue) override
                {
                    return basyx::object::error::None;
                }

                /* IGNORED */
                virtual basyx::object::error deleteValue(const std::string& t_path, basyx::object t_deletedValue) override
                {
                    return basyx::object::error::ProviderException;
                }

                /* REST DELETE */
                virtual basyx::object::error deleteValue(const std::string& t_path) override
                {
                    auto obj{ basyx::object::make_null() };

                    return parseAggregatorApi(t_path, RequestType::DELETE_, obj).getError();
                }

                /* REST POST */
                virtual basyx::object invokeOperation(const std::string& t_path, basyx::object t_parameters) override
                {
                    if (t_parameters.IsNull())
                    {
                        m_logger.error("Body cannot be null");

                        return basyx::object::make_error(basyx::object::error::MalformedRequest);
                    }
                    else if (t_path.empty())
                    {
                        m_logger.error("Emtpy path");

                        return basyx::object::make_error(basyx::object::error::MalformedRequest);
                    }

                    return parseAggregatorApi(t_path, RequestType::INVOKE, t_parameters);
                }

            private:
                basyx::object parseAggregatorApi(const std::string& t_path,
                    RequestType t_reqType,
                    basyx::object& t_body);

                basyx::object delegateGetInvoke(const std::string& t_path,
                    RequestType t_reqType,
                    basyx::object& t_body);

                basyx::object delegateCreateUpdateDelete(const std::string& t_path,
                    RequestType t_reqType,
                    basyx::object t_value);

            private:
                std::unique_ptr<CONNECTOR_TYPE> m_connector;
                std::unique_ptr<IModelProvider> m_submodelProvider;
                basyx::log m_logger;
                NodeId m_rootNode;
            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASAggregatorProvider<CONNECTOR_TYPE>::loggerName[];

            template<>
            inline AASAggregatorProvider<Client>::AASAggregatorProvider(const std::string & t_endpoint, const NodeId & t_rootNode) :
                m_rootNode(t_rootNode), m_logger(basyx::log(loggerName))
            {
                m_connector = util::make_unique<Client>(t_endpoint, shared::Namespaces::BASYX_NS_URI);

                m_connector->setRootNode(t_rootNode);

                m_connector->connect();

                m_submodelProvider = util::make_unique<AASSubmodelProvider<Client>>(*m_connector.get(), t_rootNode);

                metamodel::AASMetamodel::define<Client>(m_connector->getNamespaceIndexDefault(), *m_connector.get());
            }

            template<>
            inline AASAggregatorProvider<Server>::AASAggregatorProvider(const std::string & t_port, const NodeId & t_rootNode) :
                m_rootNode(t_rootNode), m_logger(basyx::log(loggerName))
            {
                m_connector = util::make_unique<Server>(std::stoi(t_port), shared::Namespaces::BASYX_NS_URI);

                m_connector->setRootNode(t_rootNode);

                m_connector->initialize();

                m_submodelProvider = util::make_unique<AASSubmodelProvider<Server>>(*m_connector.get(), t_rootNode);

                metamodel::AASMetamodel::define<Server>(m_connector->getNamespaceIndexDefault(), *m_connector.get());

                m_connector->runInBackground();
            }

            template<>
            inline AASAggregatorProvider<Client>::AASAggregatorProvider(const std::string & t_endpoint,
                std::unique_ptr<IModelProvider> t_submodelProvider,
                const NodeId & t_rootNode) :
                m_rootNode(t_rootNode), m_logger(basyx::log(loggerName))
            {
                m_connector = util::make_unique<Client>(t_endpoint, shared::Namespaces::BASYX_NS_URI);

                m_connector->setRootNode(t_rootNode);

                m_connector->connect();

                m_submodelProvider = std::move(t_submodelProvider);

                metamodel::AASMetamodel::define<Client>(m_connector->getNamespaceIndexDefault(), *m_connector.get());
            }

            template<>
            inline AASAggregatorProvider<Server>::AASAggregatorProvider(const std::string & t_port,
                std::unique_ptr<IModelProvider> t_submodelProvider,
                const NodeId & t_rootNode) :
                m_rootNode(t_rootNode), m_logger(basyx::log(loggerName))
            {
                m_connector = util::make_unique<Server>(std::stoi(t_port), shared::Namespaces::BASYX_NS_URI);

                m_connector->setRootNode(t_rootNode);

                m_connector->initialize();

                m_submodelProvider = std::move(t_submodelProvider);

                metamodel::AASMetamodel::define<Server>(m_connector->getNamespaceIndexDefault(), *m_connector.get());
            }

            template<>
            inline AASAggregatorProvider<Client>::~AASAggregatorProvider()
            {
                m_connector->disconnect();
            }

            template<>
            inline AASAggregatorProvider<Server>::~AASAggregatorProvider()
            {
                m_connector->abort();
            }


            template<typename CONNECTOR_TYPE>
            inline basyx::object AASAggregatorProvider<CONNECTOR_TYPE>::parseAggregatorApi(const std::string & t_path,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_path.empty())
                {
                    return basyx::object::make_error(basyx::object::error::MalformedRequest, "vab path is empty");
                }

                vab::core::VABPath path{ t_path };

                auto idTuple{ AASProviderApiParseHelpers::parseIdentifiers(vab::core::VABPath(t_path)) };

                /* shells*/
                /* GET */
                if (t_path == Element::shells)
                {
                    auto response = AASAggregatorProviderHelpers::handleAASList<CONNECTOR_TYPE>(*m_connector.get(),
                        m_rootNode,
                        t_reqType,
                        t_body);

                    if (response != ApiResponse::OK)
                    {
                        return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                    }
                    else
                    {
                        return t_body;
                    }
                }
                /* shells/{aasID}*/
                /* GET, CREATE, UPDATE, DELETE */
                else if (AASProviderApiParseHelpers::isApiShellsAASId(path))
                {
                    auto response = AASAggregatorProviderHelpers::handleAASListIdentifier<CONNECTOR_TYPE>(*m_connector.get(),
                        m_rootNode,
                        std::get<AAS_ID>(idTuple),
                        t_reqType,
                        t_body);

                    if (response != ApiResponse::OK)
                    {
                        return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                    }
                    else
                    {
                        return t_body;
                    }
                }
                /* shells/{aasID}/aas*/
                /* GET */
                else if (AASProviderApiParseHelpers::isApiShellsAASIdAAS(path))
                {
                    auto response = AASAggregatorProviderHelpers::handleAASListIdentifierAAS(*m_connector.get(),
                        m_rootNode,
                        std::get<AAS_ID>(idTuple),
                        t_reqType,
                        t_body);

                    if (response != ApiResponse::OK)
                    {
                        return ApiResponse_::ApiResponseToError(response, ApiResponse_::toString(response));
                    }
                    else
                    {
                        return t_body;
                    }
                }
                else
                {
                    if ((t_reqType == RequestType::GET) || (t_reqType == RequestType::INVOKE))
                    {
                        return delegateGetInvoke(t_path, t_reqType, t_body);
                    }
                    else
                    {
                        return delegateCreateUpdateDelete(t_path, t_reqType, t_body);
                    }
                }
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASAggregatorProvider<CONNECTOR_TYPE>::delegateGetInvoke(const std::string & t_path,
                RequestType t_reqType,
                basyx::object & t_body)
            {
                if (t_reqType == RequestType::GET)
                {
                    return  m_submodelProvider->getModelPropertyValue(t_path);
                }
                else if (t_reqType == RequestType::INVOKE)
                {
                    return m_submodelProvider->invokeOperation(t_path, t_body);
                }
                return basyx::object::make_error(basyx::object::error::ProviderException);
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::object AASAggregatorProvider<CONNECTOR_TYPE>::delegateCreateUpdateDelete(const std::string & t_path,
                RequestType t_reqType,
                basyx::object t_value)
            {
                basyx::object::error response = basyx::object::error::None;

                /* An UPDATE request is also treated as CREATE */
                if (t_reqType == RequestType::CREATE)
                {

                    response = m_submodelProvider->setModelPropertyValue(t_path, t_value);

                }
                else if (t_reqType == RequestType::DELETE_)
                {
                    response = m_submodelProvider->deleteValue(t_path);
                }
                return basyx::object::make_error(response);
            }
        }
    }
}

#endif