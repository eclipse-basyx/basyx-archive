/*
 * HasSemantics.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASSEMANTICS_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASSEMANTICS_H_

#include "submodel/api/qualifier/IHasSemantics.h"
#include "submodel/api/reference/IReference.h"

#include "basyx/object.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {


class HasSemantics : 
	public virtual IHasSemantics,
	public virtual vab::ElementMap
{
public:
	~HasSemantics() = default;

	HasSemantics();
	HasSemantics(basyx::object object);
	HasSemantics(const std::shared_ptr<IReference> & reference);

	void setSemanticId(const std::shared_ptr<IReference> & reference);
	void setSemanticId(const IReference & reference);

	// Inherited via IHasSemantics
	virtual std::shared_ptr<IReference> getSemanticId() const override;
};

}
}

#endif
