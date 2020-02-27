/*
 * HasDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASDATASPECIFICATION_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASDATASPECIFICATION_H_

#include <BaSyx/submodel/api/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api/reference/IReference.h>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

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
	HasDataSpecification(const IHasDataSpecification & hasDataSpecification);

	~HasDataSpecification() = default;

	// Inherited via IHasDataSpecification
	virtual basyx::specificCollection_t<IReference> getDataSpecificationReferences() const override;

	void setDataSpecificationReferences(const basyx::specificCollection_t<IReference> & references);
};

}
}

#endif
