#ifndef BASYX_SUBMODEL_SIMPLE_IDENTIFIER_IDENTIFIER_H
#define BASYX_SUBMODEL_SIMPLE_IDENTIFIER_IDENTIFIER_H

#include <BaSyx/submodel/api_v2/identifier/IdentifierType.h>

#include <string>

namespace basyx {
namespace submodel {
namespace simple {

class Identifier
{
public:
	Identifier();
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


  friend bool operator==(const Identifier & left, const Identifier & right);
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_IDENTIFIER_IDENTIFIER_H */
