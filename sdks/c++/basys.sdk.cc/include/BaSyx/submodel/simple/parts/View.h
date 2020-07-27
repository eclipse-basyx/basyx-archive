#ifndef BASYX_SIMPLE_SDK_VIEW_H
#define BASYX_SIMPLE_SDK_VIEW_H

#include <BaSyx/submodel/api_v2/parts/IView.h>

#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace simple {

class View
  : public virtual api::IView
  , public virtual HasDataSpecification
  , public virtual Referable
{
private:
  ElementContainer<api::IReferable> contained_elements;
  Reference semanticId;
public:
  View(const std::string & idShort, const Referable * parent = nullptr);

  //Inherited via api::IView
  const api::IElementContainer<IReferable> & getContainedElements() const override;

  //not inherited
  void addContainedElement(std::unique_ptr<Referable> referable);

  //inherited via IHasSemantics
  const api::IReference & getSemanticId() const override;
  void setSemanticId(const api::IReference & reference) override;
};

}
}
}
#endif //BASYX_SIMPLE_SDK_VIEW_H
