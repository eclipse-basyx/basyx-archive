#ifndef AAS_ADMINISTRATIVE_INFORMATION_TYPE_H
#define AAS_ADMINISTRATIVE_INFORMATION_TYPE_H

#include <BaSyx/opcua/common/NodeId.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/QualifiedName.h>
#include <BaSyx/opcua/common/ObjectAttributes.h>
#include <BaSyx/opcua/common/ObjectTypeAttributes.h>


namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			namespace metamodel
			{
				struct AASAdministrativeInformationType
				{
					struct AttrNames
					{
						static constexpr const char InstanceName[] = "AdministrativeInformation";
						static constexpr const char BrowseText[] = "AASAdministrativeInformationType";
						static constexpr const char DisplayName[] = "AASAdministrativeInformationType";
						static constexpr const char Description[] = "AASAdministrativeInformationType";
						static constexpr const char NodeHandle[] = "AASAdministrativeInformationType";

						static constexpr const char BrowseText_Version[] = "PropertyType::Version";
						static constexpr const char DisplayName_Version[] = "Version";
						static constexpr const char Description_Version[] = "AASAdministrativeInformationType::PropertyType::Version";
						static constexpr const char NodeHandle_Version[] = "AASAdministrativeInformationType::PropertyType::Version";

						static constexpr const char BrowseText_Revision[] = "PropertyType::Revision";
						static constexpr const char DisplayName_Revision[] = "Revision";
						static constexpr const char Description_Revision[] = "AASAdministrativeInformationType::PropertyType::Revision";
						static constexpr const char NodeHandle_Revision[] = "AASAdministrativeInformationType::PropertyType::Revision";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				UA_StatusCode AASAdministrativeInformationType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					UA_StatusCode status = UA_STATUSCODE_GOOD;

					Services<CONNECTOR_TYPE> services(t_connector);

					NodeId version(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_Version);
					NodeId revision(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_Revision);

					status = services.addObjectTypeNode(
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_BASEOBJECTTYPE),
						NodeId::numeric(UA_NS0ID_HASSUBTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText),
						ObjectTypeAttributes(AttrNames::DisplayName, AttrNames::Description, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addVariableNode(
						version,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_Version),
						VariableAttributes(UA_TYPES_STRING, AttrNames::DisplayName_Version, AttrNames::Description_Version, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addForwardReferenceModellingRuleMandatory(version, UA_NODECLASS_VARIABLE);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addVariableNode(
						revision,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_Revision),
						VariableAttributes(UA_TYPES_STRING, AttrNames::DisplayName_Revision, AttrNames::Description_Revision, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					return services.addForwardReferenceModellingRuleMandatory(revision, UA_NODECLASS_VARIABLE);
				}
			}
		}
	}
}

#endif