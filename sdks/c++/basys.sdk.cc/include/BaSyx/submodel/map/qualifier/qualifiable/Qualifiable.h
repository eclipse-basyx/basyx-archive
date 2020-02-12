/*
 * Qualifiable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIABLE_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIABLE_H_

#include <BaSyx/submodel/api/qualifier/qualifiable/IQualifiable.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {

class Qualifiable : 
	public virtual IQualifiable,
	public virtual vab::ElementMap
{
public:
	~Qualifiable() = default;

	// constructors
	Qualifiable();
	Qualifiable(basyx::object object);
	Qualifiable(const std::shared_ptr<IConstraint> & constraint);
	Qualifiable(const basyx::specificCollection_t<IConstraint> constraints);

	// Inherited via IQualifiable
	virtual basyx::specificCollection_t<IConstraint> getQualifier() const override;

	// not inherited
	void setQualifier(const basyx::specificCollection_t<IConstraint> & qualifiers);
};

}
}

#endif
