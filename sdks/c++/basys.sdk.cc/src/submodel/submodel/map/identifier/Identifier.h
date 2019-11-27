/*
 * Identifier.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_IDENTIFIER_H_
#define AAS_IMPL_METAMODEL_IDENTIFIER_H_

#include "submodel/api/identifier/IIdentifier.h"

#include "basyx/types.h"
#include "basyx/object.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class Identifier : 
	public IIdentifier,
	public vab::ElementMap
{
public:
	struct IdentifierType {
		static constexpr char IRDI[] = "IRDI";
	};
public:
	~Identifier() = default;

	Identifier();
	Identifier(const std::string & id, const std::string & idType);
	Identifier(basyx::object object);

	// Inherited via IIdentifier
	virtual std::string getIdType() const override;
	virtual std::string getId() const override;
};

}
}

#endif
