/*
 * ConnectedCollectionProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDCOLLECTIONPROPERTY_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDCOLLECTIONPROPERTY_H_

#include "aas/submodelelement/property/ICollectionProperty.h"
#include "ConnectedProperty.h"

namespace basyx { 
namespace aas {
namespace backend {
namespace connected { 

class ConnectedCollectionProperty : public ConnectedProperty, submodelelement::property::ICollectionProperty
{
public:
  ConnectedCollectionProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedCollectionProperty() = default;

  virtual void set(const basyx::objectCollection_t & collection) const override;
  virtual void add(const basyx::any & newValue) override;
  virtual void remove(const basyx::any & objectRef) override;
  virtual basyx::objectCollection_t getElements() const override;
  virtual int getElementCount() const override;

};
 
}
}
}
}

#endif
