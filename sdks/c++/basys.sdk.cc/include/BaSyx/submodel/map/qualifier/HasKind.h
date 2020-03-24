/*
 * HasKind.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_HASKIND_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_HASKIND_H_

#include <BaSyx/submodel/api/qualifier/IHasKind.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {

class HasKind : 
	public virtual IHasKind, 
	public virtual basyx::vab::ElementMap
{
public:
	// constructors
	HasKind(Kind kind = Kind::Instance);
	HasKind(basyx::object object);
  HasKind(const IHasKind & other);

	void Init(Kind kind = Kind::Instance);

	~HasKind() = default;

	// Inherited via IHasKind
	virtual Kind getHasKindReference() const override;

	// not inherited
	void setHasKindReference(Kind kind);
};

}
}

#endif
