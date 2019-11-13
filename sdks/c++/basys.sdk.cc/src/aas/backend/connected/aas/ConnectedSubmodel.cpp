/*
 * ConnectedSubmodel.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodel.h"

#include "submodelelement/operation/ConnectedOperation.h"
#include "submodelelement/property/ConnectedSingleProperty.h"
#include "backend/support/submodelelement/property/ConnectedPropertyFactory.h"
#include "vab/core/util/VABPath.h"
#include "impl/metamodel/hashmap/aas/qualifier/AdministrativeInformation.h"
#include "impl/metamodel/hashmap/aas/identifier/Identifier.h"
#include "impl/metamodel/hashmap/aas/reference/Reference.h"
#include "submodelelement/property/ConnectedProperty.h"

namespace basyx {
namespace aas {
namespace backend {

using namespace submodelelement::operation;

std::shared_ptr<reference::IReference> ConnectedSubmodel::getSemanticId() const
{
  return std::make_shared<reference::impl::Reference>(*this->getProxyMap(reference::paths::REFERENCEPATH));
}

std::shared_ptr<qualifier::IAdministrativeInformation> ConnectedSubmodel::getAdministration() const
{
  return std::make_shared<qualifier::impl::AdministrativeInformation>(*this->getProxyMap(qualifier::administrationPaths::administrationPath));
}

std::shared_ptr<identifier::IIdentifier> ConnectedSubmodel::getIdentification() const
{
  return std::make_shared<identifier::impl::Identifier>(*this->getProxyMap(identifier::identifierPaths::identifierPath));
}

basyx::specificCollection_t<reference::IReference> ConnectedSubmodel::getDataSpecificationReferences() const
{
  qualifier::impl::AdministrativeInformation administrative_information(*this->getProxyMap(qualifier::administrationPaths::administrationPath));
  return administrative_information.getDataSpecificationReferences();
}

submodel::metamodel::map::qualifier::haskind::Kind ConnectedSubmodel::getHasKindReference() const
{
  //todo
  return submodel::metamodel::map::qualifier::haskind::Kind::NOTSPECIFIED;
}

void ConnectedSubmodel::setProperties(const basyx::objectMap_t & properties)
{}

void ConnectedSubmodel::setOperations(const basyx::objectMap_t & operations)
{}

std::string ConnectedSubmodel::getIdShort() const
{
  return this->getProxyValue(qualifier::ReferablePaths::IDSHORT);
}

std::string ConnectedSubmodel::getCategory() const
{
  return this->getProxyValue(qualifier::ReferablePaths::CATEGORY);
}

qualifier::impl::Description ConnectedSubmodel::getDescription() const
{
  return qualifier::impl::Description(*this->getProxyMap(qualifier::ReferablePaths::DESCRIPTION));
}

std::shared_ptr<reference::IReference> ConnectedSubmodel::getParent() const
{
  return std::make_shared<reference::impl::Reference>(*this->getProxyMap(qualifier::ReferablePaths::PARENT));
}

void ConnectedSubmodel::addSubModelElement(const std::shared_ptr<submodelelement::ISubmodelElement> & element)
{
  // Todo: May need a wrapper?
  //this->getProxy()->createElement(SubmodelPaths::SUBMODELELEMENT, element);

  //if ( dynamic_cast<submodelelement::IDataElement*>(element.get()) != nullptr )
  //{
  //  this->getProxy()->createElement(SubmodelPaths::PROPERTIES, element);
  //}
  //else if ( dynamic_cast<submodelelement::operation::IOperation*>(element.get()) != nullptr )
  //{
  //  this->getProxy()->createElement(SubmodelPaths::OPERATIONS, element);
  //}
}

basyx::specificMap_t<submodelelement::IDataElement> ConnectedSubmodel::getDataElements() const
{
  basyx::specificMap_t<submodelelement::IDataElement> map;
  auto operations = this->getProxy()->readElementValue(SubmodelPaths::PROPERTIES).Get<basyx::objectCollection_t>();

  for ( auto & operation : operations )
  {
    auto data_element_map = operation.Get<basyx::objectMap_t>();
    auto id = data_element_map.at(qualifier::ReferablePaths::IDSHORT).GetStringContent();

    vab::core::VABPath path(SubmodelPaths::OPERATIONS);
    path = path + id;
    auto connected_property =
      backend::connected::support::ConnectedPropertyFactory::createProperty(this->getProxy()->getDeepProxy(path));

    map.emplace( id, std::dynamic_pointer_cast<submodelelement::IDataElement>(connected_property) );
  }

  return map;
}

basyx::specificMap_t<IOperation> ConnectedSubmodel::getOperations() const
{
  basyx::specificMap_t<IOperation> map;
  auto operations = this->getProxy()->readElementValue(SubmodelPaths::OPERATIONS).Get<basyx::objectCollection_t>();

  for ( auto & operation : operations )
  {
    auto operation_map = operation.Get<basyx::objectMap_t>();
    auto id = operation_map.at(qualifier::ReferablePaths::IDSHORT).GetStringContent();

    vab::core::VABPath path(SubmodelPaths::OPERATIONS);
    path = path + id;
    auto connected_operation = std::make_shared<connected::ConnectedOperation>(this->getProxy()->getDeepProxy(path));
    connected_operation->setLocalValues(operation_map);

    map.emplace(id, connected_operation);
  }

  return map;
}

}
}
}
