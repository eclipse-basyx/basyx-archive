/*
 * Formula.cpp
 *
 *      Author: wendel
 */

#include "Formula.h"

namespace basyx {
namespace submodel {


Formula::Formula()
{}

Formula::Formula(const basyx::specificCollection_t<IReference>& dependsOn) :
  dependsOn {dependsOn}
{}

basyx::specificCollection_t<IReference> Formula::getDependsOn() const
{
  return this->dependsOn;
}

void Formula::setDependsOn(const basyx::specificCollection_t<IReference>& dependsOn)
{
  this->dependsOn = dependsOn;
}

}
}
