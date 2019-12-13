/*
 * Formula.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/qualifiable/Formula.h>
#include <BaSyx/submodel/map/reference/Reference.h>

namespace basyx {
namespace submodel {


Formula::Formula()
{}

Formula::Formula(const basyx::specificCollection_t<IReference>& dependsOn) :
  vab::ElementMap{}
{
  this->setDependsOn(dependsOn);
}

basyx::specificCollection_t<IReference> Formula::getDependsOn() const
{
  auto & obj_list = this->map.getProperty(IFormula::Path::Dependson).Get<basyx::object::object_list_t&>();
  return vab::ElementMap::make_specific_collection<IReference, Reference>(obj_list);
}

void Formula::setDependsOn(const basyx::specificCollection_t<IReference>& dependsOn)
{
  auto obj_list = vab::ElementMap::make_object_list<IReference, Reference>(dependsOn);
  this->map.insertKey(IFormula::Path::Dependson, obj_list);
}

}
}
