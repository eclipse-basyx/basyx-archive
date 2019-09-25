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
  virtual basyx::any getValue(std::string key) override;
  virtual void put(std::string key, basyx::any value) override;
  virtual void set(basyx::objectMap_t map) override;
  virtual basyx::objectCollection_t getKeys() override;
  virtual int getEntryCount() override;
  virtual void remove(std::string key) override;

private:
  basyx::objectMap_t getMap();
};
 
}
}
}
}

#endif
