/*
 * ConnectedDataElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedDataElement.h"
#include "basyx/types.h"

#include <memory>


namespace basyx {
namespace aas {
namespace backend {

ConnectedDataElement::ConnectedDataElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedSubmodelElement(proxy)
{}

basyx::objectCollection_t ConnectedDataElement::getDataSpecificationReferences() const
{
  return this->getProxyCollection(reference::paths::DATASPECIFICATIONS);
}

void ConnectedDataElement::setDataSpecificationReferences(const basyx::objectCollection_t & data_specification_references)
{
  this->setProxyValue(reference::paths::DATASPECIFICATIONS, data_specification_references);
}

std::string ConnectedDataElement::getIdShort() const
{
  return this->getProxyValue(qualifier::ReferablePaths::IDSHORT);
}

void ConnectedDataElement::setIdShort(const std::string & idShort)
{
  this->setProxyValue(qualifier::ReferablePaths::IDSHORT, idShort);
}

std::string ConnectedDataElement::getCategory() const
{
  return this->getProxyValue(qualifier::ReferablePaths::CATEGORY);
}

void ConnectedDataElement::setCategory(const std::string & category)
{
  this->setProxyValue(qualifier::ReferablePaths::CATEGORY, category);
}

std::string ConnectedDataElement::getDescription() const
{
  return this->getProxyValue(qualifier::ReferablePaths::DESCRIPTION);
}

void ConnectedDataElement::setDescription(const std::string & description)
{
  this->setProxyValue(qualifier::ReferablePaths::DESCRIPTION, description);
}

basyx::any ConnectedDataElement::getParent() const
{
  return this->getProxyCollection(reference::paths::PARENTS);
}

void ConnectedDataElement::setParent(const basyx::any & parent_reference)
{
  this->setProxyValue(reference::paths::PARENTS, parent_reference);
}

std::string ConnectedDataElement::getId() const
{
  return this->getIdWithLocalCheck();
}

void ConnectedDataElement::setId(const std::string & id)
{
  this->setIdWithLocalCheck(id);
}

basyx::objectCollection_t ConnectedDataElement::getQualifier() const
{
  return this->getProxyCollection(reference::paths::QUALIFIERS);
}

void ConnectedDataElement::setQualifier(const basyx::objectCollection_t & qualifiers)
{
  this->setProxyValue(reference::paths::QUALIFIERS, qualifiers);
}

basyx::any ConnectedDataElement::getSemanticId() const
{
  return this->getProxyValue(reference::paths::SEMANTICIDS);
}

void ConnectedDataElement::setSemanticID(const basyx::any & semantic_identifier)
{
  this->setProxyValue(reference::paths::SEMANTICIDS, semantic_identifier);
}

std::string ConnectedDataElement::getHasKindReference() const
{
  return this->getProxyValue(qualifier::haskind::Paths::KIND);
}

void ConnectedDataElement::setHasKindReference(const std::string & kind)
{
  this->setProxyValue(qualifier::haskind::Paths::KIND, kind);
}

void ConnectedDataElement::setProxyValue(const std::string & path, const basyx::any value) const
{
  this->getProxy()->updateElementValue(path, value);
}

std::string ConnectedDataElement::getProxyValue(const std::string & path) const
{
  auto value = getProxy()->readElementValue(path);
  return value.Get<std::string>();
}

basyx::objectCollection_t ConnectedDataElement::getProxyCollection(const std::string & path) const
{
  auto value = this->getProxy()->readElementValue(path);
  return value.Get<basyx::objectCollection_t>();
}

std::string ConnectedDataElement::getIdWithLocalCheck() const
{
  basyx::any localId = this->getLocalValue(qualifier::ReferablePaths::IDSHORT);
  if ( not localId.IsNull() )
  {
    return localId.Get<std::string>();
  }
  return this->getIdShort();
}

void ConnectedDataElement::setIdWithLocalCheck(const std::string & id)
{
  auto localId = this->getLocalValue(qualifier::ReferablePaths::IDSHORT);
  if ( not localId.IsNull() )
  {
    this->updateLocalValue(qualifier::ReferablePaths::IDSHORT, id);
    return;
  }
  this->setProxyValue(qualifier::ReferablePaths::IDSHORT, id);
}

}
}
}
