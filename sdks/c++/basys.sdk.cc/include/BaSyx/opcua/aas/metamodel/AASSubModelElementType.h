#ifndef AAS_SUBMODEL_ELEMENT_TYPE
#define AAS_SUBMODEL_ELEMENT_TYPE

#include <BaSyx/opcua/common/NodeId.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/QualifiedName.h>
#include <BaSyx/opcua/common/ObjectAttributes.h>
#include <BaSyx/opcua/common/ObjectTypeAttributes.h>
#include <BaSyx/opcua/aas/metamodel/AASQualifierType.h>
#include <BaSyx/opcua/aas/metamodel/IAASReferableType.h>
#include <BaSyx/opcua/aas/metamodel/AASReferenceType.h>

namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			namespace metamodel
			{
				struct AASSubModelElementType
				{
					struct AttrNames
					{
						static constexpr const char InstanceName[] = "SubModelElement";
						static constexpr const char BrowseText[] = "AASSubModelElementType";
						static constexpr const char DisplayName[] = "AASSubModelElementType";
						static constexpr const char Description[] = "AASSubModelElementType";
						static constexpr const char NodeHandle[] = "AASSubModelElementType";

						static constexpr const char BrowseText_Kind[] = "AASSubModelElementType::Kind";
						static constexpr const char DisplayName_Kind[] = "Kind";
						static constexpr const char Description_Kind[] = "AASSubModelElementType::Kind";
						static constexpr const char NodeHandle_Kind[] = "AASSubModelElementType::Kind";

						static constexpr const char BrowseText_Qualifier[] = "AASSubModelElementType::<Qualifier>";
						static constexpr const char DisplayName_Qualifier[] = "<Qualifier>";
						static constexpr const char Description_Qualifier[] = "AASSubModelElementType::Qualifier";
						static constexpr const char NodeHandle_Qualifier[] = "AASSubModelElementType::Qualifier";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static BrowsePath getIdShortPath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static BrowsePath getCategoryPath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static BrowsePath getKindPath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static BrowsePath getKindQaulifier(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASSubModelElementType::getIdShortPath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return { {services.getBrowseNameFromNodeId(t_parentNode)},
							{BrowseName(t_ns, IAASReferableType::AttrNames::BrowseText_IdShort)}
					};
				}

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASSubModelElementType::getCategoryPath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return { {services.getBrowseNameFromNodeId(t_parentNode)},
							{BrowseName(t_ns, IAASReferableType::AttrNames::BrowseText_Category)}
					};
				}

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASSubModelElementType::getKindPath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return { {services.getBrowseNameFromNodeId(t_parentNode)},
							{BrowseName(t_ns, AttrNames::BrowseText_Kind)}
					};
				}

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASSubModelElementType::getKindQaulifier(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return { {services.getBrowseNameFromNodeId(t_parentNode)},
							{BrowseName(t_ns, AttrNames::BrowseText_Qualifier)}
					};
				}
				

				template<typename CONNECTOR_TYPE>
				UA_StatusCode AASSubModelElementType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					UA_StatusCode status = UA_STATUSCODE_GOOD;

					Services<CONNECTOR_TYPE> services(t_connector);

					NodeId kind(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_Kind);
					NodeId qualifier(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_Qualifier);

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

					status = services.addForwardReference(
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASINTERFACE),
						ExpandedNodeId::string(t_ns, IAASReferableType::AttrNames::NodeHandle),
						UA_NODECLASS_OBJECTTYPE
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addVariableNode(
						kind,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_Kind),
						VariableAttributes(UA_TYPES_STRING, AttrNames::DisplayName_Kind, AttrNames::Description_Kind, "en-US")
					);


					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addForwardReferenceModellingRuleMandatory(kind, UA_NODECLASS_VARIABLE);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addObjectNode(
						qualifier,
						getNodeId(t_ns),
						AASQualifierType::getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASCOMPONENT),
						QualifiedName(t_ns, AttrNames::BrowseText_Qualifier),
						ObjectAttributes(AttrNames::DisplayName_Qualifier, AttrNames::Description_Qualifier, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					return services.addForwardReferenceModellingRuleOptional(qualifier, UA_NODECLASS_OBJECT);
				}
			}
		}
	}
}
#endif
