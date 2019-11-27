/*
 * View.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_VIEW_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_VIEW_H_

#include "aas/aas/parts/IView.h"

namespace basyx {
namespace aas {

class View : public IView
{
public:
  ~View() = default;

  // Inherited via IView
  virtual std::shared_ptr<submodel::IReference> getSemanticId() const override;
  virtual basyx::specificCollection_t<submodel::IReference> getDataSpecificationReferences() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual submodel::Description getDescription() const override;
  virtual std::shared_ptr<submodel::IReference> getParent() const override;
  virtual void setContainedElement(std::vector<submodel::IReference> references) override;
  virtual std::vector<submodel::IReference> getContainedElement() const override;
};

}
}

#endif
