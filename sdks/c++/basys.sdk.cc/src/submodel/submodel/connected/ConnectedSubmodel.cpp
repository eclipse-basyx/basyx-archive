/*
 * ConnectedSubmodel.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/ConnectedSubmodel.h>

#include <BaSyx/submodel/connected/submodelelement/operation/ConnectedOperation.h>
#include <BaSyx/submodel/connected/submodelelement/property/ConnectedSingleProperty.h>
#include <BaSyx/submodel/connected/submodelelement/property/ConnectedPropertyFactory.h>
#include <BaSyx/vab/core/util/VABPath.h>
#include <BaSyx/submodel/map/qualifier/AdministrativeInformation.h>
#include <BaSyx/submodel/map/identifier/Identifier.h>
#include <BaSyx/submodel/map/reference/Reference.h>
#include <BaSyx/submodel/connected/submodelelement/property/ConnectedProperty.h>

namespace basyx {
namespace submodel {

std::shared_ptr<IReference> ConnectedSubmodel::getSemanticId() const
{
  return std::make_shared<Reference>(*this->getProxyMap(IReference::Path::ReferencePath));
}

std::shared_ptr<IAdministrativeInformation> ConnectedSubmodel::getAdministration() const
{
  return std::make_shared<AdministrativeInformation>(*this->getProxyMap(IAdministrativeInformation::Path::AdministrationPath));
}

std::shared_ptr<IIdentifier> ConnectedSubmodel::getIdentification() const
{
  return std::make_shared<Identifier>(*this->getProxyMap(IIdentifier::Path::IdentifierPath));
}

basyx::specificCollection_t<IReference> ConnectedSubmodel::getDataSpecificationReferences() const
{
  AdministrativeInformation administrative_information(*this->getProxyMap(IAdministrativeInformation::Path::AdministrationPath));
  return administrative_information.getDataSpecificationReferences();
}

Kind ConnectedSubmodel::getHasKindReference() const
{
  //todo
  return Kind::NotSpecified;
}

void ConnectedSubmodel::setProperties(const basyx::object::object_map_t & properties)
{}

void ConnectedSubmodel::setOperations(const basyx::object::object_map_t & operations)
{}

std::string ConnectedSubmodel::getIdShort() const
{
  return this->getProxyValue(IReferable::Path::IdShort);
}

std::string ConnectedSubmodel::getCategory() const
{
  return this->getProxyValue(IReferable::Path::Category);
}

Description ConnectedSubmodel::getDescription() const
{
  return Description(*this->getProxyMap(IReferable::Path::Description));
}

std::shared_ptr<IReference> ConnectedSubmodel::getParent() const
{
  return std::make_shared<Reference>(*this->getProxyMap(IReferable::Path::Parent));
}

void ConnectedSubmodel::addSubModelElement(const std::shared_ptr<ISubmodelElement> & element)
{
  // Todo: May need a wrapper?
  //this->getProxy()->createElement(SubmodelPaths::SUBMODELELEMENT, element);

  //if ( dynamic_cast<IDataElement*>(element.get()) != nullptr )
  //{
  //  this->getProxy()->createElement(SubmodelPaths::PROPERTIES, element);
  //}
  //else if ( dynamic_cast<operation::IOperation*>(element.get()) != nullptr )
  //{
  //  this->getProxy()->createElement(SubmodelPaths::OPERATIONS, element);
  //}
}

basyx::specificMap_t<IDataElement> ConnectedSubmodel::getDataElements() const
{
  basyx::specificMap_t<IDataElement> map;
  auto operations = this->getProxy()->readElementValue(Path::Properties).Get<basyx::object::object_list_t>();

  for ( auto & operation : operations )
  {
    auto data_element_map = operation.Get<basyx::object::object_map_t>();
    auto id = data_element_map.at(IReferable::Path::IdShort).GetStringContent();

    vab::core::VABPath path(Path::Operations);
    path = path + id;
    auto connected_property =
      backend::connected::support::ConnectedPropertyFactory::createProperty(this->getProxy()->getDeepProxy(path));

  //todo  map.emplace( id, std::dynamic_pointer_cast<IDataElement>(connected_property) );
  }

  return map;
}

basyx::specificMap_t<IOperation> ConnectedSubmodel::getOperations() const
{
  basyx::specificMap_t<IOperation> map;
  auto operations = this->getProxy()->readElementValue(Path::Operations).Get<basyx::object::object_list_t>();

  for ( auto & operation : operations )
  {
    auto operation_map = operation.Get<basyx::object::object_map_t>();
    auto id = operation_map.at(IReferable::Path::IdShort).GetStringContent();

    vab::core::VABPath path(Path::Operations);
    path = path + id;
    auto connected_operation = std::make_shared<ConnectedOperation>(this->getProxy()->getDeepProxy(path));
    connected_operation->setLocalValues(operation_map);

    map.emplace(id, connected_operation);
  }

  return map;
}

}
}
