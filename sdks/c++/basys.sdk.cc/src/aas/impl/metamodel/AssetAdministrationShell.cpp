/*
 * AssetAdministrationShell.cpp
 *
 *      Author: wendel
 */

#include "AssetAdministrationShell.h"

namespace basyx {
namespace aas {

AssetAdministrationShell::AssetAdministrationShell()
{}

AssetAdministrationShell::AssetAdministrationShell(reference::impl::Reference derivedFrom, security::Security security, parts::Asset asset, 
  basyx::object::set_t<std::shared_ptr<descriptor::SubModelDescriptor>> submodels, basyx::object::set_t< std::shared_ptr<parts::ConceptDictionary>> dictionaries,
  basyx::object::set_t< std::shared_ptr<parts::View>> views)
{}

basyx::specificCollection_t<reference::IReference> AssetAdministrationShell::getDataSpecificationReferences() const
{
  return basyx::specificCollection_t<reference::IReference>();
}

std::string AssetAdministrationShell::getIdShort() const
{
  return std::string();
}

std::string AssetAdministrationShell::getCategory() const
{
  return std::string();
}

qualifier::impl::Description AssetAdministrationShell::getDescription() const
{
  return qualifier::impl::Description("","");
}

std::shared_ptr<reference::IReference> AssetAdministrationShell::getParent() const
{
  return std::shared_ptr<reference::IReference>();
}

std::shared_ptr<qualifier::IAdministrativeInformation> AssetAdministrationShell::getAdministration() const
{
  return std::shared_ptr<qualifier::IAdministrativeInformation>();
}

std::shared_ptr<identifier::IIdentifier> AssetAdministrationShell::getIdentification() const
{
  return std::shared_ptr<identifier::IIdentifier>();
}

basyx::specificMap_t<ISubModel> AssetAdministrationShell::getSubModels() const
{
  return basyx::specificMap_t<ISubModel>();
}

void AssetAdministrationShell::addSubModel(const descriptor::SubModelDescriptor subModelDescriptor)
{}

std::shared_ptr<ISecurity> AssetAdministrationShell::getSecurity() const
{
  return std::shared_ptr<ISecurity>();
}

std::shared_ptr<reference::IReference> AssetAdministrationShell::getDerivedFrom() const
{
  return std::shared_ptr<reference::IReference>();
}

std::shared_ptr<reference::IReference> AssetAdministrationShell::getAsset() const
{
  return std::shared_ptr<reference::IReference>();
}

void AssetAdministrationShell::setSubModel(const basyx::specificCollection_t<descriptor::SubModelDescriptor> submodels) const
{}

basyx::specificCollection_t<descriptor::SubModelDescriptor> AssetAdministrationShell::getSubModelDescriptors() const
{
  return basyx::specificCollection_t<descriptor::SubModelDescriptor>();
}

basyx::specificCollection_t<IView> AssetAdministrationShell::getViews() const
{
  return basyx::specificCollection_t<IView>();
}

basyx::specificCollection_t<IConceptDictionary> AssetAdministrationShell::getConceptDictionary() const
{
  return basyx::specificCollection_t<IConceptDictionary>();
}

}
}
