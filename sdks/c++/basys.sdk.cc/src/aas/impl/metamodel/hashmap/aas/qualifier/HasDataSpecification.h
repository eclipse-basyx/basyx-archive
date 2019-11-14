/*
 * HasDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASDATASPECIFICATION_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASDATASPECIFICATION_H_

#include "aas/qualifier/IHasDataSpecification.h"
#include "aas/reference/IReference.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

class HasDataSpecification : public aas::qualifier::IHasDataSpecification
{
public:
  ~HasDataSpecification() = default;

  // Inherited via IHasDataSpecification
  virtual basyx::specificCollection_t<aas::reference::IReference> getDataSpecificationReferences() const override;

  void setDataSpecificationReferences(const basyx::specificCollection_t<aas::reference::IReference> & references);

private:
  basyx::specificCollection_t<aas::reference::IReference> references;

};

}
}
}
}
}

#endif
