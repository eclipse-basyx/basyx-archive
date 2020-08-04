#include <BaSyx/submodel/simple/parts/ConceptDictionary.h>


namespace basyx {
namespace submodel {
namespace simple {
using namespace basyx::submodel::api;

ConceptDictionary::ConceptDictionary(const std::string & idShort)
  : conecpt_descriptions{}
  , Referable(idShort)
{}

const api::IElementContainer<api::IConceptDescription> & ConceptDictionary::getConceptDescriptions() const
{
  return this->conecpt_descriptions;
}

void ConceptDictionary::addConceptDescription(std::unique_ptr<ConceptDescription> description)
{
  this->conecpt_descriptions.addElement(std::move(description));
}


}
}
}
