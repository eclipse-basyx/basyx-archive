/*
 * HasKind.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_HASKIND_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_HASKIND_H_

#include "aas/qualifier/haskind/IHasKind.h"
#include "Kind.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace haskind {

class HasKind : public aas::qualifier::haskind::IHasKind
{
public:
  ~HasKind() = default;

  // constructors
  HasKind();
  HasKind(Kind kind);

  // Inherited via IHasKind
  virtual qualifier::haskind::Kind getHasKindReference() const override;

  // not inherited
  void setHasKindReference(Kind kind);

private:
  Kind kind;
};

}
}
}
}
}
}

#endif
