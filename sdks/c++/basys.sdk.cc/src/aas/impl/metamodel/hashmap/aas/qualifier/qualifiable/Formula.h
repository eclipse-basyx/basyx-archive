/*
 * Formula.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_FORMULA_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_FORMULA_H_

#include "aas/qualifier/qualifiable/IFormula.h"
#include "Constraint.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace qualifiable {

class Formula : public Constraint, public aas::qualifier::qualifiable::IFormula
{
public:
  ~Formula() = default;

  // constructors
  Formula();
  Formula(const basyx::specificCollection_t<aas::reference::IReference> & dependsOn);

  // Inherited via IFormula
  virtual basyx::specificCollection_t<aas::reference::IReference> getDependsOn() const override;

  // not inherited
  void setDependsOn(const basyx::specificCollection_t<aas::reference::IReference> & dependsOn);

private:
  basyx::specificCollection_t<aas::reference::IReference> dependsOn;
};

}
}
}
}
}
}

#endif
