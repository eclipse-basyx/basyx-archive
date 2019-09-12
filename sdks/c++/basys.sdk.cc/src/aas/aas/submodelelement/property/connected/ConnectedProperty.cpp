/*
 * ConnectedProperty.cpp
 *
 *      Author: wendel
 */
#include "ConnectedProperty.h"
#include "basyx/anyTypeChecker.h"

namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

ConnectedProperty::ConnectedProperty(PropertyType type, std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  backend::ConnectedDataElement(std::move(proxy)),
  type(type)
{}

PropertyType ConnectedProperty::getPropertyType() const
{
  return this->type;
}

void ConnectedProperty::setValue(const basyx::any & value)
{
  this->getProxy()->updateElementValue(PropertyPaths::VALUE, value);
  this->getProxy()->updateElementValue(PropertyPaths::VALUETYPE, basyx::getPropertyTypeInfo(value));
}

basyx::any ConnectedProperty::getValue() const
{
  return this->getProxy()->readElementValue(PropertyPaths::VALUE);
}

void ConnectedProperty::setValueId(const basyx::any & valueId)
{
  this->getProxy()->updateElementValue(PropertyPaths::VALUEID, valueId);
}

basyx::any ConnectedProperty::getValueId() const
{
  return this->getProxy()->readElementValue(PropertyPaths::VALUEID);
}

void ConnectedProperty::setId(const std::string & id)
{
  ConnectedDataElement::setId(id);
}

std::string ConnectedProperty::getId() const
{
  return ConnectedDataElement::getId();
}

}
}
}
}
