/*
 * ConnectedSubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElement.h"
#include "backend/connected/aas/ConnectedElement.h"
#include "impl/metamodel/hashmap/aas/reference/Reference.h"
#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

ConnectedSubmodelElement::ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  backend::ConnectedElement(proxy)
{}

basyx::specificCollection_t<reference::IReference> ConnectedSubmodelElement::getDataSpecificationReferences() const
{
  auto data_specs = this->getProxyCollection(reference::paths::DATASPECIFICATIONS);

  basyx::specificCollection_t<reference::IReference> specific_data_specs;
  for ( auto & spec : data_specs )
  {
    reference::impl::Reference data_ref(spec.Get<basyx::objectMap_t>());
    specific_data_specs.push_back(std::make_shared<reference::impl::Reference>(data_ref));
  }

  return specific_data_specs;
}

std::string ConnectedSubmodelElement::getIdShort() const
{
  return this->getProxyValue(qualifier::ReferablePaths::IDSHORT);
}

std::string ConnectedSubmodelElement::getCategory() const
{
  return this->getProxyValue(qualifier::ReferablePaths::CATEGORY);
}

qualifier::impl::Description ConnectedSubmodelElement::getDescription() const
{
  return qualifier::impl::Description(*this->getProxyMap(qualifier::ReferablePaths::DESCRIPTION));
}

std::shared_ptr<reference::IReference> ConnectedSubmodelElement::getParent() const
{
  return std::make_shared<reference::impl::Reference>(*this->getProxyMap(reference::paths::PARENTS));
}

basyx::objectCollection_t ConnectedSubmodelElement::getQualifier() const
{
  return this->getProxyCollection(reference::paths::QUALIFIERS);
}

std::shared_ptr<reference::IReference> ConnectedSubmodelElement::getSemanticId() const
{
  return std::make_shared<reference::impl::Reference>(*this->getProxyMap(reference::paths::SEMANTICIDS));
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
