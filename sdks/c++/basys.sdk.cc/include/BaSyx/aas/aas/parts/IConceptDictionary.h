/*
 * IConceptDictionary.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IConceptDictionary_H_
#define BASYX_METAMODEL_IConceptDictionary_H_


#include <BaSyx/submodel/api/qualifier/IReferable.h>

#include <string>
#include <vector>

namespace basyx {
namespace aas {


class IConceptDictionary : public submodel::IReferable
{
public:
  struct Path {
    static constexpr char ConceptDescription[] = "conceptDescription";
    static constexpr char ConceptDescriptions[] = "conceptDescriptions";
  };

  virtual ~IConceptDictionary() = default;

  virtual std::vector<std::string> getConceptDescription() const = 0;
  virtual void setConceptDescription(const std::vector<std::string> & ref) = 0;
};

}
}

#endif

