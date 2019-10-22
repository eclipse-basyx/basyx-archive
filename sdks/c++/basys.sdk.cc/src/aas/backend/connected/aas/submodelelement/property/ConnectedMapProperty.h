/*
 * ConnectedMapProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDMAPPROPERTY_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDMAPPROPERTY_H_

#include "ConnectedProperty.h"
#include "aas/submodelelement/property/IMapProperty.h"

namespace basyx { 
namespace aas {
namespace backend {
namespace connected { 

class ConnectedMapProperty : public submodelelement::property::IMapProperty, ConnectedProperty
{
public:
  ConnectedMapProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedMapProperty() = default;

  // Inherited via IMapProperty
  virtual basyx::any getValue(const std::string & key) const override;
  virtual void put(const std::string & key, const basyx::any & value) const override;
  virtual void set(const basyx::objectMap_t & map) const override;
  virtual basyx::objectCollection_t getKeys() const override;
  virtual int getEntryCount() const override;
  virtual void remove(const std::string & key) const override;

private:
  basyx::objectMap_t getMap() const;
};
 
}
}
}
}

#endif
