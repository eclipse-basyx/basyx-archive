/*
 * HasSemantics.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASSEMANTICS_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_HASSEMANTICS_H_

#include "aas/qualifier/IHasSemantics.h"
#include "aas/reference/IReference.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

class HasSemantics : public aas::qualifier::IHasSemantics
{
public:
  ~HasSemantics() = default;

  HasSemantics();
  HasSemantics(const std::shared_ptr<aas::reference::IReference> & reference);

  void setSemanticId(const std::shared_ptr<aas::reference::IReference> & reference);

  // Inherited via IHasSemantics
  virtual std::shared_ptr<aas::reference::IReference> getSemanticId() const override;

private:
  std::shared_ptr<aas::reference::IReference> reference;
};

}
}
}
}
}

#endif
