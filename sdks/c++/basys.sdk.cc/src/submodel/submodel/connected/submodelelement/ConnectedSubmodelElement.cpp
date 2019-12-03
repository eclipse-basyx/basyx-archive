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

ConnectedSubmodelElement::ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedElement(proxy)
{}

basyx::specificCollection_t<IReference> ConnectedSubmodelElement::getDataSpecificationReferences() const
{
  auto data_specs = this->getProxyCollection(IReference::IReference::Path::DataSpecifications);

  basyx::specificCollection_t<IReference> specific_data_specs;
  for ( auto & spec : data_specs )
  {
    Reference data_ref(spec.Get<basyx::object::object_map_t>());
    specific_data_specs.push_back(std::make_shared<Reference>(data_ref));
  }

  return specific_data_specs;
}

std::string ConnectedSubmodelElement::getIdShort() const
{
  return this->getProxyValue(IReferable::Path::IdShort);
}

std::string ConnectedSubmodelElement::getCategory() const
{
  return this->getProxyValue(IReferable::Path::Category);
}

Description ConnectedSubmodelElement::getDescription() const
{
  return Description(*this->getProxyMap(IReferable::Path::Description));
}

std::shared_ptr<IReference> ConnectedSubmodelElement::getParent() const
{
  return std::make_shared<Reference>(*this->getProxyMap(IReference::Path::Parents));
}

basyx::specificCollection_t<IConstraint> ConnectedSubmodelElement::getQualifier() const
{
  //TODO not implemented
  return basyx::specificCollection_t<IConstraint>(); 
}

std::shared_ptr<IReference> ConnectedSubmodelElement::getSemanticId() const
{
  return std::make_shared<Reference>(*this->getProxyMap(IReference::Path::SemanticIds));
}

Kind ConnectedSubmodelElement::getHasKindReference() const
{
  //todo
  return Kind::NotSpecified;
}


basyx::object::object_list_t ConnectedSubmodelElement::getProxyCollection(const std::string & path) const
{
  auto value = this->getProxy()->readElementValue(path);
  return value.Get<basyx::object::object_list_t>();
}

std::string ConnectedSubmodelElement::getIdWithLocalCheck() const
{
  basyx::object localId = this->getLocalValue(IReferable::Path::IdShort);
  if ( not localId.IsNull() ) {
    return localId.Get<std::string>();
  }
  return this->getIdShort();
}

void ConnectedSubmodelElement::setIdWithLocalCheck(const std::string & id)
{
  auto localId = this->getLocalValue(IReferable::Path::IdShort);
  if ( not localId.IsNull() ) {
    this->updateLocalValue(IReferable::Path::IdShort, id);
    return;
  }
  this->setProxyValue(IReferable::Path::IdShort, id);
}

}
}
