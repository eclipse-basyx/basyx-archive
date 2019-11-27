/*
 * ConnectedSingleProperty.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSingleProperty.h"
#include "submodel/api/submodelelement/property/IProperty.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

using namespace submodelelement::property;

ConnectedSingleProperty::ConnectedSingleProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedProperty(PropertyType::Single, proxy)
{}

basyx::object ConnectedSingleProperty::get() const
{
  return this->retrieveObject();
}

void ConnectedSingleProperty::set(const basyx::object & value)
{
  this->setProxyValue(PropertyPaths::VALUE, value);
}

std::string ConnectedSingleProperty::getValueType() const
{
  auto map = this->getProxy()->readElementValue("").Get<basyx::object::object_map_t>();
  return map.at(PropertyPaths::VALUETYPE).Get<std::string>();
}


}
}
}
}