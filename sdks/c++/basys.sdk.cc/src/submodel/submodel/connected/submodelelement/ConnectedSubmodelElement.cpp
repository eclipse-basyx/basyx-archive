/*
 * ConnectedSubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElement.h"
#include "submodel/connected/ConnectedElement.h"
#include "submodel/map/reference/Reference.h"
#include "basyx/types.h"

namespace basyx {
namespace submodel {
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
    reference::impl::Reference data_ref(spec.Get<basyx::object::object_map_t>());
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

basyx::specificCollection_t<qualifier::qualifiable::IConstraint> ConnectedSubmodelElement::getQualifier() const
{
  //TODO not implemented
  return basyx::specificCollection_t<qualifier::qualifiable::IConstraint>(); 
}

std::shared_ptr<reference::IReference> ConnectedSubmodelElement::getSemanticId() const
{
  return std::make_shared<reference::impl::Reference>(*this->getProxyMap(reference::paths::SEMANTICIDS));
}

submodel::metamodel::map::qualifier::haskind::Kind ConnectedSubmodelElement::getHasKindReference() const
{
  //todo
  return submodel::metamodel::map::qualifier::haskind::Kind::NOTSPECIFIED;
}


basyx::object::object_list_t ConnectedSubmodelElement::getProxyCollection(const std::string & path) const
{
  auto value = this->getProxy()->readElementValue(path);
  return value.Get<basyx::object::object_list_t>();
}

std::string ConnectedSubmodelElement::getIdWithLocalCheck() const
{
  basyx::object localId = this->getLocalValue(qualifier::ReferablePaths::IDSHORT);
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
