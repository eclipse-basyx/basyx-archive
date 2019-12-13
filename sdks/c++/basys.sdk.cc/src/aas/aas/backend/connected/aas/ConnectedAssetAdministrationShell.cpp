/*
 * ConnectedAssetAdministrationShell.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/aas/aas/backend/connected/aas/ConnectedAssetAdministrationShell.h>


namespace basyx {
namespace aas {
namespace backend {

ConnectedAssetAdministrationShell::ConnectedAssetAdministrationShell(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, std::shared_ptr<api::manager::IAssetAdministrationShellManager> manager) :
  ConnectedVABModelMap(proxy),
  manager(manager)
{}

basyx::specificCollection_t<reference::IReference> ConnectedAssetAdministrationShell::getDataSpecificationReferences() const
{
  return basyx::specificCollection_t<reference::IReference>();
}

std::string ConnectedAssetAdministrationShell::getIdShort() const
{
  return std::string();
}

std::string ConnectedAssetAdministrationShell::getCategory() const
{
  return std::string();
}

qualifier::impl::Description ConnectedAssetAdministrationShell::getDescription() const
{
  return qualifier::impl::Description("","");
}

std::shared_ptr<reference::IReference> ConnectedAssetAdministrationShell::getParent() const
{
  return std::shared_ptr<reference::IReference>();
}

std::shared_ptr<qualifier::IAdministrativeInformation> ConnectedAssetAdministrationShell::getAdministration() const
{
  return nullptr;
}

std::shared_ptr<identifier::IIdentifier> ConnectedAssetAdministrationShell::getIdentification() const
{
  return std::shared_ptr<identifier::IIdentifier>();
}

basyx::specificMap_t<ISubModel> ConnectedAssetAdministrationShell::getSubModels() const
{
  return basyx::specificMap_t<ISubModel>();
}

void ConnectedAssetAdministrationShell::addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor)
{}

std::shared_ptr<security::ISecurity> ConnectedAssetAdministrationShell::getSecurity() const
{
  return std::shared_ptr<security::ISecurity>();
}

std::shared_ptr<reference::IReference> ConnectedAssetAdministrationShell::getDerivedFrom() const
{
  return std::shared_ptr<reference::IReference>();
}

std::shared_ptr<parts::IAsset> ConnectedAssetAdministrationShell::getAsset() const
{
  return std::shared_ptr<parts::IAsset>();
}

void ConnectedAssetAdministrationShell::setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor> & submodels)
{}

basyx::specificCollection_t<descriptor::SubModelDescriptor> ConnectedAssetAdministrationShell::getSubModelDescriptors() const
{
  return basyx::specificCollection_t<descriptor::SubModelDescriptor>();
}

basyx::specificCollection_t<IView> ConnectedAssetAdministrationShell::getViews() const
{
  return basyx::specificCollection_t<IView>();
}

basyx::specificCollection_t<IConceptDictionary> ConnectedAssetAdministrationShell::getConceptDictionary() const
{
  return basyx::specificCollection_t<IConceptDictionary>();
}


}
}
}

