/*
 * Constraint.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_CONSTRAINT_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_CONSTRAINT_H_

#include "submodel/api/qualifier/qualifiable/IConstraint.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class Constraint : 
	public virtual IConstraint,
	public virtual vab::ElementMap
{
public:
	Constraint();
	Constraint(basyx::object object);
	Constraint(const IConstraint & constraint);

	~Constraint() = default;
};

}
}

#endif
