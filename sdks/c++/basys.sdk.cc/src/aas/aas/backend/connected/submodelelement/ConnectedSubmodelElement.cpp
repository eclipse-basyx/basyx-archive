/*
 * ConnectedSubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElement.h"
#include "aas/backend/connected/ConnectedElement.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

ConnectedSubmodelElement::ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  backend::ConnectedElement(proxy)
{}

void ConnectedSubmodelElement::setId(const std::string & id)
{}

std::string ConnectedSubmodelElement::getId() const
{
  return std::string();
}

basyx::objectCollection_t ConnectedSubmodelElement::getDataSpecificationReferences() const
{
  auto collection = this->getProxy()->readElementValue(qualifier::HasDataSpecificationPaths::HASDATASPECIFICATION);
  return collection.Get<basyx::objectCollection_t>();
}

void ConnectedSubmodelElement::setDataSpecificationReferences(const basyx::objectCollection_t & references)
{
  //Check if all subclasses do the same
}

std::string ConnectedSubmodelElement::getIdShort() const
{
  return std::string();
}

void ConnectedSubmodelElement::setIdShort(const std::string & idShort)
{}

std::string ConnectedSubmodelElement::getCategory() const
{
  return std::string();
}

void ConnectedSubmodelElement::setCategory(const std::string & category)
{}

std::string ConnectedSubmodelElement::getDescription() const
{
  return std::string();
}

void ConnectedSubmodelElement::setDescription(const std::string & description)
{}

basyx::any ConnectedSubmodelElement::getParent() const
{
  return basyx::any();
}

void ConnectedSubmodelElement::setParent(const basyx::any & parent)
{}

void ConnectedSubmodelElement::setQualifier(const basyx::objectCollection_t & qualifiers)
{}

basyx::objectCollection_t ConnectedSubmodelElement::getQualifier() const
{
  return basyx::objectCollection_t();
}

basyx::any ConnectedSubmodelElement::getSemanticId() const
{
  return basyx::any();
}

void ConnectedSubmodelElement::setSemanticID(const basyx::any & semantic_identifier)
{}

std::string ConnectedSubmodelElement::getHasKindReference() const
{
  return std::string();
}

void ConnectedSubmodelElement::setHasKindReference(const std::string & kind)
{}

}
}
}
}
