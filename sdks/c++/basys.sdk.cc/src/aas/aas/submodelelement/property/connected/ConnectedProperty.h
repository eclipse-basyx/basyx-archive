/*
 * ConnectedProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_CONNECTEDPROPERTY_H_
#define AAS_BACKEND_CONNECTEDPROPERTY_H_

#include "aas/backend/connected/ConnectedDataElement.h"
#include "aas/submodelelement/property/IProperty.h"
#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

class ConnectedProperty : public IProperty, public backend::ConnectedDataElement
{

public:
  ConnectedProperty(PropertyType type, std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);

  // Inherited via IProperty
  virtual PropertyType getPropertyType() const override;

  virtual void setValue(const basyx::any & value) override;
  virtual basyx::any getValue() const override;

  virtual void setValueId(const basyx::any & valueId) override;
  virtual basyx::any getValueId() const override;

  // Inherited via IProperty : IElement
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

private:
  PropertyType type;

};

}
}
}
}

#endif