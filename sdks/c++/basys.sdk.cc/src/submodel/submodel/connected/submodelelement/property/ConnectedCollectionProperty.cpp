/*
 * ConnectedCollectionProperty.cpp
 *
 *      Author: wendel
 */

#include "ConnectedCollectionProperty.h"
#include "submodel/api/submodelelement/property/IProperty.h"

namespace basyx {
namespace submodel {

using namespace submodelelement::property;

ConnectedCollectionProperty::ConnectedCollectionProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedProperty(PropertyType::Collection, proxy)
{}

void ConnectedCollectionProperty::set(const basyx::object::object_list_t & collection) const
{
  this->getProxy()->updateElementValue(IProperty::Path::Value, collection);
}

void ConnectedCollectionProperty::add(const basyx::object & newValue)
{
  this->getProxy()->createElement(IProperty::Path::Value, newValue);
}

void ConnectedCollectionProperty::remove(basyx::object & objectRef)
{
  this->getProxy()->deleteElement(IProperty::Path::Value, objectRef);
}

basyx::object::object_list_t ConnectedCollectionProperty::getElements() const
{
  return this->retrieveObject().Get<basyx::object::object_list_t>();
}

int ConnectedCollectionProperty::getElementCount() const
{
  return this->retrieveObject().Get<basyx::object::object_list_t>().size();
}

}
}
