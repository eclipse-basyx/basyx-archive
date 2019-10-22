/*
 * ConnectedSingleProperty.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSingleProperty.h"
#include "aas/submodelelement/property/IProperty.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

using namespace submodelelement::property;

ConnectedSingleProperty::ConnectedSingleProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedProperty(PropertyType::Single, proxy)
{}

basyx::any ConnectedSingleProperty::get() const
{
  return this->retrieveObject();
}

void ConnectedSingleProperty::set(const basyx::any & value)
{
  this->setProxyValue(PropertyPaths::VALUE, value);
}

std::string ConnectedSingleProperty::getValueType() const
{
  auto map = this->getProxy()->readElementValue("").Get<basyx::objectMap_t>();
  return map.at(PropertyPaths::VALUETYPE).Get<std::string>();
}


}
}
}
}