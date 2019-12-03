/*
 * ConnectedProperty.cpp
 *
 *      Author: wendel
 */
#include "ConnectedProperty.h"
#include "basyx/anyTypeChecker.h"

namespace basyx {
namespace submodel {


ConnectedProperty::ConnectedProperty(PropertyType type, std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedDataElement(proxy),
  type(type)
{}

IProperty::PropertyType ConnectedProperty::getPropertyType() const
{
  return this->type;
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
  return this->getProxy()->readElementValue(IProperty::Path::Value).Get<basyx::object::object_map_t>().at(IProperty::Path::Value);
}

void ConnectedProperty::setValueId(const std::string & valueId)
{}

std::string ConnectedProperty::getValueId() const
{
  return std::string();
}

}
}