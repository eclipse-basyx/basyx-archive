/*
 * ConnectedSubmodelElementCollection.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElementCollection.h"
#include "submodel/api/submodelelement/property/IProperty.h"
#include "submodel/api/ISubModel.h"

namespace basyx {
namespace submodel {
namespace backend {
namespace connected {

using namespace basyx::aas::submodelelement;

ConnectedSubmodelElementCollection::ConnectedSubmodelElementCollection(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy):
  ConnectedSubmodelElement(proxy)
{}

void ConnectedSubmodelElementCollection::setValue(const basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> & value)
{
  //todo
  //this->setProxyValue(property::PropertyPaths::VALUE, value);
}

basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> ConnectedSubmodelElementCollection::getValue() const
{
  //todo 
  return basyx::specificCollection_t<aas::submodelelement::ISubmodelElement>(); // this->getProxyCollection(property::PropertyPaths::VALUE);
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

void ConnectedSubmodelElementCollection::setElements(const basyx::specificMap_t<aas::submodelelement::ISubmodelElement> & elements)
{
  //todo 
  //this->setProxyValue(SubmodelPaths::SUBMODELELEMENT, elements);
}

basyx::specificMap_t<aas::submodelelement::ISubmodelElement> ConnectedSubmodelElementCollection::getElements() const
{
  //todo 
  
  //auto elements = this->getProxy()->readElementValue(SubmodelPaths::SUBMODELELEMENT);
  return basyx::specificMap_t<aas::submodelelement::ISubmodelElement>();
}


}
}
}
}