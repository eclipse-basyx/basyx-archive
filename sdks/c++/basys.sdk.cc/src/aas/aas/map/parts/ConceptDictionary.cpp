/*
 * ConceptDictionary.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/aas/map/parts/ConceptDictionary.h>

#include <BaSyx/submodel/map/reference/Reference.h>

using namespace basyx::submodel;

namespace basyx {
namespace aas {

ConceptDictionary::ConceptDictionary(basyx::object obj)
  : vab::ElementMap(obj)
{}

ConceptDictionary::ConceptDictionary(basyx::specificCollection_t<submodel::IReference> concept_descriptions)
  : vab::ElementMap()
{
  this->setConceptDescription(concept_descriptions);
}

basyx::specificCollection_t<submodel::IReference> ConceptDictionary::getConceptDescription() const
{
  auto description_objects = this->map.getProperty(IConceptDictionary::Path::ConceptDescriptions).Get<basyx::object::object_list_t>();
  return vab::ElementMap::make_specific_collection<IReference, Reference>(description_objects);
}

void ConceptDictionary::setConceptDescription(const basyx::specificCollection_t<submodel::IReference>& references)
{
  auto description_objects = vab::ElementMap::make_object_list<IReference, Reference>(references);
  this->map.insertKey(IConceptDictionary::Path::ConceptDescriptions, description_objects);
}

}
}
