#ifndef AAS_REFERENCE_H
#define AAS_REFERENCE_H

#include <BaSyx/opcua/common/NodeId.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/QualifiedName.h>
#include <BaSyx/opcua/common/ObjectAttributes.h>
#include <BaSyx/opcua/common/ObjectTypeAttributes.h>
#include <BaSyx/opcua/common/ReferenceTypeAttributes.h>

namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			namespace metamodel
			{
				struct AASReference
				{
					struct AttrNames
					{
						static constexpr const char BrowseName[] = "AASReference";
						static constexpr const char DisplayName[] = "AASReference";
						static constexpr const char Description[] = "AASReference";
						static constexpr const char NodeHandle[] = "AASReference";
					};
					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				inline UA_StatusCode AASReference::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					Services<CONNECTOR_TYPE> services(t_connector);

					return services.addReferenceTypeNode(
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_NONHIERARCHICALREFERENCES),
						NodeId::numeric(UA_NS0ID_HASSUBTYPE),
						QualifiedName(t_ns, AttrNames::BrowseName),
						ReferenceTypeAttributes(AttrNames::DisplayName, AttrNames::Description, "en-US")
					);
				}

			}
		}
	}
}
#endif