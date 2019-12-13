/*
 * ReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_REFERENCEELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_REFERENCEELEMENT_H_

#include <BaSyx/submodel/map/submodelelement/DataElement.h>
#include <BaSyx/submodel/api/submodelelement/IReferenceElement.h>

namespace basyx {
namespace submodel {

class ReferenceElement : public DataElement, public IReferenceElement
{
public:
  ~ReferenceElement() = default;

  // construtors
  ReferenceElement();
  ReferenceElement(const std::shared_ptr<IReference> & reference);

  // Inherited via IReferenceElement
  virtual void setValue(const std::shared_ptr<IReference> & ref) override;
  virtual std::shared_ptr<IReference> getValue() const override;

private:
  std::shared_ptr<IReference> reference;
};

}
}

#endif
