/*
 * ConceptDictionary.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDICTIONARY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDICTIONARY_H_

#include "aas/aas/parts/IConceptDictionary.h"
#include "aas/aas/parts/IConceptDescription.h"
#include "submodel/map/qualifier/Referable.h"

namespace basyx {
namespace aas {

class ConceptDictionary : 
  public virtual IConceptDictionary,
  public submodel::Referable,
  public virtual basyx::vab::ElementMap
{
public:
  ~ConceptDictionary() = default;

  // Inherited via IConceptDictionary
  virtual std::vector<std::string> getConceptDescription() const override;
  virtual void setConceptDescription(const std::vector<std::string>& ref) override;
};

}
}

#endif
