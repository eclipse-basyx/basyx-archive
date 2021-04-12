#ifndef AAS_BLOB_TYPE
#define AAS_BLOB_TYPE

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
				struct AASBlobType
				{
					struct AttrNames
					{
						static constexpr const char BrowseText[] = "AASBlobType";
						static constexpr const char DisplayName[] = "AASBlobType";
						static constexpr const char Description[] = "AASBlobType";
						static constexpr const char NodeHandle[] = "AASBlobType";

						static constexpr const char BrowseText_FileType[] = "FileType::File";
						static constexpr const char DisplayName_FileType[] = "File";
						static constexpr const char Description_FileType[] = "AASBlobType::FileType";
						static constexpr const char NodeHandle_FileType[] = "AASBlobType::FileType";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_connector);
				};

				template<typename CONNECTOR_TYPE>
				inline UA_StatusCode AASBlobType::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					UA_StatusCode status = UA_STATUSCODE_GOOD;

					Services<CONNECTOR_TYPE> services(t_connector);

					NodeId fileType(UA_NodeIdType::UA_NODEIDTYPE_STRING, t_ns, AttrNames::NodeHandle_FileType);

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

					status = services.addObjectNode(
						fileType,
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_FILETYPE),
						NodeId::numeric(UA_NS0ID_HASCOMPONENT),
						QualifiedName(t_ns, AttrNames::BrowseText_FileType),
						ObjectAttributes(AttrNames::DisplayName_FileType, AttrNames::Description_FileType, "en-US")
					);

					if (status != UA_STATUSCODE_GOOD)
					{
						return status;
					}

					return services.addForwardReferenceModellingRuleMandatory(fileType, UA_NODECLASS_OBJECT);
				};
			}
		}
	}
}
#endif