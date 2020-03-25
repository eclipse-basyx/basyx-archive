/*
 * View.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/aas/map/parts/View.h>

#include <BaSyx/submodel/map/reference/Reference.h>

using namespace basyx::submodel;

namespace basyx {
namespace aas {

View::View(basyx::object obj)
  : vab::ElementMap(obj)
{}

View::View()
  : vab::ElementMap()
  , ModelType(IView::Path::ModelType)
{}

View::View(const basyx::specificCollection_t<submodel::IReference>& references)
  : vab::ElementMap()
  , ModelType(IView::Path::ModelType)
{
  this->setContainedElements(references);
}

void View::setContainedElements(const basyx::specificCollection_t<submodel::IReference>& references)
{
  auto description_objects = vab::ElementMap::make_object_list<IReference, Reference>(references);
  this->map.insertKey(IView::Path::ContainedElement, description_objects);
}

basyx::specificCollection_t<submodel::IReference> View::getContainedElements() const
{
  auto description_objects = this->map.getProperty(IView::Path::ContainedElement).Get<basyx::object::object_list_t>();
  return vab::ElementMap::make_specific_collection<IReference, Reference>(description_objects);
}

}
}
