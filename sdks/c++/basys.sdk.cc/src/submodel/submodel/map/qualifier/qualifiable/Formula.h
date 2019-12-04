/*
 * Formula.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_FORMULA_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_FORMULA_H_

#include "submodel/api/qualifier/qualifiable/IFormula.h"
#include "Constraint.h"

namespace basyx {
namespace submodel {

class Formula 
  : public virtual Constraint
  , public IFormula
  , public virtual vab::ElementMap
{
public:
  ~Formula() = default;

  // constructors
  Formula();
  Formula(const basyx::specificCollection_t<IReference> & dependsOn);

  // Inherited via IFormula
  virtual basyx::specificCollection_t<IReference> getDependsOn() const override;

  // not inherited
  void setDependsOn(const basyx::specificCollection_t<IReference> & dependsOn);

};

}
}

#endif
