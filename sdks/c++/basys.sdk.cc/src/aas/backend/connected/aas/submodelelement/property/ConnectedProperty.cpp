/*
 * ConnectedProperty.cpp
 *
 *      Author: wendel
 */
#include "ConnectedProperty.h"
#include "basyx/anyTypeChecker.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

using namespace submodelelement::property; 

ConnectedProperty::ConnectedProperty(submodelelement::property::PropertyType type, std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  backend::ConnectedDataElement(proxy),
  type(type)
{}

PropertyType ConnectedProperty::getPropertyType() const
{
  return this->type;
}

void ConnectedProperty::setValue(const basyx::object & value)
{
  this->getProxy()->updateElementValue(PropertyPaths::VALUE, value);
  this->getProxy()->updateElementValue(PropertyPaths::VALUETYPE, basyx::getPropertyTypeInfo(value));
}

basyx::object ConnectedProperty::getValue() const
{
  return this->getProxy()->readElementValue(PropertyPaths::VALUE);
}

void ConnectedProperty::setValueId(const basyx::object & valueId)
{
  this->getProxy()->updateElementValue(PropertyPaths::VALUEID, valueId);
}

basyx::object ConnectedProperty::getValueId() const
{
  return this->getProxy()->readElementValue(PropertyPaths::VALUEID);
}

void ConnectedProperty::setId(const std::string & id)
{
  this->setId(id);
}

std::string ConnectedProperty::getId() const
{
  return this->getId();
}

basyx::object ConnectedProperty::retrieveObject() const
{
  return this->getProxy()->readElementValue(PropertyPaths::VALUE).Get<basyx::object::object_map_t>().at(PropertyPaths::VALUE);
}

}
}
}
}
