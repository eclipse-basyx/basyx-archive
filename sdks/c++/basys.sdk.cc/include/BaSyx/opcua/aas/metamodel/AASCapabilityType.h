#ifndef AAS_CAPABILITY_TYPE_H
#define AAS_CAPABILITY_TYPE_H

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
				struct AASCapabilityType
				{
					struct AttrNames
					{
						static constexpr const char BrowseText[] = "AASCapabilityType";
						static constexpr const char DisplayName[] = "AASCapabilityType";
						static constexpr const char Description[] = "AASCapabilityType";
						static constexpr const char NodeHandle[] = "AASCapabilityType";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				UA_StatusCode AASCapabilityType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					Services<CONNECTOR_TYPE> services(t_connector);

					return services.addObjectTypeNode(
						getNodeId(t_ns),
						AASSubModelElementType::getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASSUBTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText),
						ObjectTypeAttributes(AttrNames::DisplayName, AttrNames::Description, "en-US")
					);
				};
			}
		}
	}
}
#endif