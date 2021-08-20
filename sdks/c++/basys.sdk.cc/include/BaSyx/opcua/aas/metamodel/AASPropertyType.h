#ifndef AAS_PROPERTY_TYPE_H
#define AAS_PROPERTY_TYPE_H

#include <BaSyx/opcua/common/NodeId.h>
#include <BaSyx/opcua/common/Services.h>
#include <BaSyx/opcua/common/QualifiedName.h>
#include <BaSyx/opcua/common/ObjectAttributes.h>
#include <BaSyx/opcua/common/ObjectTypeAttributes.h>
#include <BaSyx/opcua/aas/metamodel/AASSubModelElementType.h>
#include <BaSyx/opcua/aas/metamodel/AASReferenceType.h>
#include <BaSyx/opcua/aas/metamodel/AASReference.h>
#include <BaSyx/opcua/aas/metamodel/DictionaryEntryFragmentedId.h>
#include <BaSyx/opcua/aas/metamodel/DictionaryEntryIdShort.h>
#include <BaSyx/opcua/aas/metamodel/IAASIdentifiableType.h>

namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			namespace metamodel
			{
				struct AASPropertyType
				{
					struct AttrNames
					{
						static constexpr const char  InstanceName[] = "Property";
						static constexpr const char  BrowseText[] = "AASPropertyType";
						static constexpr const char  DisplayName[] = "AASPropertyType";
						static constexpr const char  Description[] = "AASPropertyType";
						static constexpr const char  NodeHandle[] = "AASPropertyType";

						static constexpr const char  BrowseText_Value[] = "Value";
						static constexpr const char  DisplayName_Value[] = "Value";
						static constexpr const char  Description_Value[] = "AASPropertyType::Value";
						static constexpr const char  NodeHandle_Value[] = "AASPropertyType::Value";

						static constexpr const char  BrowseText_ValueType[] = "ValueType";
						static constexpr const char  DisplayName_ValueType[] = "ValueType";
						static constexpr const char  Description_ValueType[] = "AASPropertyType::ValueType";
						static constexpr const char  NodeHandle_ValueType[] = "AASPropertyType::ValueType";

						static constexpr const char  BrowseText_ReferenceValueId[] = "ValueId";
						static constexpr const char  DisplayName_ReferenceValueId[] = "ValueId";
						static constexpr const char  Description_ReferenceValueId[] = "AASPropertyType::AASReferenceType::ValueId";
						static constexpr const char  NodeHandle_ReferenceValueId[] = "AASPropertyType::AASReferenceType::ValueId";
					};

					struct TypeName
					{
						static constexpr const char bool_[] = "xsd:boolean";
						static constexpr const char int8[] = "xsd:byte";
						static constexpr const char uint8[] = "xsd:unsignedByte";
						static constexpr const char int16[] = "xsd:short";
						static constexpr const char uint16[] = "xsd:unsignedShort";
						static constexpr const char int32[] = "xsd:int";
						static constexpr const char uint32[] = "xsd:unsignedInt";
						static constexpr const char int64[] = "xsd:long";
						static constexpr const char uint64[] = "xsd:unsignedLong";
						static constexpr const char float_[] = "xsd:float";
						static constexpr const char double_[] = "xsd:double";
						static constexpr const char string_[] = "xsd:string";
						static constexpr const char anyUri[] = "xsd:anyuri";
						static constexpr const char date[] = "xsd:date";
						static constexpr const char time[] = "xsd:time";
						static constexpr const char dayTimeDuration[] = "xsd:dayTimeDuration";
						static constexpr const char yearMonthDuration[] = "xsd:yearMonthDuration";
						static constexpr const char dateTime[] = "xsd:dateTime";
						static constexpr const char gDay[] = "xsd:gDay";
						static constexpr const char gMonth[] = "xsd:gMonth";
						static constexpr const char gMonthDay[] = "xsd:gMonthDay";
						static constexpr const char gYear[] = "xsd:gYear";
						static constexpr const char gYearMonth[] = "xsd:gYearMonth";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static BrowsePath getValuePath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static BrowsePath getValueTypePath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static BrowsePath getReferenceTypePath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId& t_parentNode);

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASPropertyType::getValuePath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return {{services.getBrowseNameFromNodeId(t_parentNode)},{BrowseName(t_ns, AttrNames::BrowseText_Value)}};
				}

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASPropertyType::getValueTypePath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return {{services.getBrowseNameFromNodeId(t_parentNode)},{BrowseName(t_ns, AttrNames::BrowseText_ValueType)}};
				}

				template<typename CONNECTOR_TYPE>
				inline BrowsePath AASPropertyType::getReferenceTypePath(int32_t t_ns, CONNECTOR_TYPE & t_connector, const NodeId & t_parentNode)
				{
					Services<CONNECTOR_TYPE> services(t_connector);

					return {{services.getBrowseNameFromNodeId(t_parentNode)},{BrowseName(t_ns, AttrNames::BrowseText_ReferenceValueId)}};
				}

				template<typename CONNECTOR_TYPE>
				UA_StatusCode AASPropertyType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					Services<CONNECTOR_TYPE> services(t_connector);

					UA_StatusCode status = UA_STATUSCODE_GOOD;

					NodeId referenceValueId (UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_ReferenceValueId);
					NodeId value            (UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_Value);
					NodeId valueType        (UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_ValueType);

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
						value,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_Value),
						VariableAttributes(UA_TYPES_STRING, AttrNames::DisplayName_Value, AttrNames::Description_Value, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addForwardReferenceModellingRuleOptional(value, UA_NODECLASS_VARIABLE);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addVariableNode(
						valueType,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASPROPERTY),
						NodeId::numeric(UA_NS0ID_PROPERTYTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText_ValueType),
						VariableAttributes(UA_TYPES_STRING, AttrNames::DisplayName_ValueType, AttrNames::Description_ValueType, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addForwardReferenceModellingRuleMandatory(valueType, UA_NODECLASS_VARIABLE);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					status = services.addObjectNode(
						referenceValueId,
						getNodeId(t_ns),
						AASReferenceType::getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_HASCOMPONENT),
						QualifiedName(t_ns, AttrNames::BrowseText_ReferenceValueId),
						ObjectAttributes(AttrNames::DisplayName_ReferenceValueId, AttrNames::Description_ReferenceValueId, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					return services.addForwardReferenceModellingRuleMandatory(referenceValueId, UA_NODECLASS_OBJECT);
				};
			}
		}
	}
}

#endif