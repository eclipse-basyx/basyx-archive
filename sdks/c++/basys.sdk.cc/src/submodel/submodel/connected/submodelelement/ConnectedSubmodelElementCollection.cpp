/*
 * ConnectedSubmodelElementCollection.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/submodelelement/ConnectedSubmodelElementCollection.h>
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/api/ISubModel.h>

namespace basyx {
namespace submodel {


ConnectedSubmodelElementCollection::ConnectedSubmodelElementCollection(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy):
  ConnectedSubmodelElement(proxy)
{}

void ConnectedSubmodelElementCollection::setValue(const basyx::specificCollection_t<ISubmodelElement> & value)
{
  //todo
  //this->setProxyValue(property::PropertyPaths::VALUE, value);
}

basyx::specificCollection_t<ISubmodelElement> ConnectedSubmodelElementCollection::getValue() const
{
  //todo 
  return basyx::specificCollection_t<ISubmodelElement>(); // this->getProxyCollection(property::PropertyPaths::VALUE);
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

void ConnectedSubmodelElementCollection::setElements(const basyx::specificMap_t<ISubmodelElement> & elements)
{
  //todo 
  //this->setProxyValue(SubmodelPaths::SUBMODELELEMENT, elements);
}

basyx::specificMap_t<ISubmodelElement> ConnectedSubmodelElementCollection::getElements() const
{
  //todo 
  
  //auto elements = this->getProxy()->readElementValue(SubmodelPaths::SUBMODELELEMENT);
  return basyx::specificMap_t<ISubmodelElement>();
}

}
}
