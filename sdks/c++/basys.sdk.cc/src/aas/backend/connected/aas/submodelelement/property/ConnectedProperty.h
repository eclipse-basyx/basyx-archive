/*
 * ConnectedProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_CONNECTEDPROPERTY_H_
#define AAS_BACKEND_CONNECTEDPROPERTY_H_

#include "aas/submodelelement/property/IProperty.h"
#include "backend/connected/aas/submodelelement/ConnectedDataElement.h"
#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

class ConnectedProperty : public submodelelement::property::IProperty, public backend::ConnectedDataElement
{

public:
  ConnectedProperty(submodelelement::property::PropertyType type, std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);

  // Inherited via IProperty
  virtual submodelelement::property::PropertyType getPropertyType() const override;

  virtual void setValue(const basyx::any & value) override;
  virtual basyx::any getValue() const override;

  virtual void setValueId(const basyx::any & valueId) override;
  virtual basyx::any getValueId() const override;

  //Inherited via IProperty : IElement
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

protected:
  basyx::any retrieveObject() const;

private:
  submodelelement::property::PropertyType type;

  

};

}
}
}
}

#endif