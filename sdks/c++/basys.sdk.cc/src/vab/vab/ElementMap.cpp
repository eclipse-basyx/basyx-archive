/*
 * ElementMap.cpp
 *
 *      Author: wendel
 */

#include "ElementMap.h"

namespace basyx {
namespace vab {

ElementMap::ElementMap()
  : map(basyx::object::make_map())
{}

ElementMap::ElementMap(basyx::object object)
  : map(basyx::object::make_null())
{
  if ( object.InstanceOf<basyx::object::object_map_t>() )
  {
    map = object;
  };
}

ElementMap::ElementMap(const ElementMap & other) :
  map{other.getMap()}
{}

basyx::object ElementMap::getMap() const
{
  return map;
};

void ElementMap::insertMapElement(const std::string & key, const ElementMap & element)
{
  this->map.insertKey(key, element.getMap(), true);
}

}
}
