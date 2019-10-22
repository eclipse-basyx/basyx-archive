/*
 * ConnectedCollectionProperty.cpp
 *
 *      Author: wendel
 */

#include "ConnectedCollectionProperty.h"
#include "aas/submodelelement/property/IProperty.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

using namespace submodelelement::property;

ConnectedCollectionProperty::ConnectedCollectionProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedProperty(PropertyType::Collection, proxy)
{}

void ConnectedCollectionProperty::set(const basyx::objectCollection_t & collection) const
{
  this->getProxy()->updateElementValue(PropertyPaths::VALUE, collection);
}

void ConnectedCollectionProperty::add(const basyx::any & newValue)
{
  this->getProxy()->createElement(PropertyPaths::VALUE, newValue);
}

void ConnectedCollectionProperty::remove(const basyx::any & objectRef)
{
  this->getProxy()->deleteElement(PropertyPaths::VALUE, objectRef);
}

basyx::objectCollection_t ConnectedCollectionProperty::getElements() const
{
  return this->retrieveObject().Get<basyx::objectCollection_t>();
}

int ConnectedCollectionProperty::getElementCount() const
{
  return this->retrieveObject().Get<basyx::objectCollection_t>().size();
}

}
}
}
}