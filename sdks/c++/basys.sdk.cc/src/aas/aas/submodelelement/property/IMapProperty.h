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

class IMapProperty
{

public:
  virtual ~IMapProperty() = default;

  virtual basyx::any getValue(const std::string & key) const = 0;
  virtual void put(const std::string & key, const basyx::any & value) const = 0;
  virtual void set(const basyx::objectMap_t & map) const = 0;
  virtual basyx::objectCollection_t getKeys() const = 0;
  virtual int getEntryCount() const = 0;
  virtual void remove(const std::string & key) const = 0;

};

}
}
}
}

#endif