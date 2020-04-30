#ifndef BASYX_SUBMODEL_API_V2_REFERENCE_KEYTYPE_H
#define BASYX_SUBMODEL_API_V2_REFERENCE_KEYTYPE_H

#include <BaSyx/util/util.h>

#include <string>
#include <unordered_map>

namespace basyx {
namespace submodel {

enum class KeyType
{
	IdShort,
	FragementId,
	Custom,
	IRDI,
	URI,
	Unknown
};

struct KeyTypeUtil
{
	inline static std::string toString(KeyType keyType)
	{
		switch (keyType)
		{
		case KeyType::IdShort:
			return "IdShort";
			break;
		case KeyType::FragementId:
			return "FragementId";
			break;
		case KeyType::Custom:
			return "Custom";
			break;
		case KeyType::IRDI:
			return "IRDI";
			break;
		case KeyType::URI:
			return "URI";
			break;
		case KeyType::Unknown:
		default:
			return "Unknown";
			break;
		};
	};

	inline static KeyType fromString(const std::string & str)
	{
		const std::unordered_map<std::string, KeyType> table = {
			{"IdShort", KeyType::IdShort},
			{"FragementId", KeyType::FragementId},
			{"Custom", KeyType::Custom},
			{"IRDI", KeyType::IRDI},
			{"URI", KeyType::URI},
			{"Unknown", KeyType::Unknown},
		};

		if (table.find(str) != table.end())
			return table.at(str);

		return KeyType::Unknown;
	};
};


enum class LocalKeyType
{
	IdShort,
	FragementId
};

}
}

#endif /* BASYX_SUBMODEL_API_V2_REFERENCE_KEYTYPE_H */
