/*
 * Qualifiable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIABLE_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_QUALIFIABLE_H_

#include "submodel/api/qualifier/qualifiable/IQualifiable.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace qualifiable {

class Qualifiable : public aas::qualifier::qualifiable::IQualifiable
{
public:
  ~Qualifiable() = default;

  // constructors
  Qualifiable();
  Qualifiable(const std::shared_ptr<aas::qualifier::qualifiable::IConstraint> & constraint);
  Qualifiable(const basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> constraints);

  // Inherited via IQualifiable
  virtual basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> getQualifier() const override;

  // not inherited
  void setQualifier(const basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> & qualifiers);

private:
  basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> constraints;
};

}
}
}
}
}
}

#endif
