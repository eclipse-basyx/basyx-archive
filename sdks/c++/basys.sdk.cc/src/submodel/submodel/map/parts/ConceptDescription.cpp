/*
 * ConceptDictionary.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/parts/ConceptDescription.h>

#include <BaSyx/submodel/map/reference/Reference.h>

using namespace basyx::submodel;

namespace basyx {
namespace submodel {

ConceptDescription::ConceptDescription(basyx::object obj)
  : vab::ElementMap(obj)
{}

ConceptDescription::ConceptDescription()
  : vab::ElementMap()
  , submodel::ModelType(IConceptDescription::Path::ModelType)
{}

ConceptDescription::ConceptDescription(IConceptDescription & other)
  : vab::ElementMap()
  , submodel::ModelType(IConceptDescription::Path::ModelType)
{
  this->setIsCaseOf(other.getIsCaseOf());
}


basyx::specificCollection_t<IReference> ConceptDescription::getIsCaseOf() const
{
  auto description_objects = this->map.getProperty(IConceptDescription::Path::IsCaseOf).Get<basyx::object::object_list_t>();
  return vab::ElementMap::make_specific_collection<IReference, Reference>(description_objects);  
}

void ConceptDescription::setIsCaseOf(const basyx::specificCollection_t<IReference>& references)
{
  auto description_objects = vab::ElementMap::make_object_list<IReference, Reference>(references);
  this->map.insertKey(IConceptDescription::Path::IsCaseOf, description_objects);
}

}
}

