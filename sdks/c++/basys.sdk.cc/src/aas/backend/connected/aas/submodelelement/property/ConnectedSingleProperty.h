/*
 * ConnectedSingleProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDSINGLEPROPERTY_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDSINGLEPROPERTY_H_

#include "ConnectedProperty.h"
#include "aas/submodelelement/property/ISingleProperty.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected { 

class ConnectedSingleProperty : public ConnectedProperty, submodelelement::property::ISingleProperty
{
public:
  ConnectedSingleProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedSingleProperty() = default;

  virtual basyx::any get() const override;
  virtual void set(const basyx::any & value) override;
  virtual std::string getValueType() const override;

};

}
}
}
}

#endif
