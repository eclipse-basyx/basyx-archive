/*
 * ConnectedSubmodelElementCollection.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElementCollection.h"
#include "aas/submodelelement/property/IProperty.h"
#include "aas/ISubModel.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

using namespace basyx::aas::submodelelement;

ConnectedSubmodelElementCollection::ConnectedSubmodelElementCollection(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy):
  ConnectedSubmodelElement(proxy)
{}

void ConnectedSubmodelElementCollection::setValue(const basyx::objectCollection_t & value)
{
  this->setProxyValue(property::PropertyPaths::VALUE, value);
}

basyx::objectCollection_t ConnectedSubmodelElementCollection::getValue() const
{
  return this->getProxyCollection(property::PropertyPaths::VALUE);
}

void ConnectedSubmodelElementCollection::setOrdered(const bool & value)
{
  this->setProxyValue(SubmodelElementCollectionPaths::ORDERED, value);
}

bool ConnectedSubmodelElementCollection::isOrdered() const
{
  auto element = this->getProxy()->readElementValue(SubmodelElementCollectionPaths::ORDERED);
  return element.Get<bool>();
}

void ConnectedSubmodelElementCollection::setAllowDuplicates(const bool & value)
{
  this->setProxyValue(SubmodelElementCollectionPaths::ALLOWDUPLICATES, value);
}

bool ConnectedSubmodelElementCollection::isAllowDuplicates() const
{
  auto element = this->getProxy()->readElementValue(SubmodelElementCollectionPaths::ALLOWDUPLICATES);
  return element.Get<bool>();
}

void ConnectedSubmodelElementCollection::setElements(const basyx::objectMap_t & elements)
{
  this->setProxyValue(SubmodelPaths::SUBMODELELEMENT, elements);
}

basyx::objectMap_t ConnectedSubmodelElementCollection::getElements() const
{
  auto elements = this->getProxy()->readElementValue(SubmodelPaths::SUBMODELELEMENT);
  return elements.Get<basyx::objectMap_t>();
}


}
}
}
}