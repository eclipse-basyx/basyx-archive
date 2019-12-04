/*
 * ConnectedMapProperty.cpp
 *
 *      Author: wendel
 */

#include "ConnectedMapProperty.h"
#include "vab/core/util/VABPath.h"

namespace basyx {
namespace submodel {

using namespace submodelelement::property;

ConnectedMapProperty::ConnectedMapProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedProperty(PropertyType::Map, proxy)
{}

basyx::object ConnectedMapProperty::getValue(const std::string & key) const
{
  return this->getMap().at(key);
}

void ConnectedMapProperty::put(const std::string & key, const basyx::object & value) const
{
  basyx::vab::core::VABPath path(IProperty::Path::Value);
  path.append(key);
  this->setProxyValue(path, value);
}

void ConnectedMapProperty::set( const basyx::object::object_map_t & map ) const
{
  this->setProxyValue(IProperty::Path::Value, map);
}

basyx::object::object_list_t ConnectedMapProperty::getKeys() const
{
  auto map = this->getMap();
  basyx::object::object_list_t keys;
  keys.reserve(map.size());

  for ( auto entry : map ) {
    keys.push_back(entry.first);
  }

  return keys;
}

int ConnectedMapProperty::getEntryCount() const 
{
  return this->getMap().size();
}

void ConnectedMapProperty::remove(const std::string & key) const
{
  basyx::vab::core::VABPath path(IProperty::Path::Value);
  path.append(key);
  this->getProxy()->deleteElement(path);
}

basyx::object::object_map_t ConnectedMapProperty::getMap() const
{
  auto map = this->getProxy()->readElementValue(IProperty::Path::Value).Get<basyx::object::object_map_t>();
  return map;
}

}
}
