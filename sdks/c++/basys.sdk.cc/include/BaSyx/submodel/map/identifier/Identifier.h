/*
 * Identifier.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_IDENTIFIER_H_
#define AAS_IMPL_METAMODEL_IDENTIFIER_H_

#include <BaSyx/submodel/api/identifier/IIdentifier.h>

#include <BaSyx/shared/types.h>
#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {

class Identifier : 
	public IIdentifier,
	public virtual vab::ElementMap
{
public:
	~Identifier() = default;

	Identifier();
	Identifier(const std::string & id, const std::string & idType);
	Identifier(basyx::object object);
  Identifier(const std::shared_ptr<IIdentifier> & other);
  Identifier(const IIdentifier & other);

	// Inherited via IIdentifier
	virtual std::string getIdType() const override;
	virtual std::string getId() const override;
};

}
}

#endif
