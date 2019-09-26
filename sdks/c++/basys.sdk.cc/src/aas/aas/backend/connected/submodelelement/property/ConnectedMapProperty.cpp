/*
 * ConnectedMapProperty.cpp
 *
 *      Author: wendel
 */

#include "ConnectedMapProperty.h"
#include "vab/core/util/VABPath.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

using namespace submodelelement::property;

ConnectedMapProperty::ConnectedMapProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedProperty(PropertyType::Map, proxy)
{}

basyx::any ConnectedMapProperty::getValue(std::string key)
{
  return this->getMap().at(key);
}

void ConnectedMapProperty::put(std::string key, basyx::any value)
{
  basyx::vab::core::VABPath path(PropertyPaths::VALUE);
  path.append(key);
  this->setProxyValue(path, value);
}

void ConnectedMapProperty::set(basyx::objectMap_t map)
{
  this->setProxyValue(PropertyPaths::VALUE, map);
}

basyx::objectCollection_t ConnectedMapProperty::getKeys()
{
  auto map = this->getMap();
  basyx::objectCollection_t keys;
  keys.reserve(map.size());

  for ( auto entry : map ) {
    keys.push_back(entry.first);
  }

  return keys;
}

int ConnectedMapProperty::getEntryCount()
{
  return this->getMap().size();
}

void ConnectedMapProperty::remove(std::string key)
{
  basyx::vab::core::VABPath path(PropertyPaths::VALUE);
  path.append(key);
  this->getProxy()->deleteElement(path);
}

basyx::objectMap_t ConnectedMapProperty::getMap()
{
  auto map = this->getProxy()->readElementValue(PropertyPaths::VALUE).Get<basyx::objectMap_t>();
  return map;
}

}
}
}
}
