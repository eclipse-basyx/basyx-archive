/*
 * IConceptDictionary.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IConceptDictionary_H_
#define BASYX_METAMODEL_IConceptDictionary_H_


#include "submodel/api/qualifier/IReferable.h"

#include <string>
#include <vector>

namespace basyx {
namespace aas {

class IConceptDictionary 
  : public submodel::IReferable
{
public:
  struct Path {
    static constexpr char ConceptDescription[] = "conceptDescription";
    static constexpr char ConceptDescriptions[] = "conceptDescriptions";
  };

  virtual ~IConceptDictionary() = default;

  virtual basyx::specificCollection_t<submodel::IReference> getConceptDescription() const = 0;
  virtual void setConceptDescription(const basyx::specificCollection_t<submodel::IReference> & references) = 0;
};

}
}

#endif

