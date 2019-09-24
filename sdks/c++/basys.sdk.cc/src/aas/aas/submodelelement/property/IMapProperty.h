/*
 * IMapProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_PROPERTY_IMAPPROPERTY_
#define AAS_SUBMODELELEMENT_PROPERTY_IMAPPROPERTY_


#include "IProperty.h"
#include "basyx/types.h"


namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

class IMapProperty : IProperty
{

public:
  virtual ~IMapProperty() = default;

  virtual basyx::any getValue(std::string key) = 0;
  virtual void put(std::string key, basyx::any value) = 0;
  virtual void set(basyx::objectMap_t map) = 0;
  virtual basyx::objectCollection_t getKeys() = 0;
  virtual int getEntryCount() = 0;
  virtual void remove(std::string key) = 0;

};

}
}
}
}

#endif