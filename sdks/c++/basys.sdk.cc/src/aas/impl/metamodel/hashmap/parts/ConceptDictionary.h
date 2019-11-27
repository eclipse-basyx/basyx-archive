/*
 * ConceptDictionary.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDICTIONARY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDICTIONARY_H_

#include "aas/aas/parts/IConceptDictionary.h"
#include "aas/aas/parts/IConceptDescription.h"

namespace basyx {
namespace aas {

class ConceptDictionary : public IConceptDictionary
{
public:
  ~ConceptDictionary() = default;

  // Inherited via IConceptDictionary
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual submodel::Description getDescription() const override;
  virtual std::shared_ptr<submodel::IReference> getParent() const override;
  virtual std::vector<std::string> getConceptDescription() const override;
  virtual void setConceptDescription(const std::vector<std::string>& ref) override;
  void addConceptDescription(const std::shared_ptr<IConceptDescription> & description);
};

}
}

#endif