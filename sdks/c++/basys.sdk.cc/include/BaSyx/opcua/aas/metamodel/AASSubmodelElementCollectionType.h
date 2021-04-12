#ifndef AAS_SUBMODEL_ELEMENT_COLLECTION_TYPE_H
#define AAS_SUBMODEL_ELEMENT_COLLECTION_TYPE_H

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
				struct AASSubmodelElementCollectionType
				{
					struct AttrNames
					{
						static constexpr const char BrowseText[] = "AASSubmodelElementCollectionType";
						static constexpr const char DisplayName[] = "AASSubmodelElementCollectionType";
						static constexpr const char Description[] = "AASSubmodelElementCollectionType";
						static constexpr const char NodeHandle[] = "AASSubmodelElementCollectionType";

						static constexpr const char BrowseText_AllowDuplicates[] = "PropertyType::AllowDuplicates";
						static constexpr const char DisplayName_AllowDuplicates[] = "AllowDuplicates";
						static constexpr const char Description_AllowDuplicates[] = "AASSubmodelElementCollectionType::PropertyType::AllowDuplicates";
						static constexpr const char NodeHandle_AllowDuplicates[] = "AASSubmodelElementCollectionType::PropertyType::AllowDuplicates";

						static constexpr const char BrowseText_SubModelElement[] = "AASSubModelElementType::SubModelElement";
						static constexpr const char DisplayName_SubModelElement[] = "SubModelElement";
						static constexpr const char Description_SubModelElement[] = "AASSubmodelElementCollectionType::AASSubModelElementType::SubModelElement";
						static constexpr const char NodeHandle_SubModelElement[] = "AASSubmodelElementCollectionType::AASSubModelElementType::SubModelElement";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				UA_StatusCode AASSubmodelElementCollectionType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					UA_StatusCode status = UA_STATUSCODE_GOOD;

					Services<CONNECTOR_TYPE> services(t_connector);

					NodeId allowDuplicates(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_AllowDuplicates);
					NodeId subModelElement(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_SubModelElement);

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

					status = services.addVariableNode(
						allowDuplicates,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_AllowDuplicates),
						VariableAttributes(UA_TYPES_STRING, AttrNames::Description_AllowDuplicates, AttrNames::Description_AllowDuplicates, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addForwardReferenceModellingRuleMandatory(allowDuplicates, UA_NODECLASS_VARIABLE);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addVariableNode(
						subModelElement,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_SubModelElement),
						VariableAttributes(UA_TYPES_STRING, AttrNames::Description_SubModelElement, AttrNames::Description_SubModelElement, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					return services.addForwardReferenceModellingRuleMandatory(subModelElement, UA_NODECLASS_OBJECT);
				}
			}
		}
	}
}
#endif