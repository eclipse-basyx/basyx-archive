#ifndef AAS_EVENT_TYPE_H
#define AAS_EVENT_TYPE_H

#include <BaSyx/opcua/common/NodeId.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/QualifiedName.h>
#include <BaSyx/opcua/common/ObjectAttributes.h>
#include <BaSyx/opcua/common/ObjectTypeAttributes.h>

#include <BaSyx/opcua/aas/metamodel/AASSubModelElementType.h>

namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			namespace metamodel
			{
				struct AASEventType
				{
					struct AttrNames
					{
						static constexpr const char BrowseText[] = "AASEventType";
						static constexpr const char DisplayName[] = "AASEventType";
						static constexpr const char Description[] = "AASEventType";
						static constexpr const char NodeHandle[] = "AASEventType";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_connector);
				};

				template<typename CONNECTOR_TYPE>
				UA_StatusCode AASEventType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					UA_StatusCode status = UA_STATUSCODE_GOOD;

					Services<CONNECTOR_TYPE> services(t_connector);

					status = services.addObjectTypeNode(
						getNodeId(t_ns),
						AASSubModelElementType::getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASSUBTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText),
						ObjectTypeAttributes(AttrNames::DisplayName, AttrNames::Description, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					return services.addForwardReference(
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_GENERATESEVENT),
						ExpandedNodeId::numeric(UA_NS0ID_BASEEVENTTYPE),
						UA_NODECLASS_OBJECTTYPE
					);

				}
			}
		}
	}
}
#endif