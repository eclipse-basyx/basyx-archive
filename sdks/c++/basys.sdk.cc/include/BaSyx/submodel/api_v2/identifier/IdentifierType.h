#ifndef BASYX_SUBMODEL_API_V2_IDENTIFIER_IDENTIFIERTYPE_H
#define BASYX_SUBMODEL_API_V2_IDENTIFIER_IDENTIFIERTYPE_H

#include <BaSyx/util/util.h>

#include <string>
#include <unordered_map>

namespace basyx {
namespace submodel {

enum class IdentifierType
{
	Custom,
	IRDI,
	URI,
	Unknown
};

struct IdentifierTypeUtil {
		inline static std::string toString(IdentifierType val)
		{
			switch (val)
			{
			case IdentifierType::Custom:
				return "Custom";
				break;
			case IdentifierType::IRDI:
				return "IRDI";
				break;
			case IdentifierType::URI:
				return "URI";
				break;
			case IdentifierType::Unknown:
			default:
				return "Unknown";
				break;
			};
		};

		inline static IdentifierType fromString(const std::string & str)
		{
			const std::unordered_map<std::string, IdentifierType> table = {
				{"Custom", IdentifierType::Custom},
				{"IRDI", IdentifierType::IRDI},
				{"URI", IdentifierType::URI},
				{"Unknown", IdentifierType::Unknown},
			};

			if (table.find(str) != table.end())
				return table.at(str);

			return IdentifierType::Unknown;
		};
};

}
}

#endif /* BASYX_SUBMODEL_API_V2_IDENTIFIER_IDENTIFIERTYPE_H */
