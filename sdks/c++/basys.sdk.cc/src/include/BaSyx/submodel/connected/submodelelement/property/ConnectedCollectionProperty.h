/*
 * ConnectedCollectionProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDCOLLECTIONPROPERTY_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDCOLLECTIONPROPERTY_H_

#include <BaSyx/submodel/api/submodelelement/property/ICollectionProperty.h>
#include <BaSyx/submodel/connected/submodelelement/property/ConnectedProperty.h>

namespace basyx { 
namespace submodel {

class ConnectedCollectionProperty : public ConnectedProperty, submodelelement::property::ICollectionProperty
{
public:
  ConnectedCollectionProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedCollectionProperty() = default;

  virtual void set(const basyx::object::object_list_t & collection) const override;
  virtual void add(const basyx::object & newValue) override;
  virtual void remove(basyx::object & objectRef) override;
  virtual basyx::object::object_list_t getElements() const override;
  virtual int getElementCount() const override;

};
 
}
}

#endif
