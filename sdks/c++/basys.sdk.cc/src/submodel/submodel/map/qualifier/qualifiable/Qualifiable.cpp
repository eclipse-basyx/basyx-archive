/*
 * Qualifiable.cpp
 *
 *      Author: wendel
 */

#include "Qualifiable.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace qualifiable {

Qualifiable::Qualifiable()
{}

Qualifiable::Qualifiable(const std::shared_ptr<aas::qualifier::qualifiable::IConstraint>& constraint) :
  constraints {constraint}
{}

Qualifiable::Qualifiable(const basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> constraints) :
  constraints {constraints}
{}

basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> Qualifiable::getQualifier() const
{
  return this->constraints;
}

void Qualifiable::setQualifier(const basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint>& qualifiers)
{
  this->constraints = constraints;
}

}
}
}
}
}
}