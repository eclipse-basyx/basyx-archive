/*
 * ConnectedProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_CONNECTEDPROPERTY_H_
#define AAS_BACKEND_CONNECTEDPROPERTY_H_

#include "submodel/api/submodelelement/property/IProperty.h"
#include "submodel/connected/submodelelement/ConnectedDataElement.h"
#include "basyx/types.h"
#include "basyx/object.h"

namespace basyx {
namespace submodel {

class ConnectedProperty : public ConnectedDataElement, public IProperty
{

public:
  ConnectedProperty(PropertyType type, std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);

  // Inherited via IProperty
  virtual PropertyType getPropertyType() const override;

  //Inherited via IProperty : IElement
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

protected:
  basyx::object retrieveObject() const;

private:
  PropertyType type;

  // Inherited via IProperty
  virtual void setValueId(const std::string & valueId) override;
  virtual std::string getValueId() const override;
};

}
}

#endif