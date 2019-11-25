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
#include "basyx/object.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

class ConnectedProperty : public backend::ConnectedDataElement, public submodelelement::property::IProperty
{

public:
  ConnectedProperty(submodelelement::property::PropertyType type, std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);

  // Inherited via IProperty
  virtual submodelelement::property::PropertyType getPropertyType() const override;

  virtual void setValue(const basyx::object & value) override;
  virtual basyx::object getValue() const override;

  virtual void setValueId(const basyx::object & valueId) override;
  virtual basyx::object getValueId() const override;

  //Inherited via IProperty : IElement
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

protected:
  basyx::object retrieveObject() const;

private:
  submodelelement::property::PropertyType type;

  

};

}
}
}
}

#endif