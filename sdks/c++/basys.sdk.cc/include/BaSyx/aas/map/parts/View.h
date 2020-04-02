/*
 * View.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_VIEW_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_VIEW_H_

#include <BaSyx/aas/api/parts/IView.h>

#include <BaSyx/submodel/map/modeltype/ModelType.h>
#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map/qualifier/Referable.h>
#include <BaSyx/submodel/map/qualifier/HasSemantics.h>

namespace basyx {
namespace aas {

class View 
  : public IView
  , public virtual vab::ElementMap
  , public virtual submodel::ModelType
  , public virtual submodel::HasDataSpecification
  , public virtual submodel::HasSemantics
{
public:
  ~View() = default;

  View(basyx::object obj);
  View();
  View(const basyx::specificCollection_t<submodel::IReference> & references);

  // Inherited via IView
  virtual void setContainedElements(const basyx::specificCollection_t<submodel::IReference> & references) override;
  virtual basyx::specificCollection_t<submodel::IReference> getContainedElements() const override;
};

}
}

#endif
