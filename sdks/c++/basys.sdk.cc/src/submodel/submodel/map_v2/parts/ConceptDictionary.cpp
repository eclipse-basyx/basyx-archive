#include <BaSyx/submodel/map_v2/parts/ConceptDictionary.h>


namespace basyx {
namespace submodel {
namespace map {
using namespace basyx::submodel::api;

ConceptDictionary::ConceptDictionary(const std::string & idShort)
  : vab::ElementMap{}
  , Referable(idShort)
{
  this->map.insertKey("ConceptDescriptions", this->concept_descriptions.getMap());
}

const api::IElementContainer<api::IConceptDescription> & ConceptDictionary::getConceptDescriptions() const
{
  return this->concept_descriptions;
}

void ConceptDictionary::addConceptDescription(std::unique_ptr<ConceptDescription> description)
{
  this->concept_descriptions.addElement(std::move(description));
}


}
}
}
