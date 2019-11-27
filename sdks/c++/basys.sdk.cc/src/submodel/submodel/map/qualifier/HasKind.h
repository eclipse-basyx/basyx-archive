/*
 * HasKind.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_HASKIND_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_HASKIND_H_

#include "submodel/api/qualifier/IHasKind.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class HasKind : 
	public virtual IHasKind, 
	public virtual basyx::vab::ElementMap
{
public:
	// constructors
	HasKind(Kind kind = Kind::NotSpecified);
	HasKind(basyx::object object);

	void Init(Kind kind = Kind::NotSpecified);

	~HasKind() = default;

	// Inherited via IHasKind
	virtual Kind getHasKindReference() const override;

	// not inherited
	void setHasKindReference(Kind kind);
};

}
}

#endif
