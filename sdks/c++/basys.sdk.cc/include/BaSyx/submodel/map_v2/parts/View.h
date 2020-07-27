#ifndef BASYX_MAP_V2_SDK_VIEW_H
#define BASYX_MAP_V2_SDK_VIEW_H

#include <BaSyx/submodel/api_v2/parts/IView.h>

#include <BaSyx/vab/ElementMap.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

namespace basyx {
namespace submodel {
namespace map {

class View
  : public api::IView
  , public virtual HasDataSpecification
  , public virtual Referable
  , public virtual vab::ElementMap
{
private:
  ElementContainer<IReferable> contained_elements;
  Reference semanticId;
public:
  View(const std::string & idShort, const Referable * parent = nullptr);

  //inherited via api::IView
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
#endif //BASYX_MAP_V2_SDK_VIEW_H
