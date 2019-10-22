/*
 * ConnectedSubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElement.h"
#include "backend/connected/aas/ConnectedElement.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

ConnectedSubmodelElement::ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  backend::ConnectedElement(proxy)
{}

basyx::objectCollection_t ConnectedSubmodelElement::getDataSpecificationReferences() const
{
  return this->getProxyCollection(reference::paths::DATASPECIFICATIONS);
}

std::string ConnectedSubmodelElement::getIdShort() const
{
  return this->getProxyValue(qualifier::ReferablePaths::IDSHORT);
}

std::string ConnectedSubmodelElement::getCategory() const
{
  return this->getProxyValue(qualifier::ReferablePaths::CATEGORY);
}

std::string ConnectedSubmodelElement::getDescription() const
{
  return this->getProxyValue(qualifier::ReferablePaths::DESCRIPTION);
}

basyx::any ConnectedSubmodelElement::getParent() const
{
  return this->getProxyCollection(reference::paths::PARENTS);
}

basyx::objectCollection_t ConnectedSubmodelElement::getQualifier() const
{
  return this->getProxyCollection(reference::paths::QUALIFIERS);
}

basyx::any ConnectedSubmodelElement::getSemanticId() const
{
  return this->getProxyValue(reference::paths::SEMANTICIDS);
}

std::string ConnectedSubmodelElement::getHasKindReference() const
{
  return this->getProxyValue(qualifier::haskind::Paths::KIND);
}


basyx::objectCollection_t ConnectedSubmodelElement::getProxyCollection(const std::string & path) const
{
  auto value = this->getProxy()->readElementValue(path);
  return value.Get<basyx::objectCollection_t>();
}



std::string ConnectedSubmodelElement::getIdWithLocalCheck() const
{
  basyx::any localId = this->getLocalValue(qualifier::ReferablePaths::IDSHORT);
  if ( not localId.IsNull() ) {
    return localId.Get<std::string>();
  }
  return this->getIdShort();
}

void ConnectedSubmodelElement::setIdWithLocalCheck(const std::string & id)
{
  auto localId = this->getLocalValue(qualifier::ReferablePaths::IDSHORT);
  if ( not localId.IsNull() ) {
    this->updateLocalValue(qualifier::ReferablePaths::IDSHORT, id);
    return;
  }
  this->setProxyValue(qualifier::ReferablePaths::IDSHORT, id);
}

}
}
}
}
