#ifndef AAS_SUBMODEL_TYPE_H
#define AAS_SUBMODEL_TYPE_H

#include <BaSyx/opcua/common/NodeId.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/QualifiedName.h>
#include <BaSyx/opcua/common/ObjectAttributes.h>
#include <BaSyx/opcua/common/ObjectTypeAttributes.h>
#include <BaSyx/opcua/aas/metamodel/AASQualifierType.h>
#include <BaSyx/opcua/aas/metamodel/AASSubModelElementType.h>
#include <BaSyx/opcua/aas/metamodel/IAASIdentifiableType.h>

namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			namespace metamodel
			{
				struct AASSubModelType
				{
					struct AttrNames
					{
						static constexpr const char InstanceName[] = "Submodel";
						static constexpr const char BrowseText[] = "AASSubmodelType";
						static constexpr const char DisplayName[] = "AASSubmodelType";
						static constexpr const char Description[] = "AASSubmodelType";
						static constexpr const char NodeHandle[] = "AASSubmodelType";

						static constexpr const char BrowseText_ReferenceDataSpec[] = "AASReferenceType::<DataSpecification>";
						static constexpr const char DisplayName_ReferenceDataSpec[] = "<DataSpecification>";
						static constexpr const char Description_ReferenceDataSpec[] = "AASReferenceType::DataSpecification";
						static constexpr const char NodeHandle_ReferenceDataSpec[] = "AASReferenceType::DataSpecification";

						static constexpr const char BrowseText_PropertyKind[] = "Kind";
						static constexpr const char DisplayName_PropertyKind[] = "Kind";
						static constexpr const char Description_PropertyKind[] = "AASSubmodelType::PropertyType::Kind";
						static constexpr const char NodeHandle_PropertyKind[] = "AASSubmodelType::PropertyType::Kind";

						static constexpr const char BrowseText_Qualifier[] = "AASQualifierType::<Qualifier>";
						static constexpr const char DisplayName_Qualifier[] = "<Qualifier>";
						static constexpr const char Description_Qualifier[] = "AASSubmodelType::AASQualifierType::Qualifier";
						static constexpr const char NodeHandle_Qualifier[] = "AASSubmodelType::AASQualifierType::Qualifier";

						static constexpr const char BrowseText_SubModelElement[] = "SubModelElementType::<SubModelElement>";
						static constexpr const char DisplayName_SubModelElement[] = "<SubModelElement>";
						static constexpr const char Description_SubModelElement[] = "AASSubmodelType::SubModelElementType::SubModelElement";
						static constexpr const char NodeHandle_SubModelElement[] = "AASSubmodelType::SubModelElementType::SubModelElement";
					};


					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static BrowsePath getKindPath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASSubModelType::getKindPath(int32_t t_ns, CONNECTOR_TYPE &t_connector ,const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return { {services.getBrowseNameFromNodeId(t_parentNode)},
							{BrowseName(t_ns, AttrNames::BrowseText_PropertyKind)}
					};
				}

				template<typename CONNECTOR_TYPE>
				UA_StatusCode AASSubModelType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{

					using namespace basyx::opcua;

					UA_StatusCode status = UA_STATUSCODE_GOOD;

					Services<CONNECTOR_TYPE> services(t_connector);

					NodeId referenceDataSpec(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_ReferenceDataSpec);
					NodeId propertyKind(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_PropertyKind);
					NodeId qualifier(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_Qualifier);
					NodeId submodeElement(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_SubModelElement);


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
						ExpandedNodeId::string(t_ns, IAASIdentifiableType::AttrNames::NodeHandle),
						UA_NODECLASS_OBJECTTYPE
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addVariableNode(
						propertyKind,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_PropertyKind),
						VariableAttributes(UA_TYPES_STRING, AttrNames::DisplayName_PropertyKind, AttrNames::Description_PropertyKind, "en-US")
					);

					if (UA_STATUSCODE_GOOD != status)
					{
						return status;
					}

					status = services.addForwardReferenceModellingRuleMandatory(propertyKind, UA_NODECLASS_VARIABLE);


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

					status = services.addForwardReferenceModellingRuleOptional(qualifier, UA_NODECLASS_OBJECT);


					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addObjectNode(
						submodeElement,
						getNodeId(t_ns),
						AASSubModelElementType::getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASCOMPONENT),
						QualifiedName(t_ns, AttrNames::BrowseText_SubModelElement),
						ObjectAttributes(AttrNames::DisplayName_SubModelElement, AttrNames::Description_SubModelElement, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					return services.addForwardReferenceModellingRuleOptional(submodeElement, UA_NODECLASS_OBJECT);
				}
			}
		}
	}
}

#endif
