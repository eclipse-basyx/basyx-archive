#ifndef AAS_DICTIONARY_ENTRY_IDHOSRT_H
#define AAS_DICTIONARY_ENTRY_IDHOSRT_H

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
				struct DictionaryEntryIdShort
				{
				public:
					struct AttrNames
					{
						static constexpr const char BrowseText[] = "DictionaryEntryIdShort::IdShort";
						static constexpr const char DisplayName[] = "IdShortDictionaryEntryType";
						static constexpr const char Description[] = "DictionaryEntryIdShort::IdShort";
						static constexpr const char NodeHandle[] = "DictionaryEntryIdShort::IdShort";
					};

					static basyx::opcua::NodeId getNodeId(int32_t t_ns)
					{
						return basyx::opcua::NodeId::string(t_ns, AttrNames::NodeHandle);
					}

					template<typename CONNECTOR_TYPE>
					static UA_StatusCode define(int32_t t_ns, CONNECTOR_TYPE& t_client);
				};

				template<typename CONNECTOR_TYPE>
				inline UA_StatusCode DictionaryEntryIdShort::define(int32_t t_ns, CONNECTOR_TYPE & t_connector)
				{
					using namespace basyx::opcua;

					Services<CONNECTOR_TYPE> services(t_connector);

					return services.addObjectNode(
						getNodeId(t_ns),
						NodeId::numeric(UA_NS0ID_DICTIONARYENTRYTYPE),
						NodeId::numeric(UA_NS0ID_DICTIONARYENTRYTYPE),
						NodeId::numeric(UA_NS0ID_HASSUBTYPE),
						QualifiedName(t_ns, AttrNames::BrowseText),
						ObjectAttributes(AttrNames::DisplayName, AttrNames::Description, "en-US")
					);
				}
			}
		}
	}
}
#endif