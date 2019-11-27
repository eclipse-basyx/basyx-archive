/*
 * HasKind.cpp
 *
 *      Author: wendel
 */

#include "HasKind.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace haskind {

HasKind::HasKind() :
  kind(Kind::NOTSPECIFIED)
{}

HasKind::HasKind(Kind kind) :
  kind(kind)
{}

qualifier::haskind::Kind HasKind::getHasKindReference() const
{
  return this->kind;
}

void HasKind::setHasKindReference(Kind kind)
{
  this->kind = kind;
}

}
}
}
}
}
}