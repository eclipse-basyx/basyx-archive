/*
 * ConnectedReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDREFERENCEELEMENT_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDREFERENCEELEMENT_H_

#include "backend/connected/aas/submodelelement/ConnectedDataElement.h"
#include "aas/submodelelement/IReferenceElement.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

class ConnectedReferenceElement : ConnectedDataElement, submodelelement::property::IReferenceElement
{
public:
  ConnectedReferenceElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedReferenceElement() = default;

  virtual void setValue(const basyx::any & ref) override;
  virtual basyx::any getValue() const override;
};

}
}
}
}

#endif
