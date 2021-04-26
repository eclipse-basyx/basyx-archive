#ifndef AAS_MODEL_NODE_H
#define AAS_MODEL_NODE_H

#include <BaSyx/log/log.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/NodeDescription.h>
#include <BaSyx/opcua/aas/metamodel/AASMetamodelAliases.h>
#include <BaSyx/opcua/aas/metamodel/AASMetamodel.h>
#include <BaSyx/opcua/aas/api/AASApiMetamodelHelpers.h>
// For XSD types
#include <BaSyx/submodel/api_v2/submodelelement/property/XSDAnySimpleType.h>
#include <BaSyx/submodel/simple/common/xsd_types/AnyURI.h>
#include <BaSyx/submodel/simple/common/xsd_types/DateTime.h>
#include <BaSyx/submodel/simple/common/xsd_types/Date.h>
#include <BaSyx/submodel/simple/common/xsd_types/DayTimeDuration.h>
#include <BaSyx/submodel/simple/common/xsd_types/YearMonthDuration.h>
#include <BaSyx/submodel/simple/common/xsd_types/Time.h>
#include <BaSyx/submodel/simple/common/xsd_types/GYearMonth.h>
#include <BaSyx/submodel/simple/common/xsd_types/GYear.h>
#include <BaSyx/submodel/simple/common/xsd_types/GMonthDay.h>
#include <BaSyx/submodel/simple/common/xsd_types/GDay.h>
#include <BaSyx/submodel/simple/common/xsd_types/GMonth.h>
// For type transformation
#include <BaSyx/opcua/typesmap/TypesTransformer.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE>
            class AASModelNode
            {
            public:
                virtual ~AASModelNode() = default;

                AASModelNode(
                    CONNECTOR_TYPE& t_connector,
                    const NodeId& t_nodeId,
                    const NodeId& t_parentNodeId,
                    const std::string& t_idShort,
                    const std::string& t_instanceName,
                    const NodeDescription& t_description,
                    const std::string& t_loggerName
                ) :
                    m_connector(t_connector),
                    m_idShort(t_idShort),
                    m_description(t_description),
                    m_logger(basyx::log(t_loggerName)),
                    m_services(Services<CONNECTOR_TYPE>(t_connector))
                {
                    m_nodeId = t_nodeId;
                    m_parentNodeId = t_parentNodeId;
                }

                AASModelNode() = default;

                void setParentNodeId(const NodeId& t_parentNodeId);

                void setIdShort(const std::string& t_idShort);

                void setInstanceName(const std::string& t_instanceName);

                void setNodeId(const NodeId& _nodeId);

                const NodeId& getParentNodeId() const;

                const NodeId& getNodeId() const;

                const std::string& getIdShort() const;

                const NodeDescription& getNodeDescription() const;

                const std::string& getInstanceName() const;

                basyx::log& getLogger();

                CONNECTOR_TYPE& getConnector();

                Services<CONNECTOR_TYPE>& getServices();

            private:
                mutable basyx::log m_logger;
                NodeId m_nodeId;
                NodeId m_parentNodeId;
                std::string  m_idShort;
                std::string  m_instanceName;
                NodeDescription m_description;
                CONNECTOR_TYPE& m_connector;
                Services<CONNECTOR_TYPE> m_services;
            };

            template<typename CONNECTOR_TYPE>
            const NodeId& AASModelNode<CONNECTOR_TYPE>::getParentNodeId() const
            {
                return m_parentNodeId;
            }

            template<typename CONNECTOR_TYPE>
            const NodeId& AASModelNode<CONNECTOR_TYPE>::getNodeId() const
            {
                return m_nodeId;
            }

            template<typename CONNECTOR_TYPE>
            CONNECTOR_TYPE&  AASModelNode<CONNECTOR_TYPE>::getConnector()
            {
                return m_services.getConnector();
            }

            template<typename CONNECTOR_TYPE>
            Services<CONNECTOR_TYPE>& AASModelNode<CONNECTOR_TYPE>::getServices()
            {
                return m_services;
            }

            template<typename CONNECTOR_TYPE>
            const std::string& AASModelNode<CONNECTOR_TYPE>::getIdShort() const
            {
                return m_idShort;
            }

            template<typename CONNECTOR_TYPE>
            const NodeDescription& AASModelNode<CONNECTOR_TYPE>::getNodeDescription() const
            {
                return m_description;
            }

            template<typename CONNECTOR_TYPE>
            const std::string& AASModelNode<CONNECTOR_TYPE>::getInstanceName() const
            {
                return m_instanceName;
            }

            template<typename CONNECTOR_TYPE>
            inline basyx::log & AASModelNode<CONNECTOR_TYPE>::getLogger()
            {
                return m_logger;
            }

            template<typename CONNECTOR_TYPE>
            void AASModelNode<CONNECTOR_TYPE>::setNodeId(const NodeId& t_nodeId)
            {
                m_nodeId = t_nodeId;
            }

            template<typename CONNECTOR_TYPE>
            void AASModelNode<CONNECTOR_TYPE>::setParentNodeId(const NodeId& t_parentNodeId)
            {
                m_parentNodeId = t_parentNodeId;
            }

            template<typename CONNECTOR_TYPE>
            void AASModelNode<CONNECTOR_TYPE>::setIdShort(const std::string& t_idShort)
            {
                m_idShort = t_idShort;
            }

            template<typename CONNECTOR_TYPE>
            void AASModelNode<CONNECTOR_TYPE>::setInstanceName(const std::string& _instanceName)
            {
                m_instanceName = _instanceName;
            }
        }
    }
}

#endif