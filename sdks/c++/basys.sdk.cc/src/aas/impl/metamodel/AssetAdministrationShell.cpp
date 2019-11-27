/*
 * AssetAdministrationShell.cpp
 *
 *      Author: wendel
 */

#include "AssetAdministrationShell.h"

namespace basyx {
namespace aas {

AssetAdministrationShell::AssetAdministrationShell() :
  description{"",""}
{}

AssetAdministrationShell::AssetAdministrationShell(std::shared_ptr<reference::impl::Reference> derivedFrom, std::shared_ptr<security::Security> security, 
      std::shared_ptr<parts::Asset> asset, basyx::specificCollection_t<descriptor::SubModelDescriptor> submodels, 
      basyx::specificCollection_t<IConceptDictionary> dictionaries, basyx::specificCollection_t<IView> views) :
  derivedFrom{derivedFrom},
  security{security},
  asset{asset},
  submodels{submodels},
  dictionaries{dictionaries},
  views{views},
  description{"",""}
{}

void AssetAdministrationShell::setEndpoint(const std::string & endpoint, const std::string & endpointType)
{
  this->endpoint = endpoint;
  this->endpointType = endpointType;
}

basyx::object::list_t<basyx::object::hash_map_t<std::string>> AssetAdministrationShell::getEndpoints()
{
  basyx::object::hash_map_t<std::string> map {{"type", endpointType}, {"address", endpoint}};
  basyx::object::list_t<basyx::object::hash_map_t<std::string>> list;
  list.push_back(map);

  return list;
}

basyx::specificCollection_t<reference::IReference> AssetAdministrationShell::getDataSpecificationReferences() const
{
  return this->dataSpecificationReferences;
}

void AssetAdministrationShell::setDataSpecificationReferences(const basyx::specificCollection_t<reference::IReference>& references)
{
  this->dataSpecificationReferences = references;
}

std::string AssetAdministrationShell::getIdShort() const
{
  return this->idShort;
}

std::string AssetAdministrationShell::getCategory() const
{
  return this->category;
}

void AssetAdministrationShell::setCategory(const std::string & category)
{
  this->category = category;
}

qualifier::impl::Description AssetAdministrationShell::getDescription() const
{
  return this->description;
}

void AssetAdministrationShell::setDescription(const qualifier::impl::Description & description)
{
  this->description = description;
}

std::shared_ptr<reference::IReference> AssetAdministrationShell::getParent() const
{
  return this->parent;
}

void AssetAdministrationShell::setParent(const std::shared_ptr<reference::IReference>& parent)
{
  this->parent = parent;
}

std::shared_ptr<qualifier::IAdministrativeInformation> AssetAdministrationShell::getAdministration() const
{
  return this->administration;
}

void AssetAdministrationShell::setAdministration(const std::shared_ptr<qualifier::IAdministrativeInformation>& administrativeInformation)
{
  this->administration = administrativeInformation;
}

std::shared_ptr<identifier::IIdentifier> AssetAdministrationShell::getIdentification() const
{
  return this->identification;
}

void AssetAdministrationShell::setIdentification(const std::shared_ptr<identifier::IIdentifier>& identification)
{
  this->identification = identification;
}

basyx::specificMap_t<ISubModel> AssetAdministrationShell::getSubModels() const
{
  // not allowed on local copies
}

void AssetAdministrationShell::addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor)
{
  this->submodels.push_back(std::make_shared<descriptor::SubModelDescriptor>(subModelDescriptor));
}

std::shared_ptr<security::ISecurity> AssetAdministrationShell::getSecurity() const
{
  return this->security;
}

void AssetAdministrationShell::setSecurity(const std::shared_ptr<security::ISecurity>& security)
{
  this->security = security;
}

std::shared_ptr<reference::IReference> AssetAdministrationShell::getDerivedFrom() const
{
  return this->derivedFrom;
}

void AssetAdministrationShell::setDerivedFrom(const std::shared_ptr<reference::IReference>& derivedFrom)
{
  this->derivedFrom = derivedFrom;
}

std::shared_ptr<parts::IAsset> AssetAdministrationShell::getAsset() const
{
  return this->asset;
}

void AssetAdministrationShell::setAsset(const std::shared_ptr<parts::IAsset> & asset)
{
  this->asset = asset;
}

void AssetAdministrationShell::setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor> & submodels)
{
  this->submodels = submodels;
}

basyx::specificCollection_t<descriptor::SubModelDescriptor> AssetAdministrationShell::getSubModelDescriptors() const
{
  return this->submodels;
}

basyx::specificCollection_t<IView> AssetAdministrationShell::getViews() const
{
  return this->views;
}

void AssetAdministrationShell::setViews(const basyx::specificCollection_t<IView>& views)
{
  this->views = views;
}

basyx::specificCollection_t<IConceptDictionary> AssetAdministrationShell::getConceptDictionary() const
{
  return dictionaries;
}

void AssetAdministrationShell::setConceptDictionary(const basyx::specificCollection_t<IConceptDictionary>& dictionaries)
{
  this->dictionaries = dictionaries;
}

void AssetAdministrationShell::addConceptDescription(const std::shared_ptr<parts::IConceptDescription> & description)
{
  if ( dictionaries.empty() )
  {
    dictionaries.push_back(std::make_shared<parts::ConceptDictionary>());
  }
  auto back = std::dynamic_pointer_cast<parts::ConceptDictionary>(dictionaries.back());
  back->addConceptDescription(description);
}

}
}
