/*
 * View.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_VIEW_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_VIEW_H_

#include "aas/parts/IView.h"

namespace basyx {
namespace aas {
namespace parts {

class View : public IView
{
public:
  ~View() = default;

  // Inherited via IView
  virtual std::shared_ptr<reference::IReference> getSemanticId() const override;
  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual qualifier::impl::Description getDescription() const override;
  virtual std::shared_ptr<reference::IReference> getParent() const override;
  virtual void setContainedElement(std::vector<reference::IReference> references) override;
  virtual std::vector<reference::IReference> getContainedElement() const override;
};

}
}
}

#endif
