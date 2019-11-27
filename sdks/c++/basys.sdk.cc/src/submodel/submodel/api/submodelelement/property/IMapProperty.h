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
namespace submodel {
namespace submodelelement {
namespace property {

class IMapProperty
{

public:
  virtual ~IMapProperty() = default;

  virtual basyx::object getValue(const std::string & key) const = 0;
  virtual void put(const std::string & key, const basyx::object & value) const = 0;
  virtual void set(const basyx::object::object_map_t & map) const = 0;
  virtual basyx::object::object_list_t getKeys() const = 0;
  virtual int getEntryCount() const = 0;
  virtual void remove(const std::string & key) const = 0;

};

}
}
}
}

#endif