/*
 * HasDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASDATASPECIFICATION_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASDATASPECIFICATION_H_

#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/reference/IReference.h"

#include "basyx/object.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class HasDataSpecification : 
	public virtual IHasDataSpecification,
	public virtual vab::ElementMap
{
public:
	HasDataSpecification();
	HasDataSpecification(basyx::object & obj);
	HasDataSpecification(const basyx::specificCollection_t<IReference> & refs);

	~HasDataSpecification() = default;

	// Inherited via IHasDataSpecification
	virtual basyx::specificCollection_t<IReference> getDataSpecificationReferences() const override;

	void setDataSpecificationReferences(const basyx::specificCollection_t<IReference> & references);
};

}
}

#endif
