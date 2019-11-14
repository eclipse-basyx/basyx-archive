/*
 * Formula.cpp
 *
 *      Author: wendel
 */

#include "Formula.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace qualifiable {

Formula::Formula()
{}

Formula::Formula(const basyx::specificCollection_t<aas::reference::IReference>& dependsOn) :
  dependsOn {dependsOn}
{}

basyx::specificCollection_t<aas::reference::IReference> Formula::getDependsOn() const
{
  return this->dependsOn;
}

void Formula::setDependsOn(const basyx::specificCollection_t<aas::reference::IReference>& dependsOn)
{
  this->dependsOn = dependsOn;
}

}
}
}
}
}
}