/*
 * ConnectedSingleProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDSINGLEPROPERTY_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDSINGLEPROPERTY_H_

#include <BaSyx/submodel/connected/submodelelement/property/ConnectedProperty.h>
#include <BaSyx/submodel/api/submodelelement/property/ISingleProperty.h>

namespace basyx {
namespace submodel {

class ConnectedSingleProperty : public ConnectedProperty, public ISingleProperty
{
public:
  ConnectedSingleProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedSingleProperty() = default;

  virtual basyx::object get() const override;
  virtual void set(const basyx::object & value) override;
  virtual std::string getValueType() const override;


  // Inherited via ConnectedProperty
  virtual void setValueId(const std::string & valueId) override;
  virtual std::string getValueId() const override;
  virtual PropertyType getPropertyType() const override;

};

}
}

#endif
