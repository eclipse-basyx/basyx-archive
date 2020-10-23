#ifndef BASYX_SUBMODEL_SIMPLE_PARTS_VIEW_H
#define BASYX_SUBMODEL_SIMPLE_PARTS_VIEW_H

#include <BaSyx/submodel/api_v2/parts/IView.h>

#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace simple {

class View
  : public virtual api::IView
{
private:
	HasDataSpecification dataSpec;
	Referable referable;

  ElementContainer<api::IReferable> contained_elements;
  Reference semanticId;
public:
  View(const std::string & idShort, Referable * parent = nullptr);

  //Inherited via api::IView
  const api::IElementContainer<IReferable> & getContainedElements() const override;

  //not inherited
  void addContainedElement(std::unique_ptr<Referable> referable);

  // Inherited via IHasDataSpecification
  virtual void addDataSpecification(const simple::Reference & reference) override;
  virtual const std::vector<simple::Reference> getDataSpecificationReference() const override;

  //inherited via IHasSemantics
  const api::IReference & getSemanticId() const override;
  void setSemanticId(const api::IReference & reference) override;

  // Inherited via IReferable
  virtual const std::string & getIdShort() const override;
  virtual const std::string * const getCategory() const override;
  virtual void setCategory(const std::string & category) override;
  virtual simple::LangStringSet & getDescription() override;
  virtual const simple::LangStringSet & getDescription() const override;
  virtual void setParent(api::IReferable * parent) override;
  virtual IReferable * getParent() const override;
  virtual simple::Reference getReference() const override;

  virtual Key getKey(bool local = true) const override { return referable.getKey(); }
  virtual KeyElements getKeyElementType() const override { return KeyElements::View; };
};

}
}
}
#endif /* BASYX_SUBMODEL_SIMPLE_PARTS_VIEW_H */
