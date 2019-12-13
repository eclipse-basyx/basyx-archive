/*
 * ConnectedReferenceElement.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDREFERENCEELEMENT_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDREFERENCEELEMENT_H_

#include <BaSyx/submodel/connected/submodelelement/ConnectedDataElement.h>
#include <BaSyx/submodel/api/submodelelement/IReferenceElement.h>

namespace basyx {
namespace submodel {

class ConnectedReferenceElement : public ConnectedDataElement, public IReferenceElement
{
public:
  ConnectedReferenceElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedReferenceElement() = default;

  virtual void setValue(const std::shared_ptr<IReference> & ref) override;
  virtual std::shared_ptr<IReference> getValue() const override;
};

}
}

#endif
