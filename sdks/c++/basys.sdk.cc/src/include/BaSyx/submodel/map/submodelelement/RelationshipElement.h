/*
 * RelationshipElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_RELATIONSHIPELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_RELATIONSHIPELEMENT_H_

#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/api/submodelelement/IRelationshipElement.h>
#include <BaSyx/submodel/api/reference/IReference.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {

class RelationshipElement : public SubmodelElement, public submodel::submodelelement::IRelationshipElement
{
public:
  ~RelationshipElement() = default;

  //constructors
  RelationshipElement();
  RelationshipElement(const std::shared_ptr<IReference> & first, const std::shared_ptr<IReference> & second);

  // Inherited via IRelationshipElement
  virtual void setFirst(const std::shared_ptr<IReference>& first) override;
  virtual std::shared_ptr<IReference> getFirst() const override;
  virtual void setSecond(const std::shared_ptr<IReference>& second) override;
  virtual std::shared_ptr<IReference> getSecond() const override;

private:
  std::shared_ptr<IReference> first, second;
};

}
}


#endif
