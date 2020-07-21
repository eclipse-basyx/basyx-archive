#ifndef BASYX_SIMPLE_SDK_CONCEPTDICTIONARY_H
#define BASYX_SIMPLE_SDK_CONCEPTDICTIONARY_H

#include <BaSyx/submodel/api_v2/parts/IConceptDictionary.h>

#include <BaSyx/vab/ElementMap.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>
#include <BaSyx/submodel/simple/parts/ConceptDescription.h>

namespace basyx {
namespace submodel {
namespace simple {

class ConceptDictionary
    : public api::IConceptDictionary
    , public Referable
{
private:
  ElementContainer<api::IConceptDescription> conecpt_descriptions;

public:
  ConceptDictionary(const std::string & idShort);

  //inherited via IConceptDictionary
  const api::IElementContainer<api::IConceptDescription> & getConceptDescriptions() const override;

  //not inherited
  void addConceptDescription(std::unique_ptr<ConceptDescription> description);
};

}
}
}
#endif //BASYX_SIMPLE_SDK_CONCEPTDICTIONARY_H
