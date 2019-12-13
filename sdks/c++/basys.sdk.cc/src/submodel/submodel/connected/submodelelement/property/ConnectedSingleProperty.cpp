/*
 * ConnectedSingleProperty.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/submodelelement/property/ConnectedSingleProperty.h>
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>

namespace basyx {
namespace submodel {

ConnectedSingleProperty::ConnectedSingleProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedProperty(PropertyType::Single, proxy)
{}

basyx::object ConnectedSingleProperty::get() const
{
  return this->retrieveObject();
}

void ConnectedSingleProperty::set(const basyx::object & value)
{
  this->setProxyValue(IProperty::Path::Value, value);
}

std::string ConnectedSingleProperty::getValueType() const
{
  auto map = this->getProxy()->readElementValue("").Get<basyx::object::object_map_t>();
  return map.at(IProperty::Path::ValueType).Get<std::string>();
}

void basyx::submodel::ConnectedSingleProperty::setValueId(const std::string & valueId)
{}

std::string basyx::submodel::ConnectedSingleProperty::getValueId() const
{
  return std::string();
}

IProperty::PropertyType basyx::submodel::ConnectedSingleProperty::getPropertyType() const
{
  return PropertyType();
}


}
}
