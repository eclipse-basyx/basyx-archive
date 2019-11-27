/*
 * ReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_REFERENCEELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_REFERENCEELEMENT_H_

#include "DataElement.h"
#include "submodel/api/submodelelement/IReferenceElement.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

class ReferenceElement : public DataElement, public aas::submodelelement::IReferenceElement
{
public:
  ~ReferenceElement() = default;

  // construtors
  ReferenceElement();
  ReferenceElement(const std::shared_ptr<aas::reference::IReference> & reference);

  // Inherited via IReferenceElement
  virtual void setValue(const std::shared_ptr<aas::reference::IReference> & ref) override;
  virtual std::shared_ptr<aas::reference::IReference> getValue() const override;

private:
  std::shared_ptr<aas::reference::IReference> reference;
};

}
}
}
}
}

#endif
