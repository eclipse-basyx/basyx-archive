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

backend::ConnectedDataElement::ConnectedDataElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedSubmodelElement(proxy)
{}

std::vector< std::shared_ptr<IReference>> backend::ConnectedDataElement::getDataSpecificationReferences() const
{
  return std::vector< std::shared_ptr<IReference>>();
}

void backend::ConnectedDataElement::setDataSpecificationReferences(const std::vector< std::shared_ptr<IReference>>& ref)
{}

std::string backend::ConnectedDataElement::getIdshort() const
{
  return this->getValue(qualifier::ReferablePaths::IDSHORT);
}

void backend::ConnectedDataElement::setIdshort(const std::string & idShort)
{
  this->setValue(qualifier::ReferablePaths::IDSHORT, idShort);
}

std::string backend::ConnectedDataElement::getCategory() const
{
  return this->getValue(qualifier::ReferablePaths::CATEGORY);
}

void backend::ConnectedDataElement::setCategory(const std::string & category)
{
  this->setValue(qualifier::ReferablePaths::CATEGORY, category);
}

std::string backend::ConnectedDataElement::getDescription() const
{
  return this->getValue(qualifier::ReferablePaths::DESCRIPTION);
}

void backend::ConnectedDataElement::setDescription(const std::string & description)
{
  this->setValue(qualifier::ReferablePaths::DESCRIPTION, description);
}

std::shared_ptr<IReference> backend::ConnectedDataElement::getParent() const
{
  //TODO
  //return this->getValue(qualifier::ReferablePaths::PARENT);
  return nullptr;
}

void backend::ConnectedDataElement::setParent(const std::shared_ptr<IReference>& obj)
{}

std::string backend::ConnectedDataElement::getId() const
{
  auto localId = this->getLocalValue(qualifier::ReferablePaths::IDSHORT);
  if ( not localId.IsNull() )
  {
    return localId.Get<std::string>();
  }
  return this->getIdshort();
}

void backend::ConnectedDataElement::setId(const std::string & id)
{
  auto localId = this->getLocalValue(qualifier::ReferablePaths::IDSHORT);
  if ( not localId.IsNull() )
  {
    this->setLocalValue(qualifier::ReferablePaths::IDSHORT, id);
  }
  this->setValue(qualifier::ReferablePaths::IDSHORT, id);
}

std::vector< std::shared_ptr<IConstraint>> backend::ConnectedDataElement::getQualifier() const
{
  //TODO
  return std::vector< std::shared_ptr<IConstraint>>();
}

void backend::ConnectedDataElement::setQualifier(const std::vector< std::shared_ptr<IConstraint>>& qualifiers)
{
//TODO
}

std::shared_ptr<std::shared_ptr<IReference>> backend::ConnectedDataElement::getSemanticId() const
{
  //TODO
  return std::shared_ptr<std::shared_ptr<IReference>>();
}

void backend::ConnectedDataElement::setSemanticID(const std::shared_ptr<std::shared_ptr<IReference>>& ref)
{
  //TODO
}

std::string backend::ConnectedDataElement::getHasKindReference() const
{
  return this->getValue(qualifier::haskind::Paths::KIND);
}

void backend::ConnectedDataElement::setHasKindReference(const std::string & kind)
{
  this->setValue(qualifier::haskind::Paths::KIND, kind);
}

void backend::ConnectedDataElement::setValue(const std::string & path, const basyx::any value) const
{
  this->getProxy()->updateElementValue(path, value);
}

std::string backend::ConnectedDataElement::getValue(const std::string & path) const
{
  auto value = getProxy()->readElementValue(path);
  return value.Get<std::string>();
}

}
}
