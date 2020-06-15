#ifndef BASYX_SUBMODEL_API_V2_IDENTIFIER_IDENTIFIER_H
#define BASYX_SUBMODEL_API_V2_IDENTIFIER_IDENTIFIER_H

#include <BaSyx/submodel/enumerations/IdentifierType.h>

#include <string>

namespace basyx {
namespace submodel {
namespace api {

class Identifier
{
public:
	Identifier() = default;
	Identifier(IdentifierType idType, const std::string & id);
	~Identifier() = default;
private:
	IdentifierType idType;
	std::string id;
public:
	static Identifier Custom(const std::string & id);
	static Identifier URI(const std::string & uri);
	static Identifier IRDI(const std::string & irdi);
public:
	IdentifierType getIdType() const;
	const std::string & getId() const;
};

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_IDENTIFIER_IDENTIFIER_H */
