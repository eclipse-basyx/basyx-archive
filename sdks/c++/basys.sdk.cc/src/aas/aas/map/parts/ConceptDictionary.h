/*
 * ConceptDictionary.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDICTIONARY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDICTIONARY_H_

#include "aas/api/parts/IConceptDictionary.h"
#include "aas/api/parts/IConceptDescription.h"
#include "submodel/map/qualifier/Referable.h"

namespace basyx {
namespace aas {

class ConceptDictionary 
  : public virtual IConceptDictionary
  , public virtual submodel::Referable
  , public virtual basyx::vab::ElementMap
{
public:
  ~ConceptDictionary() = default;

  ConceptDictionary(basyx::object obj);
  ConceptDictionary(basyx::specificCollection_t<submodel::IReference> concept_descriptions);


  // Inherited via IConceptDictionary
  virtual basyx::specificCollection_t<submodel::IReference> getConceptDescription() const override;
  virtual void setConceptDescription(const basyx::specificCollection_t<submodel::IReference> & references) override;

};

}
}

#endif
